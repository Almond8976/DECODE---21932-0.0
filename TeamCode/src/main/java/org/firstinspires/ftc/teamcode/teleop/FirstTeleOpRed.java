package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Mortar;
import org.firstinspires.ftc.teamcode.subsystems.Sparky;
import org.firstinspires.ftc.teamcode.subsystems.Turret;
import org.firstinspires.ftc.teamcode.subsystems.Util;

@Config
@TeleOp(name = "FirstTeleOpRed")
public class FirstTeleOpRed extends LinearOpMode {

    public static int sensorThresh = 20;

    public static int brightness = 50;

    public static int maxTurretChange = 20;
    public static Pose2d resetPose = new Pose2d(62.4652,-64.94,Math.PI);

    public static int shooterTimeThresh =40;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        Util util = new Util();

        Drivetrain drive = new Drivetrain(hardwareMap, util.deviceConf);

        Intake intake = new Intake(hardwareMap, util.deviceConf);
        Turret turret = new Turret(hardwareMap, util.deviceConf, new Pose2d(-41.1914631184, 13.6936191855,2.26));
        Mortar shooter = new Mortar(hardwareMap, util.deviceConf);
        Kicker kicker = new Kicker(hardwareMap, util.deviceConf);
        Sparky sensor = new Sparky(hardwareMap);

        ElapsedTime time1 = new ElapsedTime();
        ElapsedTime time2 = new ElapsedTime();
        Pose2d pose;

        turret.setBasketPos(turret.redBasket);

        sensor.setLEDBrightness(brightness);

        waitForStart();

        boolean shooting = false, turretOverride = false, intaking = false, metDistanceSensorThresh = false;

        int prevShooterVel = 0;

        int ballCount = 0;

        int shooterTargetSpeed = 0;

        turret.tracking =false;



        while(opModeIsActive()) {
            // SHOOTER
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
                    case 0: intake.setAllPower(0); shooting = false; shooter.setVelocity(Mortar.OFF); Turret.tracking = false; break;
                    case 1:
                    case 2: intake.setAllPower(1); break;
                    case 3: intake.setIntakePower(1); break;
                }
            }
            if(shooting && shooter.getVelocity()-prevShooterVel <-Mortar.THRESH && prevShooterVel>shooterTargetSpeed-Mortar.THRESH) {
                ballCount--;
                intake.setAllPower(0);
            }
            prevShooterVel = (int)shooter.getVelocity();

//            if(time2.milliseconds()>shooterTimeThresh) {
//                if(shooting && shooter.getVelocity()-prevShooterVel <-Mortar.THRESH) {
//                    ballCount--;
//                    intake.setAllPower(0);
//                }
//                prevShooterVel = (int)shooter.getVelocity();
//                time2.reset();
//            }

            // KICKER
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
            // INTAKE
            if(gamepad2.right_bumper) {
                intaking = true;
            }

            if(gamepad2.left_bumper) {
                intaking = false;
                intake.setAllPower(0);
            }

            if(intaking) {
                switch (ballCount) {
                    case 0:
                    case 1:
                    case 2: intake.setIntakePower(1); break;
                    case 3: intake.setAllPower(0); intaking = false; break;
                }
            }

            if(gamepad2.a) {
                shooter.setVelocity(-700);
                intake.setAllPower(-.5);
            }
            if(!gamepad2.a && !shooting && !intaking) {
                shooter.setVelocity(0);
                intake.setAllPower(0);
            }
//            if(gamepad2.right_bumper) {
//                intake.setIntakePower(1.0);
//            }
//
//            if(gamepad2.left_bumper) {
//                intake.setIntakePower(0);
//            }

//            if(gamepad2.a) {
//                intake.setRollerPower(1);
//            }
//
//            if(gamepad2.b) {
//                intake.setRollerPower(0);
//            }
            // SENSOR
//            if(intaking && metDistanceSensorThresh && sensor.getDistance() < sensorThresh) {
//                ballCount++;
//
//            }
//            metDistanceSensorThresh = sensor.getDistance() > sensorThresh;


            // DRIVE
            if(gamepad1.left_trigger>.1) {
                drive.parkMode();
            }

            if(gamepad1.left_trigger<=.1) {
                drive.speedMode();
            }
            // TURRET
            if(gamepad2.xWasPressed()) {
                turretOverride = !turretOverride;
                if(turretOverride) {
                    turret.tracking = false;
                    turret.setPosition(0);
                }
            }

            if(!shooting && !turretOverride) {
                turret.setPosition(0);
            }

            if (turretOverride) {
                turret.setPosition((int) (turret.getTargetPosition() + (maxTurretChange * -gamepad2.right_stick_x)));
            }

            if (gamepad1.y && gamepad1.dpad_left) {
                turret.resetEncoder();
                turret.resetRobotPose(resetPose);
            }
            // BALL COUNT
            if(gamepad2.dpadUpWasPressed()) {
                ballCount++;
            }
            if(gamepad2.dpadDownWasPressed()) {
                ballCount--;
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
            telemetry.addData("DISTANCE:", sensor.getDistance());
            telemetry.addLine();
            telemetry.addData("Ball Count", ballCount);
            telemetry.addData("Turret Manual Override", turretOverride);
            telemetry.update();

        }
    }
}
