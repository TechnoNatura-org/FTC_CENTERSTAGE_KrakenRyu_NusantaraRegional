package org.firstinspires.ftc.teamcode.drive.opmode;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.PID;

@Config
@Autonomous(group = "drive")
public class flipperPID extends LinearOpMode {

    private DcMotorEx flipper;
    public static PIDCoefficients PIDCoef = new PIDCoefficients(0.00001,0.000002,0.007);

    public static PIDFController PID_CONTROLLER = new PIDFController(PIDCoef);
    public static PID localPID = new PID(PIDCoef, 2.0);


    @Override
    public void runOpMode() throws  InterruptedException {
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());


        flipper = hardwareMap.get(DcMotorEx.class, "flipper");

        flipper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flipper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        flipper.setDirection(DcMotorSimple.Direction.REVERSE);
        localPID.setPID(PIDCoef);
        localPID.setTargetPos(140);
        telemetry.addData("Flipper Pos", "Initialised");
        telemetry.addData("Flippr Command", "Initialised");

        telemetry.addData("Flipper Target Pos", "Initialised");
        telemetry.addData("Lifter Local PID Target Pos", "Initialised");

        telemetry.addData("Last Error ", "Initialised");
        telemetry.addData("command ", "Initialised");
        waitForStart();
        localPID.setPID(PIDCoef);

        telemetry.addData("Status ", "Done Configuring PID!");


        while (opModeIsActive() && !isStopRequested()) {
            if (flipper.getCurrentPosition() <= 140 && flipper.getCurrentPosition() == 140 && localPID.getTargetPos() != 0) {
                sleep(2000);
                localPID.setTargetPos(-40);
            }else if (flipper.getCurrentPosition() >= -40 && flipper.getCurrentPosition() == -40 && localPID.getTargetPos() != 140) {
                sleep(2000);

                localPID.setTargetPos(140);
            }



            double command  = localPID.update(flipper.getCurrentPosition());

            flipper.setPower(command);
//
//            flipper.setPower(1);

            telemetry.addData("Flipper Pos", flipper.getCurrentPosition());
            telemetry.addData("Flippr Command", command);

            telemetry.addData("Flipper Target Pos", localPID.getTargetPos());
            telemetry.addData("Lifter Local PID Target Pos", localPID.getTargetPos());

            telemetry.addData("Last Error ", localPID.getError());
            telemetry.addData("command ", command);

            telemetry.update();
        }

    }
}
