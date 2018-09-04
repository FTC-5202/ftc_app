package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;

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
@Autonomous(name = "CST2", group = "Concept")
//@Disabled
public class CST2 extends LinearOpMode {
    CRServo crs;
    double pwr = 1.;
    @Override
    public void runOpMode() {
        crs = hardwareMap.crservo.get("left_hand");
        telemetry.addData(">", "Press Start to scan Servo." );
        telemetry.update();
        waitForStart();
        while(opModeIsActive()){
            crs.setPower(pwr);  //Run servo at pwr level
            telemetry.addData("Power = ","%5.2f", pwr);
            telemetry.update();
            sleep (2000);
            pwr = pwr-0.1;
        }
        telemetry.addData(">", "Done"); // Signal done;
        telemetry.update();
    }
}
