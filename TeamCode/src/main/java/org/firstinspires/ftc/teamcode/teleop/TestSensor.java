package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Sensor;
import org.firstinspires.ftc.teamcode.subsystems.Sparky;
@Config
@TeleOp(name = "TestSensor")
public class TestSensor extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        Sparky sensor = new Sparky(hardwareMap);

        waitForStart();

        int ball = 0;

        boolean metDistanceSensorThresh = false;

        while (opModeIsActive()) {
            if(metDistanceSensorThresh && sensor.getDistance() < 50) {
                ball++;

            }
            metDistanceSensorThresh = sensor.getDistance() > 50;

            telemetry.addData("RED:", sensor.getDetectedColors().red);
            telemetry.addData("GREEN:", sensor.getDetectedColors().blue);
            telemetry.addData("BLUE:", sensor.getDetectedColors().green);
            telemetry.addData("ALPHA:", sensor.getDetectedColors().alpha);
            telemetry.addData("DISTANCE:", sensor.getDistance());
            telemetry.update();
        }
    }
}
