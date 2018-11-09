package org.firstinspires.ftc.teamcode.Dummy;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by FIRSTUser on 10/22/2017.
 */


@TeleOp(name = "DummyRover", group = "DummyBot")
@Disabled
public class DummyRelic extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareDummyRelic robot = new HardwareDummyRelic();


    @Override
    public void runOpMode() throws InterruptedException {

       // double FL;
        double FLBL = 0.0;
        double FRBR = 0.0;
        double Rel = 0.0;
        double LifPos = 0.0;
        double MidPos = 0.0;
        double GrabPos = 0.0;
        double CrPwrRev    =0.0;
        double CrPwrHT = 0.0;

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
            FLBL = -gamepad1.right_stick_y;
            FRBR = -gamepad1.left_stick_y;

            if(gamepad1.right_trigger >= 0.5  && gamepad1.left_trigger < 0.5) {
                Rel = 0.5;
            }
            if(gamepad1.left_trigger >= 0.5 && gamepad1.right_trigger < 0.5) {
                Rel = -0.5;
            }
            if(gamepad1.left_trigger < 0.5 && gamepad1.right_trigger < 0.5) {
                Rel = 0.0;
            }

            if(gamepad1.right_bumper) GrabPos = 0.0;
            if(gamepad1.left_bumper) GrabPos = 1.0;

            if (gamepad1.y) LifPos = 0.0;
            if (gamepad1.a) LifPos = 1.0;

            if (gamepad1.x) MidPos = 0.5;
            if (gamepad1.b) MidPos = 1.0;

            if (gamepad2.y) CrPwrHT = 0.6;
            if (gamepad2.a) CrPwrHT = -0.6;
            if (gamepad2.x) CrPwrHT = 0.825;
            if (gamepad2.b) CrPwrHT = -0.825;

            robot.FLBLMotor.setPower(FLBL);
            robot.FRBRMotor.setPower(FRBR);
            robot.RelicMotor.setPower(Rel);
            robot.Grab.setPosition(GrabPos);
            robot.Mid.setPosition(MidPos);
            robot.Lift.setPosition(LifPos);
            robot.CrRev.setPower(CrPwrRev);
            robot.CrHiTec.setPower(CrPwrHT);
        }

    }
}

