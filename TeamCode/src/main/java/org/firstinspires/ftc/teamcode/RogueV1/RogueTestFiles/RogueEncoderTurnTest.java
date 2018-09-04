package org.firstinspires.ftc.teamcode.RogueV1.RogueTestFiles;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RogueV1.RogueAutoMethods;

/**
 * Created by samue on 11/23/2016.
 */
@Autonomous(name="RogueEncoderTurnTest", group="RogueAuto")
public class RogueEncoderTurnTest extends RogueAutoMethods {

    public RogueEncoderTurnTest() {
        //constructor
    }

    public void runOpMode() throws InterruptedException {
        setupAll();
        waitForStart();
        eCoastTurnBot(90, Direction.LEFT, 0.7, 0.7);
        sleep(1000);
        eCoastTurnBot(90, Direction.RIGHT, 0.7, 0.7);

    }

}
