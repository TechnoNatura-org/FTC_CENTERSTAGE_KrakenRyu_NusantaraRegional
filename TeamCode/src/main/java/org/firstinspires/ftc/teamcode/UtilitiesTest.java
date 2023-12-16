package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(group = "drive")
public class UtilitiesTest extends LinearOpMode {

    private SampleMecanumDrive drive;
    private DcMotorEx intake;
    private DcMotorEx lifter1;
    private DcMotorEx lifter2;
    private DcMotorEx flipper;

    @Override
    public void runOpMode() throws InterruptedException {
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        lifter1 = hardwareMap.get(DcMotorEx.class, "lifter1");
        lifter2 = hardwareMap.get(DcMotorEx.class, "lifter2");
        flipper = hardwareMap.get(DcMotorEx.class, "flipper");

        lifter1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


    }
}
