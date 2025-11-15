package org.firstinspires.ftc.teamcode.rr_wrappers;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.teamcode.teleop.FirstTeleOpRed.shooterTimeThresh;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Mortar;
import org.firstinspires.ftc.teamcode.subsystems.Turret;
import org.firstinspires.ftc.teamcode.subsystems.Util;

import java.util.HashMap;

public class TurretWrapper {
    private Mortar shooter;
    private Turret turret;
    private Intake intake;
    private Kicker kicker;

    private int ballCount;
    ElapsedTime time1 = new ElapsedTime();
    ElapsedTime time2 = new ElapsedTime();
    ElapsedTime time3 = new ElapsedTime();

    public TurretWrapper(HardwareMap hwMap,HashMap<String, String> config ,Pose2d startPos) {
        shooter = new Mortar(hwMap, config);
        turret = new Turret(hwMap, config, startPos);
        intake = new Intake(hwMap, config);
        kicker = new Kicker(hwMap, config);

    }

    public void setBasketPos(Vector2d redBasket) {
        turret.setBasketPos(redBasket);
    }

    public class Launch implements Action {

        public Launch(int balls){
            ballCount = balls;
        }
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet)  {
            if (!initialized) {
                initialized = true;
            }

            // function to call when action is ran



            int shooterTargetSpeed = shooter.calcVelocity(Math.sqrt(
                    (turret.distanceToBasket().x * turret.distanceToBasket().x) + (turret.distanceToBasket().y * turret.distanceToBasket().y)));
            Turret.tracking = true;
            shooter.setVelocity(shooterTargetSpeed);
            time1.reset();


            int prevShooterVel = (int)shooter.getVelocity();
            while (ballCount > 0 && time1.seconds() < 5) {
                if (shooter.getVelocity() > shooterTargetSpeed - Mortar.THRESH) {
                    switch (ballCount) {
                        case 0:
                            intake.setAllPower(0);
                            shooter.setVelocity(Mortar.OFF);
                            Turret.tracking = false;
                            break;
                        case 1: intake.setAllPower(1); kicker.sweep(); break;
                        case 2: intake.setAllPower(0); Thread.sleep(2000); kicker.sweep(); break;
                        case 3:
                            intake.setAllPower(1);
                            break;
                    }
                } else if(time2.milliseconds()>shooterTimeThresh) {
                    if(shooter.getVelocity()-prevShooterVel <-Mortar.THRESH) {
                        ballCount--;
                        intake.setAllPower(0);
                    }
                    prevShooterVel = (int)shooter.getVelocity();
                    time2.reset();
                }

            }

            turret.setPosition(0);
            shooter.setPower(0);
            kicker.setPosition(Kicker.DOWN);
            return false;
        }
    }
    public Action launch(){
        return new Launch(3);
    }
    public class Update implements Action{
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
            }

            turret.update();
            shooter.update();
            intake.update();
            return false;
       }
   }

    public Action update() {
        return new Update();
    }
}
