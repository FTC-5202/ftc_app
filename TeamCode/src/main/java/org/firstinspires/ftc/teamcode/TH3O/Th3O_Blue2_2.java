package org.firstinspires.ftc.teamcode.TH3O;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static org.firstinspires.ftc.teamcode.TH3O.TH3OAutoMethods.Direction.LEFT;
import static org.firstinspires.ftc.teamcode.TH3O.TH3OAutoMethods.Direction.RIGHT;

/**
 *  by Hannah on 1/12/2018.
 */
@Autonomous(name = "Th-3O Blue2")
@Disabled
public class Th3O_Blue2_2 extends TH3OAutoMethods {

    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() {
        boolean checkColor = true;
        boolean autodone = false;
        boolean vuMarkREAD = false;
        boolean ForB = false;
        int vuTarget = 0;
        int vuCount = 0;
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

        r.Rarm.setPosition(0.0);
        sleep(1000);
        r.moveServo(r.Rprgrab2, 0.7);
        r.moveServo(r.Lprgrab2, 0.3);
        sleep(500);
        r.moveServo(r.Rprgrab2, 0.15); //open position
        r.moveServo(r.Lprgrab2, 0.72);
        sleep(500);

        //Color.RGBToHSV(r.RVc1.red() * 8, r.RVc1.green() * 8, r.RVc1.blue() * 8, hsvValues);
        Color.RGBToHSV(r.RVc2.red() * 8, r.RVc2.green() * 8, r.RVc2.blue() * 8, hsvValues);

        telemetry.addData("LED", r.bLedOn ? "On" : "Off");
        //telemetry.addData("Red1  ", r.RVc1.red());
        //telemetry.addData("Blue1 ", r.RVc1.blue());
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

            /*if ((r.RVc1.blue() >= 1.0) && (r.RVc1.blue() > r.RVc1.red()) & checkColor) {
                telemetry.addData("Blue1", r.RVc1.blue());
                telemetry.update();

                moveBot(2.5, FORWARD, 0.5);
                sleep(500);
                r.moveServo(r.Rarm, 0.9);
                sleep(1000);
                moveBot(22, BACKWARD, 0.5);
                sleep(500);
                r.moveServo(r.Rprgrab2, 0.28);
                r.moveServo(r.Lprgrab2, 0.57);
                ForB = true;
                checkColor = false;
            }*/
            if ((r.RVc2.blue() >= 1.0) && (r.RVc2.blue() > r.RVc2.red()) & checkColor) {
                telemetry.addData("Blue2", r.RVc2.blue());
                telemetry.update();
                eTurnBot(15, LEFT, 0.3, 0.3);
                sleep(200);
                r.moveServo(r.Rarm, 0.9);
                sleep(200);
                eTurnBot(20, RIGHT, 0.3, 0.3); //was 15
                sleep(200);
                moveBot(21, BACKWARD, 0.7); //was 5 --11
                r.moveServo(r.Rprgrab2, 0.35); //was 0.28
                r.moveServo(r.Lprgrab2, 0.5); //was 0.57
                checkColor = false;
                ForB = true;
            }

            if ((r.RVc2.red() >= 1.0) && (r.RVc2.red() > r.RVc2.blue()) & checkColor) {
                telemetry.addData("Red2", r.RVc2.red());
                telemetry.update();
                eTurnBot(15, RIGHT, 0.5, 0.5);
                sleep(200);
                r.moveServo(r.Rarm, 0.9);
                sleep(1800);
                eTurnBot(20, LEFT, 0.5, 0.5); //was 15
                sleep(500);
                moveBot(21, BACKWARD, 0.7); //was 17
                sleep(500);
                r.moveServo(r.Rprgrab2, 0.35);
                r.moveServo(r.Lprgrab2, 0.5);
                checkColor = false;

            }
            if (checkColor) {
                telemetry.addLine(" NO Color Val");
                telemetry.update();

                r.moveServo(r.Rarm, 0.9);
                moveBot(17, BACKWARD, 1.0);
                sleep(500);
                r.moveServo(r.Rprgrab2, 0.28);
                r.moveServo(r.Lprgrab2, 0.57);
                checkColor = false;

            }
            //7-18-19 good for now
            if (vuTarget == 2 && !autodone && !ForB) { //good
                telemetry.addLine("RIGHT COLUMN, Saw: RED JEWEL");
                telemetry.update();
                sleep(1500);
                moveBot(11, BACKWARD, 1.0);
                sleep(500);
                eTurnBot(65, LEFT, 1.0, 1.0);
                sleep(500);
                moveBot(6, BACKWARD, 0.4); //was 11.5
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
                autodone = true;

            }
            //7-18-19 good for now
            if (vuTarget == 2 && !autodone && ForB) { //good
                telemetry.addLine("RIGHT COLUMN, Saw: BLUE JEWEL");
                telemetry.update();
                sleep(1500);
                moveBot(11, BACKWARD, 0.7); //was 8 --4.5
                sleep(500);
                eTurnBot(65, LEFT, 0.7, 0.7); //was 58 --40
                sleep(500);
                moveBot(6, BACKWARD, 0.7); //was 11.5 --5.5
                sleep(500);
                r.Rprspin2.setPower(-0.6);
                r.Lprspin2.setPower(0.6);
                sleep(1000);
                r.Rprspin2.setPower(0.0);
                r.Lprspin2.setPower(0.0);
                r.moveServo(r.Rprgrab2, 0.15);
                r.moveServo(r.Lprgrab2, 0.72);
                sleep(500);
                moveBot(7, FORWARD, 0.4); //was 7 --3.5
                autodone = true;

            }
            //7-18-18 good for now
            if (vuTarget == 1 && !autodone && !ForB) { //good
                telemetry.addLine("LEFT COLUMN, Saw: RED JEWEL");
                telemetry.update();
                sleep(1500);
                moveBot(12, BACKWARD, 0.7);
                sleep(500);
                eTurnBot(145, LEFT, 0.7, 0.7);
                sleep(500);
                moveBot(8, BACKWARD, 0.5);
                sleep(500);
                r.Rprspin2.setPower(-0.6);
                r.Lprspin2.setPower(0.6);
                sleep(1000);
                r.Rprspin2.setPower(0.0);
                r.Lprspin2.setPower(0.0);
                r.moveServo(r.Rprgrab2, 0.15);
                r.moveServo(r.Lprgrab2, 0.72);
                sleep(500);
                moveBot(8, FORWARD, 0.7);
                autodone = true;

            }
            //7/18/18 good for now
            if (vuTarget == 1 && !autodone && ForB) { //good
                    telemetry.addLine("LEFT COLUMN, Saw: BLUE JEWEL");
                    telemetry.update();
                    sleep(1500);
                    moveBot(12, BACKWARD, 0.7); //was 15
                    sleep(500);
                    eTurnBot(145,LEFT, 0.7, 0.7); //was 150
                    sleep(500);
                    moveBot(8, BACKWARD, 0.5); //was 13
                    sleep(500);
                    r.Rprspin2.setPower(-0.6);
                    r.Lprspin2.setPower(0.6);
                    sleep(1000);
                    r.Rprspin2.setPower(0.0);
                    r.Lprspin2.setPower(0.0);
                    r.moveServo(r.Rprgrab2, 0.15);
                    r.moveServo(r.Lprgrab2, 0.72);
                    sleep(500);
                    moveBot(4, FORWARD, 0.7);
                    autodone = true;

            }
            //7-19-18 good for now
            if (vuTarget == 3 && !autodone && !ForB) { //good
                telemetry.addLine("CENTER COLUMN, Saw: RED JEWEL");
                telemetry.update();
                sleep(1500);
                moveBot(5, BACKWARD, 0.7); //was 7.5
                sleep(500);
                eTurnBot(63, LEFT, 0.7, 0.7);
                sleep(500);
                moveBot(8, BACKWARD, 0.5); //was 11
                sleep(500);
                r.Rprspin2.setPower(-0.6);
                r.Lprspin2.setPower(0.6);
                sleep(1000);
                r.Rprspin2.setPower(0.0);
                r.Lprspin2.setPower(0.0);
                r.moveServo(r.Rprgrab2, 0.15);
                r.moveServo(r.Lprgrab2, 0.72);
                sleep(500);
                moveBot(8, FORWARD, 0.7);
                autodone = true;

            }
            //7-18-18 good for now
            if (vuTarget == 3 && !autodone && ForB) { //good
                telemetry.addLine("CENTER COLUMN, Saw: BLUE JEWEL");
                telemetry.update();
                sleep(1500);
                moveBot(5, BACKWARD, 0.7); //was 7.5
                sleep(500);
                eTurnBot(58, LEFT, 0.7, 0.7);
                sleep(500);
                moveBot(6, BACKWARD, 0.5); //was 11
                sleep(500);
                r.Rprspin2.setPower(-0.6);
                r.Lprspin2.setPower(0.6);
                sleep(1000);
                r.Rprspin2.setPower(0.0);
                r.Lprspin2.setPower(0.0);
                r.moveServo(r.Rprgrab2, 0.15);
                r.moveServo(r.Lprgrab2, 0.72);
                sleep(500);
                moveBot(8, FORWARD, 0.7);
                autodone = true;

            }
            if (vuTarget == 0 && !autodone && !ForB) { //good
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

            }

            telemetry.addData("VuMark", "%s visible", vuTarget);
            telemetry.update();



        }

    }
}
