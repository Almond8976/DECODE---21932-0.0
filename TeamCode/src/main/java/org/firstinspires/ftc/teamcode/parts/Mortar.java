package org.firstinspires.ftc.teamcode.parts;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Mortar {
    public DcMotor flyMotor;

    public Mortar(HardwareMap hardwareMap) {
        flyMotor = hardwareMap.get(DcMotor.class, "flyMotor");
        flyMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flyMotor.setDirection(Direction.REVERSE);
    }

    public void setFlyMotorSpeed(double flyMotorSpeed) {
        flyMotor.setPower(flyMotorSpeed);
    }
}
