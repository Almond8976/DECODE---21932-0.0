package org.firstinspires.ftc.teamcode.rr_wrappers;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Mortar;
import org.firstinspires.ftc.teamcode.subsystems.Turret;
import org.firstinspires.ftc.teamcode.subsystems.Util;

import java.util.HashMap;

public class TurretWrapper {
    private Mortar shooter;
    private Turret turret;
    private Intake intake;

    private int ballCount = 3;
    ElapsedTime time1 = new ElapsedTime();

    public TurretWrapper(HardwareMap hwMap,HashMap<String, String> config ,Pose2d startPos) {
        shooter = new Mortar(hwMap, config);
        turret = new Turret(hardwareMap, config, startPos);
        intake = new Intake(hwMap, config);

    }

    public void setBasketPos(Vector2d redBasket) {
        turret.setBasketPos(redBasket);
    }

    public class Launch implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
            }

            // function to call when action is ran
            boolean metShooterThresh = false;
            int shooterTargetSpeed = shooter.calcVelocity(Math.sqrt(
                    (turret.distanceToBasket().x * turret.distanceToBasket().x) + (turret.distanceToBasket().y * turret.distanceToBasket().y)));
            Turret.tracking = true;
            shooter.setVelocity(shooterTargetSpeed);
            time1.reset();

            while (ballCount > 0 && time1.seconds() < 5) {
                if (shooter.getVelocity() > shooterTargetSpeed - Mortar.THRESH) {
                    switch (ballCount) {
                        case 0:
                            intake.setAllPower(0);
                            shooter.setVelocity(Mortar.OFF);
                            Turret.tracking = false;
                            break;
                        case 1:
                        case 2:
                        case 3:
                            intake.setAllPower(1);
                            break;
                    }
                } else if (shooter.getVelocity() <= shooterTargetSpeed - Mortar.THRESH && metShooterThresh) {
                    ballCount--;
                    intake.setAllPower(0);
                }
                metShooterThresh = shooter.getVelocity() > shooterTargetSpeed - Mortar.THRESH;

            }
            return true;
        }

    }
    public Action launch(){
        return new Launch();
    }
<<<<<<< HEAD
    public class Update implements Action {
        private boolean initialized = false;

=======

    public class Update implements Action{
        private boolean initialized = false;

>>>>>>> f839e74476c3cf78f4cfb547f94a5bed0814848d
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
            }

            turret.update();
            shooter.update();
<<<<<<< HEAD
            return false;
        }
    }
=======
            intake.update();
            return false;
       }
   }
>>>>>>> f839e74476c3cf78f4cfb547f94a5bed0814848d

    public Action update() {
        return new Update();
    }
}
