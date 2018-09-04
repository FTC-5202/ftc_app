package org.firstinspires.ftc.teamcode.RogueV1;

import android.os.SystemClock;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Created by samue on 11/14/2016.
 */
@Autonomous(name="RogueGyroTest", group="RogueAuto")
@Disabled
public class RogueGyroTest extends RogueAutoMethods {

    public RogueGyroTest(){
        //Constuction
    }

    public void runOpMode() throws InterruptedException{
        setupAll();
        gyroCalibrate();
        telemetry.addData("GYRO OFFSET: ", gyro_info[OFFSET]);
        updateTelemetry(telemetry);
        waitForStart();
        long timeLast= SystemClock.elapsedRealtimeNanos();
        long timeNow = SystemClock.elapsedRealtimeNanos();
        final double MILLIS = 0.001;
        double degreesTurned = 0;
        while(opModeIsActive()){
            timeNow = SystemClock.elapsedRealtime();
            double gyroReading = r.gyro.rawZ() -gyro_info[OFFSET];
            if(gyroReading > gyro_info[MAX] || gyroReading < gyro_info[MIN]){
                degreesTurned += (gyroReading) * ((timeNow - timeLast) * 0.000000001);
            }
            telemetry.addData("gyro degrees: ", degreesTurned);
            telemetry.addData("GYRO RAW: ", r.gyro.rawZ());
            telemetry.addData("GYRO OFFSET: ", gyro_info[OFFSET]);
            telemetry.addData("MAX, MIN: ", gyro_info[MAX] + ", ", gyro_info[MIN]);
            updateTelemetry(telemetry);
            timeLast = timeNow;
            }

    }

}
