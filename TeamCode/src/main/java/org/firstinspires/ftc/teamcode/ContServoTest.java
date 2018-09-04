package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

/**
 * This OpMode scans a single servo back and forwards until Stop is pressed.
 * The code is structured as a LinearOpMode
 * INCREMENT sets how much to increase/decrease the servo position each cycle
 * CYCLE_MS sets the update period.
 *
 * This code assumes a Servo configured with the name "left claw" as is found on a pushbot.
 *
 * NOTE: When any servo position is set, ALL attached servos are activated, so ensure that any other
 * connected servos are able to move freely before running this test.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@Autonomous(name = "ContServTest", group = "Concept")
//@Disabled
public class ContServoTest extends LinearOpMode {
    CRServo crs;
    @Override
    public void runOpMode() {
        crs = hardwareMap.crservo.get("left_hand");
        telemetry.addData(">", "Press Start to scan Servo." );
        telemetry.update();
        waitForStart();
        while(opModeIsActive()){
            crs.setPower(-1);  //Run servo in one direction
            telemetry.addData(">", "First dir");
            telemetry.update();
            sleep (3000);
            crs.setPower(1);  //Run servo in opposite direction
            telemetry.addData(">", "Second Dir");
            telemetry.update();
            sleep (3000);
            crs.setPower(0);  //Stop servo
            telemetry.addData(">", "Middle");
            telemetry.update();
            sleep (3000);
        }
        telemetry.addData(">", "Done"); // Signal done;
        telemetry.update();
    }
}
