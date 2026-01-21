
package org.firstinspires.ftc.teamcode.auto;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
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
@Autonomous(name = "Red15BallClose")
public class Red15BallClose extends LinearOpMode{

    Util util;
    Kicker kicker;
    Mortar shooter;
    Turret turret;
    IntakeWrapper intakeWr;
    Intake intake;
    Gate gate;

    private MecanumDrive drive;

    public static int KICKER_WAIT_TIME = 600;

    private int shooterTargetSpeed;
    private double target;

    ElapsedTime time1 = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        // init all subsystems (switch to using wrappers if you want parallel movement)
        util = new Util();
        kicker = new Kicker(hardwareMap, util.deviceConf);
        shooter = new Mortar(hardwareMap, util.deviceConf);
        turret = new Turret(hardwareMap, util.deviceConf, new Pose2d(-57.78, 45.6439, Math.toRadians(128.188)));
        intakeWr = new IntakeWrapper(hardwareMap, util.deviceConf);
        intake = new Intake(hardwareMap, util.deviceConf);
        gate = new Gate(hardwareMap, util.deviceConf);



        // define startpose, in, in, rad
        Pose2d startPose = new Pose2d(-57.78, 45.6439, Math.toRadians(128.188));
        drive = new MecanumDrive(hardwareMap, startPose);
        turret.setBasketPos(Turret.redBasket);
        kicker.setPosition(Kicker.DOWN);

        TrajectoryActionBuilder trajPreload = drive.actionBuilder(startPose)
                .strafeToSplineHeading(new Vector2d(-12, 16), Math.toRadians(90));

        TrajectoryActionBuilder trajIntakeSpikeOne = drive.actionBuilder(new Pose2d(new Vector2d(-12, 16), Math.toRadians(90)))
                .strafeToConstantHeading(new Vector2d(-12, 50));

        TrajectoryActionBuilder trajPickupSpikeOne = drive.actionBuilder(new Pose2d(new Vector2d(-12, 50), Math.toRadians(90)))
                .afterTime(0, intakeWr.startIntake())
                //.strafeToConstantHeading(new Vector2d(-12, 50)) in intakeSpikeOne
                .afterTime(1, intakeWr.stopIntake())
                //.strafeToSplineHeading(new Vector2d(-2, -50), Math.toRadians(-180))
                //.strafeToConstantHeading(new Vector2d(-2, -58));
                .strafeToSplineHeading(new Vector2d(-20, 20), Math.toRadians(90));
//                .strafeToSplineHeading(new Vector2d(-2, -50), Math.toRadians(-180))
//                .strafeToConstantHeading(new Vector2d(-2, -58));

        TrajectoryActionBuilder trajSpikeTwoSetUp = drive.actionBuilder(new Pose2d(new Vector2d(-20, 20), Math.toRadians(90)))
                .strafeToConstantHeading(new Vector2d(12, 29));

        TrajectoryActionBuilder trajIntakeSpikeTwo = drive.actionBuilder(new Pose2d(new Vector2d(12, 29), Math.toRadians(90)))
                .strafeToConstantHeading(new Vector2d(12, 60));

        TrajectoryActionBuilder trajSpikeTwo = drive.actionBuilder(new Pose2d(new Vector2d(12, 60), Math.toRadians(90)))
                .afterTime(0, intakeWr.startIntake())
                .afterTime(1, intakeWr.stopIntake())
                .strafeToSplineHeading(new Vector2d(-2, 50), Math.toRadians(0))
                .strafeToConstantHeading(new Vector2d(-2, 58));

        TrajectoryActionBuilder trajShootTwo = drive.actionBuilder(new Pose2d(new Vector2d(-2, 58), Math.toRadians(0)))
                /*.strafeToConstantHeading(new Vector2d(-20, 20));*/
                .strafeToSplineHeading(new Vector2d(-20, 20), Math.toRadians(90));

        TrajectoryActionBuilder trajSetUpSpikeThree = drive.actionBuilder(new Pose2d(new Vector2d(-20, 20), Math.toRadians(90)))
                .strafeToConstantHeading(new Vector2d(36, 29));

        TrajectoryActionBuilder trajIntakeSpikeThree = drive.actionBuilder(new Pose2d(new Vector2d(36, 29), Math.toRadians(90)))
                .strafeToConstantHeading(new Vector2d(36, 60));

        TrajectoryActionBuilder trajSpikeThree = drive.actionBuilder(new Pose2d(new Vector2d(36, 60), Math.toRadians(90)))
                //.strafeToConstantHeading(new Vector2d(36, 29))
                .afterTime(0, intakeWr.startIntake())
                //.strafeToConstantHeading(new Vector2d(36, 60))
                .afterTime(1, intakeWr.stopIntake())
                .strafeToConstantHeading(new Vector2d(36, 40))
                .strafeToConstantHeading(new Vector2d(-20, 20));

        TrajectoryActionBuilder trajIntakeHumanPlayer = drive.actionBuilder(new Pose2d(new Vector2d(-20, 20), Math.toRadians(90)))
                .strafeToSplineHeading(new Vector2d(36, 64), Math.toRadians(0))
                .strafeToConstantHeading(new Vector2d(62, 64), new TranslationalVelConstraint(70));

        TrajectoryActionBuilder trajHumanPlayer = drive.actionBuilder(new Pose2d(new Vector2d(62, 64), Math.toRadians(0)))
                //.strafeToSplineHeading(new Vector2d(36, 64), Math.toRadians(0))
                //.strafeToConstantHeading(new Vector2d(62, 64), new TranslationalVelConstraint(70))
                .strafeToSplineHeading(new Vector2d(-20, 20), Math.toRadians(90));

        TrajectoryActionBuilder trajLeave = drive.actionBuilder(new Pose2d(new Vector2d(-20, 20), Math.toRadians(90)))
                .strafeToSplineHeading(new Vector2d(0, 50), Math.toRadians(180));

        Thread update = new Thread( ()-> updateAll(turret, shooter, kicker, intake, gate, intakeWr));

        // TODO: move everything to start position (after init, before program start)



        waitForStart();
        update.start();
        shooter.setVelocity(shooter.calcVelocity((71-20)*Math.sqrt(2)));
        Turret.tracking = true;

        Actions.runBlocking(
                new SequentialAction(
                        trajPreload.build()
                )
        );

        Launch();

        intake.setAllPower(1);
        Actions.runBlocking(
                new SequentialAction(
                        trajIntakeSpikeOne.build()
                )
        );
        intake.setAllPower(0);
        Actions.runBlocking(
                new SequentialAction(
                        trajPickupSpikeOne.build()
                )
        );
        Launch();

        Actions.runBlocking(
                new SequentialAction(
                        trajSpikeTwoSetUp.build()
                )
        );
        intake.setAllPower(1);
        Actions.runBlocking(
                new SequentialAction(
                        trajIntakeSpikeTwo.build()
                )
        );
        intake.setAllPower(0);
        Actions.runBlocking(
                new SequentialAction(
                        trajSpikeTwo.build()
                )
        );
        sleep(400);
        Actions.runBlocking(
                new SequentialAction(
                        trajShootTwo.build()
                )
        );
        Launch();

        Actions.runBlocking(
                new SequentialAction(
                        trajSetUpSpikeThree.build()
                )
        );

        intake.setAllPower(1);
        Actions.runBlocking(
                new SequentialAction(
                        trajIntakeSpikeThree.build()
                )
        );
        intake.setAllPower(0);

        Actions.runBlocking(
                new SequentialAction(
                        trajSpikeThree.build()
                )
        );
        Launch();

        intake.setAllPower(1);
        Actions.runBlocking(
                new SequentialAction(
                        trajIntakeHumanPlayer.build()
                )
        );
        intake.setAllPower(0);

        Actions.runBlocking(
                new SequentialAction(
                        trajHumanPlayer.build()
                )
        );
        Launch();

        Turret.tracking = false;
        turret.setPosition(0);
        intake.setAllPower(0);
        Actions.runBlocking(
                new SequentialAction(
                        trajLeave.build()
                )
        );

    }
    // Define all functions here (if you call subsystems movements from here it wont be parallel)

    public void Launch() {
        shooterTargetSpeed = shooter.calcVelocity(
                Math.sqrt(
                        (turret.distanceToBasket().x * turret.distanceToBasket().x) + (turret.distanceToBasket().y * turret.distanceToBasket().y)
                )
        );
        shooter.setVelocity(shooterTargetSpeed);
        intake.setAllPower(0);
        do {
            gate.setPosition(Gate.OPEN);
        }
        while (shooter.getVelocity() < shooterTargetSpeed - Mortar.THRESH || shooter.getVelocity()>shooterTargetSpeed);
        intake.setAllPower(1);
        sleep(KICKER_WAIT_TIME);
        //intake.setIntakePower(0);
        //kicker.setPosition(Kicker.UP);
        //intake.setIntakePower(0);
        //sleep(500);
        //kicker.setPosition(Kicker.DOWN);

        gate.setPosition(Gate.CLOSE);
        //intake.setIntakePower(1);
    }

    public void updateAll(Turret turret, Mortar shooter, Kicker kicker, Intake intake, Gate gate, IntakeWrapper intakeWr){
        while (opModeInInit() || opModeIsActive())
        {
            shooter.update();
            kicker.update();
            turret.update();
            intake.update();
            gate.update();
            intakeWr.update();

            telemetry.addData("pose x", turret.getPose().position.x);
            telemetry.addData("pose y", turret.getPose().position.y);
            telemetry.addData("Heading", turret.getPose().heading.toDouble());
            telemetry.addData("Gate Position", gate.getPosition());
            telemetry.update();
        }
    }
}
