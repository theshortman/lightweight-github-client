package view

import kotlinx.css.*
import react.*
import styled.css
import styled.styledDiv

@ExperimentalJsExport
class LoadingView : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.flex
                justifyContent = JustifyContent.center
            }
            styledDiv {
                css {
                    padding(80.px, 0.px)
                }
                +"Loading..."
            }
        }
    }
}

@ExperimentalJsExport
fun RBuilder.loadingView(): ReactElement {
    return child(LoadingView::class) {}
}