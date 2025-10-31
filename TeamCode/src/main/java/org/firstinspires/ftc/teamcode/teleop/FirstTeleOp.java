package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.parts.Drivetrain;
import org.firstinspires.ftc.teamcode.parts.Intake;
import org.firstinspires.ftc.teamcode.parts.Kicker;
import org.firstinspires.ftc.teamcode.parts.Mortar;
import org.firstinspires.ftc.teamcode.parts.Turntable;


@TeleOp(name = "FirstTeleOp")
public class FirstTeleOp extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        Turntable turntable = new Turntable();
        Kicker kicker = new Kicker(hardwareMap);
        //Drivetrain drivetrain = new Drivetrain(hardwareMap, "frontLeft", "backLeft", "frontRight", "frontLeft"); // TODO: Fix this if gonna use
        Mortar flywheel = new Mortar(hardwareMap);
        Intake intake = new Intake(hardwareMap);

        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        turntable.init();

        double intakeSpeed = 0.5;
        double shootSpeed = 1.0;
        double reverseSpeed = -0.3;

        waitForStart();

        while(opModeIsActive()) {
            boolean intakeMode = false;
            turntable.loop();

            //moving using game pad one
            double y = gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = -gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y + x - rx) / denominator;
            double backRightPower = (y - x - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

            //empties only one
            if(gamepad2.rightBumperWasPressed()) {
                flywheel.setFlyMotorSpeed(shootSpeed);
                kicker.setKickerPositon(0.5);
                kicker.setKickerPositon(0);
            }
            if(gamepad2.leftBumperWasPressed()) {
                flywheel.setFlyMotorSpeed(0);
            }

            if(gamepad2.bWasPressed() && !intakeMode) {
                intake.setIntakeSpeed(intakeSpeed);
                flywheel.setFlyMotorSpeed(reverseSpeed);
                intakeMode = true;
            }

            if(gamepad2.bWasPressed() && intakeMode) {
                intake.setIntakeSpeed(0);
                intakeMode = false;
            }


        }
    }

    /*public void TurnTable() {
        MecanumDrive drive;
        double ticksPerRad;

        drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));
        ticksPerRad = (384.5*((double)85/25)) / (2*Math.PI);

        drive.updatePoseEstimate();

        Pose2d pose = drive.localizer.getPose();
        double x = pose.position.x;
        double y = pose.position.y;
        double heading = pose.heading.toDouble();

        double turretHeading = Math.atan2(y, -x) - heading; //I think this is right, you will know when you test
        turret.setTargetPosition((int)(turretHeading * ticksPerRad + 0.5));
    }*/

}
