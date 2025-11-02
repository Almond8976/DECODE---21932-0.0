package org.firstinspires.ftc.teamcode.test;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
@Config
@TeleOp(name = "TestTurretTurn")
public class TestTurretTurn extends LinearOpMode {
    public DcMotor turret;

    public void Turret(HardwareMap hardwareMap) {
        turret = hardwareMap.get(DcMotor.class, "intake");
        turret.setMode(com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setIntakeSpeed(double intakeSpeed) {
        turret.setPower(intakeSpeed);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        setIntakeSpeed(0.5);

        waitForStart();

        while (opModeIsActive()) {
            double x = gamepad1.left_stick_x;

            turret.setPower(x);
        }
    }
}
