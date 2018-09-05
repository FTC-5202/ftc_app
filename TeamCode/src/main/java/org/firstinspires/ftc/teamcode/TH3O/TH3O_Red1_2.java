package org.firstinspires.ftc.teamcode.TH3O;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static org.firstinspires.ftc.teamcode.TH3O.TH3OAutoMethods.Direction.RIGHT;
import static org.firstinspires.ftc.teamcode.TH3O.TH3OAutoMethods.Direction.LEFT;

/**
 * Created by Hannah on 10/26/2017.
 */
@Autonomous(name = "Th3O Red1")
@Disabled
public class TH3O_Red1_2 extends TH3OAutoMethods {

    OpenGLMatrix lastLocation = null;

    VuforiaLocalizer vuforia;


    //public void runOpMode() throws InterruptedException {
    @Override
    public void runOpMode() {
        boolean checkColor = true;
        boolean autodone = false;
        boolean vuMarkREAD = false;
        boolean ForB = false;
        int vuTarget = 0;
        int vuCount = 0;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */

        setupAll(); //reference Electrical setting up motors, servos, and sensors
        telemetry.addLine("INITIATED");

        //telemetry.addData(" myMarkValue = ", myMarkValue);
        telemetry.update();

        // hsvValues is an array that will hold the hue, saturation, and value information.
        final float hsvValues[] = {0F, 0F, 0F};

        //r.RVc1.enableLed(r.bLedOn);
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
       //r.moveServo(r.Rprgrab1, 0.7);
        //r.moveServo(r.Lprgrab1, 0.3);
        //sleep(500);
        r.moveServo(r.Rprgrab1, 0.9);
        r.moveServo(r.Lprgrab1, 0.1);
        sleep(100);
        r.moveServo(r.Rprgrab1, 0.8);
        r.moveServo(r.Lprgrab1, 0.2);
        sleep(100);
        r.moveServo(r.Rprgrab1, 0.7);
        r.moveServo(r.Lprgrab1, 0.3);
        sleep(100);
        r.moveServo(r.Rprgrab1, 0.6);
        r.moveServo(r.Lprgrab1, 0.4);
        sleep(100);
        r.moveServo(r.Rprgrab1, 0.5);
        r.moveServo(r.Lprgrab1, 0.5);
        sleep(100);
        r.moveServo(r.Rprgrab1, 0.4);
        r.moveServo(r.Lprgrab1, 0.6);
        sleep(100);
        r.moveServo(r.Rprgrab1, 0.27);
        r.moveServo(r.Lprgrab1, 0.66);
        sleep(100);

        r.moveServo(r.Rprgrab1, 0.15); //open position
        r.moveServo(r.Lprgrab1, 0.72);
        sleep(500);


        //Color.RGBToHSV(r.RVc1.red() * 8, r.RVc1.green() * 8, r.RVc1.blue() * 8, hsvValues);
        Color.RGBToHSV(r.RVc2.red() * 8, r.RVc2.green() * 8, r.RVc2.blue() * 8, hsvValues);

        // send the info back to driver station using telemetry function.
        telemetry.addData("LED", r.bLedOn ? "On" : "Off");
        //telemetry.addData("Red1  ", r.RVc1.red());
        telemetry.addData("Red2 ", r.RVc2.red());
        //telemetry.addData("Blue1", r.RVc1.blue());
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

                eTurnBot(15, LEFT, 0.3, 0.3);
                sleep(200);
                r.moveServo(r.Rarm, 0.9);
                sleep(1800);
                eTurnBot(17, RIGHT, 0.3, 0.3);
                sleep(200);
                moveBot(21, FORWARD, 0.7);
                r.moveServo(r.Rprgrab1, 0.35);
                r.moveServo(r.Lprgrab1, 0.5);
                checkColor = false;
            }
            //7-17-18 good for now
            if ((r.RVc2.blue() >= 1.0) && (r.RVc2.blue() > r.RVc2.red()) & checkColor) {
                telemetry.addData("RVc2 Blue ", r.RVc2.blue());
                telemetry.update();

                /*moveBot(3.2, BACKWARD, 0.7);
                r.moveServo(r.Rarm, 0.9);
                sleep(1000);
                moveBot(28, FORWARD, 1.0);
                sleep(500);*/
                eTurnBot(15, RIGHT, 0.5, 0.5);
                sleep(200);
                r.moveServo(r.Rarm, 0.9);
                sleep(1800);
                eTurnBot(17, LEFT, 0.5, 0.5);
                sleep(200);
                moveBot(23, FORWARD, 0.7); //was 21
                r.moveServo(r.Rprgrab1, 0.35);
                r.moveServo(r.Lprgrab1, 0.5);
                checkColor = false;
                ForB = true;
            }

            if (checkColor) {
                telemetry.addLine(" NO Color Val");
                telemetry.update();

                r.moveServo(r.Rarm, 0.9);
                moveBot(19, FORWARD, 1.0);
                sleep(500);
                r.moveServo(r.Rprgrab1, 0.28);
                r.moveServo(r.Lprgrab1, 0.57);
                checkColor = false;

            }

            // 7/16/18: good for now
            if (vuTarget == 1 && !autodone && ForB == false) {
                sleep(1000);
                eTurnBot(100, LEFT, 0.7, 0.7);
                sleep(500);
                moveBot(9, FORWARD, 0.7);
                sleep(500);
                eTurnBot(55, RIGHT, 0.7, 0.7); //was 35
                sleep(500);
                moveBot(10, FORWARD, 0.7); //was 11
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
                autodone = true;
            }
            // 7/16/18: good for now
            if (vuTarget == 1 && !autodone && ForB == true) {
                sleep(1000);
                eTurnBot(100, LEFT, 0.7, 0.7); //was 50
                sleep(500);
                moveBot(12, FORWARD, 0.7); //was 10.5
                eTurnBot(55, RIGHT, 0.7, 0.7); //was 35
                sleep(500);
                moveBot(10, FORWARD, 0.7); //was 11
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
                autodone = true;
            }
                // 7/16/18: good for now
                if (vuTarget == 2 && !autodone && ForB == false) { //good
                    sleep(1000);
                    eTurnBot(35, LEFT, 0.7, 0.7);
                    sleep(1000);
                    moveBot(10, FORWARD, 0.7);
                    sleep(1000);
                    r.Rprspin1.setPower(-0.6);
                    r.Lprspin1.setPower(0.6);
                    sleep(1000);
                    r.Rprspin1.setPower(0.0);
                    r.Lprspin1.setPower(0.0);
                    r.moveServo(r.Rprgrab1, 0.15);
                    r.moveServo(r.Lprgrab1, 0.72);
                    sleep(500);
                    moveBot(5, BACKWARD, 0.4);
                    autodone = true;
                }
                // 7/16/18: good for now
                if (vuTarget == 2 && !autodone && ForB == true) {

                    sleep(1000);
                    eTurnBot(38, LEFT, 0.7, 0.7); //was 40
                    sleep(500);
                    moveBot(10, FORWARD, 0.7); //was 9
                    sleep(1000);
                    r.Rprspin1.setPower(-0.6);
                    r.Lprspin1.setPower(0.6);
                    sleep(1000);
                    r.Rprspin1.setPower(0.0);
                    r.Lprspin1.setPower(0.0);
                    r.moveServo(r.Rprgrab1, 0.15);
                    r.moveServo(r.Lprgrab1, 0.72);
                    sleep(500);
                    moveBot(7, BACKWARD, 0.4);
                    autodone = true;
                }

                // 7/16/18: good for now
                if (vuTarget == 3 && !autodone && ForB == false) {
                    sleep(500);
                    eTurnBot(90, LEFT, 0.7, 0.7);
                    sleep(500);
                    moveBot(8, FORWARD, 0.7);
                    sleep(500);
                    eTurnBot(50, RIGHT, 0.7, 0.7); //was 35
                    sleep(500);
                    moveBot(7, FORWARD, 0.7);
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
                    autodone = true;
                }
                // 7/16/18: good for now
                if (vuTarget == 3 && !autodone && ForB == true) {
                    sleep(500);
                    eTurnBot(90, LEFT, 0.7, 0.7);
                    sleep(500);
                    moveBot(9.5, FORWARD, 0.7);
                    sleep(500);
                    eTurnBot(65, RIGHT, 0.7, 0.7); //was 52
                    sleep(500);
                    moveBot(8, FORWARD, 0.7); //was 7
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
                    autodone = true;
                }
                // 7/13/18: not tested
                if (vuTarget == 0 && !autodone && ForB == false) {
                    sleep(500);
                    eTurnBot(90, LEFT, 0.7, 0.7);
                    sleep(500);
                    moveBot(7, FORWARD, 0.7);
                    sleep(500);
                    eTurnBot(32, RIGHT, 0.7, 0.7);
                    sleep(500);
                    moveBot(7, FORWARD, 0.7);
                    sleep(500);
                    r.Rprspin1.setPower(-0.6);
                    r.Lprspin1.setPower(0.6);
                    sleep(1000);
                    r.Rprspin1.setPower(0.0);
                    r.Lprspin1.setPower(0.0);
                    r.moveServo(r.Rprgrab1, 0.15);
                    r.moveServo(r.Lprgrab1, 0.72);
                    sleep(700);
                    moveBot(6, BACKWARD, 0.4);
                    autodone = true;
                }
                // 7/13/18: not tested
                if (vuTarget == 0 && !autodone && ForB == true) {
                    sleep(500);
                    eTurnBot(90, LEFT, 0.7, 0.7);
                    sleep(500);
                    moveBot(9.5, FORWARD, 0.7);
                    sleep(500);
                    eTurnBot(35, RIGHT, 0.7, 0.7);
                    sleep(500);
                    moveBot(7, FORWARD, 0.7);
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
                    autodone = true;
                }

                telemetry.addData("VuMark", "%s visible", vuTarget);
                telemetry.update();


            }

        }

    }






