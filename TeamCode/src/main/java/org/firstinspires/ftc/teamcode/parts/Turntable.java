package org.firstinspires.ftc.teamcode.parts;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


public class Turntable extends OpMode {
    public DcMotor turn;

    @Override
    public void init() {
        int currentPosition = turn.getCurrentPosition();
        turn = hardwareMap.get(DcMotor.class, "turn");
        turn.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {

    }

}
