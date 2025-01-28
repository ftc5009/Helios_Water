package org.firstinspires.ftc.teamcode.instance.auto

import ca.helios5009.hyperion.pathing.Point
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.instances.auto.Simple_events_Chambers

class Auto_Chamber_Quals (private val instance : LinearOpMode) {
    val eventListener = Simple_events_Chambers(instance)
    fun run(timer: ElapsedTime) {
        val bot = Robot(instance, eventListener.listener, true)
        val linearadjust = 1.0
        bot.path.start(Point(11.0, 63.0 * linearadjust, "_init").setDeg(0.0))//start
            .segment(
                Point(15.0, 63.0 * linearadjust, "_start_sample").setTolerance(6.0),
                Point(28.0, 63.0 * linearadjust, "_set_gear")
            )
            //.wait("gear_set")
            .segment(
                Point(36.0, 63.0, "_first_chamber").setTolerance(4.0),
                Point(39.5, 63.0 * linearadjust).setTolerance(3.5),
                Point(36.5, 63.0 * linearadjust)
            )
            //.wait("first_chamber_ready")
            //.wait("first_score", "first_score_chamber")
            .segment(
                Point(32.5, 63.0 * linearadjust),
                Point(25.0, 40.0 * linearadjust).setTolerance(6.0).setDeg(-65.0),
                Point(52.0, 33.0 * linearadjust).setTolerance(6.0).setDeg(-100.0),
                Point(12.0, 28.0 * linearadjust, "ready_arm").setTolerance(6.0).setDeg(-179.0),
                Point(26.0, 25.0 * linearadjust).setTolerance(4.0).setDeg(-179.0),
                Point(24.0, 25.0 * linearadjust, "drop_arm").setDeg(-179.0)
            )
            //.wait("picked_up")
            .segment(
                Point(20.0, 27.0).setTolerance(4.0).setDeg(-90.0),
                Point(18.0, 30.0, "arm_up").setDeg(-90.0)
            )
            .segment(
                Point(24.0, 63.0 * linearadjust).setTolerance(4.0),
                Point(36.0, 73.0 * linearadjust, "chamber_up").setTolerance(4.0)//was x 38
                ,
                Point(34.0, 70.0 * linearadjust)
            )// x was 36
            //.wait("chamber_ready")
            //.wait("first_score", "first_score_chamber")
            .segment(
                Point(25.0, 63.0 * linearadjust, "_arm_off").setTolerance(4.0),
                Point(39.0, 30.0 * linearadjust).setTolerance(6.0).setDeg(-85.0),
                Point(50.0, 20.0 * linearadjust).setTolerance(6.0).setDeg(-100.0),
                Point(12.0, 28.0 * linearadjust, "_ready_arm").setTolerance(6.0).setDeg(-135.0),
                Point(26.0, 25.0 * linearadjust).setTolerance(4.0).setDeg(-179.0),
                Point(24.0, 25.0 * linearadjust, "_drop_arm").setDeg(-179.0)
            )
            //.wait("picked_up")
            .segment(Point(13.0, 26.0 * linearadjust, "_arm_off").setDeg(90.0))
            .end()

        /*.segment(Point(30.0, 55.0 * linearadjust, -179.0, "_lift_arm"))
        //.wait("chamber_ready")
        //.wait("score", "score_chamber")
        .segment(Point(30.0, 18.0 * linearadjust, -75.0)
                ,Point(43.0, 10.0 * linearadjust, -90.0).setTolerance(6.0)
                ,Point(10.0, 18.0 * linearadjust, -90.0).setTolerance(6.0)
                ,Point(13.0, 26.0 * linearadjust, -90.0, "_drop_arm")
        )
        /*path.segment(Point(33.0,69.0 * linearadjust,0.0, "arm_up"))
        path.wait(1000.0)
        path.wait("up_arm")
        path.wait("dropping", "drop")
        path.segment(Point(33.0,69.0 * linearadjust,0.0, "drop_done"))
        path.wait("ahh")
        path.segment(Point(28.0, 69.0 * linearadjust, 0.0, "lift_down"))
        path.segment(Point(15.0, 25.0 * linearadjust, 0.0))*/

         */
    }
}