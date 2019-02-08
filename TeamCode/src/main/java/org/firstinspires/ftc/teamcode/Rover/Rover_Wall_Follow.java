package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

@Autonomous (name="Rover Wall Follow")
@Disabled
public class Rover_Wall_Follow extends UltimumStella_AutoMethods {

    @Override
    public void runOpMode() {

        setupAll();


        // Set up the parameters with which we will use our IMU. Note that integration
        // algorithm here just reports accelerations to the logcat log; it doesn't actually
        // provide positional information.
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        //r.imu = hardwareMap.get(BNO055IMU.class, "imu");
        long startTime = System.currentTimeMillis();

        r.imu.initialize(parameters);
        long currentTime = System.currentTimeMillis();
        telemetry.addLine("Time taken to initialize: " + (currentTime - startTime));
        telemetry.update();

        sleep(2000);

        telemetry.addLine("Initializing...");
        telemetry.update();

        ElapsedTime period = new ElapsedTime();
        long start = 0;
// ...
        long current = System.currentTimeMillis();
        long timeElapsed = 0;

        double MotorPow = 1.0;
        double LiftPow = 1.0;
        double LifPos = 0.45;

        int startPos;
        int currentPos;

        boolean landed = false;
        boolean moveUP = false;
        boolean TFtrue = false;
        boolean encoderSET = false;

        telemetry.addData(">", "Press Play to start tracking");
        telemetry.addData("DistanceToWall", (r.FRDistance.getDistance(DistanceUnit.INCH)));
        telemetry.update();


        waitForStart();

            r.FLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //r.FRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //r.BLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //r.BRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            r.FLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //r.FRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //r.BLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //r.BRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            while (opModeIsActive()) {

                //attempting to follow wall while robot is moving backwards
                //sensor is on the FL corner, which is on the wall-side currently
                /*while (r.FLMotor.getCurrentPosition() < 2000) { //probably wrong distance
                    if (r.FRDistance.getDistance(DistanceUnit.INCH) > 5) {

                        telemetry.addData("DistanceToWall", (r.FRDistance.getDistance(DistanceUnit.INCH)));
                        telemetry.update();

                        r.FLMotor.setPower(0.2 + (r.FRDistance.getDistance(DistanceUnit.INCH) / 300)); //no idea if this will work
                        r.FRMotor.setPower(0.2 - (r.FRDistance.getDistance(DistanceUnit.INCH) / 300));
                        r.BLMotor.setPower(0.2 + (r.FRDistance.getDistance(DistanceUnit.INCH) / 300));
                        r.BRMotor.setPower(0.2 - (r.FRDistance.getDistance(DistanceUnit.INCH) / 300));
                    } else if (r.FRDistance.getDistance(DistanceUnit.INCH) < 3) {
                        telemetry.addData("DistanceToWall", (r.FRDistance.getDistance(DistanceUnit.INCH)));
                        telemetry.update();
                        r.FLMotor.setPower(0.2 - (r.FRDistance.getDistance(DistanceUnit.INCH) / 300));
                        r.FRMotor.setPower(0.2 + (r.FRDistance.getDistance(DistanceUnit.INCH) / 300));
                        r.BLMotor.setPower(0.2 - (r.FRDistance.getDistance(DistanceUnit.INCH) / 300));
                        r.BRMotor.setPower(0.2 + (r.FRDistance.getDistance(DistanceUnit.INCH) / 300));
                    } else {
                        r.FLMotor.setPower(0.2);
                        r.FRMotor.setPower(0.2);
                        r.BLMotor.setPower(0.2);
                        r.BRMotor.setPower(0.2);

                    }

                   // r.FLMotor.getCurrentPosition();
                }
                */

                while (r.FLMotor.getCurrentPosition() < 2000) { //probably wrong distance
                    if ((r.FRDistance.getDistance(DistanceUnit.INCH) > 5) && (r.BRDistance.getDistance(DistanceUnit.INCH) > 5)) {

                        //telemetry.addData("DistanceToWall", (r.FRDistance.getDistance(DistanceUnit.INCH)));
                        //telemetry.update();

                        moveBotcrab(1, RIGHT1, 0.5);

                    } else if ((r.FRDistance.getDistance(DistanceUnit.INCH) < 3)&& (r.BRDistance.getDistance(DistanceUnit.INCH) <3)) {
                        //telemetry.addData("DistanceToWall", (r.FRDistance.getDistance(DistanceUnit.INCH)));
                        //telemetry.update();

                        moveBotcrab(1, LEFT1, 0.5);

                    } else {
                        r.FLMotor.setPower(0.2);
                        r.FRMotor.setPower(0.2);
                        r.BLMotor.setPower(0.2);
                        r.BRMotor.setPower(0.2);

                    }

                    // r.FLMotor.getCurrentPosition();
                }

               /* while (r.FLMotor.getCurrentPosition() < 1500) { //probably wrong distance
                    if (r.FRDistance.getDistance(DistanceUnit.INCH) > 5) {

                        telemetry.addData("DistanceToWall", (r.FRDistance.getDistance(DistanceUnit.INCH)));
                        telemetry.update();

                        moveBotcrab(1, LEFT1, 0.6);

                    } else if (r.FRDistance.getDistance(DistanceUnit.INCH) < 3) {
                        telemetry.addData("DistanceToWall", (r.FRDistance.getDistance(DistanceUnit.INCH)));
                        telemetry.update();

                        moveBotcrab(1, RIGHT1, 0.6);


                    } else {
                        r.FLMotor.setPower(0.6);
                        r.FRMotor.setPower(0.6);
                        r.BLMotor.setPower(0.6);
                        r.BRMotor.setPower(0.6);

                    }

                    // r.FLMotor.getCurrentPosition();
                }
                */
                r.stopDrivetrain();

                TFtrue = true;
            }


        }

    }
