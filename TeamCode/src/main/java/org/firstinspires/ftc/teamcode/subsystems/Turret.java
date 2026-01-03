package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.PinpointLocalizer;

import java.util.HashMap;

@Config
public class Turret {

    //private DcMotor turret;
    private Servo servoTurret;
    private Servo servoTurret2;
    private MecanumDrive drive;
    private PinpointLocalizer localizer;

    private double ticksPerRad = (384.5*((double)85/25)) / (2*Math.PI);;

    private double rotationLimit = Math.PI * 208;

    public static double rotationSpeed = 1, maxRange = Math.toRadians(350);
    private double x, y, heading, turretHeading, turretHeadingRelative;

    public static boolean tracking = false;

    private double pos;

    private Pose2d pose;

    public static Vector2d blueBasket = new Vector2d(-71,-71);
    public static Vector2d redBasket = new Vector2d(-71, 71);
    public static Vector2d curBasket;

    public static double angleOffset = 0;

    public Turret(HardwareMap hardwareMap, HashMap<String, String> config, Pose2d startPos) {
        drive = new MecanumDrive(hardwareMap, startPos); // TODO: set this to whatever position auton will end at
        pose = startPos;
        servoTurret = hardwareMap.get(Servo.class, config.get("turret"));
        servoTurret2 = hardwareMap.get(Servo.class, config.get("turret2"));
//        turret = hardwareMap.get(DcMotor.class, config.get("turretMotor"));
//
//        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public Pose2d getPose() {
        return pose;
    }
    public double getTurretHeading() {
        return turretHeading;
    }

    public double getTurretHeadingRelative() { return turretHeadingRelative; }

    public void setBasketPos(Vector2d pos) {
        curBasket = pos;
    }
    public Vector2d distanceToBasket() {
        return new Vector2d(curBasket.y-y, curBasket.x-x);
    }

    public void setPosition(double degrees) {
        pos = Math.toRadians(degrees);
    }

//    public void resetEncoder() {
//        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//    }

    public void resetRobotPose(Pose2d newPos) {
        drive.localizer.setPose(newPos);
    }

//    public int getTargetPosition() {
//        return turret.getTargetPosition();
//    }

    public void update() {

        drive.updatePoseEstimate();

        pose = drive.localizer.getPose();
        x = pose.position.x;
        y = pose.position.y;
        heading = pose.heading.toDouble();

        turretHeadingRelative = Math.atan2(curBasket.y-y, curBasket.x-x);
        turretHeading = turretHeadingRelative - heading;

        turretHeading+=angleOffset;

        turretHeading %= 2*Math.PI;
        if(turretHeading>Math.PI) {
            turretHeading -= 2*Math.PI;
        }
        else if(turretHeading<-Math.PI) {
            turretHeading += 2*Math.PI;
        }
//        if(turretHeading < -3*Math.PI/4) {
//            turretHeading = -3 * Math.PI/4;
//        }

        if(tracking) {
            servoTurret.setPosition((-turretHeading / maxRange) + .5);
            servoTurret2.setPosition((-turretHeading / maxRange) + .5);
        }
        else {
            servoTurret.setPosition((-pos/maxRange) + .5);
            servoTurret2.setPosition((-pos/maxRange) + .5);
        }

        //for turret with motor
//        if (tracking) {
//            turret.setTargetPosition((int) ((turretHeading * ticksPerRad)));
//            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            turret.setPower(rotationSpeed);
//        }
//        else {
//            turret.setTargetPosition(pos);
//            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            turret.setPower(rotationSpeed);
//        }

    }
}
