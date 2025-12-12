package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(160, 120, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(64.5, 16.4, Math.toRadians(180)))
                .strafeToSplineHeading(new Vector2d(-12, 16), Math.toRadians(90))
                .strafeToConstantHeading(new Vector2d(-12, 50))
                .strafeToSplineHeading(new Vector2d(-20, 20), Math.toRadians(90))
                .strafeToConstantHeading(new Vector2d(-2, 58))
                .strafeToSplineHeading(new Vector2d(-20, 20), Math.toRadians(90))
                .strafeToConstantHeading(new Vector2d(12, 29))
                .strafeToConstantHeading(new Vector2d(12, 60))
                .strafeToConstantHeading(new Vector2d(12, 20))
                .strafeToConstantHeading(new Vector2d(-20, 20))
                .strafeToConstantHeading(new Vector2d(8, 29))
                .strafeToSplineHeading(new Vector2d(13, 59.8), Math.toRadians(126.35), new TranslationalVelConstraint(50))
                .strafeToSplineHeading(new Vector2d(17, 62), Math.toRadians(155))
                .strafeToSplineHeading(new Vector2d(12, 56), Math.toRadians(-90))
                .splineTo(new Vector2d(-20, 20), Math.PI/2)
                .strafeToConstantHeading(new Vector2d(36, 29))
                .strafeToConstantHeading(new Vector2d(36, 60))
                .strafeToConstantHeading(new Vector2d(36, 40))
                .strafeToConstantHeading(new Vector2d(-20, 20))
                .strafeToSplineHeading(new Vector2d(36, 64), Math.toRadians(0))
                .strafeToConstantHeading(new Vector2d(62, 64), new TranslationalVelConstraint(70))
                .strafeToSplineHeading(new Vector2d(-20, 20), Math.toRadians(90))
                .strafeToSplineHeading(new Vector2d(0, 50), Math.toRadians(180))


//                .strafeToSplineHeading(new Vector2d(-12, 12), Math.toRadians(90))
//                //.strafeToConstantHeading(new Vector2d(-11, 33))
//                .strafeToConstantHeading(new Vector2d(-11, 53))
//                .strafeToConstantHeading(new Vector2d(-20, 20))
//                .strafeToConstantHeading(new Vector2d(13, 29))
//                .strafeToConstantHeading(new Vector2d(13, 60))
//                .strafeToSplineHeading(new Vector2d(-2, 54), Math.toRadians(0))
//                .strafeToConstantHeading(new Vector2d(-2, 60))
//                .strafeToSplineHeading(new Vector2d(-20, 20), Math.toRadians(90))
//                .strafeToConstantHeading(new Vector2d(36, 29))
//                .strafeToConstantHeading(new Vector2d(36, 60))
//                .strafeToSplineHeading(new Vector2d(-20, 20), Math.toRadians(0))
//                .strafeToConstantHeading(new Vector2d(24, 65))
//                .strafeToConstantHeading(new Vector2d(60, 65))
//                .strafeToConstantHeading(new Vector2d(-20, 20))
//                .strafeToConstantHeading(new Vector2d(0, 52))

                /*
                .strafeToConstantHeading(new Vector2d(-11, 53))
                .turnTo(0)
                .strafeToConstantHeading(new Vector2d(0, 54))
                .strafeToConstantHeading(new Vector2d(0, 58))
                .strafeToSplineHeading(new Vector2d(-20, 20), Math.toRadians(90))
                .strafeToConstantHeading(new Vector2d(11, 29))
                .strafeToConstantHeading(new Vector2d(11, 60))
                .strafeTo(new Vector2d(-20, 20))
                .strafeToConstantHeading(new Vector2d(36, 29))
                .strafeToConstantHeading(new Vector2d(36, 60))
                .strafeToConstantHeading(new Vector2d(-20, 20))*/
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}