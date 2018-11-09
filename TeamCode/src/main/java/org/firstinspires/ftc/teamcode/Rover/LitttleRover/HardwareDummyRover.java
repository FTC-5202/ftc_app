package org.firstinspires.ftc.teamcode.Rover.LitttleRover;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Rover.LitttleRover.RoverElectricalM;

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
public class HardwareDummyRover extends RoverElectricalM

{
    /* Public OpMode members. */
    public DcMotor  FLBL     = null;
    public DcMotor  FRBR     = null;
//    public Servo    DoublClamp1  = null;
//    public Servo    DoublRelicY  = null;

   // public Servo    GRservo     =null;
  //  public Servo    RTservo     =null;

    /* Local OpMode members. */
    HardwareMap hwMap  = null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareDummyRover() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // save reference to HW Map
        hwMap = ahwMap;

        // Define and Initialize Motors
        FLBL   = hwMap.dcMotor.get("FLBL");
        FRBR  = hwMap.dcMotor.get("FRBR");

        // Set all motors to zero power
        FLBL.setPower(0);
        FRBR.setPower(0);

        FLBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FRBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FLBL.setDirection(DcMotorSimple.Direction.REVERSE);
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
