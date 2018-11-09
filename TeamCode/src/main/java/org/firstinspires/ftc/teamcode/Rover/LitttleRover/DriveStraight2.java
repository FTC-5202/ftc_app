package org.firstinspires.ftc.teamcode.Rover.LitttleRover;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="DriveStraight2", group ="Rover")
@Disabled
public class DriveStraight2 extends LinearOpMode {

    public DcMotor FLBLMotor = null;
    public DcMotor FRBRMotor = null;

        @Override
    public void runOpMode() throws InterruptedException {

            FLBLMotor = hardwareMap.dcMotor.get("FLBL");
            FRBRMotor = hardwareMap.dcMotor.get("FRBR");

            FLBLMotor.setPower(0);
            FRBRMotor.setPower(0);

        int count = 0;

        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

            /** Start tracking the data sets we care about. */
         while (opModeIsActive()) {

                if(count<1) {
                    //moveStraight(100, FORWARD, 0.5);
                    //moveBot(100, FORWARD, 0.5);
                    FLBLMotor.setPower(0.5);
                    FRBRMotor.setPower(0.5);
                    sleep(3000);

                    count = count + 1;
                }
                FLBLMotor.setPower(0.);
                FRBRMotor.setPower(0.);

            }

    }
}



