package org.firstinspires.ftc.teamcode.TH3O;

/**
 * Created by Hannah on 10/31/2017.
 */

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

//TH3OAutoMethods is used to define basic movements for the robot in auto

public class TH3OAutoMethods extends LinearOpMode {
//Creates version of hardware class called "r" for robot
    public Th3OElectrical r = new Th3OElectrical();
    public TH3OAutoMethods() {    }
    public void runOpMode() throws InterruptedException{    }

    public void setupMotors() {
        //This section sets up the brake behavior so when the power is off, motors hold current position
        //This is important for Lift1 and Lift2 so when we lift the glyph, it will stay in the air without running the motors
        r.moveDrivetrain(0,0);
        r.LRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.RRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.LFMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.RFMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.Lift1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.Lift2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void setupServos() {
        //Setting initial positions for auto arm and paint roller arms
        r.moveServo(r.Rarm, 0.9);
        r.moveServo(r.Rprgrab1, 1.0);
        r.moveServo(r.Lprgrab1, 0.0);
        r.moveServo(r.Rprgrab2, 1.0);
        r.moveServo(r.Lprgrab2, 0.0);    }

    public void setupSensors() {
        //The following section sets up the Color Sensor name in the Configuration on the phones
        //We add this line here because the RevColor Sensor is only for auto
        r.RVc2 = hardwareMap.colorSensor.get("rvCS2");    }

    //setupAll will be used in all of our auto programs

    public void setupAll(){
        r.init(hardwareMap);
        setupMotors();
        setupServos();
        setupSensors();    }

    /* Ticks per rotation / inches (circumference) per rotation = ticks per inch
    The encoders on our motors have 1120 ticks per revolution
    The diameter of the mecanum wheels is 4 in
    In our drivetrain, we are using 45 to 35 tooth gears configured for a 1.28 gear ratio*/

    final double TICKS_PER_INCH = 1120 / (4 * Math.PI);
    final double WHEEL_GEAR_RATIO = 0.78/1.0; //was 1.28/1.0
    public final int FORWARD = -1;
    public final int BACKWARD = 1;
    //inches_to_ticks receives a distance in inches and returns the number of ticks
    int inches_to_ticks(double target){
        return (int)(target * TICKS_PER_INCH * WHEEL_GEAR_RATIO);
    }

    public enum EndStatus { STOP, COAST    }

    //moveBot is used by all of our auto programs; distance(inches), direction, and power are passed in from our autonomous program
    //Distance is converted to ticks, then all motors are rotated until the target ticks are reached
    public void moveBot(double distance, int direction, double power, EndStatus status){
        int target = inches_to_ticks(distance);
        int startPos = r.LFMotor.getCurrentPosition();
        int currentPos = r.LFMotor.getCurrentPosition();
        //Strangely enough, we had to reduce the right side power by 80% to get the robot to run straight
        //Unfortunately, we do not yet understand why
        r.moveDrivetrain(power * direction, power * 0.2 * direction);

        while(Math.abs(currentPos - startPos) < target) currentPos = r.LFMotor.getCurrentPosition();

        if(status == EndStatus.STOP){
            r.stopDrivetrain();
            //To get repeatability in our auto programs, we found that we need to reset the encoders after every run of moveBot method
            r.LFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            r.RFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            r.LFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            r.RFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);        }
    }

    //The instance of moveBot below is the most commonly used method
    public void moveBot(double distance, int direction, double power){
        moveBot(distance, direction, power, EndStatus.STOP);    }

    public void easeMoveBot(double distance, int direction, double power){
        if(distance <= 3)   moveBot(distance, direction, power);
        else {
            double eDist = distance * 0.6;
            moveBot(eDist, direction, power, EndStatus.COAST);
            moveBot((distance - eDist), direction, 0.25);        }
    }

    public enum Direction{LEFT, RIGHT}

    //eTurnBot is used by all of our auto programs; angle(degrees), direction, and power are passed in from our autonomous program
    //We are currently not using an IMU for turning in autonomous.
    //Rather, we track the angle turned by tracking the distance traveled by the front wheel on the outside of turn.

    public void eTurnBot(double degrees, Direction dir, double lPow, double rPow, EndStatus status){
        //"encoderMotor" is the motor that we track, we use LFMotor when powered
        DcMotor encoderMotor = (lPow == 0.0) ? r.RFMotor : r.LFMotor;
        //Get the starting position for "encoderMotor"
        int startPos = encoderMotor.getCurrentPosition();
        //Angle is converted to radians
        double dToR = (Math.PI/180.0);
        double rad = degrees * dToR;
        //"HALFWHEELBASE" is one half the width of our robot
        final double HALFWHEELBASE = 8.5; //inches
        //If one side is not powered, the other side must travel twice as far to complete the turn
        int coeff = (lPow == 0.0 || rPow == 0.0) ? 2 : 1;
        //Calculate length of turn and convert to encoder ticks
        double sLength = rad * (coeff * HALFWHEELBASE);
        double target = inches_to_ticks(sLength);
        //Initialize sgn
        double sgn = 0;

        //Depending on desired turn dir and motor used, motor power is set in the section below
        if(dir == Direction.RIGHT){
            if(encoderMotor.equals(r.LFMotor))  sgn = 1;
            else sgn = -1;
            lPow = Math.abs(lPow) * -1;
            rPow = Math.abs(rPow);        }

        else if(dir == Direction.LEFT){
            if(encoderMotor.equals(r.LFMotor))  sgn = -1;
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
            r.LFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            r.RFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            r.LFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            r.RFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }
    //The instance of eTurnBot below is the most commonly used method
    public void eTurnBot(double degrees, Direction dir, double lPow, double rPow){
        eTurnBot(degrees, dir, lPow, rPow, EndStatus.STOP);
    }
    //The section below initializes Vuforia for use in autonomous to read the cryptographs.
    public RelicRecoveryVuMark getVuMark () {
        VuforiaLocalizer vuforia;

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "AXLb9ZD/////AAAAGc+ylHTIf0+aorS8rw6aoBRMAiybD7XCkifjVKb1gFrWJ+pZOL6huLnful+ArD+R2XN3/ZGcwQl6+4jsRj2e3Y82Sm/yTgANmCQEqhIqLjfWNePdOqmT0apncNRVE8YfklK+VRNs976s0xR2rEPIl4tNaYoGOHqaJl8JfIrZ5CjIIxKV55C5PUdzzgAxR3NS8hR7wGu5H0rX1of4shVf1Nncn3WNKTrsOU//PPBjgE79RIN3G5aUC54lMNkzMfaJ2FwAfTXoMbSUygQiGu1Sh0UizQpgjqzPH8gIt6v8qt542i4Pk5T+gbrculkfzvFhzMQu81EyP2v4TfCNmCsSrFQdRl2Z7pTbWddtn5//e6Px";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;//was BACK
        vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        relicTrackables.activate();

        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        return vuMark;
    }
}









