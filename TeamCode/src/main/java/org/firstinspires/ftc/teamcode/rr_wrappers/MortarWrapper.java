package org.firstinspires.ftc.teamcode.rr_wrappers;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Mortar;

import java.security.PublicKey;
import java.util.HashMap;

public class MortarWrapper {

    private Mortar shooter;

    private double power;

    public MortarWrapper(HardwareMap hwMap, HashMap<String, String> config) {
        shooter = new Mortar(hwMap, config);
    }

    // Actions
    public class SetPower implements Action {
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

    public Action setPower(double pow) {
        power = pow;
        return new SetPower();
    }

    // this will be run in a parallel thread in the auton in order to constantly update the subsystem
    public class Update implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
            }

            shooter.update();
            double pow = shooter.getPower();
            packet.put("shooter power", pow);
            return true;
        }
    }

    private Action update() {
        return new Update();
    }

    public class setBasketPos implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {


            return false;
        }
    }
}
