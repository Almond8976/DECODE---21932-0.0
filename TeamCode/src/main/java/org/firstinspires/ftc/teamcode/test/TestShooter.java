package org.firstinspires.ftc.teamcode.test;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Mortar;

@TeleOp
@Config
public class TestShooter extends LinearOpMode {
    public static double spinRate = 1.0;
    public double intakeRate = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {
        Mortar flywheel = new Mortar(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        Kicker kicker = new Kicker(hardwareMap);
        boolean startSpin = false;

        waitForStart();

        while (opModeIsActive()) {

            if(gamepad1.aWasPressed()) {
                flywheel.setFlyMotorSpeed(spinRate);
                intake.setIntakeSpeed(intakeRate);
            }
            if(gamepad1.backWasPressed()) {
                flywheel.setFlyMotorSpeed(0.0);
            }
            if(gamepad1.xWasPressed())
            {
                kicker.setKickerPositon(0.5);
                kicker.setKickerPositon(0);
            }


        }
    }
}





