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

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

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
@Autonomous(name="K9bot: TheoMRTEST", group="K9bot")
@Disabled
public class TheoMRTEST extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareTheoMecanum robot = new HardwareTheoMecanum();     // Use the K9's hardware
    double armPosition = robot.ARM_HOME;                   // Servo safe position
    double clawPosition = robot.CLAW_HOME;                  // Servo safe position
    static final double INCREMENT = 0.02;     // amount to slew servo each CYCLE_MS cycle
    static final int CYCLE_MS = 50;     // period of each cycle
    static final double MAX_POS = 1.0;     // Maximum rotational position
    static final double MIN_POS = 0.0;     // Minimum rotational position
    private ElapsedTime runtime = new ElapsedTime();
    private OpticalDistanceSensor myLight;
    private double myLightReadingAverage;
    public final static double WHITE_THRESHOLD = 0.28;// was 0.52

    private static final double LIGHT_MAXTHRESHOLD = 0.5;
    final double CLAW_SPEED = 0.01;                            // sets rate to move servo
    final double ARM_SPEED = 0.06;                            // sets rate to move servo
    double position = (0.3);
    ColorSensor colorSensor;  //hardware device object

    @Override
    public void runOpMode() throws InterruptedException {

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        // bPrevState and bCurrState represent the previous and current state of the button.

        boolean bPrevState = false;
        boolean bCurrState = false;
        boolean bValue = false;

        // bLedOn represents the state of the LED.
        boolean bLedOn = true;

        // get a reference to our ColorSensor object.
        colorSensor = hardwareMap.get(ColorSensor.class, "sensor_color");

        // Set the LED in the beginning
        colorSensor.enableLed(bLedOn);
        {

        }

        double lf;
        double rf;
        double lr;
        double rr;
        double armPosition = robot.ARM_HOME;                  // Servo safe position
        double clawPosition = robot.CLAW_HOME;                  // Servo safe position
        // double position = (MAX_POS - MIN_POS) / 2; // Start at halfway position
        // boolean rampUp = true;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        //robot.arm.setPosition(0.8);

        // run until the end of the match (driver presses STOP)
        //while (opModeIsActive()) {
        // slew the servo, according to the rampUp (direction) variable.
            /*if (rampUp) {
                // Keep stepping up until we hit the max value.
                position += INCREMENT;
                if (position >= MAX_POS) {
                    position = MAX_POS;
                    rampUp = !rampUp;   // Switch ramp direction
                }

                else {
                    // Keep stepping down until we hit the min value.
                    position -= INCREMENT;
                    if (position <= CLAW_MAX_RANGE) {
                        position = CLAW_MAX_RANGE;
                        rampUp = true;
                        //rampUp = !rampUp;  // Switch ramp direction
                    }
                }
            }
            */
      //  robot.claw.setPosition(position);
        sleep(1000);
        while (position > 0.14) {
            position -= INCREMENT;
       //     robot.claw.setPosition(position);
            sleep(500);
        }

        //robot.claw.setPosition(0.22);
        sleep(500);
       // robot.arm.setPosition(0.9);
        sleep(500);



      //  robot.claw.setPosition(0.3);
        sleep(500);
      //  robot.claw.setPosition(0.22);
        sleep(1000);
      //  robot.arm.setPosition(0.3);
        sleep(500);
        sleep(CYCLE_MS);
        idle();

            /*robot.claw.setPosition(0.1); //moves claw to left for sweep
            sleep(1000);
            robot.claw.setPosition(0.3);  //moves claw to right for sweep
            sleep(1000);
            robot.claw.setPosition(0.1);
            sleep(1000);
            robot.claw.setPosition(0.2); //moves claw to home position
            sleep(1000);
            robot.arm.setPosition(0.9); //moves arm past the home position in middle of target objects.

            }



            // Move both servos to new position.
            // armPosition = Range.clip(armPosition, robot.ARM_MIN_RANGE, robot.ARM_MAX_RANGE);
            //robot.arm.setPosition(armPosition);
            // clawPosition = Range.clip(clawPosition, 0.0, 1.0);
            // robot.claw.setPosition(clawPosition);


        /* check the status of the x button on either gamepad.
        bCurrState = gamepad1.x;

        // check for button state transitions.
        if (bCurrState && (bCurrState != bPrevState)) {

            // button is transitioning to a pressed state. So Toggle LED
            bLedOn = !bLedOn;
            colorSensor.enableLed(bLedOn);
        }

        // update previous state variable.
        bPrevState = bCurrState;

        */

        // convert the RGB values to HSV values.
        while (opModeIsActive()) {
            Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

            // send the info back to driver station using telemetry function.
            //telemetry.addData("LED", bLedOn ? "On" : "Off");
            telemetry.addData("Clear", colorSensor.alpha());
            telemetry.addData("Red  ", colorSensor.red());
            telemetry.addData("Green", colorSensor.green());
            telemetry.addData("Blue ", colorSensor.blue());
            telemetry.addData("Hue", hsvValues[0]);

            // change the background color to match the color detected by the RGB sensor.
            // pass a reference to the hue, saturation, and value array as an argument
            // to the HSVToColor method.
            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                }
            });

            telemetry.update();
        }

    }

}
