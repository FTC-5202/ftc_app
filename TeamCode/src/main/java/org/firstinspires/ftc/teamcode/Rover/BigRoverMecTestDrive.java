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
    double HangPow = 1.0;
    double zoneWidth = 0.5;//12/4/17 changed from 0.3 to 0.5
    boolean a_pressed = false;
    boolean a_pressed2 = false;
    boolean tfd_state = false;
    boolean pin_state = false;
    boolean rb2Pressed = false;
    boolean flap_state = false;

    //UltimumStella_Electrical r  = new UltimumStella_Electrical();

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

            L2 = r.power(gamepad2.right_stick_y);
            r.Sweeper.setPower(L2);

           /* if (gamepad1.a) {
                a_pressed = true;
            }

            if (a_pressed == true) {
                if (gamepad1.a == false) {
                    if (pin_state == false) {
                        r.pin.setPosition(0.5);
                        pin_state = true;
                    }
                    else if (pin_state == true) {
                        r.pin.setPosition(1.0);
                        pin_state = false;
                    }
                    a_pressed = false;
                }

            }*/

           if (gamepad1.a) {
               r.pin.setPosition(1.0);
           } else if (gamepad1.b) {
               r.pin.setPosition(0.0);
           } else {
               r.pin.setPosition(0.5);
           }

           if (gamepad2.right_bumper) {
               rb2Pressed = true;
           }
           if (rb2Pressed == true) {
               if (!gamepad2.right_bumper) {
                   if (flap_state == false) {
                       r.flap.setPosition(0.2);
                       flap_state = true;
                   } else if (flap_state == true) {
                       r.flap.setPosition(0.5);
                       flap_state = false;
                   }
                   rb2Pressed = false;
               }

           }

           if (gamepad2.a) {
               a_pressed = true;
           }

           if (a_pressed == true) {
               if (!gamepad2.a) {
                   if (tfd_state == false) {
                       r.tfd.setPosition(0.0);
                       tfd_state = true;
                   } else if (tfd_state == true) {
                       r.tfd.setPosition(0.6);
                       tfd_state = false;
                   }
                   a_pressed = false;
               }
           }


        if (gamepad1.right_bumper && !rb1Pressed) {
            rb1Pressed = true;
        }
        if (!gamepad1.right_bumper && rb1Pressed) {
            isForward = !isForward;
            rb1Pressed = false;
        }
        if (Math.abs(gamepad1.left_stick_x) <= (zoneWidth) || (Math.abs(gamepad1.right_stick_x)) <= (zoneWidth)) {
            Tank = 1.0; // threshold for joystick val too determine tank of mechanum drive
            if (!isForward) { //when polarity is flipped
                BL = (-gamepad1.right_stick_y); //set power for the motors with the right and left stick y val
                BR = (-gamepad1.left_stick_y);
                FL = (-gamepad1.right_stick_y);
                FR = (-gamepad1.left_stick_y);
            } else { //when polarity isn't flipped
                BL = (gamepad1.left_stick_y);
                BR = (gamepad1.right_stick_y);
                FL = (gamepad1.left_stick_y);
                FR = (gamepad1.right_stick_y);
            }

        }

        if (Math.abs(gamepad1.left_stick_x) >= zoneWidth || (Math.abs(gamepad1.right_stick_x) >= zoneWidth)) {
            Tank = 2.0; // threshold for joystick val too determine tank of mechanum drive

            if (!isForward) { //when polarity is flipped
                BL = (-gamepad1.right_stick_x); //set power for the motors with the right and left stick y val
                BR = (gamepad1.left_stick_x);
                FL = (gamepad1.right_stick_x);
                FR = (-gamepad1.left_stick_x);
            } else { //when polarity is flipped
                BL = (gamepad1.left_stick_x);
                BR = (-gamepad1.right_stick_x);
                FL = (-gamepad1.left_stick_x);
                FR = (gamepad1.right_stick_x);
            }


        }

        r.BLMotor.setPower(BL);
        r.BRMotor.setPower(BR);
        r.FLMotor.setPower(FL);
        r.FRMotor.setPower(FR);

        if (gamepad1.left_trigger > 0.2 && gamepad1.left_trigger < 0.5) {
            r.Hang.setPower(0.25); }

        else if (gamepad1.left_trigger >= 0.5 && gamepad1.left_trigger < 0.8) {
                r.Hang.setPower(0.5); }

        else if (gamepad1.left_trigger >= 0.8){
            r.Hang.setPower(1.0); }

        else if (gamepad1.right_trigger > 0.2 && gamepad1.right_trigger < 0.5) {
            r.Hang.setPower(-0.25); }

        else if (gamepad1.right_trigger >= 0.5 && gamepad1.right_trigger < 0.8) {
            r.Hang.setPower(-0.5);        }

        else if (gamepad1.right_trigger >= 0.8){
            r.Hang.setPower(-1.0);        }

        else {r.Hang.setPower(0.0);}

    }  //end of loop
}   //end of extends BIGRoverTeleOpMethods




