package view

import kotlinx.css.*
import react.*
import services.Repository
import styled.css
import styled.styledDiv
import styled.styledSpan


external interface RepoProps : RProps {
    var repository: Repository?

}

@OptIn(ExperimentalJsExport::class)
@JsExport
class Repo : RComponent<RepoProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                margin(2.em, LinearDimension.auto)
                maxWidth = 970.px
            }
            styledSpan {
                css {
                    margin(0.px, .1.rem)
                    fontWeight = FontWeight.bold
                }
                +"${props.repository?.owner?.login}/${props.repository?.name}"
            }

            issueList {
                issues = props.repository?.issues?.nodes!!
                totalCount = props.repository?.issues?.totalCount!!
            }
        }
    }
}

fun RBuilder.repo(handler: RepoProps.() -> Unit): ReactElement {
    return child(Repo::class) {
        this.attrs(handler)
    }
}