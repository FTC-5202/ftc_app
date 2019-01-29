package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.vuforia.CameraDevice;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Rover.LitttleRover.RoverAutoMethods;
//import org.firstinspires.ftc.teamcode.TH3O.TH3OAutoMethods;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;
//import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODERS;

public class UltimumStella_AutoMethods extends LinearOpMode {

    public static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    public static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    public static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    public int position = 0;
    public boolean minCheck = false;
    double globalAngle, power = .15, correction, robotHeading, robotX, robotY, robotToWall, distanceToTravel;
    double distanceTraveled = 0;
    //public Rev2mDistanceSensor sensorRange;


    private static final String VUFORIA_KEY = "AXLb9ZD/////AAAAGc+ylHTIf0+aorS8rw6aoBRMAiybD7XCkifjVKb1gFrWJ+pZOL6huLnful+ArD+R2XN3/ZGcwQl6+4jsRj2e3Y82Sm/yTgANmCQEqhIqLjfWNePdOqmT0apncNRVE8YfklK+VRNs976s0xR2rEPIl4tNaYoGOHqaJl8JfIrZ5CjIIxKV55C5PUdzzgAxR3NS8hR7wGu5H0rX1of4shVf1Nncn3WNKTrsOU//PPBjgE79RIN3G5aUC54lMNkzMfaJ2FwAfTXoMbSUygQiGu1Sh0UizQpgjqzPH8gIt6v8qt542i4Pk5T+gbrculkfzvFhzMQu81EyP2v4TfCNmCsSrFQdRl2Z7pTbWddtn5//e6Px\"";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    public TFObjectDetector tfod;

    public BIGRoverElectrical r = new BIGRoverElectrical();

    public UltimumStella_AutoMethods() {
    }

    public void runOpMode() throws InterruptedException {
        boolean Target1;
        boolean targetSpotted = false;
        boolean drivetrainStopped = false;
        boolean turnL = false;
        double globalAngle = 0, power = .15, correction, robotHeading, robotX, robotY, robotToWall;
        double distanceTraveled = 0;
        double distanceToTravel = 0;


        /*initVuforia();
        CameraDevice.getInstance().setFlashTorchMode(true);

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        } */

    }


    public void setupMotors() {
        //This section sets up the brake behavior so when the power is off, motors hold current position
        //This is important for Lift1 and Lift2 so when we lift the glyph, it will stay in the air without running the motors
//10/27 commented out the line just below

        r.moveDrivetrain(0, 0);
        r.FRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.BRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.FLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.BLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       // r.FLMotor.setMode(RUN_USING_ENCODER);
       // r.BLMotor.setMode(RUN_USING_ENCODER);
       // r.FRMotor.setMode(RUN_USING_ENCODER);
       // r.BRMotor.setMode(RUN_USING_ENCODER);
        //r.RarmLif.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       // r.LarmLif.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.FLMotor.setDirection(DcMotor.Direction.REVERSE);
        r.BLMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setupServos() {
      /*  r.moveServo(r.LSrot, 0.65);
        r.moveServo(r.LSgrab, 0.65);
        r.moveServo(r.RSrot, 0.6); //was 0.15
        r.moveServo(r.RSgrab, 0.2); //was 7
        r.moveServo(r.RSLif, 0);
        r.moveServo(r.LSLif, 1.0);
        r.moveServo(r.MinFlap, 0.4);*/
        r.pin.setPosition(1.0);
        r.tfd.setPosition(0.6);

    }

    public void setupSensors() {
//        r.sensorTouch.setMode(DigitalChannel.Mode.INPUT);
       //r.imu.getAngularOrientation();

    }

    public void setupAll() {
        r.init(hardwareMap);
        setupMotors();
        setupServos();
        setupSensors();
        initVuforia();
        CameraDevice.getInstance().setFlashTorchMode(true);

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }
    }

    /* Ticks per rotation / inches (circumference) per rotation = ticks per inch
    The encoders on our motors have 1120 ticks per revolution
    The diameter of the mecanum wheels is 4 in
    In our drivetrain, we are using 45 to 35 tooth gears configured for a 1.28 gear ratio*/

    final double TICKS_PER_INCH = 1120 / (6 * Math.PI);
    final double WHEEL_GEAR_RATIO = 1.0 / 1.0; //was 1.28/1.0
    //    double globalAngle, correction;
    final int FORWARD = -1; //was -1
    final int BACKWARD = 1; //was 1
    final int RIGHT1 = -1;
    final int LEFT1= 1;

    //inches_to_ticks receives a distance in inches and returns the number of ticks
    int inches_to_ticks(double target) {
        return (int) (target * TICKS_PER_INCH * WHEEL_GEAR_RATIO);
    }

    public enum EndStatus {STOP, COAST}

    public void moveBot(double distance, int direction, double power, EndStatus status) {
        int target = inches_to_ticks(distance);
        int startPos = r.BLMotor.getCurrentPosition();
        int currentPos = r.BLMotor.getCurrentPosition();
        r.moveDrivetrain(power * 0.8 * direction, power * 1.0 * direction);// was 0.6 || 1

        while (Math.abs(currentPos - startPos) < target && !isStopRequested())
            currentPos = r.BLMotor.getCurrentPosition();

        if (status == EndStatus.STOP) {
            r.stopDrivetrain();
            r.BLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            r.BLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    //The instance of moveBot below is the most commonly used method
    public void moveBot(double distance, int direction, double power) {
        moveBot(distance, direction, power, EndStatus.STOP);
    }

    public void moveBotcrab(double distance, int direction, double power, EndStatus status) {
        int target = inches_to_ticks(distance);
        int startPos = r.FLMotor.getCurrentPosition();
        int currentpos = r.FLMotor.getCurrentPosition();
        r.moveDriveTrainC(power * direction, power * -direction, power * -direction, power * direction);

        while (Math.abs(currentpos - startPos) < target && !isStopRequested())
            currentpos = r.FLMotor.getCurrentPosition();

        if (status == EndStatus.STOP) {
            r.stopDrivetrainC();
            r.FLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            r.FLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }

    public void moveBotcrab(double distance, int direction, double power) {
        moveBotcrab(distance, direction, power, EndStatus.STOP);
    }

    public enum Direction {LEFT, RIGHT}

    //eTurnBot is used by all of our auto programs; angle(degrees), direction, and power are passed in from our autonomous program
    //We are currently not using an IMU for turning in autonomous.
    //Rather, we track the angle turned by tracking the distance traveled by the front wheel on the outside of turn.

    public void eTurnBot(double degrees, Direction dir, double lPow, double rPow, EndStatus status) {
        //"encoderMotor" is the motor that we track, we use LFMotor when powered
        DcMotor encoderMotor = (lPow == 0.0) ? r.BRMotor : r.BLMotor;
        //Get the starting position for "encoderMotor"
        int startPos = encoderMotor.getCurrentPosition();
        //Angle is converted to radians
        double dToR = (Math.PI / 180.0);
        double rad = degrees * dToR;
        //"HALFWHEELBASE" is one half the width of our robot
        final double HALFWHEELBASE = 8.125; //inches -change
        //If one side is not powered, the other side must travel twice as far to complete the turn
        int coeff = (lPow == 0.0 || rPow == 0.0) ? 2 : 1;
        //Calculate length of turn and convert to encoder ticks
        double sLength = rad * (coeff * HALFWHEELBASE);
        double target = inches_to_ticks(sLength);
        //Initialize sgn
        double sgn = 0;

        //Depending on desired turn dir and motor used, motor power is set in the section below
        if (dir == Direction.RIGHT) {
            if (encoderMotor.equals(r.BLMotor)) sgn = 1;
            else sgn = -1;
            lPow = Math.abs(lPow) * -1;
            rPow = Math.abs(rPow);
        } else if (dir == Direction.LEFT) {
            if (encoderMotor.equals(r.BLMotor)) sgn = -1;
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
            r.BLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            // r.RFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            r.BLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            // r.RFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }

    //The instance of eTurnBot below is the most commonly used method
    public void eTurnBot(double degrees, Direction dir, double lPow, double rPow) {
        eTurnBot(degrees, dir, lPow, rPow, EndStatus.STOP);
    }

    /*public void moveStraight(double distance, int direction, double power, RoverAutoMethods.EndStatus status) {
        r.FLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.FRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.BLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        r.BRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        r.FLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.FRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.BLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        r.BRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        int target = inches_to_ticks(distance);
        telemetry.addData("distanceinTicks", target);
        //int startPos = r.LFMotor.getCurrentPosition();
        //int currentPos = r.LFMotor.getCurrentPosition();
        int startPos = r.BLMotor.getCurrentPosition();
        int currentPos = r.BLMotor.getCurrentPosition();
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
            currentPos = r.BLMotor.getCurrentPosition();
            r.correction = checkDirection();
            r.BLMotor.setPower(power - r.correction);// was0.15, was -correction
            r.BRMotor.setPower(power - r.correction); // was0.15, was -correction


        }

        if (status == RoverAutoMethods.EndStatus.STOP) {
            r.stopDrivetrain();
            //To get repeatability in our auto programs, we found that we need to reset the encoders after every run of moveBot method
            //r.FLBLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //r.FRBRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //r.FLBLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //r.FRBRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        //return target/TICKS_PER_INCH;
    }*/

    double currentHeading;
    double startHeading;

    Orientation angles;
    Acceleration gravity;

   // BNO055IMU imu;



    public void imuTurn (double degrees, double iPowerScalar) { //METHOD using degrees, iPowerScalar to make a clockwiseTurn

        angles = r.imu.getAngularOrientation(); //define angles as imu.getAngularOrientation
        currentHeading = angles.firstAngle; //define currentHeading as angles.firstAngle
        startHeading = angles.firstAngle; //define startHeading as angles.firstAngle
        r.BLMotor.setPower(-iPowerScalar);
        r.FLMotor.setPower(-iPowerScalar);//set left_side Power to the opposite val of iPowerScalar
        r.BRMotor.setPower(iPowerScalar);//set right_side Power to the opposite val of iPowerScalar
        r.FRMotor.setPower(iPowerScalar);
        if (iPowerScalar < 0) {
            final double GOAL_HEADING = startHeading + degrees; //define Goal_Heading
            while (currentHeading < GOAL_HEADING) { //condition: currentHeading must be greater than Goal_Heading for this loop to execute
                telemetry.addData("current heading: ", +currentHeading);
                telemetry.update();
                r.BLMotor.setPower(-powerFuct(iPowerScalar, GOAL_HEADING, currentHeading)); //set left_side Power to inverse of powerFunct using iPowerScalar, Goal_Heading, and currentHeading
                r.FLMotor.setPower(-powerFuct(iPowerScalar, GOAL_HEADING, currentHeading));
                r.BRMotor.setPower(powerFuct(iPowerScalar, GOAL_HEADING, currentHeading)); //set right_side Power to inverse of powerFunct using iPowerScalar, Goal_Heading, and currentHeading
                r.FRMotor.setPower(powerFuct(iPowerScalar, GOAL_HEADING, currentHeading));
                angles = r.imu.getAngularOrientation(); //define angles as imu.getAngularOrientation
                currentHeading = angles.firstAngle; //define currentHeading as angles.firstAngle
            }
        }
        if (iPowerScalar > 0) {
            final double GOAL_HEADING = startHeading - degrees;
            while (currentHeading > GOAL_HEADING) {
                telemetry.addData("current heading: ", +currentHeading);
                telemetry.update();
                r.BLMotor.setPower(-powerFuct(iPowerScalar, GOAL_HEADING, currentHeading));
                r.FLMotor.setPower(-powerFuct(iPowerScalar, GOAL_HEADING, currentHeading));
                r.BRMotor.setPower(powerFuct(iPowerScalar, GOAL_HEADING, currentHeading));
                r.FRMotor.setPower(powerFuct(iPowerScalar, GOAL_HEADING, currentHeading));
                angles = r.imu.getAngularOrientation();
                currentHeading = angles.firstAngle;
            }
        }
        r.BLMotor.setPower(0); //set left_side Power to null
        r.FLMotor.setPower(0);
        r.BRMotor.setPower(0); //set right_side Power to null
        r.FRMotor.setPower(0);
        telemetry.addLine("done");
        telemetry.update();
        sleep(1500); //stop for 0.3 seconds
        angles = r.imu.getAngularOrientation(); //define angles as imu.getAngularOrientation
        currentHeading = angles.firstAngle; //define currentHeading as angles.firstAngle
        /*if (currentHeading < GOAL_HEADING - 5) { //condition: currentHeading must be less than the val of Goal_Heading - 5 in order for this loop to execute
            telemetry.addLine("CORRECTION"); //display that loop is being executed on driver station
            telemetry.update(); //update the line
            sleep(1000); //the increments in which this line will continually be updated
            r.BLMotor.setPower(-0.14); //sets left_side Power to 0.14
            r.FLMotor.setPower(-0.14);
            r.BRMotor.setPower(0.14); //sets right_side Power to 0.14
            r.FRMotor.setPower(0.14);
            while (currentHeading < (GOAL_HEADING - 3)) { //condition: currentHeading must be less than the val of Goal_Heading -3 in order for this loop to execute
                angles = r.imu.getAngularOrientation(); //defines angles as imu.getAngularOrientation
                currentHeading = angles.firstAngle; //defines currentHeading as angles.firstAngle
            }
            r.BLMotor.setPower(0); //sets left_side Power to null
            r.FLMotor.setPower(0);
            r.BRMotor.setPower(0); //sets right_side Power to null
            r.FRMotor.setPower(0);
            telemetry.addLine("DONE"); //displays on driver station that the loop is finished
            telemetry.update(); //update the line displayed
            sleep(1000); //the increments in which this line will continually be updated
        }
        */
    }

    private double powerFuct(double initialPower, double goalAngle, double currentAngle){ //METHOD using initialPower, goalAngle, currentAngle
        final double FLIPPING_POINT = 1.0/3.0; //sets the val of flipping point as the quotient of 1/3
        final double C = 1.0/(60.0); // note -- old = 1 / (FLIPPING_POINT * (goalAngle - startAngle))
        final double EXP = 3; //sets the val of EXP as 3
        final double MIN_POWER = 0.13; //sets the val of Min_Power as 0.13
        final double DIFFERENCE = Math.abs(goalAngle - currentAngle); //sets the val of Difference as the abs val of goalAngle - currentAngle
        double y = MIN_POWER + (initialPower - MIN_POWER)*Math.pow((C*(DIFFERENCE)), EXP); //sets the val of y to Min_Power + the total of initialPower - Min_Power, multiplied by Math.pow((C*(DIFFERENCE)), EXP)
        return (y <= initialPower) ? y : initialPower; //returns the val as y less than or equal to initial Power or y : initialPower
    }


    //The instance of moveBot below is the most commonly used method
    /*public void moveStraight(double distance, int direction, double power) {
        moveStraight(distance, direction, power, RoverAutoMethods.EndStatus.STOP);
    }*/

    public double checkDirection() {
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

    public double getAngle() {
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


    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);

    }

}
