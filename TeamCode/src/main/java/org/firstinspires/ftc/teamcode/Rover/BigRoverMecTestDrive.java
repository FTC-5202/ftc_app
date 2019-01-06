package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Hannah on 11/16/2017.
 */
@TeleOp (name = "BigRovTestMech")
//@Disabled
public class BigRoverMecTestDrive extends BIGRoverTeleOpMethods {




    double FR;
    double BR;
    double FL;
    double BL;
    double L1;
    double L2;

    double pwr = 1.0;
    //double RLEX;
    boolean isForward = true;
    boolean rb1Pressed = false;
    double Tank = 0.0;
    double HangPow = 0.5;
    double zoneWidth = 0.5;//12/4/17 changed from 0.3 to 0.5



    UltimumStella_Electrical r  = new UltimumStella_Electrical();




    @Override
    public void init() {

        setupAll();


    }



    public void runOpMode() throws InterruptedException {
    }

    @Override
    public void loop() {

        //r.moveServo(r.Larm, 0.2);
        //r.moveServo(r.Rarm, 0.85);

        /*LB = gamepad1.left_stick_y + gamepad1.left_stick_x;
        RF = gamepad1.right_stick_y - gamepad1.right_stick_x;
        LF = gamepad1.left_stick_y - gamepad1.left_stick_x;
        RB = gamepad1.right_stick_y + gamepad1.right _stick_x;*/
        //RLEX = gamepad2.left_stick_y;

            L1 = gamepad2.left_stick_y;
            r.Arm.setPower(L1);

            L2 = gamepad2.right_stick_y;
            r.Sweeper.setPower(L2);

        if (gamepad1.right_bumper && !rb1Pressed) {
            rb1Pressed = true;
        }
        if (!gamepad1.right_bumper && rb1Pressed) {
            isForward = !isForward;
            rb1Pressed = false;
        }
        if (Math.abs(gamepad1.left_stick_x) <= (zoneWidth) || (Math.abs(gamepad1.right_stick_x)) <= (zoneWidth)) {
            Tank = 1.0;
            if (!isForward) {
                BL = -gamepad1.right_stick_y;
                BR = -gamepad1.left_stick_y;
                FL = -gamepad1.right_stick_y;
                FR = -gamepad1.left_stick_y;
            } else {
                BL = gamepad1.left_stick_y;
                BR = gamepad1.right_stick_y;
                FL = gamepad1.left_stick_y;
                FR = gamepad1.right_stick_y;
            }

        }


        if (Math.abs(gamepad1.left_stick_x) >= zoneWidth || (Math.abs(gamepad1.right_stick_x) >= zoneWidth)) {
            Tank = 2.0;

            if (!isForward) {
                BL = -gamepad1.right_stick_x;
                BR = gamepad1.left_stick_x;
                FL = gamepad1.right_stick_x;
                FR = -gamepad1.left_stick_x;
            } else {
                BL = gamepad1.left_stick_x;
                BR = -gamepad1.right_stick_x;
                FL = -gamepad1.left_stick_x;
                FR = gamepad1.right_stick_x;
            }


        }

        r.BLMotor.setPower(BL);
        r.BRMotor.setPower(BR);
        r.FLMotor.setPower(FL);
        r.FRMotor.setPower(FR);

        HangPow = gamepad1.left_trigger;
        r.Hang.setPower(HangPow / 1.0);

        HangPow = gamepad1.right_trigger;
        r.Hang.setPower(HangPow / -1.0);

    }//end of loop
} //end of extends BIGRoverTeleOpMethods




