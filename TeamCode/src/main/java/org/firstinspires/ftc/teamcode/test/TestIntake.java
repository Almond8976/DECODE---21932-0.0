package org.firstinspires.ftc.teamcode.test;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.parts.Intake;
import org.firstinspires.ftc.teamcode.parts.Kicker;
import org.firstinspires.ftc.teamcode.parts.Mortar;

@Config
@TeleOp(name = "TestIntake")
public class TestIntake extends LinearOpMode {
    public double intakeSpeed = 1.0;
    public double reverseSpeed = -0.7;
    public double shootSpeed = 0.7;
    @Override
    public void runOpMode() throws InterruptedException {
        Intake intake = new Intake(hardwareMap);
        Mortar flywheel = new Mortar(hardwareMap);
        Kicker kicker = new Kicker(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
            if(gamepad1.bWasPressed()) {
                intake.setIntakeSpeed(intakeSpeed);
            }
            if(gamepad1.aWasPressed()) {
                intake.setIntakeSpeed(0);
            }

            if(gamepad1.right_bumper) {
                intake.setIntake2Speed(1);
            }
            if(gamepad1.left_bumper) {
                intake.setIntake2Speed(0);
            }

            if(gamepad1.xWasPressed()) {
                flywheel.setFlyMotorSpeed(shootSpeed);
            }
            if(gamepad1.dpad_up) {
                kicker.setKickerPositon(1);
            }
            if(gamepad1.dpad_down) {
                kicker.setKickerPositon(0.75);
            }
        }
    }
}
