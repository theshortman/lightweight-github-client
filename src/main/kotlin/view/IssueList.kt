package view

import kotlinx.css.*
import kotlinx.css.properties.*
import react.*
import model.Issue
import styled.*


external interface IssueListProps : RProps {
    var issues: List<Issue>
    var totalCount: Int

}

@ExperimentalJsExport
class IssueListView : RComponent<IssueListProps, RState>() {
    override fun RBuilder.render() {
        styledUl {
            css {
                fontSize = .9.rem
                border(1.px, BorderStyle.solid, Color.lightSlateGrey)
                borderTopWidth = 0.px
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
                    issueView{
                        title = issue.title
                        url = issue.url
                        number = issue.number
                        createdAt = issue.createdAt
                        labels = issue.labels?.nodes ?: emptyList()
                        commentsTotalCount = issue.comments.totalCount
                    }
                }
            }
        }
    }
}

@ExperimentalJsExport
fun RBuilder.issueListView(handler: IssueListProps.() -> Unit): ReactElement {
    return child(IssueListView::class) {
        this.attrs(handler)
    }
}