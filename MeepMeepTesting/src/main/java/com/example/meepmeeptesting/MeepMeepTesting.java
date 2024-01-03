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
                .setConstraints(
                        23, 60, Math.toRadians(180), Math.toRadians(180), 11)
                .followTrajectorySequence(drive ->
                         drive.trajectorySequenceBuilder(new Pose2d(-36.53, 64, Math.toRadians(270)))

                                 // normal position  (forward by 30)

                                 .forward(40)

                                 // temporal marker check their  using color sensor

                                 .back(10)
                                 // set position\


//                                 .turn(Math.toRadians(-90))
//                                 .forward(3)
//                                 .back(3)
//                                 .turn(Math.toRadians(90))

//                                 .forward(8)

//                                 .forward(7)
//                                 .strafeLeft(11)

                                 // robot at front and the team prop on the side of steel bar
                                 .strafeRight(5)
                                 // temporal marker put white pixel


//                                 .back(6)
//                                 .turn(Math.toRadians(50))
//
//                                 .forward(5)
//
//
//                                 // Move to backdrop
                                 .splineToLinearHeading(new Pose2d(-24.28, 10.24, Math.toRadians(0)), Math.toRadians(-3.69))

                                 .splineToLinearHeading(new Pose2d(24.6, 10.61, Math.toRadians(0)), Math.toRadians(358.53))
                                 .splineToLinearHeading(new Pose2d(48, 35.14, Math.toRadians(0)), Math.toRadians(-2.86))
                                 .strafeRight(6.5)
                                 .turn(Math.toRadians(180))
                                 // temporal marker here to put pixel on backdrop
                                 .splineToLinearHeading(new Pose2d(40, 34.16, Math.toRadians(180)), Math.toRadians(196.58))
                                 .splineToLinearHeading(new Pose2d(23, 11.06, Math.toRadians(180)), Math.toRadians(175.52))
                                 .splineToLinearHeading(new Pose2d(-5.28, 11.06, Math.toRadians(180)), Math.toRadians(182.92))
                                 .splineToLinearHeading(new Pose2d(-26.75, 10.40, Math.toRadians(180)), Math.toRadians(180.00))
                                 .splineToLinearHeading(new Pose2d(-63.91, 11.39, Math.toRadians(180)), Math.toRadians(180.00))
                                    //temporal marker here to sucks pixel
                                 .splineToLinearHeading(new Pose2d(-31.38, 11.23, Math.toRadians(180)), Math.toRadians(0.58))
                                 .splineToSplineHeading(new Pose2d(25.10, 11.39, Math.toRadians(180)), Math.toRadians(1.53))
                                 .splineToLinearHeading(new Pose2d(49.21, 35.67, Math.toRadians(180)), Math.toRadians(-2.73))
                                 // temporal marker to put pixel
                                 .splineToLinearHeading(new Pose2d(46.90, 60.44, Math.toRadians(180)), Math.toRadians(0.00))
                                 .splineToLinearHeading(new Pose2d(60.61, 61.10, Math.toRadians(180)), Math.toRadians(-2.60))
                                 .turn(Math.toRadians(180))
//                                 .splineTo(new Vector2d(11.61, 30), Math.toRadians(270.00))
//                                 .strafeTo(new Vector2d(45,35))
//                                 .splineToSplineHeading(new Pose2d(45, 35, Math.toRadians(0)), Math.toRadians(0))
//                                 .turn(Math.toRadians(90))
                                 // turn
//                                 .splineToLinearHeading(new Pose2d(18.65, 15.32, Math.toRadians(180)), Math.toRadians(182.68))
//                                 .splineToLinearHeading(new Pose2d(1.48, 9.39, Math.toRadians(180)), Math.toRadians(185.44))
//                                 .splineToLinearHeading(new Pose2d(-14.57, 9.39, Math.toRadians(180)), Math.toRadians(178.96))
//                                 .splineToLinearHeading(new Pose2d(-42.16, 9.39, Math.toRadians(180)), Math.toRadians(178.96))
//                                 .splineToLinearHeading(new Pose2d(-61.72, 36.11, Math.toRadians(180)), Math.toRadians(180.00))
//                                 .splineToLinearHeading(new Pose2d(-58.06, 43.14,  Math.toRadians(180)), Math.toRadians(6.34))
//                                 .splineToLinearHeading(new Pose2d(-35.54, 9.39,  Math.toRadians(180)), Math.toRadians(2.39))
//                                 .splineToLinearHeading(new Pose2d(-3.17, 9.39,  Math.toRadians(180)), Math.toRadians(-2.16))
//                                 .splineToLinearHeading(new Pose2d(9.78, 9.39,  Math.toRadians(180)), Math.toRadians(-2.20))
//                                 .splineToLinearHeading(new Pose2d(27.10, 9.39,  Math.toRadians(180)), Math.toRadians(-2.20))
//                                 .splineToLinearHeading(new Pose2d(48.49, 37.37,  Math.toRadians(180)), Math.toRadians(0.00))
//                                 .splineToLinearHeading(new Pose2d(62.85, 62.43,  Math.toRadians(180)), Math.toRadians(41.42))
                                 .build()
//                        drive.trajectorySequenceBuilder(new Pose2d(11.47, 60.18, Math.toRadians(-88.85)))
//                                .splineTo(new Vector2d(10.49, 34.84), Math.toRadians(270.00))
//                                .splineTo(new Vector2d(27.94, 41.03), Math.toRadians(-7.13))
//                                .splineTo(new Vector2d(45.54, 39.77), Math.toRadians(0.00))
//                                .splineTo(new Vector2d(45.54, 19.07), Math.toRadians(-86.63))
//                                .splineTo(new Vector2d(32.30, -32.02), Math.toRadians(270.00))
//                                .splineTo(new Vector2d(10.77, -41.17), Math.toRadians(150.05))
//                                .splineTo(new Vector2d(-58.63, -24.84), Math.toRadians(90.00))
//                                .splineTo(new Vector2d(-60.32, -12.04), Math.toRadians(176.99))
//                                .splineTo(new Vector2d(-43.00, 6.97), Math.toRadians(-0.47))
//                                .splineTo(new Vector2d(-16.12, 9.92), Math.toRadians(-0.49))
//                                .splineTo(new Vector2d(49.06, 31.88), Math.toRadians(6.58))
//                                .splineTo(new Vector2d(62.29, 60.60), Math.toRadians(0.00))
//                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}