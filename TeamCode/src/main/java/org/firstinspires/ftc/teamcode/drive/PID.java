/*

 */

package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PID {
    private ElapsedTime timer;
    private double reference;
    private double tolerance= 0;

    private double Kp, Ki,Kd;
    public boolean isPIDRunning = false;

    private double error, derivative, integralSum, out;
    private double lastError = 0;

    private DcMotorEx motor = null;

    public PID(double kP, double kI, double kD, double tolerance) {
        timer = new ElapsedTime();
        Kp = kP;
        Ki = kI;
        Kd = kD;
        tolerance= tolerance;
    }

    public PID(double kP, double kI, double kD) {
        timer = new ElapsedTime();
        Kp = kP;
        Ki = kI;
        Kd = kD;
//        tolerance= tolerance;
    }
    public PID(PIDCoefficients PIDCo) {
        timer = new ElapsedTime();
        Kp = PIDCo.kP;
        Ki = PIDCo.kI;
        Kd = PIDCo.kD;
//        tolerance= tolerance;
    }
    public PID(PIDCoefficients PIDCo, double tolerance) {
        timer = new ElapsedTime();
        Kp = PIDCo.kP;
        Ki = PIDCo.kI;
        Kd = PIDCo.kD;
        tolerance= tolerance;
    }

    public void setMotor(DcMotorEx motor) {
        motor = motor;
    }

    public void setPID(PIDCoefficients PIDCo) {
        Kp = PIDCo.kP;
        Ki = PIDCo.kI;
        Kd = PIDCo.kD;
    }

    private void startPID() {
        timer = new ElapsedTime();
        lastError= 0;
    }

    public double getTargetPos() {
        return reference;
    }
    public void setTargetPos(double target) {
        reference = target;
        startPID();

        if (motor != null) {
            do {
                double motorPos = motor.getCurrentPosition();
                double command = update(motorPos);

                motor.setPower(command);
            } while (isPIDRunning);
        }
    }

    public double getError() {
        return lastError;
    }

    public double update(double position) {
        // obtain the encoder position

        // calculate the error
        error = reference - position;

        if (Math.abs(error) <= tolerance) {
            isPIDRunning = false;
            return 0;
        }else {
            if (isPIDRunning == false) {
                isPIDRunning = true;
            }
        }

        // rate of change of the error
        derivative = (error - lastError) / timer.seconds();

        // sum of all error over time
        integralSum = integralSum + (error * timer.seconds());

        out = (Kp * error) + (Ki * integralSum) + (Kd * derivative);

        lastError = error;
        // reset the timer for next time
        timer.reset();

        return out;
    }
}
