package org.firstinspires.ftc.teamcode.drive;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

@TeleOp(name="AprilTagTest",  group="drive")

public class AprilTagTest extends LinearOpMode {

    AprilTagProcessor aprilTagProcessor;
    AprilTagProcessor.Builder aprilTagProcessorBuilder;

    TfodProcessor tfodProcessor;
    TfodProcessor.Builder tfodProcessorBuilder;

    @Override
    public void runOpMode() {

        tfodProcessorBuilder = new TfodProcessor.Builder();
        tfodProcessorBuilder.setMaxNumRecognitions(10);  // Max. number of recognitions the network will return
        tfodProcessorBuilder.setUseObjectTracker(true);  // Whether to use the object tracker
        tfodProcessorBuilder.setTrackerMaxOverlap((float) 0.2);  // Max. % of box overlapped by another box at recognition time
        tfodProcessorBuilder.setTrackerMinSize(16);  // Min. size of object that the object tracker will track
        tfodProcessor = tfodProcessorBuilder.build();

//        aprilTagProcessor =
        aprilTagProcessorBuilder = new AprilTagProcessor.Builder();

        // Optional: specify a custom Library of AprilTags.
        aprilTagProcessorBuilder.setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary());   // The OpMode must have already created a Library.

        // Optional: set other custom features of the AprilTag Processor (4 are shown here).
        aprilTagProcessorBuilder.setDrawTagID(true);       // Default: true, for all detections.
        aprilTagProcessorBuilder.setDrawTagOutline(true);  // Default: true, when tag size was provided (thus eligible for pose estimation).
        aprilTagProcessorBuilder.setDrawAxes(true);        // Default: false.
        aprilTagProcessorBuilder.setDrawCubeProjection(true);        // Default: false.

        aprilTagProcessor = aprilTagProcessorBuilder.build();

        VisionPortal visionPortal;
        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessors(aprilTagProcessor, tfodProcessor)
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .enableLiveView(true)
                .setAutoStopLiveView(false)
                .build();



        AprilTagProcessor myAprilTagProcessor;
        List<AprilTagDetection> aprilTagDetections;  // list of all detections
        int myAprilTagIdCode;

        waitForStart();// ID code of current detection, in for() loop

        if (isStopRequested()) {
         visionPortal.setProcessorEnabled(aprilTagProcessor, false);
         visionPortal.setProcessorEnabled(tfodProcessor, false);

        }

        visionPortal.resumeStreaming();

        while (opModeIsActive()) {
            telemetry.addLine("Started!!");

// Get a list of AprilTag detections.
            aprilTagDetections = aprilTagProcessor.getDetections();

// Cycle through through the list and process each AprilTag.
            for (AprilTagDetection myAprilTagDetection : aprilTagDetections) {
                if (myAprilTagDetection.metadata != null) {  // This check for non-null Metadata is not needed for reading only ID code.
                    myAprilTagIdCode = myAprilTagDetection.id;
                    String aprilName = myAprilTagDetection.metadata.name;
//                    String aprilName = myAprilTagDetection.metadata.fieldOrientation;


                    telemetry.addData("AprilTag ID", myAprilTagIdCode);
                    telemetry.addData("AprilTag Name", aprilName);

                    // Now take action based on this tag's ID code, or store info for later action.

                }
            }

            telemetry.update();

        }

    }
}
