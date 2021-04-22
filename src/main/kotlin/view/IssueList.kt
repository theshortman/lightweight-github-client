package view

import kotlinx.css.*
import kotlinx.css.properties.*
import react.*
import react.dom.button
import services.Issue
import styled.*


external interface IssueListProps : RProps {
    var issues: List<Issue>
    var totalCount: Int

}

@ExperimentalJsExport
class IssueList : RComponent<IssueListProps, RState>() {
    override fun RBuilder.render() {
        styledUl {
            css {
                fontSize = .9.rem
                border(1.px, BorderStyle.solid, Color.lightSlateGrey)
                borderRadius = 4.px
                listStyleType = ListStyleType.none
                padding(0.px)
            }
            styledLi {
                css {
                    position = Position.relative
                    borderTop(1.px, BorderStyle.solid, Color.lightGrey)
                    padding(1.em)
                }
                key = "issue-header"
                +"${props.totalCount} Open"
            }
            for (issue in props.issues) {
                styledLi {
                    css {
                        position = Position.relative
                        borderTop(1.px, BorderStyle.solid, Color.lightGrey)
                        padding(1.em)
                    }
                    key = issue.id
                    issue{
                        title = issue.title
                        url = issue.url
                        number = issue.number
                        createdAt = issue.createdAt
                        labels = issue.labels.nodes
                    }
                }
            }
        }
    }
}

@ExperimentalJsExport
fun RBuilder.issueList(handler: IssueListProps.() -> Unit): ReactElement {
    return child(IssueList::class) {
        this.attrs(handler)
    }
}