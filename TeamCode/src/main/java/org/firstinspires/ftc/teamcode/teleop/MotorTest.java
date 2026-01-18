package org.firstinspires.ftc.teamcode.teleop;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Mortar;
import org.firstinspires.ftc.teamcode.subsystems.Turret;
import org.firstinspires.ftc.teamcode.subsystems.Util;

@Config
@TeleOp
public class MotorTest extends LinearOpMode {

    private boolean intakeMotor = false;
    private boolean rollerMotor = false;
    private boolean flywheelMotor = false;
    private boolean flywheelMotor2 = false;
    private boolean frontLeftMotor = false;
    private boolean backLeftMotor = false;
    private boolean frontRightMotor = false;
    private boolean backRightMotor = false;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        Util util = new Util();

        Drivetrain drive = new Drivetrain(hardwareMap, util.deviceConf);

        Intake intake = new Intake(hardwareMap, util.deviceConf);
        Turret turret = new Turret(hardwareMap, util.deviceConf, new Pose2d(0, -50, Math.PI));
        Mortar shooter = new Mortar(hardwareMap, util.deviceConf);

        if(gamepad1.leftBumperWasPressed()) {
            intakeMotor = !intakeMotor;
        }
        if(gamepad1.rightBumperWasPressed()) {
            rollerMotor = !rollerMotor;
        }
        if(gamepad1.dpadUpWasPressed()) {
            flywheelMotor = !flywheelMotor;
        }
        if(gamepad1.dpadDownWasPressed()) {
            flywheelMotor2 = !flywheelMotor2;
        }
        if(gamepad1.yWasPressed()) {
            frontRightMotor = !frontRightMotor;
        }
        if(gamepad1.xWasPressed()) {
            frontLeftMotor = !frontLeftMotor;
        }
        if(gamepad1.bWasPressed()) {
            backRightMotor = !backRightMotor;
        }
        if(gamepad1.aWasPressed()) {
            backLeftMotor = !backLeftMotor;
        }
        

        if(intakeMotor) {
            intake.setIntakePower(1);
        }
        else {
            intake.setIntakePower(0);
        }
        if(rollerMotor) {
            intake.setRollerPower(1);
        }
        else {
            intake.setRollerPower(0);
        }
        if(flywheelMotor) {
            shooter.setFlyMotorPower(1);
        }
        else {
            shooter.setFlyMotorPower(0);
        }
        if(flywheelMotor2) {
            shooter.setFlyMotor2Power(1);
        }
        else {
            shooter.setFlyMotor2Power(0);
        }

    }
}
