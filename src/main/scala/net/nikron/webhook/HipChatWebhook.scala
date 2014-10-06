package net.nikron
package hipactors

import akka.actor.ActorSelection
import com.imadethatcow.hipchat.common.caseclass._
import spray.http._
import spray.httpx.unmarshalling.Unmarshaller
import spray.httpx.marshalling.Marshaller
import spray.routing._
import spray.json._

trait SprayJsonSupport {
  implicit def sprayJsonUnmarshallerConverter[T](reader: RootJsonReader[T]) =
    sprayJsonUnmarshaller(reader)
  implicit def sprayJsonUnmarshaller[T: RootJsonReader] =
    Unmarshaller[T](MediaTypes.`application/json`) {
      case x: HttpEntity.NonEmpty =>
        val json = JsonParser(x.asString(defaultCharset = HttpCharsets.`UTF-8`))
        jsonReader[T].read(json)
    }

  implicit def sprayJsonMarshallerConverter[T](writer: RootJsonWriter[T])(implicit printer: JsonPrinter = PrettyPrinter) =
    sprayJsonMarshaller[T](writer, printer)
  implicit def sprayJsonMarshaller[T](implicit writer: RootJsonWriter[T], printer: JsonPrinter = PrettyPrinter) =
    Marshaller.delegate[T, String](ContentTypes.`application/json`) { value =>
      val json = writer.write(value)
      printer(json)
    }
}
object SprayJsonSupport extends SprayJsonSupport

object WebhookMessageJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val HCFileFormats = jsonFormat3(HCFile)
  implicit val FromLinksFormats = jsonFormat1(FromLinks)
  implicit val FromFormats = jsonFormat4(From)
  implicit val MentionItemLinks = jsonFormat1(MentionLinks)
  implicit val MentionItemFormats = jsonFormat4(MentionItem)
  implicit val RoomsItemLinksFormats = jsonFormat3(RoomsItemLinks)
  implicit val RoomsItemFormats = jsonFormat3(RoomsItem)
  implicit val WebhookMessageFormats = jsonFormat6(WebhookMessage)
  implicit val WebhookMessageItemFormats = jsonFormat2(WebhookRoomMessageItem)
  implicit val WebhookRoomMessageFormat = jsonFormat4(WebhookRoomMessage)
}

trait HipChatWebhook extends HttpService {
  val messageActor: ActorSelection
  def rootRoute = drunkRoute

  import WebhookMessageJsonSupport._
  def drunkRoute = path("drunk") {
    post {
      entity(as[WebhookRoomMessage]) { message =>
        messageActor ! message
        complete("")

      }
    }
  }
}
