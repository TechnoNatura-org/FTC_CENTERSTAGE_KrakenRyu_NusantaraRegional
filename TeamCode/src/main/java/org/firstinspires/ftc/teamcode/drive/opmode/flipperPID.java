package org.firstinspires.ftc.teamcode.drive.opmode;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@Config
@Autonomous(group = "drive")
public class flipperPID extends LinearOpMode {

    private DcMotorEx flipper;
    public static PIDCoefficients FLIPPER_PID = new PIDCoefficients(0, 0, 0);
    public static PIDFController FLIPPER_PIDF = new PIDFController(FLIPPER_PID, 0.0);

    public static PIDFCoefficients FLIPPER_PID_CONTROLLER = new PIDFCoefficients();


    @Override
    public void runOpMode() throws  InterruptedException {
        flipper = hardwareMap.get(DcMotorEx.class, "flipper");
        int flipperIndex = ((DcMotorEx)flipper).getPortNumber();

        flipper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

//        flipper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        flipper.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, FLIPPER_PID_CONTROLLER);

        waitForStart();

        if (isStopRequested()) return;

        flipper.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(FLIPPER_PID.kP,FLIPPER_PID.kI,FLIPPER_PID.kD,0));

        flipper.setTargetPosition(-131);
        sleep(1000);
        flipper.setTargetPosition(0);

    }
}
