package net.nikron
package hipactors

import akka.actor.Actor
import com.imadethatcow.hipchat.common.caseclass.WebhookRoomMessage
import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator
import com.luckycatlabs.sunrisesunset.dto.Location
import java.util.Calendar

class SunActor extends Actor {
  val calc = new SunriseSunsetCalculator(new Location("32.822601", "-96.790687"), "America/Chicago")

  def receive = {
    case message: WebhookRoomMessage => if (message.item.message.message == "When is the sunset?") {
      context.actorSelection("/user/drunkestbot") ! new DrunkSpeak("Sunset is at " + calc.getOfficialSunsetForDate(Calendar.getInstance()) + ".")
    }
  }
}
