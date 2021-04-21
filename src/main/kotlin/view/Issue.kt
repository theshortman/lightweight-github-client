package view

import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.TextDecorationLine
import kotlinx.css.properties.border
import kotlinx.css.properties.textDecoration
import kotlinx.html.ATarget
import react.*
import react.dom.div
import services.Label
import styled.css
import styled.styledA
import styled.styledDiv
import styled.styledSpan
import kotlin.js.Date

external interface IssueProps : RProps {
    var title: String
    var url: String
    var number: Int
    var createdAt: String
    var labels: List<Label>

}

@OptIn(ExperimentalJsExport::class)
@JsExport
class Issue : RComponent<IssueProps, RState>() {
    override fun RBuilder.render() {
        div {
            styledA(props.url, target = ATarget.blank) {
                css {
                    display = Display.inlineBlock
                    textDecoration = TextDecoration.none
                    marginRight = 3.px
                    color = Color.black
                    hover { textDecoration(TextDecorationLine.underline) }
                }
                +props.title
            }

            for (label in props.labels) {
                styledSpan {
                    css {
                        display = Display.inlineBlock
                        padding(4.px)
                        marginRight = 5.px
                        borderRadius = 5.px
                        border(2.px,BorderStyle.solid,color = Color("#${label.color}"))
                    }
                    +label.name
                }
            }

        }
        styledDiv {
            css {
                color = Color.grey
            }
            val date = Date(props.createdAt)
            +"#${props.number} opened on ${date.toDateString().dropWhile { it != ' ' }}"
        }
    }
}

fun RBuilder.issue(handler: IssueProps.() -> Unit): ReactElement {
    return child(Issue::class) {
        this.attrs(handler)
    }
}