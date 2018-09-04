package org.firstinspires.ftc.teamcode.RogueV1;

import android.os.SystemClock;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.hardware.VoltageSensor;

/**
 * Created by samue on 12/21/2016.
 */
@Autonomous(name="RogueSB2_WFTest", group="RogueAuto")
@Disabled
public class RogueSB2_WFTest extends RogueAutoMethods {
    public RogueSB2_WFTest() {
        color = "BLUE";
    }


    public void runOpMode() throws InterruptedException {
        setupAll();
        UltrasonicSensor sideSonar;
        sideSonar = hardwareMap.ultrasonicSensor.get("sideSonar");
        telemetry.addData("STATUS: ", "Hey look boi u got mail");
        telemetry.update();
        waitForStart();
        gyroCalibrate();
        r.lightMiddle.calibrate();
        r.lightBack.calibrate();
        r.lightRight.calibrate();
        mrCalibrate();
        adaRGBCalibrate();
        easeMoveBot(43.9, FORWARD, 0.9); //was 46.9
        telemetry.addData("STATUS: ", "Ur Mom");
        telemetry.update();
        sleep(300);
        eCoastSpinBot(37.3, Direction.LEFT, 0.8);
        sleep(300);

        r.lightMiddle.wakeUp();
        r.lightBack.wakeUp();
        goUntilLine(r.lightMiddle, 0.25, 0.25);
        sleep(300);
        goUntilLine(r.lightMiddle, -0.2, -0.2);
        sleep(350);
        if(!r.lightMiddle.isOnLine()){
            goUntilLine(r.lightMiddle, 0.18, 0.18);
        }
        sleep(350);


        goUntilLine(r.lightBack, 0.63, -0.63);
        sleep(300);
        goUntilLine(r.lightBack, -0.58, 0.58);
        sleep(350);
        if(!r.lightBack.isOnLine()){
            goUntilLine(r.lightBack, 0.55, -0.55, (RogueLight.WHITE_THRESHOLD * 0.6));
            sleep(200);
        }

        runSonar();



        r.moveDrivetrain(0.3, 0.3);
        double sonarReading;
        boolean isSeen = false;
        int goodTracker = 0;
        int badTracker = 0;
        for(sonarReading = r.sonar.getUltrasonicLevel(); !isSeen; sonarReading = r.sonar.getUltrasonicLevel()){
            //telemetry.addData("sonarReading: ", sonarReading);
            if((sonarReading >= 1.0 && sonarReading <= 14.0)){
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

        eSpinBot(70, Direction.LEFT, 0.7);
        sleep(200);
        moveBot(4, BACKWARD, 0.4);
        sleep(400);
        r.lightMiddle.wakeUp();
        goUntilLine(r.lightMiddle, 0.2, 0.2);
        sleep(400);
        if(!r.lightMiddle.isOnLine()){
            goUntilLine(r.lightMiddle, -0.16, -0.16, (RogueLight.WHITE_THRESHOLD * 0.6));
            sleep(300);
        }


        r.lightRight.wakeUp();
        goUntilLine(r.lightRight, -0.7, 0.7);
        sleep(400);
        if(!r.lightRight.isOnLine()) {
            goUntilLine(r.lightRight, 0.6, -0.6, (RogueLight.WHITE_THRESHOLD * 0.6));
            sleep(200);
        }
        r.lightMiddle.wakeUp();



        final double MIDPOINT = 30.0;
        double SENSITIVITY = 4;
        double SPEED = 0.37;

        sonarReading = MIDPOINT;
        long startTime = SystemClock.elapsedRealtime();
        long currentTime = SystemClock.elapsedRealtime();
        for(;(currentTime - startTime) < 500; currentTime = SystemClock.elapsedRealtime()){
            sideSonar.getUltrasonicLevel();
        }

        for(startTime = SystemClock.elapsedRealtime(), currentTime = SystemClock.elapsedRealtime(); (currentTime - startTime) < 1000; currentTime = SystemClock.elapsedRealtime()){
            double newsonarReading = sideSonar.getUltrasonicLevel();
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
            telemetry.addData("DELTA: ", delta);
            telemetry.addData("SonarReading: ", sonarReading);
            updateTelemetry(telemetry);

        }

        SPEED = 0.25;
        SENSITIVITY = 2;
        for(double lightVal = r.lightMiddle.getReading(); lightVal < (RogueLight.WHITE_THRESHOLD * 0.5);){
            double newsonarReading = sideSonar.getUltrasonicLevel();
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

            telemetry.addData("DELTA: ", delta);
            telemetry.addData("SonarReading: ", sonarReading);
            updateTelemetry(telemetry);

        }
        r.stopDrivetrain();


        //PART 2\\
        sleep(300);
        goUntilLine(r.lightMiddle, -0.2, -0.2);
        sleep(350);
        if(!r.lightMiddle.isOnLine()){
            goUntilLine(r.lightMiddle, 0.16, 0.16);
        }
        sleep(350);

        goUntilLine(r.lightBack, 0.63, -0.63);
        sleep(300);
        goUntilLine(r.lightBack, -0.58, 0.58);
        sleep(350);
        if(!r.lightBack.isOnLine()){
            goUntilLine(r.lightBack, 0.55, -0.55, (RogueLight.WHITE_THRESHOLD * 0.6));
            sleep(200);
        }
        runSonar();

        r.moveDrivetrain(0.3, 0.3);
        isSeen = false;
        goodTracker = 0;
        badTracker = 0;
        for(sonarReading = r.sonar.getUltrasonicLevel(); !isSeen; sonarReading = r.sonar.getUltrasonicLevel()){
            //telemetry.addData("sonarReading: ", sonarReading);
            if((sonarReading >= 1.0 && sonarReading <= 14.0)){
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

        eSpinBot(40, Direction.LEFT, 0.9);
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
