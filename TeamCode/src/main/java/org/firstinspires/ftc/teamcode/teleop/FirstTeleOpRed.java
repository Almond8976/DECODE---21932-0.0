package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Mortar;
import org.firstinspires.ftc.teamcode.subsystems.Turret;
import org.firstinspires.ftc.teamcode.subsystems.Util;

@Config
@TeleOp(name = "FirstTeleOpRed")
public class FirstTeleOpRed extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Util util = new Util();

        Drivetrain drive = new Drivetrain(hardwareMap, util.deviceConf);

        Intake intake = new Intake(hardwareMap, util.deviceConf);
        Turret turret = new Turret(hardwareMap, util.deviceConf, new Pose2d(0,0,0));
        Mortar shooter = new Mortar(hardwareMap, util.deviceConf);
        Kicker kicker = new Kicker(hardwareMap, util.deviceConf);

        ElapsedTime time1 = new ElapsedTime();
        Pose2d pose;

        turret.setBasketPos(turret.redBasket);
        waitForStart();

        boolean shooting = false, metShooterThresh = false, turretOverride = false;

        int ballCount = 0;
        int shooterTargetSpeed = 0;

        while(opModeIsActive()) {

            pose = turret.getPose();
            shooterTargetSpeed = shooter.calcVelocity(Math.sqrt(
                    (turret.distanceToBasket().x * turret.distanceToBasket().x) + (turret.distanceToBasket().y * turret.distanceToBasket().y)));

            if (gamepad1.right_bumper || shooting) {
                if(!turretOverride) {
                    turret.tracking = true;
                }
                shooter.setVelocity(shooterTargetSpeed);
                shooting = true;
            }
            if (gamepad1.left_bumper) {
                shooter.setVelocity(Mortar.OFF);
                intake.setAllPower(0);
                turret.tracking = false;
                shooting = false;
            }
            if(shooting && shooter.getVelocity() > shooterTargetSpeed - Mortar.THRESH) {
                switch(ballCount) {
                    case 0: intake.setAllPower(0); shooting = false; shooter.setVelocity(Mortar.OFF); turret.tracking = false; break;
                    case 1:
                    case 2:
                    case 3: intake.setAllPower(1); break;
                }
            }

            if(shooting && shooter.getVelocity() <= shooterTargetSpeed - Mortar.THRESH && metShooterThresh) {
                ballCount--;
                intake.setAllPower(0);
            }
            metShooterThresh = shooter.getVelocity() > shooterTargetSpeed - Mortar.THRESH;

            if (gamepad1.x) {
                kicker.setPosition(Kicker.DOWN);
            }
            if (gamepad1.y) {
                kicker.setPosition(Kicker.UP);
            }

            if(gamepad2.right_bumper) {
                intake.setIntakePower(1.0);
            }

            if(gamepad2.left_bumper) {
                intake.setIntakePower(0);
            }

            if(gamepad2.a) {
                intake.setRollerPower(1);
            }

            if(gamepad2.b) {
                intake.setRollerPower(0);
            }

            if(gamepad1.dpadUpWasPressed()) {
                ballCount++;
            }
            if(gamepad1.dpadDownWasPressed()) {
                ballCount--;
            }

            if(gamepad1.left_trigger>.1) {
                drive.parkMode();
            }

            if(gamepad1.left_trigger<=.1) {
                drive.speedMode();
            }

            if(gamepad2.dpadUpWasPressed()) {
                turretOverride = !turretOverride;
                if(turretOverride) {
                    turret.tracking = false;
                }
            }

            if(ballCount<0) {
                ballCount = 0;
            }

            if(ballCount>3) {
                ballCount = 3;
            }



            // update all systems
            drive.update(gamepad1.left_stick_x, -gamepad1.left_stick_y, -gamepad1.right_stick_x);
            intake.update();
            turret.update();
            shooter.update();
            kicker.update();

            telemetry.addData("pose x", pose.position.x);
            telemetry.addData("pose y", pose.position.y);
            telemetry.addData("pose heading", pose.heading.toDouble());
            telemetry.addData("Turret Heading relative", turret.getTurretHeadingRelative());
            telemetry.addData("Turret target", turret.getTurretHeading());
            telemetry.addData("Shooter vel", shooter.getVelocity());
            telemetry.addData("Shooter target vel", shooter.getTargetVelocity());
            telemetry.addData("Ball Count", ballCount);
            telemetry.addData("Turret Manual Override", turretOverride);
            telemetry.update();

        }
    }
}
