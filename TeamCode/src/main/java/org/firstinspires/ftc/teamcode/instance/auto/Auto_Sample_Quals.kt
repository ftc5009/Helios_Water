package org.firstinspires.ftc.teamcode.instance.auto

import ca.helios5009.hyperion.pathing.Point
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.instances.auto.Simple_events

class Auto_Sample_Quals (private val instance : LinearOpMode) {
    val eventListener = Simple_events(instance)
    fun run(timer: ElapsedTime) {
        val bot = Robot(instance, eventListener.listener, true)
        val linearadjust = 1.0
        bot.path.start(Point(11.0, 110.0 * linearadjust, "_init").setDeg(0.0))//start
        //.wait("initialized", "start_sample")
        .segment(Point(21.0,110.0 * linearadjust, "_set_gear").setTolerance(4.0)
                ,Point(18.0, 105.0 * linearadjust)
        )//place0
        //.wait("gear_set")
        .segment(Point(17.5,122.5 * linearadjust, "arm_up").setTolerance(5.0).setDeg(-45.0)
                ,Point(17.5,123.0 * linearadjust).setDeg(-45.0)
        )
        //.wait("up_arm")
        //.wait("dropped", "drop_sample")
        .segment(Point(28.0, 115.5, "_lift_down").setTolerance(4.0).setDeg(-5.0)
                ,Point(25.0, 119.0 * linearadjust, "_pick_up")
        )
        //.segment(Point(19.0,124.0 * linearadjust,-80.0, "drop_done"))
        //.wait("picked_up") //pickup 1
        .segment(Point(17.5,122.5 * linearadjust, "_arm_up").setTolerance(4.0).setDeg(-45.0)
                ,Point(17.5,123.0 * linearadjust).setDeg(-45.0)
        )
        //.wait("up_arm")
        //.wait("dropped", "drop_sample")
        .segment(Point(29.0, 130.5 * linearadjust, "_lift_down").setTolerance(4.0).setDeg(-10.0)
                ,Point(26.0, 126.5 * linearadjust, "_pick_up").setDeg(0.0)
        )
        //.segment(Point(19.0,124.0 * linearadjust,-80.0, "drop_done"))
        //.wait("picked_up") //pickup 2
        .segment(Point(17.0,123.0 * linearadjust, "_arm_up").setTolerance(4.0).setDeg(-38.0)
                ,Point(17.0,123.5 * linearadjust).setDeg(-38.0)
        )
        //.wait("_up_arm")
        //.wait("_dropped", "_drop_sample")
        if(timer.seconds() < 24.0) {
            bot.path.segment(Point(31.0,132.0 * linearadjust, "_lift_down").setTolerance(5.0).setDeg(10.0)
                        ,Point(28.5,127.0 * linearadjust, "_pick_up").setDeg(26.0)
            )
            //.wait("_picked_up") //pickup 3
            .segment(Point(17.5,122.5 * linearadjust, "arm_up").setTolerance(4.0).setDeg(-38.0)
                    ,Point(17.5,123.0 * linearadjust).setDeg(-38.0)
            )
            //.wait("_up_arm")
            //.wait("_dropped", "_drop_sample")
        }
        bot.path.segment(Point(62.0,115.0 * linearadjust, "_lift_down_final").setTolerance(12.0).setDeg(-80.0)
                ,Point(60.0, 90.0 * linearadjust, "_ascend").setTolerance(8.0).setDeg(-80.0)
                ,Point(60.0, 98.0 * linearadjust).setDeg(-80.0)
        )
        .end()
    }
}