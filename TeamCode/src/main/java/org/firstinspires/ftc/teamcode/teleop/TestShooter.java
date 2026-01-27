package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Gate;
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
        Gate gate = new Gate(hardwareMap, util.deviceConf);
        Intake intake = new Intake(hardwareMap, util.deviceConf);
        Hood hood = new Hood(hardwareMap, util.deviceConf, new Pose2d(0,0,0));
        Turret.tracking = false;
        turret.setBasketPos(Turret.redBasket);
        boolean color=true;
        ElapsedTime time1 = new ElapsedTime();

        hood.setHoodPosition(0.70);
        Pose2d pose;
        waitForStart();


        while(opModeIsActive()) {
            pose = turret.getPose();
            if (gamepad1.xWasPressed()) {
                vel = 0;
            }
            if (gamepad1.dpadUpWasPressed()) {
                vel += 20;
            }
            if (gamepad1.dpadDownWasPressed()) {
                vel -= 20;
            }

            if (gamepad1.yWasPressed()) {
                gate.setPosition(Gate.OPEN);
            }

            if (gamepad1.bWasPressed()) {
                gate.setPosition(Gate.CLOSE);
            }


            if (gamepad1.aWasPressed()) {
                kicker.setPosition(Kicker.UP);
                time1.reset();
            }
            if(time1.milliseconds() > 500 && kicker.getPosition() > Kicker.DOWN) {
                kicker.setPosition(Kicker.DOWN);
            }
            if(kicker.getPosition() > Kicker.DOWN) {
                intake.setAllPower(0);
            }


            if(gamepad1.rightBumperWasPressed()) {
                intake.setAllPower(1);
            }

            if(gamepad1.leftBumperWasPressed()) {
                intake.setAllPower(0);
            }



            if (gamepad2.leftBumperWasPressed()) {
                hood.hoodIncrement(0.05, hood.getHoodPosition());
            }
            if (gamepad2.rightBumperWasPressed()) {
                hood.hoodIncrement(-0.05, hood.getHoodPosition());
            }

            if (gamepad2.aWasPressed()) {
                if (color){
                    turret.setBasketPos(Turret.blueBasket);
                    color = false;

                }
                else {
                    turret.setBasketPos(Turret.redBasket);
                    color = true;
                }
            }

            if (gamepad2.bWasPressed()) {
                turret.tracking = !turret.tracking;
            }



            /*
            if (gamepad1.dpad_up) {
                vel = shooter.calcVelocity(Math.sqrt((pose.position.x * pose.position.x) + (pose.position.y * pose.position.y)));
            }
            */

            /*
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
            */

            
            shooter.setVelocity(vel);
            shooter.update();
            kicker.update();
            turret.update();
            intake.update();
            gate.update();
            hood.update();
            telemetry.addData("vel", shooter.getVelocity());
            telemetry.addData("target vel", shooter.getTargetVelocity());
            telemetry.addData("hood servo", hood.getHoodPosition());
            telemetry.addData("pose x", pose.position.x);
            telemetry.addData("pose y", pose.position.y);
            telemetry.addData("pose heading", Math.toDegrees((pose.heading.toDouble())));
            telemetry.update();


        }
    }
}
