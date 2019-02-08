package org.firstinspires.ftc.teamcode.Rover.LitttleRover;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.Rover.LitttleRover.RoverAutoMethods;

import java.util.ArrayList;
import java.util.List;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.vuforia.CameraDevice;

import static com.qualcomm.hardware.bosch.BNO055IMU.AngleUnit.RADIANS;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;
import static org.firstinspires.ftc.teamcode.Rover.LitttleRover.RoverAutoMethods.Direction.LEFT;
import static org.firstinspires.ftc.teamcode.Rover.LitttleRover.RoverAutoMethods.Direction.RIGHT;

//import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.

@Autonomous(name="RoverAuto", group ="Rover")
@Disabled
public class RoverAuto extends RoverAutoMethods {
    //    VuforiaLocalizer vuforia;
//    boolean Target1;
//    boolean targetSpotted = false;
    BNO055IMU imu;
    Orientation lastAngles = new Orientation();
    double globalAngle, power = .15, correction, robotHeading, robotX, robotY, robotToWall, distanceToTravel;
    double distanceTraveled = 0;
    // public Rev2mDistanceSensor sensorRange;
    //DistanceSensor sensorRangeR;
    //ColorSensor sensorColor;

//    @Override

    //    public void runOpMode() throws InterruptedException{
    public void runOpMode() {
        telemetry.addLine("Initializing...");
        telemetry.update();

        boolean Target1;
        boolean targetSpotted = false;
        boolean drivetrainStopped = false;
        boolean turnL = false;
        double globalAngle = 0, power = .15, correction, robotHeading, robotX, robotY, robotToWall;
        double distanceTraveled = 0;
        double distanceToTravel = 0;

        setupAll();
//        DcMotor FLBLMotor = null;
//        DcMotor FRBRMotor = null;

        //HardwareDummyRover r = new HardwareDummyRover();
        /*
         * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
         * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
         * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
         * web site at https://developer.vuforia.com/license-manager.
         *
         * Vuforia license keys are always 380 characters long, and look as if they contain mostly
         * random data. As an example, here is a example of a fragment of a valid key:
         *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
         * Once you've obtained a license key, copy the string from the Vuforia web site
         * and paste it in to your code on the next line, between the double quotes.
         */
        final String VUFORIA_KEY = "AXLb9ZD/////AAAAGc+ylHTIf0+aorS8rw6aoBRMAiybD7XCkifjVKb1gFrWJ+pZOL6huLnful+ArD+R2XN3/ZGcwQl6+4jsRj2e3Y82Sm/yTgANmCQEqhIqLjfWNePdOqmT0apncNRVE8YfklK+VRNs976s0xR2rEPIl4tNaYoGOHqaJl8JfIrZ5CjIIxKV55C5PUdzzgAxR3NS8hR7wGu5H0rX1of4shVf1Nncn3WNKTrsOU//PPBjgE79RIN3G5aUC54lMNkzMfaJ2FwAfTXoMbSUygQiGu1Sh0UizQpgjqzPH8gIt6v8qt542i4Pk5T+gbrculkfzvFhzMQu81EyP2v4TfCNmCsSrFQdRl2Z7pTbWddtn5//e6Px";

        // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
        // We will define some constants and conversions here
        final float mmPerInch = 25.4f;
        final float mmFTCFieldWidth = (12 * 6) * mmPerInch;       // the width of the FTC field (from the center point to the outer panels)
        final float mmTargetHeight = (6) * mmPerInch;          // the height of the center of the target image above the floor

        // Select which camera you want use.  The FRONT camera is the one on the same side as the screen.
        // Valid choices are:  BACK or FRONT
        final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = FRONT;

        OpenGLMatrix lastLocation = null;
        boolean targetVisible = false;

        /**
         * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
         * localization engine.
         */


        //r.init(hardwareMap);
        telemetry.addLine("Initializing...");
        telemetry.update();
//            setupAll();
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = RADIANS;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        imu = hardwareMap.get(BNO055IMU.class, "imu");

        imu.initialize(parameters);

        telemetry.addData("Mode", "calibrating...");
        telemetry.update();

        // make sure the imu gyro is calibrated before continuing.
        while (!isStopRequested() && !imu.isGyroCalibrated()) {
            sleep(50);
            idle();
        }

        final float hsvValues[] = {0F, 0F, 0F};


        telemetry.addData("Mode", "waiting for start");
        telemetry.addData("imu calib status", imu.getCalibrationStatus().toString());
        telemetry.update();

        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameterless constructor instead (commented out below).
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parametersVU = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parametersVU.vuforiaLicenseKey = VUFORIA_KEY;
        parametersVU.cameraDirection = CAMERA_CHOICE;

        //  Instantiate the Vuforia engine
        r.vuforia = ClassFactory.getInstance().createVuforia(parametersVU);

        // Load the data sets that for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.
        VuforiaTrackables targetsRoverRuckus = r.vuforia.loadTrackablesFromAsset("RoverRuckus");
        VuforiaTrackable blueRover = targetsRoverRuckus.get(0);
        blueRover.setName("Blue-Rover");
        VuforiaTrackable redFootprint = targetsRoverRuckus.get(1);
        redFootprint.setName("Red-Footprint");
        VuforiaTrackable frontCraters = targetsRoverRuckus.get(2);
        frontCraters.setName("Front-Craters");
        VuforiaTrackable backSpace = targetsRoverRuckus.get(3);
        backSpace.setName("Back-Space");

        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsRoverRuckus);

        /**
         * In order for localization to work, we need to tell the system where each target is on the field, and
         * where the phone resides on the robot.  These specifications are in the form of <em>transformation matrices.</em>
         * Transformation matrices are a central, important concept in the math here involved in localization.
         * See <a href="https://en.wikipedia.org/wiki/Transformation_matrix">Transformation Matrix</a>
         * for detailed information. Commonly, you'll encounter transformation matrices as instances
         * of the {@link OpenGLMatrix} class.
         *
         * If you are standing in the Red Alliance Station looking towards the center of the field,
         *     - The X axis runs from your left to the right. (positive from the center to the right)
         *     - The Y axis runs from the Red Alliance Station towards the other side of the field
         *       where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
         *     - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)
         *
         * This Rover Ruckus sample places a specific target in the middle of each perimeter wall.
         *
         * Before being transformed, each target image is conceptually located at the origin of the field's
         *  coordinate system (the center of the field), facing up.
         */

        /**
         * To place the BlueRover target in the middle of the blue perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Then, we translate it along the Y axis to the blue perimeter wall.
         */
        OpenGLMatrix blueRoverLocationOnField = OpenGLMatrix
                .translation(0, mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0));
        blueRover.setLocation(blueRoverLocationOnField);

        /**
         * To place the RedFootprint target in the middle of the red perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Second, we rotate it 180 around the field's Z axis so the image is flat against the red perimeter wall
         *   and facing inwards to the center of the field.
         * - Then, we translate it along the negative Y axis to the red perimeter wall.
         */
        OpenGLMatrix redFootprintLocationOnField = OpenGLMatrix
                .translation(0, -mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180));
        redFootprint.setLocation(redFootprintLocationOnField);

        /**
         * To place the FrontCraters target in the middle of the front perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Second, we rotate it 90 around the field's Z axis so the image is flat against the front wall
         *   and facing inwards to the center of the field.
         * - Then, we translate it along the negative X axis to the front perimeter wall.
         */
        OpenGLMatrix frontCratersLocationOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90));
        frontCraters.setLocation(frontCratersLocationOnField);

        /**
         * To place the BackSpace target in the middle of the back perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Second, we rotate it -90 around the field's Z axis so the image is flat against the back wall
         *   and facing inwards to the center of the field.
         * - Then, we translate it along the X axis to the back perimeter wall.
         */
        OpenGLMatrix backSpaceLocationOnField = OpenGLMatrix
                .translation(mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90));
        backSpace.setLocation(backSpaceLocationOnField);

        /**
         * Create a transformation matrix describing where the phone is on the robot.
         *
         * The coordinate frame for the robot looks the same as the field.
         * The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
         * Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
         *
         * The phone starts out lying flat, with the screen facing Up and with the physical top of the phone
         * pointing to the LEFT side of the Robot.  It's very important when you test this code that the top of the
         * camera is pointing to the left side of the  robot.  The rotation angles don't work if you flip the phone.
         *
         * If using the rear (High Res) camera:
         * We need to rotate the camera around it's long axis to bring the rear camera forward.
         * This requires a negative 90 degree rotation on the Y axis
         *
         * If using the Front (Low Res) camera
         * We need to rotate the camera around it's long axis to bring the FRONT camera forward.
         * This requires a Positive 90 degree rotation on the Y axis
         *
         * Next, translate the camera lens to where it is on the robot.
         * In this example, it is centered (left to right), but 110 mm forward of the middle of the robot, and 200 mm above ground level.
         */

        final int CAMERA_FORWARD_DISPLACEMENT = 125;   // eg: Camera is 110 mm in front of robot center
        final int CAMERA_VERTICAL_DISPLACEMENT = 114;   // eg: Camera is 200 mm above ground
        final int CAMERA_LEFT_DISPLACEMENT = 64;     // eg: Camera is ON the robot's center line

        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES,
                        CAMERA_CHOICE == FRONT ? 90 : -90, 0, 0));


        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(phoneLocationOnRobot, parametersVU.cameraDirection);
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

        //Color.RGBToHSV(r.sensorColor.red() * 8, r.sensorColor.green() * 8, r.sensorColor.blue() * 8, hsvValues);

        /** Start tracking the data sets we care about. */
        targetsRoverRuckus.activate();
        while (opModeIsActive()) {

            correction = checkDirection();

            telemetry.addData("1 imu heading", lastAngles.firstAngle);
            telemetry.addData("2 global heading", globalAngle);
            telemetry.addData("3 correction", correction);
            telemetry.update();


            while (!targetSpotted && opModeIsActive()) {

                r.moveDrivetrain(-0.3, 0.3);
//                    r.FRBRMotor.setPower(0.3);
//                    r.FLBLMotor.setPower(0.3);
                sleep(250);
                r.stopDrivetrain();
//                    r.FRBRMotor.setPower(0);
//                    r.FLBLMotor.setPower(0);
                sleep(250);


                // targetVisible = false;

                for (VuforiaTrackable trackable : allTrackables) {
                    if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                        telemetry.addData("Visible Target", trackable.getName());
                        targetVisible = true;

                        // getUpdatedRobotLocation() will return null if no new information is available since
                        // the last time that call was made, or if the trackable is not currently visible.
                        OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                        if (robotLocationTransform != null) {
                            lastLocation = robotLocationTransform;
                        }
                        break;
                    }
                }

                // Provide feedback as to where the robot is located (if we know).
                if (targetVisible && !targetSpotted) {
                    // express position (translation) of robot in inches.
                    VectorF translation = lastLocation.getTranslation();
                    telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                            translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

                    // express the rotation of the robot in degrees.
                    Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
                    telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
                    telemetry.update();
                    r.stopDrivetrain();
//                        r.FRBRMotor.setPower(0);
//                        r.FLBLMotor.setPower(0);
                    targetSpotted = true;
                    robotHeading = Math.toRadians(rotation.thirdAngle);
                    robotX = translation.get(0) / mmPerInch;
                    robotY = translation.get(1) / mmPerInch;
                    robotToWall = (72 - robotY - 10); //was -12/-6
                    distanceToTravel = robotToWall / Math.abs(Math.cos(robotHeading - Math.PI / 2));
                    telemetry.addData("distance", distanceToTravel);
                    telemetry.addData("robotToWall", robotToWall);
                    telemetry.addData("robotHeading", robotHeading);
                    telemetry.addData("Cos", Math.cos(robotHeading));
                    telemetry.update();
                    //sleep(5000);

                    //} else {
                    //telemetry.addData("Visible Target", "none");
                }

            }

            getAngle();
            if (targetSpotted && opModeIsActive()) {
                if (distanceTraveled < distanceToTravel) {

//                        correction = checkDirection();
                    r.FLBLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    r.FRBRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    r.FLBLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    r.FRBRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);//checkpoint


                    telemetry.addLine("IMU Straight");//IMU works, however, out previous config was set up with four individual motors, so I need to do some tweaking to the commands
                    telemetry.update();

                    //moveBot(distanceToTravel, FORWARD, 0.5);
                    moveStraight(distanceToTravel, FORWARD, 0.5);
                    //r.moveDrivetrain(0.5, 0.5);
                    sleep(1000);
                    distanceTraveled = distanceToTravel;
                    eTurnBot(38, RIGHT, 0.3, -0.3);
                    sleep(200);
                    telemetry.addData("DistanceTraveled", distanceTraveled);
                    telemetry.addData("Correction", r.correction);
                    telemetry.update();
                }
                telemetry.addData("range", String.format("%.01f in", r.sensorRange.getDistance(DistanceUnit.INCH)));
                //telemetry.addData("rangeR", String.format("%.01f in", sensorRangeR.getDistance(DistanceUnit.INCH)));
                telemetry.update();


                //this stuff needs to be inside a distance loop so the robot stops when it gets to the depot
                //turn left
                //eTurnBot(30, RIGHT, 0.3, -0.3);//change to turn using IMU when working
                //sleep(250);
                if (r.sensorRange.getDistance(DistanceUnit.INCH) > 3) {
                    r.FLBLMotor.setPower(0.5 + (r.sensorRange.getDistance(DistanceUnit.INCH)/90)); //no idea if this will work
                    r.FRBRMotor.setPower(0.5 - (r.sensorRange.getDistance(DistanceUnit.INCH)/90));
                }
                else if (r.sensorRange.getDistance(DistanceUnit.INCH) < 3) {
                    r.FLBLMotor.setPower(0.5 - (r.sensorRange.getDistance(DistanceUnit.INCH)/90));
                    r.FRBRMotor.setPower(0.5 + (r.sensorRange.getDistance(DistanceUnit.INCH)/90));
                }
                else {//don't know if this is needed
                    r.FLBLMotor.setPower(0.5);
                    r.FRBRMotor.setPower(0.5);
                }

             /*   if (!turnL) { //*
                    r.FLBLMotor.setPower(-0.25);
                    r.FRBRMotor.setPower(0.25);
                    turnL = true;

                }
                if (r.sensorRange.getDistance(DistanceUnit.INCH) <= 8 && !drivetrainStopped) {
                    r.stopDrivetrain();
                    drivetrainStopped = true;
                    sleep(500);
                    if (r.sensorRange.getDistance(DistanceUnit.INCH) <= 8 && drivetrainStopped) {
                        r.moveDrivetrain(0.5, 0.5);
                        //if (sensorColor.blue() >= 1) {
                        //r.stopDrivetrain();

                        if (r.sensorRange.getDistance(DistanceUnit.INCH) >= 8 && drivetrainStopped) {
                            r.FLBLMotor.setPower(0.5);
                            r.FRBRMotor.setPower(0.4);

                        }

                        else {
                            r.FLBLMotor.setPower(0.4);
                            r.FRBRMotor.setPower(0.5);
                        }*/


                    }


                }

                //FLBL.setPower(power);
                //FRBR.setPower(power);


                //FLBL.setPower(0);
                //FRBR.setPower(0);

            }   //while opModeIsActive
        }    // end RunOpMode
/*
    private void resetAngle()
    {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS); //was DEGREES

        globalAngle = 0;
    }
*/
        /**
         * Get current cumulative angle rotation from last reset.
         * @return Angle in degrees. + = left, - = right.
         */
    /*public double getAngle()
    {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS); //was DEGREES

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -3.1)//convert to radians - 180 degrees
            deltaAngle += 6.3;//convert to radians - 360 degrees
        else if (deltaAngle > 3.1)//convert to radians - 180 degrees
            deltaAngle -= 6.3;//convert to radians - 360 degrees

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }


    /**
     * See if we are moving in a straight line and if not return a power correction value.
     * @return Power adjustment, + is adjust left - is adjust right.
     */
/*    public double checkDirection()
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

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS); //was DEGREES

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -3.1)//convert to radians - 180 degrees
            deltaAngle += 6.3;//convert to radians - 360 degrees
        else if (deltaAngle > 3.1)//convert to radians - 180 degrees
            deltaAngle -= 6.3;//convert to radians - 360 degrees

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }

    public void moveStraight(double distance, int direction, double power, EndStatus status) {
        int target = inches_to_ticks(distance);
        //int startPos = r.LFMotor.getCurrentPosition();
        //int currentPos = r.LFMotor.getCurrentPosition();
        int startPos = r.FLBLMotor.getCurrentPosition();
        int currentPos = r.FLBLMotor.getCurrentPosition();
        //int startPos = FRBRMotor.getCurrentPosition();
        //int currentPos = FRBRMotor.getCurrentPosition();
        //Strangely enough, we had to reduce the right side power by 80% to get the robot to run straight
        //Unfortunately, we do not yet understand why
        r.moveDrivetrain(power * direction, power * direction);

        // while(Math.abs(currentPos - startPos) < target) currentPos = r.LFMotor.getCurrentPosition();
        while (Math.abs(currentPos - startPos) < target) {
            currentPos = r.FLBLMotor.getCurrentPosition();
            correction = checkDirection();
            r.FLBLMotor.setPower(power - correction); // was0.15 REVERSE in Hr.FRBRMotor.setPower(power - correction); // was0.15

        }

        if (status == EndStatus.STOP) {
            r.stopDrivetrain();
            //To get repeatability in our auto programs, we found that we need to reset the encoders after every run of moveBot method
            r.FLBLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            r.FRBRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            r.FLBLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            r.FRBRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);        }
    }



    //The instance of moveBot below is the most commonly used method
    public void moveStraight(double distance, int direction, double power){
        moveStraight(distance, direction, power, RoverAutoMethods.EndStatus.STOP);    }

    /**
     * Rotate left or right the number of degrees. Does not support turning more than 180 degrees.
     * @param degrees Degrees to turn, + is left - is right
     */
/*    private void rotate(int degrees, double power)
    {
        double  leftPower, rightPower;

        // restart imu movement tracking.
        resetAngle();

        // getAngle() returns + when rotating counter clockwise (left) and - when rotating
        // clockwise (right).

        if (degrees < 0)
        {   // turn right.
            leftPower = power;
            rightPower = -power;
        }
        else if (degrees > 0)
        {   // turn left.
            leftPower = -power;
            rightPower = power;
        }
        else return;

        // set power to rotate.
        r.FLBLMotor.setPower(-leftPower); //REVERSE in HW
        r.FRBRMotor.setPower(leftPower);


        // rotate until turn is completed.
        if (degrees < 0)
        {
            // On right turn we have to get off zero first.
            while (opModeIsActive() && getAngle() == 0) {}

            while (opModeIsActive() && getAngle() > degrees) {}
        }
        else    // left turn.
            while (opModeIsActive() && getAngle() < degrees) {}

        // turn the motors off.
        r.FLBLMotor.setPower(0);
        r.FRBRMotor.setPower(0);

        // wait for rotation to stop.
        sleep(1000);

        // reset angle tracking on new heading.
        resetAngle();
    }  //end rotate
*/
  //  } //end LinearOpMode

//}
