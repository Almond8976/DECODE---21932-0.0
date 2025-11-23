package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-57.78, 45.6439, Math.toRadians(128.188)))
                .strafeToConstantHeading(new Vector2d(-20, 20))
                .strafeToSplineHeading(new Vector2d(-11, 33), Math.toRadians(90))
                .strafeToConstantHeading(new Vector2d(-11, 53))
                .turnTo(0)
                .strafeToConstantHeading(new Vector2d(0, 54))
                .strafeToConstantHeading(new Vector2d(0, 58))
                .strafeToConstantHeading(new Vector2d(-20, 20))
                .strafeToSplineHeading(new Vector2d(11, 33), Math.toRadians(90))
                .strafeToConstantHeading(new Vector2d(11, 53))
                .strafeTo(new Vector2d(-20, 20))
                .strafeToConstantHeading(new Vector2d(36, 33))
                .strafeToConstantHeading(new Vector2d(36, 53))
                .strafeToConstantHeading(new Vector2d(-20, 20))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}