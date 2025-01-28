package org.firstinspires.ftc.teamcode.auto

import ca.helios5009.hyperion.misc.events.EventListener
import ca.helios5009.hyperion.pathing.Point
import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Robot

@Autonomous(name = "PID_Tuning_HA", group = "Test")
class PID_Tuning_HA: LinearOpMode() {
    val eventListener = EventListener()
    override fun runOpMode() {
        val dashboard = FtcDashboard.getInstance()
        telemetry = MultipleTelemetry(telemetry, dashboard.telemetry)
        val bot = Robot(this, eventListener, true)
        val path = bot.path
        waitForStart()
        path.start(Point(32.5,8.0).setDeg(0.0))

        path.segment(
            Point(32.5,8.0)
        )
        path.wait(10000.0)
        path.end("_")

    }
}
