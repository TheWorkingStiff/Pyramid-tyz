package com.tyz.androidgames.pyramid;

import java.util.List;

import android.graphics.Bitmap;

import com.tyz.androidgames.framework.Game;
import com.tyz.androidgames.framework.Graphics;
import com.tyz.androidgames.framework.Input.TouchEvent;
import com.tyz.androidgames.framework.Screen;
import com.tyz.androidgames.framework.impl.AndroidGraphics;
import com.tyz.androidgames.gem.Gem;
import com.tyz.androidgames.gem.GemView;

public class MainMenuScreen extends Screen {
	World world = World.getInstance();
	ScreenRects sr = null;
	
    public MainMenuScreen(Game game) {
        super(game);   
        sr = new ScreenRects(game.getGraphics());
    }   

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();       
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(sr.getRect("Play").contains(event.x,event.y)) {
                	world.state = World.GameState.Ready;
                	world.gameOver = false;
                    game.setScreen(new GameScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                if(sr.getRect("HighScores").contains(event.x,event.y)) {
                    game.setScreen(new HighscoreScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                if(sr.getRect("Help").contains(event.x,event.y)) {
                    game.setScreen(new HelpScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
            }
        }
    }
    
    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
        if(event.x > x && event.x < x + width - 1 && 
           event.y > y && event.y < y + height - 1) 
            return true;
        else
            return false; 
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.logo, 32, 20);
        g.drawPixmap(Assets.mainMenu, sr.getRect("Play").left, sr.getRect("Play").top);
        g.drawPixmap(Assets.pyramid, sr.getRect("Pyramid").left, sr.getRect("Pyramid").top);
        
/*
        if(Settings.soundEnabled)
            g.drawPixmap(Assets.buttons, 0, 416, 0, 0, 64, 64);
        else
            g.drawPixmap(Assets.buttons, 0, 416, 64, 0, 64, 64);*/
    }

    @Override
    public void pause() {        
        if(world.gameOver){
        	Settings.save(game.getFileIO());
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
