package org.firstinspires.ftc.teamcode.parts;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
public class Turret {

    public DcMotor turret;
    public MecanumDrive drive;

    public double ticksPerRad;

    public double rotationLimit;

    public Turret(HardwareMap hardwareMap) {
        drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));
        turret = hardwareMap.get(DcMotor.class, "turret");
        turret.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void init() {
        ticksPerRad = (384.5*((double)85/25)) / (2*Math.PI);
        rotationLimit = 3.14159 * 208;
    }


    public void update() {
        drive.updatePoseEstimate();

        Pose2d pose = drive.localizer.getPose();
        double x = pose.position.x;
        double y = pose.position.y;
        double heading = pose.heading.toDouble();

        double turretHeading = Math.atan2(y, -x) - heading; //TODO: Make sure this is right when testing

        if(turret.getTargetPosition() > rotationLimit) {
            turret.setTargetPosition((int)((turretHeading * ticksPerRad + 0.5)-(rotationLimit*2)));
        }
        else if(turret.getTargetPosition() < -rotationLimit) {
            turret.setTargetPosition((int)((turretHeading * ticksPerRad + 0.5)+(rotationLimit*2)));
        }
        else {
            turret.setTargetPosition((int)((turretHeading * ticksPerRad + 0.5)));
        }
        turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
