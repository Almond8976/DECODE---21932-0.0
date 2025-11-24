package org.firstinspires.ftc.teamcode.rr_wrappers;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Intake;

import java.util.HashMap;

public class IntakeWrapper {

    private Intake intake;

    public IntakeWrapper(HardwareMap hwMap, HashMap<String, String> config) {
        intake = new Intake(hwMap, config);
    }

    public class StartIntake implements Action {

        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!initialized) {
                initialized = true;
            }

            intake.setIntakePower(1);

            return false;
        }
    }

    public class StopIntake implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!initialized) {
                initialized = true;
            }

            intake.setIntakePower(0);

            return false;
        }
    }
    private class Update implements Action {

        private boolean initialized = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!initialized) {
                initialized = true;
            }
            intake.update();
            return false;
        }
    }
    public Action update() {
        return new Update();
    }
}
