package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.core.entity.BotEntity;
import com.noahbres.meepmeep.roadrunner.Constraints;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.DriveTrainType;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequence;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

public class Autonomous_RobotPositionFarBackdrop {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);


        RoadRunnerBotEntity myBot = new RoadRunnerBotEntity(meepMeep, new Constraints(23, 23, Math.toRadians(180), Math.toRadians(180), 11),15,15,new Pose2d(11.47, -70, Math.toRadians(90)), new ColorSchemeRedDark(), 1, DriveTrainType.MECANUM, true)
                ;

        TrajectorySequence InitialTrajectory = myBot.getDrive().trajectorySequenceBuilder(new Pose2d(-36.53, -72, Math.toRadians(90)))
                .forward(43)
                .strafeLeft(5)
                .addTemporalMarker(
                        () -> {
                            // measure the color
                        }
                )

                .strafeRight(5)
                .back(30)
                .strafeLeft(4)
//                .build();
                .splineToLinearHeading(new Pose2d(-36.53, -58, Math.toRadians(90)), Math.toRadians(0))

                .splineToLinearHeading(new Pose2d(-34, -58, Math.toRadians(90)), Math.toRadians(355))
                .splineToLinearHeading(new Pose2d(-20, -58, Math.toRadians(90)), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(-10, -58, Math.toRadians(90)), Math.toRadians(0))

                .splineToLinearHeading(new Pose2d(11, -58, Math.toRadians(90)), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(29, -43, Math.toRadians(90)), Math.toRadians(0))

                .splineToLinearHeading(new Pose2d(51, -36, Math.toRadians(0)), Math.toRadians(-2.86))


                .build();
        myBot.followTrajectorySequence(InitialTrajectory);

//        meepMeep.addEntity(myBot);
//        meepMeep.
//        meepMeep.

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
