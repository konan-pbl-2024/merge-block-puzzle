package com.example.merge_block_puzzle;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {

    private static SoundPool soundPool;
    private static int LetGoSound;

    public SoundPlayer(Context context) {

        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        LetGoSound = soundPool.load(context,R.raw.letgo,1);
    }

    public void playLetGoSound(){
        soundPool.play(LetGoSound,1.0f,1.0f, 1, 0, 1.0f);
    }
}
