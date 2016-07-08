package com.tyz.androidgames.framework.impl;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.tyz.androidgames.framework.Audio;
import com.tyz.androidgames.framework.FileIO;
import com.tyz.androidgames.framework.Game;
import com.tyz.androidgames.framework.Graphics;
import com.tyz.androidgames.framework.Input;
import com.tyz.androidgames.framework.Screen;
import com.tyz.androidgames.pyramid.AboutScreen;
import com.tyz.androidgames.pyramid.Assets;
import com.tyz.androidgames.pyramid.HelpScreen;
import com.tyz.androidgames.pyramid.R;
import com.tyz.androidgames.pyramid.Settings;
import com.tyz.androidgames.pyramid.World;

public abstract class AndroidGame extends Activity implements Game {
    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    WakeLock wakeLock;
    World world = World.getInstance();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        int frameBufferWidth = isLandscape ? 480 : 320;
        int frameBufferHeight = isLandscape ? 320 : 480;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Config.RGB_565);
        
        float scaleX = (float) frameBufferWidth
                / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float) frameBufferHeight
                / getWindowManager().getDefaultDisplay().getHeight();

        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(getAssets());
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        screen = getStartScreen();
        setContentView(renderView);
        
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
    }

    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        renderView.pause();
        screen.pause();

        if (isFinishing())
            screen.dispose();
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");
        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }
    
    public Screen getCurrentScreen() {
        return screen;
    }
    
    //ddroid added: 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.pause:
            menuPause();
            return true;
        case R.id.quit:
        	menuQuit();
            return true;
        case R.id.help:
        	menuHelp();
            return true;
        case R.id.about:
        	menuAbout();
            return true;
        case R.id.sound:
        	menuSound();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }        
    }   
    
    void menuPause(){
        if(Settings.soundEnabled)
            Assets.click.play(1);
    	world.state = World.GameState.Paused;
        return;	
    	//Toast.makeText(this, "pause", Toast.LENGTH_LONG).show();
    }
    
    void menuQuit() {
        world.state = World.GameState.GameOver;
    	setScreen(getStartScreen()); 
    	if(Settings.soundEnabled)
            Assets.click.play(1);
        return;	
 
    }
    
    void menuHelp() {
        this.setScreen(new HelpScreen(this));
        if(Settings.soundEnabled)
            Assets.click.play(1);
        return;	
    }
    void menuAbout() {
        this.setScreen(new AboutScreen(this));
        if(Settings.soundEnabled)
            Assets.click.play(1);
        return;	
    }
    
    void menuSound() {
    	Settings.soundEnabled = !Settings.soundEnabled;
    	Toast.makeText(this, "Sound is " + (Settings.soundEnabled?"on.":"off.") , 
    			Toast.LENGTH_LONG).show();
        if(Settings.soundEnabled)
            Assets.click.play(1);
    }
    
}