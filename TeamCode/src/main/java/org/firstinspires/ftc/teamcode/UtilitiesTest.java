package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import java.util.List;

@Autonomous(group = "drive")
public class UtilitiesTest extends LinearOpMode {

    private SampleMecanumDrive drive;
    private DcMotorEx intake;
    private DcMotorEx lifter1;
    private DcMotorEx lifter2;
    private DcMotorEx flipper;

    private ColorSensor color;

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        intake = hardwareMap.get(DcMotorEx.class, "intake");

//        intake = hardwareMap.get(DcMotorEx.class, "intake");
//        lifter1 = hardwareMap.get(DcMotorEx.class, "lifter1");
//        lifter2 = hardwareMap.get(DcMotorEx.class, "lifter2");
//        flipper = hardwareMap.get(DcMotorEx.class, "flipper");
//
//        lifter1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

//        color = hardwareMap.get(ColorSensor.class, "Color");
        telemetry.addData("leftFront ", "Initialised");
        telemetry.addData("leftRear ", "Initialised");
        telemetry.addData("rightFront ", "Initialised");
        telemetry.addData("rightRear ", "Initialised");

        // Wait for the Play button to be pressed
        waitForStart();

        // While the Op Mode is running, update the telemetry values.

        sleep(2000);
        telemetry.clearAll();
        double mode = -1;
        while (opModeIsActive()) {

            if (gamepad1.a) {
                mode = 0;

            }else if (gamepad1.b) {
                mode= 1;
            }else if (gamepad1.y) {
                mode=2;

            }else if (gamepad1.x) {
                mode = 3;

            }

            List<Double> Motors = drive.getWheelPositions();
            if (mode==0) {
                drive.setMotorPowers(1,0,0,0);

            }else if (mode==1.0) {
                drive.setMotorPowers(0,1,0,0);
            }else if (mode==2.0) {
                drive.setMotorPowers(0,0,1,0);

            }else if (mode==3.0) {
                drive.setMotorPowers(0,0,0,1);

            }
            telemetry.addData("leftFront ", Motors.get(0));
            telemetry.addData("leftRear ", Motors.get(1));
            telemetry.addData("rightFront ", Motors.get(2));
            telemetry.addData("rightRear ", Motors.get(3));

            telemetry.update();
//
//            sleep(1000);
//            telemetry.addData("leftFront ", Motors.get(0));
//            telemetry.update();
//
//
//            drive.setMotorPowers(0,1,0,0);
//            telemetry.addData("leftRear ", Motors.get(1));
//            telemetry.update();
//
//            sleep(1000);
//
//            telemetry.addData("leftRear ", Motors.get(1));
//            telemetry.update();
//
//            telemetry.addData("rightFront ", Motors.get(2));
//            telemetry.update();
//
//            drive.setMotorPowers(0,0,1,0);
//            sleep(1000);
//
//            telemetry.addData("rightFront ", Motors.get(2));
//            telemetry.update();
//
//            drive.setMotorPowers(0,0,0,1);
//            telemetry.addData("rightRear ", Motors.get(3));
//            telemetry.update();
//
//            sleep(1000);
//            drive.setMotorPowers(0,0,0,0);
//            telemetry.update();
        }

    }
}
