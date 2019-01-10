package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static java.lang.Thread.sleep;

/**
 * Created by Hannah on 11/16/2017.
 */
@TeleOp (name = "BIGRoverTele")
@Disabled
public class BIGRoverTeleOp extends BIGRoverTeleOpMethods {
    //static final double INCREMENT = 0.01;     // amount to slew servo each CYCLE_MS cycle
    //static final long CYCLE_MS = 50;     // period of each cycle
    //static final double MAX_POS = 1.0;     // Maximum rotational position
    //static final double MIN_POS = 0.0;     // Minimum rotational position
    //double position = (MAX_POS - MIN_POS) / 2; // Start at halfway position
    //boolean rampUp = true;

    //

    public void init() {

        setupAll();
    }

    //Front on the robot is the side where the claws are.  Opposite side from the hanging lift.
    //Thus the right side is the side which has the robot power switch.
    double FR;      //Front Right motor power
    double BR;      //Back Right motor power
    double FL;      //Front Left motor power
    double BL;      //Back Left motor power
    double Lift;    //power for the motor that will be used to hang.
    double RarmLifPow;      //The long arm on the right side of the robot.
    double LarmLifPow;      //The long arm on the left side of the robot.
    double POS = 0;
    boolean lb_pressed;
    boolean rb_pressed;
    boolean isForward = true;//True means that the the claw side of the robot is currently forward for driving.
    boolean ArmsForward = true;
    boolean rb2pressed = false;

    boolean lb1Pressed = false;
    boolean rb1Pressed = false;  //Program toggles this between false and true after gamepad1's right button has been pressed.
    //Used in conjunction with the button being pressed to make the button toggle between two actions.
    boolean a_pressed = false;      //Similar to rb1 pressed for gamepad2, button a
    boolean b_pressed = false;      //Similar to rb1 pressed for gamepad2, button b
    boolean x_pressed = false;      //Similar to rb1 pressed for gamepad2, button x
    boolean y_pressed = false;      //Similar to rb1 pressed for gamepad2, button y
    boolean dpad_right_pressed = false;  //Similar to rb1 pressed for gamepad2, dpad right
    boolean dpad_up_pressed = false;  //Similar to rb1 pressed for gamepad2, dpad up
    boolean dpad_left_pressed = false;  //Similar to rb1 pressed for gamepad2, dpad left
    boolean dpad_down_pressed = false;  //Similar to rb1 pressed for gamepad2, dpad down
    boolean left_trigger_pressed = false;  //Similar to rb1 pressed for gamepad2, dpad down
    boolean right_trigger_pressed = false;  //Similar to rb1 pressed for gamepad2, dpad down
    boolean right_trigger_state = false;  //Similar to rb1 pressed for gamepad2, dpad down

    boolean LSgrab_state = false;  //State of the left claw
    boolean RSgrab_state = false;  //State of the right claw
    boolean left_trigger_state = false;  //State of the left trigger
    boolean left_bumper_state = false;


    public void loop() {

        //Lift is the power for the motor that will be used to hang.
        Lift = gamepad1.left_trigger;
       // r.Lift.setPower(Lift / 1.0);//changed from 1.5

        Lift = gamepad1.right_trigger;
       // r.Lift.setPower(-Lift / 1.0); //changed from 1.5

        if (gamepad1.left_bumper) { // good
            lb1Pressed = true;
        }

        if (lb1Pressed == true) { // good
            if (gamepad1.left_bumper == false) {
                if (left_bumper_state == false) {
                   // r.MinFlap.setPosition(0.4);
                    left_bumper_state = true;
                } else if (left_bumper_state == true) {
                    //r.MinFlap.setPosition(1.0);
                    left_bumper_state = false;
                }
                lb1Pressed = false;
            }
        }

//The first time gamepad1's right bumper is pressed and release, the hanging lift side of the robot will become the "front" side for driving purposes.
//After the first time, each press/release of the right bumper will toggle which end of the robot is the "front".
        if (gamepad1.right_bumper && !rb1Pressed) {
            rb1Pressed = true;
        }
        if (!gamepad1.right_bumper && rb1Pressed) {
            isForward = !isForward;
            rb1Pressed = false;
        }

        if (gamepad2.right_bumper && !rb2pressed) {
            rb2pressed = true;
        }
        if (!gamepad2.right_bumper && rb2pressed) {
            ArmsForward = !ArmsForward;
            rb2pressed = false;
        }

//The code within the if (!isForward) section below (before the else statement) sets up the behaviors we want when the robot has the hanging side as forward.
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

        if (!ArmsForward) {

            //The left trigger on gamepad2 will only cause action if it is pressed more than halfway.
          /*  if (gamepad2.left_trigger >= 0.5) {
                left_trigger_pressed = true;
            }
*/
//If the left trigger was pressed and has not been pressed again past halfway, and if it's state is false, which is the IC,
//set the claw arm servo to the desired position and turn its state to true.
//Next 2 lines could be combined into one if statement.
          /*  if (left_trigger_pressed == true) { // good
                if (gamepad2.left_trigger < 0.5) {
                    if (left_trigger_state == false) {
                        r.RSLif.setPosition(0.0);
                        left_trigger_state = true;
                    }
//the else if here is not needed, it could be just else
//if left_trigger_state is true, set the servo to a different position (what position?) and turn its state to false.
                    else if (left_trigger_state == true) {
                        r.RSLif.setPosition(0.25);//was 0.5
                        left_trigger_state = false;
                    }
//don't do this section again unless the gamepad2 trigger is pressed more than halfway.
                    left_trigger_pressed = false;
                }
            }

            if (gamepad2.right_trigger >= 0.5) { // good
                right_trigger_pressed = true;
            }

            if (right_trigger_pressed == true) { // good
                if (gamepad2.right_trigger < 0.5) {
                    if (right_trigger_state == false) {
                        r.LSLif.setPosition(0.8);
                        right_trigger_state = true;
                    }
                    else if (right_trigger_state == true) {
                        r.LSLif.setPosition(0.5);
                        right_trigger_state = false;
                    }
                    right_trigger_pressed = false;
                }
            }*/
          //  r.RSLif.setPosition(0.25);
           // r.LSLif.setPosition(0.7);


            if (gamepad2.a) { // good
                a_pressed = true;
            }

            if (a_pressed == true) { // good
                if (gamepad2.a == false) {
                    if (LSgrab_state == false) {
                     //   r.LSgrab.setPosition(0.7);
                        LSgrab_state = true;
                    } else if (LSgrab_state == true) {
                     //   r.LSgrab.setPosition(0.3);
                        LSgrab_state = false;
                    }
                    a_pressed = false;
                }
            }
            if (gamepad2.dpad_down) { // good
                dpad_down_pressed = true;
            }

            if (dpad_down_pressed == true) { // good
                if (gamepad2.dpad_down == false) {
                    if (RSgrab_state == false) {
                     //   r.RSgrab.setPosition(0.2);
                        RSgrab_state = true;
                    } else if (RSgrab_state == true) {
                       // r.RSgrab.setPosition(0.65);
                        RSgrab_state = false;
                    }
                    dpad_down_pressed = false;
                }
            }

            /*

            if (gamepad2.b) { // good
                b_pressed = true;

            }
            if (b_pressed == true) { // good
                if (gamepad2.b == false) {
                    r.LSrot.setPosition(0.9);//was 0.0
                    b_pressed = false;
                }
            }

            */

            if (gamepad2.x) { // good
                x_pressed = true;
            }

            if (x_pressed == true) { // good
                if (gamepad2.x == false) {
                  //  r.LSrot.setPosition(0.15);//was 1.0
                    x_pressed = false;
                }
            }

            if (gamepad2.y) { //good
                y_pressed = true;
            }

            if (y_pressed == true) { //good
                if (gamepad2.y == false) {
                  //  r.LSrot.setPosition(0.7);
                    y_pressed = false;
                }
            }

            if (gamepad2.dpad_right) { // good
                dpad_right_pressed = true;
            }

            if (dpad_right_pressed == true) { // good
                if (gamepad2.dpad_right == false) {
                   // r.RSrot.setPosition(0.9);//was 0.7

                    dpad_right_pressed = false;
                }
            }

            if (gamepad2.dpad_up) { // good
                dpad_up_pressed = true;
            }

            if (dpad_up_pressed == true) { // good - positions need work
                if (gamepad2.dpad_up == false) {
                  //  r.RSrot.setPosition(0.61);
                    dpad_up_pressed = false;
                }
            }

            /*

            if (gamepad2.dpad_left) { // good
                dpad_left_pressed = true;
            }

            if (dpad_left_pressed == true) { // good
                if (gamepad2.dpad_left == false) {
                    r.RSrot.setPosition(0.45);
                    dpad_left_pressed = false;
                }
            }

            */




        } else {

         //   r.RSrot.setPosition(0.6);//was 0.3
         //   r.LSrot.setPosition(0.65);

            if (gamepad2.left_trigger >= 0.5) {
                left_trigger_pressed = true;
            }

            if (left_trigger_pressed == true) {
                if (gamepad2.left_trigger < 0.5) {
                    if (left_trigger_state == false) {
                    //    r.LSLif.setPosition(0.42);//was 0.4
                        left_trigger_state = true;
                    } else if (left_trigger_state == true) {
                       // r.LSLif.setPosition(1.0);
                        left_trigger_state = false;
                    }
                    left_trigger_pressed = false;
                }
            }

            if (gamepad2.right_trigger >= 0.5) {
                right_trigger_pressed = true;
            }

            if (right_trigger_pressed == true) {
                if (gamepad2.right_trigger < 0.5) {
                    if (right_trigger_state == false) {
                      //  r.RSLif.setPosition(0);
                        right_trigger_state = true;
                    } else if (right_trigger_state == true) {
                      //  r.RSLif.setPosition(0.56);
                        right_trigger_state = false;
                    }
                    right_trigger_pressed = false;
                }
            }

            if (gamepad2.dpad_down) {
                dpad_down_pressed = true;
            }

            if (dpad_down_pressed == true) {
                if (gamepad2.dpad_down == false) {
                    if (LSgrab_state == false) {
                     //   r.LSgrab.setPosition(0.7);//was 0.6
                        LSgrab_state = true;
                    } else if (LSgrab_state == true) {
                      //  r.LSgrab.setPosition(0.3);//was 0.2
                        LSgrab_state = false;
                    }
                    dpad_down_pressed = false;
                }
            }
            if (gamepad2.a) {
                a_pressed = true;
            }

            if (a_pressed == true) {
                if (gamepad2.a == false) {
                    if (RSgrab_state == false) {
                     //   r.RSgrab.setPosition(0.2);//was 0.3
                        RSgrab_state = true;
                    } else if (RSgrab_state == true) {
                      //  r.RSgrab.setPosition(0.65);
                        RSgrab_state = false;
                    }
                    a_pressed = false;
                }
            }

        }

        // slew the servo, according to the rampUp (direction) variable.
        /*
        if (gamepad2.b) {
            if (rampUp) {
                // Keep stepping up until we hit the max value.
                position += INCREMENT;
                if (position >= MAX_POS) {
                    position = MAX_POS;
                    rampUp = !rampUp;
                }
            }
        }
            if (gamepad2.x) {
                if (!rampUp) {
                }
                // Keep stepping down until we hit the min value.
                position -= INCREMENT;
                if (position <= MIN_POS) {
                    position = MIN_POS;
                    rampUp = !rampUp;
                }
            }

            */

            //r.RSrot.setPosition(position);

            r.BLMotor.setPower(BL); //good
            r.BRMotor.setPower(BR);
            r.FLMotor.setPower(FL);
            r.FRMotor.setPower(FR);

            LarmLifPow = gamepad2.right_stick_y; //good
           // r.LarmLif.setPower(LarmLifPow / 1.0);
            RarmLifPow = -gamepad2.left_stick_y;
            //r.RarmLif.setPower(RarmLifPow / 1.0);


        }

    }

