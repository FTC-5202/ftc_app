package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import static com.qualcomm.hardware.bosch.BNO055IMU.AngleUnit.RADIANS;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;

@Autonomous(name="DriveStraightTEST", group ="Rover")
//@Disabled
public class DriveStraightTEST extends RoverAutoMethods {
//    VuforiaLocalizer vuforia;
//    boolean Target1;
//    boolean targetSpotted = false;
//    BNO055IMU imu;
//    Orientation lastAngles = new Orientation();
//    double globalAngle, power = .15, correction, robotHeading, robotX, robotY, robotToWall, distanceToTravel;
//    double distanceTraveled = 0;

    @Override

//    public void runOpMode() throws InterruptedException{
    public void runOpMode() {
//        telemetry.addLine("Initializing...")
//      telemetry.update();

/*        boolean Target1;
        boolean targetSpotted = false;
        double globalAngle = 0, power = .15, correction, robotHeading, robotX, robotY, robotToWall;
        double distanceTraveled = 0;
        double distanceToTravel = 0;
*/
        setupAll();

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
//        final String VUFORIA_KEY = "AXLb9ZD/////AAAAGc+ylHTIf0+aorS8rw6aoBRMAiybD7XCkifjVKb1gFrWJ+pZOL6huLnful+ArD+R2XN3/ZGcwQl6+4jsRj2e3Y82Sm/yTgANmCQEqhIqLjfWNePdOqmT0apncNRVE8YfklK+VRNs976s0xR2rEPIl4tNaYoGOHqaJl8JfIrZ5CjIIxKV55C5PUdzzgAxR3NS8hR7wGu5H0rX1of4shVf1Nncn3WNKTrsOU//PPBjgE79RIN3G5aUC54lMNkzMfaJ2FwAfTXoMbSUygQiGu1Sh0UizQpgjqzPH8gIt6v8qt542i4Pk5T+gbrculkfzvFhzMQu81EyP2v4TfCNmCsSrFQdRl2Z7pTbWddtn5//e6Px";

        // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
        // We will define some constants and conversions here
//       final float mmPerInch = 25.4f;
//        final float mmFTCFieldWidth = (12 * 6) * mmPerInch;       // the width of the FTC field (from the center point to the outer panels)
//          final float mmTargetHeight = (6) * mmPerInch;          // the height of the center of the target image above the floor

        // Select which camera you want use.  The FRONT camera is the one on the same side as the screen.
        // Valid choices are:  BACK or FRONT
/*        final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = FRONT;

        OpenGLMatrix lastLocation = null;
        boolean targetVisible = false;
        */
        int count = 2;

        /**
         * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
         * localization engine.
         */


            //r.init(hardwareMap);
            telemetry.addLine("Initializing...");
            telemetry.update();
//            setupAll();
/*        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

            parameters.mode = BNO055IMU.SensorMode.IMU;
            parameters.angleUnit = RADIANS;
            parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            parameters.loggingEnabled = false;

            imu = hardwareMap.get(BNO055IMU.class, "imu");

            imu.initialize(parameters);

            telemetry.addData("Mode", "calibrating...");
            telemetry.update();
*/
            // make sure the imu gyro is calibrated before continuing.
            while (!isStopRequested() && !r.imu.isGyroCalibrated()) {
                sleep(50);
                idle();
            }

            if(r.imu.isGyroCalibrated()) telemetry.addLine("Gyro Calibrated");


            telemetry.addData("Mode", "waiting for start");
//            telemetry.addData("imu calib status", r.imu.getCalibrationStatus().toString());
            telemetry.update();

            /*
             * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
             * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
             * If no camera monitor is desired, use the parameterless constructor instead (commented out below).
             */
/*            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
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
*/
            // For convenience, gather together all the trackable objects in one easily-iterable collection */
/*            List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
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

/*
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
/*
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
/*
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
/*
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
/*
            final int CAMERA_FORWARD_DISPLACEMENT = 125;   // eg: Camera is 110 mm in front of robot center
            final int CAMERA_VERTICAL_DISPLACEMENT = 114;   // eg: Camera is 200 mm above ground
            final int CAMERA_LEFT_DISPLACEMENT = 64;     // eg: Camera is ON the robot's center line

            OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                    .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES,
                            CAMERA_CHOICE == FRONT ? 90 : -90, 0, 0));


            /**  Let all the trackable listeners know where the phone is.  */
/*            for (VuforiaTrackable trackable : allTrackables) {
                ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(phoneLocationOnRobot, parametersVU.cameraDirection);
            }
*/
            /** Wait for the game to begin */
            telemetry.addData(">", "Press Play to start tracking");
            telemetry.update();
            waitForStart();

            /** Start tracking the data sets we care about. */
//            targetsRoverRuckus.activate();
            while (opModeIsActive()) {

//                r.correction = checkDirection();

                //telemetry.addData("1 imu heading", lastAngles.firstAngle);
                //telemetry.addData("2 global heading", globalAngle);
                //telemetry.addData("3 correction", correction);
                //telemetry.update();

                if(count<1) {
//                  moveStraight(100, FORWARD, 0.5);
//                    moveBot(20, FORWARD, 0.5);
                    r.FLBLMotor.setPower(0.5);
                    r.FRBRMotor.setPower(0.5);
                    sleep(500);
                    r.stopDrivetrain();
                    sleep(500);
                    count = count + 1;
                }
                if(count==1) {
//                    moveStraight(40, FORWARD, 0.5);
//                    moveBot(100, FORWARD, 0.5);
                    r.moveDrivetrain(.5,.5);
//                    r.FLBLMotor.setPower(0.5);
//                    r.FRBRMotor.setPower(0.5);
                    sleep(500);
                    r.stopDrivetrain();
                    sleep(500);
                    count = count + 1;
                }
                if(count==1) {
//                    moveStraight(40, FORWARD, 0.5);
                    moveBot(20, FORWARD, 0.5);
//                    r.moveDrivetrain(.5,.5);
//                    r.FLBLMotor.setPower(0.5);
//                    r.FRBRMotor.setPower(0.5);
                    sleep(1000);

                    count = count + 1;
                }
                if(count==2) {
                    moveStraight(90, FORWARD, 0.5);
//                    moveBot(, FORWARD, 0.5);
//                    r.moveDrivetrain(.5,.5);
//                    r.FLBLMotor.setPower(0.5);
//                    r.FRBRMotor.setPower(0.5);
                    sleep(1000);

                    count = count + 1;
                }
                r.FLBLMotor.setPower(0.);
                r.FRBRMotor.setPower(0.);

            }
        }


}
