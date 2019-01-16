package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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
        r.Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.Sweeper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.FLMotor.setDirection(DcMotor.Direction.REVERSE);
        r.BLMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setupServos() {
      // r.tfd.setPosition();
       r.pin.setPosition(1.0);
       r.flap.setPosition(0.5);
       r.tfd.setPosition(0.6);
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






