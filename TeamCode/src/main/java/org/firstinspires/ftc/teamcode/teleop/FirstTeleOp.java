package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

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
        // TODO: dont use class for this, theres a better way but i dont remember
        Util util = new Util();

        Drivetrain drive = new Drivetrain(hardwareMap, util.deviceConf);

        Intake intake = new Intake(hardwareMap, util.deviceConf);
        Turret turret = new Turret(hardwareMap, util.deviceConf);
        Mortar shooter = new Mortar(hardwareMap, util.deviceConf);
        Kicker kicker = new Kicker(hardwareMap, util.deviceConf);

        Pose2d pose;

        waitForStart();

        while(opModeIsActive()) {


            if (gamepad1.right_bumper) {
                shooter.setPower(shooter.NORMAL);

            }
            if (gamepad1.left_bumper) {
                shooter.setPower(shooter.OFF);
                intake.setIntakePower(0);
            }

            if (gamepad2.x) {
                kicker.setPosition(kicker.DOWN);
            }
            if (gamepad2.y) {
                kicker.setPosition(kicker.UP);
            }

            if(gamepad2.right_bumper) {
                intake.setIntakePower(1.0);
            }

            if(gamepad2.left_bumper) {
                intake.setIntakePower(0);
                shooter.setPower(shooter.OFF);
            }

            // update all systems
            drive.update(-gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            intake.update();
            turret.update();
            shooter.update();
            kicker.update();

            pose = turret.getPose();

            telemetry.addData("pose x", pose.position.x);
            telemetry.addData("pose y", pose.position.y);
            telemetry.addData("pose heading", pose.heading);
            telemetry.addData("Turret target", turret.getTurretHeading());
            telemetry.update();

        }
    }
}
