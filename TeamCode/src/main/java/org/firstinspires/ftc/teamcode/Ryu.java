/* Copyright (c) 2017 FIRST. All rights reserved.
*

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:
Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.
Neither the name of FIRST nor the names of its contributors may be used to endorse or
promote products derived from this software without specific prior written permission.
NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.drive.PID;

@TeleOp(name="Ryu", group="A")

public class Ryu extends OpMode
{
    public static PIDCoefficients LIFTERS_PIDC = new PIDCoefficients(0.1,0 ,0);
    public static PIDFController LIFTERS_PID_CONTROLLER = new PIDFController(LIFTERS_PIDC, 1, 1,1);


    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontleft = null;
    private DcMotor frontright = null;
    private DcMotor backright = null;
    private DcMotor backleft = null;

    private DcMotor intake = null;
    private DcMotor flipper = null;

    private DcMotor lifter1 = null;
    private DcMotor lifter2 = null;

    private Servo flipper2 = null;
    private Servo droneLauncher = null;
    private Servo pixelServo = null;

    private double defspeed = 0;

    public static PIDCoefficients Flipper_PIDCoef = new PIDCoefficients(0.00001,0.000002,0.007);
    public static PID flipper_PID = new PID(Flipper_PIDCoef, 2.0);

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        frontleft  = hardwareMap.get(DcMotor.class, "frontleft");
        frontright = hardwareMap.get(DcMotor.class, "frontright");
        backright  = hardwareMap.get(DcMotor.class, "backright");
        backleft = hardwareMap.get(DcMotor.class, "backleft");

        flipper = hardwareMap.get(DcMotor.class, "flipper");
        flipper2 = hardwareMap.get(Servo.class, "flipper2");

        droneLauncher = hardwareMap.get(Servo.class, "droneLauncher");
        pixelServo = hardwareMap.get(Servo.class, "pixelServo");

        intake = hardwareMap.get(DcMotor.class, "intake");

        lifter1 = hardwareMap.get(DcMotor.class, "lifter1");
        lifter2 = hardwareMap.get(DcMotor.class, "lifter2");

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        frontleft.setDirection(DcMotor.Direction.FORWARD);
        frontright.setDirection(DcMotor.Direction.REVERSE);
        backleft.setDirection(DcMotor.Direction.FORWARD);
        backright.setDirection(DcMotor.Direction.REVERSE);

        flipper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flipper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flipper.setDirection(DcMotor.Direction.REVERSE);

        lifter2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lifter1.setDirection(DcMotor.Direction.REVERSE);

//        lifter2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        //DRIVEBASE
        double slide = gamepad1.left_stick_x;
        double drive = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;

        defspeed = gamepad1.left_trigger > 0 ? 1.0 : 0.6;

        frontleft.setPower(Range.clip(turn + drive + slide, -1.0, 1.0) * defspeed);
        frontright.setPower(Range.clip(-turn + drive - slide, -1.0, 1.0) * defspeed);
        backright.setPower(Range.clip(-turn + drive + slide, -1.0, 1.0) * defspeed);
        backleft.setPower(Range.clip(turn + drive - slide, -1.0, 1.0) * defspeed);


        if (gamepad2.a){
            flipper_PID.enablePID();
            flipper_PID.setTargetPos(140);
        }else if (gamepad2.y){
            flipper_PID.enablePID();
            flipper_PID.setTargetPos(-140);
        }



        if (flipper_PID.isPIDEnabled()) {
            double flipperCommand = flipper_PID.update(flipper.getCurrentPosition());
            flipper.setPower(flipperCommand);
        }else {
            if (gamepad2.dpad_up) {
                flipper_PID.disablePID();
                flipper_PID.setTargetPos(0);
                flipper.setPower(0.4);
            }else if (gamepad2.dpad_down) {
                flipper_PID.disablePID();
                flipper_PID.setTargetPos(0);
                flipper.setPower(-0.4);
            }
        }

        if (gamepad1.right_bumper){
            intake.setPower(1.0);
        }else if (gamepad1.left_bumper){
            intake.setPower(-1.0);
        }else{
            intake.setPower(0.0);
        }

        //lifter
        if(gamepad2.right_stick_y > 0){
            lifter1.setPower(gamepad2.right_stick_y);
            lifter2.setPower(gamepad2.right_stick_y);
        }else if(gamepad2.right_stick_y < 0){
            lifter1.setPower(gamepad2.right_stick_y);
            lifter2.setPower(gamepad2.right_stick_y);
        }else{
            lifter1.setPower(0);
            lifter2.setPower(0);
        }

        if (gamepad2.b){
            flipper2.setPosition(0.10);
        }else if (gamepad2.x) {
            flipper2.setPosition(0.4);
        }

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.update();
        // telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}