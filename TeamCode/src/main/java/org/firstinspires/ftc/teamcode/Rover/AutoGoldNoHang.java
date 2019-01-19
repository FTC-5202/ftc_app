package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

@Autonomous (name="AutoGoldNoHang")
public class AutoGoldNoHang extends UltimumStella_AutoMethods {

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


        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();


        while (opModeIsActive()) {

            telemetry.addLine("Op Mode is Active");
            telemetry.update();
            sleep(250);
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            start = System.currentTimeMillis();

            while (opModeIsActive()) {

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
                    minCheck = true;

                }

                if ((position == 2 || position == 0) && !minCheck) { //center -good

                    moveBotcrab(5, RIGHT1, 0.5);//was 12
                    sleep(100);
                    imuTurn(-10,-0.4);
                    sleep(100);
                    moveBotcrab(13, RIGHT1, 0.5);//was 12
                    sleep(100);
                    moveBotcrab(5, LEFT1, 0.5);//was 6
                    sleep(100);
                    imuTurn(90,0.4);
                    sleep(100);
                    moveBot(14, FORWARD, 0.5);//was 8

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
                    sleep(1000);
                    r.tfd.setPosition(0.6);
                    TFtrue = true;
                    }


                }

            }
        }
    }
