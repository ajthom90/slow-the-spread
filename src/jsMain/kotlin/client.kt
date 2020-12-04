import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window

fun main() {
	window.onload = {
		render(document.getElementById("root")) {
			child(Welcome::class) {
				attrs {
					name = "Kotlin/JS"
				}
			}
			child(Days::class) {
				attrs {
					days = 2
					times = 17.53
				}
			}
		}
	}
}
