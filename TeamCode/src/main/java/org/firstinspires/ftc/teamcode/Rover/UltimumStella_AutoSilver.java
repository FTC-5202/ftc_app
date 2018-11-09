package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous (name="UltimumStella Silver")
public class UltimumStella_AutoSilver extends UltimumStella_AutoMethods {


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
            while (r.sensorTouch.getState() == true && (timeElapsed < 20000)) {
                r.Lift.setPower(LiftPow);
                timeElapsed = System.currentTimeMillis()- start;
            }

            if (timeElapsed > 20000) {MotorPow = 0; LiftPow=0;}

            r.Lift.setPower(0);
            moveBot(30, FORWARD, MotorPow, EndStatus.STOP);

//            r.FRMotor.setPower(MotorPow);
//            r.FLMotor.setPower(MotorPow);
//            r.BRMotor.setPower(MotorPow);
//            r.BLMotor.setPower(MotorPow);
//            sleep(1750);
            MotorPow = 0;
            r.Lift.setPower(-LiftPow);
            sleep(5000);
            LiftPow = 0.;
            //r.stopDrivetrain();
//            r.RSLif.setPosition(0.8);
//            r.LSLif.setPosition(0.2);
        }


    }

}

    //correction = checkDirection();

    //telemetry.addData("1 imu heading", lastAngles.firstAngle);
    //telemetry.addData("2 global heading", globalAngle);
    //telemetry.addData("3 correction", correction);
    //telemetry.update();





