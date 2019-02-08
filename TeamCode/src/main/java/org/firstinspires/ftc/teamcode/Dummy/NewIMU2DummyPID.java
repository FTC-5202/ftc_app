package org.firstinspires.ftc.teamcode.Dummy;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import static com.qualcomm.hardware.bosch.BNO055IMU.AngleUnit.DEGREES;

/**
 * Created by FIRSTUser on 6/18/2018.
 */
@Autonomous (name= "NEWIMU2DummyPID")
@Disabled
public class NewIMU2DummyPID extends LinearOpMode {

   /* public DcMotor BLMotor = null;
    public DcMotor BRMotor = null;
    public DcMotor FLMotor = null;
    public DcMotor FRMotor = null;*/
    DcMotor left_side;
    DcMotor right_side;
    //DigitalChannel touch;
    BNO055IMU imu;
    Orientation
            lastAngles = new Orientation();
    double globalAngle, power = .45/*was .3*/, correction, errorDegrees = 90 - getAngle(), targetAngle = 90; //(was .15)
    boolean aButton, bButton;
    double minimumPower = 0.2;
    double currentPowerR = left_side.getPower();
    double currentPowerL = right_side.getPower();
    double currentAngle = getAngle();


    // called when init button is  pressed.
    @Override
    public void runOpMode() throws InterruptedException {

        right_side = hardwareMap.dcMotor.get("FRBR");
        left_side = hardwareMap.dcMotor.get("FLBL");

        left_side.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right_side.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        left_side.setDirection(DcMotorSimple.Direction.REVERSE);
      /*  BLMotor = hardwareMap.get(DcMotor.class, "BL");
        BRMotor = hardwareMap.get(DcMotor.class, "BR");
        FLMotor = hardwareMap.get(DcMotor.class, "FL");
        FRMotor = hardwareMap.get(DcMotor.class, "FR");

        FLMotor.setDirection(DcMotor.Direction.REVERSE);

        BLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);*/



        // get a reference to REV Touch sensor.
        //touch = hardwareMap.digitalChannel.get("touch_sensor");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        imu.initialize(parameters);

        telemetry.addData("Mode", "calibrating...");
        telemetry.update();

        // make sure the imu gyro is calibrated before continuing.
        while (!isStopRequested() && !imu.isGyroCalibrated()) {
            sleep(50);
            idle();
        }

        telemetry.addData("Mode", "waiting for start");
        telemetry.addData("imu calib status", imu.getCalibrationStatus().toString());
        telemetry.update();

        // wait for start button.

        waitForStart();

        telemetry.addData("Mode", "running");
        telemetry.update();

        sleep(1000);

        // drive until end of period.

        while (opModeIsActive()) {
            // Use gyro to drive in a straight line.
            correction = checkDirection();

            telemetry.addData("1 imu heading", lastAngles.firstAngle);
            telemetry.addData("2 global heading", globalAngle);
            telemetry.addData("3 correction", correction);
            telemetry.update();

            // We record the sensor values because we will test them in more than
            // one place with time passing between those places. See the lesson on
            // Timing Considerations to know why.

            /*aButton = gamepad1.a;
            bButton = gamepad1.b;

            BLMotor.setPower(-power - correction);
            FLMotor.setPower(-power - correction);
            BRMotor.setPower(-power);
            FRMotor.setPower(-power);

            // turn the motors off.
            BLMotor.setPower(0);
            FLMotor.setPower(0);
            BRMotor.setPower(0);
            FRMotor.setPower(0);*/
        }
            //turning right
         /*   if (gamepad1.a) {
                BLMotor.setPower(power);
                FLMotor.setPower(power);
                BRMotor.setPower(-power);
                FRMotor.setPower(-power);
                getAngle();
                FLMotor.getPower();


                if (currentAngle <=targetAngle){
                    if (currentPowerR > minimumPower) {
                        currentPowerR -=  currentAngle /100;
                        BLMotor.setPower(currentPowerR);
                        FLMotor.setPower(currentPowerR);
                        BRMotor.setPower(-currentPowerR);
                        FRMotor.setPower(-currentPowerR);
                    }
                }
                if (currentAngle <=targetAngle){
                    if (currentPowerR == minimumPower) {
                        currentPowerR = minimumPower;
                        BLMotor.setPower(currentPowerR);
                        FLMotor.setPower(currentPowerR);
                        BRMotor.setPower(-currentPowerR);
                        FRMotor.setPower(-currentPowerR);
                    }
                }
                if (currentAngle == 90 && currentPowerR == minimumPower){
                    BLMotor.setPower(0);
                    FLMotor.setPower(0);
                    BRMotor.setPower(0);
                    FRMotor.setPower(0);
                }
            }
            //turning left
            if (gamepad1.b) {
                BLMotor.setPower(-power);
                FLMotor.setPower(-power);
                BRMotor.setPower(power);
                FRMotor.setPower(power);
                getAngle();
                FRMotor.getPower();

                if (currentAngle <=targetAngle){
                    if (currentPowerL > minimumPower) {
                        currentPowerL -= currentAngle /100;
                        BLMotor.setPower(-currentPowerL);
                        FLMotor.setPower(-currentPowerL);
                        BRMotor.setPower(currentPowerL);
                        FRMotor.setPower(currentPowerL);
                    }
                }
                if (currentAngle <=targetAngle){
                    if (currentPowerL == minimumPower) {
                        currentPowerL = minimumPower;
                        BLMotor.setPower(-currentPowerL);
                        FLMotor.setPower(-currentPowerL);
                        BRMotor.setPower(currentPowerL);
                        FRMotor.setPower(currentPowerL);
                    }
                }
                if (currentAngle == 90 && currentPowerL == minimumPower){
                    currentPowerL = 0;
                    BLMotor.setPower(currentPowerL);
                    FLMotor.setPower(currentPowerL);
                    BRMotor.setPower(currentPowerL);
                    FRMotor.setPower(currentPowerL);
                }
            }

            //turn right
       /* if (aButton && errorDegrees > 20) {
            BLMotor.setPower(power);
            FLMotor.setPower(power);
            BRMotor.setPower(-power);
            FRMotor.setPower(-power);
        }
        if (aButton && errorDegrees < 20) {
            BLMotor.setPower(power / 3);
            FLMotor.setPower(power / 3);
            BRMotor.setPower(-power / 3);
            FRMotor.setPower(-power / 3);

        }

        //turn left
        if (bButton && errorDegrees > 20) {
            BLMotor.setPower(-power);
            FLMotor.setPower(-power);
            BRMotor.setPower(power);
            FRMotor.setPower(power);
        }
        if (bButton && errorDegrees < 20) {
            BLMotor.setPower(-power / 3);
            FLMotor.setPower(-power / 3);
            BRMotor.setPower(power / 3);
            FRMotor.setPower(power / 3);

        }*/

        }

        /**
         * Resets the cumulative angle tracking to zero.
         */
        private void resetAngle ()
        {
            lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            globalAngle = 0;
        }

        /**
         * Get current cumulative angle rotation from last reset.
         * @return Angle in degrees. + = left, - = right.
         */
        private double getAngle ()
        {
            // We experimentally determined the Z axis is the axis we want to use for heading angle.
            // We have to process the angle because the imu works in euler angles so the Z axis is
            // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
            // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

            Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

            if (deltaAngle < -180)
                deltaAngle += 360;
            else if (deltaAngle > 180)
                deltaAngle -= 360;

            globalAngle += deltaAngle;

            lastAngles = angles;

            return globalAngle;
        }

        /**
         * See if we are moving in a straight line and if not return a power correction value.
         * @return Power adjustment, + is adjust left - is adjust right.
         */
        private double checkDirection()
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

        /**
         * Rotate left or right the number of degrees. Does not support turning more than 180 degrees.
         * @param degrees Degrees to turn, + is left - is right
         */
        private void rotate ( int degrees, double power)
        {
            double leftPower, rightPower;

            // restart imu movement tracking.
            resetAngle();

            // getAngle() returns + when rotating counter clockwise (left) and - when rotating
            // clockwise (right).

            if (degrees < 0) {   // turn right.
                leftPower = power;
                rightPower = -power;
            } else if (degrees > 0) {   // turn left.
                leftPower = -power;
                rightPower = power;
            } else return;

            // set power to rotate.
           /* BLMotor.setPower(leftPower);
            FLMotor.setPower(leftPower);
            BRMotor.setPower(rightPower);
            FRMotor.setPower(rightPower);*/

            left_side.setPower(leftPower);
            right_side.setPower(rightPower);

            // rotate until turn is completed.
            if (degrees < 0) {
                // On right turn we have to get off zero first.
                while (opModeIsActive() && getAngle() == 0) {
                }

                while (opModeIsActive() && getAngle() > degrees) {
                }
            } else    // left turn.
                while (opModeIsActive() && getAngle() < degrees) {
                }

            // turn the motors off.
           /* BLMotor.setPower(0);
            FLMotor.setPower(0);
            BRMotor.setPower(0);
            FRMotor.setPower(0);*/

            left_side.setPower(0);
            right_side.setPower(0);

            // wait for rotation to stop.
            sleep(1000);

            // reset angle tracking on new heading.
            resetAngle();
        }
    }

