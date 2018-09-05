package org.firstinspires.ftc.teamcode.TH3O;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by FIRSTUser on 1/16/2018.
 */
@Autonomous (name = "DriveSTR")
@Disabled
public class DrivetrainStraightTest extends TH3OAutoMethods {

    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() throws InterruptedException {
        boolean checkColor = true;
        boolean autodone = false;
        boolean vuMarkREAD = false;
        boolean ForB = false;
        int vuTarget = 0;
        int vuCount = 0;
        int count = 0;
        setupAll();
        telemetry.addLine("INITIATED");

        final float hsvValues[] = {0F, 0F, 0F};

        //r.RVc1.enableLed(r.bLedOn);
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

        //r.moveServo(r.Rarm, 0.0);
        sleep(1000);
        /*r.moveServo(r.Rarm, 0.05);
        sleep(1000);*/
        r.moveServo(r.Rprgrab1, 0.15);
        r.moveServo(r.Lprgrab1, 0.72);
        sleep(500);

        //Color.RGBToHSV(r.RVc1.red() * 8, r.RVc1.green() * 8, r.RVc1.blue() * 8, hsvValues);
        Color.RGBToHSV(r.RVc2.red() * 8, r.RVc2.green() * 8, r.RVc2.blue() * 8, hsvValues);

        telemetry.addData("LED", r.bLedOn ? "On" : "Off");
        //telemetry.addData("Red1  ", r.RVc1.red());
        telemetry.addData("Red2 ", r.RVc2.red());
        //telemetry.addData("Blue1", r.RVc1.blue());
        telemetry.addData("Blue2", r.RVc2.red());
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

                //if (vuMark == RelicRecoveryVuMark.UNKNOWN) {
                //vuTarget = 0;
                // }
                if (vuCount >= 100) {
                    vuMarkREAD = true;

                }

            }

            telemetry.addData("VuMark", "%s visible", vuTarget);
            telemetry.update();
            sleep(2000);

           /* if ((r.RVc1.red() >= 1.0) && (r.RVc1.red() > r.RVc1.blue()) & checkColor) {
                telemetry.addData("Red1 ", r.RVc1.red());
                telemetry.update();
                moveBot(5, FORWARD, 0.5); //18 total
                r.moveServo(r.Rarm, 0.9);
                sleep(500);
                moveBot(13, FORWARD, 0.5);
                r.moveServo(r.Rprgrab1, 0.28);
                r.moveServo(r.Lprgrab1, 0.57);
                checkColor = false;
            }*/
           if (count <4) {
               moveBot(24, FORWARD, 1.0);
               //checkColor = false;
               sleep(1000);
               count = count + 1;

           }

            /*if ((r.RVc2.red() >= 1.0) && (r.RVc2.red() > r.RVc2.blue()) & checkColor ) {
                telemetry.addData("Red2 ", r.RVc2.red());
                telemetry.update();
                moveBot(5, FORWARD, 0.5);
                r.moveServo(r.Rarm, 0.9);
                sleep(1000);
                moveBot(13, FORWARD, 0.5);
                r.moveServo(r.Rprgrab1, 0.28);
                r.moveServo(r.Lprgrab1, 0.57);
                checkColor = false;
            }
            /*if ((r.RVc1.blue() >= 1.0) && (r.RVc2.blue() > r.RVc2.red()) & checkColor) {
                telemetry.addData("RVc1 Blue ", r.RVc1.blue());
                telemetry.update();

                moveBot(2, BACKWARD, 0.5);
                r.moveServo(r.Rarm, 0.9);
                sleep(500);
                moveBot(21, FORWARD, 0.5);
                sleep(500);
                r.moveServo(r.Rprgrab1, 0.28);
                r.moveServo(r.Lprgrab1, 0.57);
                checkColor = false;
                ForB = true;
            }
            if ((r.RVc2.blue() >= 1.0) && (r.RVc2.blue() > r.RVc2.red()) & checkColor) { //rev color sensor (blue) = 0 and MR color sensor (blue2) input value > 2.0 and MR color sensor (blue2) input value > MR color sensor (red2) input value
                telemetry.addData("RVc2 Blue ", r.RVc2.blue());
                telemetry.update();

                moveBot(2, BACKWARD, 0.8); //was 0.5
                r.moveServo(r.Rarm, 0.9);
                sleep(500);
                moveBot(21, FORWARD, 0.8); //was 0.5
                sleep(500);
                r.moveServo(r.Rprgrab1, 0.28);
                r.moveServo(r.Lprgrab1, 0.57);
                checkColor = false;
                ForB = true;
            }

            if (checkColor) {
                telemetry.addLine(" NO Color Val");
                telemetry.update();

                r.moveServo(r.Rarm, 0.9);
                moveBot(19, FORWARD, 0.5);
                sleep(500);
                r.moveServo(r.Rprgrab1, 0.28);
                r.moveServo(r.Lprgrab1, 0.57);
                checkColor = false;

            }
            //1-24-18: Ran twice and worked well and consistently
            if (vuTarget == 1 && !autodone && ForB == false) { //sees red - testing
                telemetry.addLine("LEFT COLUMN, Saw: RED JEWEL");
                telemetry.update();
                sleep(1500);
                moveBot(4, FORWARD, 0.5);
                sleep(500);
                eTurnBot(58, RIGHT, 0.5, 0.5);
                sleep(500);
                moveBot(11.5, FORWARD, 0.5);
                sleep(500);
                r.Rprspin1.setPower(-0.6);
                r.Lprspin1.setPower(0.6);
                sleep(1000);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                r.moveServo(r.Rprgrab1, 0.15);
                r.moveServo(r.Lprgrab1, 0.72);
                sleep(500);
                moveBot(4, BACKWARD, 0.4);
                autodone = true;

            }
            //1-24-18: Ran twice and worked well and consistently
            if (vuTarget == 1 && !autodone && ForB == true) { //sees blue - need to test
                telemetry.addLine("LEFT COLUMN, Saw: BLUE JEWEL");
                telemetry.update();
                sleep(1500);
                moveBot(5, FORWARD, 0.8); //was 0.5
                sleep(500);
                eTurnBot(58, RIGHT, 0.8, 0.8); //was 0.5, 0.5
                sleep(500);
                moveBot(11.5, FORWARD, 0.8); //was 0.5
                sleep(500);
                r.Rprspin1.setPower(-0.6);
                r.Lprspin1.setPower(0.6);
                sleep(900);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                sleep(500);
                r.moveServo(r.Rprgrab1, 0.15);
                r.moveServo(r.Lprgrab1, 0.72);
                sleep(700);
                moveBot(4, BACKWARD, 0.4);
                autodone = true;
            }
            if (vuTarget == 2 && !autodone && ForB == false) { //need to test
                telemetry.addLine("RIGHT COLUMN, Saw: RED JEWEL");
                telemetry.update();
                sleep(1500);
                moveBot(3,FORWARD, 0.5);
                sleep(500);
                eTurnBot(100, LEFT, 0.5, 0.5);
                sleep(500);
                moveBot(12, FORWARD, 0.5);
                sleep(500);
                r.Rprspin1.setPower(-0.6);
                r.Lprspin1.setPower(0.6);
                sleep(1000);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                r.moveServo(r.Rprgrab1, 0.15);
                r.moveServo(r.Lprgrab1, 0.72);
                sleep(500);
                moveBot(4, BACKWARD, 0.4);
                autodone = true;
            }
            if (vuTarget == 2 && !autodone  && ForB == true) { //need to test
                telemetry.addLine("RIGHT COLUMN, Saw: BLUE JEWEL");
                telemetry.update();
                sleep(1500);
                moveBot(3, FORWARD, 0.5);
                sleep(500);
                eTurnBot(115, LEFT, 0.5, 0.5);
                sleep(500);
                moveBot(10, FORWARD, 0.5);
                sleep(500);
                r.Rprspin1.setPower(-0.6);
                r.Lprspin1.setPower(0.6);
                sleep(1000);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                r.moveServo(r.Rprgrab1, 0.15);
                r.moveServo(r.Lprgrab1, 0.72);
                sleep(500);
                moveBot(4, BACKWARD, 0.4);
                autodone = true;
            }
            if (vuTarget == 3 && !autodone && ForB == false) { //need to test
                telemetry.addLine("CENTER COLUMN, Saw: RED JEWEL");
                telemetry.update();
                sleep(1500);
                sleep(500);
                moveBot(2, BACKWARD, 0.4); //Change directional value to FORWARD?
                sleep(500);
                eTurnBot(58, RIGHT, 0.5, 0.5);
                sleep(500);
                moveBot(11, FORWARD, 0.5);
                sleep(500);
                r.Rprspin1.setPower(-0.6);
                r.Lprspin1.setPower(0.6);
                sleep(1000);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                r.moveServo(r.Rprgrab1, 0.15);
                r.moveServo(r.Lprgrab1, 0.72);
                sleep(500);
                moveBot(4, BACKWARD, 0.4);
                autodone = true;
            }
            if (vuTarget == 3 && !autodone && ForB == true) { //need to test
                telemetry.addLine("CENTER COLUMN, Saw: BLUE JEWEL");
                telemetry.update();
                sleep(1500);
                moveBot(3, BACKWARD, 0.4);
               sleep(500);
               eTurnBot(58, RIGHT, 0.5, 0.5);
               sleep(500);
               moveBot(11, FORWARD, 0.5);
               sleep(500);
               r.Rprspin1.setPower(-0.6);
               r.Lprspin1.setPower(0.6);
               sleep(1000);
               r.Rprspin1.setPower(0.0);
               r.Lprspin1.setPower(0.0);
               r.moveServo(r.Rprgrab1, 0.15);
               r.moveServo(r.Lprgrab1, 0.72);
               sleep(500);
               moveBot(4, BACKWARD, 0.4);
               autodone = true;

            }
            //IF pictograph is not read, try for center
            if (vuTarget == 0 && !autodone && ForB == false) { //need to test
                telemetry.addLine("UNKNOWN COLUMN, Saw: RED JEWEL");
                telemetry.update();
                sleep(1500);
                sleep(500);
                moveBot(2, BACKWARD, 0.4);
                sleep(500);
                eTurnBot(58, RIGHT, 0.5, 0.5);
                sleep(500);
                moveBot(11, FORWARD, 0.5);
                sleep(500);
                r.Rprspin1.setPower(-0.6);
                r.Lprspin1.setPower(0.6);
                sleep(1000);
                r.Rprspin1.setPower(0.0);
                r.Lprspin1.setPower(0.0);
                r.moveServo(r.Rprgrab1, 0.15);
                r.moveServo(r.Lprgrab1, 0.72);
                sleep(500);
                moveBot(4, BACKWARD, 0.4);
                autodone = true;
            }
            // if pictograph is not read, try for center
            if (vuTarget == 0 && !autodone && ForB == true) { //need to test
                telemetry.addLine("UNKNOWN COLUMN, Saw: RED JEWEL");
                telemetry.update();
                sleep(1500);
               sleep(500);
               moveBot(3, BACKWARD, 0.4);
               sleep(500);
               eTurnBot(58, RIGHT, 0.5, 0.5);
               sleep(500);
               moveBot(11, FORWARD, 0.5);
               sleep(500);
               r.Rprspin1.setPower(-0.6);
               r.Lprspin1.setPower(0.6);
               sleep(1000);
               r.Rprspin1.setPower(0.0);
               r.Lprspin1.setPower(0.0);
               r.moveServo(r.Rprgrab1, 0.15);
               r.moveServo(r.Lprgrab1, 0.72);
               sleep(500);
               moveBot(4, BACKWARD, 0.4);
               autodone = true;
            }

            telemetry.addData("VuMark", "%s visible", vuTarget);
            telemetry.addData("ForB ", ForB );
            telemetry.addData ("CheckColor ", checkColor);
            telemetry.addData ("autodone ", autodone);
            telemetry.update();
*/
        }

        telemetry.addData("VuMark", "%s visible", vuTarget);
        telemetry.addData("ForB ", ForB );
        telemetry.addData ("CheckColor ", checkColor);
        telemetry.addData ("autodone ", autodone);
        telemetry.update();

        }
    }


