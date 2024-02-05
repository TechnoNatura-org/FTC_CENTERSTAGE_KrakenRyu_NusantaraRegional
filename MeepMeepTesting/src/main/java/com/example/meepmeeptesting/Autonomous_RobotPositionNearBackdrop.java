package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.roadrunner.Constraints;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.DriveTrainType;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequence;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

public class Autonomous_RobotPositionNearBackdrop {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);


        RoadRunnerBotEntity myBot = new RoadRunnerBotEntity(meepMeep, new Constraints(23, 23, Math.toRadians(180), Math.toRadians(180), 11),15,15,new Pose2d(11.47, -70, Math.toRadians(90)), new ColorSchemeRedDark(), 1, DriveTrainType.MECANUM, true);
        TrajectorySequence InitialTrajectory = myBot.getDrive().trajectorySequenceBuilder(new Pose2d(11.47, -72, Math.toRadians(90)))
                .forward(43)
                .strafeRight(4)
                .addTemporalMarker(() -> {

                })

                .strafeLeft(4)
                .back(7)
                .turn(Math.toRadians(90))
                .forward(3)
                .addTemporalMarker(()->{
                    // put the pixel
//                    pixelServo.setPosition(1);
                })
                .waitSeconds(1)
                .back(3)
                .turn(Math.toRadians(-90))
                .back(5)

                .splineToLinearHeading(new Pose2d(11, -43, Math.toRadians(0)), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(29, -43, Math.toRadians(0)), Math.toRadians(0))

                .splineToLinearHeading(new Pose2d(51, -36, Math.toRadians(0)), Math.toRadians(-2.86))
                .addTemporalMarker(() -> {
//                                   intake.setPower(1);
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
//                                    intake.setPower(0);
                })

                .splineToLinearHeading(new Pose2d(46.90, -60, Math.toRadians(0)), Math.toRadians(0.00))
                .splineToLinearHeading(new Pose2d(66, -62, Math.toRadians(0)), Math.toRadians(0))

                .build(  );
        myBot.followTrajectorySequence(InitialTrajectory);

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
