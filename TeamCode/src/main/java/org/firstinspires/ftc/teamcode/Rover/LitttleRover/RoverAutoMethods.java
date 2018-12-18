package org.firstinspires.ftc.teamcode.Rover.LitttleRover;

/**
 * Created by Matthew on 10/20/18
 */

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

//TH3OAutoMethods is used to define basic movements for the robot in auto

public class RoverAutoMethods extends LinearOpMode {
    //Creates version of hardware class called "r" for robot
    RoverElectricalM r = new RoverElectricalM();

    public RoverAutoMethods() {  }

    public void runOpMode() throws InterruptedException {
    }

    //BNO055IMU imu;


    //Orientation lastAngles = new Orientation();


    public void setupMotors() {
        //This section sets up the brake behavior so when the power is off, motors hold current position
        //This is important for Lift1 and Lift2 so when we lift the glyph, it will stay in the air without running the motors
//10/27 commented out the line just below

        r.moveDrivetrain(0,0);
        r.FRBRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.FLBLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //r.RarmLif.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //r.LarmLif.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       // r.FLBLMotor.setDirection(DcMotor.Direction.REVERSE);
        //r.BLMotor.setDirection(DcMotor.Direction.REVERSE);


    }

    public void setupServos() {
       // r.moveServo(r.LSrot, 0.7);
        //r.moveServo(r.LSgrab, 0.2);
       // r.moveServo(r.RSrot, 0.3); //was 0.15
       // r.moveServo(r.RSgrab, 0.65); //was 7
       // r.moveServo(r.RSLif, 0);
       // r.moveServo(r.LSLif, 1.0);
    }

   // public void setupSensors() {

   // }

    //setupAll will be used in all of our auto programs

    public void setupAll() {
        r.init(hardwareMap);
        setupMotors();
        setupServos();
       // setupSensors();
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

    //moveBot is used by all of our auto programs; distance(inches), direction, and power are passed in from our autonomous program
    //Distance is converted to ticks, then all motors are rotated until the target ticks are reached
    /*public void moveBot(double distance, int direction, double power, EndStatus status) {
        r.FLBLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.FRBRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.FLBLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        r.FRBRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        int target = inches_to_ticks(distance);
        //int startPos = r.LFMotor.getCurrentPosition();
        //int currentPos = r.LFMotor.getCurrentPosition();
        int startPos = r.FLBLMotor.getCurrentPosition();
        int currentPos = r.FLBLMotor.getCurrentPosition();
        //int startPos = r.FRBRMotor.getCurrentPosition();
        //int currentPos = r.FRBRMotor.getCurrentPosition();
        //Strangely enough, we had to reduce the right side power by 40% to get the robot to run straight
        //Unfortunately, we do not yet understand why
        r.moveDrivetrain(power * direction, 0.6 * power * direction);

        // while(Math.abs(currentPos - startPos) < target) currentPos = r.LFMotor.getCurrentPosition();
        while (Math.abs(currentPos - startPos) < target)
            currentPos = r.FLBLMotor.getCurrentPosition();

        if (status == EndStatus.STOP) {
            r.stopDrivetrain();
            //To get repeatability in our auto programs, we found that we need to reset the encoders after every run of moveBot method
            //r.LFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //r.RFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //r.LFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //r.RFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);        }
        }

    }
    */

    public void moveBot(double distance, int direction, double power, EndStatus status) {
        int target = inches_to_ticks(distance);
        int startPos = r.FLBLMotor.getCurrentPosition();
        int currentPos = r.FLBLMotor.getCurrentPosition();

        while (Math.abs(currentPos - startPos) < target) currentPos = r.FLBLMotor.getCurrentPosition();

        if (status == EndStatus.STOP) {
            r.stopDrivetrain();
            r.FLBLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            r.FLBLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

        //The instance of moveBot below is the most commonly used method
        public void moveBot(double distance, int direction, double power){
            moveBot(distance, direction, power, RoverAutoMethods.EndStatus.STOP);    }

        public void easeMoveBot(double distance, int direction, double power){
            if(distance <= 3)   moveBot(distance, direction, power);
            else {
                double eDist = distance * 0.6;
                moveBot(eDist, direction, power, RoverAutoMethods.EndStatus.COAST);
                moveBot((distance - eDist), direction, 0.25);        }
        }


    public void moveStraight(double distance, int direction, double power, EndStatus status) {
        r.FLBLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.FRBRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.FLBLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        r.FRBRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        int target = inches_to_ticks(distance);
        telemetry.addData("distanceinTicks", target);
        //int startPos = r.LFMotor.getCurrentPosition();
        //int currentPos = r.LFMotor.getCurrentPosition();
        int startPos = r.FLBLMotor.getCurrentPosition();
        int currentPos = r.FLBLMotor.getCurrentPosition();
        telemetry.addData("Start Pos", startPos);
        telemetry.addData("Current Pos", currentPos);
        telemetry.update();
        //int startPos = r.FRBRMotor.getCurrentPosition();
        //int currentPos = r.FRBRMotor.getCurrentPosition();
        //Strangely enough, we had to reduce the right side power by 80% to get the robot to run straight
        //Unfortunately, we do not yet understand why
       // r.moveDrivetrain(power * direction, power * direction);

        // while(Math.abs(currentPos - startPos) < target) currentPos = r.LFMotor.getCurrentPosition();
        while (Math.abs(currentPos - startPos) < target) {
            currentPos = r.FLBLMotor.getCurrentPosition();
            r.correction = checkDirection();
            r.FLBLMotor.setPower(power - r.correction);// was0.15, was -correction
            r.FRBRMotor.setPower(.6 * power - r.correction); // was0.15, was -correction


        }

        if (status == EndStatus.STOP) {
            r.stopDrivetrain();
            //To get repeatability in our auto programs, we found that we need to reset the encoders after every run of moveBot method
            //r.FLBLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //r.FRBRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //r.FLBLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //r.FRBRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            //return target/TICKS_PER_INCH;
        }



    //The instance of moveBot below is the most commonly used method
    public void moveStraight(double distance, int direction, double power){
        moveStraight(distance, direction, power, RoverAutoMethods.EndStatus.STOP);    }



        public enum Direction {LEFT, RIGHT}

        //eTurnBot is used by all of our auto programs; angle(degrees), direction, and power are passed in from our autonomous program
        //We are currently not using an IMU for turning in autonomous.
        //Rather, we track the angle turned by tracking the distance traveled by the front wheel on the outside of turn.

        public void eTurnBot ( double degrees, Direction dir, double lPow, double rPow, EndStatus status){
            //"encoderMotor" is the motor that we track, we use LFMotor when powered
            DcMotor encoderMotor = (lPow == 0.0) ? r.FRBRMotor : r.FLBLMotor;
            //DcMotor encoderMotor = (lPow == 0.0) ? r.BRMotor : r.BLMotor;
            //Get the starting position for "encoderMotor"
            int startPos = encoderMotor.getCurrentPosition();
            //Angle is converted to radians
            double dToR = (Math.PI / 180.0);
            double rad = degrees * dToR;
            //"HALFWHEELBASE" is one half the width of our robot
            double HALFWHEELBASE = 8.5; //inches
            //If one side is not powered, the other side must travel twice as far to complete the turn
            int coeff = (lPow == 0.0 || rPow == 0.0) ? 2 : 1;
            //Calculate length of turn and convert to encoder ticks
            double sLength = rad * (coeff * HALFWHEELBASE);
            double target = inches_to_ticks(sLength);
            //Initialize sgn
            double sgn = 0;

            //Depending on desired turn dir and motor used, motor power is set in the section below
            if (dir == Direction.RIGHT) {
                if (encoderMotor.equals(r.FLBLMotor)) sgn = 1; //wasFLBL
                else sgn = -1;
                lPow = Math.abs(lPow) * -1;
                rPow = Math.abs(rPow);
            } else if (dir == Direction.LEFT) {
                if (encoderMotor.equals(r.FLBLMotor)) sgn = -1; //wasFL
                else sgn = 1;
                lPow = Math.abs(lPow);
                rPow = Math.abs(rPow) * -1;
            } else {
            }

            //"moveDrivetrain" refers to TH3OElectrical using the powers calculated above
            r.moveDrivetrain(lPow, rPow);

            //Telemetry statments used for debugging
            if (sgn == -1) {
                for (int currentPos = encoderMotor.getCurrentPosition(); (currentPos - startPos) < target; currentPos = encoderMotor.getCurrentPosition()) {
                    telemetry.addData("Distance left: ", target - (currentPos - startPos));
                    updateTelemetry(telemetry);
                }
            } else if (sgn == 1) { //was-1
                target *= -1.0;
                for (int currentPos = encoderMotor.getCurrentPosition(); (currentPos - startPos) > target; currentPos = encoderMotor.getCurrentPosition()) {
                    telemetry.addData("Distance left: ", target - (currentPos - startPos));
                    updateTelemetry(telemetry);
                }
            } else {
                telemetry.addData("BAD:", " FAIL");
                updateTelemetry(telemetry);
                sleep(3000);
            }

            //To get repeatability in our auto programs, we found that we need to reset the encoders after every run of moveBot method
            if (status == EndStatus.STOP) {
                r.stopDrivetrain();
                r.FLBLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                r.FRBRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                r.FLBLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                r.FRBRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }

        }
        //The instance of eTurnBot below is the most commonly used method
        public void eTurnBot ( double degrees, Direction dir,double lPow, double rPow){
            eTurnBot(degrees, dir, lPow, rPow, EndStatus.STOP);
        }


    public double checkDirection()
    {
        // The gain value determines how sensitive the correction is to direction changes.
        // You will have to experiment with your robot to get small smooth direction changes
        // to stay on a straight line.
        double correction, angle, gain = .10;

        angle = getAngle();

        if (angle == 0)
            correction = 0;             // no adjustment.
        else
            correction = -angle;        // reverse sign of angle for correction.

        correction = correction * gain;

        return correction;
    }

    public double getAngle(){
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = r.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS); //was DEGREES

        double deltaAngle = angles.firstAngle - r.lastAngles.firstAngle;

        if (deltaAngle < -3.1)//convert to radians - 180 degrees
            deltaAngle += 6.3;//convert to radians - 360 degrees
        else if (deltaAngle > 3.1)//convert to radians - 180 degrees
            deltaAngle -= 6.3;//convert to radians - 360 degrees

        r.globalAngle += deltaAngle;

        r.lastAngles = angles;

        return r.globalAngle;
    }


        //The section below initializes Vuforia for use in autonomous to read the cryptographs.
 /*       public RelicRecoveryVuMark getVuMark () {
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
        }*/
    }





