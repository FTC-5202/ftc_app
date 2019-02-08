package org.firstinspires.ftc.teamcode.OldMisc;

import android.app.Activity;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by FIRSTUser on 10/22/2017.
 */


    @TeleOp(name = "Theo: TheoTeleopNC", group = "Theo")
    @Disabled
    public class KLubSpecNC extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareTheoMecanumNC robot = new HardwareTheoMecanumNC();     // Use the K9's hardware
    //    double          armPosition     = robot.ARM_HOME;                   // Servo safe position
//    double          clawPosition    = robot.CLAW_HOME;                  // Servo safe position
    final double CLAW_SPEED = 0.01;                            // sets rate to move servo
    final double ARM_SPEED = 0.06;                            // sets rate to move servo
    final double RELIC_X_SPEED = 0.01;


    @Override
    public void runOpMode() throws InterruptedException {

        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        // bPrevState and bCurrState represent the previous and current state of the button.

        boolean bPrevState = false;
        boolean bCurrState = false;
        boolean FLIP = false;
        boolean FGClamp = false;

        {

        }

        double FL;
        double FR;
        double BL;
        double BR;
        double FG;
        //double LX;
        //double LY;
        double wopFL = 0.0;
        double wopFR = 0.0;
        double wopBL = 0.0;
        double wopBR = 0.0;
        double left_stick_y_ARMS = 0.0;
        double FgLift = 0.0;                  // Servo safe position
        double fg = 1.0;                  // Servo safe position
        double Rarm = 0.0;
        double Larm = 0.0;
        final double FG_SPEED = 0.06;
        boolean FLIP1 = false;
        boolean AutoR = false;
        boolean AutoL = false;


        // Initialize the hardware variables.
        //The init() method of the hardware class does all the work here

        robot.init(hardwareMap);


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
            BL = gamepad1.left_stick_y + gamepad1.left_stick_x;
            BR = gamepad1.right_stick_y - gamepad1.right_stick_x;
            FL = gamepad1.left_stick_y - gamepad1.left_stick_x;
            FR = gamepad1.right_stick_y + gamepad1.right_stick_x;
            FG = gamepad2.left_stick_y;
            // LX = gamepad2.left_trigger;


            robot.BLMotor.setPower(BL);
            robot.BRMotor.setPower(BR);
            robot.FLMotor.setPower(FL);
            robot.FRMotor.setPower(FR);
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

            if (gamepad2.dpad_right) {           //when dpad right on ARMS controller is pressed
                //LY += RELIC_Y_SPEED;              //relic servo will move up
                if (gamepad2.dpad_right = !gamepad2.dpad_right) {
                    //when dpad right on ARMS controller isn't pressed
                    // LY -= RELIC_Y_SPEED;              //reliclif (motor) will move back down to set position

                }
            }


            if (gamepad1.left_bumper) {
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
                telemetry.addData("fg ", fg);
                telemetry.update();

            }
        }
    }
}

