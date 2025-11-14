package org.firstinspires.ftc.teamcode.rr_wrappers;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

public class TurretWrapper {
    public class Launch implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
            }

            // function to call when action is ran
            shooter.setPower(power);
            double pow = shooter.getPower();
            packet.put("shooter power", pow);
            return true;
        }
    }
}
