package org.firstinspires.ftc.teamcode.Rover.Archive;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;

import static com.qualcomm.hardware.bosch.BNO055IMU.AngleUnit.RADIANS;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;

//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;


/**
 * This 2018-2019 OpMode illustrates the basics of using the Vuforia localizer to determine
 * positioning and orientation of robot on the FTC field.
 * The code is structured as a LinearOpMode
 *
 * Vuforia uses the phone's camera to inspect it's surroundings, and attempt to locate target images.
 *
 * When images are located, Vuforia is able to determine the position and orientation of the
 * image relative to the camera.  This sample code than combines that information with a
 * knowledge of where the target images are on the field, to determine the location of the camera.
 *
 * This example assumes a "square" field configuration where the red and blue alliance stations
 * are on opposite walls of each other.
 *
 * From the Audience perspective, the Red Alliance station is on the right and the
 * Blue Alliance Station is on the left.

 * The four vision targets are located in the center of each of the perimeter walls with
 * the images facing inwards towards the robots:
 *     - BlueRover is the Mars Rover image target on the wall closest to the blue alliance
 *     - RedFootprint is the Lunar Footprint target on the wall closest to the red alliance
 *     - FrontCraters is the Lunar Craters image target on the wall closest to the audience
 *     - BackSpace is the Deep Space image target on the wall farthest from the audience
 *
 * A final calculation then uses the location of the camera on the robot to determine the
 * robot's location and orientation on the field.
 *
 * @see VuforiaLocalizer
 * @see VuforiaTrackableDefaultListener
 * see  ftc_app/doc/tutorial/FTC_FieldCoordinateSystemDefinition.pdf
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */

public class UltimumStella_ElectricalM {

    //Orientation lastAngles = new Orientation();
    //public double globalAngle;
    public DcMotor FLMotor = null;
    public DcMotor FRMotor = null;
    public DcMotor BLMotor = null;
    public DcMotor BRMotor = null;
    public Rev2mDistanceSensor sensorRange;
    public TouchSensor sensorTouch;
    //    public void runOpMode() throws InterruptedException {    }
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
    public static final String VUFORIA_KEY = "AXLb9ZD/////AAAAGc+ylHTIf0+aorS8rw6aoBRMAiybD7XCkifjVKb1gFrWJ+pZOL6huLnful+ArD+R2XN3/ZGcwQl6+4jsRj2e3Y82Sm/yTgANmCQEqhIqLjfWNePdOqmT0apncNRVE8YfklK+VRNs976s0xR2rEPIl4tNaYoGOHqaJl8JfIrZ5CjIIxKV55C5PUdzzgAxR3NS8hR7wGu5H0rX1of4shVf1Nncn3WNKTrsOU//PPBjgE79RIN3G5aUC54lMNkzMfaJ2FwAfTXoMbSUygQiGu1Sh0UizQpgjqzPH8gIt6v8qt542i4Pk5T+gbrculkfzvFhzMQu81EyP2v4TfCNmCsSrFQdRl2Z7pTbWddtn5//e6Px";

    // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
    // We will define some constants and conversions here
    public static final float mmPerInch = 25.4f;
    public static final float mmFTCFieldWidth = (12 * 6) * mmPerInch;       // the width of the FTC field (from the center point to the outer panels)
    public static final float mmTargetHeight = (6) * mmPerInch;          // the height of the center of the target image above the floor

    // Select which camera you want use.  The FRONT camera is the one on the same side as the screen.
    // Valid choices are:  BACK or FRONT
    public static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = FRONT;

    public OpenGLMatrix lastLocation = null;
    public  boolean targetVisible = false;
    double globalAngle, correction;
    VuforiaLocalizer vuforia;
    //DistanceSensor sensorRangeR;
    //ColorSensor sensorColor;
    BNO055IMU imu;
    Orientation lastAngles = new Orientation();

    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

//    public RoverElectricalM r = new RoverElectricalM();

    public UltimumStella_ElectricalM() {
    }


            /**
             * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
             * localization engine.
             */

      /* Ticks per rotation / inches (circumference) per rotation = ticks per inch
    The encoders on our motors have 1120 ticks per revolution
    The diameter of the mecanum wheels is 4 in
    In our drivetrain, we are using 45 to 35 tooth gears configured for a 1.28 gear ratio*/

            final double TICKS_PER_INCH = 1120 / (4 * Math.PI);
            final double WHEEL_GEAR_RATIO = 0.78 / 1.0; //was 1.28/1.0
            public final int FORWARD = 1; //was -1
            public final int BACKWARD = -1; //was 1
            //inches_to_ticks receives a distance in inches and returns the number of ticks
            int inches_to_ticks ( double target){
                return (int) (target * TICKS_PER_INCH * WHEEL_GEAR_RATIO);
            }

            public void init (HardwareMap ahwMap){

                hwMap = ahwMap;


                FLMotor = hwMap.dcMotor.get("FL");
                FRMotor = hwMap.dcMotor.get("FR");
                BLMotor = hwMap.dcMotor.get("BL");
                BRMotor = hwMap.dcMotor.get("BR");
                //sensorColor = hwMap.get(ColorSensor.class, "sensor_color");
                sensorRange = hwMap.get(Rev2mDistanceSensor.class, "sensor_range");
                Rev2mDistanceSensor sensorTimeOfFlight = (Rev2mDistanceSensor)sensorRange;
               // sensorRangeR = hwMap.get(DistanceSensor.class, "sensor_range_R");

                FLMotor.setPower(0);
                FRMotor.setPower(0);
                BLMotor.setPower(0);
                FRMotor.setPower(0);
//10/27 oommented out the two lines just below for drive straight testing
//10/27 uncommented two lines below
                FLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                FRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                BLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                BRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


                //FLBLMotor.setDirection(DcMotorSimple.Direction.REVERSE);

                imu = hwMap.get(BNO055IMU.class, "imu");

                BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

                parameters.mode = BNO055IMU.SensorMode.IMU;
                parameters.angleUnit = RADIANS;
                parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
                parameters.loggingEnabled = false;

                imu.initialize(parameters);


            }


            public static void moveMotor (DcMotor motor,double pwr){
                if (pwr >= 1.0) {
                    motor.setPower(1.0);
                } else if (pwr <= -1.0) {
                    motor.setPower(-1.0);
                } else {
                    motor.setPower(pwr);
                }
            }

            public static void moveServo (Servo servo,double pos){
                if (pos >= 1.0) {
                    servo.setPosition(1.0);
                } else if (pos <= 0.0) {
                    servo.setPosition(0.0);
                } else {
                    servo.setPosition(pos);
                }
            }

            public void moveLeftSide ( double lPow){
                lPow = (lPow >= 1.0) ? 1.0 : lPow;
                lPow = (lPow <= -1.0) ? -1.0 : lPow;
                FLMotor.setPower(lPow);
                BLMotor.setPower(lPow);
            }

            public void moveRightSide ( double rPow){
                rPow = (rPow >= 1.0) ? 1.0 : rPow;
                rPow = (rPow <= -1.0) ? -1.0 : rPow;
                FRMotor.setPower(rPow);

            }

            public void moveDrivetrain ( double lPow, double rPow){
                moveLeftSide(lPow);
                moveRightSide(rPow);
            }

            public void stopDrivetrain () {
                moveDrivetrain(0, 0);
            }


            public void waitForTick ( long periodMs) throws InterruptedException {

                long remaining = periodMs - (long) period.milliseconds();

                if (remaining > 0)
                    Thread.sleep(remaining);

                period.reset();

            }
            public double power ( double val){
                int sgn = (val >= 0) ? 1 : -1;
                double holder = Math.pow(val, 2.0) * sgn;
                return holder;
            }

/*
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
/*
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

    private void resetAngle()
    {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS); //was DEGREES

        globalAngle = 0;
    }
    */


}


