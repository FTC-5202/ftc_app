package org.firstinspires.ftc.teamcode.Dummy;

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
public class HardwareDummyMecanum
{
    /* Public OpMode members. */
    public DcMotor  FLBLMotor     = null;
    public DcMotor  FRBRMotor     = null;
    //public DcMotor  RelicMotor     = null;
    public DcMotor  TgrLift1     = null;
    public DcMotor    RelicY       = null;
    public Servo    DoublClamp1  = null;
    public Servo    DoublRelicY  = null;

   // public Servo    GRservo     =null;
  //  public Servo    RTservo     =null;

    /* Local OpMode members. */
    HardwareMap hwMap  = null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareDummyMecanum() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // save reference to HW Map
        hwMap = ahwMap;

        // Define and Initialize Motors
        FLBLMotor   = hwMap.dcMotor.get("FLBL");
        FRBRMotor  = hwMap.dcMotor.get("FRBR");
        //RelicMotor = hwMap.dcMotor.get("RelicLift");
        TgrLift1  = hwMap.dcMotor.get("TgrLift1");
        RelicY = hwMap.dcMotor.get("RelicY");
        RelicY.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //LYmotor = hwMap.dcMotor.get("LYmotor");

        FRBRMotor.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        FLBLMotor.setPower(0);
        FRBRMotor.setPower(0);
        //RelicMotor = hwMap.dcMotor.get("RelicLift");
        TgrLift1.setPower(0);
       // LXmotor.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        FLBLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FRBRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // Define and initialize ALL installed servos.
        DoublClamp1 = hwMap.servo.get("DoublClamp1");
        DoublRelicY = hwMap.servo.get("DoublRelicY");
       // GRservo = hwMap.servo.get("GRservo");
       // RTservo = hwMap.servo.get("RTservo");

//        relicLif.setPosition(RELIC_HOME);
       // arm.setPosition(ARM_HOME);
       // claw.setPosition(CLAW_HOME);
       // GRservo.setPosition(0.0);//may need to change
       // RTservo.setPosition(0.5);//may need to change
        DoublClamp1.setPosition(1.0);
        DoublRelicY.setPosition(0.0);




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
