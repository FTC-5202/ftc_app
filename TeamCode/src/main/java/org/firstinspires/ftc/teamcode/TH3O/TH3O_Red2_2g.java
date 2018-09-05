package org.firstinspires.ftc.teamcode.TH3O;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static org.firstinspires.ftc.teamcode.TH3O.TH3OAutoMethods.Direction.LEFT;
import static org.firstinspires.ftc.teamcode.TH3O.TH3OAutoMethods.Direction.RIGHT;

/**
 * Created by FIRSTUser on 2/15/2018.
 */
//@Autonomous (name = "TH3O Red2 2Glyph")
public class TH3O_Red2_2g extends TH3OAutoMethods {

    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() throws InterruptedException {
        /* In this section we define the variables
            checkColor  Will be changed to false if we sense a red or blue jewel
            autodone    Numerical value to determine when first glyph has been scored
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
        sleep(1000);
        r.moveServo(r.Rprgrab1, 0.15);
        r.moveServo(r.Lprgrab1, 0.72);
        sleep(500);


        Color.RGBToHSV(r.RVc2.red() * 8, r.RVc2.green() * 8, r.RVc2.blue() * 8, hsvValues);

        telemetry.addData("LED", r.bLedOn ? "On" : "Off");
        telemetry.addData("Red2 ", r.RVc2.red());
        telemetry.addData("Blue2", r.RVc2.blue());
        telemetry.addData("Hue", hsvValues[0]);
        telemetry.update();
        sleep(1500);

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


            if ((r.RVc2.red() >= 1.0) && (r.RVc2.red() > r.RVc2.blue()) & checkColor ) {
                telemetry.addData("Red2 ", r.RVc2.red());
                telemetry.update();
                moveBot(5, FORWARD, 1.0);
                r.moveServo(r.Rarm, 0.9);
                sleep(1000);
                moveBot(12, FORWARD, 1.0);
                r.moveServo(r.Rprgrab1, 0.28);
                r.moveServo(r.Lprgrab1, 0.57);
                checkColor = false;
            }

            if ((r.RVc2.blue() >= 1.0) && (r.RVc2.blue() > r.RVc2.red()) & checkColor) {
                telemetry.addData("RVc2 Blue ", r.RVc2.blue());
                telemetry.update();

                r.moveServo(r.Lprgrab1, 0.72);
                sleep(600);
                r.moveServo(r.Rprgrab1, 0.15);
                sleep(500);
                moveBot(3, BACKWARD, 1.0);
                r.moveServo(r.Rarm, 0.9);
                sleep(500);
                moveBot(20, FORWARD, 1.0);
                sleep(250);
                r.moveServo(r.Rprgrab1, 0.28);
                r.moveServo(r.Lprgrab1, 0.57);
                checkColor = false;
                ForB = true;
            }

            if (checkColor) { //need to change distances to match blue
                telemetry.addLine(" NO Color Val");
                telemetry.update();

                r.moveServo(r.Rarm, 0.9);
                moveBot(17, FORWARD, 1.0);
                sleep(500);
                r.moveServo(r.Rprgrab1, 0.28);
                r.moveServo(r.Lprgrab1, 0.57);
                checkColor = false;

            }

            if (vuTarget == 1 && autodone == 1 && ForB == false) { //sees red - good
                telemetry.addLine("LEFT COLUMN, Saw: RED JEWEL");
                telemetry.update();
                sleep(900);
                eTurnBot(10, LEFT, 1.0, 1.0);
                moveBot(12, FORWARD, 1.0);
                sleep(250);
                eTurnBot(70, RIGHT, 1.0, 1.0);
                sleep(250);
                moveBot(11.5, FORWARD, 1.0);
                sleep(250);
                r.Rprspin1.setPower(-0.6);
                r.Lprspin1.setPower(0.6);
                sleep(1000);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                r.moveServo(r.Rprgrab1, 0.15);
                r.moveServo(r.Lprgrab1, 0.72);
                sleep(250);
                moveBot(7, BACKWARD, 0.4);
                autodone = 2;

            }

            if (vuTarget == 1 && autodone == 1 && ForB == true) { //sees blue - good
                telemetry.addLine("LEFT COLUMN, Saw: BLUE JEWEL");
                telemetry.update();
                sleep(900);
                moveBot(12, FORWARD, 1.0);
                sleep(250);
                eTurnBot(58, RIGHT, 1.0, 1.0);
                sleep(250);
                moveBot(11.5, FORWARD, 1.0);
                sleep(250);
                r.Rprspin1.setPower(-0.6);
                r.Lprspin1.setPower(0.6);
                sleep(900);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                sleep(250);
                r.moveServo(r.Rprgrab1, 0.15);
                r.moveServo(r.Lprgrab1, 0.72);
                sleep(700);
                moveBot(7, BACKWARD, 1.0);
                autodone = 2;
            }
            if (vuTarget == 2 && autodone == 1 && ForB == false) { // sees red - good
                telemetry.addLine("RIGHT COLUMN, Saw: RED JEWEL");
                telemetry.update();
                sleep(900);
                moveBot(17, FORWARD, 1.0);
                sleep(250);
                eTurnBot(159, RIGHT, 1.0, 1.0);
                sleep(250);
                moveBot(12, FORWARD, 1.0);
                sleep(250);
                r.Rprspin1.setPower(-0.6);
                r.Lprspin1.setPower(0.6);
                sleep(1000);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                r.moveServo(r.Rprgrab1, 0.15);
                r.moveServo(r.Lprgrab1, 0.72);
                sleep(500);
                moveBot(8, BACKWARD, 1.0);
                autodone = 2;
            }
            if (vuTarget == 2 && autodone == 1  && ForB == true) { //good
                telemetry.addLine("RIGHT COLUMN, Saw: BLUE JEWEL");
                telemetry.update();
                sleep(900);
                moveBot(15, FORWARD, 1.0);
                sleep(250);
                eTurnBot(160, RIGHT, 1.0, 1.0);
                sleep(250);
                moveBot(12, FORWARD, 1.0);
                sleep(250);
                r.Rprspin1.setPower(-0.6);
                r.Lprspin1.setPower(0.6);
                sleep(1000);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                r.moveServo(r.Rprgrab1, 0.15);
                r.moveServo(r.Lprgrab1, 0.72);
                sleep(500);
                moveBot(8, BACKWARD, 1.0);
                autodone = 2;
            }
            if ((vuTarget == 3 || vuTarget == 0) && autodone == 1 && ForB == false) { //good - sees red
                telemetry.addLine("CENTER COLUMN, Saw: RED JEWEL");
                telemetry.update();
                sleep(900);
                moveBot(6, FORWARD, 1.0);
                sleep(250);
                eTurnBot(55, RIGHT, 1.0, 1.0);
                sleep(250);
                moveBot(11, FORWARD, 1.0);
                sleep(250);
                r.Rprspin1.setPower(-0.6);
                r.Lprspin1.setPower(0.6);
                sleep(1000);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                r.moveServo(r.Rprgrab1, 0.15);
                r.moveServo(r.Lprgrab1, 0.72);
                sleep(500);
                moveBot(8, BACKWARD, 1.0);
                autodone = 2;
            }
            if ((vuTarget == 3 || vuTarget == 0) && autodone == 1 && ForB == true) { //good
                telemetry.addLine("CENTER COLUMN, Saw: BLUE JEWEL");
                telemetry.update();
                sleep(900);
                moveBot(4, FORWARD, 1.0);
                sleep(250);
                eTurnBot(58, RIGHT, 1.0, 1.0);
                sleep(250);
                moveBot(11, FORWARD, 1.0);
                sleep(250);
                r.Rprspin1.setPower(-0.6);
                r.Lprspin1.setPower(0.6);
                sleep(1000);
                r.Rprspin1.setPower(0.0);
                r.moveServo(r.Rprgrab1, 0.15);
                r.moveServo(r.Lprgrab1, 0.72);
                sleep(500);
                moveBot(8, BACKWARD, 1.0);
                autodone = 2;

            }

            if (autodone == 2 && vuTarget == 1) { //good
                moveBot(6, BACKWARD, 1.0);
                eTurnBot(90, LEFT, 1.0, 1.0);
                moveBot(10, FORWARD, 1.0);
                eTurnBot(160, LEFT, 1.0, 1.0);
                r.Rprspin1.setPower(0.6);
                r.Lprspin1.setPower(-0.6);
                sleep(250);
                moveBot(30, FORWARD, 1.0);
                sleep(500);
                r.moveServo(r.Rprgrab1, 0.28);
                r.moveServo(r.Lprgrab1, 0.57);
                sleep(2000);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                moveBot(28, BACKWARD, 1.0);
                eTurnBot(200, LEFT, 1.0, 1.0);
                autodone = 3;
            }
            if (autodone == 2 && (vuTarget == 3 || vuTarget == 0)) { //good
                moveBot(3, BACKWARD, 1.0);
                eTurnBot(69, LEFT, 1.0, 1.0);
                moveBot(6, FORWARD, 1.0);
                eTurnBot(115, LEFT, 1.0, 1.0);
                r.Rprspin1.setPower(0.6);
                r.Lprspin1.setPower(-0.6);
                sleep(500);
                moveBot(28, FORWARD, 1.0);
                sleep(500);
                r.moveServo(r.Rprgrab1, 0.28);
                r.moveServo(r.Lprgrab1, 0.57);
                sleep(2000);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                moveBot(26, BACKWARD, 1.0);
                eTurnBot(290, LEFT, 1.0, 1.0);
                autodone = 3;
            }
            if (autodone == 2 && vuTarget == 2 ){ //good
                moveBot(6, BACKWARD, 1.0);
                eTurnBot(210, RIGHT, 1.0, 1.0);
                r.Rprspin1.setPower(0.6);
                r.Lprspin1.setPower(-0.6);
                sleep(500);
                moveBot(33, FORWARD, 1.0);
                sleep(500);
                r.moveServo(r.Rprgrab1, 0.28);
                r.moveServo(r.Lprgrab1, 0.57);
                sleep(2000);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                moveBot(35, BACKWARD, 1.0);
                eTurnBot(240, LEFT, 1.0, 1.0);
                autodone = 3;


            }


            telemetry.addData("VuMark", "%s visible", vuTarget);
            telemetry.addData("ForB ", ForB );
            //telemetry.addData ("CheckColor ", checkColor);
            telemetry.addData ("autodone ", autodone);
            telemetry.update();

        }



        telemetry.addData("VuMark", "%s visible", vuTarget);
        telemetry.addData("ForB ", ForB );
        //telemetry.addData ("CheckColor ", checkColor);
        telemetry.addData ("autodone ", autodone);
        telemetry.update();
        r.LRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.RRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.LFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.RFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }
}



