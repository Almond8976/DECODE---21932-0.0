package org.firstinspires.ftc.teamcode.auto;

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

@Autonomous(name = "FirstAutoFar")
public class FirstAutoFar extends LinearOpMode {


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
        util = new Util();
        kicker = new Kicker(hardwareMap, util.deviceConf);
        shooter = new Mortar(hardwareMap, util.deviceConf);
        turret = new Turret(hardwareMap, util.deviceConf, new Pose2d(-57.78, -45.6439, Math.toRadians(-128.188)));
        intake = new Intake(hardwareMap, util.deviceConf);



        // define startpose, in, in, rad
        Pose2d startPose = new Pose2d(-57.78, -45.6439, Math.toRadians(-128.188));
        drive = new MecanumDrive(hardwareMap, startPose);
        turret.setBasketPos(Turret.blueBasket);

        kicker.setPosition(Kicker.DOWN);


        Thread update = new Thread( ()-> updateAll(turret, shooter, kicker, intake));

        // TODO: move everything to start position (after init, before program start)



        waitForStart();
        update.start();

        turret.tracking = true;
        shooterTargetSpeed = shooter.calcVelocity(
                Math.sqrt(
                        (turret.distanceToBasket().x * turret.distanceToBasket().x) + (turret.distanceToBasket().y * turret.distanceToBasket().y)
                )
        );
        shooter.setVelocity(shooterTargetSpeed);

        sleep(1500);
        intake.setIntakePower(1);

        for(int i = 3; i >= 1; i--) {
            time1.reset();
            shooter.setVelocity(shooterTargetSpeed);
            while ((Math.abs(shooter.getVelocity() - shooterTargetSpeed) > Mortar.THRESH) && time1.milliseconds() < 5000) {}; // wait until shooter velocity is with THRESH of target or shooter has been spinning for over 5s
            switch(i) {
                case 1:
                case 2: intake.setIntakePower(1); break;
                case 3: intake.setIntakePower(1); break;
            }

            kicker.setPosition(kicker.UP);
            sleep(500);
            kicker.setPosition(kicker.DOWN);
            sleep(500);
        }
        turret.tracking = false;

        turret.setPosition(0);
        shooter.setPower(0);
        intake.setIntakePower(0);

    }
    // Define all functions here (if you call subsystems movements from here it wont be parallel)


    public void updateAll(Turret turret, Mortar shooter, Kicker kicker, Intake intake){
        while (opModeInInit() || opModeIsActive())
        {

            telemetry.update();
        }
    }
}
