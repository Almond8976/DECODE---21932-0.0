package org.firstinspires.ftc.teamcode.auto;


import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Mortar;
import org.firstinspires.ftc.teamcode.subsystems.Util;

public class FirstAuto extends LinearOpMode {
    Util util = new Util();
    Kicker kicker = new Kicker(hardwareMap, util.deviceConf);
    Mortar flywheel = new Mortar(hardwareMap, util.deviceConf);
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0,0,0);

        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));

        waitForStart();


    }
    public void Launch() {
        flywheel.setPower(1);
        for(int i = 0; i < 3; i++) {
            kicker.setPosition(kicker.UP);
            kicker.setPosition(kicker.DOWN);
        }
    }
    public void intake() {

    }

}