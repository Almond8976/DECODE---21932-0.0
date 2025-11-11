package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.PinpointLocalizer;

import java.util.HashMap;

@Config
public class Turret {

    private DcMotor turret;
    private MecanumDrive drive;
    private PinpointLocalizer localizer;

    private double ticksPerRad = (384.5*((double)85/25)) / (2*Math.PI);;

    private double rotationLimit = Math.PI * 208;;

    public static double rotationSpeed = 1;
    private double x, y, heading, turretHeading, turretHeadingRelative;

    public static boolean tracking = true;

    private Pose2d pose;

    public static Vector2d blueBasket = new Vector2d(-66,-66);
    public static Vector2d redBasket = new Vector2d(-66, 66);

    public Turret(HardwareMap hardwareMap, HashMap<String, String> config) {
        drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0)); // TODO: set this to whatever position auton will end at
        turret = hardwareMap.get(DcMotor.class, config.get("turretMotor"));

        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public Pose2d getPose() {
        return pose;
    }
    public double getTurretHeading() {
        return turretHeading;
    }

    public double getTurretHeadingRelative() { return turretHeadingRelative; }


    public void update() {

        drive.updatePoseEstimate();

        pose = drive.localizer.getPose();
        x = pose.position.x;
        y = pose.position.y;
        heading = pose.heading.toDouble();

        turretHeadingRelative = Math.atan2(redBasket.y-y, redBasket.x-x);
        turretHeading = turretHeadingRelative - heading; //TODO: find a function that given the robots position will find the angle to the basket (assume basket is at 0, 0)


        turretHeading %= 2*Math.PI;
        if(turretHeading>Math.PI) {
            turretHeading -= 2*Math.PI;
        }
        else if(turretHeading<-Math.PI) {
            turretHeading += 2*Math.PI;
        }
        /*if (turret.getTargetPosition() > rotationLimit) {
            turret.setTargetPosition((int) ((turretHeading * ticksPerRad) - (rotationLimit * 2)));
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            turret.setPower(rotationSpeed);
        } else if (turret.getTargetPosition() < -rotationLimit) {
            turret.setTargetPosition((int) ((turretHeading * ticksPerRad) + (rotationLimit * 2)));
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            turret.setPower(rotationSpeed);
        } else {
            turret.setTargetPosition((int) ((turretHeading * ticksPerRad)));
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            turret.setPower(rotationSpeed);
        }*/
        if (tracking) {
            turret.setTargetPosition((int) ((turretHeading * ticksPerRad)));
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            turret.setPower(rotationSpeed);
        }

    }
}
