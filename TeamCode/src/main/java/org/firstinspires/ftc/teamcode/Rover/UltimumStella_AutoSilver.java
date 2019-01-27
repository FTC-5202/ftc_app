package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous (name="UltimumStella Silver")
@Disabled
public class UltimumStella_AutoSilver extends UltimumStella_AutoMethods {

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

        telemetry.addLine("Initializing...");
        telemetry.update();

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();
        start = System.currentTimeMillis();

        //Color.RGBToHSV(r.sensorColor.red() * 8, r.sensorColor.green() * 8, r.sensorColor.blue() * 8, hsvValues);

        /** Start tracking the data sets we care about. */
        //targetsRoverRuckus.activate();
        while (opModeIsActive()) {

            // if the digital channel returns true it's HIGH and the button is unpressed.
            //if (r.sensorTouch.getState() == true && (timeElapsed < 10000)) {
           /* while (r.sensorTouch.getState() == true && (timeElapsed < 20000) && !isStopRequested()) {
             //   r.Lift.setPower(LiftPow);
                timeElapsed = System.currentTimeMillis() - start;
            }*/

            if (timeElapsed > 20000) {
                MotorPow = 0;
                LiftPow = 0;
            }

            if (MotorPow > 0) {
             //   r.Lift.setPower(0);
                moveBot(30, FORWARD, MotorPow, EndStatus.STOP);
                MotorPow = 0;
            } else {

              //  r.Lift.setPower(-LiftPow);
                sleep(5000);
                LiftPow = 0;
                //  r.RSLif.setPosition(0.58);
               // r.LSLif.setPosition(0.21);
            }


        }

    }
}

    //correction = checkDirection();

    //telemetry.addData("1 imu heading", lastAngles.firstAngle);
    //telemetry.addData("2 global heading", globalAngle);
    //telemetry.addData("3 correction", correction);
    //telemetry.update();





