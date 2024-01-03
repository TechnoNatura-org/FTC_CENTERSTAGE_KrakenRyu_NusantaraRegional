package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.PID;

@Config
@Autonomous(group = "drive")
public class lifterPID extends LinearOpMode {
    private VoltageSensor batteryVoltageSensor;


    private DcMotorEx lifter1;
    private DcMotorEx lifter2;

//    public static PIDFCoefficients LIFTERS_PID = new PIDFCoefficients(0, 0 ,0, 32767 / (6000 / 60 * 1680));
    public static PIDCoefficients LIFTERS_PIDC = new PIDCoefficients(0.1,0,0);
    public static PIDFController LIFTERS_PID_CONTROLLER = new PIDFController(LIFTERS_PIDC);

    public static PID localPID = new PID(LIFTERS_PIDC);


    @Override
    public void runOpMode() {
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        batteryVoltageSensor = hardwareMap.voltageSensor.iterator().next();

        lifter1 =hardwareMap.get(DcMotorEx.class, "lifter1");
        lifter2 =hardwareMap.get(DcMotorEx.class, "lifter2");

        lifter1.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status ", "Done Configuring!");

        lifter2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.addData("Status ", "Done Configuring Motors!");

        LIFTERS_PID_CONTROLLER.setOutputBounds(-1.0, 1.0);
        LIFTERS_PID_CONTROLLER.setInputBounds( 0, 4139);


        localPID.setPID(LIFTERS_PIDC);

        waitForStart();

        localPID.setPID(LIFTERS_PIDC);


        telemetry.addData("Status ", "Done Configuring PID!");


        while (opModeIsActive() && !isStopRequested()) {


            if (lifter2.getCurrentPosition() >= 3000 && LIFTERS_PID_CONTROLLER.getTargetPosition() == 3000 && LIFTERS_PID_CONTROLLER.getTargetPosition() != 0) {
                sleep(1000);
                LIFTERS_PID_CONTROLLER.setTargetPosition(0);
//                localPID.startPID();
                localPID.setTargetPos(0);
            }else if (lifter2.getCurrentPosition() <= 0 && LIFTERS_PID_CONTROLLER.getTargetPosition() == 0 && LIFTERS_PID_CONTROLLER.getTargetPosition() != 3000) {
                sleep(1000);
                LIFTERS_PID_CONTROLLER.setTargetPosition(3000);
//                localPID.startPID();
                localPID.setTargetPos(3000);
            }

            double command  = localPID.update(lifter2.getCurrentPosition());
//            double error = LIFTERS_PID_CONTROLLER.getLastError();
            command *= -1;

            lifter2.setPower(command);
            lifter1.setPower(command);

            telemetry.addData("Lifter2 Pos", lifter2.getCurrentPosition());
            telemetry.addData("Lifter Target Pos", LIFTERS_PID_CONTROLLER.getTargetPosition());
            telemetry.addData("Lifter Local PID Target Pos", localPID.getTargetPos());

            telemetry.addData("Last Error ", localPID.getError());
            telemetry.addData("command ", command);
            
            telemetry.update();
        }
    }


//    private calculatePID(currentPosition) {
//
//    }

}
