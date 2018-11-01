package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Hannah on 11/16/2017.
 */

public class BIGRoverTeleOpMethods extends OpMode {



    BIGRoverElectrical r = new BIGRoverElectrical();

    public BIGRoverTeleOpMethods() {

    }

    public void loop() {

    }
    public void init(){

    }

    public void setupMotors() {

        r.moveDrivetrain(0,0);
        r.FRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.BRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.FLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.BLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.RarmLif.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.LarmLif.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.FLMotor.setDirection(DcMotor.Direction.REVERSE);
        r.BLMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setupServos() {
        r.moveServo(r.LSrot, 0.0);
        r.moveServo(r.LSgrab, 0.8);
        r.moveServo(r.RSrot, 0.15);
        r.moveServo(r.RSgrab, 0.8);
    }

        //public void setupSensors() {

    //}
    public void setupAll(){
        r.init(hardwareMap);
        setupMotors();
        setupServos();
        //setupSensors();
    }



}





