package com.kuba.controller;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
import java.util.ArrayList;

public class Son {
    private Clip clip;
    private float previousVolume=0;
    private float currentVolume=0;
    private FloatControl fc;
    private boolean mute=false;
    private boolean isPlaying;
    ArrayList<URL> sounds = new ArrayList<>();

    public Son() {
        try{
            sounds.add(getClass().getResource("/resources/audio/game.wav"));
            sounds.add(getClass().getResource("/resources/audio/move.wav"));
            sounds.add(getClass().getResource("/resources/audio/fail.wav"));
        }
        catch(Exception e){
            System.out.println(" Error on loading sounds ");
        }
    }

    public void setSound(int i) {
        URL soundUrl = sounds.get(i);
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc=(FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception e) {
            System.out.println(" Error on setting sound ");
            e.printStackTrace();
        }
        
    }

    private void play() {
        clip.start();
    }

    private void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    private void stop() {
        clip.stop();
    }

    public void playMusic(int i) {
        setSound(i);
        play();
        loop();
        isPlaying = true;
    }

    public void playSoundEffect(int i) {
        boolean muteEffect = false;
        if(!muteEffect){
            setSound(i);
            play();
        }
    }

    public void stopMusic() {
        stop();
        isPlaying = false;
        mute=true;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void volumeMute(){
        if(this.clip==null){
            this.setSound(0);
        }
        if(!mute){  
            previousVolume=currentVolume;
            currentVolume= 6.0f;
            fc.setValue(currentVolume);
            mute=true;
        }
        else{
            currentVolume=previousVolume;
            fc.setValue(currentVolume);
            mute=false;
        }
    }
    
}
