package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.util.CameraSubsystem;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous(name="Autonomous_RobotPositionFarFromBackdrop", group = "IFR2024")
public class Autonomous_RobotPositionFarFromBackdrop extends LinearOpMode {

    private SampleMecanumDrive drive;
    private String alliance = "";
    private String team_prop_location = ""; // left, center, right

    private DcMotor intake = null;
    private DcMotor flipper = null;

    // Servo Subsystems
    private Servo flipper2 = null;
    private Servo pixelServo = null;

    // DC Motor subsystem
    private DcMotor lifter1 = null;
    private DcMotor lifter2 = null;

    // color sensor
    private ColorSensor color;

//    public static PIDCoefficients LIFTERS_PIDC = new PIDCoefficients(0.1,0,0);
//    public static PID LifterPID = new PID(LIFTERS_PIDC);

    CameraSubsystem camera;

    @Override
    public void runOpMode() {
        camera = new CameraSubsystem(hardwareMap);

        drive = new SampleMecanumDrive(hardwareMap);
        pixelServo = hardwareMap.get(Servo.class, "pixelServo");

//        flipper = hardwareMap.get(DcMotor.class, "flipper");
//        flipper2 = hardwareMap.get(Servo.class, "flipper2");

//        intake = hardwareMap.get(DcMotor.class, "intake");

        color = hardwareMap.get(ColorSensor.class, "Color");

//        Pose2d Red = new Pose2d(-36.53, -34.28, Math.toRadians(90));
//        Pose2d Blue = new Pose2d(-36.53, 34.28, Math.toRadians(270));
        Pose2d Red = new Pose2d(-37.0, -61, Math.toRadians(90));
        Pose2d Blue = new Pose2d(-37.0, 61, Math.toRadians(270));

        Pose2d RedNear = new Pose2d(6, -62, Math.toRadians(90));
        Pose2d BlueNear = new Pose2d(6, 62, Math.toRadians(270));


        telemetry.addData("Color Red ", "Initialised");
        telemetry.addData("Color Blue ", "Initialised");

        TrajectorySequence InitialTrajectory = drive.trajectorySequenceBuilder(new Pose2d(0,0, Math.toRadians(90)))
                .forward(43)
                .strafeLeft(2)
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
                            telemetry.addData("Color Red ", red);
                            telemetry.addData("Color Blue ", blue);
                            telemetry.addData("Alliance ", alliance);

                            telemetry.update();
                            color.close();
                        }
                )
                .build();

        TrajectorySequence Trajectory_TeamProp_Center = drive.trajectorySequenceBuilder(InitialTrajectory.end())
                .back(1)
                .strafeLeft(13)
                .addTemporalMarker(()->{
                    // put the pixel
                    pixelServo.setPosition(1);
                })
                .waitSeconds(1)
                .strafeRight(13)
                .back(32)
                .build();
        TrajectorySequence Trajectory_TeamProp_Right = drive.trajectorySequenceBuilder(InitialTrajectory.end())
                .back(7)
                .strafeRight(1)
                 .addTemporalMarker(()->{
                    // put the pixel
                    pixelServo.setPosition(1);
                })
                .waitSeconds(1)
                .strafeLeft(4)
                .back(32)
                .build();

        TrajectorySequence Trajectory_TeamProp_Left = drive.trajectorySequenceBuilder(InitialTrajectory.end())
                .strafeLeft(3)
                .back(7)
                .turn(Math.toRadians(90))
                .forward(2)
                .addTemporalMarker(()->{
                    // put the pixel
                    pixelServo.setPosition(1);
                })
                .waitSeconds(1)
                .back(2)
                .turn(Math.toRadians(-90))
                .strafeRight(3)

                .back(32)
                .build();



        waitForStart();

        telemetry.addData("Camera Status ", "Waiting for streaming state");

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
                                camera.stopCamera();

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
                .back(5)
                .build();

        TrajectorySequence Trajectory_AsRed_ToBackDrop = drive.trajectorySequenceBuilder(Red)
//                .strafeLeft(8)
//                .forward(14)
//                .splineToLinearHeading(new Pose2d(-31.16, -9.84, Math.toRadians(0)), Math.toRadians(-1.83))
//                .splineToLinearHeading(new Pose2d(-6.37, -9.84, Math.toRadians(0)), Math.toRadians(0.00))
//                .splineToLinearHeading(new Pose2d(9.84, -9.84, Math.toRadians(0)), Math.toRadians(-2.20))
//                .splineToLinearHeading(new Pose2d(29, -9.84, Math.toRadians(0)), Math.toRadians(-2.20))
//                .splineToLinearHeading(new Pose2d(36.29, -34.47, Math.toRadians(0)), Math.toRadians(-2.20))
//                .splineToLinearHeading(new Pose2d(51, -36, Math.toRadians(0)), Math.toRadians(-2.20))
//                .addTemporalMarker(() -> {
//                    intake.setPower(1);
//                })
//                .waitSeconds(0.5)
//                .addTemporalMarker(() -> {
//                    intake.setPower(0);
//                })
//                .splineToLinearHeading(new Pose2d(46.90, -12.32, Math.toRadians(0)), Math.toRadians(0.00))
//                .splineToLinearHeading(new Pose2d(70, -15, Math.toRadians(0)), Math.toRadians(0))

                // THROUGH SEMPIT
                .strafeRight(80)

                .build();
        TrajectorySequence Trajectory_AsBlue_ToBackDrop = drive.trajectorySequenceBuilder(Blue)
                // THROUGH SEMPIT
//                .splineToLinearHeading(new Pose2d(-34, 39, Math.toRadians(90)), Math.toRadians(0))
//                .splineToLinearHeading(new Pose2d(-20, 39, Math.toRadians(90)), Math.toRadians(0))
//                .splineToLinearHeading(new Pose2d(-10, 34, Math.toRadians(90)), Math.toRadians(0))
//
//                .splineToLinearHeading(new Pose2d(11, 40, Math.toRadians(90)), Math.toRadians(0))
//                .splineToLinearHeading(new Pose2d(29, 40, Math.toRadians(90)), Math.toRadians(0))

                // THROUGH BAWAH
                .strafeLeft(80)
              .build();

        TrajectorySequence Trajectory_AsRed_ToBackstage = drive.trajectorySequenceBuilder(RedNear)
                .splineToLinearHeading(new Pose2d(20.75, -59, Math.toRadians(90)), Math.toRadians(0))

                .splineToLinearHeading(new Pose2d(40, -43, Math.toRadians(90)), Math.toRadians(0))

                .splineToLinearHeading(new Pose2d(51, -36, Math.toRadians(0)), Math.toRadians(-2.86))

//                .addTemporalMarker(() -> {
//                    intake.setPower(-1);
//                })
//                .waitSeconds(1)
//                .addTemporalMarker(() -> {
//                    intake.setPower(0);
//                })

                .splineToLinearHeading(new Pose2d(46.90, -60, Math.toRadians(0)), Math.toRadians(0.00))
                .splineToLinearHeading(new Pose2d(68, -64, Math.toRadians(0)), Math.toRadians(0))
                .build();

        TrajectorySequence Trajectory_AsBlue_ToBackstage = drive.trajectorySequenceBuilder(BlueNear)
                .splineToLinearHeading(new Pose2d(20.75, 59, Math.toRadians(270)), Math.toRadians(0))

                .splineToLinearHeading(new Pose2d(40, 43, Math.toRadians(270)), Math.toRadians(0))

                .splineToLinearHeading(new Pose2d(51, 36, Math.toRadians(0)), Math.toRadians(-2.86))

//                .addTemporalMarker(() -> {
//                    intake.setPower(-1);
//                })
//                .waitSeconds(1)
//                .addTemporalMarker(() -> {
//                    intake.setPower(0);
//                })

                .splineToLinearHeading(new Pose2d(46.90, 60, Math.toRadians(0)), Math.toRadians(0.00))
                .splineToLinearHeading(new Pose2d(68, 64, Math.toRadians(0)), Math.toRadians(0)).build();


        // Detect team prop position
        drive.setPoseEstimate(Trajectory_FindTeamProp_AtCenter.start());
        drive.followTrajectorySequence(Trajectory_FindTeamProp_AtCenter);

        if (team_prop_location.equals("")){
            drive.setPoseEstimate(Trajectory_FindTeamProp_AtRight.end());
            drive.followTrajectorySequence(Trajectory_FindTeamProp_AtRight);
        }

        // Move forward for 15 inch
        drive.followTrajectorySequence(InitialTrajectory);

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

        // set position at the center of the tape landmark
        if (alliance.equals("red")) {
            drive.setPoseEstimate(Red);
            drive.followTrajectorySequence(Trajectory_AsRed_ToBackDrop);
            drive.setPoseEstimate(RedNear);
            drive.followTrajectorySequence(Trajectory_AsRed_ToBackstage);
        }else if (alliance.equals("blue")) {
            drive.setPoseEstimate(Blue);
            drive.followTrajectorySequence(Trajectory_AsBlue_ToBackDrop);
            drive.setPoseEstimate(BlueNear);
            drive.followTrajectorySequence(Trajectory_AsBlue_ToBackstage);
        }

        // Uncomment this if you are red alliance
//        drive.setPoseEstimate(Red);
//        drive.followTrajectorySequence(Trajectory_AsRed_ToBackDrop);
//        drive.setPoseEstimate(RedNear);
//        drive.followTrajectorySequence(Trajectory_AsRed_ToBackstage);

        // Uncomment this if you are blue alliance
        drive.setPoseEstimate(Blue);
        drive.followTrajectorySequence(Trajectory_AsBlue_ToBackDrop);
        drive.setPoseEstimate(BlueNear);
        drive.followTrajectorySequence(Trajectory_AsBlue_ToBackstage);



//        drive.setPoseEstimate(Red);
//        drive.followTrajectorySequence(Trajectory_AsRed_ToBackDrop);



        // reach tape marker at the center.


        // put purple pixel using servo


        if (isStopRequested()) {

            return;
        }
//        drive.followTrajectorySequence(untitled0);

    }
}
