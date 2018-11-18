package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous (name="UltimumStella ImageRecGold")
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

        double MotorPow = 0.5;
        double LiftPow = 1.0;

        setupAll();

        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

        /** Start tracking the data sets we care about. */
        //targetsRoverRuckus.activate();

        if (opModeIsActive()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            start = System.currentTimeMillis();

            while (opModeIsActive()) {

                // if the digital channel returns true it's HIGH and the button is unpressed.
                //if (r.sensorTouch.getState() == true && (timeElapsed < 10000)) {
                while (r.sensorTouch.getState() == true && (timeElapsed < 20000) && !isStopRequested()) {
                    r.Lift.setPower(LiftPow);
                    timeElapsed = System.currentTimeMillis() - start;
                }

                if (timeElapsed > 20000) {
                    MotorPow = 0;
                    LiftPow = 0;
                }

                //if (MotorPow > 0) {
                //r.Lift.setPower(0);
                // moveBot(38, FORWARD, MotorPow, EndStatus.STOP);
                // MotorPow = 0.;
                // } else {
                // r.Lift.setPower(-LiftPow);
                // sleep(5500);
                // LiftPow = 0.;
                //r.RSLif.setPosition(0.4);
                // }

                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        if (updatedRecognitions.size() == 3) {
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
                                if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                    position = 1;
                                    telemetry.addData("Gold Mineral Position", "Left");
                                } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                    position = 3;
                                    telemetry.addData("Gold Mineral Position", "Right");
                                } else {
                                    position = 2;
                                    telemetry.addData("Gold Mineral Position", "Center");
                                }
                            }
                        }

                        telemetry.update();
                    }
                }

                if (position == 1) {
                    //move left

                }

                if (position == 2) {
                    //move center
                }

                if (position == 3) {

                }

                if (MotorPow > 0) {
                    r.Lift.setPower(0);
                    moveBot(38, FORWARD, MotorPow, EndStatus.STOP);
                    MotorPow = 0.;
                } else {
                    r.Lift.setPower(-LiftPow);
                    sleep(5500);
                    LiftPow = 0.;
                    r.RSLif.setPosition(0.4);
                }
            }
        }
        if (tfod != null) tfod.shutdown();
    }
}




