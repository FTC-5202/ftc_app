package org.firstinspires.ftc.teamcode.RogueV1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Created by samue on 2/8/2017.
 */
@Autonomous(name="RogueSnotRocket", group = "RogueAuto")
@Disabled
public class RogueSnotRocket extends RogueAutoMethods {

    public void runOpMode() throws InterruptedException{
        setupAll();
        waitForStart();
        r.moveServo(r.hood, r.HOOD_UP);
        sleep(300);
        fire();
        r.moveServo(r.hood, r.HOOD_DOWN);
        sweep(5000);
        r.moveServo(r.hood, r.HOOD_UP);
        sleep(300);
        fire();
        r.moveServo(r.hood, r.HOOD_DOWN);
        //moveBot(50, BACKWARD, 1.0);

    }
}
