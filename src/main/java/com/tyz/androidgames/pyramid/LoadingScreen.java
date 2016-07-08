package com.tyz.androidgames.pyramid;

import com.tyz.androidgames.framework.Game;
import com.tyz.androidgames.framework.Graphics;
import com.tyz.androidgames.framework.Pixmap;
import com.tyz.androidgames.framework.Screen;
import com.tyz.androidgames.framework.Graphics.PixmapFormat;

public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
        super(game);
    }
    
    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.background = g.newPixmap("background.png", PixmapFormat.RGB565);
        Assets.background_2 = g.newPixmap("background_2.png", PixmapFormat.RGB565);
        Assets.background_3 = g.newPixmap("background_3.png", PixmapFormat.RGB565);
        Assets.background_4 = g.newPixmap("background_4.png", PixmapFormat.RGB565);
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444);
        Assets.mainMenu = g.newPixmap("mainmenu.png", PixmapFormat.ARGB4444);
        Assets.buttons = g.newPixmap("buttons.png", PixmapFormat.ARGB4444);
        //Assets.gems = g.newPixmap("gems.png", PixmapFormat.ARGB4444);
        Assets.gemsTriHollow = g.newPixmap("tri_hollow.png", PixmapFormat.ARGB4444);
        Assets.gemsTri2d = g.newPixmap("tri_2d.png", PixmapFormat.ARGB4444);
        Assets.gemsTri3d = g.newPixmap("tri_3d.png", PixmapFormat.ARGB4444);
        Assets.selectionFrame = g.newPixmap("selection_frame.png", PixmapFormat.ARGB4444);
        Assets.usedFrame = g.newPixmap("used_frame.png", PixmapFormat.ARGB4444);
        Assets.about = g.newPixmap("about.png", PixmapFormat.ARGB4444);
        Assets.help1 = g.newPixmap("help1.png", PixmapFormat.ARGB4444);
        Assets.help2 = g.newPixmap("help2.png", PixmapFormat.ARGB4444);
        Assets.help3 = g.newPixmap("help3.png", PixmapFormat.ARGB4444);
        Assets.numbers = g.newPixmap("numbers.png", PixmapFormat.ARGB4444);
        Assets.numbers_w = g.newPixmap("numbers_w.png", PixmapFormat.ARGB4444);
        Assets.ready = g.newPixmap("ready.png", PixmapFormat.ARGB4444);
        Assets.pause = g.newPixmap("pausemenu.png", PixmapFormat.ARGB4444);
        Assets.gameOver = g.newPixmap("gameover.png", PixmapFormat.ARGB4444);
        //Assets.spotted = g.newPixmap("spotted.png", PixmapFormat.ARGB4444);
        Assets.chooseButtons = g.newPixmap("chooseButtons.png", PixmapFormat.ARGB4444);
        Assets.pyramid = g.newPixmap("pyramid.png", PixmapFormat.ARGB4444);
        Assets.click = game.getAudio().newSound("tick.ogg");
        Assets.tick = game.getAudio().newSound("tick.ogg");
        Assets.warning = game.getAudio().newSound("warning.ogg");
        Assets.detectpossible = game.getAudio().newSound("detectpossible.ogg");
        Assets.choose = game.getAudio().newSound("choose.ogg");
        Assets.goodcombo = game.getAudio().newSound("goodcombo.ogg");
        Assets.badcombo = game.getAudio().newSound("badcombo.ogg");
        Settings.load(game.getFileIO()); 
        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void present(float deltaTime) {

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