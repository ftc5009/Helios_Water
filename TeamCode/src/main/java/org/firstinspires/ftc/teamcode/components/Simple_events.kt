package org.firstinspires.ftc.teamcode.instances.auto

import ca.helios5009.hyperion.misc.events.EventListener
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.Servo
import kotlinx.coroutines.delay
import org.firstinspires.ftc.teamcode.components.Arm_v2

class Simple_events (instance:LinearOpMode) {
    val listener = EventListener()
    val arm = Arm_v2(instance)
    val stopper = instance.hardwareMap.get(Servo::class.java, "stopper")

    init {
        arm.init_auto()
        listener.addListener("init") {
            Arm_v2.gear_target.set(50.0)
            stopper.position = 0.0
            Arm_v2.grav.set(true)
            Arm_v2.grav.set(false)
            arm.wrist_servos(-0.05,-0.05)
            for(i in 1..9) {
                arm.go_to_target()
                delay(50)
            }
            Arm_v2.grav.set(true)
            instance.telemetry.addData("Not Yet 0", Arm_v2.grav.get())
            instance.telemetry.update()
            "initialized"
        }
        listener.addListener("start_sample") {
            arm.wrist_servos(0.25,0.25)
            while(instance.opModeIsActive() || instance.opModeInInit()){
                arm.go_to_target(gear_is_on = !Arm_v2.grav.get())
            }
            Arm_v2.grav.set(false)
            "started"
        }
        listener.addListener("set_gear") {
            Arm_v2.grav.set(false)
            Arm_v2.gear_target.set(58.0)
            delay(600)
            arm.wrist_servos(0.25, 0.25)
            Arm_v2.gear_target.set(40.0)
            Arm_v2.slide_target.set(9.0)
            while(arm.gear_r.position / arm.gear_degrees_ticks < 30.0){
                delay(50)
            }
            Arm_v2.grav.set(false)
            Arm_v2.gear_target.set(29.0)
            delay(200)
            "gear_set"
        }
        listener.addListener("arm_up") {
            arm.intake_servos(0.0)
            Arm_v2.gear_target.set(24.0)
            Arm_v2.slide_target.set(15.0)
            delay(1000)
            arm.intake_servos(0.0)
            Arm_v2.slide_target.set(27.0)
            delay(200)
            Arm_v2.gear_target.set(35.0)
            while(arm.slide_r.position / arm.slide_inches_ticks < 19.0){
                delay(50)
            }
            Arm_v2.grav.set(true)
            delay(500)
            arm.wrist_servos(0.05, 0.05)
            "up_arm"
        }
        listener.addListener("drop_sample") {
            arm.intake_servos(-1.0)
            delay(900)
            arm.wrist_servos(0.5, 0.5)
            delay(200)
            arm.intake_servos(0.0)
            Arm_v2.slide_target.set(4.0)
            Arm_v2.gear_target.set(50.0)
            Arm_v2.grav.set(false)
            delay(1100)
            "dropped"
        }
        listener.addListener("lift_down") {
            Arm_v2.grav.set(false)
            Arm_v2.gear_target.set(100.0)
            "lift_ready"
        }
        listener.addListener("lift_down_final") {
            Arm_v2.grav.set(false)
            Arm_v2.gear_target.set(36.0)
            Arm_v2.slide_target.set(8.0)
            "_"
        }
        listener.addListener("pick_up") {
            Arm_v2.grav.set(true)
            //arm.wrist_servos(0.45,0.45)
            arm.intake_servos(1.0)
            delay(900)
            arm.intake_servos(0.8)
            Arm_v2.grav.set(false)
            Arm_v2.gear_target.set(40.0)
            delay(300)
            arm.intake_servos(0.0)
            "picked_up"
        }
        listener.addListener("ascend") {
            Arm_v2.gear_target.set(74.0)
            delay(1000)
            Arm_v2.grav.set(true)
            "_"
        }
    }
}