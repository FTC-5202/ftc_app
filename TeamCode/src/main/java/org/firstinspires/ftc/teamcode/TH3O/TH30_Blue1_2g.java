package org.firstinspires.ftc.teamcode.TH3O;

import android.graphics.Color;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static org.firstinspires.ftc.teamcode.TH3O.TH3OAutoMethods.Direction.LEFT;
import static org.firstinspires.ftc.teamcode.TH3O.TH3OAutoMethods.Direction.RIGHT;

/**
 * Created by FIRSTUser on 2/21/2018.
 */
//@Autonomous (name = "TH30 Red1 2g")
public class TH30_Blue1_2g extends TH3OAutoMethods {

    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() {
        /* In this section we define the variables
            checkColor  Will be changed to false if we sense a red or blue jewel
            autodone    Will be used to track when we complete autonomous
            vuMarkREAD  Vuforia image recognition has been successful
            ForB        Will become true if we see a blue jewel
            vutarget    Will use Left=1, Right=2, and Center or no reading = 3
            vuCount     how many times we have read the Vuforia mark if multiple readings fail
         */
        boolean checkColor = true;
        boolean vuMarkREAD = false;
        boolean ForB = false;
        int vuTarget = 0;
        int vuCount = 0;
        int autodone = 1;

        setupAll(); //refers to the TH3OAutoMethods: init hardwareMap, motors, servos, and sensors
        telemetry.addLine("INITIATED");

        // Set up the Vuforia image recognition
        final float hsvValues[] = {0F, 0F, 0F};

        r.RVc2.enableLed(r.bLedOn);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AXLb9ZD/////AAAAGc+ylHTIf0+aorS8rw6aoBRMAiybD7XCkifjVKb1gFrWJ+pZOL6huLnful+ArD+R2XN3/ZGcwQl6+4jsRj2e3Y82Sm/yTgANmCQEqhIqLjfWNePdOqmT0apncNRVE8YfklK+VRNs976s0xR2rEPIl4tNaYoGOHqaJl8JfIrZ5CjIIxKV55C5PUdzzgAxR3NS8hR7wGu5H0rX1of4shVf1Nncn3WNKTrsOU//PPBjgE79RIN3G5aUC54lMNkzMfaJ2FwAfTXoMbSUygQiGu1Sh0UizQpgjqzPH8gIt6v8qt542i4Pk5T+gbrculkfzvFhzMQu81EyP2v4TfCNmCsSrFQdRl2Z7pTbWddtn5//e6Px";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;//was BACK
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        waitForStart();

        relicTrackables.activate();

        r.moveServo(r.Rarm, 0.0);
        sleep(500);

        Color.RGBToHSV(r.RVc2.red() * 8, r.RVc2.green() * 8, r.RVc2.blue() * 8, hsvValues);

        telemetry.addData("LED", r.bLedOn ? "On" : "Off");
        telemetry.addData("Red2 ", r.RVc2.red());
        telemetry.addData("Blue2", r.RVc2.blue());
        telemetry.addData("Hue", hsvValues[0]);
        telemetry.update();
        sleep(2000);

        while (opModeIsActive()) {

       /* this section following contains the initial beginning of autonomous: vuforia
            We will begin by setting up the vuCount to continue reading values when they fail
            The while(!vuMarkRead) loop is the initial reading of the target values.
            When these values are read we identify them by giving them a number according to their directional value
            If these values are read more than 100 times, than the program will default to the vuTarget = 0.
            When these previous conditions are met, the program will move out of this loop. */

            while (!vuMarkREAD) {
                vuCount = vuCount + 1;
                RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
                telemetry.addData("VuMark", "%s visible", vuMark);
                telemetry.update();

                if (vuMark == RelicRecoveryVuMark.LEFT) {
                    vuTarget = 1;
                    vuMarkREAD = true;
                }
                if (vuMark == RelicRecoveryVuMark.RIGHT) {
                    vuTarget = 2;
                    vuMarkREAD = true;
                }
                if (vuMark == RelicRecoveryVuMark.CENTER) {
                    vuTarget = 3;
                    vuMarkREAD = true;
                }
                if (vuCount >= 100) {
                    vuMarkREAD = true;
                    vuTarget =3;
                }
            }

            /* checkColor ensures that we only make one attempt to read the jewel color
            the following section will determine what our robot will do depending on the read color value
            The if (r.RVc2 *blue or *red) loop will determine whether our robot will back up or go forward.

            the following loop will only be executed if the following conditions are true:
                -the sensor reads blue
                -the blue value read is higher than the red value, if any
                -if checkColor is still true
            eTurnBot and moveBot methods are defined in TH3O AutoMethods*/

            if ((r.RVc2.blue() >= 1.0) && (r.RVc2.blue() > r.RVc2.red()) & checkColor) {
                telemetry.addData("Blue2", r.RVc2.blue());
                telemetry.update();
                //Paint rollers close
                r.moveServo(r.Lprgrab2, 0.8);
                r.moveServo(r.Rprgrab2, 0.2);
                sleep(500);

                moveBot(5, FORWARD, 1.0);
                sleep(500);
                r.moveServo(r.Rarm, 0.9);
                sleep(1000);
                moveBot(28.5, BACKWARD, 1.0);
                sleep(500);
                eTurnBot(10, RIGHT, 1.0, 1.0);
                sleep(700);
                r.moveServo(r.Lprgrab2, 0.4);
                r.moveServo(r.Rprgrab2, 0.4);
                sleep(700);
                ForB = true;
                checkColor = false;
            }

            //The code below will be executed if we see a red jewel.
            if ((r.RVc2.red() >= 1.0) && (r.RVc2.red() > r.RVc2.blue()) & checkColor) {
                telemetry.addData("RVc2 Red ", r.RVc2.red());
                telemetry.update();

                r.moveServo(r.Lprgrab2, 0.8);
                r.moveServo(r.Rprgrab2, 0.2);
                sleep(800);

                moveBot(3, BACKWARD, 1.0);
                r.moveServo(r.Rarm, 0.9);
                sleep(500);
                moveBot(18.7, BACKWARD, 1.0);
                sleep(500);
                r.moveServo(r.Lprgrab2, 0.4);
                r.moveServo(r.Rprgrab2, 0.4);
                sleep(700);
                checkColor = false;
            }
            //If a red or blue jewel is not read, we will execute the following code.
            if (checkColor){ //need to change distances to match red
                telemetry.addLine(" NO Color Val");
                telemetry.update();

                r.moveServo(r.Rarm, 0.9);
                moveBot(19, BACKWARD, 1.0);
                sleep(500);
                r.moveServo(r.Lprgrab2, 0.57);
                r.moveServo(r.Rprgrab2, 0.28);
                checkColor = false;
            }
            /* the following section will determine which path our robot will follow
                vuTarget value in combination with ForB allows us to follow a particular path.
                autodone ensures that only one path is executed per autonomous run.
                Once a sequence is completed, we turn the autodone boolean to true.
                The eTurnBot and moveBot methods are defined in TH3O AutoMethods.

                The loop below is for the Right vuTarget when we have sensed the red jewel.*/
            if (vuTarget == 2 && autodone == 1 && ForB == false) {
                telemetry.addLine("RIGHT COLUMN, Saw: RED JEWEL");
                telemetry.update();
                sleep(1000);
                // Turn robot 105 degrees to the right at 100% power
                eTurnBot(105, RIGHT, 1.0, 1.0);
                sleep(500);
                // in Blue Auto, our robot faces backwards, so the polarity is reversed
                //Our robot will move "fwd" 13.5 inches at 100% power
                moveBot(13.5, BACKWARD, 1.0);
                sleep(500);
                // Turn robot 56 degrees to the left at 100% power
                eTurnBot(56, LEFT, 1.0, 1.0);
                sleep(500);
                //robot moves "fwd" 10 inches at 100% power
                moveBot(10, BACKWARD, 1.0);
                sleep(500);
                // Paint rollers spin to push glyph out
                r.Rprspin2.setPower(-0.6);
                r.Lprspin2.setPower(0.6);
                sleep(1000);
                // Paint rollers stop
                r.Rprspin2.setPower(0.0);
                r.Lprspin2.setPower(0.0);
                // Paint rollers open
                r.moveServo(r.Lprgrab2, 0.8);
                r.moveServo(r.Rprgrab2, 0.2);
                sleep(500);
                //robot moves "backwards" at 40% power
                moveBot(7, FORWARD, 0.4);
                //make autodone=2 so no vuTarget sections are repeated
                autodone = 2;
            }

            //The loop below is for the Right vuTarget when we have sensed the blue jewel.
            //if statements in this area are slight variations on one another
            if (vuTarget == 2 && autodone == 1 && ForB == true) { //good
                telemetry.addLine("RIGHT COLUMN, Saw: BLUE JEWEL");
                telemetry.update();
                sleep(1000);
                eTurnBot(105, RIGHT, 1.0, 1.0);
                sleep(500);
                moveBot(15, BACKWARD, 1.0);
                sleep(500);
                eTurnBot(62, LEFT, 1.0, 1.0);
                sleep(500);
                moveBot(9.5, BACKWARD, 1.0);
                sleep(500);
                r.Rprspin2.setPower(-0.6);
                r.Lprspin2.setPower(0.6);
                sleep(1000);
                r.Rprspin2.setPower(0.0);
                r.Lprspin2.setPower(0.0);
                r.moveServo(r.Lprgrab2, 0.8);
                r.moveServo(r.Rprgrab2, 0.2);
                sleep(500);
                moveBot(7, FORWARD, 0.4);
                autodone = 2;
            }

            //The loop below is for the Left vuTarget when we have sensed the red jewel.
            //if statements in this area are slight variations on one another
            if (vuTarget == 1 && autodone == 1 && ForB == false) { //good
                telemetry.addLine("LEFT COLUMN, Saw: RED JEWEL");
                telemetry.update();
                sleep(1500);
                eTurnBot(35, RIGHT, 1.0, 1.0);
                sleep(500);
                moveBot(7, BACKWARD, 1.0);
                sleep(500);
                r.Rprspin2.setPower(-0.6);
                r.Lprspin2.setPower(0.6);
                sleep(1000);
                r.Rprspin2.setPower(0.0);
                r.Lprspin2.setPower(0.0);
                r.moveServo(r.Lprgrab2, 0.8);
                r.moveServo(r.Rprgrab2, 0.2);
                sleep(500);
                moveBot(8, FORWARD, 0.4);
                autodone = 2;
            }
            //The loop below is for the Left vuTarget when we have sensed the blue jewel.
            //if statements in this area are slight variations on one another
            if (vuTarget == 1 && autodone == 1 && ForB == true) { //good for now, need to find better start position
                telemetry.addLine("LEFT COLUMN, Saw: BLUE JEWEL");
                telemetry.update();
                sleep(1500);
                eTurnBot(25, RIGHT, 1.0, 1.0);
                sleep(500);
                moveBot(10, BACKWARD, 1.0);
                sleep(500);
                r.Rprspin2.setPower(-0.6);
                r.Lprspin2.setPower(0.6);
                sleep(1000);
                r.Rprspin2.setPower(0.0);
                r.Lprspin2.setPower(0.0);
                r.moveServo(r.Lprgrab2, 0.8);
                r.moveServo(r.Rprgrab2, 0.2);
                sleep(500);
                moveBot(8, FORWARD, 0.4);
                autodone = 2;
            }

            //The loop below is for the Center vuTarget when we have sensed the red jewel.
            //It is also used if we did not read the vuTarget.
            //if statements in this area are slight variations on one another
            if ((vuTarget == 3 || vuTarget == 0) && autodone == 1 && ForB == false) { //good
                telemetry.addLine("CENTER COLUMN, Saw: RED JEWEL");
                telemetry.update();
                sleep(1500);
                eTurnBot(76, RIGHT, 1.0, 1.0);
                sleep(500);
                moveBot(9.5, BACKWARD, 1.0);
                sleep(500);
                eTurnBot(15, LEFT, 1.0, 1.0);
                r.Rprspin2.setPower(-0.6);
                r.Lprspin2.setPower(0.6);
                sleep(1000);
                r.Rprspin2.setPower(0.0);
                r.Lprspin2.setPower(0.0);
                r.moveServo(r.Rprgrab2, 0.2);
                r.moveServo(r.Lprgrab2, 0.8);
                sleep(500);
                moveBot(8, FORWARD, 0.4);
                autodone = 2;
            }
            //The loop below is for the Center vuTarget when we have sensed the blue jewel.
            //if statements in this area are slight variations on one another
            if ((vuTarget == 3 || vuTarget == 0) && autodone == 1 && ForB == true) { //good
                telemetry.addLine("CENTER COLUMN, Saw: BLUE JEWEL");
                telemetry.update();
                sleep(1500);
                moveBot(3, BACKWARD, 1.0);
                sleep(500);
                eTurnBot(73, RIGHT, 1.0, 1.0);
                sleep(500);
                moveBot(10, BACKWARD, 1.0);
                sleep(500);
                eTurnBot(10, LEFT, 1.0, 1.0);
                r.Rprspin2.setPower(-0.6);
                r.Lprspin2.setPower(0.6);
                sleep(1000);
                r.Rprspin2.setPower(0.0);
                r.Lprspin2.setPower(0.0);
                r.moveServo(r.Rprgrab2, 0.2);
                r.moveServo(r.Lprgrab2, 0.8);
                sleep(500);
                moveBot(8, FORWARD, 0.4);
                autodone = 2;
            }
            /*Once we score the glyph in the key column, we move to grab another glyph from the glyph pit
            and return to the cryptobox parking zone with the glyph. */

            //This first section will be executed after we score the glyph in the right column.
            if (autodone == 2 && vuTarget == 2) {
                moveBot(4, FORWARD, 1.0);
                eTurnBot(90, RIGHT, 1.0, 1.0);
                moveBot(24, BACKWARD, 1.0);
                eTurnBot(110, RIGHT, 1.0, 1.0);
                moveBot(40, BACKWARD, 1.0);
                r.Rprspin2.setPower(0.6);
                r.Lprspin2.setPower(-0.6);
                sleep(700);
                r.moveServo(r.Rprgrab2, 0.4);
                r.moveServo(r.Lprgrab2, 0.4);
                sleep(1500);
                r.Rprspin2.setPower(0.0);
                r.Lprspin2.setPower(0.0);
                moveBot(40, FORWARD, 1.0);
                eTurnBot(110, RIGHT, 1.0, 1.0);
                moveBot(24, BACKWARD, 1.0);
                autodone = 3;
            }
            //Center column
             if (autodone == 2 && (vuTarget == 3 || vuTarget == 0)) {
                moveBot(4, FORWARD, 1.0);
                eTurnBot(90, RIGHT, 1.0, 1.0);
                moveBot(30, BACKWARD, 1.0);
                eTurnBot(110, RIGHT, 1.0, 1.0);
                moveBot(40, BACKWARD, 1.0);
                r.Rprspin2.setPower(0.6);
                r.Lprspin2.setPower(-0.6);
                sleep(700);
                r.moveServo(r.Rprgrab2, 0.4);
                r.moveServo(r.Lprgrab2, 0.4);
                sleep(1500);
                r.Rprspin2.setPower(0.0);
                r.Lprspin2.setPower(0.0);
                moveBot(40, FORWARD, 1.0);
                eTurnBot(110, RIGHT, 1.0, 1.0);
                moveBot(24, BACKWARD, 1.0);
                autodone = 3;
            }
            //Left column
            if (autodone == 2 && vuTarget == 1) {
                moveBot(3, FORWARD, 1.0);
                eTurnBot(90, RIGHT, 1.0, 1.0);
                moveBot(36, BACKWARD, 1.0);
                eTurnBot(110, RIGHT, 1.0, 1.0);
                moveBot(40, BACKWARD, 1.0);
                r.Rprspin2.setPower(0.6);
                r.Lprspin2.setPower(-0.6);
                sleep(700);
                r.moveServo(r.Rprgrab2, 0.4);
                r.moveServo(r.Lprgrab2, 0.4);
                sleep(1500);
                r.Rprspin2.setPower(0.0);
                r.Lprspin2.setPower(0.0);
                moveBot(40, FORWARD, 1.0);
                eTurnBot(110, RIGHT, 1.0, 1.0);
                moveBot(24, BACKWARD, 1.0);
                autodone = 3;
            }
            telemetry.addData("VuMark", "%s visible", vuTarget);
            telemetry.update();
        }
    }
}
