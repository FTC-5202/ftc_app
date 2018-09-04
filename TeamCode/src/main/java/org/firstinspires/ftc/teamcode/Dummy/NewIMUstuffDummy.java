/*

package org.firstinspires.ftc.teamcode.Dummy;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by FIRSTUser on 6/18/2018.
 */
/*
@Autonomous (name="IMU pls work")
public class NewIMUstuffDummy extends LinearOpMode {

    //HardwareDummyMecanumIMU robot = new HardwareDummyMecanumIMU();
    public DcMotor BLMotor = null;
    public DcMotor BRMotor = null;
    public DcMotor FLMotor = null;
    public DcMotor FRMotor = null;

    double CurrentHeading;
    double NewHeading;

    BNO055IMU imu;

    public void runOpMode() {


         /*public*/
/*
        public enum Direction {
            FORWARD (+1.0),
                BACKWARD(-1.0),
                CLOCKWISE(+1.0),
                COUNTERCLOCKWISE(-1.0);
        public final double value;

        Direction( double value){
            this.value = value;
        }
    }


            public double getHeading() {
                return imu.getAngles()[0];
            }

            public double adjustAngle(double angle) {
                while (angle > 180) angle -= 360;
                while (angle <= -180) angle += 360;
                return angle;
            }
        }

            public void turnP(double degrees, Direction direction, double timeout, double speed, double kp) {
                double targetAngle = adjustAngle(getHeading() + direction.value * degrees);
                double error;
                double power;

                do {
                    error = adjustAngle(targetAngle - getHeading());
                    power = kp * error;
                    power = Range.clip(power, -speed, +speed);
                    //driveTrain.setPower(-power, +power);
                    BLMotor.setPower(+-power);
                    BRMotor.setPower(+-power);
                    FLMotor.setPower(+-power);
                    FRMotor.setPower(+-power);
                    idle();
                } while (opModeIsActive() && error > 0.5);
                //driveTrain.stopMotors();
                BLMotor.setPower(0.0);
                BRMotor.setPower(0.0);
                FLMotor.setPower(0.0);
                FRMotor.setPower(0.0);
            }
        }

*/
