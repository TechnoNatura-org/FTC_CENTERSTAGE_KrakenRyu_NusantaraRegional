package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.PID;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.util.CameraSubsystem;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous(name="Autonomous_RobotPositionNearBackdrop", group = "IFR2024")
public class Autonomous_RobotPositionNearBackdrop extends LinearOpMode {

    private SampleMecanumDrive drive;
    private String alliance = "";
    private String team_prop_location = ""; // left, center, right

    private DcMotor intake = null;
    private Servo pixelServo = null;

    private ColorSensor color;

    CameraSubsystem camera;

    public static PIDCoefficients Flipper_PIDCoef = new PIDCoefficients(0.1,0.0002,0.007);
    public static PID flipper_PID = new PID(Flipper_PIDCoef, 2.0);


    @Override
    public void runOpMode() {
        camera = new CameraSubsystem(hardwareMap);

        drive = new SampleMecanumDrive(hardwareMap);
        pixelServo = hardwareMap.get(Servo.class, "pixelServo");
//
//        intake = hardwareMap.get(DcMotor.class, "intake");

        color = hardwareMap.get(ColorSensor.class, "Color");

        Pose2d Red = new Pose2d(6, -37.5, Math.toRadians(90));
        Pose2d Blue = new Pose2d(6, 37.5, Math.toRadians(270));

        telemetry.addData("Alliance ", "Initialised");
        telemetry.addData("Team Prop Location ", "Initialised");

        TrajectorySequence InitialTrajectory = drive.trajectorySequenceBuilder(new Pose2d(0,0, Math.toRadians(90)))
                .forward(43)
                .strafeRight(4)
                .addTemporalMarker(
                        () -> {
                            // measure the color
                            double red = color.red();
                            double blue = color.blue();
                            if (red>blue) {
                                alliance = "red";
                            } else if (red<blue) {
                                alliance = "blue";
                            }

                            telemetry.addData("Alliance ", alliance);

                            telemetry.update();
                            color.close();
                        }
                )
                .build();

        TrajectorySequence Trajectory_TeamProp_Center = drive.trajectorySequenceBuilder(InitialTrajectory.end())
                .back(3)
                .strafeLeft(13)
                .addTemporalMarker(()->{
                    // put the pixel
                    pixelServo.setPosition(1);
                })

                .waitSeconds(1)
                .strafeRight(13)

                .back(4)
                .build();
        TrajectorySequence Trajectory_TeamProp_Right = drive.trajectorySequenceBuilder(InitialTrajectory.end())
                .back(7)
                .strafeRight(1)
                .addTemporalMarker(()->{
                    // put the pixel
                    pixelServo.setPosition(1);
                })
                .waitSeconds(1)
                .strafeLeft(5)
                .back(8)
                .build();
        TrajectorySequence Trajectory_TeamProp_Left = drive.trajectorySequenceBuilder(InitialTrajectory.end())
                .strafeLeft(4)
                .back(7)
                .turn(Math.toRadians(90))
                .forward(3)
                .addTemporalMarker(()->{
                    // put the pixel
                    pixelServo.setPosition(1);
                })
                .waitSeconds(1)
                .back(3)
                .turn(Math.toRadians(-90))
                .back(5)
                .build();


        TrajectorySequence Trajectory_AsRed_ToBackDrop = drive.trajectorySequenceBuilder(Red)
                .splineToLinearHeading(new Pose2d(11, -43, Math.toRadians(0)), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(29, -43, Math.toRadians(0)), Math.toRadians(0))

                .splineToLinearHeading(new Pose2d(51, -36, Math.toRadians(0)), Math.toRadians(-2.86))

//                .addTemporalMarker(() -> {
//                    intake.setPower(-1);
//                })
//                .waitSeconds(1)
//                .addTemporalMarker(() -> {
//                    intake.setPower(0);
//                })

                .splineToLinearHeading(new Pose2d(46.90, -60, Math.toRadians(0)), Math.toRadians(0.00))
                .splineToLinearHeading(new Pose2d(70, -62, Math.toRadians(0)), Math.toRadians(0))

                .build();

        TrajectorySequence Trajectory_AsBlue_ToBackDrop = drive.trajectorySequenceBuilder(Blue)
                .splineToLinearHeading(new Pose2d(11, 43, Math.toRadians(0)), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(29, 43, Math.toRadians(0)), Math.toRadians(0))

                .splineToLinearHeading(new Pose2d(51, 36, Math.toRadians(0)), Math.toRadians(2.86))


//                .turn(180)
//                .back(4)

//                .addTemporalMarker(() -> {
//                    intake.setPower(-1);
//                })
//                .waitSeconds(1)
//                .addTemporalMarker(() -> {
//                    intake.setPower(0);
//                })

                .splineToLinearHeading(new Pose2d(46.90,60 /*12.32*/, Math.toRadians(0)), Math.toRadians(0.00))
                .splineToLinearHeading(new Pose2d(70, 62, Math.toRadians(0)), Math.toRadians(0))

                .build();
        waitForStart();

        telemetry.addData("Camera Status ", "Waiting for streaming state");

        // waiting for camera to respond
        VisionPortal.CameraState cameraState = null;
        do {
            cameraState = camera.getCameraState();
            if (cameraState == VisionPortal.CameraState.ERROR) {
                break;
            }
        }
        while (camera.getCameraState() != VisionPortal.CameraState.STREAMING);
        telemetry.addData("Camera Status ", "Camera is Streaming ");

        TrajectorySequence Trajectory_FindTeamProp_AtCenter = drive.trajectorySequenceBuilder(new Pose2d(0,0, Math.toRadians(90)))
                .waitSeconds(2)
                .addTemporalMarker(
                        () -> {
                            boolean isDetectingTeamProp = camera.isDetectingTeamProp();
                            if (isDetectingTeamProp) {
                                team_prop_location = "center";
                            }
                            telemetry.addData("Team Prop Location ", team_prop_location);
                            telemetry.update();
                        }
                )
                .build();

        TrajectorySequence Trajectory_FindTeamProp_AtRight = drive.trajectorySequenceBuilder(new Pose2d(0,0, Math.toRadians(90)))
                .forward(5)
                .turn(Math.toRadians(-20))
                .waitSeconds(2)
                .addTemporalMarker(
                        () -> {
                            boolean isDetectingTeamProp = camera.isDetectingTeamProp();
                            if (isDetectingTeamProp) {
                                team_prop_location = "right";
                            }else {
                                team_prop_location = "left";
                            }
                            telemetry.addData("Team Prop Location ", team_prop_location);
                            telemetry.update();

                            camera.stopCamera();
                        }
                )
                .turn(Math.toRadians(20))
                .forward(39)
                .strafeRight(5)
                .addTemporalMarker(
                        () -> {
                            // measure the color
                            double red = color.red();
                            double blue = color.blue();
                            if (red>blue) {
                                alliance = "red";
                            } else if (red<blue) {
                                alliance = "blue";
                            }
//                            telemetry.addData("Color Red ", red);
//                            telemetry.addData("Color Blue ", blue);
                            telemetry.addData("Alliance ", alliance);

                            telemetry.update();
                            color.close();
                        }
                )
                .build();

        // Detect team prop position
        drive.setPoseEstimate(Trajectory_FindTeamProp_AtCenter.start());
        drive.followTrajectorySequence(Trajectory_FindTeamProp_AtCenter);

        if (team_prop_location.equals("")){
            drive.followTrajectorySequence(Trajectory_FindTeamProp_AtRight);
        }else {
            // Move forward for 15 inch
            drive.followTrajectorySequence(InitialTrajectory);
        }

        // Spike Mark Randomization Task
        switch (team_prop_location) {
            case "center":
                drive.followTrajectorySequence(Trajectory_TeamProp_Center);
                break;
            case "right":
                drive.followTrajectorySequence(Trajectory_TeamProp_Right);
                break;
            case "left":
                drive.followTrajectorySequence(Trajectory_TeamProp_Left);
                break;
        }
//
//        // set position at the center of the tape landmark
//        if (alliance.equals("red")) {
//            drive.setPoseEstimate(Red);
//            drive.followTrajectorySequence(Trajectory_AsRed_ToBackDrop);
//        }else if (alliance.equals("blue")) {
//            drive.setPoseEstimate(Blue);
//            drive.followTrajectorySequence(Trajectory_AsBlue_ToBackDrop);
//        }

        // Uncoment this if you are red alliance
//        drive.setPoseEstimate(Red);
//        drive.followTrajectorySequence(Trajectory_AsRed_ToBackDrop);

        // Uncoment this if you are blue alliance
        drive.setPoseEstimate(Blue);
        drive.followTrajectorySequence(Trajectory_AsBlue_ToBackDrop);

//        drive.setPoseEstimate(Red);
//        drive.followTrajectorySequence(Trajectory_AsRed_ToBackDrop);



        // go to backdrop to put yellow pixel

        if (isStopRequested()) {
            return;
        }

    }
}
