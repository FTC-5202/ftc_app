package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MechanumWheelIsolationTest extends OpMode {

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    int brm = 1;
    int blm = 1;
    int flm = 1;
    int frm = 1;

    boolean aPressed = false;
    boolean bPressed = false;
    boolean xPressed = false;
    boolean yPressed = false;



    @Override
    public void init() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
    }

    @Override
    public void loop() {

        if(gamepad1.a && !aPressed){
            aPressed = true;
        }

        if(!gamepad1.a && aPressed){ // a : back left = blm
            if(blm == 1){
                blm = 0;
            }
            else if(blm == 0){
                blm = -1;
            }
            else {
                blm = 1;
            }
            aPressed = false;
        }

        if(gamepad1.b && !bPressed){
            bPressed = true;
        }

        if(!gamepad1.b && bPressed){ // b : back right = brm
            if(brm == 1){
                brm = 0;
            }
            else if(brm == 0){
                brm = -1;
            }
            else {
                brm = 1;
            }
            bPressed = false;
        }

        if(gamepad1.x && !xPressed){
            xPressed = true;
        }

        if(!gamepad1.x && xPressed){ // x : front left = flm
            if(flm == 1){
                flm = 0;
            }
            else if(flm == 0){
                flm = -1;
            }
            else {
                flm = 1;
            }
            xPressed = false;
        }

        if(gamepad1.y && !yPressed){
            yPressed = true;
        }

        if(!gamepad1.y && yPressed){ // y : front right = frm
            if(frm == 1){
                frm = 0;
            }
            else if(frm == 0){
                frm = -1;
            }
            else {
                frm = 1;
            }
            yPressed = false;
        }

        double power = gamepad1.left_stick_y;
        backRight.setPower(power * brm);
        backLeft.setPower(power * blm);
        frontLeft.setPower(power * flm);
        frontRight.setPower(power * frm);

        telemetry.addLine("Back Left Motor Multiplier = " + blm);
        telemetry.addLine("Back Right Motor Multiplier = " + brm);
        telemetry.addLine("Front Left Motor Multiplier = " + flm);
        telemetry.addLine("Front Right Motor Multiplier = " + frm);
        telemetry.update();
    }
}
