package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous (name="NewRobotHang")
@Disabled
public class NewRobotHang extends LinearOpMode {

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
        double LowerPow = -0.;
        double InitPow = 0.5;

        //setupAll();
        DcMotor Hang = null;

        Hang = hardwareMap.dcMotor.get("Lift");

        Hang.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        Hang.setPower(InitPow);

        waitForStart();

        //while (opModeIsActive()) { }
        Hang.setPower(LowerPow);
        sleep(1000);


    }
}

    //correction = checkDirection();

    //telemetry.addData("1 imu heading", lastAngles.firstAngle);
    //telemetry.addData("2 global heading", globalAngle);
    //telemetry.addData("3 correction", correction);
    //telemetry.update();





