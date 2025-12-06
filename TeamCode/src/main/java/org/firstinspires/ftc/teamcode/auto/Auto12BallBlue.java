
package org.firstinspires.ftc.teamcode.auto;


import com.acmerobotics.dashboard.config.Config;
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
@Autonomous(name = "Auto12BallBlue")
public class Auto12BallBlue extends LinearOpMode{

    Util util;
    Kicker kicker;
    Mortar shooter;
    Turret turret;
    IntakeWrapper intakeWr;
    Intake intake;
    Gate gate;

    private MecanumDrive drive;

    public static int KICKER_WAIT_TIME = 700;

    private int shooterTargetSpeed;
    private double target;

    ElapsedTime time1 = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        // init all subsystems (switch to using wrappers if you want parallel movement)
        util = new Util();
        kicker = new Kicker(hardwareMap, util.deviceConf);
        shooter = new Mortar(hardwareMap, util.deviceConf);
        //turret = new Turret(hardwareMap, util.deviceConf, new Pose2d(-57.78, 45.6439, Math.toRadians(128.188)));
        turret = new Turret(hardwareMap, util.deviceConf, new Pose2d(-57.7, -45.9, Math.toRadians(-128.188)));
        intakeWr = new IntakeWrapper(hardwareMap, util.deviceConf);
        intake = new Intake(hardwareMap, util.deviceConf);
        gate = new Gate(hardwareMap, util.deviceConf);



        // define startpose, in, in, rad
        Pose2d startPose = new Pose2d(-57.7, -45.9, Math.toRadians(-128.188));
        drive = new MecanumDrive(hardwareMap, startPose);
        turret.setBasketPos(Turret.blueBasket);

        kicker.setPosition(Kicker.DOWN);
        TrajectoryActionBuilder trajPreload = drive.actionBuilder(startPose)
                .strafeToSplineHeading(new Vector2d(-12, -12), Math.toRadians(-90));


        TrajectoryActionBuilder trajPickupOne = drive.actionBuilder(new Pose2d(new Vector2d(-12, -12), Math.toRadians(-90)))
                .afterTime(0, intakeWr.startIntake())
                .strafeToConstantHeading(new Vector2d(-11, -53))
                .afterTime(1, intakeWr.stopIntake())
                /*.turnTo(Math.toRadians(0))
                .strafeToConstantHeading(new Vector2d(-2, 54))*/
                .strafeToSplineHeading(new Vector2d(-2, -54), Math.toRadians(-180))
                .strafeToConstantHeading(new Vector2d(-2, -60));

        TrajectoryActionBuilder trajShootOne = drive.actionBuilder(new Pose2d(new Vector2d(-2, -60), Math.toRadians(-180)))
                /*.strafeToConstantHeading(new Vector2d(-20, 20));*/
                .strafeToSplineHeading(new Vector2d(-20, -20), Math.toRadians(-90));

        TrajectoryActionBuilder trajSetTwo = drive.actionBuilder(new Pose2d(new Vector2d(-20, -20), Math.toRadians(-90)))
                .strafeToConstantHeading(new Vector2d(13, -29))
                .afterTime(0, intakeWr.startIntake())
                .strafeToConstantHeading(new Vector2d(13, -60))
                .afterTime(1, intakeWr.stopIntake())
                .strafeToConstantHeading(new Vector2d(-20, -20));

        TrajectoryActionBuilder trajSetThree = drive.actionBuilder(new Pose2d(new Vector2d(-20, -20), Math.toRadians(-90)))
                .strafeToConstantHeading(new Vector2d(36, -29))
                .afterTime(0, intakeWr.startIntake())
                .strafeToConstantHeading(new Vector2d(36, -60))
                .afterTime(1, intakeWr.stopIntake())
                .strafeToConstantHeading(new Vector2d(36, -22))
                .strafeToConstantHeading(new Vector2d(-28, -22));

        TrajectoryActionBuilder trajSetFour = drive.actionBuilder(new Pose2d(new Vector2d(-28, -22), Math.toRadians(-90)))
                .strafeToSplineHeading(new Vector2d(0, -52), Math.toRadians(-180));

        Thread update = new Thread( ()-> updateAll(turret, shooter, kicker, intake, gate, intakeWr));

        // TODO: move everything to start position (after init, before program start)



        waitForStart();
        update.start();
        shooter.setVelocity(shooter.calcVelocity(73.5391));
        Turret.tracking = true;

        Actions.runBlocking(
                new SequentialAction(
                        trajPreload.build()
                )
        );

        Launch();


        Actions.runBlocking(
                new SequentialAction(
                        trajPickupOne.build()
                )
        );

        sleep(400);

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

        Turret.tracking = false;
        turret.setPosition(0);
        Actions.runBlocking(
                new SequentialAction(
                        trajSetFour.build()
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
        //target = turret.getTargetPosition();
        shooter.setVelocity(shooterTargetSpeed);

        shooter.setVelocity(shooterTargetSpeed);
        do {
            gate.setPosition(Gate.OPEN);
        }
        while (shooter.getVelocity() < shooterTargetSpeed - Mortar.THRESH);
        intake.setIntakePower(1);
        sleep(KICKER_WAIT_TIME);
        intake.setIntakePower(0);
        kicker.setPosition(Kicker.UP);
        sleep(500);
        kicker.setPosition(Kicker.DOWN);
        intake.setIntakePower(1);
        sleep(KICKER_WAIT_TIME);
        kicker.setPosition(Kicker.UP);
        sleep(500);
        kicker.setPosition(Kicker.DOWN);

        //Turret.tracking = false;
        //turret.setPosition(0);


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
