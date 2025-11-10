package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.Sensor;

public class TestSensor extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Sensor sensor = new Sensor();

        waitForStart();

        while (opModeIsActive()) {
            sensor.getDistance();
        }
    }
}
