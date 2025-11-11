package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Mortar;
import org.firstinspires.ftc.teamcode.subsystems.Util;

@Config
@TeleOp(name = "TestShooter")
public class TestShooter extends LinearOpMode {
    public static double vel = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        Util util = new Util();
        Mortar shooter = new Mortar(hardwareMap, util.deviceConf);
        Kicker kicker = new Kicker(hardwareMap, util.deviceConf);



        waitForStart();

        while(opModeIsActive()) {

            if (gamepad1.a) {
                vel = 0;
            }
            if (gamepad1.right_bumper) {
                vel += 100;
            }
            if (gamepad1.left_bumper) {
                vel -= 100;
            }

            if (gamepad2.x) {
                kicker.setPosition(Kicker.DOWN);
            }
            if (gamepad2.y) {
                kicker.setPosition(Kicker.UP);
            }
            
            shooter.setVelocity(vel);
            shooter.update();
            kicker.update();

            telemetry.addData("vel", shooter.getVelocity());
            telemetry.addData("target vel", shooter.getTargetVelocity());
            telemetry.update();

        }
    }
}
