package org.firstinspires.ftc.teamcode.util;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

public class CameraSubsystem {

    private AprilTagProcessor aprilTagProcessor;
    private AprilTagProcessor.Builder aprilTagProcessorBuilder;

    private TfodProcessor tfodProcessor;
    private TfodProcessor.Builder tfodProcessorBuilder;

    private static final String[] LABELS = {
            "teamprop_red",
            "teamprop_blue",
    };
    private VisionPortal visionPortal;

    public CameraSubsystem(HardwareMap hardwareMap) {

        tfodProcessorBuilder = new TfodProcessor.Builder();
        tfodProcessorBuilder.setModelFileName("model_efficientDet.tflite");
        tfodProcessorBuilder.setModelLabels(LABELS);
        tfodProcessorBuilder.setIsModelTensorFlow2(true);
        tfodProcessorBuilder.setIsModelQuantized(true);

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

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessors(aprilTagProcessor, tfodProcessor)
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .enableLiveView(true)
                .setAutoStopLiveView(false)


                .build();


    }

    public void resumeCamera() {
        visionPortal.resumeStreaming();
    }

    public void stopCamera() {
        visionPortal.stopStreaming();
    }

    public void stopTfod() {
        visionPortal.setProcessorEnabled(tfodProcessor, false);

    }
    public void stopApriltag() {
        visionPortal.setProcessorEnabled(aprilTagProcessor, false);

    }

    public List<AprilTagDetection> getAprilTagDetection() {
        return aprilTagProcessor.getDetections();
    }



    public boolean isDetectingTeamProp( ) {
        List<Recognition> currentRecognitions = tfodProcessor.getRecognitions();

        for (Recognition recognition : currentRecognitions) {
            String label = recognition.getLabel();

            if (label.equals("teamprop_red") || label.equals("teamprop_blue") ) {
                return true;

            }
        }

        return false;
    }

    public VisionPortal.CameraState getCameraState() {
       return visionPortal.getCameraState();
    }

    public void detectingTFoD(Telemetry telemetry) {
        List<Recognition> currentRecognitions = tfodProcessor.getRecognitions();

        for (Recognition recognition : currentRecognitions) {
            String label = recognition.getLabel();

            if (label.equals("teamprop_red") || label.equals("teamprop_blue") ) {
                double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
                double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;
                double width = recognition.getWidth();
                double height = recognition.getHeight();

                telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
                telemetry.addData("- Position", "%.0f / %.0f", x, y);
                telemetry.addData("- Size", "%.0f x %.0f", width, height);


            }
        }
    }
    public boolean isDetectingTeamProp(Telemetry telemetry ) {
        List<Recognition> currentRecognitions = tfodProcessor.getRecognitions();

        for (Recognition recognition : currentRecognitions) {
            String label = recognition.getLabel();
            telemetry.addData("Label ", label);

            if (label.equals("teamprop_red") || label.equals("teamprop_blue") ) {
                double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
                double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;
                double width = recognition.getWidth();
                double height = recognition.getHeight();

                telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
                telemetry.addData("- Position", "%.0f / %.0f", x, y);
                telemetry.addData("- Size", "%.0f x %.0f", width, height);

                if (width < height) {
                    return true;
                }

            }
        }

        return false;
    }
}
