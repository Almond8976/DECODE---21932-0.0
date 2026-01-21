package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

import java.util.HashMap;

public class Hood {

    private Servo hood;
    private MecanumDrive drive;
    private double x, y;

    public static boolean tracking = false;
    private double pos;
    public static double CLOSE = .67, FAR = .35;
    private Pose2d pose;


    public Hood(HardwareMap hardwareMap, HashMap<String, String> config, Pose2d startPos) {
        drive = new MecanumDrive(hardwareMap, startPos);
        pose = startPos;
        hood = hardwareMap.get(Servo.class, config.get("hood"));
    }

    public void setHoodPosition(double pos) {
        hood.setPosition(pos);
    }
}
