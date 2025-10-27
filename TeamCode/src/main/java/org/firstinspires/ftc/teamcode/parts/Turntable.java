package org.firstinspires.ftc.teamcode.parts;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.MecanumDrive;

public class Turntable extends OpMode {

    public DcMotor turn;
    MecanumDrive drive;
    public double ticksPerRad;

    @Override
    public void init() {
        drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));
        ticksPerRad = (384.5*((double)85/25)) / (2*Math.PI);
    }

    @Override
    public void loop() {
        drive.updatePoseEstimate();

        Pose2d pose = drive.localizer.getPose();
        double x = pose.position.x;
        double y = pose.position.y;
        double heading = pose.heading.toDouble();

        double turretHeading = Math.atan2(y, -x) - heading; //TODO: Make sure this is right when testing
        turn.setTargetPosition((int)(turretHeading * ticksPerRad + 0.5));
    }
}
