package org.firstinspires.ftc.teamcode.Rover.LitttleRover;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

@TeleOp (name="Little Mech Robot")
@Disabled
public class LittleMechRobot extends OpMode {

    DcMotor BLMotor;
    DcMotor FLMotor;
    DcMotor FRMotor;
    DcMotor BRMotor;

    boolean isForward = true;
    double FR;
    double BR;
    double FL;
    double BL;
    double Tank = 0.0;
    double zoneWidth = 0.5;


    public void init() {
        BLMotor = hardwareMap.dcMotor.get("BLMotor");
        FLMotor = hardwareMap.dcMotor.get("FLMotor");
        BRMotor = hardwareMap.dcMotor.get("BRMotor");
        FRMotor = hardwareMap.dcMotor.get("FRMotor");

        BRMotor.setDirection(DcMotor.Direction.REVERSE);
        FRMotor.setDirection(DcMotor.Direction.REVERSE);

    }

    public void loop() {

        if (Math.abs(gamepad1.left_stick_x) <= (zoneWidth) || (Math.abs(gamepad1.right_stick_x)) <= (zoneWidth)) {
            Tank = 1.0;
            if (!isForward) {
                BL = (-gamepad1.right_stick_y);
                BR = (-gamepad1.left_stick_y);
                FL = (-gamepad1.right_stick_y);
                FR = (-gamepad1.left_stick_y);
            } else {
                BL = (gamepad1.left_stick_y);
                BR = (gamepad1.right_stick_y);
                FL = (gamepad1.left_stick_y);
                FR = (gamepad1.right_stick_y);
            }

        }


        if (Math.abs(gamepad1.left_stick_x) >= zoneWidth || (Math.abs(gamepad1.right_stick_x) >= zoneWidth)) {
            Tank = 2.0;

            if (!isForward) {
                BL = (-gamepad1.right_stick_x);
                BR = (gamepad1.left_stick_x);
                FL = (gamepad1.right_stick_x);
                FR = (-gamepad1.left_stick_x);
            } else {
                BL = (gamepad1.left_stick_x);
                BR = (-gamepad1.right_stick_x);
                FL = (-gamepad1.left_stick_x);
                FR = (gamepad1.right_stick_x);
            }


        }

        BLMotor.setPower(BL);
        BRMotor.setPower(BR);
        FLMotor.setPower(FL);
        FRMotor.setPower(FR);
    }
}
