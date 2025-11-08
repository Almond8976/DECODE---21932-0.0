package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;

import java.util.HashMap;

@Config
public class Util {

    public HashMap<String, String> deviceConf = new HashMap<String, String>();

    public Util() {
        // TODO: put proper names from config
        //             Name in code         Name in config
        deviceConf.put("frontLeftMotor",    "frontLeftMotor");
        deviceConf.put("backLeftMotor",     "backLeftMotor");
        deviceConf.put("frontRightMotor",   "frontRightMotor");
        deviceConf.put("backRightMotor",    "backRightMotor");
        deviceConf.put("intakeMotor",       "intake");
        deviceConf.put("rollersMotor",      "roller");
        deviceConf.put("kicker",            "kicker");
        deviceConf.put("turretMotor",       "turret");
        deviceConf.put("webcam1",           "camera");
        deviceConf.put("shooter",           "flyMotor");
        //deviceConf.put("colorSensor",       " colorSensor");
    }
}