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
@TeleOp(name = "FirstTeleOp")
public class FirstTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Util util = new Util();

        Drivetrain drive = new Drivetrain(hardwareMap, util.deviceConf);

        Intake intake = new Intake(hardwareMap, util.deviceConf);
        Turret turret = new Turret(hardwareMap, util.deviceConf);
        Mortar shooter = new Mortar(hardwareMap, util.deviceConf);
        Kicker kicker = new Kicker(hardwareMap, util.deviceConf);

        Pose2d pose;

        ElapsedTime time1 = new ElapsedTime();

        waitForStart();

        boolean shooting = false, metShooterThresh = false;

        int ballCount = 0;

        while(opModeIsActive()) {


            if (gamepad1.right_bumper) {
                shooter.setPower(Mortar.NORMAL);
                shooting = true;
            }
            if (gamepad1.dpad_left) {
                shooter.setPower(Mortar.OFF);
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
                shooter.setPower(Mortar.OFF);
            }

            if(gamepad1.dpad_up) {
                ballCount++;
            }
            if(gamepad1.dpad_down) {
                ballCount--;
            }

            if(gamepad1.left_bumper) {
                drive.parkMode();
            }

            if(!gamepad1.left_bumper) {
                drive.speedMode();
            }


            // update all systems
            drive.update(gamepad1.left_stick_x, -gamepad1.left_stick_y, -gamepad1.right_stick_x);
            intake.update();
            //turret.update();
            shooter.update();
            kicker.update();

            pose = turret.getPose();

            telemetry.addData("pose x", pose.position.x);
            telemetry.addData("pose y", pose.position.y);
            telemetry.addData("pose heading", pose.heading);
            telemetry.addData("Turret target", turret.getTurretHeading());
            telemetry.addData("Shooter speed", shooter.getVelocity());
            telemetry.update();

        }
    }
}
