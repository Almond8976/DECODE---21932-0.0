package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.HashMap;

public class Mortar {
    private DcMotorEx flyMotor;

    private double power;

    public static double THRESH = 100;
    public static double OFF = 0, MAX = 1, NORMAL = 0.6;

    public Mortar(HardwareMap hardwareMap, HashMap<String, String> config) {
        flyMotor = hardwareMap.get(DcMotorEx.class, config.get("shooter"));
        flyMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        flyMotor.setDirection(Direction.REVERSE);
    }
    public void setPower(double motorPower) {
        power = motorPower;
    }

    public void setVelocity(double velocity) {
        flyMotor.setVelocity(velocity);
    }

    public double getVelocity() {
        return flyMotor.getVelocity(AngleUnit.DEGREES);
    }

    public double getPower() {
        return power;
    }

    public void update() {
        flyMotor.setPower(power);
    }
 }
