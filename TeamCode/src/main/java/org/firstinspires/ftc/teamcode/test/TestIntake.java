package org.firstinspires.ftc.teamcode.test;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.parts.Intake;
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

        boolean intakeMode = false;

        waitForStart();
        while (opModeIsActive()) {
            if(gamepad1.bWasPressed()) {
                intake.setIntakeSpeed(intakeSpeed);
                flywheel.setFlyMotorSpeed(reverseSpeed);
                //intakeMode = true;
            }

            if(gamepad1.aWasPressed()) {
                intake.setIntakeSpeed(0);
                flywheel.setFlyMotorSpeed(0);
                //intakeMode = false;
            }

            if (gamepad1.xWasPressed()) {
                flywheel.setFlyMotorSpeed(shootSpeed);
            }
        }
    }
}
