package org.firstinspires.ftc.teamcode.TH3O;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Hannah on 10/26/2017.
 */

public class Th3OElectrical {

    /*This section sets up our motors, servos, sensors, and boolean value(s).*/
    //Turn LED on RevColor Sensor on
    public boolean bLedOn = true;
    //the following four motors are drive motors for mecanum wheels.
    public DcMotor LRMotor = null;
    public DcMotor RRMotor = null;
    public DcMotor LFMotor = null;
    public DcMotor RFMotor = null;
    //Lift1 and Lift2 are for glyph lifts; Lift1 is always closer to phone
    //Our convention is that anything with a 1 is closer to phone, 2 is farthest away
    public DcMotor Lift1 = null;
    public DcMotor Lift2 = null;
    //RelicEx moves the the extending slide for relic arm
    public DcMotor RelicEx = null;
    //extending autonomous arm that moves out w a color sensor to read the jewels
    public Servo Rarm = null;
    /*The following is for our paint rollers which are using two different types of servos.
   CRServos are continuous rotation servos to spin the paint rollers and pull the glyph in or push out
   the Servos pivot the paint roller arms allowing them to move in and out, and grab or release glyphs*/


    public CRServo Rprspin1 = null;
    public CRServo Lprspin1 = null;
    public CRServo Rprspin2 = null;
    public CRServo Lprspin2 = null;
    public Servo Rprgrab1 = null;
    public Servo Lprgrab1 = null;
    public Servo Rprgrab2 = null;
    public Servo Lprgrab2 = null;

    public ColorSensor RVc2;

    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    public Th3OElectrical() {
    }

    //The following section sets up the Motor and Servo names in the Configuration on the phones

    public void init(HardwareMap ahwMap) {

        hwMap = ahwMap;

        LRMotor = hwMap.dcMotor.get("BL");
        RRMotor = hwMap.dcMotor.get("BR");
        LFMotor = hwMap.dcMotor.get("FL");
        RFMotor = hwMap.dcMotor.get("FR");

        Lift1 = hwMap.dcMotor.get("TgrLift1");
        Lift2 = hwMap.dcMotor.get("TgrLift2");
        RelicEx = hwMap.dcMotor.get("RelicEx");

        Rarm = hwMap.servo.get("Rarm");
        Rprspin1 = hwMap.crservo.get("PRRtRoller");
        Lprspin1 = hwMap.crservo.get("PRLtRoller");
        Rprspin2 = hwMap.crservo.get("PRRtRoller2");
        Lprspin2 = hwMap.crservo.get("PRLtRoller2");
        Rprgrab1 = hwMap.servo.get("PRRtPiv");
        Lprgrab1 = hwMap.servo.get("PRLtPiv");
        Rprgrab2 = hwMap.servo.get("PRRtpiv2");
        Lprgrab2 = hwMap.servo.get("PRLtpiv2");

        //Change motors on left side to run in reverse

        LFMotor.setDirection(DcMotor.Direction.REVERSE);
        LRMotor.setDirection(DcMotor.Direction.REVERSE);

        //The following sets the initial power of all motors to zero

        LRMotor.setPower(0);
        RRMotor.setPower(0);
        LFMotor.setPower(0);
        RFMotor.setPower(0);
        Lift1.setPower(0);
        Lift2.setPower(0);
        RelicEx.setPower(0);

        //This sets up all our motors in order to use encoders and track how far our robot moves in auto

        LRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //was RUN_WITHOUT_ENCODERS
        RRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
    //This makes sure the motor powers passed in are within range, and sets default motor power
    public static void moveMotor(DcMotor motor, double pwr){
        if(pwr >= 1.0)          motor.setPower(1.0);
        else if(pwr <= -1.0)    motor.setPower(-1.0);
        else                    motor.setPower(pwr);    }

    //This makes sure the servo positions passed in are within range, and sets default servo position
    public static void moveServo(Servo servo, double pos){
        if(pos >= 1.0)          servo.setPosition(1.0);
        else if (pos <= 0.0)    servo.setPosition(0.0);
        else                    servo.setPosition(pos);    }

    //This makes sure the left side motor powers passed in are within range, and sets default left side motor power
    public void moveLeftSide(double lPow){
        lPow = (lPow >= 1.0) ? 1.0 : lPow;
        lPow = (lPow <= -1.0) ? -1.0 : lPow;
        LRMotor.setPower(lPow);
        LFMotor.setPower(lPow);    }

    //This makes sure the right side motor powers passed in are within range, and sets default right side motor power
    public void moveRightSide(double rPow){
        rPow = (rPow >= 1.0) ? 1.0 : rPow;
        rPow = (rPow <= -1.0) ? -1.0 : rPow;
        RRMotor.setPower(rPow);
        RFMotor.setPower(rPow);    }

    //Sets up method to move both motor sides.
    public void moveDrivetrain(double lPow, double rPow){
        moveLeftSide(lPow);
        moveRightSide(rPow);    }

    //Establishes a stop method for drivetrain, which refers to above method
    public void stopDrivetrain(){moveDrivetrain(0,0);}

    public void waitForTick(long periodMs) throws InterruptedException {
        long remaining = periodMs - (long) period.milliseconds();
        if (remaining > 0)
            Thread.sleep(remaining);
        period.reset();    }

    //Method to have a integer as a power val for motors.
    public double power(double val){
        int sgn = (val >= 0) ? 1 : -1;
        double holder = Math.pow(val, 2.0) * sgn;
        return holder;
    }
}


