package org.firstinspires.ftc.teamcode.components

import ca.helios5009.hyperion.core.PIDFController
import ca.helios5009.hyperion.hardware.HyperionMotor
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo
import java.util.HashMap
import java.util.concurrent.atomic.AtomicReference
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.reflect.typeOf

class Arm_v2(private val instance: LinearOpMode) {

    val safety = 0.1

    val pid_gear = PIDFController(0.072, 0.0, 0.005, 0.0)
    val pid_slide = PIDFController(0.8,0.0,0.005,0.2)

    val gear_degrees_ticks = (100.0*384.5)/16.0/360.0
    var cur_gear_target  = 0.0
    val distance_limit = 10.0
    val gear_ratio = 10.0/14.0
    val slide_inches_ticks = 384.5*25.4/120.0
    var cur_slide_target = 0.0

    val gear_r = HyperionMotor(instance.hardwareMap, "GR")
    val gear_l = HyperionMotor(instance.hardwareMap, "GL")
    val slide_l = HyperionMotor(instance.hardwareMap, "SR")
    val slide_r = HyperionMotor(instance.hardwareMap, "SL")

    val left_wrist = instance.hardwareMap.get(Servo::class.java, "Left_Wrist")
    val right_wrist = instance.hardwareMap.get(Servo::class.java, "Right_Wrist")
    val intake_1 = instance.hardwareMap.get(CRServo::class.java, "Intake_1")
    val intake_2 = instance.hardwareMap.get(CRServo::class.java,"Intake_2")

    init {
        gear_r.motor.direction = DcMotorSimple.Direction.FORWARD
        gear_l.motor.direction = DcMotorSimple.Direction.REVERSE
        slide_l.motor.direction = DcMotorSimple.Direction.FORWARD
        slide_r.motor.direction = DcMotorSimple.Direction.REVERSE
    }
    fun init_auto() {
        slide_l.motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        slide_l.motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        slide_r.motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        slide_r.motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        gear_r.motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        gear_r.motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        gear_l.motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        gear_l.motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

        val gear_start_pos = 0.0

        slide_target.set(0.0)
        gear_target.set(gear_start_pos)
        cur_gear_target = gear_start_pos

        //wrist_servos(0.25,0.25)
        intake_1.power = 0.0
        intake_2.power = 0.0
    }
    fun init_teleOp() {
        gear_target.set(0.0)
        cur_gear_target = gear_target.get()
        slide_target.set(0.0)
        intake_1.power = 0.0
        intake_2.power = 0.0
    }
    fun slide_height() : Double {
        return slide_l.position.toDouble()/slide_inches_ticks
    }
    fun gear_angle() : Double {
        return gear_r.position.toDouble()/gear_degrees_ticks
    }
    fun go_to_target(gear_is_on:Boolean = true, slide_is_on:Boolean = true) {
        var gear_output = 0.0
        var slide_output = 0.0

        val maxSlideDist = if (gear_target.get() == 90.0) {
            35.0
        } else {
            distance_limit / cos(gear_target.get() * PI / 180.0)
        }

        val min_dist_at_angle = HashMap<Double, Double>()
        min_dist_at_angle.put(100.0, 20.3)

        val min_gear_angle = acos(distance_limit / max(slide_target.get(), 0.1)) / PI * 180.0
        if (gear_target.get() != cur_gear_target && slide_height() < maxSlideDist) {
            cur_gear_target = gear_target.get()
        } else if (slide_target.get() != cur_slide_target && gear_angle() < min_gear_angle) {
            cur_slide_target = slide_target.get()
        } else if(gear_target.get() != cur_gear_target && slide_target.get() != cur_slide_target) {
            cur_slide_target = slide_target.get()
        }

        instance.telemetry.addData("Error Gear", min(maxSlideDist, cur_slide_target) - slide_height())
        instance.telemetry.addData("Direct Calc Gear", pid_gear.directCalculate(max(min_gear_angle.toDouble(), cur_gear_target.toDouble()) - gear_angle().toDouble()))
        instance.telemetry.addData("Type", distance_limit::class.simpleName)
        instance.telemetry.addData("Type2", max(slide_target.get(), 0.1)::class.simpleName)
        instance.telemetry.addData("Type3", (PI * 180.0)::class.simpleName)

        slide_output = pid_slide.directCalculate(min(maxSlideDist.toDouble(), cur_slide_target.toDouble()) - slide_height().toDouble())
        gear_output = pid_gear.directCalculate(max(min_gear_angle.toDouble(), cur_gear_target.toDouble()) - gear_angle().toDouble(), instance.telemetry)

        instance.telemetry.addData("Error Gear", gear_output)

        slide_output *= if(slide_output > 0.4) {
            slide_l.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
            0.0
        } else if(slide_output < -0.6) {
            slide_l.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
            0.8
        } else {
            slide_l.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
            0.5
        }

        gear_output *= if(gear_output < 0.0){
            if(gear_angle() < 60.0){
                0.2 - slide_height()/100.0
            } else if(gear_angle() > 120.0) {
                0.8 + slide_height()/100.0
            } else {
                0.4 + slide_height()/150.0
            }
        } else {
            if(gear_angle() < 60.0){
                0.8 + slide_height()/100.0
            } else if(gear_angle() > 120.0) {
                0.2 - slide_height()/100.0
            } else {
                0.4 + slide_height()/150.0
            }
        }

        instance.telemetry.addData("Error Gear", gear_output)

        val gear_offset = (gear_r.position - gear_l.position).toDouble() / 200.0
        val slide_offset = (slide_l.position - slide_r.position).toDouble() / 200.0

        gear_r.setPowerWithTol(gear_output - gear_offset)
        gear_l.setPowerWithTol(gear_output + gear_offset)
        slide_l.setPowerWithTol(slide_output - slide_offset)
        slide_r.setPowerWithTol(slide_output + slide_output)

        instance.telemetry.addData("Error Gear", gear_output)

    }
    fun wrist_servos(left: Double, right: Double){
        left_wrist.position = min(left + safety, 0.9)
        right_wrist.position = min(right + safety, 0.9)
    }
    fun intake_servos(power: Double){
        intake_1.power = power
        intake_2.power = -power
    }
    enum class ArmState {
        CRUISE,
        LOW_BASKET,
        HIGH_BASKET,
        LOW_CHAMBER,
        LOW_CHAMBER_SCORE,
        HIGH_CHAMBER,
        HIGH_CHAMBER_SCORE,
        WALL_PICKUP,
        SUBMERSIBLE,
        ENDED
    }
    companion object {
        val gear_target = AtomicReference(0.0)
        val slide_target = AtomicReference(0.0)
        val grav = AtomicReference(false)
        val free_slide = AtomicReference(false)
    }
}