
package org.firstinspires.ftc.teamcode.auto;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.rr_wrappers.IntakeWrapper;
import org.firstinspires.ftc.teamcode.subsystems.Gate;
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
    Gate gate;

    private MecanumDrive drive;

    public static int KICKER_WAIT_TIME = 2000;

    private int ballCount = 3, shooterTargetSpeed;

    ElapsedTime time1 = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        // init all subsystems (switch to using wrappers if you want parallel movement)
        util = new Util();
        kicker = new Kicker(hardwareMap, util.deviceConf);
        shooter = new Mortar(hardwareMap, util.deviceConf);
        turret = new Turret(hardwareMap, util.deviceConf, new Pose2d(-57.78, 45.6439, Math.toRadians(128.188)));
        intake = new Intake(hardwareMap, util.deviceConf);
        gate = new Gate(hardwareMap, util.deviceConf);



        // define startpose, in, in, rad
        Pose2d startPose = new Pose2d(-57.78, 45.6439, Math.toRadians(128.188));
        drive = new MecanumDrive(hardwareMap, startPose);
        turret.setBasketPos(Turret.redBasket);

        kicker.setPosition(Kicker.DOWN);
        TrajectoryActionBuilder trajPreload = drive.actionBuilder(startPose)
                .strafeToConstantHeading(new Vector2d(-20, 20));

        TrajectoryActionBuilder trajSetUpOne = drive.actionBuilder(new Pose2d(new Vector2d(-20, 20), Math.toRadians(-128.188)))//TODO: find ending pose
                .strafeToSplineHeading(new Vector2d(-11, 33), Math.toRadians(90));

        TrajectoryActionBuilder trajPickupOne = drive.actionBuilder(new Pose2d(new Vector2d(-11, 33), Math.toRadians(90)))
                .strafeToConstantHeading(new Vector2d(-11, 53))
                .turnTo(0)
                .strafeToConstantHeading(new Vector2d(0, 54))
                .strafeToConstantHeading(new Vector2d(0, 58));

        TrajectoryActionBuilder trajShootOne = drive.actionBuilder(new Pose2d(new Vector2d(0, 58), Math.toRadians(0)))
                .strafeToConstantHeading(new Vector2d(-20, 20));

        TrajectoryActionBuilder trajSetTwo = drive.actionBuilder(new Pose2d(new Vector2d(-20, 20), Math.toRadians(0)))
                .strafeToSplineHeading(new Vector2d(11, 33), Math.toRadians(90))
                .strafeToConstantHeading(new Vector2d(11, 53))
                .strafeToConstantHeading(new Vector2d(-20, 20));

        TrajectoryActionBuilder trajSetThree = drive.actionBuilder(new Pose2d(new Vector2d(-20, 20), Math.toRadians(90)))
                .strafeToConstantHeading(new Vector2d(36, 33))
                .strafeToConstantHeading(new Vector2d(36, 53))
                .strafeToConstantHeading(new Vector2d(-20, 20));

        Thread update = new Thread( ()-> updateAll(turret, shooter, kicker, intake, gate));

        // TODO: move everything to start position (after init, before program start)



        waitForStart();
        update.start();
        shooter.setVelocity(shooter.calcVelocity(73.5391));

        Actions.runBlocking(
                new SequentialAction(
                        trajPreload.build()
                )
        );

        Launch();

        Actions.runBlocking(
                new SequentialAction(
                        trajSetUpOne.build()
                )
        );
        gate.setPosition(Gate.CLOSE);
        intake.setIntakePower(1);

        Actions.runBlocking(
                new SequentialAction(
                        trajPickupOne.build()
                )
        );

        Actions.runBlocking(
                new SequentialAction(
                        trajShootOne.build()
                )
        );
        Launch();

        Actions.runBlocking(
                new SequentialAction(
                        trajSetTwo.build()
                )
        );
        Launch();

        Actions.runBlocking(
                new SequentialAction(
                        trajSetThree.build()
                )
        );
        Launch();

    }
    // Define all functions here (if you call subsystems movements from here it wont be parallel)

    public void Launch() {

        turret.tracking = true;
        shooterTargetSpeed = shooter.calcVelocity(
                Math.sqrt(
                        (turret.distanceToBasket().x * turret.distanceToBasket().x) + (turret.distanceToBasket().y * turret.distanceToBasket().y)
                )
        );
        shooter.setVelocity(shooterTargetSpeed);

        shooter.setVelocity(shooterTargetSpeed);
        while (shooter.getVelocity() < shooterTargetSpeed - Mortar.THRESH) {
            gate.setPosition(Gate.OPEN);
        }
        intake.setIntakePower(1);

        sleep(KICKER_WAIT_TIME);
        kicker.sweep();
        gate.setPosition(Gate.CLOSE);

        turret.tracking = false;

        turret.setPosition(0);
        kicker.update();
        turret.update();
    }

    public void updateAll(Turret turret, Mortar shooter, Kicker kicker, Intake intake, Gate gate){
        while (opModeInInit() || opModeIsActive())
        {
            shooter.update();
            kicker.update();
            turret.update();
            intake.update();
            gate.update();

            telemetry.addData("Heading", turret.getPose().heading);
            telemetry.addData("Gate Position", gate.getPosition());
            telemetry.update();
        }
    }
}
