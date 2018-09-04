package org.firstinspires.ftc.teamcode.RogueV1;

import android.os.SystemClock;
import android.provider.Settings;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannelController;

/**
 * Created by samue on 11/14/2016.
 */
public class RogueAutoMethods extends LinearOpMode {

    public RogueElectrical r = new RogueElectrical();
    public RogueAutoMethods(){
        //Constructor
    }


    public void setupMotors(){
        r.leftSide = hardwareMap.dcMotor.get("left");
        r.rightSide = hardwareMap.dcMotor.get("right");
        r.radapult = hardwareMap.dcMotor.get("radapult");
        r.sweeper = hardwareMap.dcMotor.get("sweeper");
        //r.lift = hardwareMap.dcMotor.get("lift");
        r.moveDrivetrain(0,0);
        r.radapult.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.leftSide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.rightSide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r.leftSide.setDirection(DcMotor.Direction.REVERSE);
        r.rightSide.setDirection(DcMotor.Direction.REVERSE);

    }

    public void setupServos(){
        r.leftArm = hardwareMap.servo.get("larm");
        r.rightArm = hardwareMap.servo.get("rarm");
        r.leftPlate = hardwareMap.servo.get("lplate");
        r.rightPlate = hardwareMap.servo.get("rplate");
        r.release = hardwareMap.servo.get("release");
        r.lightArm = hardwareMap.servo.get("lightArm");
        r.hood = hardwareMap.servo.get("hood");
        r.lSpooker = hardwareMap.servo.get("lSpook");
        r.rSpooker = hardwareMap.servo.get("rSpook");
        r.moveServo(r.lSpooker, r.LSPOOK_UP);
        r.moveServo(r.rSpooker, r.RSPOOK_UP);
        r.moveServo(r.hood, r.HOOD_DOWN);
        r.moveServo(r.lightArm, r.LIGHTARM_DOWN);
        r.moveServo(r.release, r.FORK_HOME);
        r.moveServo(r.leftArm, r.LARMIN);
        r.moveServo(r.rightArm, r.RARMIN);
        r.moveServo(r.leftPlate, r.LPLATEIN);
        r.moveServo(r.rightPlate, r.RPLATEIN);

    }


    public void setupSensors(){
        r.touchSensor = hardwareMap.touchSensor.get("touch");
        r.ada = hardwareMap.colorSensor.get("ada");
        r.mr = hardwareMap.colorSensor.get("mr");
        r.sonar = hardwareMap.ultrasonicSensor.get("sonar");
        r.gyro = hardwareMap.gyroSensor.get("gyro");
        r.lightMiddle.setMyLight(hardwareMap.opticalDistanceSensor.get("lightMiddle"));
        r.lightBack.setMyLight(hardwareMap.opticalDistanceSensor.get("lightBack"));
        r.lightRight.setMyLight(hardwareMap.opticalDistanceSensor.get("lightRight"));
        r.lightLeft.setMyLight(hardwareMap.opticalDistanceSensor.get("lightLeft"));
        /*
        r.lightMiddle = hardwareMap.opticalDistanceSensor.get("lightMiddle");
        r.lightBack = hardwareMap.opticalDistanceSensor.get("lightBack");*/

        r.cdim = hardwareMap.deviceInterfaceModule.get("dim");
        r.cdim.setDigitalChannelMode(r.LED_CHANNEL, DigitalChannelController.Mode.OUTPUT);
        r.cdim.setDigitalChannelState(r.LED_CHANNEL, led);
        r.mr.enableLed(led);
        r.sideSonar = hardwareMap.ultrasonicSensor.get("sideSonar");

    }

    public void setupAll(){
        setupMotors();
        setupServos();
        setupSensors();
    }

    public void pause(long time){        // Time is in milliseconds
        long startTime = SystemClock.elapsedRealtime();
        long currentTime;

        for(currentTime = SystemClock.elapsedRealtime(); currentTime - startTime < time; currentTime = SystemClock.elapsedRealtime()){
            //Do nothing
        }

    }


    static double gyro_info[];
    final int OFFSET = 0;
    final int MAX = 1;
    final int MIN = 2;

    /**
     * double[] GyroCalibrate
     *
     * Calibrate the gyro and return the values for storage
     *
     * @return gyro offset (index 0) and gyro value fluctuation (index 1)
     */

    public void gyroCalibrate(){            // old method for gyro values -- getRotation. NOTE -- Find right method for reading

        double firstReading = r.gyro.rawZ();
        double min = firstReading;
        double max = firstReading;
        int nReadings = 100;
        double average = firstReading;

        for(int i = 0; i < nReadings; i++){
            double reading = r.gyro.rawZ();
            average += reading;


            if(reading < min){
                min = reading;
            }
            if(reading > max){
                max = reading;
            }
        }
        average /= nReadings;
        //average = (average / nReadings);
        double[] holder = {average, max, min};
        gyro_info = holder;

    }


    public enum Turn {
        LEFT,
        RIGHT
    }

    public void turnBot(int goal, Turn direction, double lPow, double rPow, EndStatus status){ // Integrated Z -- + LEFT, - RIGHT
        int current = ((ModernRoboticsI2cGyro) r.gyro).getIntegratedZValue();
        int target;
        int good = 0;
        int bad = 0;
        boolean isDone = false;
        if(direction == Turn.LEFT){
            target = current + goal;
            lPow = Math.abs(lPow) * -1;
            rPow = Math.abs(rPow);
            r.moveDrivetrain(lPow, rPow);
            for(current = ((ModernRoboticsI2cGyro)r.gyro).getIntegratedZValue(); current < target; current = ((ModernRoboticsI2cGyro)r.gyro).getIntegratedZValue()){
              /*  if(current >= target){
                    good++;
                    if(good > 14){
                        isDone = true;
                    }
                }
                else if(good > 0){
                    bad++;
                    if(bad > 2){
                        good = 0;
                        bad = 0;
                    }
                }
                else {

                }*/
            }

        }
        else if (direction == Turn.RIGHT){
            target = current - goal;
            lPow = Math.abs(lPow);
            rPow = Math.abs(rPow) * -1;
            r.moveDrivetrain(lPow, rPow);
            for(current = ((ModernRoboticsI2cGyro)r.gyro).getIntegratedZValue(); current > target; current = ((ModernRoboticsI2cGyro)r.gyro).getIntegratedZValue()){
/*                if(current <= target){
                    good++;
                    if(good > 14){
                        isDone = true;
                    }
                }
                else if(good > 0){
                    bad++;
                    if(bad > 2){
                        good = 0;
                        bad = 0;
                    }
                }
                else {

                }*/
            }

        }
        else {

        }

        if(status == EndStatus.STOP){
            r.stopDrivetrain();
        }

    }

    public void turnBot(int goal, Turn direction, double lPow, double rPow){
        turnBot(goal, direction, lPow, rPow, EndStatus.STOP);
    }

    public void spinBot(int goal, Turn direction, double pow, EndStatus status){
        turnBot(goal, direction, pow, pow, status);
    }

    public void spinBot(int goal, Turn direction, double pow){
        turnBot(goal, direction, pow, pow, EndStatus.STOP);
    }


    final int LEFT = 1;
    final int RIGHT = -1;
/*
OLD GYRO TURNBOT
    public void turnBot(int goal, int direction, double lPower, double rPower){
        int currentHeading = ((ModernRoboticsI2cGyro)r.gyro).getIntegratedZValue();
        int target = currentHeading + (goal * direction);
        double lPow = Math.abs(lPower) * direction * -1;
        double rPow = Math.abs(rPower) * direction;
        r.moveDrivetrain(lPow, rPow);


        if(direction == RIGHT){
            for(currentHeading = ((ModernRoboticsI2cGyro)r.gyro).getIntegratedZValue(); currentHeading > target; currentHeading = ((ModernRoboticsI2cGyro)r.gyro).getIntegratedZValue()){

            }
        }
        else{
            for(currentHeading = ((ModernRoboticsI2cGyro)r.gyro).getIntegratedZValue(); currentHeading < target; currentHeading = ((ModernRoboticsI2cGyro)r.gyro).getIntegratedZValue()){

            }
        }
        r.stopDrivetrain();
    }*/


                    /*
                    Custom Gyro telemetry
                telemetry.addData("gyro degrees: ", degreesTurned);
                telemetry.addData("GYRO RAW: ", r.gyro.rawZ());
                telemetry.addData("GYRO OFFSET: ", gyro_info[OFFSET]);
                telemetry.addData("MAX, MIN: ", gyro_info[MAX] + ", " + gyro_info[MIN]);
                updateTelemetry(telemetry);*/

    /*
    public void turnBot(double target, int direction, double lpower, double rpower){
        final double MILLIS = 0.001;

        target = Math.abs(target);

        long timeLast= SystemClock.elapsedRealtime();
        long timeNow = SystemClock.elapsedRealtime();

        r.moveDrivetrain(lpower * direction, rpower * direction);

        if(direction == RIGHT){
            for(double degreesTurned = 0; degreesTurned < target; timeLast = timeNow){
                timeNow = SystemClock.elapsedRealtime();
                double gyroReading = r.gyro.rawZ() -gyro_info[OFFSET];
                if(gyroReading > (gyro_info[OFFSET] + 300) || gyroReading < (gyro_info[OFFSET]-300)){
                    degreesTurned += ((gyroReading) * ((timeNow - timeLast) * MILLIS) * (Math.PI/180));

                }
            }
        }
        else {
            for(double degreesTurned = 0; degreesTurned > (target * -1) ; timeLast = timeNow){
                timeNow = SystemClock.elapsedRealtime();
                double gyroReading = r.gyro.rawZ() -gyro_info[OFFSET];
                if(gyroReading > (gyro_info[OFFSET] + 300) || gyroReading < (gyro_info[OFFSET]-300)){
                    degreesTurned += ((gyroReading) * ((timeNow - timeLast) * MILLIS) * (Math.PI/180));

                }
            }
        }

        r.stopDrivetrain();
    }

    public void turnBot(double target, int direction, double lpower, double rpower, int millis){
        final double MILLIS = 0.001;

        target = Math.abs(target);

        long startT = SystemClock.elapsedRealtime();
        long timeLast= SystemClock.elapsedRealtime();
        long timeNow = SystemClock.elapsedRealtime();

        r.moveDrivetrain(lpower * direction, rpower * direction);

        if(direction == RIGHT){
            for(double degreesTurned = 0; degreesTurned < target && timeNow - startT < millis; timeLast = timeNow){
                timeNow = SystemClock.elapsedRealtime();
                degreesTurned += (r.gyro.rawZ() - gyro_info[OFFSET]) * ((timeNow - timeLast) * MILLIS);
                telemetry.addData("gyro", degreesTurned);
                telemetry.addData("gyro offset", gyro_info[OFFSET]);
                updateTelemetry(telemetry);
            }
        }
        else {
            for(double degreesTurned = 0; degreesTurned > target * -1 && timeNow - startT < millis; timeLast = timeNow){
                timeNow = SystemClock.elapsedRealtime();
                degreesTurned += (r.gyro.rawZ() - gyro_info[OFFSET]) * ((timeNow - timeLast) * MILLIS);
                telemetry.addData("gyro", degreesTurned);
                telemetry.addData("gyro offset", gyro_info[OFFSET]);
                updateTelemetry(telemetry);
            }
        }

        r.stopDrivetrain();
        telemetry.addData("stopped!", "time " + (timeNow - startT) );
    }

    public void spinBot(double target, int direction, double power){
        turnBot(target, direction, power, -power);
    }*/

    final double TICKS_PER_INCH = 1120 / (4 * Math.PI); // Ticks per rotation / inches (circumference) per rotation = ticks per inch
    final double WHEEL_GEAR_RATIO = 1.0/2.0;               // Green stuff adds a quarter of an inch to diameter

    final int FORWARD = 1;
    final int BACKWARD = -1;

    int inches_to_ticks(double target){
        return (int)(target * TICKS_PER_INCH * WHEEL_GEAR_RATIO);
    }

    public enum EndStatus {
        STOP, COAST
    }

    public void moveBot(double distance, int direction, double power, EndStatus status){
        int target = inches_to_ticks(distance);
        int startPos = r.leftSide.getCurrentPosition();
        int currentPos = r.leftSide.getCurrentPosition();
        r.moveDrivetrain(power * direction, power * direction);

        while(Math.abs(currentPos - startPos) < target){
            currentPos = r.leftSide.getCurrentPosition();
        }

        if(status == EndStatus.STOP){
            r.stopDrivetrain();
        }

    }

    public void moveBot(double distance, int direction, double power){
        moveBot(distance, direction, power, EndStatus.STOP);
    }

    public void easeMoveBot(double distance, int direction, double power){
        if(distance <= 3){
            moveBot(distance, direction, power);
        }
        else {
            double eDist = distance * 0.6;
            moveBot(eDist, direction, power, EndStatus.COAST);
            moveBot((distance - eDist), direction, 0.25);
        }
    }

    public void fire(){
        if(!r.touchSensor.isPressed()){
            r.moveMotor(r.radapult, r.RADAPULT_ON);
            while(!r.touchSensor.isPressed()) ;
            r.moveMotor(r.radapult, r.RADAPULT_OFF);
            pause(300);
        }

        r.moveMotor(r.radapult, r.RADAPULT_ON);
        while(r.touchSensor.isPressed()) ;
        while(!r.touchSensor.isPressed()) ;
        r.moveMotor(r.radapult, r.RADAPULT_OFF);

    }

    public void sweep(int time){
        if(!r.touchSensor.isPressed()){
            r.moveMotor(r.radapult, r.RADAPULT_ON);
            while(!r.touchSensor.isPressed()) ;
            r.moveMotor(r.radapult, r.RADAPULT_OFF);
            pause(300);
        }
        r.moveMotor(r.sweeper, 1.0);
        long currentTime = SystemClock.elapsedRealtime();
        for(long startTime = SystemClock.elapsedRealtime(); currentTime - startTime < time; currentTime = SystemClock.elapsedRealtime()){
            if(!r.touchSensor.isPressed()){
                r.moveMotor(r.radapult, r.RADAPULT_ON);
            }
            else {
                r.moveMotor(r.radapult, r.RADAPULT_OFF);
            }
        }
        r.moveMotor(r.sweeper, 0.0);
    }

    public void fireTwo(){
        fire();
        sweep(1900);
        fire();
    }


    //LIGHT\\
    /*
    static double[] lightReadingAverage = new double[2];
    final double LIGHT_MAXTHRESHOLD = 0.5;


    final int MIDDLE = 0;
    final int BACK = 1;


    public void lightCalibrate(OpticalDistanceSensor light){
        double average = 0;
        int nReadings = 1000;

        for(int i = 0; i < nReadings; i++){
            double reading = light.getLightDetected();
            average += reading;
            if(i > 0){
                average /= 2;
            }
        }
        int sgn = MIDDLE;
        if(light.equals(r.lightBack)){
            sgn = BACK;
        }

        lightReadingAverage[sgn] = average;
    }

    public double getLightReading(OpticalDistanceSensor light){
        int sgn = MIDDLE;
        if(light.equals(r.lightBack)){
            sgn = BACK;
        }
        double holder = light.getLightDetected() - lightReadingAverage[sgn];
        if(holder < 0){
            holder = 0;
        }

        if(holder > LIGHT_MAXTHRESHOLD){
            holder = LIGHT_MAXTHRESHOLD;
        }
        holder = holder / LIGHT_MAXTHRESHOLD;
        return holder;
    }

    public void runLight(){
        for(int i = 0; i < 2000; i++){
            getLightReading(r.lightMiddle);
            getLightReading(r.lightBack);
        }
    }*/

    public void runSonar(){
        for(int i = 0; i < 500; i++){
            r.sonar.getUltrasonicLevel();
        }
    }

    //BEACON

    final double TOBEACON_TIMEOUT = 3000;
    double[] gracie = new double[4];
    double[] mrValues = new double[3];
    final int RED = 0;
    final int BLUE = 1;
    final int GREEN = 2;
    final int BRIGHT = 3;

    static String color = "RED";

    boolean led = false;

    public void adaRGBCalibrate(){
        double rAverage = 0;
        double bAverage = 0;
        double gAverage = 0;
        double lightAverage = 0;
        int nReadings = 5000;

        for(int i = 0; i < nReadings; i++){
            double red = r.ada.red(), blue = r.ada.blue(), green = r.ada.green(), brigid = r.ada.alpha();
            rAverage += red;
            bAverage += blue;
            gAverage += green;
            lightAverage += brigid;

            if(i > 0){
                rAverage /=2;
                bAverage /=2;
                gAverage /=2;
                lightAverage /=2;
            }

        }

        gracie[RED] = rAverage;
        gracie[BLUE] = bAverage;
        gracie[GREEN] = gAverage;
        gracie[BRIGHT] = lightAverage;

    }


    public void mrCalibrate(){
        double rAverage = 0;
        double bAverage = 0;
        double gAverage = 0;
        int nReadings = 5000;

        for(int i = 0; i < nReadings; i++){
            double red = r.mr.red(), blue = r.mr.blue(), green = r.mr.green();
            rAverage += red;
            bAverage += blue;
            gAverage += green;

            if(i > 0){
                rAverage /=2;
                bAverage /=2;
                gAverage /=2;
            }

            mrValues[RED] = rAverage;
            mrValues[BLUE] = bAverage;
            mrValues[GREEN] = gAverage;

        }
    }

    public double[] getAdaRGBvalues(){
        double[] holder = {r.ada.red(), r.ada.blue(), r.ada.green(), r.ada.alpha()};

        for(int i = 0; i < holder.length; i++){
            holder[i] -= gracie[i];
            if(holder[i] <= 0.0){
                holder[i] = 0;
            }
        }

        return holder;

    }

    public double[] getMRValues(){
        double[] holder = {r.mr.red(), r.mr.blue(), r.mr.green()};

        for(int i = 0; i < holder.length; i++){
            holder[i] -= mrValues[i];
            if(holder[i] <= 0.0){
                holder[i] = 0;
            }
        }

        return holder;
    }

    public String getSideToHit(){
        double adaAve[] = getAdaRGBvalues();
        double mrAve[] = getMRValues();
        double[] adaHolder;
        double[] mrHolder;
        telemetry.addData("STATUS: ", "STARTING");
        updateTelemetry(telemetry);
        for (int i = 0; i < 4000; i++) {
            adaHolder = getAdaRGBvalues();
            mrHolder = getMRValues();

            for (int j = 0; j < 4; j++) {    //
                adaAve[j] += adaHolder[j];
                adaAve[j] /= 2.0;
            }
            for (int k = 0; k < 3; k++) {
                mrAve[k] += mrHolder[k];
                mrAve[k] /= 2.0;
            }
            telemetry.addData("STATUS: ", "GETTING VALUES...");
            updateTelemetry(telemetry);
        }

        boolean adaSeenBlue = false;
        boolean adaSeenRed = false;
        boolean mrSeenBlue = false;
        boolean mrSeenRed = false;
        telemetry.addData("STATUS: ", "MAKING DECISIONS...");
        updateTelemetry(telemetry);
        if (adaAve[BLUE] >= 70) {
            adaSeenBlue = true;
            if(adaAve[RED] > 0.0 && (adaAve[BLUE] / adaAve[RED]) <= 3.0){
                adaSeenBlue = false;
            }
        }
        if (adaAve[RED] >= 70) {
            adaSeenRed = true;
            if(adaAve[BLUE] > 0.0 && (adaAve[RED] / adaAve[BLUE]) <= 1.6){
                adaSeenRed = false;
            }
        }

        if (mrAve[BLUE] >= 3.0) {
            mrSeenBlue = true;
            if(mrAve[RED] > 0.0 && (mrAve[BLUE] / mrAve[RED]) <= 3.0){
                mrSeenBlue = false;
            }
        }
        if (mrAve[RED] >= 1.0) {
            mrSeenRed = true;
            if(mrAve[BLUE] > 0.0 && (mrAve[RED] / mrAve[BLUE]) <= 1.6){
                mrSeenRed = false;
            }
        }


        int leftColor;
        int rightColor;

        if (adaSeenBlue && mrSeenRed) {
            leftColor = BLUE;
            rightColor = RED;
        } else if (adaSeenRed && mrSeenBlue) {
            leftColor = RED;
            rightColor = BLUE;
        } else if (adaSeenBlue && !mrSeenBlue) {
            leftColor = BLUE;
            rightColor = RED;
        } else if (mrSeenBlue && !adaSeenBlue) {
            leftColor = RED;
            rightColor = BLUE;
        }                                        // Maybe add cases for when one says red
        else {
            leftColor = -1;
            rightColor = -1;
        }

                /*if (leftColor == -1) {
                    rightColor = -1;
                }*/
        String sideToHit;


        if (leftColor == -1 && rightColor == -1) {
            sideToHit = "FAILURRRRRRRE";

        } else if (leftColor == BLUE && color.equals("BLUE")) {  // Start by finding the blue side, then switch
            sideToHit = "LEFT";
        } else if (rightColor == BLUE && color.equals("BLUE")) {
            sideToHit = "RIGHT";
        } else if (leftColor == RED && color.equals("RED")) {
            sideToHit = "LEFT";
        } else if (rightColor == RED && color.equals("RED")) {
            sideToHit = "RIGHT";
        } else {
            sideToHit = "Ummmm...... What? You got nothing? Impressive. You seem to have defied the laws of nature and entered the MATRIX";
        }
        return sideToHit;
    }

    public void leftOut(){
        double lAPos = r.LARMIN;
        double lPPos = r.LPLATEIN;
        while(lAPos < r.LARMOUT){
            lAPos += r.CHANGERATEJH;
            double lArmRecip = 1.0 - lAPos;
            if(lAPos <= r.LTRIGGER){
                lPPos = lArmRecip - r.LOUT_OFFSET;
            }
            else {
                double recipLCenter = lArmRecip + r.LCENTER_OFFSET;
                lPPos = recipLCenter;
            }
            r.moveServo(r.leftArm, lAPos);
            r.moveServo(r.leftPlate, lPPos);
            telemetry.addData("LEFT", "h");
            updateTelemetry(telemetry);
        }
        r.moveServo(r.leftArm, r.LARMOUT);
        r.moveServo(r.leftPlate, r.LPLATEOUT);
    }
    public void leftIn(){
        double lAPos = r.LARMOUT;
        double lPPos = r.LPLATEOUT;
        double loldPos = lAPos;
        long currentTime = SystemClock.elapsedRealtime();
        long startTime = SystemClock.elapsedRealtime();
        while(lAPos > r.LARMIN){
            currentTime = SystemClock.elapsedRealtime();
            lPPos = (1.0 - lAPos) - r.LOUT_OFFSET;
            if((currentTime - startTime) < 500){
                lAPos = loldPos;
            }
            lAPos -= r.CHANGERATEJH;
            r.moveServo(r.leftArm, lAPos);
            r.moveServo(r.leftPlate, lPPos);
        }
        r.moveServo(r.leftArm, r.LARMIN);
        r.moveServo(r.leftPlate, r.LPLATEIN);
    }

    public void rightOut(){
        double rArmPos = r.RARMIN;
        double rPPos = r.RPLATEIN;
        while(rArmPos > r.RARMOUT){
            rArmPos -= r.CHANGERATEJH;
            double rArmRecip = 1.0 - rArmPos;
            if(rArmPos >= r.RTRIGGER){
                rPPos = rArmRecip + r.ROUT_OFFSET;
            }
            else {
                double recipRCenter = rArmRecip - r.RCENTER_OFFSET;
                rPPos = recipRCenter;
            }
            r.moveServo(r.rightArm, rArmPos);
            r.moveServo(r.rightPlate, rPPos);
            telemetry.addData("RIGHT", "h");
            updateTelemetry(telemetry);
        }
        r.moveServo(r.rightArm, r.RARMOUT);
        r.moveServo(r.rightPlate, r.RPLATEOUT);
    }

    public void rightIn(){
        double rAPos = r.RARMOUT;
        double rPPos = r.RPLATEOUT;
        double roldPos = rAPos;
        long currentTime = SystemClock.elapsedRealtime();
        long startTime = SystemClock.elapsedRealtime();
        while(rAPos < r.RARMIN){
            currentTime = SystemClock.elapsedRealtime();
            rAPos += r.CHANGERATEJH;
            double rArmRecip = 1.0 - rAPos;
            rPPos = rArmRecip + r.ROUT_OFFSET;
            if((currentTime - startTime) < 500){
                rAPos = roldPos;
            }
            r.moveServo(r.rightArm, rAPos);
            r.moveServo(r.rightPlate, rPPos);
        }
        r.moveServo(r.rightArm, r.RARMIN);
        r.moveServo(r.rightPlate, r.RPLATEIN);
    }

    //test
    final double MINPOWER = 0.44;
    public enum Direction{
        LEFT,
        RIGHT
    }
    public void eTurnBot(double degrees, Direction dir, double lPow, double rPow, EndStatus status){

        DcMotor encoderMotor = (lPow == 0.0) ? r.rightSide : r.leftSide;
        int startPos = encoderMotor.getCurrentPosition();
        double dToR = (Math.PI/180.0);
        double rad = degrees * dToR;
        final double HALFWHEELBASE = 8.5; //inches
        int coeff = (lPow == 0.0 || rPow == 0.0) ? 2 : 1;
        double sLength = rad * (coeff * HALFWHEELBASE);
        double target = inches_to_ticks(sLength);
        double sgn = 0;
        if(dir == Direction.LEFT){
            if(encoderMotor.equals(r.leftSide)){
                sgn = 1;
            }
            else {
                sgn = -1;
            }
            lPow = Math.abs(lPow) * -1;
            rPow = Math.abs(rPow);
        }
        else if(dir == Direction.RIGHT){
            if(encoderMotor.equals(r.leftSide)){
                sgn = -1;
            }
            else {
                sgn = 1;
            }
            lPow = Math.abs(lPow);
            rPow = Math.abs(rPow) * -1;
        }
        else {

        }

        r.moveDrivetrain(lPow, rPow);
        if(sgn == 1){
            for(int currentPos = encoderMotor.getCurrentPosition(); (currentPos - startPos) < target; currentPos = encoderMotor.getCurrentPosition()){
                telemetry.addData("Distance left: ", target - (currentPos - startPos));
                updateTelemetry(telemetry);
            }
        }
        else if(sgn == -1){
            target *= -1.0;
            for(int currentPos = encoderMotor.getCurrentPosition(); (currentPos - startPos) > target; currentPos = encoderMotor.getCurrentPosition()){
                telemetry.addData("Distance left: ", target - (currentPos - startPos));
                updateTelemetry(telemetry);
            }
        }
        else {
            telemetry.addData("BAD:", " FAIL");
            updateTelemetry(telemetry);
            pause(3000);
        }
        if(status == EndStatus.STOP){
            r.stopDrivetrain();
        }

    }

    public void eTurnBot(double degrees, Direction dir, double lPow, double rPow){
        eTurnBot(degrees, dir, lPow, rPow, EndStatus.STOP);
    }

    public void eCoastTurnBot(double degrees, Direction dir, double lPow, double rPow){
        if(degrees < 15){
            eTurnBot(degrees, dir, lPow, rPow);
        }
        else {
            double d = degrees * 0.63;
            eTurnBot(d, dir, lPow, rPow, EndStatus.COAST);
            if(lPow != 0.0){
                lPow *= 0.5;
                lPow = (lPow <= MINPOWER) ? MINPOWER : lPow;
                }
            if(rPow != 0.0){
                rPow *= 0.5;
                rPow = (rPow <= MINPOWER) ? MINPOWER : rPow;
            }
            eTurnBot((degrees - d), dir, lPow, rPow);
        }
    }

    public void eSpinBot(double degrees, Direction dir, double pow, EndStatus status){
        eTurnBot(degrees, dir, pow ,pow, status);
    }

    public void eSpinBot(double degrees, Direction dir, double pow){
        eTurnBot(degrees, dir, pow, pow);
    }

    public void eCoastSpinBot(double degrees, Direction dir, double pow){
        eCoastTurnBot(degrees, dir, pow, pow);
    }

    public void goUntilLine(RogueLight lSensor, double lPow, double rPow, double thresh){
        r.moveDrivetrain(lPow, rPow);
        for(double reading = lSensor.getReading(); reading < (thresh);){
            reading = lSensor.getReading();
        }
        r.stopDrivetrain();
    }
    public void goUntilLine(RogueLight lSensor, double lPow, double rPow, double thresh, long time){
        r.moveDrivetrain(lPow, rPow);

        long start = SystemClock.elapsedRealtime();
        long current = SystemClock.elapsedRealtime();
        for(double reading = lSensor.getReading(); reading < (thresh) && (current - start) < time;){
            current = SystemClock.elapsedRealtime();
            reading = lSensor.getReading();
        }
        r.stopDrivetrain();
    }
    public void goUntilLine(RogueLight lSensor, double lPow, double rPow,  long time){
        goUntilLine(lSensor, lPow, rPow, RogueLight.WHITE_THRESHOLD, time);
    }
    public void goUntilLine(RogueLight lSensor, double lPow, double rPow){
        goUntilLine(lSensor, lPow, rPow, RogueLight.WHITE_THRESHOLD);
    }
    public double[] testSpeed(RogueLight lSensor, int x){
        long startTime = SystemClock.elapsedRealtime();
        r.moveDrivetrain(0.5, 0.5);
        int j = 0;
        double max = 0;
        long current = SystemClock.elapsedRealtimeNanos();
        long last = SystemClock.elapsedRealtimeNanos();
        for(double reading = lSensor.getReading() ; j < x; j++, current = SystemClock.elapsedRealtimeNanos()){
            reading = lSensor.getReading();
            max = (current - last > max) ? (double)(current - last) : max;
            last = current;
        }
        long endTime = SystemClock.elapsedRealtime();
        r.stopDrivetrain();
        double interval = (double)(endTime - startTime);
        max /= 1000000;
        double average = interval / (double)x;
        double holder[] = {interval, average, max};
        return holder;
    }


    public void runOpMode() throws InterruptedException{

    }




}
