package org.firstinspires.ftc.teamcode.auto;


import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Mortar;
import org.firstinspires.ftc.teamcode.subsystems.Util;

public class FirstAuto extends LinearOpMode {

    Util util;
    Kicker kicker;
    Mortar shooter;

    @Override
    public void runOpMode() throws InterruptedException {
        // init all subsystems (switch to using wrappers if you want parallel movement)
        util = new Util();
        kicker = new Kicker(hardwareMap, util.deviceConf);
        shooter = new Mortar(hardwareMap, util.deviceConf);

        // define startpose, in, in, rad
        Pose2d startPose = new Pose2d(0,0,0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        // TODO: create trajectories using TrajectoryActionBuilder (Use .afterTime() for parallel actions)

        // TODO: start update thread
        // TODO: move everything to start position (after init, before program start)

        waitForStart();

        // run actions using Actions.runBlocking()


    }

    // Define all functions here (if you call subsystems movements from here it wont be parallel)
    public void Launch() {
        shooter.setPower(1);
        for(int i = 0; i < 3; i++) {
            kicker.setPosition(kicker.UP);
            kicker.setPosition(kicker.DOWN);
        }
    }
    public void intake() {

    }

}