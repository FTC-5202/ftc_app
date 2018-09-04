package org.firstinspires.ftc.teamcode.Dummy;

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
public class HardwareDummyMecanumNEW
{
    /* Public OpMode members. */
    public DcMotor  BLMotor     = null;
    public DcMotor  BRMotor     = null;
    public DcMotor  FRMotor     = null;
    public DcMotor  FLMotor     = null;
    public DcMotor  TgrLift1     = null;
    public Servo    DoublClampR  = null;
    public Servo    DoublClampL  =null;
    public Servo    DoublSweepR  =null;
    public Servo    DoublSweepL  =null;
   // public Servo    GRservo     =null;
  //  public Servo    RTservo     =null;

    /* Local OpMode members. */
    HardwareMap hwMap  = null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareDummyMecanumNEW() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // save reference to HW Map
        hwMap = ahwMap;

        // Define and Initialize Motors
        BLMotor   = hwMap.dcMotor.get("BL");
        BRMotor  = hwMap.dcMotor.get("BR");
        FRMotor  = hwMap.dcMotor.get("FR");
        FLMotor  = hwMap.dcMotor.get("FL");
        TgrLift1  = hwMap.dcMotor.get("TgrLift1");
        //LYmotor = hwMap.dcMotor.get("LYmotor");

        FRMotor.setDirection(DcMotor.Direction.REVERSE);
        BRMotor.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        BLMotor.setPower(0);
        BRMotor.setPower(0);
        FRMotor.setPower(0);
        FLMotor.setPower(0);
        TgrLift1.setPower(0);
       // LXmotor.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        BLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // Define and initialize ALL installed servos.
        DoublClampR = hwMap.servo.get("DoublClamp1");
        DoublClampL = hwMap.servo.get("DoublClamp2");
        DoublSweepR = hwMap.servo.get("DoublSweepR");
        DoublSweepL = hwMap.servo.get("DoublSweepL");
       // GRservo = hwMap.servo.get("GRservo");
       // RTservo = hwMap.servo.get("RTservo");

//        relicLif.setPosition(RELIC_HOME);
       // arm.setPosition(ARM_HOME);
       // claw.setPosition(CLAW_HOME);
       // GRservo.setPosition(0.0);//may need to change
       // RTservo.setPosition(0.5);//may need to change
        DoublClampR.setPosition(0.0);
        DoublClampL.setPosition(0.0);
        DoublSweepR.setPosition(0.0);
        DoublSweepL.setPosition(0.0);





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
