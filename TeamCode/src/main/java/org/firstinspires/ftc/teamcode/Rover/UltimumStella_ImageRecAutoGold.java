package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.Rover.LitttleRover.RoverAutoMethods;

import java.util.List;

import static org.firstinspires.ftc.teamcode.Rover.UltimumStella_AutoMethods.Direction.LEFT;
import static org.firstinspires.ftc.teamcode.Rover.UltimumStella_AutoMethods.Direction.RIGHT;

@Autonomous (name="UltimumStella ImageRecGold")
@Disabled
public class UltimumStella_ImageRecAutoGold extends UltimumStella_AutoMethods {

    @Override
    public void runOpMode() {

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

        setupAll();

        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

        /** Start tracking the data sets we care about. */
        //targetsRoverRuckus.activate();

        if (opModeIsActive()) {
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

                //r.LSLif.setPosition(0.25);
                // r.RSLif.setPosition(0.56);

                /** if the digital channel returns true it's HIGH and the button is unpressed.
                 */
               /* while (r.sensorTouch.getState() == true && (timeElapsed < 18000) && !isStopRequested()) {
                  //  r.Lift.setPower(LiftPow);
                    timeElapsed = System.currentTimeMillis() - start;

                }*/
               // r.Lift.setPower(0);

                if (timeElapsed > 20000) {
                    MotorPow = 0;
                }

                telemetry.addLine("TFOD Complete");
                telemetry.update();
                sleep(250);

                /**
                 * The following is the different branches that will run through only if their position val = true
                 *
                 */

                if (position == 1 && !minCheck) {
                    if (MotorPow > 0) {
                        // r.moveServo(r.LSLif, 1.0);
                        //move left
                        moveBot(6, FORWARD, MotorPow);
                        sleep(250);
                        eTurnBot(37, RIGHT, MotorPow, MotorPow); //was LEFT
                        sleep(250);
                      /*  r.MinFlap.setPosition(0.4);
                        moveBot(27, FORWARD, MotorPow);//was 32-23
                        sleep(250);
                        moveBot(5, BACKWARD, MotorPow);//was 5
                        sleep(250);
                        eTurnBot(43, LEFT, MotorPow, MotorPow);//was 45-33
                        r.MinFlap.setPosition(1.0);
                        sleep(250);

                        moveBot(20, FORWARD, MotorPow);
                        sleep(250);
                    }
                    minCheck = true;
                    telemetry.addLine("Position: Left");
                    sleep(250);
                    telemetry.update();
                }

                if (position == 2 && !minCheck) {
                    if (MotorPow > 0) {
                        // r.moveServo(r.LSLif, 1.0);
                        //move center
                        sleep(250);
                        r.MinFlap.setPosition(0.4);
                        moveBot(24, FORWARD, MotorPow); //was 38
                        r.MinFlap.setPosition(1.0);
                        sleep(250);
                        moveBot(14, FORWARD, MotorPow);
                        sleep(250);

                    }
                    minCheck = true;
                    telemetry.addLine("Position: Center");
                    sleep(250);
                    telemetry.update();
                }

                if (position == 3 && !minCheck) {
                    if (MotorPow > 0) {
                        //   r.moveServo(r.LSLif, 1.0);
                        //move right
                        moveBot(3, FORWARD, MotorPow);//was 6-4
                        sleep(250);
                        eTurnBot(31, LEFT, MotorPow, MotorPow); //was RIGHT-33-28-26
                        sleep(250);
                        r.MinFlap.setPosition(0.4);
                        sleep(250);
                        moveBot(30, FORWARD, MotorPow);//was 31-22-25-28
                        sleep(250);
                        moveBot(2, BACKWARD, MotorPow);
                        sleep(250);
                        eTurnBot(68, RIGHT, MotorPow, MotorPow);//was 70-65
                        r.MinFlap.setPosition(1.0);
                        sleep(250);
                        moveBot(20, FORWARD, MotorPow);
                        sleep(250);
                    }
                    minCheck = true;
                    telemetry.addLine("Position: Right");
                    sleep(250);
                    telemetry.update();
                }

                if (position == 0 && !minCheck) {
                    if (MotorPow > 0) {
                        // r.moveServo(r.LSLif, 1.0);
                        //move center
                        sleep(250);
                        r.MinFlap.setPosition(0.4);
                        moveBot(24, FORWARD, MotorPow); //was 38
                        r.MinFlap.setPosition(1.0);
                        sleep(250);
                        moveBot(14, FORWARD, MotorPow);
                        sleep(250);

                    }
                }

                /** This is the section of code we use at the end of our auto to deliver the team marker
                 * used to reset the lift back down
                 * lift the arm side lifts back to init positions
                 *
                 */

              /*  r.RSLif.setPosition(0.65);
                r.LSLif.setPosition(0.65);
                sleep(250);
                r.RSLif.setPosition(0.0);
                r.LSLif.setPosition(1.0);
                sleep(250);
                r.Lift.setPower(-LiftPow);
                sleep(6000);
                LiftPow = 0.;
                r.MinFlap.setPosition(1.0);*/
                    }

                }

                if (tfod != null) tfod.shutdown();
            }


        }
    }
}//extends AutoMethods
//correction = checkDirection();

//telemetry.addData("1 imu heading", lastAngles.firstAngle);
//telemetry.addData("2 global heading", globalAngle);
//telemetry.addData("3 correction", correction);
//telemetry.update();


