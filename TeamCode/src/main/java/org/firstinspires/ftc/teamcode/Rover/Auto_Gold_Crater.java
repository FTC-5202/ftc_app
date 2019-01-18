package org.firstinspires.ftc.teamcode.Rover;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import static org.firstinspires.ftc.teamcode.Rover.UltimumStella_AutoMethods.Direction.RIGHT;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;


//import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.

public class Auto_Gold_Crater extends UltimumStella_AutoMethods { // TEST with wall-following to crater on Blue Silver side
    @Override

    public void runOpMode() {

        setupAll();

        final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = FRONT;

        OpenGLMatrix lastLocation = null;

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
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFRuit IMU",
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


        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {


            // targetVisible = false;

            // Provide feedback as to where the robot is located (if we know).
            //robotHeading=Math.toRadians(rotation.thirdAngle);
            //robotX=translation.get(0)/mmPerInch;
            //robotY=translation.get(1)/mmPerInch;
            robotToWall = (72 - robotY - 10); //was -12/-6
            distanceToTravel = robotToWall / Math.abs(Math.cos(robotHeading - Math.PI / 2));
            telemetry.addData("distance", distanceToTravel);
            telemetry.addData("robotToWall", robotToWall);
            telemetry.addData("robotHeading", robotHeading);
            telemetry.addData("Cos", Math.cos(robotHeading));
            telemetry.update();
            //sleep(5000);

            //} else {
            //telemetry.addData("Visible Target", "none");
            //                        correction = checkDirection();
            r.FLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            r.FRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            r.FLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            r.FRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);//checkpoint


            telemetry.addData("range", String.format("%.01f in", r.sensorRange.getDistance(DistanceUnit.INCH)));
            //telemetry.addData("rangeR", String.format("%.01f in", sensorRangeR.getDistance(DistanceUnit.INCH)));
            telemetry.update();


            //this stuff needs to be inside a distance loop so the robot stops when it gets to the depot
            //turn left
            //eTurnBot(30, RIGHT, 0.3, -0.3);//change to turn using IMU when working
            //sleep(250);

            while (timeElapsed < 2000 && !isStopRequested()) {
                timeElapsed = System.currentTimeMillis() - start;

                if (tfod != null) { //we have not yet activated TFOD
                    //if (tfod !=null && timeElapsed >= ?) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.

                    /**The following is basically the TFOD sample program code
                     *  it determines where the gold mineral and the silver minerals are located
                     */
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        telemetry.update();
                        sleep(250);
                        if (updatedRecognitions.size() == 2) {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;
                            //telemetry.addData("Update Recognitions", updatedRecognitions);
                            //telemetry.update();
                            //sleep(250);
                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineralX = (int) recognition.getLeft();
                                } else if (silverMineral1X == -1) {
                                    silverMineral1X = (int) recognition.getLeft();
                                } else {
                                    silverMineral2X = (int) recognition.getLeft();
                                }
                            }

                            //telemetry.addData("gold", goldMineralX);
                            //telemetry.addData("silver 1", silverMineral1X);
                            //telemetry.addData("silver 2", silverMineral2X);
                            //telemetry.update();
                            //sleep(250);

                            /**
                             * If all the min values are being registered then we move on
                             * if the gold min val hasn't changed from above
                             * Gold Mineral Position will register as left
                             * tfod = null shows we have gone through this and don't want to go through all of these again
                             *
                             * if the gold min val is less than silver min val 1
                             * Gold Mineral Position will register as center
                             *
                             * if gold min val is greater than silver min val 1
                             * Gold Mineral Position will register as right
                             */

                            if (goldMineralX != -1 || silverMineral1X != -1) {
                                if (goldMineralX == -1) { //needs FIX
                                    position = 3; //was 1
                                    telemetry.addData("Gold Mineral Position", "Right"); //GOOD
                                } else if (goldMineralX < silverMineral1X) {
                                    //if (goldMineralX != -1 || silverMineral1X != -1) {
                                    //(goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                    position = 1;
                                    telemetry.addData("Gold Mineral Position", "Left");

                                } else {
                                    position = 2;
                                    telemetry.addData("Gold Mineral Position", "Center");
                                }
                                tfod = null;
                            }

                        }

                        telemetry.update();
                    }

                }

            }


            r.pin.setPosition(0.5);
            sleep(1000);

            startPos = r.Hang.getCurrentPosition();
            currentPos = r.Hang.getCurrentPosition();


            while (Math.abs(currentPos - startPos) < 530 && !landed) {
                r.Hang.setPower(0.5);
                currentPos = r.Hang.getCurrentPosition();


            }
            landed = true;
            r.Hang.setPower(0);

            startPos = r.Hang.getCurrentPosition();
            currentPos = r.Hang.getCurrentPosition();

            while (Math.abs(currentPos - startPos) < 30 && !moveUP) {
                r.Hang.setPower(-0.5);
                currentPos = r.Hang.getCurrentPosition();
            }
            moveUP = true;
            r.Hang.setPower(0);


            if (!minCheck) {
                moveBot(2, FORWARD, 0.4);
                sleep(100);
            }
            //moveBotcrab(7, RIGHT1, 0.5);

            if (position == 1 && !minCheck) { //left -good

                moveBotcrab(12, RIGHT1, 0.5);
                sleep(100);
                moveBot(7, FORWARD, 0.5); //was 6
                sleep(100);
                moveBotcrab(10, RIGHT1, 0.5);//was 6
                sleep(100);
                imuTurn(100, 0.4);//was 90
                sleep(100);
                moveBot(8, FORWARD, 0.5);
                //r.Arm.setPower(0.5);
                //sleep(1000);
                //r.Arm.setPower(0);
                minCheck = true;

            }

            if ((position == 2 || position == 0) && !minCheck) { //center -good

                moveBotcrab(18, RIGHT1, 0.5);//was 12
                sleep(100);
                // moveBot(1, BACKWARD, 0.5);
                // sleep(100);
                // moveBotcrab(6, RIGHT1, 0.5);
                sleep(100);
                moveBotcrab(5, LEFT1, 0.5);//was 6
                sleep(100);
                imuTurn(90,0.4);
                sleep(100);
                moveBot(14, FORWARD, 0.5);//was 8
                //sleep(100);
                //r.Arm.setPower(-0.5);
                //sleep(1500);

                minCheck = true;
                telemetry.addData("timeElapsed", timeElapsed);
                sleep(1000);

            }

            if (position == 3 && !minCheck) { //right -good

                moveBotcrab(12, RIGHT1, 0.5);
                sleep(100);
                moveBot(10, BACKWARD, 0.5); //was 14
                sleep(100);
                moveBotcrab(10, RIGHT1, 0.5);//was 6
                sleep(100);
                imuTurn(55, 0.4);//was 90
                sleep(100);
                moveBot(8, FORWARD, 0.5);
                minCheck = true;


            }

            if (TFtrue == false) {
                moveBot(6, FORWARD, 0.5);
                sleep(100);
                r.tfd.setPosition(0.0);
                sleep(100);
                r.tfd.setPosition(0.6);

                if (position == 1 && minCheck) { // LEFT
                   //imuTurn();
                   //moveBotcrab(RIGHT1, );
                }

                if (position == 2 || position == 0 && minCheck) { // CENTER
                    //imuTurn();
                    //moveBotcrab(RIGHT1, );
                }

                if (position == 3 && minCheck) {
                    //imuTurn();
                    //moveBotcrab(RIGHT1, );
                }


                while (r.FLMotor.getCurrentPosition() < 20000) {
                    if (r.sensorRange.getDistance(DistanceUnit.INCH) > 3) {
                        r.FLMotor.setPower(0.5 + (r.sensorRange.getDistance(DistanceUnit.INCH) / 90)); //no idea if this will work
                        r.FRMotor.setPower(0.5 - (r.sensorRange.getDistance(DistanceUnit.INCH) / 90));
                        r.BLMotor.setPower(0.5 + (r.sensorRange.getDistance(DistanceUnit.INCH) / 90));
                        r.BRMotor.setPower(0.5 - (r.sensorRange.getDistance(DistanceUnit.INCH) / 90));
                    } else if (r.sensorRange.getDistance(DistanceUnit.INCH) < 3) {
                        r.FLMotor.setPower(0.5 - (r.sensorRange.getDistance(DistanceUnit.INCH) / 90));
                        r.FRMotor.setPower(0.5 + (r.sensorRange.getDistance(DistanceUnit.INCH) / 90));
                        r.BLMotor.setPower(0.5 - (r.sensorRange.getDistance(DistanceUnit.INCH) / 90));
                        r.BRMotor.setPower(0.5 + (r.sensorRange.getDistance(DistanceUnit.INCH) / 90));
                    } else {
                        r.FLMotor.setPower(0.5);
                        r.FRMotor.setPower(0.5);
                        r.BLMotor.setPower(0.5);
                        r.BRMotor.setPower(0.5);

                    }
                }
                r.stopDrivetrain();

                    r.Arm.setPower(0.5);
                    sleep(1500);

                TFtrue = true;
            }
        }
    }
}