package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name="CrabTest")
public class CrabTest extends UltimumStella_AutoMethods {

    @Override
    public void runOpMode () {

        setupAll();

        boolean stop = false;

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        r.imu.initialize(parameters);



        waitForStart();

        while (opModeIsActive()) {

            moveBotcrab(24, RIGHT1, 0.5);
            sleep (3000);

            moveBotcrab(24, LEFT1, 0.5);
            sleep (3000);

        }
    }


}
