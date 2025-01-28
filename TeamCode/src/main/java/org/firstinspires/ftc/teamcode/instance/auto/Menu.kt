package org.firstinspires.ftc.teamcode.instance.auto

import ca.helios5009.hyperion.misc.events.EventListener
import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.auto.PID_Tuning_F
import org.firstinspires.ftc.teamcode.components.Selector
import org.firstinspires.ftc.teamcode.tunning.PID_Tuning_Diagon

@Autonomous(name = "Menu", group = "Autos")
class Menu : LinearOpMode() {
    override fun runOpMode() {
        val dashboard = FtcDashboard.getInstance()
        telemetry = MultipleTelemetry(telemetry, dashboard.telemetry)
        val s = Selector(this)
        val timer = ElapsedTime()
        while(opModeInInit() && Selector.selectors.entries[s.selector] != Selector.selectors.DONE) {
            s.select()
            s.scroll()
            telemetry.addData("Path: ", Selector.paths.entries[s.path_index])
            telemetry.addData("Selected Path: ", s.path_name)
            telemetry.addData("Delay(ms): ", s.delay)
            telemetry.addData("Selector: ", Selector.selectors.entries[s.selector])
            telemetry.update()
        }

        waitForStart()
        timer.reset()
        sleep(s.delay)

        if(s.path_name == Selector.paths.AUTO_SAMPLE) {
            Auto_Sample_Quals(this).run(timer)
        } else if(s.path_name == Selector.paths.AUTO_CHAMBER) {
            Auto_Chamber_Quals(this).run(timer)
        }
    }

}
