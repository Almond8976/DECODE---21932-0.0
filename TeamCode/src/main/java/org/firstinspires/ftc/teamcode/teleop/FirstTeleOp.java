package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.parts.Intake;
import org.firstinspires.ftc.teamcode.parts.Kicker;
import org.firstinspires.ftc.teamcode.parts.Mortar;
import org.firstinspires.ftc.teamcode.parts.Turret;

@Config
@TeleOp(name = "FirstTeleOp")
public class FirstTeleOp extends LinearOpMode {
    public double shootSpeed = 0.6;
    public double speedMult = 1;
    public double rotationMult = 0.7;

    public boolean intakeMode = false;

    @Override
    public void runOpMode() throws InterruptedException {

        Turret turret = new Turret(hardwareMap);
        Kicker kicker = new Kicker(hardwareMap);
        Mortar flywheel = new Mortar(hardwareMap);
        Intake intake = new Intake(hardwareMap);

        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        turret.init();

        waitForStart();

        while(opModeIsActive()) {
            turret.update();

            //moving using game pad one
            double y = gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x;
            double rx = -gamepad1.right_stick_x*rotationMult;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = ((y + x + rx) / denominator)*speedMult;
            double backLeftPower = ((y - x + rx) / denominator)*speedMult;
            double frontRightPower = ((y - x - rx) / denominator)*speedMult;
            double backRightPower = ((y + x - rx) / denominator)*speedMult;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);


            if(gamepad1.right_bumper) {
                flywheel.setFlyMotorSpeed(shootSpeed);
                //intake.setIntakeSpeed(0.1);
            }
            if(gamepad1.left_bumper) {
                flywheel.setFlyMotorSpeed(0);
                intake.setIntakeSpeed(0);
            }
            if(gamepad2.x) {
                kicker.setKickerPositon(1);
            }
            if(gamepad2.y) {
                kicker.setKickerPositon(0.75);
            }

            if(gamepad2.right_bumper) {
                intake.setIntakeSpeed(1.0);
                intakeMode = true;
            }

            if(gamepad2.left_bumper) {
                intake.setIntakeSpeed(0);
                flywheel.setFlyMotorSpeed(0);
                intakeMode = false;
            }
        }
    }
}
