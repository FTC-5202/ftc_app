package org.firstinspires.ftc.teamcode.OldMisc;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a K9 robot.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left rear drive motor:    "lr"
 * Motor channel:  Right drive motor:        "rr"
 * Motor channel:  Left front drive motor:   "lf"
 * Motor channel:  Right front motor:        "rf"
 * Servo channel:  Servo to raise/lower arm: "arm"
 * Servo channel:  Servo to open/close claw: "claw"
 *
 * Note: the configuration of the servos is such that:
 *   As the arm servo approaches 0, the arm position moves up (away from the floor).
 *   As the claw servo approaches 0, the claw opens up (drops the game element).
 */
public class HardwareTheoMecanum
{
    /* Public OpMode members. */
    public DcMotor  BLMotor     = null;
    public DcMotor  BRMotor     = null;
    public DcMotor  FLMotor     = null;
    public DcMotor  FRMotor     = null;
    public DcMotor  FgLift      = null;
    public Servo    Fg          =null;
    public Servo    wopFL       =null;
    public Servo    wopFR       =null;
    public Servo    wopBL       =null;
    public Servo    wopBR       =null;
    public Servo    Rarm        =null;
    public Servo    Larm        =null;
    public Servo    RYservo     =null;
    public Servo    RXservo     =null;
    public Servo    RCservo     =null;

    //public ColorSensor LS1      =null;
    public ColorSensor RS1      =null;
    public ColorSensor LS2      =null;
    public ColorSensor RS2      =null;
    public ColorSensor RVcl    = null;
    public boolean bLedOn = true;
    public final static double ARM_HOME = 0.0;
    public final static double CLAW_HOME = 0.2;
    //   public final static double RELIC_HOME = 0.0;
    public final static double ARM_MIN_RANGE  = 0.0;
    public final static double ARM_MAX_RANGE  = 0.90;
    public final static double CLAW_MIN_RANGE  = 0.20;
    public final static double CLAW_MAX_RANGE  = 0.7;

    /* Local OpMode members. */
    HardwareMap hwMap  = null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareTheoMecanum() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // save reference to HW Map
        hwMap = ahwMap;

        // Define and Initialize Motors
        BLMotor   = hwMap.dcMotor.get("BL");
        BRMotor  = hwMap.dcMotor.get("BR");
        FLMotor   = hwMap.dcMotor.get("FL");
        FRMotor  = hwMap.dcMotor.get("FR");
        FgLift   = hwMap.dcMotor.get("TgrLift1");

        FLMotor.setDirection(DcMotor.Direction.REVERSE);
        BLMotor.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        BLMotor.setPower(0);
        BRMotor.setPower(0);
        FLMotor.setPower(0);
        FRMotor.setPower(0);
        FgLift.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        BLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // Define and initialize ALL installed servos.
        Fg = hwMap.servo.get("T_Grabber");
        //RYservo = hwMap.servo.get("GRservo");
        RXservo = hwMap.servo.get("GrabRel");
        RCservo = hwMap.servo.get("RotateRel");
        wopFL = hwMap.servo.get("wopFL");
        wopFR = hwMap.servo.get("wopFR");
        wopBL = hwMap.servo.get("wopBL");
        wopBR = hwMap.servo.get("wopBR");
        Rarm = hwMap.servo.get("Rarm");
        Larm = hwMap.servo.get("Larm");

        RS1 = hwMap.get(ColorSensor.class, "rs1");
        LS2 = hwMap.get(ColorSensor.class, "ls2");
        RS2 = hwMap.get(ColorSensor.class, "rs2");
        RVcl = hwMap.get(ColorSensor.class, "rvCS");


        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

//        relicLif.setPosition(RELIC_HOME);
       // arm.setPosition(ARM_HOME);
       // claw.setPosition(CLAW_HOME);

        Fg.setPosition(0.35);
        Rarm.setPosition(0.9);
        Larm.setPosition(0.2);
        wopFL.setPosition(0.3);
        wopFR.setPosition(0.47);
        wopBL.setPosition(0.4);
        wopBR.setPosition(0.25);
       // GRservo.setPosition(0.0);//may need to change
       // RTservo.setPosition(0.5);//may need to change
        RXservo.setPosition(0.5);
        RCservo.setPosition(0.2);





    }



    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     * @throws InterruptedException
     */
   /* public void waitForTick(long periodMs)  throws InterruptedException {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset the cycle clock for the next pass.
        period.reset();


    }
    */
}
