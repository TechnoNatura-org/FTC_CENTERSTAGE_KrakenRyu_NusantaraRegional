package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

@TeleOp(name="AprilTagTest",  group="drive")
public class AprilTagTest extends LinearOpMode {

    AprilTagProcessor aprilTagProcessor;
    AprilTagProcessor.Builder aprilTagProcessorBuilder;

    TfodProcessor tfodProcessor;
    TfodProcessor.Builder tfodProcessorBuilder;


    @Override
    public void runOpMode() {


    }
}
