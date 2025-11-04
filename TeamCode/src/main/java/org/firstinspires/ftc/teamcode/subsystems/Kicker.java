package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.HashMap;

@Config
public class Kicker {
    private Servo kicker;

    // TODO: find actual positions with servotest
    public static double DOWN = 0, UP = 0.5;
    private double position;

    public Kicker(HardwareMap hardwareMap, HashMap<String, String> config) {
        kicker = hardwareMap.get(Servo.class, config.get("kicker"));
    }

    public void setPosition(double kickerPositon){
        position = kickerPositon;
    }

    public void update() {
        kicker.setPosition(position);
    }
}
