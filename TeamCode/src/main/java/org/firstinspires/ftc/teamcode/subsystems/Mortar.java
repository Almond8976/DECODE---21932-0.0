package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.HashMap;

public class Mortar {
    private DcMotor flyMotor;

    private double power;

    public static double OFF = 0, MAX = 1, NORMAL = 0.6;

    public Mortar(HardwareMap hardwareMap, HashMap<String, String> config) {
        flyMotor = hardwareMap.get(DcMotor.class, config.get("shooter"));
        flyMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flyMotor.setDirection(Direction.REVERSE);
    }

    public void setPower(double motorPower) {
        power = motorPower;
    }

    public double getPower() {
        return power;
    }

    public void update() {
        flyMotor.setPower(power);
    }
 }
