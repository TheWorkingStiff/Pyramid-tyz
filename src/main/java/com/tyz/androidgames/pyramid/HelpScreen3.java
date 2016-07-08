package com.tyz.androidgames.pyramid;

import java.util.List;

import com.tyz.androidgames.framework.Game;
import com.tyz.androidgames.framework.Graphics;
import com.tyz.androidgames.framework.Screen;
import com.tyz.androidgames.framework.Input.TouchEvent;

public class HelpScreen3 extends Screen {
    
    public HelpScreen3(Game game) {
        super(game);
    }
    
    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        Graphics g = game.getGraphics();        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x > 256 && event.y > 416 ) {
                    game.setScreen(new MainMenuScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
            }
            if(event.x < 64 && event.y > g.getHeight() - 64 ) {
                game.setScreen(new HelpScreen2(game));
                if(Settings.soundEnabled)
                    Assets.click.play(1);
                return;
            }  
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();      
        g.drawPixmap(Assets.help3, 0,0);
        g.drawPixmap(Assets.buttons, 256, 416, 0, 128, 64, 64);
        g.drawPixmap(Assets.buttons, 0, 416, 64, 64, 64, 64);        
        
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
