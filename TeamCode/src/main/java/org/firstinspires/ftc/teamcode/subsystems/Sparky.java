package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Sparky {

    public int ballCount = 0;
    NormalizedColorSensor colorSensor;

    public enum DetectedColors {
        GREEN,
        BLUE,
        PURPLE,
        UNKNOWN
    }

    public void init(HardwareMap hwMap) {
        colorSensor = hwMap.get(NormalizedColorSensor.class, "colorSensor");
        colorSensor.setGain(6);
    }

    public DetectedColors getDetectedColor(Telemetry telemetry) {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();

        float normGreen, normRed, normBlue;
        normGreen = colors.green / colors.alpha;
        normRed = colors.red / colors.alpha;
        normBlue = colors.blue / colors.alpha;

        telemetry.addData("green", normGreen);
        telemetry.addData("red", normRed);
        telemetry.addData("blue", normBlue);
        telemetry.addData("Ball Count: ", ballCount);
        telemetry.update();

        if(normBlue > 0.1) {
            ballCount++;
        }

        return DetectedColors.BLUE;
    }
}
