package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import android.graphics.Color;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import com.qualcomm.hardware.bosch.BNO055IMU;

import static com.qualcomm.hardware.bosch.BNO055IMU.AngleUnit.RADIANS;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;
import static org.firstinspires.ftc.teamcode.Rover.RoverAutoMethods.Direction.RIGHT;
import static org.firstinspires.ftc.teamcode.Rover.RoverAutoMethods.Direction.LEFT;

@Autonomous (name="UltimumStella Silver")
public class UltimumStella_AutoSilver extends UltimumStella_AutoMethods {


    public void runOpMode() {

        telemetry.addLine("Initializing...");
        telemetry.update();

        setupAll();

        telemetry.addLine("Initializing...");
        telemetry.update();

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

        //Color.RGBToHSV(r.sensorColor.red() * 8, r.sensorColor.green() * 8, r.sensorColor.blue() * 8, hsvValues);

        /** Start tracking the data sets we care about. */
        //targetsRoverRuckus.activate();
        while (opModeIsActive()) {
            // if the digital channel returns true it's HIGH and the button is unpressed.
            if (r.sensorTouch.getState() == true) {
                r.Lift.setPower(1.0);
            }

            r.Lift.setPower(0);
            moveBot(30, FORWARD, 0.5, EndStatus.STOP);
            r.stopDrivetrain();
//            r.RSLif.setPosition(0.8);
//            r.LSLif.setPosition(0.2);
        }


    }

    //correction = checkDirection();

    //telemetry.addData("1 imu heading", lastAngles.firstAngle);
    //telemetry.addData("2 global heading", globalAngle);
    //telemetry.addData("3 correction", correction);
    //telemetry.update();




}
