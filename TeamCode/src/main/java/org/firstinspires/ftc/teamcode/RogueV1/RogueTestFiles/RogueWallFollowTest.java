package org.firstinspires.ftc.teamcode.RogueV1.RogueTestFiles;

import android.os.SystemClock;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

import org.firstinspires.ftc.teamcode.RogueV1.RogueAutoMethods;

/**
 * Created by samue on 12/21/2016.
 */
@Autonomous(name="WallFollowTest", group="RogueAuto")
@Disabled
public class RogueWallFollowTest extends RogueAutoMethods {

    final double MIDPOINT = 30.0;
    final double SENSITIVITY = 4;
    final double SPEED = 0.4;

    public void runOpMode() throws InterruptedException {
        long currentTime = SystemClock.elapsedRealtime();
        long startTime;
        setupAll();
        UltrasonicSensor sideSonar;
        sideSonar = hardwareMap.ultrasonicSensor.get("sideSonar");
        waitForStart();
        startTime = SystemClock.elapsedRealtime();
        currentTime = SystemClock.elapsedRealtime();
        for(;(currentTime - startTime) < 500; currentTime = SystemClock.elapsedRealtime()){
            sideSonar.getUltrasonicLevel();
        }

        double sonarReading = MIDPOINT;

        for(startTime = SystemClock.elapsedRealtime(), currentTime = SystemClock.elapsedRealtime(); (currentTime - startTime) < 2500; currentTime = SystemClock.elapsedRealtime()){
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
            /*
            double delta = ((sonarReading - MIDPOINT) / 100.0) * SENSITIVITY;
            int sgn = (delta >= 0.0) ? 1 : -1;
            if(Math.abs(delta) >= (1.0 - SPEED)){
                delta = (1.0 - SPEED) * sgn;
            }*/
            r.moveDrivetrain(SPEED + delta, SPEED - delta);
            telemetry.addData("DELTA: ", delta);
            telemetry.addData("SonarReading: ", sonarReading);
            updateTelemetry(telemetry);

        }
        r.stopDrivetrain();

        while(opModeIsActive()){
            double newsonarReading = sideSonar.getUltrasonicLevel();
            if(newsonarReading > 0.0 && newsonarReading < 255){
                sonarReading = newsonarReading;
            }
            double delta;
            if(sonarReading > (3 + MIDPOINT) || sonarReading < (MIDPOINT - 3)){
                delta = (sonarReading - MIDPOINT) * 0.01 * SENSITIVITY;
                delta = (delta > 0.35) ? 0.35 : delta;
                delta = (delta < -0.35) ? -0.35 : delta;
            }
            else{
                delta = 0;
            }
            /*
            double newsonarReading = sideSonar.getUltrasonicLevel();
            if(newsonarReading > 0.0 && newsonarReading < 255){
                sonarReading = newsonarReading;
            }
            double delta = ((sonarReading - MIDPOINT) / 100.0) * SENSITIVITY;
            int sgn = (delta >= 0.0) ? 1 : -1;
            if(Math.abs(delta) >= (1.0 - SPEED)){
                delta = (1.0 - SPEED) * sgn;
            }*/
            telemetry.addData("DELTA: ", delta);
            telemetry.addData("SonarReading: ", newsonarReading);
            updateTelemetry(telemetry);
        }
    }
    public double power(double val){
        int sgn = (val >= 0) ? 1 : -1;
        double holder = Math.pow(val, 2.0) * sgn;
        return holder;
    }
}
