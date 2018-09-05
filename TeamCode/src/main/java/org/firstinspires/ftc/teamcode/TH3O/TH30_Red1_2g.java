package org.firstinspires.ftc.teamcode.TH3O;

import android.graphics.Color;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static org.firstinspires.ftc.teamcode.TH3O.TH3OAutoMethods.Direction.LEFT;
import static org.firstinspires.ftc.teamcode.TH3O.TH3OAutoMethods.Direction.RIGHT;

/**
 * Created by FIRSTUser on 2/21/2018.
 */
//@Autonomous (name = "Th30 Red1 2gl")
public class TH30_Red1_2g extends TH3OAutoMethods  {

    VuforiaLocalizer vuforia;


    @Override public void runOpMode() {
        boolean checkColor = true;
        boolean vuMarkREAD = false;
        boolean ForB = false;
        int vuTarget = 0;
        int vuCount = 0;
        int autodone = 1;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */

        setupAll(); //reference Electrical setting up motors, servos, and sensors
        telemetry.addLine("INITIATED");

        //telemetry.addData(" myMarkValue = ", myMarkValue);
        telemetry.update();

        // hsvValues is an array that will hold the hue, saturation, and value information.
        final float hsvValues[] = {0F, 0F, 0F};


        r.RVc2.enableLed(r.bLedOn);


        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);


        parameters.vuforiaLicenseKey = "AXLb9ZD/////AAAAGc+ylHTIf0+aorS8rw6aoBRMAiybD7XCkifjVKb1gFrWJ+pZOL6huLnful+ArD+R2XN3/ZGcwQl6+4jsRj2e3Y82Sm/yTgANmCQEqhIqLjfWNePdOqmT0apncNRVE8YfklK+VRNs976s0xR2rEPIl4tNaYoGOHqaJl8JfIrZ5CjIIxKV55C5PUdzzgAxR3NS8hR7wGu5H0rX1of4shVf1Nncn3WNKTrsOU//PPBjgE79RIN3G5aUC54lMNkzMfaJ2FwAfTXoMbSUygQiGu1Sh0UizQpgjqzPH8gIt6v8qt542i4Pk5T+gbrculkfzvFhzMQu81EyP2v4TfCNmCsSrFQdRl2Z7pTbWddtn5//e6Px";


        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;//was BACK
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        /**
         * Load the data set containing the VuMarks for Relic Recovery. There's only one trackable
         * in this data set: all three of the VuMarks in the game were created from this one template,
         * but differ in their instance id information.
         * @see VuMarkInstanceId
         */
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        waitForStart();

        relicTrackables.activate();

        r.Rarm.setPosition(0.0);
        sleep(1000);
        r.moveServo(r.Rprgrab1, 0.15); //open position
        r.moveServo(r.Lprgrab1, 0.72);
        sleep(500);



        Color.RGBToHSV(r.RVc2.red() * 8, r.RVc2.green() * 8, r.RVc2.blue() * 8, hsvValues);

        // send the info back to driver station using telemetry function.
        telemetry.addData("LED", r.bLedOn ? "On" : "Off");
        telemetry.addData("Red2 ", r.RVc2.red());
        telemetry.addData("Blue2", r.RVc2.blue());
        telemetry.addData("Hue", hsvValues[0]);
        telemetry.update();
        sleep(2000);

        while (opModeIsActive()) {

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

                if (vuMark == RelicRecoveryVuMark.UNKNOWN) {
                    vuTarget = 0;
                }
                if (vuCount >= 100) {
                    vuMarkREAD = true;

                }

            }

            telemetry.addData("VuMark", "%s visible", vuTarget);
            telemetry.update();
            sleep(2000);


            if ((r.RVc2.red() >= 1.0) && (r.RVc2.red() > r.RVc2.blue()) & checkColor) {
                telemetry.addData("RVc2 Red ", r.RVc2.red());
                telemetry.update();

                moveBot(3, FORWARD, 1.0);
                r.moveServo(r.Rarm, 0.9);
                sleep(1000);
                moveBot(18, FORWARD, 1.0);
                r.moveServo(r.Rprgrab1, 0.28);
                r.moveServo(r.Lprgrab1, 0.57);
                checkColor = false;
            }

            if ((r.RVc2.blue() >= 1.0) && (r.RVc2.blue() > r.RVc2.red()) & checkColor) {
                telemetry.addData("RVc2 Blue ", r.RVc2.blue());
                telemetry.update();

                moveBot(4.3, BACKWARD, 1.0);
                r.moveServo(r.Rarm, 0.9);
                sleep(1000);
                moveBot(28, FORWARD, 1.0);
                sleep(500);
                r.moveServo(r.Rprgrab1, 0.28);
                r.moveServo(r.Lprgrab1, 0.57);
                checkColor = false;
                ForB = true;
            }

            if (checkColor) { //change distances to match red
                telemetry.addLine(" NO Color Val");
                telemetry.update();

                r.moveServo(r.Rarm, 0.9);
                moveBot(19, FORWARD, 1.0);
                sleep(500);
                r.moveServo(r.Rprgrab1, 0.28);
                r.moveServo(r.Lprgrab1, 0.57);
                checkColor = false;

            }

            if (vuTarget == 1 && autodone == 1 && ForB == false) { //good
                sleep(1000);
                eTurnBot(105, LEFT, 1.0, 1.0);
                sleep(500);
                moveBot(15, FORWARD, 1.0);
                sleep(500);
                eTurnBot(35, RIGHT, 1.0, 1.0);
                sleep(500);
                moveBot(7, FORWARD, 1.0);
                sleep(500);
                r.Rprspin1.setPower(-0.6);
                r.Lprspin1.setPower(0.6);
                sleep(1000);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                r.moveServo(r.Rprgrab1, 0.15);
                r.moveServo(r.Lprgrab1, 0.72);
                sleep(500);
                moveBot(7, BACKWARD, 0.4);
                autodone = 2;
            }
            if (vuTarget == 1 && autodone == 1 && ForB == true) { //good
                sleep(1000);
                eTurnBot(120, LEFT, 1.0, 1.0);
                sleep(500);
                moveBot(15, FORWARD, 1.0);
                sleep(500);
                eTurnBot(58, RIGHT, 1.0, 1.0);
                sleep(500);
                moveBot(7, FORWARD, 1.0);
                sleep(500);
                r.Rprspin1.setPower(-0.6);
                r.Lprspin1.setPower(0.6);
                sleep(1000);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                r.moveServo(r.Rprgrab1, 0.15);
                r.moveServo(r.Lprgrab1, 0.72);
                sleep(500);
                moveBot(7, BACKWARD, 0.4);
                autodone = 2;
            }
            if (vuTarget == 2 && autodone == 1 && ForB == false) { //good
                sleep(500);
                eTurnBot(35, LEFT, 1.0, 1.0);
                sleep(500);
                moveBot(10, FORWARD, 1.0);
                sleep(500);
                r.Rprspin1.setPower(-0.6);
                r.Lprspin1.setPower(0.6);
                sleep(1000);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                r.moveServo(r.Rprgrab1, 0.15);
                r.moveServo(r.Lprgrab1, 0.72);
                sleep(500);
                moveBot(5, BACKWARD, 0.4);
                autodone = 2;
            }
            if (vuTarget == 2 && autodone == 1  && ForB == true) { //good
                sleep(1000);
                eTurnBot(40, LEFT, 1.0, 1.0);
                sleep(500);
                moveBot(8, FORWARD, 1.0);
                sleep(500);
                r.Rprspin1.setPower(-0.6);
                r.Lprspin1.setPower(0.6);
                sleep(1000);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                r.moveServo(r.Rprgrab1, 0.15);
                r.moveServo(r.Lprgrab1, 0.72);
                sleep(500);
                moveBot(5, BACKWARD, 0.4);
                autodone = 2;
            }
            if ((vuTarget == 3 || vuTarget == 0) && autodone == 1 && ForB == false) { //good
                sleep(500);
                eTurnBot(90, LEFT, 1.0, 1.0);
                sleep(500);
                moveBot(7, FORWARD, 1.0);
                sleep(500);
                eTurnBot(29, RIGHT, 1.0, 1.0);
                sleep(500);
                moveBot(7, FORWARD, 1.0);
                sleep(500);
                r.Rprspin1.setPower(-0.6);
                r.Lprspin1.setPower(0.6);
                sleep(1000);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                r.moveServo(r.Rprgrab1, 0.15);
                r.moveServo(r.Lprgrab1, 0.72);
                sleep(700);
                moveBot(8, BACKWARD, 0.4);
                autodone = 2;
            }
            if ((vuTarget == 3 || vuTarget ==0) && autodone == 1 && ForB == true) { //good
                sleep(500);
                eTurnBot(90, LEFT, 1.0, 1.0);
                sleep(500);
                moveBot(9.5, FORWARD, 1.0);
                sleep(500);
                eTurnBot(35, RIGHT, 1.0, 1.0);
                sleep(500);
                moveBot(7, FORWARD, 1.0);
                sleep(500);
                r.Rprspin1.setPower(-0.6);
                r.Lprspin1.setPower(0.6);
                sleep(1000);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                r.moveServo(r.Rprgrab1, 0.15);
                r.moveServo(r.Lprgrab1, 0.72);
                sleep(700);
                moveBot(8, BACKWARD, 0.4);
                autodone = 2;
            }

            if (autodone == 2 && vuTarget == 1) { //not tested
                moveBot(4, BACKWARD, 1.0);
                eTurnBot(90, LEFT, 1.0, 1.0);
                moveBot(24, FORWARD, 1.0);
                eTurnBot(110, LEFT, 1.0, 1.0);
                moveBot(40, FORWARD, 1.0);
                r.Rprspin1.setPower(0.6);
                r.Lprspin1.setPower(-0.6);
                sleep(700);
                r.moveServo(r.Rprgrab1, 0.28);
                r.moveServo(r.Lprgrab1, 0.57);
                sleep(1500);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                moveBot(40, BACKWARD, 1.0);
                eTurnBot(110, LEFT, 1.0, 1.0);
                moveBot(24, FORWARD, 1.0);
                autodone = 3;
            }

            if (autodone == 2 && (vuTarget == 3 || vuTarget == 0)) { //not tested
                moveBot(4, BACKWARD, 1.0);
                eTurnBot(90, LEFT, 1.0, 1.0);
                moveBot(30, FORWARD, 1.0);
                eTurnBot(110, LEFT, 1.0, 1.0);
                moveBot(40, FORWARD, 1.0);
                r.Rprspin1.setPower(0.6);
                r.Lprspin1.setPower(-0.6);
                sleep(700);
                r.moveServo(r.Rprgrab1, 0.28);
                r.moveServo(r.Lprgrab1, 0.57);
                sleep(1500);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                moveBot(40, BACKWARD, 1.0);
                eTurnBot(110, LEFT, 1.0, 1.0);
                moveBot(24, FORWARD, 1.0);
                autodone = 3;
            }

            if (autodone == 2 && vuTarget == 2) { //not tested
                moveBot(3, BACKWARD, 1.0);
                eTurnBot(90, LEFT, 1.0, 1.0);
                moveBot(36, FORWARD, 1.0);
                eTurnBot(110, LEFT, 1.0, 1.0);
                moveBot(40, FORWARD, 1.0);
                r.Rprspin1.setPower(0.6);
                r.Lprspin1.setPower(-0.6);
                sleep(700);
                r.moveServo(r.Rprgrab1, 0.28);
                r.moveServo(r.Lprgrab1, 0.57);
                sleep(1500);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                moveBot(40, BACKWARD, 1.0);
                eTurnBot(110, LEFT, 1.0, 1.0);
                moveBot(24, FORWARD, 1.0);
                autodone = 3;
            }

            telemetry.addData("VuMark", "%s visible", vuTarget);
            telemetry.update();





        }

    }
}
