package com.kuba.controller;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
import java.util.ArrayList;

public class Son {

    private Clip clip;
    private final ArrayList<URL> sounds = new ArrayList<>();
    private boolean isPlaying;

    public Son() {
        try{
            sounds.add(getClass().getResource("/resources/audio/game.wav"));
            sounds.add(getClass().getResource("/resources/audio/move2.wav"));
            sounds.add(getClass().getResource("/resources/audio/fail.wav"));
        }
        catch(Exception e){
            System.out.println(" Error on loading sounds ");
            //e.printStackTrace();
        }
    }

    public void setSound(int i) {
        URL soundUrl = sounds.get(i);
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl);
            clip = AudioSystem.getClip();
            clip.open(ais);
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
        setSound(i);
        play();
    }

    public void stopMusic() {
        stop();
        isPlaying = false;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
    
}
