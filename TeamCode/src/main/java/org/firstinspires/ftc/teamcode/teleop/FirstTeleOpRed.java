package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Gate;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Mortar;
//import org.firstinspires.ftc.teamcode.subsystems.Sparky;
import org.firstinspires.ftc.teamcode.subsystems.Turret;
import org.firstinspires.ftc.teamcode.subsystems.Util;

@Config
@TeleOp(name = "FirstTeleOpRed")
public class FirstTeleOpRed extends LinearOpMode {

    public static int sensorThresh = 20;

    public static int brightness = 50;

    public static int maxTurretChange = 15;
    public static Pose2d resetPose = new Pose2d(62.4652,-64.94,Math.PI);


    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        Util util = new Util();

        Drivetrain drive = new Drivetrain(hardwareMap, util.deviceConf);

        Intake intake = new Intake(hardwareMap, util.deviceConf);
        //Turret turret = new Turret(hardwareMap, util.deviceConf, new Pose2d(-41.1914631184, 13.6936191855,2.26));
        Turret turret = new Turret(hardwareMap, util.deviceConf, new Pose2d(0, 0,0));
        Mortar shooter = new Mortar(hardwareMap, util.deviceConf);
        Kicker kicker = new Kicker(hardwareMap, util.deviceConf);
        //Sparky sensor = new Sparky(hardwareMap);
        Gate gate = new Gate(hardwareMap, util.deviceConf);

        ElapsedTime time1 = new ElapsedTime();
        ElapsedTime time2 = new ElapsedTime();
        Pose2d pose;

        turret.setBasketPos(turret.redBasket);

        //sensor.setLEDBrightness(brightness);

        waitForStart();

        boolean shooting = false, turretOverride = false, intaking = false, metDistanceSensorThresh = false, keepShooterRunning = true;

        int shooterTargetSpeed = 0;

        turret.tracking = false;



        while(opModeIsActive()) {
            // SHOOTER
            pose = turret.getPose();
            shooterTargetSpeed = shooter.calcVelocity(Math.sqrt(
                    (turret.distanceToBasket().x * turret.distanceToBasket().x) + (turret.distanceToBasket().y * turret.distanceToBasket().y)));

            if (gamepad1.right_bumper || shooting) {
                shooting = true;
                if(!turretOverride) {
                    turret.tracking = true;
                }
                shooter.setVelocity(shooterTargetSpeed);
                gate.setPosition(Gate.OPEN);
                if(shooter.getVelocity() > shooterTargetSpeed - Mortar.THRESH) {
                    intake.setIntakePower(1);
                }
            }
            if (gamepad1.left_bumper) {
                //intake.setIntakePower(0);
                turret.tracking = false;
                shooting = false;
                gate.setPosition(Gate.CLOSE);
            }

            if(!shooting && keepShooterRunning) {
                shooter.setVelocity(Mortar.WAIT);
            }

            if(!shooting && !keepShooterRunning) {
                shooter.setVelocity(0);
            }

            if(gamepad2.aWasPressed()) {
                keepShooterRunning = !keepShooterRunning;
            }
            // KICKER
            if (gamepad1.aWasPressed()) {
                kicker.setPosition(Kicker.UP);
                time1.reset();
            }
            if(time1.milliseconds() > 500 && kicker.getPosition() > Kicker.DOWN) {
                kicker.setPosition(Kicker.DOWN);
            }

            if(kicker.getPosition() > Kicker.DOWN) {
                intake.setIntakePower(0);
            }
            // INTAKE
            if(gamepad2.right_bumper) {
                intaking = true;
                intake.setIntakePower(1);
                gate.setPosition(Gate.CLOSE);
            }

            if(gamepad2.left_bumper) {
                intaking = false;
                intake.setIntakePower(0);
            }
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



            // update all systems
            drive.update(gamepad1.left_stick_x, -gamepad1.left_stick_y, -gamepad1.right_stick_x);
            intake.update();
            turret.update();
            shooter.update();
            kicker.update();
            gate.update();


            telemetry.addData("pose x", pose.position.x);
            telemetry.addData("pose y", pose.position.y);
            telemetry.addData("pose heading", pose.heading.toDouble());
            telemetry.addData("Turret Heading relative", turret.getTurretHeadingRelative());
            telemetry.addData("Turret target", turret.getTurretHeading());
            telemetry.addData("Shooter vel", shooter.getVelocity());
            telemetry.addData("Shooter target vel", shooter.getTargetVelocity());
            //telemetry.addData("DISTANCE:", sensor.getDistance());
            telemetry.addLine();
            telemetry.addData("Turret Manual Override", turretOverride);
            telemetry.update();

        }
    }
}
