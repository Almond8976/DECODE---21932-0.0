package org.firstinspires.ftc.teamcode.parts;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    public DcMotor intake;

    public Intake(HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotor.class, "intake");
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setIntakeSpeed(double intakeSpeed) {
        intake.setPower(intakeSpeed);
    }
}
