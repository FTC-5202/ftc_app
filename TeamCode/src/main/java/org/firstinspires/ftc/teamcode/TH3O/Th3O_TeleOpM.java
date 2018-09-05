package org.firstinspires.ftc.teamcode.TH3O;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Hannah on 11/16/2017.
 */
@TeleOp (name = "TH3O TeleOpM")
@Disabled
public class Th3O_TeleOpM extends Th3OTeleOpMethodsM {

     public void init() {

         setupAll();
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



    public void loop(){

        r.moveServo(r.Larm, 0.2);
        r.moveServo(r.Rarm, 0.6);

        /*LB = gamepad1.left_stick_y + gamepad1.left_stick_x;
        RF = gamepad1.right_stick_y - gamepad1.right_stick_x;
        LF = gamepad1.left_stick_y - gamepad1.left_stick_x;
        RB = gamepad1.right_stick_y + gamepad1.right _stick_x;*/
        //RLEX = gamepad2.left_stick_y;

        TgL1 = gamepad2.right_stick_y;
        r.TgrLift1.setPower(TgL1 / 1.5);

        TgL2 = gamepad2.left_stick_y;
        r.TgrLift2.setPower(TgL2 / 1.5);

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



        r.LRMotor.setPower(BL);
        r.RRMotor.setPower(BR);
        r.LFMotor.setPower(FL);
        r.RFMotor.setPower(FR);


        if (gamepad1.y){
            r.wopFL.setPosition(0.7);
            r.wopFR.setPosition(0.8);
            r.wopBL.setPosition(0.8);
            r.wopBR.setPosition(0.7);

        }

       if (gamepad2.right_bumper) {
            right_bumperispressed = true;
        }
        if (right_bumperispressed == true) {
            if (gamepad2.right_bumper == false) {
                if (Tgr_state == false) {
                    r.moveServo(r.Tgr, 0.0);
                    Tgr_state = true;
                }
                else if (Tgr_state == true) {
                    r.moveServo(r.Tgr, 0.4);
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
                    r.moveServo(r.TgrU, 0.0);
                    TgrU_state = true;
                }
                else if (TgrU_state == true) {
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

        /*if (gamepad2.right_bumper) {
            r.Tgr.setPosition(OPEN);
        }
        if (gamepad2.left_bumper) {
            r.Tgr.setPosition(CLOSED);
        }

        if (gamepad2.left_bumper) {
            r.moveServo(r.Tgr, 0.35);
        }

        if (gamepad2.right_bumper) {
            r.moveServo(r.Tgr, 0.0);
        }*/

        if (gamepad2.dpad_up) {
            r.moveMotor(r.RelicEx, 0.5);
        }
        else if (gamepad2.dpad_down) {
            r.moveMotor(r.RelicEx, -0.5);
        }
        else {
            r.moveMotor(r.RelicEx, 0.0);
        }

       //RotateRel
        if (gamepad2.b) {
            b_Pressed = true;
        }
        if (b_Pressed == true) {
            if (gamepad2.b == false) {
                if (rotation == false) {
                    r.moveServo(r.RotateRel, 0.15);
                    rotation = true;

                }
                else if (rotation == true) {
                    r.moveServo(r.RotateRel, 0.7);
                    rotation = false;
                }
                b_Pressed = false;
            }

        }


        //GrabRel
        if (gamepad2.a) {
            a_pressed = true;
        }
        if (a_pressed == true) {
            if (gamepad2.a == false) {
                if (grab_state == true) {
                    r.moveServo(r.GrabRel, 0.5);
                    grab_state = false;
                }
                else if (grab_state == false) {
                    r.moveServo(r.GrabRel, 0.8);
                    grab_state = true;
                }
                a_pressed = false;
            }
        }

        telemetry.addData("RotateRel position", r.RotateRel);
        telemetry.update();








}
}


