package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.HashMap;

@Config
public class Kicker {
    private Servo kicker;

    public static double DOWN = 0.75, UP = 1.0;
    private double position = DOWN;

    public Kicker(HardwareMap hardwareMap, HashMap<String, String> config) {
        kicker = hardwareMap.get(Servo.class, config.get("kicker"));
    }

    public void setPosition(double kickerPositon){
        position = kickerPositon;
    }

    public void update() {
        kicker.setPosition(position);
    }

    public double getPosition() {
        return kicker.getPosition();
    }
}
