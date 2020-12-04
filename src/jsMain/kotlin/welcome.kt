import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.input

external interface WelcomeProps : RProps {
	var name: String
}

data class WelcomeState(val name: String) : RState

@ExperimentalJsExport
@JsExport
class Welcome(props: WelcomeProps) : RComponent<WelcomeProps, WelcomeState>(props) {

	init {
		state = WelcomeState(props.name)
	}

	override fun RBuilder.render() {
		div {
			+"Hello, ${state.name}"
		}
		input {
			attrs {
				type = InputType.text
				value = state.name
				onChangeFunction = { event ->
					setState(
						WelcomeState(name = (event.target as HTMLInputElement).value)
					)
				}
			}
		}
	}
}

external interface DaysProps: RProps {
	var days: Int
	var times: Double
}

data class DaysState(val days: Int, val times: Double): RState

@ExperimentalJsExport
@JsExport
class Days(props: DaysProps): RComponent<DaysProps, DaysState>(props) {
	init {
		state = DaysState(props.days, props.times)
	}

	override fun RBuilder.render() {
		div {
			+"Hello Days"
		}
		input {
			attrs {
				type = InputType.number
				value = state.days.toString()
				onChangeFunction = { event ->
					setState(DaysState(days = 7, times = 47.2))
				}
			}
		}
	}
}
