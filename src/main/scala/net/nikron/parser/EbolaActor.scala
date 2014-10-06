package net.nikron
package hipactors

import akka.actor.Actor
import com.imadethatcow.hipchat.common.caseclass.WebhookRoomMessage
import scala.util.Random

class EbolaActor extends Actor {
  val ebolIsm = Seq(
    "Don't worry, Ebola has a 50% survival rate!",
    "Don't worry, HipChat bots can't get Ebola!",
    "Don't worry, you probably don't have Ebola yet!",
    "There are less than 5000 cases of Ebola in Liberia.",
    "Even if you got Ebola you'd have at least a week to enjoy yourself!",
    "If you got Ebola you'd might never have to fix a commission job again!",
    "Health officials have the outbreak undercontrol.",
    "A vaccine for Ebola is coming shortly.",
    "Many patients with Ebola stop complaining about symptoms in less than a month!",
    "Don't worry you live in a first world country with a modern medical system!",
    "Don't worry, your health benefits are going to get better!",
    "Remember the Ebola virus only has a diameter of 80nm!",
    "As long you aren't in contact with anyone with Ebola, you'll be fine.",
    "Ebola can only live for up to 6 days on a surface.",
    "The chances that Ebola could become airbone are pretty small."
  )

  def receive = {
    case message: WebhookRoomMessage => if (message.item.message.message.toLowerCase().contains("ebola")) {
      context.actorSelection("/user/drunkestbot") ! new DrunkSpeak(Random.shuffle(ebolIsm).head)
    }
  }
}
