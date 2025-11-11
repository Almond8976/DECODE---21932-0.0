package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Mortar;
import org.firstinspires.ftc.teamcode.subsystems.Turret;
import org.firstinspires.ftc.teamcode.subsystems.Util;

@Config
@TeleOp(name = "TestShooter")
public class TestShooter extends LinearOpMode {
    public static double vel = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        Util util = new Util();
        Mortar shooter = new Mortar(hardwareMap, util.deviceConf);
        Kicker kicker = new Kicker(hardwareMap, util.deviceConf);
        Turret turret = new Turret(hardwareMap, util.deviceConf, new Pose2d(0,0,0));
        turret.tracking = false;

        Pose2d pose;
        waitForStart();

        while(opModeIsActive()) {
            pose = turret.getPose();
            if (gamepad1.a) {
                vel = 0;
            }
            if (gamepad1.right_bumper) {
                vel += 100;
            }
            if (gamepad1.left_bumper) {
                vel -= 100;
            }

            if (gamepad2.x) {
                kicker.setPosition(Kicker.DOWN);
            }
            if (gamepad2.y) {
                kicker.setPosition(Kicker.UP);
            }

            if (gamepad1.b) {
                turret.tracking = !turret.tracking;
            }

            if (gamepad1.dpad_up) {
                vel = shooter.calcVelocity(Math.sqrt((pose.position.x * pose.position.x) + (pose.position.y * pose.position.y)));
            }
            
            shooter.setVelocity(vel);
            shooter.update();
            kicker.update();
            turret.update();
            telemetry.addData("vel", shooter.getVelocity());
            telemetry.addData("target vel", shooter.getTargetVelocity());
            telemetry.update();

        }
    }
}
