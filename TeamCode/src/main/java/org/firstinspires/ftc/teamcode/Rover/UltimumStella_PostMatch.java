package org.firstinspires.ftc.teamcode.Rover;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="UltimumStella PostMatch")
public class UltimumStella_PostMatch extends UltimumStella_AutoMethods {
    //@Override
    public void runOpmode() {


        while (opModeIsActive()) {

            if (r.sensorTouch.getState() == true) {
                r.Lift.setPower(-1.0);
            }

            else {
                r.Lift.setPower(0);
            }
        }

    }

}







