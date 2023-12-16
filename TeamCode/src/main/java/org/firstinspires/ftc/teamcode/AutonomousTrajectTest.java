package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Config
@Autonomous(group = "drive")
public class AutonomousTrajectTest extends LinearOpMode {

    private Telemetry telemetry;
    private SampleMecanumDrive drive;
    private DcMotorEx intake;
    private DcMotorEx lifter1;
    private DcMotorEx lifter2;
    private DcMotorEx flipper;

    @Override
    public void runOpMode() throws InterruptedException {

//        intake = hardwareMap.get(DcMotorEx.class, "intake");
//        lifter1 = hardwareMap.get(DcMotorEx.class, "lifter1");
//        lifter2 = hardwareMap.get(DcMotorEx.class, "lifter2");
//        flipper = hardwareMap.get(DcMotorEx.class, "flipper");

        telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        drive = new SampleMecanumDrive(hardwareMap);

        TrajectorySequence untitled0 = drive.trajectorySequenceBuilder(new Pose2d(11.86, 60.80, Math.toRadians(270.00)))
                .splineTo(new Vector2d(35.59, 34.43), Math.toRadians(0.00))
                .build();
        drive.setPoseEstimate(untitled0.start());

        waitForStart();
        if (isStopRequested()) return;

        drive.followTrajectorySequence(untitled0);

        Pose2d poseEstimate = drive.getPoseEstimate();
        telemetry.addData("finalX", poseEstimate.getX());
        telemetry.addData("finalY", poseEstimate.getY());
        telemetry.addData("finalHeading", poseEstimate.getHeading());
        telemetry.update();


        while (!isStopRequested() && opModeIsActive()) ;


    }
}
