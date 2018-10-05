package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

/**
 * Created by FIRSTUser on 10/22/2017.
 */


@TeleOp(name = "DumRelTele", group = "Relic")
//@Disabled
public class DummyRover extends LinearOpMode {
    OpenGLMatrix lastLocation = null;

    VuforiaLocalizer vuforia;

    /* Declare OpMode members. */
    HardwareDummyRover robot = new HardwareDummyRover();     // Use the K9's hardware


    @Override
    public void runOpMode() throws InterruptedException {

        double FLBL = 1.0;
        double FRBR = 1.0;

        // Initialize the hardware variables.
        //The init() method of the hardware class does all the work here

        robot.init(hardwareMap);


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
//        zoneWidth = 0.3;
        //triggerPress = 0.3;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {


            // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
            robot.FL.setPower(0.6);
            robot.BL.setPower(0.6);

            robot.FL.setPower(FLBL);
            robot.BL.setPower(FLBL);
            robot.FR.setPower(FRBR);
            robot.BR.setPower(FRBR);


//            telemetry.addData("Tank = ", Tank);
//            telemetry.update();

        }


            }
        }

