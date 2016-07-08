package com.tyz.androidgames.pyramid;

import com.tyz.androidgames.gem.GemManager;


public class World {
    static final int WORLD_WIDTH = 10;
    static final int WORLD_HEIGHT = 13;
    static final int SCORE_INCREMENT = 10;
    static final float TICK_INITIAL = 7.0f;
    static final float TICK_DECREMENT = 0.05f;
	private static World instance = null;
	GemManager gm = GemManager.getInstance();
    
    public enum GameState {
        Ready,
        Running,
        Paused,
        GameOver
    }

    public enum PlayState {
    	Displaying,
    	AwaitingChoice,
    	ChoiceMade
    }
    
    public PlayState pstate = PlayState.Displaying;
    public GameState state = GameState.Ready;
    
    public boolean gameOver = false;;
    public int score = 0;
    boolean inWarning = false;

    boolean fields[][] = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
    //Random random = new Random();
    float tickTime = 0;
    static float tick = TICK_INITIAL;
    

	public static World getInstance(){
		if(instance == null){
			instance = new World();
		}
		return instance;
	}
	
	
	private World(){
		//Defeat instantiation
	}

	
	public void update(float deltaTime, PlayState ps) {
        pstate = ps;
        float warningLength = 4; //Seconds
        
        if (gameOver)
            return;

        tickTime += deltaTime;

        if((!inWarning) && ((tickTime + warningLength) > tick)){
            if(Settings.soundEnabled)
                Assets.warning.play(1);
            inWarning = true;
        }
        
        while (tickTime > tick) {
            tickTime -= tick;
            // DDroid assess current game state here.
            if(pstate == PlayState.Displaying){
            	inWarning = false;
            	gm.advance();
            }
            if (/*good thing is*/true) { // Test for game over.
                //ddroid score += SCORE_INCREMENT;
                if (/* Win State is */false) {
                    gameOver = true;
                    return;
                } else {
                	// update game state
                }

                if (score % 100 == 0 && tick - TICK_DECREMENT > 0) {
                    tick -= TICK_DECREMENT;
                }
            }
        }
    }
}
