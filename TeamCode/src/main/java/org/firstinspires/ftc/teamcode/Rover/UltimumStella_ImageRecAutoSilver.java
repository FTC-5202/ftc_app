package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.Rover.LitttleRover.RoverAutoMethods;

import java.util.List;

import static org.firstinspires.ftc.teamcode.Rover.UltimumStella_AutoMethods.Direction.LEFT;
import static org.firstinspires.ftc.teamcode.Rover.UltimumStella_AutoMethods.Direction.RIGHT;

@Autonomous (name="UltimumStella ImageRecSilver")
public class UltimumStella_ImageRecAutoSilver extends UltimumStella_AutoMethods {

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing...");
        telemetry.update();

        ElapsedTime period = new ElapsedTime();
        long start = 0;
// ...
        long current = System.currentTimeMillis();
        long timeElapsed = 0;

        double MotorPow = 0.5;
        double LiftPow = 1.0;

        setupAll();

        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

        /** Start tracking the data sets we care about. */
        //targetsRoverRuckus.activate();

        if (opModeIsActive()) {
            telemetry.addLine("Op Mode is Active");
            telemetry.update();
            sleep(1000);
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            start = System.currentTimeMillis();

            while (opModeIsActive()) {
                //r.LSLif.setPosition(0.25);
               // r.RSLif.setPosition(0.56);

                // if the digital channel returns true it's HIGH and the button is unpressed.
                //if (r.sensorTouch.getState() == true && (timeElapsed < 10000)) {
                /*while (r.sensorTouch.getState() == true && (timeElapsed < 20000) && !isStopRequested()) {
                    r.Lift.setPower(LiftPow);
                    timeElapsed = System.currentTimeMillis() - start;
                }
                */

                if (timeElapsed > 20000) {
                    MotorPow = 0;
                    LiftPow = 0;
                }

                if (tfod != null) {
                    //if (tfod !=null && timeElapsed >= ?) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        if (updatedRecognitions.size() == 2) {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;
                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineralX = (int) recognition.getLeft();
                                } else if (silverMineral1X == -1) {
                                    silverMineral1X = (int) recognition.getLeft();
                                } else {
                                    silverMineral2X = (int) recognition.getLeft();
                                }
                            }

                            if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                                if (goldMineralX == -1) {
                                    position = 1;
                                    telemetry.addData("Gold Mineral Position", "Left");
                                    tfod = null;
                                }
                            }
                                if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                                    if (goldMineralX < silverMineral1X) {
                                        position = 2;
                                        telemetry.addData("Gold Mineral Position", "Center");
                                    } else {
                                        position = 3;
                                        telemetry.addData("Gold Mineral Position", "Right");
                                    }
                                    tfod = null;
                                }

                            tfod = null;
                        }

                        telemetry.update();
                    }

                }

                telemetry.addLine("TFOD Complete");
                telemetry.update();
                sleep(1000);



                if (position == 1 && !minCheck) {
                    if (MotorPow > 0) {
                       // r.moveServo(r.LSLif, 1.0);
                        //move left
                        moveBot(6, FORWARD, MotorPow);
                        sleep(500);
                        eTurnBot(37, RIGHT, MotorPow, MotorPow); //was LEFT
                        sleep(500);
                        //r.MinFlap.setPosition(0.6);
                        moveStraight(20, FORWARD, MotorPow);
                        sleep(1000);
                        //r.MinFlap.setPosition(0.2);
                        eTurnBot(60, LEFT, MotorPow, MotorPow); //was RIGHT
                        sleep(500);
                        moveStraight(2, FORWARD, MotorPow);
                        sleep(500);
                    }
                    minCheck = true;
                    telemetry.addLine("Position: Left");
                    sleep(2000);
                    telemetry.update();
                }

                if (position == 2 && !minCheck) {
                    if (MotorPow > 0) {
                       // r.moveServo(r.LSLif, 1.0);
                        //move center
                        sleep(1000);
                        //r.MinFlap.setPosition(0.6);
                        moveStraight(24, FORWARD, MotorPow); //was 38
                        //r.MinFlap.setPosition(0.2);
                        sleep(1500);
                        moveStraight(6, FORWARD, MotorPow);
                        sleep(500);

                    }
                    minCheck = true;
                    telemetry.addLine("Position: Center");
                    sleep(2000);
                    telemetry.update();
                }

                if (position == 3 && !minCheck) {
                    if (MotorPow > 0) {
                     //   r.moveServo(r.LSLif, 1.0);
                        //move right
                        moveBot(6, FORWARD, MotorPow);
                        sleep(500);
                        eTurnBot(30, LEFT, MotorPow, MotorPow); //was RIGHT
                        sleep(500);
                        //r.MinFlap.setPosition(0.6);
                        moveStraight(16, FORWARD, MotorPow);
                        sleep(1000);
                        //r.MinFlap.setPosition(0.2);
                        eTurnBot(60, RIGHT, MotorPow, MotorPow); //was LEFT
                        sleep(500);
                        moveStraight(2, FORWARD, MotorPow);
                        sleep(500);
                    }
                    minCheck = true;
                    telemetry.addLine("Position: Right");
                    sleep(2000);
                    telemetry.update();
                }

                /*if (position == 0 && !minCheck) {
                    if (MotorPow > 0) {
                        //move center
                        sleep(1000);
                        //r.MinFlap.setPosition(0.6);
                        moveStraight(24, FORWARD, MotorPow, RoverAutoMethods.EndStatus.STOP); //was 38
                        //r.MinFlap.setPosition(0.2);
                        sleep(1500);
                        moveStraight(2, FORWARD, MotorPow, RoverAutoMethods.EndStatus.STOP);
                        sleep(500);

                    }
                }
                */
                //r.Lift.setPower(-LiftPow);

                sleep(5500);
                LiftPow = 0.;
                //r.RSLif.setPosition(0.21);
            }
        }
        if (tfod != null) tfod.shutdown();
    }
}
//correction = checkDirection();

//telemetry.addData("1 imu heading", lastAngles.firstAngle);
//telemetry.addData("2 global heading", globalAngle);
//telemetry.addData("3 correction", correction);
//telemetry.update();

