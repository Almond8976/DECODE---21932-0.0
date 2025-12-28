package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
@Config
public class dualServoTest extends LinearOpMode {
    public static double change = 0.001;
    public static String servoName = "turret";
    public static String servoName2 = "turret2";

    @Override
    public void runOpMode() throws InterruptedException {
        Servo servo = hardwareMap.get(Servo.class, servoName);
        Servo servo2 = hardwareMap.get(Servo.class, servoName2);
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            if (gamepad1.dpad_right)
            {
                servo.setPosition(servo.getPosition() + change);
                servo2.setPosition(servo2.getPosition() + change);
            }
            if (gamepad1.dpad_left)
            {
                servo.setPosition(servo.getPosition() - change);
                servo2.setPosition(servo2.getPosition() - change);
            }
            if (gamepad1.dpad_up)
            {
                servo.setPosition(1);
                servo2.setPosition(1);
            }
            if (gamepad1.dpad_down)
            {
                servo.setPosition(0);
                servo2.setPosition(0);
            }
            if(gamepad1.x) {
                servo.setPosition(.5);
                servo2.setPosition(.5);
            }
            if (gamepad1.a)
            {
                servo.setDirection(Servo.Direction.FORWARD);
                servo2.setDirection(Servo.Direction.FORWARD);
            }
            if (gamepad1.b)
            {
                servo.setDirection(Servo.Direction.REVERSE);
                servo2.setDirection(Servo.Direction.REVERSE);
            }
            telemetry.addData("Pos", servo.getPosition());
            telemetry.addData("Dir", servo.getDirection());
            telemetry.addData("Pos", servo2.getPosition());
            telemetry.addData("Dir", servo2.getDirection());


            telemetry.update();


        }
    }
}