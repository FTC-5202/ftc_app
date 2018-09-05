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
package org.firstinspires.ftc.teamcode.TH3O.OldTheo;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.OldMisc.HardwareTheoMecanum;

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
@Autonomous(name="Th30-AutoR2", group="TH-30")
@Disabled
public class TH30AutoR2 extends LinearOpMode {

    // ColorSensor colorSensor;  //hardware device object
    // ColorSensor colorSensor2; //hardware device object

    /* Declare OpMode members. */
    HardwareTheoMecanum robot = new HardwareTheoMecanum();     // Use the K9's hardware
    double Rarm;                 // Servo safe position
    double Larm;              // Servo safe position
    static final double INCREMENT = 0.02;     // amount to slew servo each CYCLE_MS cycle
    static final int CYCLE_MS = 50;     // period of each cycle
    static final double MAX_POS = 1.0;     // Maximum rotational position
    static final double MIN_POS = 0.0;     // Minimum rotational position
    final double CLAW_SPEED = 0.01;                            // sets rate to move servo
    final double ARM_SPEED = 0.06;                            // sets rate to move servo
    double position = (0.3);
    double FWD = (0.0);


    @Override
    public void runOpMode() throws InterruptedException {

        boolean checkColor = true;
        boolean autodone = false;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        robot.Rarm.setPosition(0.8);
        robot.Larm.setPosition(0.1);

        // hsvValues is an array that will hold the hue, saturation, and value information.
        final float hsvValues[] = {0F, 0F, 0F};

        robot.RS1.enableLed(robot.bLedOn);
        robot.RS2.enableLed(robot.bLedOn);
       // robot.LS1.enableLed(!robot.bLedOn);
        robot.LS2.enableLed(!robot.bLedOn);

        // Send telemetry message to signify robot waiting;
        /*telemetry.addData("Say", "Hello Driver");
        Color.RGBToHSV(robot.RS1.red() * 8, robot.RS1.green() * 8, robot.RS1.blue() * 8, hsvValues);
        Color.RGBToHSV(robot.RS2.red() * 8, robot.RS2.green() * 8, robot.RS2.blue() * 8, hsvValues);
        telemetry.addData("LED", robot.bLedOn ? "On" : "Off");
        telemetry.addData("Red  ", robot.RS1.red());
        telemetry.addData("Red2  ", robot.RS2.red());
        telemetry.addData("Blue ", robot.RS1.blue());
        telemetry.addData("Blue2 ", robot.RS2.blue());
        telemetry.update();

        */

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        robot.Fg.setPosition(0.0);
        sleep(500);
       // robot.FgLift.setPower(1.0/0.5);
       // sleep(500);
       // robot.FgLift.setPower(0.0);

        robot.Rarm.setPosition(0.2);
        sleep(500);
        robot.Rarm.setPosition(0.1);
        sleep(1000);
        Color.RGBToHSV(robot.RS1.red() * 8, robot.RS1.green() * 8, robot.RS1.blue() * 8, hsvValues);
        Color.RGBToHSV(robot.RS2.red() * 8, robot.RS2.green() * 8, robot.RS2.blue() * 8, hsvValues);



        // ElapsedTime period  = new ElapsedTime();

        // send the info back to driver station using telemetry function.
        telemetry.addData("LED", robot.bLedOn ? "On" : "Off");
        telemetry.addData("Red  ", robot.RS1.red());
        telemetry.addData("Red2  ", robot.RS2.red());
        telemetry.addData("Blue ", robot.RS1.blue());
        telemetry.addData("Blue2 ", robot.RS2.blue());
        telemetry.addData("Hue", hsvValues[0]);
        telemetry.update();
        sleep(1000);

        while (opModeIsActive()) {

/*            // convert the RGB values to HSV values.
            Color.RGBToHSV(robot.RS1.red() * 8, robot.RS1.green() * 8, robot.RS1.blue() * 8, hsvValues);
            Color.RGBToHSV(robot.RS2.red() * 8, robot.RS2.green() * 8, robot.RS2.blue() * 8, hsvValues);

            // ElapsedTime period  = new ElapsedTime();

            // send the info back to driver station using telemetry function.
            telemetry.addData("LED", robot.bLedOn ? "On" : "Off");
            telemetry.addData("Red  ", robot.RS1.red());
            telemetry.addData("Red2  ", robot.RS2.red());
            telemetry.addData("Blue ", robot.RS1.blue());
            telemetry.addData("Blue2 ", robot.RS2.blue());
            telemetry.addData("Hue", hsvValues[0]);
            telemetry.update();
*/

            if ((robot.RS1.red()  >= 2.0) & checkColor) {          //when the value of the color red > 1.0
                telemetry.addData("RS1 red ", robot.RS1.red());
                telemetry.update();
                sleep(1000);
                FWD = -0.25;
                robot.BLMotor.setPower(FWD);
                robot.BRMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.FRMotor.setPower(FWD);
                sleep(1500);
                FWD = 0.0;
                robot.BLMotor.setPower(FWD);
                robot.BRMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.FRMotor.setPower(FWD);
                robot.Rarm.setPosition(0.6);
                checkColor = false;
            }

            if ((robot.RS2.red() >= 2.0) & checkColor) {       //when the value of the color red2 > 1.0
                telemetry.addData("RS2 red ", robot.RS2.red());
                telemetry.update();
                sleep(1000);
                FWD = -0.25;
                robot.BLMotor.setPower(FWD);
                robot.BRMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.FRMotor.setPower(FWD);
                sleep(1500);
                FWD = 0.0;
                robot.BLMotor.setPower(FWD);
                robot.BRMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.FRMotor.setPower(FWD);
                robot.Rarm.setPosition(0.6);
                checkColor = false;
            }


            if ((robot.RS1.blue()  >= 2.0) & checkColor) {          //when the value of the color blue > 1.0
                telemetry.addData("RS1 blue  ", robot.RS1.blue());
                telemetry.update();
                sleep(2000);
                FWD = 0.25;
                robot.BLMotor.setPower(FWD);
                robot.BRMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.FRMotor.setPower(FWD);
                sleep(400);
                FWD = 0.0;
                robot.BLMotor.setPower(FWD);
                robot.BRMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.FRMotor.setPower(FWD);
                robot.Rarm.setPosition(0.6);
                FWD = -0.25;
                robot.BLMotor.setPower(FWD);
                robot.BRMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.FRMotor.setPower(FWD);
                sleep(1900);
                FWD = 0.0;
                robot.BLMotor.setPower(FWD);
                robot.BRMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.FRMotor.setPower(FWD);
                checkColor = false;
            }

            if ((robot.RS2.blue() >= 2.0) & checkColor) {       //when the value of the color blue > 1.0
                telemetry.addData("RS2 Blue  ", robot.RS2.blue());
                telemetry.update();
                sleep(2000);
                FWD = 0.25;
                robot.BLMotor.setPower(FWD);
                robot.BRMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.FRMotor.setPower(FWD);
                sleep(400);
                FWD = 0.0;
                robot.BLMotor.setPower(FWD);
                robot.BRMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.FRMotor.setPower(FWD);
                robot.Rarm.setPosition(0.6);
                FWD = -0.25;
                robot.BLMotor.setPower(FWD);
                robot.BRMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.FRMotor.setPower(FWD);
                sleep(1900);
                FWD = 0.0;
                robot.BLMotor.setPower(FWD);
                robot.BRMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.FRMotor.setPower(FWD);
                checkColor = false;
            }

            if (checkColor) {
                telemetry.addData("Red No RS1 ", robot.RS1.red());
                telemetry.addData("Red2 No RS2 ", robot.RS2.red());
                telemetry.addData("Blue No RS1", robot.RS1.blue());
                telemetry.addData("Blue2 No RS2 ", robot.RS2.blue());
                robot.Rarm.setPosition(0.6);
                telemetry.update();
                sleep(1000);
                FWD = -0.25;
                robot.BLMotor.setPower(FWD);
                robot.BRMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.FRMotor.setPower(FWD);
                sleep(1500);
                FWD = 0.0;
                robot.BLMotor.setPower(FWD);
                robot.BRMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.FRMotor.setPower(FWD);
                checkColor = false;
            }

            if (!autodone) {
                FWD = -0.25;
                robot.FRMotor.setPower(FWD);
                robot.BLMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.BRMotor.setPower(FWD);
                sleep(400);
                FWD = 0.0;
                robot.BLMotor.setPower(FWD);
                robot.BRMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.FRMotor.setPower(FWD);
                sleep(500);
                FWD = -1.0;
                robot.FRMotor.setPower(-FWD);
                robot.BLMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.BRMotor.setPower(-FWD);
                sleep(400);
                FWD = 0.0;
                robot.BLMotor.setPower(FWD);
                robot.BRMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.FRMotor.setPower(FWD);
                sleep(500);
                FWD = -0.25;
                robot.BLMotor.setPower(FWD);
                robot.BRMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.FRMotor.setPower(FWD);
                sleep(1500);
                FWD = 0.0;
                robot.BLMotor.setPower(FWD);
                robot.BRMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.FRMotor.setPower(FWD);
                //robot.FgLift.setPower(-1 / 0.5);
               // sleep(500);
               // robot.FgLift.setPower(0.0);
                robot.Fg.setPosition(0.35);
                FWD = 0.25;
                robot.BLMotor.setPower(FWD);
                robot.BRMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.FRMotor.setPower(FWD);
                sleep(500);
                FWD = 0.0;
                robot.BLMotor.setPower(FWD);
                robot.BRMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.FRMotor.setPower(FWD);

                FWD = -1.0;
                robot.FRMotor.setPower(-FWD);
                robot.BLMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.BRMotor.setPower(-FWD);
                sleep(400);
                FWD = 0.0;
                robot.BLMotor.setPower(FWD);
                robot.BRMotor.setPower(FWD);
                robot.FLMotor.setPower(FWD);
                robot.FRMotor.setPower(FWD);
                sleep(500);

                autodone = true;
            }

        }
    }

}