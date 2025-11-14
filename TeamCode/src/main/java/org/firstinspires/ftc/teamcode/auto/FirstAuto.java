package org.firstinspires.ftc.teamcode.auto;


import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Mortar;
import org.firstinspires.ftc.teamcode.subsystems.Turret;
import org.firstinspires.ftc.teamcode.subsystems.Util;

public class FirstAuto extends LinearOpMode {

    Util util;
    Kicker kicker;
    Mortar shooter;
    Turret turret;
    Intake intake;

    ElapsedTime time1 = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        // init all subsystems (switch to using wrappers if you want parallel movement)
        util = new Util();
        kicker = new Kicker(hardwareMap, util.deviceConf);
        shooter = new Mortar(hardwareMap, util.deviceConf);
        turret = new Turret(hardwareMap, util.deviceConf, new Pose2d(-57.78, 45.6439, Math.toRadians(128.188)));
        intake = new Intake(hardwareMap, util.deviceConf);



        // define startpose, in, in, rad
        Pose2d startPose = new Pose2d(-57.78, 45.6439, Math.toRadians(128.188));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        turret.setBasketPos(Turret.redBasket);

        TrajectoryActionBuilder trajPreload = drive.actionBuilder(startPose)
                .strafeToConstantHeading(new Vector2d(-41.1914631184, 13.6936191855));
        Launch();


        turret.update();
        shooter.update();
        kicker.update();
        // TODO: move everything to start position (after init, before program start)

        waitForStart();

        Actions.runBlocking(
                new SequentialAction(
                        trajPreload.build()
                )
        );
    }
    // Define all functions here (if you call subsystems movements from here it wont be parallel)
    public void Launch() {
        turret.tracking = true;
        shooter.setPower(1);
    }
    public void intake() {
        intake.setAllPower(1);
    }
}