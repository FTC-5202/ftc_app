package org.firstinspires.ftc.teamcode.Dummy;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by FIRSTUser on 10/22/2017.
 */


@TeleOp(name = "DummyBotTele", group = "DummyBot")
@Disabled
public class DummyBot extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareDummyMecanum robot = new HardwareDummyMecanum();     // Use the K9's hardware


    @Override
    public void runOpMode() throws InterruptedException {

        {

        }

       // double FL;
        double FLBL = 1.0;
        double FRBR = 1.0;
        //double RelicLift = 1.0;
       double TgrL1 = 1.0;
        double DoublClamp1 = 1.0;
        double DoublRelicY = 0.0;
        double RelicY = 1.0;
        double zoneWidth = 0.3;
        double triggerPress = 0.5;
        final double FG_SPEED = 0.06;
        //boolean FLIP1 = false;
        //boolean AutoR = false;
        boolean AutoL = false;
        boolean isForward = true;
        boolean rb1Pressed = false;
        double Tank = 0.0;


        // Initialize the hardware variables.
        //The init() method of the hardware class does all the work here

        robot.init(hardwareMap);


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        zoneWidth = 0.3;
        //triggerPress = 0.3;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if (gamepad1.right_bumper && !rb1Pressed) {
                rb1Pressed = true;
            }

            if (!gamepad1.right_bumper && rb1Pressed) {
                isForward = !isForward;
                rb1Pressed = false;
            }

            if (Math.abs(gamepad1.left_stick_x) <= (zoneWidth) || (Math.abs(gamepad1.right_stick_x)) <= (zoneWidth)) {// Searches for if we are engaging Tank drive or Mechanum Drive, no in-between
                Tank = 1.0;
                if (!isForward) {  // if reversed (Tank)
                    FLBL = -gamepad1.right_stick_y;
                    FRBR = -gamepad1.left_stick_y;
                    //RelicLift = (gamepad2.left_stick_y) / 1.5;
                    TgrL1 = (gamepad2.left_stick_y) / 1.5;
                    RelicY = (gamepad2.right_stick_y) / 1.5;

                } else {                              // not reversed (Tank)
                    FLBL = gamepad1.left_stick_y;
                    FRBR = gamepad1.right_stick_y;
                    //RelicLift = (gamepad2.left_stick_y) / 1.5;
                    TgrL1 = (gamepad2.left_stick_y) / 1.5;
                    RelicY = (gamepad2.right_stick_y) / 1.5;
                }

                //} else {
                //BL = -gamepad1.left_stick_x;
                // BR = gamepad1.left_stick_x;
                //FL = gamepad1.left_stick_x;
                //FRFL = -gamepad1.left_stick_x;
                // TgL1 = gamepad2.left_stick_y;

                //if (Math.abs(gamepad1.left_stick_x) >= (zoneWidth) || (Math.abs(gamepad1.right_stick_x)) >= (zoneWidth)) {
                //Tank = 2.0;

                //if (!isForward) {  // if reversed (Mechanum)

                     /*   BL = -gamepad1.right_stick_x;
                        BR = gamepad1.left_stick_x;
                        //FL = gamepad1.right_stick_x;
                        FR = -gamepad1.left_stick_x;
                        TgL1 = (gamepad2.left_stick_y);
                    } else {                              // not reversed (Mechanum)
                        BL = -gamepad1.left_stick_x;//-BL
                        BR = -gamepad1.right_stick_x;//BR
                        //FL = gamepad1.left_stick_x;//FL
                        FR = gamepad1.right_stick_x;//-FR
                        TgL1 = (gamepad2.left_stick_y);
                    }

                    */


            }

            if (gamepad2.y) {
                DoublClamp1 = 1.0;
                telemetry.addData("Clamp is OPEN", gamepad1.y);
                robot.DoublClamp1.setPosition(DoublClamp1);

            }

            //}

            if (gamepad2.a) {
                DoublClamp1 = 0.0;
                telemetry.addData("Clamp is CLOSED", gamepad1.a);
                robot.DoublClamp1.setPosition(DoublClamp1);

            }

            if (gamepad2.x) {
                DoublRelicY = 1.0;
                robot.DoublRelicY.setPosition(DoublRelicY);
            }

            if (gamepad2.b) {
                DoublRelicY =1.0;
                robot.DoublRelicY.setPosition(DoublRelicY);
            }

            robot.FLBLMotor.setPower(FLBL);
            robot.FRBRMotor.setPower(FRBR);
            robot.TgrLift1.setPower(TgrL1);
            robot.RelicY.setPower(RelicY);


            telemetry.addData("Tank = ", Tank);
            telemetry.update();

        }


            }
        }

