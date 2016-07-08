package com.tyz.androidgames.pyramid;

import java.util.List;

import com.tyz.androidgames.framework.Game;
import com.tyz.androidgames.framework.Graphics;
import com.tyz.androidgames.framework.Input.TouchEvent;
import com.tyz.androidgames.framework.Screen;

public class AboutScreen extends Screen {      
    public AboutScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if (event.x < 64 && event.y > 416) {
                    game.setScreen(new MainMenuScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();      
        g.drawPixmap(Assets.about, 0, 0);
        g.drawPixmap(Assets.buttons, 0, 416, 64, 64, 64, 64);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}