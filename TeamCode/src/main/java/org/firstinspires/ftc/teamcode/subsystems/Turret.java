package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MecanumDrive;

import java.util.HashMap;

@Config
public class Turret {

    private DcMotor turret;
    private MecanumDrive drive;

    private double ticksPerRad = (384.5*((double)85/25)) / (2*Math.PI);;

    private double rotationLimit = Math.PI * 208;;

    private double x, y, heading, turretHeading;

    private boolean tracking = true;

    private Pose2d pose;

    public Turret(HardwareMap hardwareMap, HashMap<String, String> config) {
        drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0)); // TODO: set this to whatever position auton will end at
        turret = hardwareMap.get(DcMotor.class, config.get("turretMotor"));

        turret.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void enableTracking() {
        tracking = true;
    }

    public void disableTracking() {
        tracking = false;
    }

    public void toggleTracking() {
        tracking = !tracking;
    }

    public Pose2d getPose() {
        return pose;
    }


    public void update() {
        if (tracking) {
            drive.updatePoseEstimate();

            pose = drive.localizer.getPose();
            x = pose.position.x;
            y = pose.position.y;
            heading = pose.heading.toDouble();

            turretHeading = Math.atan2(y, -x) - heading; //TODO: find a function that given the robots position will find the angle to the basket (assume basket is at 0, 0)

            if (turret.getTargetPosition() > rotationLimit) {
                turret.setTargetPosition((int) ((turretHeading * ticksPerRad + 0.5) - (rotationLimit * 2)));
            } else if (turret.getTargetPosition() < -rotationLimit) {
                turret.setTargetPosition((int) ((turretHeading * ticksPerRad + 0.5) + (rotationLimit * 2)));
            } else {
                turret.setTargetPosition((int) ((turretHeading * ticksPerRad + 0.5)));
            }
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }
}
