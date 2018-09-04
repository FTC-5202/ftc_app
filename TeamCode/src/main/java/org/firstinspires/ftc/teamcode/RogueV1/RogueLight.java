package org.firstinspires.ftc.teamcode.RogueV1;

import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

/**
 * Created by samue on 1/2/2017.
 */
public class RogueLight {

    private OpticalDistanceSensor myLight;
    private double myLightReadingAverage;
    public final static double WHITE_THRESHOLD = 0.28;// was 0.52

    private static final double LIGHT_MAXTHRESHOLD = 0.5;

    public RogueLight(){
    }

    public void setMyLight(OpticalDistanceSensor light){
        myLight = light;
    }

    public void calibrate(){
        double average = 0;
        int nReadings = 1000;

        for(int i = 0; i < nReadings; i++){
            double reading = myLight.getLightDetected();
            average += reading;
            if(i > 0){
                average /= 2;
            }
        }

        myLightReadingAverage = average;
    }

    public double getReading(){

        double holder = myLight.getLightDetected() - myLightReadingAverage;
        if(holder < 0){
            holder = 0;
        }

        if(holder > LIGHT_MAXTHRESHOLD){
            holder = LIGHT_MAXTHRESHOLD;
        }
        holder = holder / LIGHT_MAXTHRESHOLD;
        return holder;
    }

    public void wakeUp(){
        for(int i = 0; i < 2000; i++){
            this.getReading();

        }
    }

    public boolean isOnLine(){
        double lightAve  = 0;
        for(int i = 0; i < 300; i++){
            lightAve += this.getReading();
        }
        lightAve /= 300;
        return (lightAve > (WHITE_THRESHOLD * 0.6));
    }



}
