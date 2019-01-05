package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

//import org.firstinspires.ftc.teamcode.TH3O.Th3OTeleOpMethods;

/**
 * Created by Hannah on 11/16/2017.
 */
@TeleOp (name = "BigRovTestMech")
//@Disabled
public class BigRoverMecTestDrive extends OpMode {

    DcMotor LRMotor = null;
    DcMotor RRMotor = null;
    DcMotor LFMotor = null;
    DcMotor RFMotor = null;
    DcMotor Arm     = null;
    DcMotor Sweeper = null;


    double FR;
    double BR;
    double FL;
    double BL;
    double L1;
    double L2;
    final double OPEN = 0.4;
    final double CLOSED = 0.0;
    double pwr = 1.0;
    //double RLEX;
    boolean isForward = true;
    boolean rb1Pressed = false;
    double Tank = 0.0;
    double zoneWidth = 0.5; //12/4/17 changed from 0.3 to 0.5
    boolean b_Pressed = false;
    boolean arm_state = false;
    boolean a_pressed = false;
    boolean y_pressed = false;
    boolean dpad_up_pressed = false;
    boolean dpad_down_pressed = false;
    boolean y_state = false;
    boolean left_bumperispressed = false;
    boolean glyphgrab1_state = false;
    boolean glyphgrab2_state = false;
    boolean dpad_upispressed = false;
    boolean PRspins = false;
    boolean dpad_downispressed = false;
    boolean right_bumperispressed = false;


    @Override
    public void init() {

        //setupAll();

        LRMotor = hardwareMap.dcMotor.get("BL");
        RRMotor = hardwareMap.dcMotor.get("BR");
        LFMotor = hardwareMap.dcMotor.get("FL");
        RFMotor = hardwareMap.dcMotor.get("FR");
        Arm     = hardwareMap.dcMotor.get("arm");
        Sweeper = hardwareMap.dcMotor.get("sweep");


        LRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LFMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RFMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Sweeper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        LFMotor.setDirection(DcMotor.Direction.REVERSE);
        LRMotor.setDirection(DcMotor.Direction.REVERSE);
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
            Arm.setPower(L1);

            L2 = gamepad2.right_stick_y;
            Sweeper.setPower(L2);

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

        LRMotor.setPower(BL);
        RRMotor.setPower(BR);
        LFMotor.setPower(FL);
        RFMotor.setPower(FR);

    }//end of loop
} //end of extends OpMode




