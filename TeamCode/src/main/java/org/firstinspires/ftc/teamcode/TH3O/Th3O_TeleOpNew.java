package org.firstinspires.ftc.teamcode.TH3O;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Hannah on 11/16/2017.
 */
@TeleOp (name = "TH3O TeleOpH2")
@Disabled
public class Th3O_TeleOpNew extends Th3OTeleOpMethods {

    public void init() {

        setupAll();
    }

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


    /*public void runOpMode() throws InterruptedException {

    }*/
        public void loop() {

            //r.moveServo(r.Larm, 0.2);
            r.moveServo(r.Rarm, 0.85);

        /*LB = gamepad1.left_stick_y + gamepad1.left_stick_x;
        RF = gamepad1.right_stick_y - gamepad1.right_stick_x;
        LF = gamepad1.left_stick_y - gamepad1.left_stick_x;
        RB = gamepad1.right_stick_y + gamepad1.right _stick_x;*/
            //RLEX = gamepad2.left_stick_y;

            L1 = gamepad2.left_stick_y;
            r.Lift1.setPower(L1);

            L2 = gamepad2.right_stick_y;
            r.Lift2.setPower(L2);

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


            r.LRMotor.setPower(BL);
            r.RRMotor.setPower(BR);
            r.LFMotor.setPower(FL);
            r.RFMotor.setPower(FR);


            if (gamepad1.y) {
                //r.wopFL.setPosition(0.7);
                //r.wopFR.setPosition(0.8);
                //r.wopBL.setPosition(0.8);
                //r.wopBR.setPosition(0.7);
                //r.wopDL.setPosition(0.5); //many need to change to 0.4 or 0.6

            }
            //PAINT GRABBER 1
            if (gamepad2.left_bumper) {
                left_bumperispressed = true;
            }

            if (left_bumperispressed == true) {
                if (gamepad2.left_bumper == false) {
                    if (glyphgrab1_state == false) {
                        r.moveServo(r.Rprgrab1, 0.11); // - closed
                        r.moveServo(r.Lprgrab1, 0.77); // - closed
                        glyphgrab1_state = true;
                    } else if (glyphgrab1_state == true) {
                        r.moveServo(r.Rprgrab1, 0.28); // - open
                        r.moveServo(r.Lprgrab1, 0.57); // - open
                        glyphgrab1_state = false;
                    }
                    left_bumperispressed = false;

                }
            }
            //forward
            if (gamepad2.y) {
                y_pressed = true;
            }
            if (y_pressed == true) {
                if (gamepad2.y == false) {
                    if (PRspins == false) {
                        r.Rprspin1.setPower(-0.6);
                        r.Lprspin1.setPower(0.6);
                      //  r.Rprspin2.setPower(-0.6);
                      //  r.Lprspin2.setPower(0.6);
                        PRspins = true;
                    }
                    else if (PRspins == true) {
                        r.Rprspin1.setPower(0.0);
                        r.Lprspin1.setPower(0.0);
                        PRspins = false;
                    }
                    y_pressed = false;
                }
            }
            //backward
            if (gamepad2.a) {
                a_pressed = true;
            }
            if (a_pressed == true) {
                if (gamepad2.a == false) {
                    if (PRspins == false) {
                        r.Rprspin1.setPower(0.6);
                        r.Lprspin1.setPower(-0.6);
                        PRspins = true;
                    }
                    else if (PRspins == true) {
                        r.Rprspin1.setPower(0.0);
                        r.Lprspin1.setPower(0.0);
                        PRspins = false;
                    }
                    a_pressed = false;
                }
            }

            //PAINT GRABBER 2

            if (gamepad2.right_bumper) {
                right_bumperispressed = true;
            }
            if (right_bumperispressed == true) {
                if (gamepad2.right_bumper == false) {
                    if (glyphgrab2_state == false) {
                        r.moveServo(r.Rprgrab2, 0.2); //was 0.2,.25,.35
                        r.moveServo(r.Lprgrab2, 0.6); //was 0.8,.75,.65
                        glyphgrab2_state = true;
                    }
                    else if (glyphgrab2_state == true) {
                        r.moveServo(r.Rprgrab2, 0.4); // -- CLOSED
                        r.moveServo(r.Lprgrab2, 0.4); // -- CLOSED
                        glyphgrab2_state = false;
                    }
                    right_bumperispressed = false;
                }
            }

            if (gamepad2.dpad_up) {
                dpad_up_pressed = true;
            }
            if (dpad_up_pressed == true) {
                if (gamepad2.dpad_up == false) {
                    if (PRspins == false) {
                        r.Rprspin2.setPower(-0.6);
                        r.Lprspin2.setPower(0.6);
                        PRspins = true;
                    }
                    else if (PRspins == true) {
                        //r.moveServo(r.Rprspin, 0.5);
                        //r.moveServo(r.Lprspin, 0.5);
                        r.Rprspin2.setPower(0.0);
                        r.Lprspin2.setPower(0.0);
                        // r.Rprspin2.setPower(0.0);
                        // r.Lprspin2.setPower(0.0);
                        PRspins = false;
                    }
                    dpad_up_pressed = false;
                }
            }
            //backward
            if (gamepad2.dpad_down) {
                dpad_down_pressed = true;
            }
            if (dpad_down_pressed == true) {
                if (gamepad2.dpad_down == false) {
                    if (PRspins == false) {
                        r.Rprspin2.setPower(0.6);
                        r.Lprspin2.setPower(-0.6);
                        PRspins = true;
                    } else if (PRspins == true) {
                        r.Rprspin2.setPower(0.0);
                        r.Lprspin2.setPower(0.0);
                        PRspins = false;
                    }
                    dpad_down_pressed = false;

                }

            }




            if (gamepad2.b) {
                b_Pressed = true;
            }
            if (b_Pressed == true) {
                if (gamepad2.b == false) {
                    if (arm_state == false) {
                        r.moveServo(r.Rarm, 0.1); //down
                        arm_state = true;
                    }
                    else if (arm_state == true) {
                        r.moveServo(r.Rarm, 0.9); //up
                        arm_state = false;
                    }
                    b_Pressed = false;
                }
            }
            telemetry.update();


        }
    }




