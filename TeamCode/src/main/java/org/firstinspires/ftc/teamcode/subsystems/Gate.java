package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;

public class Gate {
    private Servo gate;

    public static double CLOSE = 0.75, OPEN = 1.0;
    private double position = OPEN;

    public Gate(HardwareMap hardwareMap, HashMap<String, String> config) {
        gate = hardwareMap.get(Servo.class, config.get("kicker"));
    }

    public void setPosition(double gatePosition){
        position = gatePosition;
    }



    public void update() {
        gate.setPosition(position);
    }

    public double getPosition() {
        return gate.getPosition();
    }
}
