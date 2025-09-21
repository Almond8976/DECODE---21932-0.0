package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.parts.Flywheel;

@TeleOp
@Config
public class TestShooter extends LinearOpMode {
    public static double spinRate = 0.01;

    Flywheel flywheel = new Flywheel(hardwareMap);
    @Override
    public void runOpMode() throws InterruptedException {
        boolean startSpin = false;

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            //flywheel.flyMotor.setPower(gamepad1.left_stick_y);



            if(gamepad1.a) {
                startSpin = true;
            }
            if(gamepad1.b) {
                startSpin = false;
            }
            if(gamepad1.dpad_down) {
                flywheel.flyMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            }
            if(gamepad1.dpad_up) {
                flywheel.flyMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            }
            if(startSpin) {
                flywheel.flyMotor.setPower(0.1);
            }
        }
    }
}





