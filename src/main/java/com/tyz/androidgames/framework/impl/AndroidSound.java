package com.tyz.androidgames.framework.impl;

import android.media.SoundPool;

import com.tyz.androidgames.framework.Sound;

public class AndroidSound implements Sound {
    int soundId;
    SoundPool soundPool;

    public AndroidSound(SoundPool soundPool, int soundId) {
        this.soundId = soundId;
        this.soundPool = soundPool;
    }

    @Override
    public void play(float volume) {
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    //ddroid added
    public void stop() {
        soundPool.stop(soundId);
    }
    
    @Override
    public void dispose() {
        soundPool.unload(soundId);
    }

}
