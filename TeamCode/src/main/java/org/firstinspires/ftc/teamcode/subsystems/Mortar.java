package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.HashMap;

public class Mortar {
    private DcMotorEx flyMotor;

    private double power, vel;

    public static double THRESH = 100;
    public static double OFF = 0, MAX = 1, NORMAL = 0.6;

    public static double MAX_VEL = 1200;

    public Mortar(HardwareMap hardwareMap, HashMap<String, String> config) {
        flyMotor = hardwareMap.get(DcMotorEx.class, config.get("shooter"));
        flyMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        flyMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(300, 0, 0, 10));
        flyMotor.setDirection(Direction.REVERSE);
    }
    public void setPower(double motorPower) {
        power = motorPower;
    }

    public void setVelocity(double velocity) {
        vel = velocity;
    }

    public double getVelocity() {
        return flyMotor.getVelocity();
    }

    public double getTargetVelocity() {
        return vel;
    }

    public double getPower() {
        return power;
    }

    public void update() {
        //flyMotor.setPower(power);
        flyMotor.setVelocity(vel);
    }
 }
