package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.parts.Kicker;
import org.firstinspires.ftc.teamcode.parts.Mortar;

@Autonomous(name = "FirstAuto")
public class FirstAuto extends LinearOpMode {
    Kicker kicker = new Kicker(hardwareMap);
    Mortar flywheel = new Mortar(hardwareMap);
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));

        waitForStart();


        Actions.runBlocking(
                drive.actionBuilder(now Pose2d(0,0,0))





    }
    public void Launch() {
        flywheel.setFlyMotorSpeed(1);
        for(int i = 0; i < 3; i++) {
            kicker.setKickerPositon(0.5);
            kicker.setKickerPositon(0);
        }
    }
    public void intake() {

    }

}
