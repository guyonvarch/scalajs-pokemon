import scalajs.concurrent.JSExecutionContext.Implicits.runNow
import scalajs.js.annotation.JSExport
import org.scalajs.dom

import dom.html
import dom.ext.Ajax

import scalatags.JsDom.all._

@JSExport
object Client extends{
  @JSExport
  def main(container: html.Div) = {
    val inputBox = input.render
    val outputBox = ul.render
    def update() = Ajax.get(s"http://localhost:8080/api/pokemon/${inputBox.value}").foreach{ xhr =>
      val pokemon = upickle.default.read[String](xhr.responseText)
      outputBox.innerHTML = ""
      outputBox.appendChild(
        li(
          pokemon
        ).render
      )
    }
    inputBox.onkeyup = (e: dom.Event) => update()
    update()
    container.appendChild(
      div(
        h1("Pokemon search"),
        inputBox,
        outputBox
      ).render
    )
  }
}
