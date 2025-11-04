package org.firstinspires.ftc.teamcode.test;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.subsystems.Turret;

@Config
@TeleOp(name = "TestTurretTurn")
public class TestTurretTurn extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Turret turret = new Turret(hardwareMap);

        while (opModeIsActive()) {
            turret.turret.setPower(gamepad1.left_stick_x);

            telemetry.addData("Pos", turret.turret.getCurrentPosition());
            telemetry.addData("Dir", turret.turret.getDirection());
        }
    }
}
