package view

import kotlinx.css.*
import model.Error
import react.*
import react.dom.div
import styled.css
import styled.styledDiv

external interface ErrorsProps : RProps {
    var errorMessages: List<String>

}

@ExperimentalJsExport
class ErrorsView : RComponent<ErrorsProps, RState>() {
    override fun RBuilder.render() {
            styledDiv {
                css {
                    display = Display.flex
                    justifyContent = JustifyContent.center
                }
                div {
                    +props.errorMessages.joinToString(" ")
                }
            }
    }
}

@ExperimentalJsExport
fun RBuilder.errorsView(handler: ErrorsProps.() -> Unit): ReactElement {
    return child(ErrorsView::class) {
        this.attrs(handler)
    }
}