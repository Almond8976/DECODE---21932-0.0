package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Hood;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
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
        Intake intake = new Intake(hardwareMap, util.deviceConf);
        Hood hood = new Hood(hardwareMap, util.deviceConf, new Pose2d(0,0,0));
        Turret.tracking = false;
        turret.setBasketPos(Turret.redBasket);

        Pose2d pose;
        waitForStart();

        while(opModeIsActive()) {
            pose = turret.getPose();
            if (gamepad1.dpad_down) {
                vel = 0;
            }
            if (gamepad1.rightBumperWasPressed()) {
                vel += 25;
            }
            if (gamepad1.leftBumperWasPressed()) {
                vel -= 25;
            }

            if (gamepad2.x) {
                kicker.setPosition(Kicker.DOWN);
            }
            if (gamepad2.y) {
                kicker.setPosition(Kicker.UP);
            }

            if(gamepad1.a) {
                intake.setAllPower(1);
            }
            if(gamepad1.b) {
                intake.setAllPower(0);
            }
            if(gamepad1.x) {
                intake.setAllPower(1);
            }
            if(gamepad1.y) {
                intake.setAllPower(0);
            }

            if (gamepad2.bWasPressed()) {
                turret.tracking = !turret.tracking;
            }

            if (gamepad1.dpad_up) {
                vel = shooter.calcVelocity(Math.sqrt((pose.position.x * pose.position.x) + (pose.position.y * pose.position.y)));
            }

            if(gamepad2.rightBumperWasPressed()) {
                hood.setHoodPosition(Hood.CLOSE);
            }
            if(gamepad2.leftBumperWasPressed()) {
                hood.setHoodPosition(Hood.FAR);
            }
            if(gamepad2.dpadDownWasPressed() && Hood.CLOSE > .35) {
                Hood.CLOSE-=.02;
            }
            if(gamepad2.dpadUpWasPressed() && Hood.CLOSE < .95) {
                Hood.CLOSE+=.02;
            }
            if(gamepad2.dpadRightWasPressed() && Hood.FAR < .95) {
                Hood.FAR+=.02;
            }
            if(gamepad2.dpadLeftWasPressed() && Hood.FAR > .35) {
                Hood.FAR-=.02;
            }
            
            shooter.setVelocity(vel);
            shooter.update();
            kicker.update();
            turret.update();
            intake.update();
            telemetry.addData("vel", shooter.getVelocity());
            telemetry.addData("target vel", shooter.getTargetVelocity());
            telemetry.update();

        }
    }
}
