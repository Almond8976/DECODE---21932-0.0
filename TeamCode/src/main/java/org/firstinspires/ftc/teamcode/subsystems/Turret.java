package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.PinpointLocalizer;

import java.util.HashMap;

@Config
public class Turret {

    private DcMotorEx turret;
    private MecanumDrive drive;
    private PinpointLocalizer localizer;

    private PIDController pid;

    private double ticksPerRad = (384.5*((double)85/25)) / (2*Math.PI);;

    private double rotationLimit = Math.PI * 208;

    private double x, y, heading, turretHeading, turretHeadingRelative;

    public static boolean tracking = false;

    private int pos;

    private Pose2d pose;

    public static Vector2d blueBasket = new Vector2d(-71,-71);
    public static Vector2d redBasket = new Vector2d(-71, 71);
    public static Vector2d curBasket;

    public static double kP = 0.005;
    public static double kD = 0.0006;
    public static double kF = 0.015;

    public Turret(HardwareMap hardwareMap, HashMap<String, String> config, Pose2d startPos) {
        drive = new MecanumDrive(hardwareMap, startPos); // TODO: set this to whatever position auton will end at
        pose = startPos;
        turret = hardwareMap.get(DcMotorEx.class, config.get("turretMotor"));

        pid = new PIDController(kP, 0, kD);

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

    public void setBasketPos(Vector2d pos) {
        curBasket = pos;
    }
    public Vector2d distanceToBasket() {
        return new Vector2d(curBasket.y-y, curBasket.x-x);
    }

    public void setPosition(int ticks) {
        pos = ticks;
    }

    public void resetEncoder() {
        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void resetRobotPose(Pose2d newPos) {
        drive.localizer.setPose(newPos);
    }

    public int getTargetPosition() {
        return turret.getTargetPosition();
    }

    public void update() {
        pid.setPID(kP, 0, kD);

        drive.updatePoseEstimate();

        pose = drive.localizer.getPose();
        x = pose.position.x;
        y = pose.position.y;
        heading = pose.heading.toDouble();

        turretHeadingRelative = Math.atan2(curBasket.y-y, curBasket.x-x);
        turretHeading = turretHeadingRelative - heading;


        turretHeading %= 2*Math.PI;
        if(turretHeading>Math.PI) {
            turretHeading -= 2*Math.PI;
        }
        else if(turretHeading<-Math.PI) {
            turretHeading += 2*Math.PI;
        }
        if(turretHeading < -3*Math.PI/4) {
            turretHeading = -3 * Math.PI/4;
        }
        int target = tracking ? (int) (turretHeading * ticksPerRad) : pos;
        double power = pid.calculate(turret.getCurrentPosition(), target);
        power += kF * Math.signum(power);
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turret.setPower(power);
    }
}
