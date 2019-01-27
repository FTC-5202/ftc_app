package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name="Crab IMU Test")
public class CrabIMU_Test extends UltimumStella_AutoMethods {

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

            if (stop == false) {
                imuTurn(45, 0.4);
                sleep(5000);
                imuTurn(45, -0.4);
                stop = true;
            }

        }
    }


}
