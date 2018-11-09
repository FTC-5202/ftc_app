package org.firstinspires.ftc.teamcode.TH3O;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Hannah on 11/16/2017.
 */
@TeleOp (name = "TH3O TeleOpH1")
@Disabled
public class Th3O_TeleOp extends Th3OTeleOpMethods {

    public void init() {

        setupAll();
    }

    /* In this section we define the variables
    final double OPEN and final double CLOSED determines the orientation of the glyph mechanism
    boolean isForward and rb1Pressed are used to determine when polarity flip is true
    double Tank and zoneWidth are used to determine when mechanum drive is being used
    boolean b_Pressed is used to control the "autonomous arm"
    boolean arm_state determines orientation of the "autonomous arm"
    boolean a_pressed and y_pressed are used to determine the direction of the spinning paint rollers
    boolean dpad_up_pressed and dpad_down_pressed are used to determine the direction of the spinning paint rollers on non-phone side
    boolean left_bumperispressed and glyphgrab(1 or 2) state is used to grab the glyph

    // Gamepad1: Base Drive
   right and left joysticks are used for tank drive and "crabbing"
   right bumper is used for polarity flip - which changes the forward direction for driving

   // Gamepad2: Glyph Grabbing/Scoring
   left bumper closes and opens the phone side paint roller arms to grab and release glyphs
   right bumper closes and opens the non-phone side paint roller arms to grab and release glyphs
   y button spins the paint rollers on phone side to push out glyphs
   a button spins the paint rollers on phone side to take in glyphs
   dpad_up spins the paint rollers on non-phone side to push out glyphs
   dpad_down spins the paint rollers on non-phone side to take in glyphs
   b moves autonomous arm up and down
   left stick controls the phone side lift
   right stick controls the non-phone side lift

     */

    double FR;
    double BR;
    double FL;
    double BL;
    double L1;
    double L2;
    boolean isForward = true;
    boolean rb1Pressed = false;
    double  Tank = 0.0;
    double  zoneWidth = 0.5;
    boolean b_Pressed = false;
    boolean arm_state = false;
    boolean a_pressed = false;
    boolean y_pressed = false;
    boolean dpad_up_pressed = false;
    boolean dpad_down_pressed = false;
    boolean left_bumper1ispressed = false;
    boolean left_bumper2ispressed = false;
    boolean glyphgrab1_state = false;
    boolean glyphgrab2_state = false;
    boolean PRspins = false;
    boolean right_bumperispressed = false;
    boolean x_pressed = false;

    public void loop() {

        r.moveServo(r.Rarm, 0.85);

        L1 = gamepad2.left_stick_y;
        r.Lift1.setPower(L1);

        L2 = gamepad2.right_stick_y;
        r.Lift2.setPower(L2);

        //The next two if statements determine which end of the robot is forward
        //This allows the driver to go backwards without having to drive backwards
        //We implemented this idea since our robot has glyph collection on both ends
        if (gamepad1.right_bumper && !rb1Pressed) rb1Pressed = true;
        if (!gamepad1.right_bumper && rb1Pressed) {
            isForward = !isForward;
            rb1Pressed = false;
        }
        // The following statements are a set of equations in which will determine whether our robot will move in tank mode or crab mode
        //As long as the stick x absolute values are less than zoneWidth, robot will stay in tank mode
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

        //As long as the stick x absolute values are greater than zoneWidth, robot will orient to "crab mode" or mecanum drive
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

        if (gamepad1.left_bumper) {
            left_bumper1ispressed = true;

            if (left_bumper1ispressed == true) {
                    r.moveServo(r.Rprgrab1, 1.0);
                    r.moveServo(r.Lprgrab1, 0.0);
                    r.moveServo(r.Rprgrab2, 1.0);
                    r.moveServo(r.Lprgrab2, 0.0);


                }

            }


        //Paint roller glyph grab/release on the phone side
        if (gamepad2.left_bumper)   left_bumper2ispressed = true;

        if (left_bumper2ispressed == true) {
            if (gamepad2.left_bumper == false) {
                if (glyphgrab1_state == false) {
                    r.moveServo(r.Rprgrab1, 0.18); // - open
                    r.moveServo(r.Lprgrab1, 0.70); // - open
                    glyphgrab1_state = true;
                } else if (glyphgrab1_state == true) {
                    r.moveServo(r.Rprgrab1, 0.42); // - closed was 35
                    r.moveServo(r.Lprgrab1, 0.47); // - closed was 5
                    glyphgrab1_state = false;
                }
                left_bumper2ispressed = false;
            }
        }
        if (gamepad2.x) x_pressed = true;

        if(x_pressed == true) {
            if (gamepad2.x == false) {
                if (glyphgrab1_state == true ) {
                    r.moveServo(r.Rprgrab1, 0.5); //change value
                    r.moveServo(r.Rprgrab2, 0.5); //change value
                    glyphgrab1_state = false;
                }
            }
        }


        //Paint rollers take in glyph on phone side
        if (gamepad2.y) y_pressed = true;

        if (y_pressed == true) {
            if (gamepad2.y == false) {
                if (PRspins == false) {
                    r.Rprspin1.setPower(-0.825);
                    r.Lprspin1.setPower(0.825);
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
        //Paint rollers push out glyph on phone side
        if (gamepad2.a) a_pressed = true;

        if (a_pressed == true) {
            if (gamepad2.a == false) {
                if (PRspins == false) {
                    r.Rprspin1.setPower(0.825);
                    r.Lprspin1.setPower(-0.825);
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

        //Paint roller glyph grab/release on non-phone side

        if (gamepad2.right_bumper) right_bumperispressed = true;

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

        //Paint roller take in glyph on non-phone side

        if (gamepad2.dpad_up) dpad_up_pressed = true;

        if (dpad_up_pressed == true) {
            if (gamepad2.dpad_up == false) {
                if (PRspins == false) {
                    r.Rprspin2.setPower(-0.825);
                    r.Lprspin2.setPower(0.825);
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
        //Paint roller push out glyph on non-phone side
        if (gamepad2.dpad_down) dpad_down_pressed = true;

        if (dpad_down_pressed == true) {
            if (gamepad2.dpad_down == false) {
                if (PRspins == false) {
                    r.Rprspin2.setPower(0.825);
                    r.Lprspin2.setPower(-0.825);
                    PRspins = true;
                } else if (PRspins == true) {
                    r.Rprspin2.setPower(0.0);
                    r.Lprspin2.setPower(0.0);
                    PRspins = false;
                }
                dpad_down_pressed = false;
            }
        }

        //Autonomous arm
        if (gamepad2.b) b_Pressed = true;

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




