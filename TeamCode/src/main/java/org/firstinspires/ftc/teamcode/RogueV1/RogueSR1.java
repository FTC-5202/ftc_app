package org.firstinspires.ftc.teamcode.RogueV1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Created by samue on 11/18/2016.
 */
@Autonomous(name="Rogue: Red Snot Rocket", group="RogueAuto")
@Disabled
public class RogueSR1 extends RogueAutoMethods {

    public RogueSR1(){
        color = "RED";
    }

    public void runOpMode() throws InterruptedException{
        setupAll();
        waitForStart();
        gyroCalibrate();
        //lightCalibrate(r.lightMiddle);
        eTurnBot(50, Direction.LEFT, 0.9, 0.0);
        sleep(200);
        moveBot(2, FORWARD, 0.5);
        adaRGBCalibrate();
        mrCalibrate();
        r.moveServo(r.hood, r.HOOD_UP);
        sleep(300);
        fireTwo();
        sleep(300);
        r.moveServo(r.hood, r.HOOD_DOWN);
        sleep(300);
        while(opModeIsActive()){

        }
/*
        easeMoveBot(44, BACKWARD, 0.8);
        sleep(600);
        moveBot(8, BACKWARD, 0.5);*/
    }
}
