package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp(name="r2045_main",  group="drive")
public class r2045_main extends LinearOpMode {

    private DcMotorEx frontright = null;
    private DcMotorEx frontleft = null;
    private DcMotorEx rearright = null;
    private DcMotorEx rearleft = null;
    private double defspeed = 0;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode(){

        telemetry.addData("Status", "Initialized");


        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        frontleft  = hardwareMap.get(DcMotorEx.class, "frontLeft_motor");
        frontright = hardwareMap.get(DcMotorEx.class, "frontRight_motor");
        rearright  = hardwareMap.get(DcMotorEx.class, "rearRight_motor");
        rearleft = hardwareMap.get(DcMotorEx.class, "rearLeft_motor");


        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        frontleft.setDirection(DcMotor.Direction.FORWARD);
        frontright.setDirection(DcMotor.Direction.REVERSE);
        rearleft.setDirection(DcMotor.Direction.FORWARD);
        rearright.setDirection(DcMotor.Direction.REVERSE);

        telemetry.clearAll();

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        waitForStart();

        while (opModeIsActive()) {
            //DRIVEBASE
            double slide = -gamepad1.left_stick_x;
            double drive = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;

            defspeed = gamepad1.left_trigger > 0 ? 1.0 : 0.6;

            frontleft.setPower(Range.clip(turn + drive + slide, -1.0, 1.0) * defspeed);
            frontright.setPower(Range.clip(-turn + drive - slide, -1.0, 1.0) * defspeed);
            rearright.setPower(Range.clip(-turn + drive + slide, -1.0, 1.0) * defspeed);
            rearleft.setPower(Range.clip(turn + drive - slide, -1.0, 1.0) * defspeed);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            // telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
        }
    }


}