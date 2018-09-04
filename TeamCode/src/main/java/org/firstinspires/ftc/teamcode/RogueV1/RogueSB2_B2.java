package org.firstinspires.ftc.teamcode.RogueV1;

import android.os.SystemClock;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Engagable;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Created by samue on 12/21/2016.
 */
@Autonomous(name="RogueSB2_B2", group="RogueAuto")
@Disabled
public class RogueSB2_B2 extends RogueAutoMethods {
    public RogueSB2_B2() {
        color = "BLUE";
    }


    public void runOpMode() throws InterruptedException {
        setupAll();
        telemetry.addData("STATUS: ", "Make sure blue sonar is in S4 on Legacy");
        telemetry.update();
        waitForStart();
        gyroCalibrate();
        r.lightMiddle.calibrate();
        r.lightBack.calibrate();
        r.lightRight.calibrate();
        mrCalibrate();
        adaRGBCalibrate();
        /*
        easeMoveBot(43.9, FORWARD, 0.9); //was 46.9
        telemetry.addData("STATUS: ", "Ur Mom");
        telemetry.update();
        sleep(300);
        eCoastSpinBot(37.3, Direction.LEFT, 0.8);
        sleep(300);*/
        r.lightMiddle.wakeUp();
        r.lightBack.wakeUp();
        moveBot(25, FORWARD, 0.8, EndStatus.COAST);
        r.lightMiddle.wakeUp();
        goUntilLine(r.lightMiddle, 0.25, 0.4, 2100);
        sleep(300);
        goUntilLine(r.lightMiddle, -0.28, -0.28);
        sleep(350);
        if(!r.lightMiddle.isOnLine()){
            goUntilLine(r.lightMiddle, 0.23, 0.23, (RogueLight.WHITE_THRESHOLD * 0.6));
        }
        sleep(350);


        int disol = 1;
        eSpinBot(40, Direction.RIGHT, 0.63, EndStatus.COAST);
        goUntilLine(r.lightBack, 0.5, -0.5);
        sleep(300);
        goUntilLine(r.lightBack, -0.45, 0.45);
        sleep(350);
        if(!r.lightBack.isOnLine()){
            goUntilLine(r.lightBack, 0.37, -0.37, (RogueLight.WHITE_THRESHOLD * 0.6));
            disol = -1;
            sleep(200);
        }


        runSonar();



        r.moveDrivetrain(0.25, 0.25);
        double sonarReading;
        boolean isSeen = false;
        int goodTracker = 0;
        int badTracker = 0;
        final double BASE = 0.31;
        double COEFF = 0.24 * disol;
        for(sonarReading = r.sonar.getUltrasonicLevel(), isSeen = false, goodTracker = 0, badTracker = 0; !isSeen; sonarReading = r.sonar.getUltrasonicLevel()){
            //telemetry.addData("sonarReading: ", sonarReading);
            if((sonarReading >= 1.0 && sonarReading <= 19.0)){
                goodTracker++;
                if(goodTracker > 5){
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
            double lightVal = (r.lightBack.getReading() - 0.5);
            lightVal *= COEFF;
            r.moveDrivetrain((BASE - lightVal), (BASE + lightVal));
            //telemetry.addData("trackers: ", "good/ " + goodTracker + " | bad/ " + badTracker);
        }
        r.stopDrivetrain();
        telemetry.addData("STATUS: ", "SAW WALL");
        updateTelemetry(telemetry);
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
            sleep(800); //was 800

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
            sleep(800); //was 800
        }
        else {

        }


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

        eSpinBot(70, Direction.LEFT, 0.7);
        sleep(200);
        moveBot(2, BACKWARD, 0.4);
        sleep(200);
        r.lightMiddle.wakeUp();
        goUntilLine(r.lightMiddle, 0.27, 0.27);
        sleep(300);
        if(!r.lightMiddle.isOnLine()){
            goUntilLine(r.lightMiddle, -0.21, -0.21, (RogueLight.WHITE_THRESHOLD * 0.6));
            sleep(200);
        }


        r.lightRight.wakeUp();
        goUntilLine(r.lightRight, -0.7, 0.7);
        sleep(200);
        if(!r.lightRight.isOnLine()) {
            goUntilLine(r.lightRight, 0.6, -0.6, (RogueLight.WHITE_THRESHOLD * 0.6));
            sleep(200);
        }
        r.lightMiddle.wakeUp();



        final double MIDPOINT = 25.0;
        double SENSITIVITY = 4;
        double SPEED = 0.47; //was .37

        sonarReading = MIDPOINT;
        long startTime = SystemClock.elapsedRealtime();
        long currentTime = SystemClock.elapsedRealtime();
        for(;(currentTime - startTime) < 200; currentTime = SystemClock.elapsedRealtime()){
            r.sideSonar.getUltrasonicLevel();
        }

        for(startTime = SystemClock.elapsedRealtime(), currentTime = SystemClock.elapsedRealtime(); (currentTime - startTime) < 700; currentTime = SystemClock.elapsedRealtime()){
            double newsonarReading = r.sideSonar.getUltrasonicLevel();
            if(newsonarReading > 0.0 && newsonarReading < 255){
                sonarReading = newsonarReading;
            }
            double delta;
            if(sonarReading > (1 + MIDPOINT) || sonarReading < (MIDPOINT - 2)){
                delta = (sonarReading - MIDPOINT) * 0.01 * SENSITIVITY;
                delta = (delta > 0.32) ? 0.32 : delta;
                delta = (delta < -0.35) ? -0.35 : delta;
            }
            else{
                delta = 0;
            }

            r.moveDrivetrain(SPEED + delta, SPEED - delta);
            //telemetry.addData("DELTA: ", delta);
            //telemetry.addData("SonarReading: ", sonarReading);
            //updateTelemetry(telemetry);

        }

        SPEED = 0.34;
        SENSITIVITY = 2;
        for(double lightVal = r.lightMiddle.getReading(); lightVal < (RogueLight.WHITE_THRESHOLD * 0.5);){
            double newsonarReading = r.sideSonar.getUltrasonicLevel();
            if(newsonarReading > 0.0 && newsonarReading < 255){
                sonarReading = newsonarReading;
            }
            double delta;
            if(sonarReading > (1 + MIDPOINT) || sonarReading < (MIDPOINT - 2)){
                delta = (sonarReading - MIDPOINT) * 0.01 * SENSITIVITY;
                delta = (delta > 0.32) ? 0.32 : delta;
                delta = (delta < -0.35) ? -0.35 : delta;
            }
            else{
                delta = 0;
            }

            r.moveDrivetrain(SPEED + delta, SPEED - delta);
            lightVal = r.lightMiddle.getReading();

            //telemetry.addData("DELTA: ", delta);
            //telemetry.addData("SonarReading: ", sonarReading);
            //updateTelemetry(telemetry);

        }
        r.stopDrivetrain();


        //PART 2\\
        sleep(300);
        goUntilLine(r.lightMiddle, -0.3, -0.3);
        sleep(350);
        if(!r.lightMiddle.isOnLine()){
            goUntilLine(r.lightMiddle, 0.25, 0.25);
            sleep(350);
        }

        adaRGBCalibrate();
        mrCalibrate();

        eSpinBot(40, Direction.RIGHT, 0.63, EndStatus.COAST);
        goUntilLine(r.lightBack, 0.5, -0.5);
        sleep(300);
        goUntilLine(r.lightBack, -0.45, 0.45);
        sleep(350);
        disol = 1;
        if(!r.lightBack.isOnLine()){
            goUntilLine(r.lightBack, 0.38, -0.38, (RogueLight.WHITE_THRESHOLD * 0.6));
            disol = -1;
            sleep(200);
        }
        runSonar();

        r.moveDrivetrain(0.27, 0.27);
        isSeen = false;
        goodTracker = 0;
        badTracker = 0;
        COEFF = Math.abs(COEFF) * disol;
        for(sonarReading = r.sonar.getUltrasonicLevel(), isSeen = false, goodTracker = 0, badTracker = 0; !isSeen; sonarReading = r.sonar.getUltrasonicLevel()){
            //telemetry.addData("sonarReading: ", sonarReading);
            if((sonarReading >= 1.0 && sonarReading <= 19.0)){
                goodTracker++;
                if(goodTracker > 5){
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
            double lightVal = (r.lightBack.getReading() - 0.5);
            lightVal *= COEFF;
            r.moveDrivetrain((BASE - lightVal), (BASE + lightVal));
            //telemetry.addData("trackers: ", "good/ " + goodTracker + " | bad/ " + badTracker);
        }
        r.stopDrivetrain();
        telemetry.addData("STATUS: ", "SAW WALL");
        updateTelemetry(telemetry);
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
        sleep(600); //was 800

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

        r.moveServo(r.lightArm, r.LIGHTARM_UP);
        eSpinBot(30, Direction.LEFT, 0.9);
        r.moveServo(r.hood, r.HOOD_UP);
        sleep(300);
        fire();
        sweep(1100);
        fire();


        while(opModeIsActive()){
            telemetry.addData("LightValMiddle: ", r.lightMiddle.getReading());
            telemetry.addData("IsMiddleOverLine?: ", r.lightMiddle.isOnLine());
            telemetry.addData("LightValFront: ", r.lightBack.getReading());
            telemetry.addData("IsFrontOverLine?: ", r.lightBack.isOnLine());
            telemetry.addData("LightValRight: ", r.lightRight.getReading());
            telemetry.addData("IsRightOverLine?: ", r.lightRight.isOnLine());
            updateTelemetry(telemetry);
        }

    }
}
