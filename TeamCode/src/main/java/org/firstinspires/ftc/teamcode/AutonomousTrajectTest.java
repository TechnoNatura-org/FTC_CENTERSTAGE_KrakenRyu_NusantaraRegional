package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Config
@Autonomous( group = "drive")
public class AutonomousTrajectTest extends LinearOpMode {

    private Telemetry telemetry;
    private SampleMecanumDrive drive;
    private DcMotorEx intake;
    private DcMotorEx lifter1;
    private DcMotorEx lifter2;
    private DcMotorEx flipper;

    @Override
    public void runOpMode() throws InterruptedException {

//        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        drive = new SampleMecanumDrive(hardwareMap);

//        TrajectorySequence untitled0 = drive.trajectorySequenceBuilder(new Pose2d(11.86, 60.80, Math.toRadians(270.00)))
//                .splineTo(new Vector2d(35.59, 34.43), Math.toRadians(0.00))
//                .build();
//        drive.setPoseEstimate(untitled0.start());
//
//        TrajectorySequence untitled1 = drive.trajectorySequenceBuilder(new Pose2d(13.02, -61.62, Math.toRadians(90.00)))
//                .splineTo(new Vector2d(11.70, -35.92), Math.toRadians(92.94))
//                .splineTo(new Vector2d(-21.42, -37.57), Math.toRadians(178.57))
//                .splineTo(new Vector2d(-55.69, -38.55), Math.toRadians(182.80))
//                .splineTo(new Vector2d(-54.04, -12.03), Math.toRadians(7.91))
//                .splineTo(new Vector2d(-30.65, -13.68), Math.toRadians(-7.85))
//                .splineTo(new Vector2d(-13.35, -10.87), Math.toRadians(-3.07))
//                .splineTo(new Vector2d(53.22, 26.20), Math.toRadians(90.00))
//                .build();
        TrajectorySequence untitled0 =  drive.trajectorySequenceBuilder(new Pose2d(11.37, -62.28, Math.toRadians(92.05)))
                .splineTo(new Vector2d(11.86, -36.08), Math.toRadians(90.00))
                .splineTo(new Vector2d(52.23, -41.52), Math.toRadians(4.64))
                .build();
        Trajectory untitled1 = drive.trajectoryBuilder(new Pose2d(11.33, 62.57, Math.toRadians(270.00)))
                .splineTo(new Vector2d(11.61, 35.68), Math.toRadians(270.00))
                .splineTo(new Vector2d(24.00, 42.16), Math.toRadians(-3.61))
                .splineTo(new Vector2d(48.07, 35.26), Math.toRadians(2.20))
                .splineTo(new Vector2d(18.65, 45.26), Math.toRadians(182.68))
                .splineTo(new Vector2d(1.48, 36.39), Math.toRadians(185.44))
                .splineTo(new Vector2d(-14.57, 36.81), Math.toRadians(185.44))
                .splineTo(new Vector2d(-42.16, 35.82), Math.toRadians(178.96))
                .splineTo(new Vector2d(-61.72, 36.11), Math.toRadians(180.00))
                .splineTo(new Vector2d(-58.06, 43.14), Math.toRadians(6.34))
                .splineTo(new Vector2d(-35.54, 59.61), Math.toRadians(2.39))
                .splineTo(new Vector2d(-3.17, 60.88), Math.toRadians(-2.16))
                .splineTo(new Vector2d(9.78, 59.47), Math.toRadians(-2.16))
                .splineTo(new Vector2d(27.10, 58.06), Math.toRadians(-2.20))
                .splineTo(new Vector2d(48.49, 37.37), Math.toRadians(0.00))
                .splineTo(new Vector2d(51.31, 60.04), Math.toRadians(41.42))
                .build();
        TrajectorySequence Autonomous =  drive.trajectorySequenceBuilder(new Pose2d(11.70, -62.11, Math.toRadians(90.00)))
                .splineTo(new Vector2d(11.70, -33.78), Math.toRadians(90.00))
                .splineTo(new Vector2d(-12.03, -60.30), Math.toRadians(181.30))
                .splineTo(new Vector2d(-57.01, -58.82), Math.toRadians(181.55))
                .splineTo(new Vector2d(-59.31, -45.14), Math.toRadians(90.00))
                .splineTo(new Vector2d(-59.81, 4.12), Math.toRadians(90.00))
                .splineTo(new Vector2d(-61.78, 11.70), Math.toRadians(184.48))
                .build();
        drive.setPoseEstimate(new Pose2d(11.47, -34.28, 270));

        waitForStart();
        if (isStopRequested()) return;

//        drive.turn(Math.toRadians(90));
//        drive.followTrajectory(untitled1);

//        Pose2d poseEstimate = drive.getPoseEstimate();
//        telemetry.addData("finalX", poseEstimate.getX());
//        telemetry.addData("finalY", poseEstimate.getY());
//        telemetry.addData("finalHeading", poseEstimate.getHeading());
//        telemetry.update();


        while (!isStopRequested() && opModeIsActive()) ;


    }
}
