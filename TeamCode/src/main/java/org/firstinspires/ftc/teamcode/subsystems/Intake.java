package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.HashMap;

public class Intake {
    private DcMotorEx intakeLeft;
    private DcMotorEx intakeRight;

    private double intakePower, rollerPower;

    public Intake(HardwareMap hwMap, HashMap<String, String> config) {
<<<<<<< HEAD
        intakeLeft = hwMap.get(DcMotorEx.class, config.get("intakeLeft"));
        intakeRight = hwMap.get(DcMotorEx.class, config.get("intakeRight"));


        intakeLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        intakeLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
=======
        intake = hwMap.get(DcMotorEx.class, config.get("intakeMotor"));
        rollers = hwMap.get(DcMotorEx.class, config.get("rollersMotor"));

        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rollers.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        intake.setDirection(DcMotorSimple.Direction.REVERSE);

        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rollers.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
>>>>>>> bd0b5db2fccea16dbe2b7043136902e66dcd797f
    }

    public void setIntakePower(double power) {
        intakePower = power;
    }

    public void setRollerPower(double power) {
        rollerPower = power;
    }

    public void setAllPower(double power) {
        intakePower = power;
        rollerPower = power;
    }

    public void update() {
<<<<<<< HEAD
        intakeLeft.setPower(intakePower);
        intakeRight.setPower(rollerPower);
=======
        intake.setPower(intakePower);
        rollers.setPower(rollerPower);
>>>>>>> bd0b5db2fccea16dbe2b7043136902e66dcd797f
    }
}
