package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;

@Autonomous (name="Auto Gold")
public class Auto_Gold extends UltimumStella_AutoMethods {

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

                    if (tfod != null) {

                        /**The following is basically the TFOD sample program code
                         *  It determines where the gold mineral and the silver minerals are located
                         *  EXCEPT: we are only looking for two minerals in the robot controller PHONE'S
                         *  Field of Vision
                         */

                        //In the following: if we see two objects, are they both silver, or one gold and one silver
                        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                        if (updatedRecognitions != null) {
                            telemetry.addData("# Object Detected", updatedRecognitions.size());
                            telemetry.update();
                            sleep(250);
                            if (updatedRecognitions.size() == 2) {
                                int goldMineralX = -1;
                                int silverMineral1X = -1;
                                for (Recognition recognition : updatedRecognitions) {
                                    if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                        goldMineralX = (int) recognition.getLeft();
                                    } else if (silverMineral1X == -1) {
                                        silverMineral1X = (int) recognition.getLeft();
                                    }
                                }

                                /**
                                 * If all the mineral values are being registered then we move on
                                 * if the gold mineral is still -1 (not seen)
                                 * Gold Mineral Position will register as right
                                 * tfod = null shows we have gone through this and don't want to go through all of these again
                                 *
                                 * if the gold mineral val is less than silver mineral val 1
                                 * Gold Mineral Position will register as center
                                 *
                                 * if gold mineral val is greater than silver mineral val 1
                                 * Gold Mineral Position will register as left
                                 */

                                if (goldMineralX != -1 || silverMineral1X != -1) {
                                    if (goldMineralX == -1) {
                                        position = 3; //was 1
                                        telemetry.addData("Gold Mineral Position", "Right"); //GOOD
                                    } else if (goldMineralX < silverMineral1X) {
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
                sleep(3000);


                if (!minCheck) {
                    moveBotcrab(7, RIGHT1, 0.4);
                    sleep(1000);
                    moveBot(2, FORWARD, 0.4);
                    sleep(100);
                }
                //moveBotcrab(7, RIGHT1, 0.5);

                if (position == 1 && !minCheck) { //left -good
                    //the following is the path that our robot will take when recognizing the gold mineral on the left
                    //With our robots orientation: we move forward, move to the left, forward again, turn, then knock off gold min

                    moveBotcrab(12, RIGHT1, 0.5);
                    sleep(100);
                    moveBot(6, FORWARD, 0.5); // mecanum not working 8.5
                    sleep(100);
                    moveBotcrab(6, RIGHT1, 0.5);// mecanum not working 10
                    sleep(100);
                    imuTurn(115, 0.4);//was 90
                    sleep(100);
                    moveBot(8, FORWARD, 0.5);
                    minCheck = true;

                }

                if ((position == 2 || position == 0) && !minCheck) { //center -good

                    moveBot(3, BACKWARD, 0.5);
                    moveBotcrab(18, RIGHT1, 0.5);//was 12
                    sleep(100);//                   sleep(100);
                    //moveBot(3, BACKWARD, 0.5);
                    //sleep(100);
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

                    moveBotcrab(16, RIGHT1, 0.5); //was 12
                    sleep(100);
                    moveBot(9, BACKWARD, 0.5); //was 10
                    sleep(100);
                    moveBotcrab(10, RIGHT1, 0.5);//was 6
                    sleep(100);
                    imuTurn(55, 0.4);//was 90
                    sleep(100);
                    moveBot(8, FORWARD, 0.5);
                    minCheck = true;


                }

                if (TFtrue == false) { // this val is used for when we are done looking at the minerals and have completed our path
                    //we will move forward and release the team marker into the depot
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
