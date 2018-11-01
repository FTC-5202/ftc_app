package org.firstinspires.ftc.teamcode.TH3O;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import static com.qualcomm.hardware.bosch.BNO055IMU.AngleUnit.RADIANS;

/**
 * Created by Hannah on 10/26/2017.
 */

public class Th3OElectricalM {

    public boolean bLedOn = true;

    public DcMotor LRMotor = null;
    public DcMotor RRMotor = null;
    public DcMotor LFMotor = null;
    public DcMotor RFMotor = null;
    public DcMotor TgrLift1 = null;
    public DcMotor TgrLift2 = null;
    public DcMotor RelicEx = null;



    public Servo wopFL = null;
    public Servo wopFR = null;
    public Servo wopBL = null;
    public Servo wopBR = null;
    public Servo Rarm = null;
    public Servo Larm = null;
    public Servo Tgr = null;
    public Servo TgrU = null;
    public Servo TgrB = null;
    public Servo RotateRel = null;
    public Servo GrabRel = null;

    ColorSensor RS1;
    ColorSensor RS2;
    ColorSensor LS2;
    ColorSensor RVcl;

    /*public final static double ARM_HOME = 0.3;
    public final static double CLAW_HOME = 0.2;
    public final static double RELIC_HOME = 0.0;
    public final static double ARM_MIN_RANGE = 0.0;
    public final static double ARM_MAX_RANGE = 0.90;
    public final static double CLAW_MIN_RANGE = 0.2;
    public final static double CLAW_MAX_RANGE = 0.7;*/

    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    public Th3OElectricalM() {
    }

    public void init(HardwareMap ahwMap) {

        hwMap = ahwMap;

        LRMotor = hwMap.dcMotor.get("BL");
        RRMotor = hwMap.dcMotor.get("BR");
        LFMotor = hwMap.dcMotor.get("FL");
        RFMotor = hwMap.dcMotor.get("FR");

        TgrLift1 = hwMap.dcMotor.get("TgrLift1");
        TgrLift2 = hwMap.dcMotor.get("TgrLift2");
        RelicEx = hwMap.dcMotor.get("RelicEx");

        LFMotor.setDirection(DcMotor.Direction.REVERSE);
        LRMotor.setDirection(DcMotor.Direction.REVERSE);

        LRMotor.setPower(0);
        RRMotor.setPower(0);
        LFMotor.setPower(0);
        RFMotor.setPower(0);
        TgrLift1.setPower(0);
        TgrLift2.setPower(0);
        RelicEx.setPower(0);

        LRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //was RUN_WITHOUT_ENCODERS
        RRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Tgr = hwMap.servo.get("T_Grabber");
        TgrU = hwMap.servo.get("T_Grabber-Upper");
        TgrB = hwMap.servo.get("T_Grabber-Lower");
        wopFL = hwMap.servo.get("wopFL");
        wopFR = hwMap.servo.get("wopFR");
        wopBL = hwMap.servo.get("wopBL");
        wopBR = hwMap.servo.get("wopBR");
        Rarm = hwMap.servo.get("Rarm");
        Larm = hwMap.servo.get("Larm");
        RotateRel = hwMap.servo.get("RotateRel");
        GrabRel = hwMap.servo.get("GrabRel");

    }
    public static void moveMotor(DcMotor motor, double pwr){
        if(pwr >= 1.0){
            motor.setPower(1.0);
        }
        else if(pwr <= -1.0){
            motor.setPower(-1.0);
        }
        else {
            motor.setPower(pwr);
        }
    }

    public static void moveServo(Servo servo, double pos){
        if(pos >= 1.0){
            servo.setPosition(1.0);
        }
        else if (pos <= 0.0){
            servo.setPosition(0.0);
        }
        else {
            servo.setPosition(pos);
        }
    }

    public void moveLeftSide(double lPow){
        lPow = (lPow >= 1.0) ? 1.0 : lPow;
        lPow = (lPow <= -1.0) ? -1.0 : lPow;
        LRMotor.setPower(lPow);
        LFMotor.setPower(lPow);
    }

    public void moveRightSide(double rPow){
        rPow = (rPow >= 1.0) ? 1.0 : rPow;
        rPow = (rPow <= -1.0) ? -1.0 : rPow;
        RRMotor.setPower(rPow);
        RFMotor.setPower(rPow);
    }

    public void moveDrivetrain(double lPow, double rPow){
        moveLeftSide(lPow);
        moveRightSide(rPow);
    }

    public void stopDrivetrain(){
        moveDrivetrain(0,0);
    }


    public void waitForTick(long periodMs) throws InterruptedException {

        long remaining = periodMs - (long) period.milliseconds();

        if (remaining > 0)
            Thread.sleep(remaining);

        period.reset();

    }
    public double power(double val){
        int sgn = (val >= 0) ? 1 : -1;
        double holder = Math.pow(val, 2.0) * sgn;
        return holder;
    }
}


