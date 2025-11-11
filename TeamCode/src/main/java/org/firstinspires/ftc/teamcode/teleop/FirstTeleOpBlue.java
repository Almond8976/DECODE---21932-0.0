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
@TeleOp(name = "FirstTeleOpBlue")
public class FirstTeleOpBlue extends LinearOpMode {

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

        turret.setBasketPos(turret.blueBasket);
        waitForStart();

        boolean shooting = false, metShooterThresh = false;

        int ballCount = 0;

        while(opModeIsActive()) {

            pose = turret.getPose();

            if (gamepad1.right_bumper) {
                shooter.setVelocity(shooter.calcVelocity(Math.sqrt(
                        (turret.distanceToBasket().x * turret.distanceToBasket().x) + (turret.distanceToBasket().y * turret.distanceToBasket().y)
                )));
                shooting = true;
            }
            if (gamepad1.dpad_left) {
                shooter.setVelocity(0);
                intake.setIntakePower(0);
                shooting = false;
            }
            if(shooting && shooter.getVelocity() > Mortar.THRESH) {
                switch(ballCount) {
                    case 0: intake.setAllPower(0); shooting = false; shooter.setPower(Mortar.OFF); break;
                    case 1:
                    case 2:
                    case 3: intake.setAllPower(1); break;
                }
            }

            if(shooting && shooter.getVelocity() <= Mortar.THRESH && metShooterThresh) {
                ballCount--;
            }
            metShooterThresh = shooter.getVelocity() > Mortar.THRESH;

            if (gamepad2.x) {
                kicker.setPosition(Kicker.DOWN);
            }
            if (gamepad2.y) {
                kicker.setPosition(Kicker.UP);
            }

            if(gamepad2.right_bumper) {
                intake.setIntakePower(1.0);
            }

            if(gamepad2.left_bumper) {
                intake.setIntakePower(0);
                //shooter.setVelocity(0);
            }

            if(gamepad2.a) {
                intake.setRollerPower(1);
            }

            if(gamepad2.b) {
                intake.setRollerPower(1);
            }

            if(gamepad1.dpadUpWasPressed()) {
                ballCount++;
            }
            if(gamepad1.dpadDownWasPressed()) {
                ballCount--;
            }

            if(gamepad1.left_bumper) {
                drive.parkMode();
            }

            if(!gamepad1.left_bumper) {
                drive.speedMode();
            }

            if(gamepad2.dpadUpWasPressed()) {
                turret.tracking = !turret.tracking;
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
            telemetry.addData("ball count", ballCount);
            telemetry.update();

        }
    }
}
