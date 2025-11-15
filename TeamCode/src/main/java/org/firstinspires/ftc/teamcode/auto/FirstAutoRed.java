
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

    private MecanumDrive drive;

    private int ballCount = 3, shooterTargetSpeed;

    ElapsedTime time1 = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        // init all subsystems (switch to using wrappers if you want parallel movement)
        // define startpose, in, in, rad
        Pose2d startPose = new Pose2d(-57.78, 45.6439, Math.toRadians(128.188));

        util = new Util();
        kicker = new Kicker(hardwareMap, util.deviceConf);
        shooter = new Mortar(hardwareMap, util.deviceConf);
        intake = new Intake(hardwareMap, util.deviceConf);
        turret = new Turret(hardwareMap, util.deviceConf, startPose);
        drive = new MecanumDrive(hardwareMap, startPose);

        turret.setBasketPos(Turret.redBasket);
        kicker.setPosition(Kicker.DOWN);

        TrajectoryActionBuilder trajPreload = drive.actionBuilder(startPose)
                .strafeToConstantHeading(new Vector2d(-41.1914631184, 13.6936191855));

        TrajectoryActionBuilder trajLeave = drive.actionBuilder(new Pose2d(new Vector2d(-41.1914631184, 13.6936191855), Math.toRadians(128.188)))//TODO: find ending pose
                .strafeToSplineHeading(new Vector2d(-13, 20), Math.toRadians(90))
                .strafeToConstantHeading(new Vector2d(-13, 50));

        Thread update = new Thread( ()-> updateAll(turret, shooter, kicker, intake));

        turret.tracking = false;
        update.start();
        // TODO: move everything to start position (after init, before program start)

        waitForStart();


        Actions.runBlocking(
                new SequentialAction(
                        trajPreload.build()
                        //turret.launch()
                        //trajLeave.build()
                )
        );

        turret.tracking = true;
        shooterTargetSpeed = shooter.calcVelocity(
                Math.sqrt(
                        (turret.distanceToBasket().x * turret.distanceToBasket().x) + (turret.distanceToBasket().y * turret.distanceToBasket().y)
                )
        );
        shooter.setVelocity(shooterTargetSpeed);

        sleep(1500);
        intake.setAllPower(1);

        for(int i = 3; i >= 1; i--) {
            time1.reset();
            shooter.setVelocity(shooterTargetSpeed);
            while ((Math.abs(shooter.getVelocity() - shooterTargetSpeed) > Mortar.THRESH) && time1.milliseconds() < 5000) {}; // wait until shooter velocity is with THRESH of target or shooter has been spinning for over 5s
            switch(i) {
                case 1:
                case 2: intake.setAllPower(1); break;
                case 3: intake.setIntakePower(1); break;
            }
            
            kicker.setPosition(kicker.UP);
            sleep(500);
            kicker.setPosition(kicker.DOWN);
            sleep(500);
        }

        turret.setPosition(0);
        shooter.setPower(0);
        intake.setAllPower(0);


    }
    // Define all functions here (if you call subsystems movements from here it wont be parallel)


    public void updateAll(Turret turret, Mortar shooter, Kicker kicker, Intake intake){
        while (opModeInInit() || opModeIsActive())
        {
            turret.update();
            shooter.update();
            kicker.update();
            intake.update();
            telemetry.update();
        }
    }
    public void sleep(int t) {
        try {
            Thread.sleep(t); // Wait for 1 millisecond
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            // Optionally, log or handle the interruption
        }
    }
}
