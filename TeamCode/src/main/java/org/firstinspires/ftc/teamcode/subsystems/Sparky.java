package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.hardware.lynx.LynxI2cDeviceSynch;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Sparky {

    NormalizedColorSensor colorSensor;

    public void init(HardwareMap hwMap) {
        RevColorSensorV3 colorSensor = hardwareMap.get(RevColorSensorV3.class, "colorSensor");
        colorSensor.setGain(6);
    }

    public float getDetectedColor() {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        float normGreen, normRed, normBlue;
        normGreen = colors.green / colors.alpha;
        normRed = colors.red / colors.alpha;
        normBlue = colors.blue / colors.alpha;

        return normBlue;
    }
}
