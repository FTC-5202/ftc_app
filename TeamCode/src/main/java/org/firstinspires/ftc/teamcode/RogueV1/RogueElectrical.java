package org.firstinspires.ftc.teamcode.RogueV1;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

import java.util.ArrayList;

/**
 * Created by samue on 11/7/2016.
 */
// TODO: 2/4/2017 code the spooker
public class RogueElectrical {

    DcMotor leftSide = null;
    DcMotor rightSide = null;
    DcMotor radapult = null;
    DcMotor sweeper = null;
    DcMotor lift = null;
    DcMotor fork = null;

    Servo leftArm = null;
    Servo rightArm = null;
    Servo leftPlate = null;
    Servo rightPlate = null;
    Servo release = null;
    Servo lightArm = null;
    Servo hood = null;
    Servo lSpooker = null;
    Servo rSpooker = null;

    DeviceInterfaceModule cdim;
    TouchSensor touchSensor;
    ColorSensor ada;
    ColorSensor mr;
    UltrasonicSensor sonar;
    GyroSensor gyro;
    UltrasonicSensor sideSonar;
    UltrasonicSensor leftSonar;
    //OpticalDistanceSensor lightMiddle;
    //OpticalDistanceSensor lightBack;

    RogueLight lightMiddle = new RogueLight();
    RogueLight lightBack = new RogueLight();
    RogueLight lightRight = new RogueLight();
    RogueLight lightLeft = new RogueLight();

    static final int LED_CHANNEL = 1;


    public final double RADAPULT_ON = 0.9;
    public final double RADAPULT_OFF = 0.0;

    final double LTRIGGER = 120/180.0;
    final double RTRIGGER = 70/180.0;//

    final double LARMOUT = 0.72;
    final double RARMOUT = 0.34;
    final double LPLATEOUT = 0.64; // was 0.64
    final double RPLATEOUT = 0.38;

    final double LARMIN = 0.21;
    final double RARMIN = 0.77;
    final double LPLATEIN = 0.48;
    final double RPLATEIN = 0.49; //was .42

    final double CHANGERATEJH = 0.04;

    final double LCENTER_OFFSET = 58/180.0; // Middle
    final double RCENTER_OFFSET = 50/180.0;        // was 80
    //inside
    final double LOUT_OFFSET = 55/180.0;              // was 30
    final double ROUT_OFFSET = 35/180.0;               // was 20

    final double FORK_HOME = 0.9;
    final double FORK_OPEN = 0.225;

    final double LIGHTARM_UP = 1.0;
    final double LIGHTARM_DOWN = 0.4;

    final double HOOD_UP = 0.5;
    final double HOOD_DOWN = 1.0;

    final double RSPOOK_UP = 0.2;
    final double RSPOOK_DOWN = 0.97;

    final double LSPOOK_UP = 0.85;
    final double LSPOOK_DOWN = 0.15;

    /**
     * void moveMotor
     *
     * @param motor : The motor to assign the power to
     * @param pwr : The desired power
     */
    public static void moveMotor(DcMotor motor, double pwr){
        if(pwr >= 1.0){
            motor.setPower(1.0);
        }
        else if(pwr <= -1.0){
            motor.setPower(-1.0);
        }
        else {
            motor.setPower(pwr);
        }
    }
    /**
     * void moveServo
     *
     * @param servo : The servo to set
     * @param pos : The position to set the servo to
     */
    public static void moveServo(Servo servo, double pos){
        if(pos >= 1.0){
            servo.setPosition(1.0);
        }
        else if (pos <= 0.0){
            servo.setPosition(0.0);
        }
        else {
            servo.setPosition(pos);
        }
    }


    public void moveLeftSide(double lPow){
        lPow = (lPow >= 1.0) ? 1.0 : lPow;
        lPow = (lPow <= -1.0) ? -1.0 : lPow;
        leftSide.setPower(lPow);
    }

    public void moveRightSide(double rPow){
        rPow = (rPow >= 1.0) ? 1.0 : rPow;
        rPow = (rPow <= -1.0) ? -1.0 : rPow;
        rightSide.setPower(rPow);
    }

    public void moveDrivetrain(double lPow, double rPow){
        moveLeftSide(lPow);
        moveRightSide(rPow);
    }

    public void stopDrivetrain(){
        moveDrivetrain(0,0);
    }






}
