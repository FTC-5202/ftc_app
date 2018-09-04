package org.firstinspires.ftc.teamcode.RogueV1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Created by samue on 11/25/2016.
 */
@Autonomous(name="RogueSB2_B1", group="RogueAuto")
@Disabled
public class RogueSB2_B1 extends RogueAutoMethods {
    public RogueSB2_B1() {
        //constructor
        color = "BLUE";
    }

    public void runOpMode() throws InterruptedException {
        setupAll();
        waitForStart();
        gyroCalibrate();
        //lightCalibrate(r.lightBack);
        //lightCalibrate(r.lightMiddle);
        mrCalibrate();
        adaRGBCalibrate();
        easeMoveBot(45.9, FORWARD, 0.9); //was 46.9
        telemetry.addData("STATUS: ", "STOPPED");
        telemetry.update();
        sleep(300);
        eCoastSpinBot(37.3, Direction.LEFT, 0.8);
        sleep(300);
        /*runLight();
        r.moveDrivetrain(0.25, 0.25);
        for(double reading = getLightReading(r.lightMiddle); reading < (WHITE_THRESHOLD);){
            telemetry.addData("lightValue: ", reading);
            updateTelemetry(telemetry);
            reading = getLightReading(r.lightMiddle);
        }
        r.stopDrivetrain();
        sleep(300);

        r.moveDrivetrain(-0.2, -0.2);
        for(double reading = getLightReading(r.lightMiddle); reading < (WHITE_THRESHOLD);){
            telemetry.addData("lightValue: ", reading);
            updateTelemetry(telemetry);
            reading = getLightReading(r.lightMiddle);
        }
        r.stopDrivetrain();
        sleep(200);

        r.moveDrivetrain(0.63, -0.63);
        for(double reading = getLightReading(r.lightBack); reading < (WHITE_THRESHOLD);){
            telemetry.addData("lightValue: ", reading);
            updateTelemetry(telemetry);
            reading = getLightReading(r.lightBack);
        }
        r.stopDrivetrain();
        sleep(400);

        r.moveDrivetrain(-0.59, 0.59);
        for(double reading = getLightReading(r.lightBack); reading < (WHITE_THRESHOLD);){
            telemetry.addData("lightValue: ", reading);
            updateTelemetry(telemetry);
            reading = getLightReading(r.lightBack);
        }
        r.stopDrivetrain();
        sleep(200);




        runSonar();

        r.moveDrivetrain(0.3, 0.3);
        double sonarReading;
        boolean isSeen = false;
        int goodTracker = 0;
        int badTracker = 0;
        for(sonarReading = r.sonar.getUltrasonicLevel(); !isSeen; sonarReading = r.sonar.getUltrasonicLevel()){
            //telemetry.addData("sonarReading: ", sonarReading);
            if((sonarReading >= 1.0 && sonarReading <= 13.0)){
                goodTracker++;
                if(goodTracker > 14){
                    isSeen = true;
                }
            }
            else if(goodTracker > 0){
                badTracker++;
                if(badTracker > 2){
                    goodTracker = 0;
                    badTracker = 0;
                }
            }
            else {

            }
            //telemetry.addData("trackers: ", "good/ " + goodTracker + " | bad/ " + badTracker);
        }
        telemetry.addData("STATUS: ", "SAW WALL");
        updateTelemetry(telemetry);
        r.stopDrivetrain();
        sleep(600);
        String side = getSideToHit();
        moveBot(4, BACKWARD, 0.5);
        if(side.equals("LEFT")){
            leftOut();
            r.moveDrivetrain(0.33, 0.33);
            for(sonarReading = r.sonar.getUltrasonicLevel(), isSeen = false, goodTracker = 0, badTracker = 0; !isSeen; sonarReading = r.sonar.getUltrasonicLevel()){
                //telemetry.addData("sonarReading: ", sonarReading);
                if((sonarReading >= 1.0 && sonarReading <= 12.0)){
                    goodTracker++;
                    if(goodTracker > 14){
                        isSeen = true;
                    }
                }
                else if(goodTracker > 0){
                    badTracker++;
                    if(badTracker > 2){
                        goodTracker = 0;
                        badTracker = 0;
                    }
                }
                else {

                }
                //telemetry.addData("trackers: ", "good/ " + goodTracker + " | bad/ " + badTracker);
            }
            telemetry.addData("STATUS: ", "SAW WALL");
            updateTelemetry(telemetry);
            r.stopDrivetrain();


        }
        else if(side.equals("RIGHT")){
            rightOut();
            r.moveDrivetrain(0.33, 0.33);
            for(sonarReading = r.sonar.getUltrasonicLevel(), isSeen = false, goodTracker = 0, badTracker = 0; !isSeen; sonarReading = r.sonar.getUltrasonicLevel()){
                //telemetry.addData("sonarReading: ", sonarReading);
                if((sonarReading >= 1.0 && sonarReading <= 12.0)){
                    goodTracker++;
                    if(goodTracker > 14){
                        isSeen = true;
                    }
                }
                else if(goodTracker > 0){
                    badTracker++;
                    if(badTracker > 2){
                        goodTracker = 0;
                        badTracker = 0;
                    }
                }
                else {

                }
                //telemetry.addData("trackers: ", "good/ " + goodTracker + " | bad/ " + badTracker);
            }
            telemetry.addData("STATUS: ", "SAW WALL");
            updateTelemetry(telemetry);
            r.stopDrivetrain();

        }
        else {

        }
        sleep(800);

        if(side.equals("LEFT") || side.equals("RIGHT")){
            moveBot(4, BACKWARD, 0.4);
            sleep(300);
            if(side.equals("LEFT")){
                leftIn();
            }
            else {
                rightIn();
            }
        }

        //SECOND BEACON
        eCoastSpinBot(107.2, Direction.LEFT, 0.8);
        sleep(300);
        moveBot(25.4, FORWARD, 0.7); // was 24.6
        sleep(300);
        eCoastSpinBot(29.8, Direction.RIGHT, 0.8); //was 42.8
        sleep(400);

        r.moveDrivetrain(0.3, 0.3);
        for(double reading = getLightReading(r.lightMiddle); reading < (WHITE_THRESHOLD);){
            telemetry.addData("lightValue: ", reading);
            updateTelemetry(telemetry);
            reading = getLightReading(r.lightMiddle);
        }
        r.stopDrivetrain();
        sleep(300);

        moveBot(2.9, FORWARD, 0.5);
        sleep(200);
        eSpinBot(17.8, Direction.LEFT, 0.55);
        sleep(200);




        r.moveDrivetrain(-0.24, -0.24);
        for(double reading = getLightReading(r.lightMiddle); reading < (WHITE_THRESHOLD);){
            telemetry.addData("lightValue: ", reading);
            updateTelemetry(telemetry);
            reading = getLightReading(r.lightMiddle);
        }
        r.stopDrivetrain();
        sleep(200);

        eCoastSpinBot(50.8, Direction.RIGHT, 0.7);
        sleep(300);
        /*
        r.moveMotor(r.sweeper, 0.9);
        sleep(500);
        r.moveMotor(r.sweeper, 0.0);*/
        sweep(700);
        fireTwo();
/*
        r.moveDrivetrain(0.7, -0.7);
        for(double reading = getLightReading(r.lightBack); reading < (WHITE_THRESHOLD);){
            telemetry.addData("lightValue: ", reading);
            updateTelemetry(telemetry);
            reading = getLightReading(r.lightBack);
        }
        r.stopDrivetrain();
        sleep(200);*/
/*
        r.moveDrivetrain(-0.59, 0.59);
        for(double reading = getLightReading(r.lightBack); reading < (WHITE_THRESHOLD);){
            telemetry.addData("lightValue: ", reading);
            updateTelemetry(telemetry);
            reading = getLightReading(r.lightBack);
        }
        r.stopDrivetrain();
        sleep(200);*/



/*
        runSonar();

        r.moveDrivetrain(0.3, 0.3);
        isSeen = false;
        sonarReading = 0;
        goodTracker = 0;
        badTracker = 0;
        for(sonarReading = r.sonar.getUltrasonicLevel(); !isSeen; sonarReading = r.sonar.getUltrasonicLevel()){
            //telemetry.addData("sonarReading: ", sonarReading);
            if((sonarReading >= 1.0 && sonarReading <= 13.0)){
                goodTracker++;
                if(goodTracker > 14){
                    isSeen = true;
                }
            }
            else if(goodTracker > 0){
                badTracker++;
                if(badTracker > 2){
                    goodTracker = 0;
                    badTracker = 0;
                }
            }
            else {

            }
            //telemetry.addData("trackers: ", "good/ " + goodTracker + " | bad/ " + badTracker);
        }
        telemetry.addData("STATUS: ", "SAW WALL");
        updateTelemetry(telemetry);
        r.stopDrivetrain();
        sleep(600);
        side = getSideToHit();
        moveBot(4, BACKWARD, 0.5);
        if(side.equals("LEFT")){
            leftOut();
            r.moveDrivetrain(0.33, 0.33);
            for(sonarReading = r.sonar.getUltrasonicLevel(), isSeen = false, goodTracker = 0, badTracker = 0; !isSeen; sonarReading = r.sonar.getUltrasonicLevel()){
                //telemetry.addData("sonarReading: ", sonarReading);
                if((sonarReading >= 1.0 && sonarReading <= 12.0)){
                    goodTracker++;
                    if(goodTracker > 14){
                        isSeen = true;
                    }
                }
                else if(goodTracker > 0){
                    badTracker++;
                    if(badTracker > 2){
                        goodTracker = 0;
                        badTracker = 0;
                    }
                }
                else {

                }
                //telemetry.addData("trackers: ", "good/ " + goodTracker + " | bad/ " + badTracker);
            }
            telemetry.addData("STATUS: ", "SAW WALL");
            updateTelemetry(telemetry);
            r.stopDrivetrain();

        }
        else if(side.equals("RIGHT")){
            rightOut();
            r.moveDrivetrain(0.33, 0.33);
            for(sonarReading = r.sonar.getUltrasonicLevel(), isSeen = false, goodTracker = 0, badTracker = 0; !isSeen; sonarReading = r.sonar.getUltrasonicLevel()){
                //telemetry.addData("sonarReading: ", sonarReading);
                if((sonarReading >= 1.0 && sonarReading <= 12.0)){
                    goodTracker++;
                    if(goodTracker > 14){
                        isSeen = true;
                    }
                }
                else if(goodTracker > 0){
                    badTracker++;
                    if(badTracker > 2){
                        goodTracker = 0;
                        badTracker = 0;
                    }
                }
                else {

                }
                //telemetry.addData("trackers: ", "good/ " + goodTracker + " | bad/ " + badTracker);
            }
            telemetry.addData("STATUS: ", "SAW WALL");
            updateTelemetry(telemetry);
            r.stopDrivetrain();

        }
        else {

        }
        sleep(800);
        moveBot(5, BACKWARD, 0.6);
        if(side.equals("LEFT")){
            leftIn();
        }
        else if(side.equals("RIGHT")){
            rightIn();
        }
        else {
        }


        while(opModeIsActive()){
            telemetry.addData("SideToHit: ", side);
            updateTelemetry(telemetry);
        }*/
    }
}
