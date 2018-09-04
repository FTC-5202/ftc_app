package org.firstinspires.ftc.teamcode.RogueV1;

import android.os.SystemClock;

import com.qualcomm.hardware.hitechnic.HiTechnicNxtGyroSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by samue on 11/14/2016.
 */
@Autonomous(name="RogueGyro", group="Rogue")
@Disabled
public class RogueGyro extends RogueAutoMethods {

    public RogueGyro(){
        //Constuction
    }

    public void runOpMode() throws InterruptedException{
        /*
        setupMotors();
        setupServos();
        r.gyro = hardwareMap.gyroSensor.get("gyro");
        */
        setupAll();
        gyroCalibrate();
        r.gyro.calibrate();
        while(r.gyro.isCalibrating()){

        }
        r.gyro.resetZAxisIntegrator();
        updateTelemetry(telemetry);
        telemetry.addData("Status:", "Calibrated and ready to go");
        updateTelemetry(telemetry);
        waitForStart();
        telemetry.addData("STARTING:", 0);
        updateTelemetry(telemetry);
        sleep(2000);

/*
        long timeLast= SystemClock.elapsedRealtime();
        long timeNow = SystemClock.elapsedRealtime();
        final double MILLIS = 0.001;
        double degreesTurned = 0;
        while(opModeIsActive()){
            timeNow = SystemClock.elapsedRealtime();
            double gyroReading = ((HiTechnicNxtGyroSensor)r.gyro).readRawVoltage() -gyro_info[OFFSET];
            if(gyroReading > gyro_info[MAX] || gyroReading < gyro_info[MIN]){
                degreesTurned += (gyroReading) * ((timeNow - timeLast) * MILLIS);
            }
            telemetry.addData("gyro degrees: ", degreesTurned);
            telemetry.addData("GYRO RAW: ", ((HiTechnicNxtGyroSensor)r.gyro).readRawVoltage());
            telemetry.addData("GYRO OFFSET: ", gyro_info[OFFSET]);
            telemetry.addData("MAX, MIN: ", gyro_info[MAX] + ", ", gyro_info[MIN]);
            updateTelemetry(telemetry);
            timeLast = timeNow;
        }*/


        spinBot(90, Turn.LEFT, 0.8);
        telemetry.addData("Turn finished", "");
        updateTelemetry(telemetry);
        sleep(1000);




       // sleep(4000);
        //spinBot(90, RIGHT, 0.8);


        long timeLast= SystemClock.elapsedRealtime();
        long timeNow = SystemClock.elapsedRealtime();

        final double MILLIS = 0.001;
        double degreesTurned = 0;
        while(opModeIsActive()){
            timeNow = SystemClock.elapsedRealtime();
            double gyroReading = r.gyro.rawZ() -gyro_info[OFFSET];
            if(gyroReading > (gyro_info[OFFSET] + 300) || gyroReading < (gyro_info[OFFSET]-300)){
                degreesTurned += ((gyroReading) * ((timeNow - timeLast) * MILLIS) * (Math.PI/180));

            }
            telemetry.addData("gyro degrees: ", degreesTurned);
            telemetry.addData("GYRO RAW: ", r.gyro.rawZ());
            telemetry.addData("GYRO OFFSET: ", gyro_info[OFFSET]);
            telemetry.addData("MAX, MIN: ", gyro_info[MAX] + ", " + gyro_info[MIN]);
            telemetry.addData("Integrated Z: ", ((ModernRoboticsI2cGyro) r.gyro).getIntegratedZValue());
            telemetry.addData("Heading: ", r.gyro.getHeading());
            updateTelemetry(telemetry);
            timeLast = timeNow;
        }

    }

}
