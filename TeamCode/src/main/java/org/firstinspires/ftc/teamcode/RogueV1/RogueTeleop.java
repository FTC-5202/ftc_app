package org.firstinspires.ftc.teamcode.RogueV1;

import android.os.SystemClock;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by samue on 11/7/2016.
 */
@TeleOp(name="RogueTeleop", group="Rogue")
@Disabled
public class RogueTeleop extends RogueTeleopMethods {

    public enum RADSTATE{
        WAITING,
        MOVINGHOOD,
        FIRING,
        RELOADING
    }

    final int FORWARD = 1;
    final int BACKWARD = -1;
    final int LEFT_DRIVE = 0;
    final int RIGHT_DRIVE = 1;
    double[] drivePow;
    int botDirection = FORWARD;

    boolean ltPressed = false;
    double radPower = 0.0;
    boolean right_bumperpressed = false;
    boolean sweeper_state = false;
    boolean lbPressed = false;
    boolean right_trigger_pressed = false;
    boolean x2Pressed =false;
    boolean b2Pressed = false;
    boolean y2Pressed = false;
    boolean rArmOut = false;
    boolean lArmOut = false;
    boolean leftArmOut = false;
    boolean rightArmOut = false;
    boolean leftArmMoving = false;
    boolean rightArmMoving = false;
    double lArmPos = r.LARMIN;
    double rArmPos = r.RARMIN;
    double lPlatePos = r.LPLATEIN;
    double rPlatePos = r.RPLATEIN;
    double releasePos = r.FORK_HOME;
    double hoodPos = r.HOOD_DOWN;
    double lSpookPos = r.LSPOOK_UP;
    double rSpookPos = r.RSPOOK_UP;
    boolean a2Pressed = false;
    boolean rb2Pressed = false;
    boolean lb2Pressed = false;
    boolean rt2Pressed = false;
    boolean isShootingMode = true;

    long rArmBackTime = SystemClock.elapsedRealtime();
    long lArmBackTime = SystemClock.elapsedRealtime();
    long fireStartTime = SystemClock.elapsedRealtime();



    public RADSTATE radState = RADSTATE.RELOADING;

    public void loop(){
        long currentTime = SystemClock.elapsedRealtime();

        if(gamepad2.right_trigger >= 0.5 && !rt2Pressed){
            rt2Pressed = true;
        }
        if(gamepad2.right_trigger < 0.5 && rt2Pressed){
            isShootingMode = !isShootingMode;
            sweeper_state = false;
            r.sweeper.setPower(0);
            rt2Pressed = false;
        }

        if(isShootingMode){
            //SWEEPER\\

            if (gamepad1.right_bumper) {
                right_bumperpressed = true;
            }
            if (right_bumperpressed == true) {
                if (gamepad1.right_bumper == false) {
                    if (sweeper_state == true) {
                        r.sweeper.setPower(0);
                        sweeper_state = false;
                    }
                    else if (sweeper_state == false) {
                        r.sweeper.setPower(1.0);
                        sweeper_state = true;
                    }
                    right_bumperpressed = false;
                }
            }


            if (gamepad1.right_trigger >= 0.5) {
                right_trigger_pressed = true;
            }
            if (right_trigger_pressed == true) {
                if (gamepad1.right_trigger < 0.5) {
                    if (sweeper_state == true) {
                        r.sweeper.setPower(0);
                        sweeper_state = false;

                    } else if (sweeper_state == false) {
                        r.sweeper.setPower(-1.0);
                        sweeper_state = true;
                    }
                    right_trigger_pressed = false;
                }
            }

            //SHOOTING TRIGGERS\\
            if(gamepad1.left_trigger >= 0.5 && (radState == RADSTATE.WAITING) && !ltPressed){
                ltPressed = true;
            }

            if(ltPressed && gamepad1.left_trigger < 0.5){
                fireStartTime = SystemClock.elapsedRealtime();
                radState = RADSTATE.MOVINGHOOD;
                ltPressed = false;
            }

            //FORK CONTROL\\
            r.moveMotor(r.fork, -power(gamepad2.right_stick_y));
        }
        else{
            double forkPow = 0;
            if(gamepad1.left_trigger >= 0.4){
                forkPow = power(gamepad1.left_trigger);
            }
            else if(gamepad1.right_trigger >= 0.4){
                forkPow = -power(gamepad1.right_trigger);
            }
            else {
                forkPow = 0;
            }
            r.moveMotor(r.fork, forkPow);
        }


        if (gamepad1.left_bumper && !lbPressed) {
            lbPressed = true;
        }


        if(lbPressed && !gamepad1.left_bumper){
            botDirection = (botDirection == FORWARD) ? BACKWARD : FORWARD;
            lbPressed = false;
        }





        if(radState == RADSTATE.WAITING && !r.touchSensor.isPressed()){
            hoodPos = r.HOOD_UP;
            radState = RADSTATE.RELOADING;
        }
        if(radState == RADSTATE.MOVINGHOOD){
            hoodPos = r.HOOD_UP;
           // telemetry.addData("TIME LEFT: ", (1000 - (currentTime - fireStartTime)));
            if((currentTime - fireStartTime) > 320){
                radState = RADSTATE.FIRING;
            }
        }
        if(radState == RADSTATE.FIRING){
            radPower = r.RADAPULT_ON;
            if(!r.touchSensor.isPressed()){
                radState = RADSTATE.RELOADING;
            }
        }
        if(radState == RADSTATE.RELOADING){
            hoodPos = r.HOOD_UP;
            radPower = r.RADAPULT_ON;
            if(r.touchSensor.isPressed()){
                radState = RADSTATE.WAITING;
                hoodPos = r.HOOD_DOWN;
                radPower = r.RADAPULT_OFF;
            }
    }



        if (gamepad2.a && !a2Pressed) {
            a2Pressed = true;
        }

        if(a2Pressed && !gamepad2.a){
            releasePos = (releasePos != r.FORK_OPEN) ? r.FORK_OPEN : r.FORK_HOME;
            a2Pressed = false;
        }

        if(gamepad2.x && !x2Pressed){
            x2Pressed = true;
        }

        if(gamepad2.y && !y2Pressed){
            y2Pressed = true;
        }

        if(gamepad2.b && !b2Pressed){
            b2Pressed = true;
        }

        if(!gamepad2.y && y2Pressed){
            leftArmOut = !leftArmOut;
            rightArmOut = !rightArmOut;
            rightArmMoving = true;
            leftArmMoving = true;

            rArmBackTime = SystemClock.elapsedRealtime();
            lArmBackTime = SystemClock.elapsedRealtime();

            y2Pressed = false;

        }
        if(!gamepad2.x && x2Pressed){
            lArmBackTime = SystemClock.elapsedRealtime();
            leftArmOut = !leftArmOut;
            leftArmMoving = true;
            x2Pressed = false;
        }
        if(!gamepad2.b && b2Pressed){
            rArmBackTime = SystemClock.elapsedRealtime();
            rightArmOut = !rightArmOut;
            rightArmMoving = true;
            b2Pressed = false;
        }
        if(leftArmMoving)        {    //finish
            double oldPos = lArmPos;
            lArmPos += (leftArmOut) ? (r.CHANGERATEJH) : (-r.CHANGERATEJH);
            double recipLArm = 1.0 - lArmPos;
            if(!leftArmOut){
                //lPlatePos = r.LPLATEIN - recipLArm;
                lPlatePos = recipLArm - r.LOUT_OFFSET;
                if((currentTime - lArmBackTime) < 500){
                    lArmPos = oldPos;
                }
            }
            else if(lArmPos <= r.LTRIGGER){

                lPlatePos = recipLArm - r.LOUT_OFFSET;
            }
            else {
                double recipLCenter = recipLArm + r.LCENTER_OFFSET;
                lPlatePos = recipLCenter;
            }

            if(leftArmOut && lArmPos >= r.LARMOUT){
                lArmPos = r.LARMOUT;
                lPlatePos = r.LPLATEOUT;
                leftArmMoving = false;
            }
            if(!leftArmOut && lArmPos <= r.LARMIN){
                lArmPos = r.LARMIN;
                lPlatePos = r.LPLATEIN;
                leftArmMoving = false;
            }


        }

        if(rightArmMoving)        {    //finish
            double roldPos = rArmPos;
            rArmPos += (!rightArmOut) ? (r.CHANGERATEJH) : (-r.CHANGERATEJH);
            double recipRArm = 1.0 - rArmPos;
            if(!rightArmOut){
                rPlatePos = recipRArm + r.ROUT_OFFSET;
                if((currentTime - rArmBackTime) < 500){
                    rArmPos = roldPos;
                }
            }
            else if(rArmPos >= r.RTRIGGER){
                rPlatePos = recipRArm + r.ROUT_OFFSET;
            }
            else {
                double recipRCenter = recipRArm - r.RCENTER_OFFSET;
                rPlatePos = recipRCenter;
            }

            if(rightArmOut && rArmPos <= r.RARMOUT){
                rArmPos = r.RARMOUT;
                rPlatePos = r.RPLATEOUT;
                rightArmMoving = false;
            }
            if(!rightArmOut && rArmPos >= r.RARMIN){
                rArmPos = r.RARMIN;
                rPlatePos = r.RPLATEIN;
                rightArmMoving = false;
            }


        }


        if(gamepad2.right_bumper && !rb2Pressed){
            rb2Pressed = true;
        }

        if(!gamepad2.right_bumper && rb2Pressed){
            rSpookPos = (rSpookPos == r.RSPOOK_UP) ? r.RSPOOK_DOWN : r.RSPOOK_UP;
            rb2Pressed = false;
        }

        if(gamepad2.left_bumper && !lb2Pressed){
            lb2Pressed = true;
        }

        if(!gamepad2.left_bumper && lb2Pressed){
            lSpookPos = (lSpookPos == r.LSPOOK_UP) ? r.LSPOOK_DOWN : r.LSPOOK_UP;
            lb2Pressed = false;
        }





        drivePow = updateDrivetrainStatus(botDirection);


        r.moveMotor(r.lift, -power(gamepad2.left_stick_y));
        r.moveMotor(r.radapult, radPower);
        r.moveServo(r.leftArm, lArmPos);
        r.moveServo(r.rightArm, rArmPos);
        r.moveServo(r.leftPlate, lPlatePos);
        r.moveServo(r.rightPlate, rPlatePos);
        r.moveServo(r.release, releasePos);
        r.moveServo(r.hood, hoodPos);
        r.moveServo(r.lSpooker, lSpookPos);
        r.moveServo(r.rSpooker, rSpookPos);
        r.moveDrivetrain(drivePow[LEFT_DRIVE], drivePow[RIGHT_DRIVE]);
        telemetry.addData("Sweeper State", sweeper_state);
        telemetry.addData("LArmPos: ", lArmPos);
        telemetry.addData("LPlatePos: ", lPlatePos);
        telemetry.addData("RArmPos: ", rArmPos);
        telemetry.addData("RPlatePos: ", rPlatePos);
        telemetry.addData("TIME: ", (currentTime - rArmBackTime));
        telemetry.addData("LSPOOKER: ", lSpookPos + " #SPOOKY");
        telemetry.addData("RSPOOKER: ", rSpookPos + " #SPOOKME");
        updateTelemetry(telemetry);


    }


    private double[] updateDrivetrainStatus(int direction){
        double lPow = 0.0;
        double rPow = 0.0;

        if(direction == FORWARD){
            lPow = power(-gamepad1.left_stick_y);
            rPow = power(-gamepad1.right_stick_y);
        }
        else if(direction == BACKWARD){
            rPow = power(-gamepad1.left_stick_y);
            lPow = power(-gamepad1.right_stick_y);
        }

        lPow *= direction;
        rPow *= direction;

        double[] holder = {lPow, rPow};
        return holder;
    }
}
