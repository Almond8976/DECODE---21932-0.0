package org.firstinspires.ftc.teamcode.auto;


import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.parts.Kicker;
import org.firstinspires.ftc.teamcode.parts.Mortar;

public class FirstAuto extends LinearOpMode {
    Kicker kicker = new Kicker(hardwareMap);
    Mortar flywheel = new Mortar(hardwareMap);
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0,0,0);

        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));

        waitForStart();


    }
    public void Launch() {
        flywheel.setFlyMotorSpeed(1);
        for(int i = 0; i < 3; i++) {
            kicker.setKickerPositon(0.5);
            kicker.setKickerPositon(0);
        }
    }
    public void intake() {

    }

}