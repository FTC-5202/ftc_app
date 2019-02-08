package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Rover.LitttleRover.RoverAutoMethods;

@Autonomous(name="BRDriveStraightTEST", group ="Rover")
@Disabled
public class BigRoverDriveStraightTEST extends UltimumStella_AutoMethods {

    @Override

    public void runOpMode() {

    setupAll();

    int count = 0;

    telemetry.addLine("Initializing...");
    telemetry.update();

    while (!isStopRequested() && !r.imu.isGyroCalibrated()) {
          sleep(50);
          idle();
    }

    if(r.imu.isGyroCalibrated()) telemetry.addLine("Gyro Calibrated");

    telemetry.addData("Mode", "waiting for start");
    telemetry.update();

    waitForStart();

            /** Start tracking the data sets we care about. */
    while (opModeIsActive()) {
        if(count<1)  {
            r.FLMotor.setPower(0.5);
            r.FRMotor.setPower(0.5);
            r.BLMotor.setPower(0.5);
            r.BRMotor.setPower(0.5);
            sleep(500);
            r.stopDrivetrain();
            sleep(500);
            count = count + 1;
        }

        if(count==1) {
            r.moveDrivetrain(.5,.5);
            sleep(500);
            r.stopDrivetrain();
            sleep(500);
            count = count + 1;
        }

        if(count==2) {
            moveBot(20, FORWARD, 0.5);
            sleep(1000);
            count = count + 1;
        }

        if(count==3) {

            // moveStraight(40, FORWARD, 0.5);
             sleep(1000);
             count = count + 1;
        }

        r.FLMotor.setPower(0.);
        r.FRMotor.setPower(0.);
        r.BLMotor.setPower(0.);
        r.BRMotor.setPower(0.);
    }

    }
}



