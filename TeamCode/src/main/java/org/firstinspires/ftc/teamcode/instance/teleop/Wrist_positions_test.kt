package org.firstinspires.ftc.teamcode.instances.teleop

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.components.Arm_v2

@TeleOp(name = "Wrist test")
class Wrist_positions_test: LinearOpMode() {
	override fun runOpMode() {
		val arm = Arm_v2(this)
		val stopper = hardwareMap.get(Servo::class.java, "stopper")
		var left = 0.5
		var right = 0.5
		val half_way = 180.0
		arm.slide_r.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
		arm.slide_l.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
		arm.slide_r.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
		arm.slide_l.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
		arm.gear_r.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
		arm.gear_l.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
		arm.gear_r.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
		arm.gear_l.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
		telemetry.addData("PIDF calc", arm.pid_gear.directCalculate(arm.gear_r.position.toDouble()))
		waitForStart()
		while (opModeIsActive()) {
			if(gamepad1.triangle) {
				arm.gear_r.power = 0.5
				arm.gear_l.power = 0.5
			}else if(gamepad1.cross){
				arm.gear_r.power = -0.5
				arm.gear_l.power = -0.5
			}else if(gamepad1.circle){
				arm.gear_r.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
				arm.gear_l.power = -0.5
			} else {
				arm.gear_r.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
				arm.gear_l.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
				arm.gear_r.power = 0.0
				arm.gear_l.power = 0.0
			}
			arm.wrist_servos(left, right)
			arm.gear_angle()
			if (gamepad1.dpad_up){
				if(right != 1.0 && left != 1.0 && right + left < 1.0) {
					right += 0.01
					left += 0.01
				}
			} else if(gamepad1.dpad_down) {
				if(right != 0.0 && left != 0.0 && right + left >0.0){
					right -= 0.01
					left -= 0.01
				}
			}
			if (gamepad1.dpad_left) {
				if(right != 1.0 && left != 0.0) {
					right += 0.01
					left -= 0.01
				}
			} else if (gamepad1.dpad_right) {
				if(right != 0.0 && left != 1.0) {
					right -= 0.01
					left += 0.01
				}
			}
			if(gamepad1.a) {
				stopper.position = 0.0
			} else if (gamepad1.b) {
				stopper.position = 0.2
			} else if (gamepad1.y) {
				stopper.position = 0.5
			}
			telemetry.addData("right", right)
			telemetry.addData("left", left)
			telemetry.addData("Gear angle", arm.gear_angle())
			telemetry.update()
		}
	}
}
