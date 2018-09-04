package org.firstinspires.ftc.teamcode.Dummy;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by FIRSTUser on 10/22/2017.
 */


@TeleOp(name = "DummyGlyphTEST", group = "DummyBot")
@Disabled
public class DummyGlyphTEST extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareDummyMecanumNEW robot = new HardwareDummyMecanumNEW();     // Use the K9's hardware


    @Override
    public void runOpMode() throws InterruptedException {

        {

        }

       // double FL;
        double FR;
        double BL;
        double BR;
        double FL;
        double TgL1;
        double GRservo = 0.0;
        double DoublClampR = 0.0;
        double DoublClampL = 0.0;
        double DoublSweepR = 0.0;
        double DoublSweepL = 0.0;
        double zoneWidth = 0.3;
        double triggerPress = 0.5;
        final double FG_SPEED = 0.06;
        //boolean FLIP1 = false;
        //boolean AutoR = false;
        boolean AutoL = false;
        boolean isForward = true;
        boolean rb1Pressed = false;
        boolean Sweep = false;

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
                    //FL = -gamepad1.right_stick_y;
                    FR = -gamepad1.left_stick_y;
                    TgL1 = (gamepad2.left_stick_y);

                } else {                              // not reversed (Tank)
                    BL = gamepad1.left_stick_y;
                    BR = gamepad1.right_stick_y;
                    //FL = gamepad1.left_stick_y;
                    FR = gamepad1.right_stick_y;
                    TgL1 = (gamepad2.left_stick_y);
                }

            } else {
                BL = -gamepad1.left_stick_x;
                BR = gamepad1.left_stick_x;
                //FL = gamepad1.left_stick_x;
                FR = -gamepad1.left_stick_x;
                TgL1 = gamepad2.left_stick_y;

                if (Math.abs(gamepad1.left_stick_x) >= (zoneWidth) || (Math.abs(gamepad1.right_stick_x)) >= (zoneWidth)) {
                    Tank = 2.0;

                    if (!isForward) {  // if reversed (Mechanum)

                        BL = -gamepad1.right_stick_x;
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


                }

            }

            if (gamepad1.y) {
                DoublClampR= 0.0;
                DoublClampL = 0.0;
                telemetry.addData("Clamp is OPEN", gamepad1.right_bumper);
                robot.DoublClampR.setPosition(DoublClampR);
                robot.DoublClampL.setPosition(DoublClampL);

            }

            //}

            if (gamepad1.a) {
                DoublClampR = 1.0;
                DoublClampL = 1.0;
                telemetry.addData("Clamp is CLOSED", gamepad1.left_bumper);
                robot.DoublClampR.setPosition(DoublClampR);
                robot.DoublClampL.setPosition(DoublClampL);

            }

            if (gamepad1.x && !Sweep) {
                Sweep = true;
                DoublSweepR = -1.0;
                DoublSweepL = 1.0;
                telemetry.addData("Sweeper FORWARD", gamepad1.x);
                robot.DoublSweepR.setPosition(DoublSweepR);
                robot.DoublSweepL.setPosition(DoublSweepL);

            }

            if (gamepad1.x && Sweep) {
                Sweep = false;
                DoublSweepR = 0.0;
                DoublSweepL = 0.0;
                telemetry.addData("Sweeper NULL", gamepad1.x);

            }

            if (gamepad1.b && !Sweep) {
                Sweep = true;
                DoublSweepR = 1.0;
                DoublSweepL = -1.0;
                telemetry.addData("Sweeper REVERSE", gamepad1.b);
                robot.DoublSweepR.setPosition(DoublSweepR);
                robot.DoublSweepL.setPosition(DoublSweepL);

            }

            if (gamepad1.b && Sweep) {
                Sweep = false;
                DoublSweepR = 0.0;
                DoublSweepL = 0.0;
                telemetry.addData("Sweeper Null", gamepad1.b);
                robot.DoublSweepR.setPosition(DoublSweepR);
                robot.DoublSweepL.setPosition(DoublSweepL);



                    }
                }
            }



                //robot.BLMotor.setPower(BL);
                //robot.BRMotor.setPower(BR);
                //robot.FRMotor.setPower(FR);
                //robot.FLMotor.setPower(FL);
                //robot.TgrLift1.setPower(TgL1);


                //telemetry.addData("Tank = ", Tank);
                //telemetry.update();


            }

