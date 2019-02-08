/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.Dummy;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.Locale;

/**
 * {@link SensorBNO055IMUDummy} gives a short demo on how to use the BNO055 Inertial Motion Unit (IMU) from AdaFruit.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 *
 * @see <a href="http://www.adafruit.com/products/2472">Adafruit IMU</a>
 */
@Autonomous(name = "IMU Test ", group = "Sensor")
@Disabled                          // Comment this out to add to the opmode list
public class SensorBNO055IMUDummy extends LinearOpMode {
    //HardwareDummyMecanumIMU robot = new HardwareDummyMecanumIMU();
   /* public DcMotor BLMotor     = null;
    public DcMotor BRMotor     = null;
    public DcMotor FLMotor     = null;
    public DcMotor FRMotor     = null;*/

    DcMotor left_side;
    DcMotor right_side;

    double currentHeading;
    double startHeading;

    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    // The IMU sensor object
    BNO055IMU imu;

    // State used for updating telemetry
    Orientation angles;
    Acceleration gravity;

    //----------------------------------------------------------------------------------------------
    // Main logic
    //----------------------------------------------------------------------------------------------

    @Override
    public void runOpMode() {

        // Set up the parameters with which we will use our IMU. Note that integration
        // algorithm here just reports accelerations to the logcat log; it doesn't actually
        // provide positional information.
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        long startTime = System.currentTimeMillis();

        imu.initialize(parameters);
        long currentTime = System.currentTimeMillis();
        telemetry.addLine("Time taken to initialize: " + (currentTime - startTime));
        telemetry.update();

        sleep(2000);

       /* BLMotor = hardwareMap.get(DcMotor.class, "BL");
        BRMotor = hardwareMap.get(DcMotor.class, "BR");
        FLMotor = hardwareMap.get(DcMotor.class, "FL");
        FRMotor = hardwareMap.get(DcMotor.class, "FR");*/

        left_side = hardwareMap.dcMotor.get("FLBL");
        right_side = hardwareMap.dcMotor.get("FRBR");

        left_side.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_side.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

       // left_side.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       // right_side.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        left_side.setDirection(DcMotorSimple.Direction.REVERSE);

       // FLMotor.setDirection(DcMotor.Direction.REVERSE);

        // Set up our telemetry dashboard
        composeTelemetry();

        // Wait until we're told to go

        waitForStart();



        // Start the logging of measured acceleration
        //imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);

        // Loop and update the dashboard
        //while (opModeIsActive()) {

          //  FRMotor.setPower(0.5);
           // FLMotor.setPower(0.5);
          //  BRMotor.setPower(0.5);
           // BLMotor.setPower(0.5);

            left_side.setPower(0.0);
            right_side.setPower(0.0);

            telemetry.update();
            sleep(1000);
            imuTurn(90, 0.4); //correcting turn - "head turn"
            while (opModeIsActive()) {
                telemetry.addLine("currentHeading = "+ currentHeading); //displays currentHeading
                telemetry.addLine("powerFunct = " + (Math.signum(-90 - currentHeading)*powerFuct(0.4, -90, currentHeading))); //displays powerFunct
                currentHeading = angles.firstAngle; //defines value of currentHeading as angles.firstAngle
                telemetry.update(); //update
                sleep(100);
        }

    }
    //TODO: try <= 60 has 15 percent reduction and > 60 does not

    private void imuTurn (double degrees, double iPowerScalar) { //METHOD using degrees, iPowerScalar to make a clockwiseTurn

        angles = imu.getAngularOrientation(); //define angles as imu.getAngularOrientation
        currentHeading = angles.firstAngle; //define currentHeading as angles.firstAngle
        startHeading = angles.firstAngle; //define startHeading as angles.firstAngle
        left_side.setPower(-iPowerScalar); //set left_side Power to the opposite val of iPowerScalar
        right_side.setPower(-iPowerScalar); //set right_side Power to the opposite val of iPowerScalar
        final double GOAL_HEADING = startHeading - degrees; //define Goal_Heading
        while(currentHeading > GOAL_HEADING){ //condition: currentHeading must be greater than Goal_Heading for this loop to execute
            left_side.setPower(-powerFuct(iPowerScalar, GOAL_HEADING, currentHeading)); //set left_side Power to inverse of powerFunct using iPowerScalar, Goal_Heading, and currentHeading
            right_side.setPower(-powerFuct(iPowerScalar, GOAL_HEADING, currentHeading)); //set right_side Power to inverse of powerFunct using iPowerScalar, Goal_Heading, and currentHeading
            angles = imu.getAngularOrientation(); //define angles as imu.getAngularOrientation
            currentHeading = angles.firstAngle; //define currentHeading as angles.firstAngle
        }
        left_side.setPower(0); //set left_side Power to null
        right_side.setPower(0); //set right_side Power to null
        sleep(300); //stop for 0.3 seconds
        angles = imu.getAngularOrientation(); //define angles as imu.getAngularOrientation
        currentHeading = angles.firstAngle; //define currentHeading as angles.firstAngle
        if(currentHeading < GOAL_HEADING - 5){ //condition: currentHeading must be less than the val of Goal_Heading - 5 in order for this loop to execute
            telemetry.addLine("GOING YAYA"); //display that loop is being executed on driver station
            telemetry.update(); //update the line
            sleep(1000); //the increments in which this line will continually be updated
            left_side.setPower(0.14); //sets left_side Power to 0.14
            right_side.setPower(0.14); //sets right_side Power to 0.14
            while(currentHeading < (GOAL_HEADING - 3)){ //condition: currentHeading must be less than the val of Goal_Heading -3 in order for this loop to execute
                angles = imu.getAngularOrientation(); //defines angles as imu.getAngularOrientation
                currentHeading = angles.firstAngle; //defines currentHeading as angles.firstAngle
            }
            left_side.setPower(0); //sets left_side Power to null
            right_side.setPower(0); //sets right_side Power to null
            telemetry.addLine("DONE"); //displays on driver station that the loop is finished
            telemetry.update(); //update the line displayed
            sleep(1000); //the increments in which this line will continually be updated
        }
        /*
        angles = imu.getAngularOrientation();
        currentHeading = angles.firstAngle; //angles.firstAngle = heading value
        final double PERCENT_FULL_POWER = 0.5, PERCENT_LOW_POWER = 0.4, PERCENT_REDUCTION = 0.1;
        startHeading = currentHeading;
        if (degrees <= 60) {
            left_side.setPower(-0.2);
            right_side.setPower(-0.2);
        } else {
            left_side.setPower(-0.35);
            right_side.setPower(-0.35);
        }

        while (startHeading > currentHeading - (0.5 * degrees)) {
            angles = imu.getAngularOrientation();
            startHeading   = angles.firstAngle;
            telemetry.addLine("heading = " + startHeading);
            telemetry.update();

        }
        left_side.setPower(-0.12);
        right_side.setPower(-0.12);

        while (startHeading > currentHeading - (1 * degrees)) {
            angles = imu.getAngularOrientation();
            startHeading   = angles.firstAngle;
            telemetry.addLine("heading = " + startHeading);
            telemetry.update();

        }
        left_side.setPower(0);
        right_side.setPower(0);
        telemetry.update();
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

    private void counterclockwiseTurn(int degrees) { //METHOD using degrees for a counterclockwise turn
        angles = imu.getAngularOrientation(); //defines angles as imu.getAngularOrientation
        currentHeading = angles.firstAngle; //angles.firstAngle = heading value
        startHeading = currentHeading; //defines the startHeading as currentHeading
        //  FRMotor.setPower(0.25);
        //  FLMotor.setPower(0.0);
        //  BRMotor.setPower(0.25);
        //  BLMotor.setPower(0.0);

        left_side.setPower(0.35); //sets left_side Power to 0.35
        right_side.setPower(0.35); // sets the right_side Power to 0.35

        while (startHeading < currentHeading + degrees) { //condition: startHeading must be less than currentHeading + degrees or the loop will not execute
            angles = imu.getAngularOrientation(); //defines angles as imu.getAngularOrientation
            startHeading = angles.firstAngle; //defines startHeading as angles.firstAngle
            telemetry.addLine("heading = " + startHeading); //displays the startHeading on the driver station phone
            telemetry.update(); //updates the line displayed

        }
        // FRMotor.setPower(0.0);
        // BRMotor.setPower(0.0);
        left_side.setPower(0); //sets the left_side Power to null
        right_side.setPower(0); //sets the right_side Power to null
        telemetry.update(); //update the line displayed
    }


//    }

    //----------------------------------------------------------------------------------------------
    // Telemetry Configuration
    //----------------------------------------------------------------------------------------------

    void composeTelemetry() {


        // At the beginning of each telemetry update, grab a bunch of data
        // from the IMU that we will then display in separate lines.
        telemetry.addAction(new Runnable() {
            @Override
            public void run() {
                // Acquiring the angles is relatively expensive; we don't want
                // to do that in each of the three items that need that info, as that's
                // three times the necessary expense.
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                gravity = imu.getGravity();
            }
        });

        /*telemetry.addLine()
                .addData("status", new Func<String>() {
                    @Override
                    public String value() {
                        return imu.getSystemStatus().toShortString();
                    }
                })
                .addData("calib", new Func<String>() {
                    @Override
                    public String value() {
                        return imu.getCalibrationStatus().toString();
                    }
                });*/

        telemetry.addLine()
                .addData("heading", new Func<String>() {
                    @Override
                    public String value() {
                        return formatAngle(angles.angleUnit, angles.firstAngle);
                    }
                });
                /*.addData("roll", new Func<String>() {
                    @Override
                    public String value() {
                        return formatAngle(angles.angleUnit, angles.secondAngle);
                    }
                })
                .addData("pitch", new Func<String>() {
                    @Override
                    public String value() {
                        return formatAngle(angles.angleUnit, angles.thirdAngle);
                    }
                });

        telemetry.addLine()
                .addData("grvty", new Func<String>() {
                    @Override
                    public String value() {
                        return gravity.toString();
                    }
                })
                .addData("mag", new Func<String>() {
                    @Override
                    public String value() {
                        return String.format(Locale.getDefault(), "%.3f",
                                Math.sqrt(gravity.xAccel * gravity.xAccel
                                        + gravity.yAccel * gravity.yAccel
                                        + gravity.zAccel * gravity.zAccel));
                    }
                });*/

    }

    //----------------------------------------------------------------------------------------------
    // Formatting
    //----------------------------------------------------------------------------------------------

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees) {
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));

    }
}
