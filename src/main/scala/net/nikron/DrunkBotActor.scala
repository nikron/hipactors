package net.nikron
package hipactors

import akka.actor.Actor
import scala.concurrent.ExecutionContext.Implicits.global
import com.imadethatcow.hipchat.rooms.RoomNotifier
import com.imadethatcow.hipchat.common.enums.Color

abstract class DrunkMessage
case class Drink() extends DrunkMessage
case class DrunkSpeak(message : String) extends DrunkMessage
case class StomachPump() extends DrunkMessage
case class SoberUp() extends DrunkMessage

class DrunkBotActor extends Actor {
  var alcoholLevel = 1

  val notifier = new RoomNotifier("AkrEOdaeUfaOJOL16C4IaLvi7QBV99N618ixbFTj")

  def chat(message : String) = {
      notifier.sendNotification("877122", "Drunkest Bot: " + message, Color.green)
  }

  def emote(message : String) = {
      notifier.sendNotification("877122", "Drunkest Bot " + message, Color.green)
  }

  def receive = {
    case DrunkSpeak(message) => chat(slur(message))
    case Drink => {
      emote("drinks a cold one.")
      alcoholLevel += 1
    }
    case StomachPump => alcoholLevel = 1
    case SoberUp => if (alcoholLevel > 1) {
      emote("sobers up a bit.")
      alcoholLevel -= 1
    }
  }

  def slur(message : String) : String = message.flatMap((c : Char) => (c + "")  * alcoholLevel)
}
