package org.firstinspires.ftc.teamcode.TH3O.OldTheo;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Hannah on 11/16/2017.
 */
@TeleOp (name = "TH3O TeleOpNT")
@Disabled
public class Th3ONewTest extends LinearOpMode {
    public boolean bLedOn = true;

    public DcMotor LRMotor = null;
    public DcMotor RRMotor = null;
    public DcMotor LFMotor = null;
    public DcMotor RFMotor = null;
    public DcMotor TgrLift1 = null;
    public DcMotor TgrLift2 = null;
    public DcMotor RelicEx = null;



    public Servo wopL = null;
    public Servo wopFR = null;
    public Servo wopBR = null;
    public Servo Rarm = null;
    public Servo PRLtPiv = null;
    public Servo PRRtPiv = null;
    public Servo PRLtRoll = null;
    public Servo PRRtRoll = null;
    public Servo BackSSG = null;
    public Servo RotateRel = null;
    public Servo GrabRel = null;

    ColorSensor LS2;
    ColorSensor RVcl;

    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    public void init(HardwareMap ahwMap) {

        hwMap = ahwMap;

        LRMotor = hwMap.dcMotor.get("BL");
        RRMotor = hwMap.dcMotor.get("BR");
        LFMotor = hwMap.dcMotor.get("FL");
        RFMotor = hwMap.dcMotor.get("FR");

        TgrLift1 = hwMap.dcMotor.get("TgrLift1");
        TgrLift2 = hwMap.dcMotor.get("TgrLift2");
        RelicEx = hwMap.dcMotor.get("RelicEx");

        LFMotor.setDirection(DcMotor.Direction.REVERSE);
        LRMotor.setDirection(DcMotor.Direction.REVERSE);

        LRMotor.setPower(0);
        RRMotor.setPower(0);
        LFMotor.setPower(0);
        RFMotor.setPower(0);
        TgrLift1.setPower(0);
        TgrLift2.setPower(0);
        RelicEx.setPower(0);

        LRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //was RUN_WITHOUT_ENCODERS
        RRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        wopL = hwMap.servo.get("wopDL");
        PRLtPiv = hwMap.servo.get("PRLtPiv");
        PRRtPiv = hwMap.servo.get("PRRtPiv");
        PRLtRoll = hwMap.servo.get("LtPTRoller");
        PRRtRoll = hwMap.servo.get("RtPTRoller");
        BackSSG = hwMap.servo.get("BackSSG");
        wopFR = hwMap.servo.get("wopFR");
        wopBR = hwMap.servo.get("wopBR");
        Rarm = hwMap.servo.get("Rarm");
        RotateRel = hwMap.servo.get("RotateRel");
        GrabRel = hwMap.servo.get("GrabRel");

    }

    @Override
    public void runOpMode() throws InterruptedException {

    }

         double FR;
         double BR;
         double FL;
         double BL;
         double TgL1;
         double TgL2;
         final double OPEN = 0.4;
         final double CLOSED = 0.0;
         //double RLEX;
         boolean isForward = true;
         boolean rb1Pressed = false;
         double Tank = 0.0;
         double zoneWidth = 0.5; //12/4/17 changed from 0.3 to 0.5
         boolean b_Pressed = false;
         boolean rotation = false;
         boolean a_pressed = false;
         boolean grab_state = true;
         boolean right_bumperispressed = false;
         boolean Tgr_state = false;
         boolean left_bumperispressed = false;
         boolean TgrU_state = false;
         boolean left_triggerispressed = false;
         boolean TgrB_state = false;

/*

    public void loop(){

        TgL1 = gamepad2.right_stick_y;
        TgrLift1.setPower(TgL1 / 1.5);

        TgL2 = gamepad2.left_stick_y;
        TgrLift2.setPower(TgL2 / 1.5);

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
        } else {
            BL = -gamepad1.left_stick_y;
            BR = gamepad1.left_stick_y;
            FL = gamepad1.left_stick_y;
            FR = -gamepad1.left_stick_y;
        }



        if (Math.abs(gamepad1.left_stick_x) >= zoneWidth || (Math.abs(gamepad1.right_stick_x) >= zoneWidth)) {
            Tank = 2.0;

            if (!isForward) {
                BL = -gamepad1.right_stick_x;
                BR = gamepad1.left_stick_x;
                FL = gamepad1.right_stick_x;
                FR = -gamepad1.left_stick_x;
            }
            else {
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


        if (gamepad2.y){
            wopFR.setPosition(0.8);
            wopL.setPosition(0.5);
            wopBR.setPosition(0.7);

        }

       if (gamepad2.right_bumper) {
            right_bumperispressed = true;
        }
        if (right_bumperispressed == true) {
            if (gamepad2.right_bumper == false) {
                if (Tgr_state == false) {
                    PRRtRoll.setPosition(1.0);
                    Tgr_state = true;
                }
                else if (Tgr_state == true) {
                    PRRtRoll.setPosition(-1.0);
                    Tgr_state = false;
                }
                right_bumperispressed = false;
            }
        }
        if (gamepad2.left_bumper) {
            left_bumperispressed = true;
        }
        if (left_bumperispressed == true) {
            if (gamepad2.left_bumper == false) {
                if (TgrU_state == false) {
                    PRLtRoll.setPosition(-1.0);
                    TgrU_state = true;
                }
                else if (TgrU_state == true) {
                    PRRtRoll.setPosition(1.0);
                    r.moveServo(r.TgrU, 0.4);
                    TgrU_state = false;
                }
                left_bumperispressed = false;
            }
        }
        if (gamepad2.left_trigger >= 0.5) {
            left_triggerispressed = true;
        }
        if (left_triggerispressed == true) {
            if (gamepad2.left_trigger < 0.5) {
                if (TgrB_state == false) {
                    r.moveServo(r.TgrB, 0.0);
                    TgrB_state = true;
                }
                else if (TgrB_state == true) {
                    r.moveServo(r.TgrB, 0.4);
                    TgrB_state = false;
                }
                left_triggerispressed = false;
            }
        }

        if (gamepad1.dpad_up) {
            RelicEx.setPower(0.5);
        }
        else if (gamepad1.dpad_down) {
            RelicEx.setPower(-0.5);
        }
        else {
            RelicEx.setPower(0.0);

        }

       //RotateRel
        if (gamepad1.b) {
            b_Pressed = true;
        }
        if (b_Pressed == true) {
            if (gamepad1.b == false) {
                if (rotation == false) {
                    RotateRel.setPosition(0.15);
                    rotation = true;

                }
                else if (rotation == true) {
                    RotateRel.setPosition(0.7);
                    rotation = false;
                }
                b_Pressed = false;
            }

        }


        //GrabRel
        if (gamepad1.a) {
            a_pressed = true;
        }
        if (a_pressed == true) {
            if (gamepad1.a == false) {
                if (grab_state == true) {
                    GrabRel.setPosition(0.5);
                    grab_state = false;
                }
                else if (grab_state == false) {
                    GrabRel.setPosition(0.8);
                    grab_state = true;
                }
                a_pressed = false;
            }
        }



        telemetry.addData("RotateRel position",RotateRel);
        telemetry.update();

        */








}
//}


