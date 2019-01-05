package org.firstinspires.ftc.teamcode.TH3O;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Hannah on 11/16/2017.
 */

public class Th3OTeleOpMethods extends OpMode {


    //Creates version of hardware class called "r" for robot
    public Th3OElectrical r = new Th3OElectrical();

    public Th3OTeleOpMethods() {

    }



    public void loop () {

    }
    public void init(){

    }

    public void setupMotors() {
        //This section sets up the brake behavior so when the power is off, motors hold current position
        //This is important for Lift1 and Lift2 so when we lift the glyph, it will stay in the air without running the motors

        r.moveDrivetrain(0,0);
        r.LRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.RRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.LFMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.RFMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.Lift1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.Lift2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.LFMotor.setDirection(DcMotor.Direction.REVERSE);
        r.LRMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setupServos() {
        //Setting initial positions for auto arm, paint rollers, and paint roller arms


        r.moveServo(r.Rarm, 0.8);
        r.Rprspin1.setPower(0.0);
        r.Lprspin1.setPower(0.0);
        r.Rprspin2.setPower(0.0);
        r.Lprspin2.setPower(0.0);
        r.moveServo(r.Rprgrab1, 0.28);
        r.moveServo(r.Lprgrab1, 0.57);
        r.moveServo(r.Rprgrab2, 0.28);
        r.moveServo(r.Lprgrab2, 0.57);
    }

    public void setupSensors() {
        //The following section sets up the Color Sensor name in the Configuration on the phones
        r.RVc2 = hardwareMap.colorSensor.get("rvCS2");

    }
    //setupAll will be used in all of our tele op programs
    public void setupAll(){
        r.init(hardwareMap);
        setupMotors();
        setupServos();
        setupSensors();
    }



}






