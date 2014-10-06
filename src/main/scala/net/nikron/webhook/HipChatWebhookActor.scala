package net.nikron
package hipactors

import akka.actor._
import spray.routing._
import spray.util.LoggingContext

class HipChatWebhookActor extends Actor with HipChatWebhook {
  def actorRefFactory = context
  val messageActor = context.actorSelection("/user/parser")

  def receive = runRoute(rootRoute)
}
