package org.firstinspires.ftc.teamcode.Rover.LitttleRover;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Rover.LitttleRover.RoverAutoMethods;

@Autonomous(name="DriveStraightTEST", group ="Rover")
@Disabled
public class DriveStraightTEST extends RoverAutoMethods {

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
            r.FLBLMotor.setPower(0.5);
            r.FRBRMotor.setPower(0.5);
            sleep(500);
            r.stopDrivetrain();
            sleep(500);
            count = count + 1;
        }

        if(count==2) {
            r.moveDrivetrain(.5,.5);
            sleep(500);
            r.stopDrivetrain();
            sleep(500);
            count = count + 1;
        }

        if(count==3) {
            moveBot(20, FORWARD, 0.5);
            sleep(1000);
            count = count + 1;
        }

        if(count==4) {

             moveStraight(90, FORWARD, 0.5);
             sleep(1000);
             count = count + 1;
        }

        r.FLBLMotor.setPower(0.);
        r.FRBRMotor.setPower(0.);
    }

    }
}



