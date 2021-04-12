import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.input

external interface AppProps : RProps {
    var name: String
}

data class AppState(val name: String) : RState

@JsExport
class App(props: AppProps) : RComponent<AppProps, AppState>(props) {

    init {
        state = AppState(props.name)
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
                        AppState(name = (event.target as HTMLInputElement).value)
                    )
                }
            }
        }
    }
}
