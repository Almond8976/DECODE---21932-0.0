package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

public class Sparky {

    NormalizedColorSensor colorSensor;

    public enum DetectedColors {
        GREEN,
        PURPLE
    }

    public void init(HardwareMap HwMap) {
        colorSensor = hardwareMap.get(NormalizedColorSensor.class "colorSensor");
    }

    public DetectedColors getDetectedColor() {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
    }
}
