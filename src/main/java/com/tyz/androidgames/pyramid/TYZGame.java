package com.tyz.androidgames.pyramid;

import com.tyz.androidgames.framework.Screen;
import com.tyz.androidgames.framework.impl.AndroidGame;

// DDroid - Have to figure out what this is for.
//public class MrNomGame extends AndroidGame {
public class TYZGame extends AndroidGame {
    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this); 
    }
}