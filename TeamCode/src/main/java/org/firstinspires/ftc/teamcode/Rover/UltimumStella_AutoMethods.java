package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.TH3O.TH3OAutoMethods;

public class UltimumStella_AutoMethods extends LinearOpMode {

    public BIGRoverElectrical r = new BIGRoverElectrical();
    public UltimumStella_AutoMethods() {   }
    public void runOpMode() throws InterruptedException {}

    public void setupMotors() {
        //This section sets up the brake behavior so when the power is off, motors hold current position
        //This is important for Lift1 and Lift2 so when we lift the glyph, it will stay in the air without running the motors
//10/27 commented out the line just below

        r.moveDrivetrain(0, 0);
        r.FRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.BRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.FLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.BLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.RarmLif.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.LarmLif.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.FLMotor.setDirection(DcMotor.Direction.REVERSE);
        r.BLMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setupServos() {
        r.moveServo(r.LSrot, 0.7);
        r.moveServo(r.LSgrab, 0.2);
        r.moveServo(r.RSrot, 0.3); //was 0.15
        r.moveServo(r.RSgrab, 0.65); //was 7
        r.moveServo(r.RSLif, 0);
        r.moveServo(r.LSLif, 1.0);
    }

    public void setupSensors() {

    }

    public void setupAll() {
        r.init(hardwareMap);
        setupMotors();
        setupServos();
        setupSensors();
    }

    /* Ticks per rotation / inches (circumference) per rotation = ticks per inch
    The encoders on our motors have 1120 ticks per revolution
    The diameter of the mecanum wheels is 4 in
    In our drivetrain, we are using 45 to 35 tooth gears configured for a 1.28 gear ratio*/

    final double TICKS_PER_INCH = 1120 / (4 * Math.PI);
    final double WHEEL_GEAR_RATIO = 1.0 / 1.0; //was 1.28/1.0
    //    double globalAngle, correction;
    final int FORWARD = 1; //was -1
    final int BACKWARD = -1; //was 1

    //inches_to_ticks receives a distance in inches and returns the number of ticks
    int inches_to_ticks(double target) {
        return (int) (target * TICKS_PER_INCH * WHEEL_GEAR_RATIO);
    }

    public enum EndStatus {STOP, COAST}

    public void moveBot(double distance, int direction, double power, EndStatus status) {
        int target = inches_to_ticks(distance);
        int startPos = r.FLMotor.getCurrentPosition();
        int currentPos = r.FLMotor.getCurrentPosition();

        while (Math.abs(currentPos - startPos) < target) currentPos = r.FLMotor.getCurrentPosition();

        if (status == EndStatus.STOP) {
            r.stopDrivetrain();
            r.FLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            r.FLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    //The instance of moveBot below is the most commonly used method
    public void moveBot(double distance, int direction, double power){
        moveBot(distance, direction, power, EndStatus.STOP);
    }

    public enum Direction{LEFT, RIGHT}

    //eTurnBot is used by all of our auto programs; angle(degrees), direction, and power are passed in from our autonomous program
    //We are currently not using an IMU for turning in autonomous.
    //Rather, we track the angle turned by tracking the distance traveled by the front wheel on the outside of turn.

    public void eTurnBot(double degrees, Direction dir, double lPow, double rPow, EndStatus status){
        //"encoderMotor" is the motor that we track, we use LFMotor when powered
        DcMotor encoderMotor = (lPow == 0.0) ? r.FRMotor : r.FLMotor;
        //Get the starting position for "encoderMotor"
        int startPos = encoderMotor.getCurrentPosition();
        //Angle is converted to radians
        double dToR = (Math.PI/180.0);
        double rad = degrees * dToR;
        //"HALFWHEELBASE" is one half the width of our robot
        final double HALFWHEELBASE = 8.5; //inches -change
        //If one side is not powered, the other side must travel twice as far to complete the turn
        int coeff = (lPow == 0.0 || rPow == 0.0) ? 2 : 1;
        //Calculate length of turn and convert to encoder ticks
        double sLength = rad * (coeff * HALFWHEELBASE);
        double target = inches_to_ticks(sLength);
        //Initialize sgn
        double sgn = 0;

        //Depending on desired turn dir and motor used, motor power is set in the section below
        if(dir == Direction.RIGHT){
            if(encoderMotor.equals(r.FLMotor))  sgn = 1;
            else sgn = -1;
            lPow = Math.abs(lPow) * -1;
            rPow = Math.abs(rPow);        }

        else if(dir == Direction.LEFT){
            if(encoderMotor.equals(r.FLMotor))  sgn = -1;
            else sgn = 1;
            lPow = Math.abs(lPow);
            rPow = Math.abs(rPow) * -1;        }
        else {        }

        //"moveDrivetrain" refers to TH3OElectrical using the powers calculated above
        r.moveDrivetrain(lPow, rPow);

        //Telemetry statments used for debugging
        if(sgn == -1){
            for(int currentPos = encoderMotor.getCurrentPosition(); (currentPos - startPos) < target; currentPos = encoderMotor.getCurrentPosition()){
                telemetry.addData("Distance left: ", target - (currentPos - startPos));
                updateTelemetry(telemetry);
            }
        }
        else if(sgn == 1){ //was-1
            target *= -1.0;
            for(int currentPos = encoderMotor.getCurrentPosition(); (currentPos - startPos) > target; currentPos = encoderMotor.getCurrentPosition()){
                telemetry.addData("Distance left: ", target - (currentPos - startPos));
                updateTelemetry(telemetry);
            }
        }
        else {
            telemetry.addData("BAD:", " FAIL");
            updateTelemetry(telemetry);
            sleep(3000);
        }

        //To get repeatability in our auto programs, we found that we need to reset the encoders after every run of moveBot method
        if(status == EndStatus.STOP){
            r.stopDrivetrain();
            r.FLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
           // r.RFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            r.FLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
           // r.RFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }
    //The instance of eTurnBot below is the most commonly used method
    public void eTurnBot(double degrees, Direction dir, double lPow, double rPow){
        eTurnBot(degrees, dir, lPow, rPow, EndStatus.STOP);
    }
}
