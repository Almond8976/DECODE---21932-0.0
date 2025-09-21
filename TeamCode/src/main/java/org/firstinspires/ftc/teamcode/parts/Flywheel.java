package org.firstinspires.ftc.teamcode.parts;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Flywheel {
    public DcMotor flyMotor;

    ElapsedTime timer = new ElapsedTime();

    public Flywheel(HardwareMap hardwareMap) {
        flyMotor = hardwareMap.get(DcMotor.class, "flyMotor");
        flyMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
/*
    public void setTimer(ElapsedTime timer) {
        this.timer = timer;
    }
*/
    public void setFlyMotorSpeed(double flyMotorSpeed) {
        flyMotor.setPower(flyMotorSpeed);
    }
}
