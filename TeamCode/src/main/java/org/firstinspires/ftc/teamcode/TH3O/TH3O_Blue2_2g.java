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
 * Created by FIRSTUser on 2/18/2018.
 */
//@Autonomous (name = "Th30 Blue2 2glyphs")
public class TH3O_Blue2_2g extends TH3OAutoMethods {

    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() {
        boolean checkColor = true;
        boolean vuMarkREAD = false;
        boolean ForB = false;
        int vuTarget = 0;
        int vuCount = 0;
        int autodone = 1;

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

                /*if (vuMark == RelicRecoveryVuMark.UNKNOWN) {
                    //vuTarget = 0;
                }*/
                if (vuCount >= 100) {
                    vuMarkREAD = true;

                }

            }


            if ((r.RVc2.blue() >= 1.0) && (r.RVc2.blue() > r.RVc2.red()) & checkColor) {
                telemetry.addData("Blue2", r.RVc2.blue());
                telemetry.update();

                r.moveServo(r.Lprgrab2, 0.8);
                r.moveServo(r.Rprgrab2, 0.2);


                moveBot(5, FORWARD, 1.0);
                sleep(500);
                r.moveServo(r.Rarm, 0.9);
                sleep(1000);
                moveBot(27, BACKWARD, 1.0);
                sleep(500);
                eTurnBot(10, RIGHT, 0.5, 0.5);
                sleep(700);
                r.moveServo(r.Rprgrab2, 0.4);
                r.moveServo(r.Lprgrab2, 0.4);
                sleep(700);
                ForB = true;
                checkColor = false;
            }

            if ((r.RVc2.red() >= 1.0) && (r.RVc2.red() > r.RVc2.blue()) & checkColor) {
                telemetry.addData("Red2", r.RVc2.red());
                telemetry.update();

                r.moveServo(r.Rprgrab2, 0.2);
                r.moveServo(r.Lprgrab2, 0.8);
                sleep(800);

                moveBot(5, BACKWARD, 1.0);
                r.moveServo(r.Rarm, 0.9);
                sleep(500);
                moveBot(16.7, BACKWARD, 1.0);
                sleep(500);
                r.moveServo(r.Rprgrab2, 0.4);
                r.moveServo(r.Lprgrab2, 0.4);
                sleep(700);
                checkColor = false;

            }
            if (checkColor) { //need to change distances to match red
                telemetry.addLine(" NO Color Val");
                telemetry.update();

                r.moveServo(r.Rarm, 0.9);
                moveBot(17, BACKWARD, 1.0);
                sleep(500);
                r.moveServo(r.Rprgrab2, 0.4);
                r.moveServo(r.Lprgrab2, 0.4);
                checkColor = false;

            }

            if (vuTarget == 2 && autodone == 1 && !ForB) { //good
                telemetry.addLine("RIGHT COLUMN, Saw: RED JEWEL");
                telemetry.update();
                sleep(1500);
                moveBot(11, BACKWARD, 1.0);
                sleep(500);
                eTurnBot(58, LEFT, 1.0, 1.0);
                sleep(500);
                moveBot(11.5, BACKWARD, 0.4);
                sleep(500);
                r.Rprspin2.setPower(-0.6);
                r.Lprspin2.setPower(0.6);
                sleep(1000);
                r.Rprspin2.setPower(0.0);
                r.Lprspin2.setPower(0.0);
                r.moveServo(r.Rprgrab2, 0.2);
                r.moveServo(r.Lprgrab2, 0.8);
                sleep(500);
                moveBot(7, FORWARD, 0.4);
                autodone = 2;

            }
            if (vuTarget == 2 && autodone == 1 && ForB) { //good
                telemetry.addLine("RIGHT COLUMN, Saw: BLUE JEWEL");
                telemetry.update();
                sleep(1500);
                moveBot(11, BACKWARD, 1.0);
                sleep(500);
                eTurnBot(65, LEFT, 1.0, 1.0);
                sleep(500);
                moveBot(12.5, BACKWARD, 1.0);
                sleep(500);
                r.Rprspin2.setPower(-0.6);
                r.Lprspin2.setPower(0.6);
                sleep(1000);
                r.Rprspin2.setPower(0.0);
                r.Lprspin2.setPower(0.0);
                r.moveServo(r.Rprgrab2, 0.2);
                r.moveServo(r.Lprgrab2, 0.8);
                sleep(500);
                moveBot(7, FORWARD, 0.4);
                autodone = 2;

            }
            if (vuTarget == 1 && autodone == 1 && !ForB) { //good
                telemetry.addLine("LEFT COLUMN, Saw: RED JEWEL");
                telemetry.update();
                sleep(1500);
                moveBot(12, BACKWARD, 1.0);
                sleep(500);
                eTurnBot(154, LEFT, 1.0, 1.0);
                sleep(500);
                moveBot(12, BACKWARD, 1.0);
                sleep(500);
                r.Rprspin2.setPower(-0.6);
                r.Lprspin2.setPower(0.6);
                sleep(1000);
                r.Rprspin2.setPower(0.0);
                r.Lprspin2.setPower(0.0);
                r.moveServo(r.Rprgrab2, 0.2);
                r.moveServo(r.Lprgrab2, 0.8);
                sleep(500);
                moveBot(8, FORWARD, 1.0);
                autodone = 2;

            }
            if (vuTarget == 1 && autodone == 1 && ForB) { //good
                telemetry.addLine("LEFT COLUMN, Saw: BLUE JEWEL");
                telemetry.update();
                sleep(1500);
                moveBot(12, BACKWARD, 1.0);
                sleep(500);
                eTurnBot(160, LEFT, 1.0, 1.0);
                sleep(500);
                moveBot(12, BACKWARD, 1.0);
                sleep(500);
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
            if ((vuTarget == 3 || vuTarget == 0) && autodone == 1 && !ForB) { //good
                telemetry.addLine("CENTER COLUMN, Saw: RED JEWEL");
                telemetry.update();
                sleep(1500);
                moveBot(2, BACKWARD, 1.0);
                eTurnBot(62, LEFT, 1.0, 1.0);
                sleep(500);
                moveBot(11, BACKWARD, 1.0);
                sleep(500);
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
            if ((vuTarget == 3 || vuTarget == 0) && autodone == 1 && ForB) { //good
                telemetry.addLine("CENTER COLUMN, Saw: BLUE JEWEL");
                telemetry.update();
                sleep(1500);
                moveBot(5, BACKWARD, 1.0);
                sleep(500);
                eTurnBot(50, LEFT, 1.0, 1.0);
                sleep(500);
                moveBot(11, BACKWARD, 1.0);
                sleep(500);
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
           /* if (vuTarget == 0 && !autodone && !ForB) { //good
                telemetry.addLine("CENTER COLUMN, Saw: RED JEWEL");
                telemetry.update();
                sleep(1500);
                eTurnBot(68, LEFT, 1.0, 1.0);
                sleep(500);
                moveBot(11, BACKWARD, 0.5);
                sleep(500);
                r.Rprspin2.setPower(-0.6);
                r.Lprspin2.setPower(0.6);
                sleep(1000);
                r.Rprspin2.setPower(0.0);
                r.Lprspin2.setPower(0.0);
                r.moveServo(r.Rprgrab2, 0.2);
                r.moveServo(r.Lprgrab2, 0.8);
                sleep(500);
                moveBot(8, FORWARD, 0.4);
                autodone = true;

            }
            if (vuTarget == 0 && !autodone && ForB) { //good
                telemetry.addLine("CENTER COLUMN, Saw: BLUE JEWEL");
                telemetry.update();
                sleep(1500);
                moveBot(5, BACKWARD, 1.0);
                sleep(500);
                eTurnBot(50, LEFT, 0.5, 0.5);
                sleep(500);
                moveBot(11, BACKWARD, 1.0);
                sleep(500);
                r.Rprspin2.setPower(-0.6);
                r.Lprspin2.setPower(0.6);
                sleep(1000);
                r.Rprspin2.setPower(0.0);
                r.Lprspin2.setPower(0.0);
                r.moveServo(r.Rprgrab2, 0.2);
                r.moveServo(r.Lprgrab2, 0.8);
                sleep(500);
                moveBot(8, FORWARD, 0.4);
                autodone = true;

            }*/

            telemetry.addData("VuMark", "%s visible", vuTarget);
            telemetry.update();

            if (autodone == 2 && vuTarget == 2) { //good
                moveBot(6, FORWARD, 1.0);
                eTurnBot(90, RIGHT, 1.0, 1.0);
                moveBot(4, BACKWARD, 1.0);
                eTurnBot(160, RIGHT, 1.0, 1.0);
                moveBot(30, BACKWARD, 1.0);
                sleep(500);
                r.Rprspin2.setPower(0.6);
                r.Lprspin2.setPower(-0.6);
                sleep(500);
                r.moveServo(r.Rprgrab2, 0.4);
                r.moveServo(r.Lprgrab2, 0.4);
                sleep(2000);
                r.Rprspin2.setPower(0.0);
                r.Lprspin2.setPower(0.0);
                moveBot(28, FORWARD, 1.0);
                eTurnBot(250, RIGHT, 1.0, 1.0);
                autodone = 3;
            }
            if (autodone == 2 && (vuTarget == 3 || vuTarget == 0)) { //good
                moveBot(3, FORWARD, 1.0);
                eTurnBot(73, RIGHT, 1.0, 1.0);
                moveBot(6, BACKWARD, 1.0);
                eTurnBot(150, RIGHT, 1.0, 1.0);
                moveBot(33, BACKWARD, 1.0);
                sleep(500);
                r.Rprspin2.setPower(0.6);
                r.Lprspin2.setPower(-0.6);
                sleep(500);
                r.moveServo(r.Rprgrab2, 0.4);
                r.moveServo(r.Lprgrab2, 0.4);
                sleep(2000);
                r.Rprspin2.setPower(0.0);
                r.Lprspin2.setPower(0.0);
                moveBot(27, FORWARD, 1.0);
                eTurnBot(290, RIGHT, 1.0, 1.0);
                autodone = 3;
            }
            if (autodone == 2 && vuTarget == 1 ){ //good
                moveBot(6, FORWARD, 1.0);
                eTurnBot(230, LEFT, 1.0, 1.0);
                moveBot(33, BACKWARD, 1.0);
                sleep(500);
                r.Rprspin2.setPower(0.6);
                r.Lprspin2.setPower(-0.6);
                sleep(500);
                r.moveServo(r.Rprgrab2, 0.4);
                r.moveServo(r.Lprgrab2, 0.4);
                sleep(2000);
                r.Rprspin2.setPower(0.0);
                r.Lprspin2.setPower(0.0);
                moveBot(35, FORWARD, 1.0);
                eTurnBot(240, RIGHT, 1.0, 1.0);
                autodone = 3;


            }



        }

    }
}
