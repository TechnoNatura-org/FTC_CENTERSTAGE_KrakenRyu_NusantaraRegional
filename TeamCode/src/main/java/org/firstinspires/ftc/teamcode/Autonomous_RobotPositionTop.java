package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.PID;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous(name="Autonomous_AtCenter", group = "IDDESQ")
public class Autonomous_RobotPositionTop extends LinearOpMode {

    private SampleMecanumDrive drive;
    private String alliance = "";
    private String team_prop_location = ""; // left, center, right

    private DcMotor intake = null;
    private DcMotor flipper = null;

    private DcMotor lifter1 = null;
    private DcMotor lifter2 = null;

    private ColorSensor color;

    public static PIDCoefficients LIFTERS_PIDC = new PIDCoefficients(0.1,0,0);
    public static PID LifterPID = new PID(LIFTERS_PIDC);


    @Override
    public void runOpMode() {
        VisionPortal visionPortal;
        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .enableLiveView(true)
                .setAutoStopLiveView(false)
                .build();

//        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        drive = new SampleMecanumDrive(hardwareMap);

        flipper = hardwareMap.get(DcMotor.class, "flipper");
        intake = hardwareMap.get(DcMotor.class, "intake");

//        color = hardwareMap.get(ColorSensor.class, "Color");

        Pose2d TopRed = new Pose2d(11.47, -34.28);
        Pose2d BelowRed = new Pose2d(-36.53, -34.28);

//        drive.setPoseEstimate(new Pose2d(0,0, Math.toRadians(90)));

        TrajectorySequence untitled0 =  drive.trajectorySequenceBuilder(new Pose2d(11.75, 63.27, Math.toRadians(270.00)))
                .splineTo(new Vector2d(11.61, 35.68), Math.toRadians(270.00))
                .splineToLinearHeading(new Pose2d(24.56, 43.00, Math.toRadians(270.00)), Math.toRadians(-3.61))
                .splineToSplineHeading(new Pose2d(48.21, 35.26, Math.toRadians(180)), Math.toRadians(2.20))
//                                 .splineToLinearHeading(new Pose2d(18.65, 15.32, Math.toRadians(180)), Math.toRadians(182.68))
                .splineToLinearHeading(new Pose2d(1.48, 9.39, Math.toRadians(180)), Math.toRadians(185.44))
                .splineToLinearHeading(new Pose2d(-14.57, 9.39, Math.toRadians(180)), Math.toRadians(178.96))
                .splineToLinearHeading(new Pose2d(-42.16, 9.39, Math.toRadians(180)), Math.toRadians(178.96))
                .splineToLinearHeading(new Pose2d(-61.72, 36.11, Math.toRadians(180)), Math.toRadians(180.00))
                .splineToLinearHeading(new Pose2d(-58.06, 43.14,  Math.toRadians(180)), Math.toRadians(6.34))
                .splineToLinearHeading(new Pose2d(-35.54, 9.39,  Math.toRadians(180)), Math.toRadians(2.39))
                .splineToLinearHeading(new Pose2d(-3.17, 9.39,  Math.toRadians(180)), Math.toRadians(-2.16))
                .splineToLinearHeading(new Pose2d(9.78, 9.39,  Math.toRadians(180)), Math.toRadians(-2.20))
                .splineToLinearHeading(new Pose2d(27.10, 9.39,  Math.toRadians(180)), Math.toRadians(-2.20))
                .splineToLinearHeading(new Pose2d(48.49, 37.37,  Math.toRadians(180)), Math.toRadians(0.00))
                .splineToLinearHeading(new Pose2d(62.85, 62.43,  Math.toRadians(180)), Math.toRadians(41.42))
                .build();

        drive.setPoseEstimate(untitled0.start());

        waitForStart();

        // Move forward for 15 inch

        drive.followTrajectory(drive.trajectoryBuilder(new Pose2d(0,0, Math.toRadians(270))).forward(30).build());
        visionPortal.stopStreaming();

        // put purple pixel
        intake.setPower(0.5);
        sleep(300);
        intake.setPower(0);

        // runs intake, take out the pixel

        // Runs color sensor
        // alliance = "blue"
//        if (color.red() > color.blue()) {
//            alliance = "red";
//        }else if (color.blue() > color.red()) {
//            alliance = "blue";
//        }
//
//        if (alliance.equals("blue")) {
//            TopRed = new Pose2d(TopRed.getX(), TopRed.getY() * -1);
//            BelowRed = new Pose2d(BelowRed.getX(), BelowRed.getY() * -1);
//        }

//        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate()).back(10).build());
//        drive.setPoseEstimate(TopRed);

        // set position of the robot
        // code below

//        drive.setPoseEstimate(untitled0.start());



        // drive.followTrajectory(drive.trajectoryBuilder(new Pose2d()).forward(30).build());


        if (isStopRequested()) return;
//        drive.followTrajectorySequence(untitled0);

    }
}
