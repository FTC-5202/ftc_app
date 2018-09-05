package org.firstinspires.ftc.teamcode.TH3O;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Hannah on 11/16/2017.
 */

public class Th3OTeleOpMethodsM extends OpMode {



    Th3OElectricalM r = new Th3OElectricalM();

    public Th3OTeleOpMethodsM() {

    }

    public void loop() {

    }
    public void init(){

    }

    public void setupMotors() {

        r.moveDrivetrain(0,0);
        r.LRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.RRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.LFMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.RFMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.LFMotor.setDirection(DcMotor.Direction.REVERSE);
        r.LRMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setupServos() {


        r.moveServo(r.Larm, 0.0);
        r.moveServo(r.Rarm, 0.8);
        r.moveServo(r.Tgr, 0.35);
        r.moveServo(r.TgrU, 0.35);
        r.moveServo(r.TgrB, 0.35);
        r.moveServo(r.wopFL, 0.3);
        r.moveServo(r.wopFR, 0.47);
        r.moveServo(r.wopBL, 0.4);
        r.moveServo(r.wopBR, 0.25);
        r.moveServo(r.RotateRel, 0.15);
        r.moveServo(r.GrabRel, 0.8);
    }

    public void setupSensors() {

        r.RS1 = hardwareMap.colorSensor.get("rs1"); //may need to set up other 3 color sensors?
        r.RS2 = hardwareMap.colorSensor.get("rs2");
        //r.LS1 = hardwareMap.colorSensor.get("ls1");
        r.LS2 = hardwareMap.colorSensor.get("ls2");
        r.RVcl = hardwareMap.colorSensor.get("rvCS");

    }
    public void setupAll(){
        r.init(hardwareMap);
        setupMotors();
        setupServos();
        setupSensors();
    }



}






