/*
 * This framework code is taken almost 
 * entirely from Beginning Android Games
 * By Mario Zechner
 * It is published under a new BSD licence
 * Apress
 * Thanks dude.
*/
package com.tyz.androidgames.pyramid;

import java.io.ObjectInputStream.GetField;
import java.util.List;

import android.graphics.Bitmap;
import android.util.Log;

import com.tyz.androidgames.framework.Game;
import com.tyz.androidgames.framework.Graphics;
import com.tyz.androidgames.framework.Input.TouchEvent;
import com.tyz.androidgames.framework.Pixmap;
import com.tyz.androidgames.framework.Screen;
import com.tyz.androidgames.framework.impl.AndroidFileIO;
import com.tyz.androidgames.framework.impl.AndroidGraphics;
import com.tyz.androidgames.framework.impl.AndroidPixmap;
import com.tyz.androidgames.framework.impl.AndroidSound;
import com.tyz.androidgames.gem.Gem;
import com.tyz.androidgames.gem.GemManager;
import com.tyz.androidgames.gem.GemView;
import com.tyz.androidgames.pyramid.World.GameState;
import com.tyz.androidgames.pyramid.World.PlayState;

/**
 * @author daniel
 *
 */
public class GameScreen extends Screen {
	GemManager gm = GemManager.getInstance();
	//static final private Pixmap atlas_spotted = ((AndroidPixmap)Assets.spotted);
	ScreenRects sr = new ScreenRects(game.getGraphics());
	
	//State information has been moved to the World Object
	
    World world;
    int oldScore = 5;
    int score = 5;
    
    public GameScreen(Game game) {
        super(game);
        world = World.getInstance();
    }

    /*
     * (non-Javadoc)
     * Update and the following methods are called by
     * the fast renderview (along with present.
     * @see com.tyz.androidgames.framework.Screen#update(float)
     */
    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        
        if(world.state == GameState.Ready)
            updateReady(touchEvents);
        if(world.state == GameState.Running)
            updateRunning(touchEvents, deltaTime);
        if(world.state == GameState.Paused)
            updatePaused(touchEvents);
        if(world.state == GameState.GameOver)
            updateGameOver(touchEvents);        
    }
    
    private void updateReady(List<TouchEvent> touchEvents) {
        if(touchEvents.size() > 0)
        	world.state = GameState.Running;
        	world.pstate = PlayState.Displaying;
    }
    
    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {        
        int len = touchEvents.size();
        Graphics g = game.getGraphics();
        
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i); 

            
            if(event.type == TouchEvent.TOUCH_DOWN) {
            	//Cheat set score to 99
/*            	if(event.x < 25 && event.y < 25){
            		score = world.score = 99;
            		//TODO
//            		Settings.reset(game.getFileIO()); 
            	}*/
            	
            	if(sr.getRect("ChooseButtons").contains(event.x,event.y)) {
                	world.pstate = PlayState.AwaitingChoice;
                    if(Settings.soundEnabled){
                        ((AndroidSound) Assets.warning).stop();  
                        Assets.detectpossible.play(1);                	
                    }
                	gm.getGemSelected().clear();
                }
            	if(	(world.pstate==PlayState.AwaitingChoice) &&
                    ((sr.getRect("Pool").contains(event.x,event.y)) ||
                    	(sr.getRect("Palette").contains(event.x,event.y)))) {
                    	// Get selected gem into selectionBox
            		int selected = -1;
            		boolean usePalette = event.y > sr.getRect("Palette").top?true:false;
            		List<Gem> myArr = usePalette ?	gm.getGemPalette():
            										gm.getGemPool();
            		
            		if(event.x < (g.getWidth()/myArr.size())){
            			selected = 0;
            		}else if(event.x < (g.getWidth()/myArr.size())*2){
            			selected = 1;            			
            		}else if(event.x < (g.getWidth()/myArr.size())*3){
            			selected = 2;            			
            		}else{
            			selected = 3;            			
            		}
            		if(!gm.isUsed(usePalette?0:1,selected)){
	               		Gem gem = myArr.get(selected);
	               		gm.usedSet(usePalette?0:1,selected);
	               		gm.updateSelected(gem);
            		}
                }
            }
        }
        
        world.update(deltaTime, world.pstate);
        if(world.gameOver) {
            if(Settings.soundEnabled)
                //DDroid Play game over sound
            	world.state = GameState.GameOver;
        }
        if(oldScore != world.score) {
        	//DMK - not sure what this does
            //oldScore = world.score;
            //score = "" + oldScore;
            if(Settings.soundEnabled)
                ;//TODO //DDroid play bonus sound
        }
    }
    
    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
            	//Resume / quit
                if(sr.getRect("Resume").contains(event.x,event.y)) {
                    if(Settings.soundEnabled)
                        Assets.tick.play(1);
                    world.state = GameState.Running;
                    return;
                }                    
                if(sr.getRect("Quit").contains(event.x,event.y)) {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    //game.setScreen(new MainMenuScreen(game));
                    world.state = GameState.GameOver;
                    return;
                }
            }
        }
    }
    
    private void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x >= 128 && event.x <= 192 &&
                   event.y >= 200 && event.y <= 264) {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }
    }
    
    /*
     * End of "Update" methods
     */
    
    /*
     * The present methods are also called from the fastRenderView
     * First the general world elements are drawn, then any
     * state-specific drawing is done
     */
    
    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        Pixmap p = null;
        
        if(score < 11){
        	p = Assets.background;
        }else if(score < 20){
        	p = Assets.background_2;
        }else if(score <= 99){
        	p = Assets.background_3;
        }else if(score > 99){
        	p = Assets.background_4;
        }else{
        	assert(false);
        }
        
        g.drawPixmap(p, 0, 0);
        drawWorld(world);
        if(world.state == GameState.Ready) 
            drawReadyUI();
        if(world.state == GameState.Running)
            drawRunningUI();
        if(world.state == GameState.Paused)
            drawPausedUI();
        if(world.state == GameState.GameOver)
            drawGameOverUI();
    }
    
    /*
     * Draw world and methods
     */
    private void drawWorld(World world) {
        Graphics g = game.getGraphics();
        // DDroid Lots of draw stuff here may need to check with book or original
        drawPool(g);
        drawPalette(g);
        drawSelection(g);    
        evaluateChoice(g); //TODO This does not belong in draw code
        drawScore(g);
    }
    
    private void drawPool(Graphics g){
        Bitmap bm = null;
        int xpos = sr.getRect("Pool").left;
        int ypos = sr.getRect("Pool").top;

        for (Gem g1 : gm.getGemPool()){
        	bm = GemView.getBitmap(g1);

        	((AndroidGraphics)g).drawPixmap(bm,xpos,ypos);
        	xpos += (g.getWidth()/gm.getGemPool().size());
        }
    }

    private void drawPalette(Graphics g){
        Bitmap bm = null;
        
        int xpos = sr.getRect("Palette").left;
        int ypos = sr.getRect("Palette").top;

        for (Gem g1 : gm.getGemPalette()){
        	bm = GemView.getBitmap(g1);
        	((AndroidGraphics)g).drawPixmap(bm,xpos,ypos);
        	xpos += bm.getWidth()+5;
        }
    }
    
    /**
     *Handle the display of selecting items 
     */
    private void drawSelection(Graphics g){
    	if(world.pstate==PlayState.AwaitingChoice){
	    	//Draw the boxes
    		g.drawPixmap(Assets.selectionFrame, sr.getRect("SelectBox1").left,sr.getRect("SelectBox1").top);                	
	    	g.drawPixmap(Assets.selectionFrame, sr.getRect("SelectBox2").left,sr.getRect("SelectBox2").top);                	
	    	g.drawPixmap(Assets.selectionFrame, sr.getRect("SelectBox3").left,sr.getRect("SelectBox3").top);

	    	//Mark the selected gems
	    	boolean[] arr = gm.usedGetArray();
	    	int USED_FRAME_WIDTH = 80;
			for(int i=0; i<gm.GEMS_IN_PALETTE;i++){
				if(arr[i]){
					//mark palette   		

					g.drawPixmap(Assets.usedFrame, USED_FRAME_WIDTH * i,sr.getRect("Palette").top);                	
				}
			}
			for(int i=0; i<gm.GEMS_IN_POOL;i++){
				if(arr[gm.GEMS_IN_PALETTE+i]){
					//mark pool
		    		g.drawPixmap(Assets.usedFrame, USED_FRAME_WIDTH * i,sr.getRect("Pool").top);                	
				}
			}
	    	
	    	//Draw the selected gems in the selection boxes
	    	int i = 0;
	    	for(Gem g1 : gm.getGemSelected()){
	    		String whichBox = "SelectBox" + (i+=1); 
           		((AndroidGraphics)g).drawPixmap(Bitmap.createScaledBitmap(GemView.getBitmap(g1), 60, 60, false),
							sr.getRect(whichBox).left+27,
							sr.getRect(whichBox).top+27); 	     		
	    	}
	    	
	    	
	    	//Have all selections been made?
	    	if(gm.getGemSelected().size() == gm.GEMS_SELECTABLE){
	    		world.pstate = PlayState.ChoiceMade;
	    	}
    	}
    }

    private void drawScore(Graphics g){
    	int textWidth = AndroidGraphics.getDrawTextWidth(String.valueOf(score), Assets.numbers_w);
   		((AndroidGraphics)g).drawPixmap(Bitmap.createScaledBitmap(((AndroidPixmap)
   					Assets.selectionFrame).getBitmap(), textWidth + 25, 40, false),
				sr.getRect("Score").left-10,
				sr.getRect("Score").top-5);    	
    	((AndroidGraphics)g).drawText(g,String.valueOf(score),sr.getRect("Score").left,sr.getRect("Score").top+3,Assets.numbers_w);
    }
    
    private void evaluateChoice(Graphics g){
    	if(world.pstate == PlayState.ChoiceMade){
    		if(gm.isMatch(gm.getGemSelected().get(0),
    				gm.getGemSelected().get(1),
    				gm.getGemSelected().get(2))){
                if(Settings.soundEnabled)
                    Assets.goodcombo.play(1);    			
    			score++;
    				
    			gm.usedReplace();
    		}else{
                if(Settings.soundEnabled)
                    Assets.badcombo.play(1);      			
    			score--;
    			if(score < 0){
    				world.state = GameState.GameOver;
    			}
    			Log.d("Pyramid",gm.getMatchError());
    		}
    		world.score = score;
        	gm.getGemSelected().clear();
        	gm.usedReset();
        	world.pstate = PlayState.Displaying;
    	}
    }
    
    /*
     * End of drawworld methods, next are the state specific drawings
     */
        
    private void drawReadyUI() {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.ready, sr.getRect("Ready").left,sr.getRect("Ready").top);
    }
    
    private void drawRunningUI() {
        Graphics g = game.getGraphics();
        //This is the button that is pressed when a pyramid is spotted
        // 
        int buttonHeight = 100;
        // Code for skinning:
        int mult = 0;
        if(score < 11){
        	mult = 0;
        }else if(score < 20){
        	mult = 1;
        }else if(score < 99){
        	mult = 2;
        }else if(score > 99){
        	mult = 3;
        }else{
        	assert(false);
        }
        	
        g.drawPixmap(Assets.chooseButtons,
        		sr.getRect("ChooseButtons").left,
        		sr.getRect("ChooseButtons").top,
        		0,
        		(mult*buttonHeight),
        		226,
        		buttonHeight);

    }
    
    private void drawPausedUI() {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.pause, sr.getRect("Pause").left,sr.getRect("Pause").top);
    }

    private void drawGameOverUI() {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.gameOver, 
        		sr.getRect("GameOver").left, 
        		sr.getRect("GameOver").top);
/*        g.drawPixmap(Assets.gameOver, 62, 100);
        g.drawPixmap(Assets.buttons, 128, 200, 0, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);*/
    }
    

    
    @Override
    public void pause() {
        if(world.state == GameState.Running)
        	world.state = GameState.Paused;
        
        if(world.gameOver) {
            Settings.addScore(world.score);
            Settings.save(game.getFileIO());
            world.gameOver = false;
        }
        if(world.state == GameState.GameOver){
        	world.gameOver= true;
        }
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void dispose() {
        
    }
}