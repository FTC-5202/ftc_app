package org.firstinspires.ftc.teamcode.RogueV1;

import android.os.SystemClock;
import android.support.annotation.AttrRes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Created by samue on 11/17/2016.
 */
@Autonomous(name="RogueSB1", group="RogueAuto")
@Disabled
public class RogueSB1 extends RogueAutoMethods {
    public RogueSB1(){
        color = "BLUE";
    }
    long currentTime, startTime;


    public void runOpMode() throws InterruptedException{

        setupAll();
        waitForStart();
        gyroCalibrate();
        //lightCalibrate(r.lightMiddle);
        turnBot(21, Turn.LEFT, 0.0, -0.7);
        adaRGBCalibrate();
        mrCalibrate();
        sleep(300);
        fireTwo();
        sleep(300);

        easeMoveBot(42, BACKWARD, 0.8);
        sleep(600);
        moveBot(8, BACKWARD, 0.5);
/*
        sleep(1000);
        spinBot(93, RIGHT, 0.45); // was 89
        sleep(200);
        easeMoveBot(45, FORWARD, 0.6);
        sleep(300);
        spinBot(43.8, RIGHT, 0.45);
        sleep(300);

        runLight();
        r.moveDrivetrain(0.25, 0.25);
        for(double reading = getLightReading(); reading < (WHITE_THRESHOLD);){
            telemetry.addData("lightValue: ", reading);
            updateTelemetry(telemetry);
            reading = getLightReading();
        }
        r.stopDrivetrain();
        sleep(300);
        r.moveDrivetrain(-0.15, -0.15);
        for(double reading = getLightReading(); reading < (WHITE_THRESHOLD);){
            telemetry.addData("lightValue: ", reading);
            updateTelemetry(telemetry);
            reading = getLightReading();
        }
        r.stopDrivetrain();
        sleep(200);

        spinBot(47.3, LEFT, 0.4);
        sleep(300);
        runSonar();
        startTime = SystemClock.elapsedRealtime();
        currentTime = SystemClock.elapsedRealtime();
        r.moveDrivetrain(0.24, 0.24);
        double sonarReading;
        boolean isSeen = false;
        int goodTracker = 0;
        int badTracker = 0;
        for(sonarReading = r.sonar.getUltrasonicLevel(); !isSeen && (currentTime - startTime < TOBEACON_TIMEOUT); sonarReading = r.sonar.getUltrasonicLevel(), currentTime = SystemClock.elapsedRealtime()){
            telemetry.addData("sonarReading: ", sonarReading);
            if((sonarReading >= 1.0 && sonarReading <= 12.0)){
                goodTracker++;
                if(goodTracker > 20){
                    isSeen = true;
                }
            }
            else if(goodTracker > 0){
                badTracker++;
                if(badTracker > 4){
                    goodTracker = 0;
                    badTracker = 0;
                }
            }
            else {

            }
            telemetry.addData("trackers: ", "good/ " + goodTracker + " | bad/ " + badTracker);
        }
        r.stopDrivetrain();
        sleep(600);
        String side = getSideToHit();

        moveBot(5, BACKWARD, 0.4);
        sleep(300);
        if(side.equals("LEFT")){
            telemetry.addData("LEFT", "");
            updateTelemetry(telemetry);
            leftOut();
        }
        else if(side.equals("RIGHT")){
            telemetry.addData("RIGHT", "");
            updateTelemetry(telemetry);
            rightOut();
        }
        else {
            telemetry.addData("FAIL", "");
            updateTelemetry(telemetry);
            sleep(2000);
        }
        moveBot(6, FORWARD, 0.3);
        /*
        telemetry.addData("waiting...", "");
        updateTelemetry(telemetry);
        sleep(5000);
        telemetry.addData("GOOOOO", "");
        updateTelemetry(telemetry);
        String s = getSideToHit();
        while(opModeIsActive()){
            telemetry.addData("Side: ", s);
            updateTelemetry(telemetry);
        }*/

    }
}
