package org.firstinspires.ftc.teamcode.RogueV1;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannelController;

/**
 * Created by samue on 11/7/2016.
 */
public class RogueTeleopMethods extends OpMode {

    RogueElectrical r = new RogueElectrical();


    public RogueTeleopMethods(){

    }

    public void loop(){

    }

    public void setupMotors(){
        r.leftSide = hardwareMap.dcMotor.get("left");
        r.rightSide = hardwareMap.dcMotor.get("right");
        r.radapult = hardwareMap.dcMotor.get("radapult");
        r.sweeper = hardwareMap.dcMotor.get("sweeper");
        r.lift = hardwareMap.dcMotor.get("lift");
        r.fork = hardwareMap.dcMotor.get("fork");
        r.moveDrivetrain(0,0);
        r.radapult.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.leftSide.setDirection(DcMotor.Direction.REVERSE);
        r.rightSide.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setupServos(){
        r.leftArm = hardwareMap.servo.get("larm");
        r.rightArm = hardwareMap.servo.get("rarm");
        r.leftPlate = hardwareMap.servo.get("lplate");
        r.rightPlate = hardwareMap.servo.get("rplate");
        r.release = hardwareMap.servo.get("release");
        r.moveServo(r.release, r.FORK_HOME);
        r.lightArm = hardwareMap.servo.get("lightArm");
        r.hood = hardwareMap.servo.get("hood");
        r.lSpooker = hardwareMap.servo.get("lSpook");
        r.rSpooker = hardwareMap.servo.get("rSpook");
        r.moveServo(r.lSpooker, r.LSPOOK_UP);
        r.moveServo(r.rSpooker, r.RSPOOK_UP);
        r.moveServo(r.hood, r.HOOD_DOWN);
        r.moveServo(r.lightArm, r.LIGHTARM_UP);
        r.moveServo(r.leftArm, r.LARMIN);
        r.moveServo(r.rightArm, r.RARMIN);
        r.moveServo(r.leftPlate, r.LPLATEIN);
        r.moveServo(r.rightPlate, r.RPLATEIN);
    }

    public void setupSensors(){
        r.touchSensor = hardwareMap.touchSensor.get("touch");
        r.ada = hardwareMap.colorSensor.get("ada");
        r.mr = hardwareMap.colorSensor.get("mr");
        r.sonar = hardwareMap.ultrasonicSensor.get("sonar");

        r.cdim = hardwareMap.deviceInterfaceModule.get("dim");
        r.cdim.setDigitalChannelMode(r.LED_CHANNEL, DigitalChannelController.Mode.OUTPUT);
    }

    public void setupAll(){
        setupMotors();
        setupServos();
        setupSensors();
        telemetry.addData("Rad motor: ", r.radapult);
    }

    public void init(){
        setupAll();
    }

    public double power(double val){
        int sgn = (val >= 0) ? 1 : -1;
        double holder = Math.pow(val, 2.0) * sgn;
        return holder;
    }


}
