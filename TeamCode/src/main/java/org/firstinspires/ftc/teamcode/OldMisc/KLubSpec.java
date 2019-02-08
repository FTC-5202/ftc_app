package org.firstinspires.ftc.teamcode.OldMisc;

import android.app.Activity;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by FIRSTUser on 10/22/2017.
 */


@TeleOp(name = "Theo: TheoTeleop", group = "Theo")
@Disabled
public class KLubSpec extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareTheoMecanum robot = new HardwareTheoMecanum();     // Use the K9's hardware
    //    double          armPosition     = robot.ARM_HOME;                   // Servo safe position
//    double          clawPosition    = robot.CLAW_HOME;                  // Servo safe position
    final double CLAW_SPEED = 0.01;                            // sets rate to move servo
    final double ARM_SPEED = 0.06;                            // sets rate to move servo
    final double RELIC_X_SPEED = 0.01;


    @Override
    public void runOpMode() throws InterruptedException {

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        // bPrevState and bCurrState represent the previous and current state of the button.

        boolean bPrevState = false;
        boolean bCurrState = false;
        boolean FLIP = false;
        boolean FGClamp = false;

        // bLedOn represents the state of the LED.
        boolean bLedOn = true;

        {

        }

        double FL;
        double FR;
        double BL;
        double BR;
        double FG;
        double wopFL = 0.0;
        double wopFR = 0.0;
        double wopBL = 0.0;
        double wopBR = 0.0;
        double RYservo = 0.0;
        double RXservo = 0.0;
        double RCservo = 0.0;
        double left_stick_y_ARMS = 0.0;
        double FgLift = 0.0;                  // Servo safe position
        double fg = 1.0;                  // Servo safe position
        double Rarm = 0.9;
        double Larm = 0.0;
        double zoneWidth = 0.3;
        //double triggerPress = 0.5;
        final double FG_SPEED = 0.06;
        boolean FLIP1 = false;
        boolean AutoR = false;
        boolean AutoL = false;
        boolean isForward = true;
        boolean rb1Pressed = false;
        boolean Dpad_UP = false;
        boolean Dpad_DOWN = false;
        boolean RC_CLOSED = false;
        double Tank = 0.0;
        double FRcr = 1.0;
        double FLcr = 1.0;
        double BRcr = 1.0;
        double BLcr = 1.0;
        /*
        ColorSensor ls1;
        ColorSensor ls2;
        ColorSensor rs1;
        ColorSensor rs2;

        */


        // Initialize the hardware variables.
        //The init() method of the hardware class does all the work here

        robot.init(hardwareMap);


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Set the LED in the beginning
        //robot.LS1.enableLed(bLedOn);
        //robot.RS1.enableLed(bLedOn);
        //robot.LS2.enableLed(bLedOn);
        //robot.RS2.enableLed(bLedOn);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        zoneWidth = 0.3;
        //triggerPress = 0.3;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if(gamepad1.right_bumper && !rb1Pressed){
                rb1Pressed = true;
            }

            if(!gamepad1.right_bumper && rb1Pressed){
                isForward = !isForward;
                rb1Pressed = false;
            }
            if (Math.abs(gamepad1.left_stick_x) <= (zoneWidth) || (Math.abs(gamepad1.right_stick_x)) <= (zoneWidth)) {// Searches for if we are engaging Tank drive or Mechanum Drive, no in-between
               Tank = 1.0;
                if (!isForward) {  // if reversed (Tank)
                    BL = -gamepad1.right_stick_y;
                    BR = -gamepad1.left_stick_y;
                    FL = -gamepad1.right_stick_y;
                    FR = -gamepad1.left_stick_y;

                    FG = gamepad2.left_stick_y;
                    robot.FgLift.setPower(FG / 1.5);
                } else {                              // not reversed (Tank)
                    BL = gamepad1.left_stick_y;
                    BR = gamepad1.right_stick_y;
                    FL = gamepad1.left_stick_y;
                    FR = gamepad1.right_stick_y;

                    FG = gamepad2.left_stick_y;
                    robot.FgLift.setPower(FG / 1.5);
                }

            } else {
                BL = -gamepad1.left_stick_x;
                BR = gamepad1.left_stick_x;
                FL = gamepad1.left_stick_x;
                FR = -gamepad1.left_stick_x;

                FG = gamepad2.left_stick_y;
                robot.FgLift.setPower(FG / 1.5);

                if (Math.abs(gamepad1.left_stick_x) >= (zoneWidth) || (Math.abs(gamepad1.right_stick_x)) >= (zoneWidth)) {
                    Tank = 2.0;

                    if (!isForward) {  // if reversed (Mechanum)

                        BL = -gamepad1.right_stick_x;
                        BL = BL*BLcr;
                        BR = gamepad1.left_stick_x;
                        BR = BR*BRcr;
                        FL = gamepad1.right_stick_x;
                        FL = FL*FLcr;
                        FR = -gamepad1.left_stick_x;
                        FR = FR*FRcr;
                        FG = gamepad2.left_stick_y;
                        robot.FgLift.setPower(FG / 1.5);
                    } else {                              // not reversed (Mechanum)
                        BL = gamepad1.left_stick_x;
                        BR = -gamepad1.right_stick_x;
                        FL = -gamepad1.left_stick_x;
                        FR = gamepad1.right_stick_x;
                        FG = gamepad2.left_stick_y;
                        robot.FgLift.setPower(FG / 1.5);
                    }


                }

            }


            /* Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
            BL = gamepad1.left_stick_y + gamepad1.left_stick_x;
            BR = gamepad1.right_stick_y - gamepad1.right_stick_x;
            FL = gamepad1.left_stick_y - gamepad1.left_stick_x;
            FR = gamepad1.right_stick_y + gamepad1.right_stick_x;
            FG = gamepad2.left_stick_y;
            */
                robot.FgLift.setPower(FG / 1.5);
                // robot.LXmotor.setPower(LX / 1.5);
                //                  left = -gamepad1.left_stick_y;
                //                right = gamepad1.right_stick_y;

                // Use gamepad left stick_y to raise and lower the arm

                if (gamepad2.right_bumper) {
                    fg = 0.4;
                    telemetry.addData("Clamp is OPEN", gamepad2.right_bumper);
                    robot.Fg.setPosition(fg);

                }

                //}

                if (gamepad2.left_bumper) {
                    fg = 0.0;
                    telemetry.addData("Clamp is CLOSED", gamepad2.left_bumper);
                    robot.Fg.setPosition(fg);

                }


                if (gamepad1.x) {
                    robot.Rarm.setPosition(0.6);              //

                }

                if (gamepad1.b) {
                    robot.Larm.setPosition(0.2);             //left autonomous arm will move to downward position when b button on BASE controller is pressed

                }


            if (gamepad2.dpad_up && !Dpad_UP){
                Dpad_UP = true;
            }

            if (gamepad2.dpad_up && Dpad_UP){
                //when dpad right on ARMS controller is pressed
                    RYservo = 0.45;          //relic servo will move up
                    Dpad_UP = false;

                    if (Dpad_UP = !Dpad_UP) {
                        //when dpad right on ARMS controller isn't pressed
                        RYservo = 0.0;

                    }
                }
                    if (gamepad2.dpad_down && !Dpad_DOWN) {
                        Dpad_DOWN = true;
                    }
                    if (gamepad2.dpad_up && Dpad_DOWN) {
                        RXservo = 0.35;
                        Dpad_DOWN = false;

                        if (Dpad_DOWN = !Dpad_DOWN) {
                            RXservo = 0.0;
                        }

                    }

                    if (gamepad2.a && !RC_CLOSED) {
                        RC_CLOSED = true;

                    }

                    if (gamepad2.a && RC_CLOSED) {
                        RCservo = 0.5;
                        RC_CLOSED = false;

                        if (RC_CLOSED = !RC_CLOSED) {
                            RCservo = 0.0;
                        }
                    }



                /*if (gamepad1.left_bumper) {
                    //when left bumper on gamepad 1 is pressed
                    FLIP1 = true;                                //variable Flip = true

                    if (FLIP1 = true)                          //when the variable Flip = true
                        FR = -gamepad1.left_stick_y;             //right on BASE controller controls the left side
                    FL = -gamepad1.right_stick_y;             //left on BASE controller controls the right side
                    BR = gamepad1.left_stick_y;
                    BL = gamepad1.right_stick_y;

                    robot.FRMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                    robot.FLMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                    robot.BRMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                    robot.BLMotor.setDirection(DcMotorSimple.Direction.REVERSE);

                }
*/
                if (gamepad1.y) {

                    robot.wopFL.setPosition(0.7);                   //WOP servos will go to designated downward position when the a button on BASE controller is pressed
                    robot.wopFR.setPosition(0.8);
                    robot.wopBL.setPosition(0.8);
                    robot.wopBR.setPosition(0.7);

                }

                // check the status of the x button on either gamepad.
                bCurrState = gamepad1.y;

                // check for button state transitions.
                if (bCurrState && (bCurrState != bPrevState)) {

                    // button is transitioning to a pressed state. So Toggle LED
                    bLedOn = !bLedOn;
                    //robot.LS1.enableLed(bLedOn);
                    //robot.RS1.enableLed(bLedOn);
                    //robot.LS2.enableLed(bLedOn);
                    //robot.RS2.enableLed(bLedOn);
                }

                // update previous state variable.
                bPrevState = bCurrState;

                // Color.RGBToHSV(robot.LS1.red() * 8, robot.LS1.green() * 8, robot.LS1.blue() * 8, hsvValues);
                // Color.RGBToHSV(robot.RS1.red() * 8, robot.RS2.green() * 8, robot.RS1.blue() * 8, hsvValues);
                //Color.RGBToHSV(robot.LS2.red() * 8, robot.LS2.green() * 8, robot.LS2.blue() * 8, hsvValues);
                // Color.RGBToHSV(robot.RS2.red() * 8, robot.RS2.green() * 8, robot.RS2.blue() * 8, hsvValues);


                // send the info back to driver station using telemetry function.

                // change the background color to match the color detected by the RGB sensor.
                // pass a reference to the hue, saturation, and value array as an argument
                // to the HSVToColor method.

                //telemetry.addData("LED", bLedOn ? "On" : "Off");
                // telemetry.addData("Clear", robot.LS1.alpha());
                //elemetry.addData("Red  ", robot.RS1.red());
                //telemetry.addData("Red   ", robot.LS1.red());
                //telemetry.addData("Blue  ", robot.RS2.blue());
                //telemetry.addData("Blue  ", robot.LS2.blue());

                robot.BLMotor.setPower(BL);
                robot.BRMotor.setPower(BR);
                robot.FLMotor.setPower(FL);
                robot.FRMotor.setPower(FR);

                telemetry.addData("fg ", fg);
                telemetry.addData("Tank = ", Tank);
                telemetry.update();

            }
        }
    }

