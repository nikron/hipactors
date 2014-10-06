package net.nikron
package hipactors

import akka.actor.{Actor, Props}
import akka.routing.ActorRefRoutee
import akka.routing.Router
import akka.routing.BroadcastRoutingLogic

class ParserActor extends Actor {
  val ebolaActor = context.actorOf(Props[EbolaActor], "ebola")
  val sunActor = context.actorOf(Props[SunActor], "sun")
  val router = {
    val routees = Vector(
      ActorRefRoutee(ebolaActor),
      ActorRefRoutee(sunActor)
    )
    Router(BroadcastRoutingLogic(), routees)
  }

  def receive = {
    case m => {
      router.route(m, sender())
    }
  }
}
