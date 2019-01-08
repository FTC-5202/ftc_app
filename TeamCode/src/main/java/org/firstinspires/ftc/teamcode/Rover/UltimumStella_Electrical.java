package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class UltimumStella_Electrical {

    DcMotor BLMotor = null;
    DcMotor BRMotor = null;
    DcMotor FLMotor = null;
    DcMotor FRMotor = null;
    DcMotor Arm     = null;
    DcMotor Sweeper = null;
    DcMotor Hang = null;

    Servo tfd;
    Servo pin;
    Servo flap;

    HardwareMap hardwareMap = null;
    private ElapsedTime period = new ElapsedTime();


    public UltimumStella_Electrical (){

    }

    public void init(HardwareMap ahwMap) {

        BLMotor = hardwareMap.dcMotor.get("BL");
        BRMotor = hardwareMap.dcMotor.get("BR");
        FLMotor = hardwareMap.dcMotor.get("FL");
        FRMotor = hardwareMap.dcMotor.get("FR");
        Arm     = hardwareMap.dcMotor.get("arm");
        Sweeper = hardwareMap.dcMotor.get("sweep");
        Hang = hardwareMap.dcMotor.get("Lift");
        tfd = hardwareMap.servo.get("tfd");
        pin = hardwareMap.servo.get("pin");
        flap = hardwareMap.servo.get("flap");


        BLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Sweeper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Hang.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FLMotor.setDirection(DcMotor.Direction.REVERSE);
        BLMotor.setDirection(DcMotor.Direction.REVERSE);

        BLMotor.setPower(0);
        FLMotor.setPower(0);
        BRMotor.setPower(0);
        FRMotor.setPower(0);
        Arm.setPower(0);
        Sweeper.setPower(0);
        Hang.setPower(0);

    }

    public void moveLeftSide(double lPow){
        lPow = (lPow >= 1.0) ? 1.0 : lPow;
        lPow = (lPow <= -1.0) ? -1.0 : lPow;
        BLMotor.setPower(lPow);
        FLMotor.setPower(lPow);    }

    //This makes sure the right side motor powers passed in are within range, and sets default right side motor power
    public void moveRightSide(double rPow){
        rPow = (rPow >= 1.0) ? 1.0 : rPow;
        rPow = (rPow <= -1.0) ? -1.0 : rPow;
        BRMotor.setPower(rPow);
        FRMotor.setPower(rPow);    }

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
