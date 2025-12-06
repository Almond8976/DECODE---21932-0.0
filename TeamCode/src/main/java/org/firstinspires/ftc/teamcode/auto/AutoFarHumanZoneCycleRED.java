
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
@Autonomous(name = "AutoFarHumanZoneCycleRED")
public class AutoFarHumanZoneCycleRED extends LinearOpMode{

    Util util;
    Kicker kicker;
    Mortar shooter;
    Turret turret;
    IntakeWrapper intakeWr;
    Intake intake;
    Gate gate;

    private MecanumDrive drive;

    public static int KICKER_WAIT_TIME = 500;

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
        turret = new Turret(hardwareMap, util.deviceConf, new Pose2d(new Vector2d(65.29, 8.29), Math.toRadians(90)));
        intakeWr = new IntakeWrapper(hardwareMap, util.deviceConf);
        intake = new Intake(hardwareMap, util.deviceConf);
        gate = new Gate(hardwareMap, util.deviceConf);



        // define startpose, in, in, rad
        TrajectoryActionBuilder trajSetOne = drive.actionBuilder(new Pose2d(new Vector2d(65.29, 8.29), Math.toRadians(90)))
                .strafeToConstantHeading(new Vector2d(36, 29))
                .afterTime(0, intakeWr.startIntake())
                .strafeToConstantHeading(new Vector2d(36, 60))
                .afterTime(1, intakeWr.stopIntake())
                .strafeToConstantHeading(new Vector2d(56, 13.5));

        TrajectoryActionBuilder trajHumanCycle = drive.actionBuilder(new Pose2d(new Vector2d(56, 13.5), Math.toRadians(90)))
                .strafeToConstantHeading(new Vector2d(56, 60))
                .strafeToConstantHeading(new Vector2d(56, 13.5));

        TrajectoryActionBuilder trajLeave = drive.actionBuilder(new Pose2d(new Vector2d(56, 13.5), Math.toRadians(90)))
                .strafeTo(new Vector2d(56, 35));


        Thread update = new Thread( ()-> updateAll(turret, shooter, kicker, intake, gate, intakeWr));

        // TODO: move everything to start position (after init, before program start)



        waitForStart();
        update.start();
        shooter.setVelocity(shooter.calcVelocity(73.5391));
        Turret.tracking = true;

        Launch();
        Actions.runBlocking(
                new SequentialAction(
                        trajSetOne.build()
                )
        );

        for(int i = 0; i < 3; i++) {
            Actions.runBlocking(
                    new SequentialAction(
                            trajHumanCycle.build()
                    )
            );
            Launch();
        }

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
        //target = turret.getTargetPosition();
        shooter.setVelocity(shooterTargetSpeed);

        shooter.setVelocity(shooterTargetSpeed);
        do {
            gate.setPosition(Gate.OPEN);
        }
        while (shooter.getVelocity() < shooterTargetSpeed - Mortar.THRESH);
        intake.setIntakePower(1);
        sleep(KICKER_WAIT_TIME);
        kicker.setPosition(Kicker.UP);
        sleep(500);
        kicker.setPosition(Kicker.DOWN);
        gate.setPosition(Gate.CLOSE);

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

            telemetry.addData("Heading", turret.getPose().heading.toDouble());
            telemetry.addData("Gate Position", gate.getPosition());
            telemetry.update();
        }
    }
}
