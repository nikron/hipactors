package net.nikron
package hipactors

import akka.io.IO
import akka.actor.{ActorSystem, Props}
import spray.can.Http

object HipActor extends App {
  implicit val system = ActorSystem()
  val listener = system.actorOf(Props[HipChatWebhookActor], "hipchatwebhook")
  val parser = system.actorOf(Props[ParserActor], "parser")
  val drunkestBot = system.actorOf(Props[DrunkBotActor], "drunkestbot")

  IO(Http) ! Http.Bind(listener, "0.0.0.0", 8080)
}
