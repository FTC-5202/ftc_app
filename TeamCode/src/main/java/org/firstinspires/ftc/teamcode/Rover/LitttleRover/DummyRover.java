package org.firstinspires.ftc.teamcode.Rover.LitttleRover;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

/**
 * Created by FIRSTUser on 10/22/2017.
 */


@Autonomous(name = "DumRelTele", group = "Relic")
@Disabled
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
            robot.FLBL.setPower(0.6);

            robot.FLBL.setPower(FLBL);
            robot.FRBR.setPower(FRBR);

//            telemetry.addData("Tank = ", Tank);
//            telemetry.update();

        }


            }
        }

