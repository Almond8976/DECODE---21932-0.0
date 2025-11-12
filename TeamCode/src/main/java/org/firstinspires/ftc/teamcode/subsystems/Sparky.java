package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.graphics.Color;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.subsystems.ColorRangefinder;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Sparky {


    private NormalizedRGBA colors;
    private double distance;

    private ColorRangefinder colorSensor;
    public Sparky(HardwareMap hwMap) {
        colorSensor = new ColorRangefinder(hwMap.get(RevColorSensorV3.class, "colorSensor"));
        //colorSensor.setGain(6);
    }

    public NormalizedRGBA getDetectedColors() {
        colors = colorSensor.emulator.getNormalizedColors();

        return colors;
    }

    public double getDistance() {
        distance = colorSensor.readDistance();

        return distance;
    }

    public void setLEDBrightness(int val) {
        colorSensor.setLedBrightness(val);
    }
}
