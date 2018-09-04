/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.Range;

/**
 * This OpMode uses the common HardwareK9bot class to define the devices on the robot.
 * All device access is managed through the HardwareK9bot class. (See this class for device names)
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes a basic Tank Drive Teleop for the K9 bot
 * It raises and lowers the arm using the Gampad Y and A buttons respectively.
 * It also opens and closes the claw slowly using the X and B buttons.
 *
 * Note: the configuration of the servos is such that
 * as the arm servo approaches 0, the arm position moves up (away from the floor).
 * Also, as the claw servo approaches 0, the claw opens up (drops the game element).
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="K9bot: GlyphtTest", group="Th30")
@Disabled
public class GlypftTest extends LinearOpMode {

    /* Declare OpMode members. */
    HdwGlyphtTest   robot           = new HdwGlyphtTest();              // Use a K9'shardware
    double          holddownPos     = robot.HD_HOME;                   // Servo safe position
    double          GBPos           = robot.GBOPEN;                      // Servo safe position
    double          GTPos           = robot.GTOPEN;
    double          lift            = 0.0;
    final double    G_SPEED        = 0.06 ;                            // sets rate to move servo

    @Override
    public void runOpMode() throws InterruptedException {

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //Position the lift control servo
            if (gamepad1.left_bumper)
                holddownPos = robot.HD_UP;
                robot.holddown.setPosition(holddownPos);

            if (gamepad1.right_bumper)
                holddownPos = robot.HD_HOME;
                robot.holddown.setPosition(holddownPos);


            // Left joystick controls the lift
            lift = gamepad1.left_stick_y;
            robot.liftMotor.setPower(lift);

            // Use gamepad Y & A to control the first glyph grabber
            if (gamepad1.a)
                GBPos += G_SPEED;
            else if (gamepad1.y)
                GBPos -= G_SPEED;

            // Use gamepad X & B to control the second glyph grabber
            if (gamepad1.x)
                GTPos += G_SPEED;
            else if (gamepad1.b)
                GTPos -= G_SPEED;

            // Move both servos to new position.
            GBPos  = Range.clip(GBPos, robot.GBCLOSE, robot.GBOPEN);
            robot.GlyphBot.setPosition(GBPos);
            GTPos = Range.clip(GTPos, robot.GTCLOSE, robot.GTOPEN);
            robot.GlyphTop.setPosition(GTPos);

            // Send telemetry message to signify robot running;
            telemetry.addData("GBPos",   "%.2f", GBPos);
            telemetry.addData("GTPos",  "%.2f", GTPos);
            telemetry.addData("holddown",  "%.2f", holddownPos);
            telemetry.addData("lift",       "%.2f", lift);

            telemetry.update();

            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            robot.waitForTick(40);
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
    }
}
