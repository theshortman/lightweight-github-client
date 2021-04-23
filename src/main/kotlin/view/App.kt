package view

import kotlinx.css.*
import react.*
import styled.css
import styled.styledDiv
import trackedRepositories

@ExperimentalJsExport
class App : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                position = Position.relative
                paddingTop = 2.em
                minHeight = 100.pct
            }
            for (repo in trackedRepositories) {
                repo { trackedRepo = repo }
            }
        }
    }
}

