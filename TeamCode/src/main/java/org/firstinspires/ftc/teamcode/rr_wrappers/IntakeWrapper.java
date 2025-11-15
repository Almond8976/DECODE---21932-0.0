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

    ElapsedTime time1 = new ElapsedTime();

    public IntakeWrapper(HardwareMap hwMap, HashMap<String, String> config) {
        intake = new Intake(hwMap, config);
    }

    public class Intaking implements Action {

        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!initialized) {
                initialized = true;
            }

            time1.reset();
            while(time1.seconds() < 4) {
                intake.setAllPower(1);
            }

            return false;
        }
    }
    public Action update() {
        return new Update();
    }
}
