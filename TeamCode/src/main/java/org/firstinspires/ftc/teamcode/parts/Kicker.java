package org.firstinspires.ftc.teamcode.parts;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Kicker {
    public Servo kicker;

    public Kicker(HardwareMap hardwareMap) {
        kicker = hardwareMap.get(Servo.class, "kicker");
    }

    public void setKickerPositon(double kickerPositon){
        kicker.setPosition(kickerPositon);
    }
}
