package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Mortar;
import org.firstinspires.ftc.teamcode.subsystems.Sparky;
import org.firstinspires.ftc.teamcode.subsystems.Util;
@TeleOp(name = "SensorTest")
public class SensorTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Util util = new Util();
        Sparky color = new Sparky();
        Intake intake = new Intake(hardwareMap, util.deviceConf);
        Mortar shooter = new Mortar(hardwareMap, util.deviceConf);
        Drivetrain drive = new Drivetrain(hardwareMap, util.deviceConf);

        color.init(hardwareMap);

        boolean intaking = false;

        waitForStart();

        while (opModeIsActive()) {
            if(gamepad1.right_bumper) {
                intaking = true;
            }
            if(gamepad1.left_bumper) {
                intaking = false;
                intake.setIntakePower(0);
                intake.setRollerPower(0);
            }


            if(intaking) {
                switch (color.ballCount) {
                    case 0 :
                    case 1 : intake.setIntakePower(1); intake.setRollerPower(1); break;
                    case 2 : intake.setIntakePower(1); intake.setRollerPower(0); break;
                    case 3 : intake.setIntakePower(0); intake.setRollerPower(0); break;
                }
            }

            if(gamepad1.a) {
                shooter.setPower(1);
                color.ballCount = 0;
            }


            drive.update(-gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            intake.update();
            shooter.update();
            color.getDetectedColor(telemetry);

            telemetry.addData("Ball Count", color.ballCount);
            telemetry.update();
        }
    }
}
