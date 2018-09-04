package org.firstinspires.ftc.teamcode.RogueV1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RogueV1.RogueAutoMethods;

/**
 * Created by samue on 2/3/2017.
 */
@Autonomous(name="RogueLineUpTest", group="RogueAuto")
@Disabled
public class RogueLineUpTest extends RogueAutoMethods {
    public void runOpMode() throws InterruptedException{
        setupAll();
        waitForStart();
        gyroCalibrate();
        r.lightMiddle.calibrate();
        r.lightBack.calibrate();
        r.lightRight.calibrate();
        mrCalibrate();
        adaRGBCalibrate();


        r.lightMiddle.wakeUp();
        r.lightBack.wakeUp();
        goUntilLine(r.lightMiddle, 0.25, 0.25);
        sleep(300);
        goUntilLine(r.lightMiddle, -0.2, -0.2);
        sleep(350);
        if(!r.lightMiddle.isOnLine()){
            goUntilLine(r.lightMiddle, 0.18, 0.18, (RogueLight.WHITE_THRESHOLD * 0.6));
        }
        sleep(350);


        eSpinBot(40, Direction.RIGHT, 0.63, EndStatus.COAST);
        goUntilLine(r.lightBack, 0.43, -0.43);
        sleep(300);
        goUntilLine(r.lightBack, -0.35, 0.35);
        sleep(350);
        if(!r.lightBack.isOnLine()){
            goUntilLine(r.lightBack, 0.35, -0.35, (RogueLight.WHITE_THRESHOLD * 0.6));
            sleep(200);
        }

        double sonarReading;
        boolean isSeen = false;
        int goodTracker = 0;
        int badTracker = 0;
        r.moveDrivetrain(0.33, 0.33);
        final double BASE = 0.29;
        final double COEFF = 0.24;
        for(sonarReading = r.sonar.getUltrasonicLevel(), isSeen = false, goodTracker = 0, badTracker = 0; !isSeen; sonarReading = r.sonar.getUltrasonicLevel()){
            //telemetry.addData("sonarReading: ", sonarReading);
            if((sonarReading >= 1.0 && sonarReading <= 19.0)){
                goodTracker++;
                if(goodTracker > 5){
                    isSeen = true;
                }
            }
            else if(goodTracker > 0){
                badTracker++;
                if(badTracker > 2){
                    goodTracker = 0;
                    badTracker = 0;
                }
            }
            else {

            }
            double lightVal = (r.lightBack.getReading() - 0.5);
            lightVal *= COEFF;
            r.moveDrivetrain((BASE - lightVal), (BASE + lightVal));
            //telemetry.addData("trackers: ", "good/ " + goodTracker + " | bad/ " + badTracker);
        }
        r.stopDrivetrain();
        telemetry.addData("STATUS: ", "SAW WALL");
        updateTelemetry(telemetry);

        while(opModeIsActive()){
            telemetry.addData("LightValMiddle: ", r.lightMiddle.getReading());
            telemetry.addData("IsMiddleOverLine?: ", r.lightMiddle.isOnLine());
            telemetry.addData("LightValFront: ", r.lightBack.getReading());
            telemetry.addData("IsFrontOverLine?: ", r.lightBack.isOnLine());
            telemetry.addData("LightValRight: ", r.lightRight.getReading());
            telemetry.addData("IsRightOverLine?: ", r.lightRight.isOnLine());
            updateTelemetry(telemetry);
        }
    }
}
