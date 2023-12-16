package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

//        TrajectorySequenceBuilder autonomous_1 = new TrajectorySequenceBuilder(new Pose2d(11.70, -60.96, Math.toRadians(90.00)))
//                .splineTo(new Vector2d(13.02, -36.25), Math.toRadians(90.00))
//                .splineTo(new Vector2d(-43.85, -37.79), Math.toRadians(170.00))
//                .splineTo(new Vector2d(-62.43, 12.32), Math.toRadians(177.21))
//                .splineTo(new Vector2d(-56.23, 24.84), Math.toRadians(3.81))
//                .splineTo(new Vector2d(-27.52, 33.43), Math.toRadians(9.71))
//                .splineTo(new Vector2d(10.91, 34.28), Math.toRadians(0.00))
//                .splineTo(new Vector2d(21.61, -5.42), Math.toRadians(-74.05))
//                .splineTo(new Vector2d(52.29, -31.32), Math.toRadians(-2.29))
//                .build();

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 90, Math.toRadians(180), Math.toRadians(180), 12)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(11.70, -60.96, Math.toRadians(90.00)))
                        .splineTo(new Vector2d(13.02, -36.25), Math.toRadians(90.00))
                        .splineTo(new Vector2d(-43.85, -37.79), Math.toRadians(170.00))
                        .splineTo(new Vector2d(-62.43, 12.32), Math.toRadians(177.21))
                        .splineTo(new Vector2d(-56.23, 24.84), Math.toRadians(3.81))
                        .splineTo(new Vector2d(-27.52, 33.43), Math.toRadians(9.71))
                        .splineTo(new Vector2d(10.91, 34.28), Math.toRadians(0.00))
                        .splineTo(new Vector2d(21.61, -5.42), Math.toRadians(-74.05))
                        .splineTo(new Vector2d(52.29, -31.32), Math.toRadians(-2.29))
                        .build()

                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}