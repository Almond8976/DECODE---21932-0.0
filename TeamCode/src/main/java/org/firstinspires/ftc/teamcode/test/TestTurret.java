package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Turret;

@TeleOp
public class TestTurret extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Turret turret = new Turret(hardwareMap);
        turret.init();

        waitForStart();
        while (opModeIsActive()) {
            turret.update();
        }
    }
}
