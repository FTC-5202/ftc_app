package org.firstinspires.ftc.teamcode.RogueV1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RogueV1.RogueAutoMethods;

/**
 * Created by samue on 2/21/2017.
 */
@Autonomous(name="RogueLightTest", group="RogueAuto")
@Disabled
public class RogueLightTest extends RogueAutoMethods {

    public void runOpMode() throws InterruptedException{
        setupAll();
        waitForStart();
        r.lightMiddle.calibrate();
        int x = 1000;
        double[] test1 = testSpeed(r.lightMiddle, x);
        double[] test2 = testSpeed(r.lightMiddle, x);
        double[] test3 = testSpeed(r.lightMiddle, x);
        telemetry.addData("TEST 1: ", "INT= " + test1[0] + " AVE= " + test1[1] + " MAX= " + test1[2]);
        telemetry.addData("TEST 2: ", "INT= " + test2[0] + " AVE= " + test2[1]+ " MAX= " + test2[2]);
        telemetry.addData("TEST 3: ", "INT= " + test3[0] + " AVE= " + test3[1]+ " MAX= " + test3[2]);
        updateTelemetry(telemetry);
        while(opModeIsActive()){

        }
    }
}
