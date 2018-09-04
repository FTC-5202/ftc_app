package org.firstinspires.ftc.teamcode.RogueV1.RogueTestFiles;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RogueV1.RogueAutoMethods;

/**
 * Created by samue on 12/23/2016.
 */
@Autonomous(name = "RogueTurnTest", group = "RogueAutoTest")
@Disabled
public class RogueTurnTest extends RogueAutoMethods {
    public RogueTurnTest() {
        //Constuctor
    }

    public void runOpMode() throws InterruptedException{
        setupAll();
        waitForStart();
        r.moveDrivetrain(0.6, -0.6);
        sleep(400);
        r.moveDrivetrain(0.45, -0.45);
        while(opModeIsActive()){

        }
        r.stopDrivetrain();
    }
}
