package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Hannah on 11/16/2017.
 */
@TeleOp (name = "RoverTele")
//@Disabled
public class BIGRoverTeleOp extends BIGRoverTeleOpMethods {

    //

        public void init () {

            setupAll();
        }
        double FR;
        double BR;
        double FL;
        double BL;
        double Lift;
        double RarmLifPow;
        double LarmLifPow;
        double LSRotPow;
        double RSRotPow;
        double LSgrabPow;
        double RSgrabPow;
        double RSLifPow;
        double LSLifPow;
        double lb_pressed = 1;
        double rb_pressed = 1;
        boolean isForward = true;
        boolean rb1Pressed = false;
        boolean dpad_pressed = false;
        boolean a_pressed = false;

        public void loop () {

            //r.moveServo(r.Larm, 0.2);
            //r.moveServo(r.Rarm, 0.6);

        /*LB = gamepad1.left_stick_y + gamepad1.left_stick_x;
        RF = gamepad1.right_stick_y - gamepad1.right_stick_x;
        LF = gamepad1.left_stick_y - gamepad1.left_stick_x;
        RB = gamepad1.right_stick_y + gamepad1.right _stick_x;*/
            //RLEX = gamepad2.left_stick_y;


            Lift = gamepad1.left_trigger;
            r.Lift.setPower(Lift/1.0);//changed from 1.5

            Lift = gamepad1.right_trigger;
            r.Lift.setPower(-Lift / 1.0); //changed from 1.5

            if (gamepad1.right_bumper && !rb1Pressed) {
                rb1Pressed = true;
            }
            if (!gamepad1.right_bumper && rb1Pressed) {
                isForward = !isForward;
                rb1Pressed = false;
            }

            if (!isForward) {
                BL = gamepad1.right_stick_y;
                BR = gamepad1.left_stick_y;
                FL = gamepad1.right_stick_y;
                FR = gamepad1.left_stick_y;
            } else {
                BL = -gamepad1.left_stick_y;
                BR = -gamepad1.right_stick_y;
                FL = -gamepad1.left_stick_y;
                FR = -gamepad1.right_stick_y;
            }

            r.BLMotor.setPower(BL);
            r.BRMotor.setPower(BR);
            r.FLMotor.setPower(FL);
            r.FRMotor.setPower(FR);

            LarmLifPow = gamepad2.left_stick_y;
            RarmLifPow = gamepad2.right_stick_y;

            if (gamepad2.dpad_down) {
                r.LSgrab.setPosition(0.7);
                dpad_pressed = true;

                if (gamepad2.dpad_down && !dpad_pressed) {
                    r.LSgrab.setPosition(0.3);
                    dpad_pressed = false;
                }
            }

            if (gamepad2.a) {
                r.RSgrab.setPosition(0.7);
                a_pressed = true;

                if (gamepad2.a && !a_pressed) {
                    r.RSgrab.setPosition(0.3);
                    a_pressed = false;
                }
            }

            if (gamepad2.left_bumper) {
                r.LSrot.setPosition(0.8);
                lb_pressed = 1;

                if (gamepad2.left_bumper && lb_pressed == 1) {
                    r.LSrot.setPosition(0.4);
                    lb_pressed = 2;

                    if (gamepad2.left_bumper && lb_pressed == 2) {
                        r.LSrot.setPosition(0);
                    }
                }

            }

            if (gamepad2.right_bumper) {
                r.RSrot.setPosition(0.8);
                rb_pressed = 1;

                if (gamepad2.right_bumper && rb_pressed == 1) {
                    r.RSrot.setPosition(0.4);
                    rb_pressed = 2;

                    if (gamepad2.right_bumper && rb_pressed == 2) {
                        r.RSrot.setPosition(0);
                    }
                }

            }

            if (gamepad2.left_trigger == 1) {
                r.RSLif.setPosition(0.6);
            }

            if (gamepad2.left_trigger == 0) {
                r.LSLif.setPosition(0.3);
            }

            if (gamepad2.right_trigger == 1) {
                r.RSLif.setPosition(0.6);
            }

            if (gamepad2.right_trigger == 0) {
                r.RSLif.setPosition(0.3);
            }

        }



    }

