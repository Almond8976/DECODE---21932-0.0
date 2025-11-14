
package org.firstinspires.ftc.teamcode.auto;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Mortar;
import org.firstinspires.ftc.teamcode.subsystems.Turret;
import org.firstinspires.ftc.teamcode.subsystems.Util;

@Config
@Autonomous(name = "FirstAutoRed")
public class FirstAutoRed extends LinearOpMode{

    Util util;
    Kicker kicker;
    Mortar shooter;
    Turret turret;
    Intake intake;

    private int ballCount = 3;

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

        kicker.setPosition(Kicker.DOWN);
        TrajectoryActionBuilder trajPreload = drive.actionBuilder(startPose)
                .strafeToConstantHeading(new Vector2d(-41.1914631184, 13.6936191855));



        TrajectoryActionBuilder trajLeave = drive.actionBuilder(new Pose2d(new Vector2d(-41.1914631184, 13.6936191855), Math.toRadians(128.188)))//TODO: find ending pose
                .lineToY(55);

        Thread update = new Thread( ()-> updateAll(turret, shooter, kicker));

        // TODO: move everything to start position (after init, before program start)


        update.start();
        waitForStart();

        Actions.runBlocking(
                new SequentialAction(
                        trajPreload.build(),
                        Launch(),
                        trajLeave.build()
                )
        );
    }
    // Define all functions here (if you call subsystems movements from here it wont be parallel)
    public Action Launch() {

            boolean metShooterThresh = false;
            int shooterTargetSpeed = shooter.calcVelocity(Math.sqrt(
                    (turret.distanceToBasket().x * turret.distanceToBasket().x) + (turret.distanceToBasket().y * turret.distanceToBasket().y)));
            Turret.tracking = true;
            shooter.setVelocity(shooterTargetSpeed);
            time1.reset();

            while (ballCount > 0 && time1.seconds() < 5 && opModeIsActive()) {
                if (shooter.getVelocity() > shooterTargetSpeed - Mortar.THRESH) {
                    switch (ballCount) {
                        case 0:
                            intake.setAllPower(0);
                            shooter.setVelocity(Mortar.OFF);
                            Turret.tracking = false;
                            break;
                        case 1:
                        case 2:
                        case 3:
                            intake.setAllPower(1);
                            break;
                    }
                } else if (shooter.getVelocity() <= shooterTargetSpeed - Mortar.THRESH && metShooterThresh) {
                    ballCount--;
                    intake.setAllPower(0);
                }
                metShooterThresh = shooter.getVelocity() > shooterTargetSpeed - Mortar.THRESH;

            }
        return new TodoAction;
    }

    public void updateAll(Turret turret, Mortar shooter, Kicker kicker){
        while (opModeIsActive()) {
            turret.update();
            shooter.update();
            kicker.update();
        }
    }
}
