package com.snakegame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Audio {

    private Clip clip;

    public void playSound(String fileName){
        try{
            File soundName = new File(fileName);
            if(soundName.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundName);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } else {
                System.out.println("The file doesn't exist.");
            }
        } catch (Exception error){
            System.out.println(error.getMessage());
        }
    }
}
