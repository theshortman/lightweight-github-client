package view

import TrackedRepository
import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import kotlinx.serialization.json.buildJsonObject
import react.*
import react.dom.button
import services.*
import styled.css
import styled.styledDiv
import styled.styledSpan

import kotlinx.serialization.json.*
import model.*
import model.Issue
import react.dom.div


external interface RepoProps : RProps {
    var trackedRepo: TrackedRepository

}

external interface RepoState : RState {
    var data: Data?
    var errors: List<Error>?
    var isLoading: Boolean
}

@ExperimentalJsExport
class Repo : RComponent<RepoProps, RepoState>() {

    override fun RepoState.init() {

        val mainScope = MainScope()
        mainScope.launch {
            val graphqlResponse = fetchRepo(props.trackedRepo)
            setState {
                data = graphqlResponse.data
                errors = graphqlResponse.errors
                isLoading = false
            }
        }
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                margin(2.em, LinearDimension.auto)
                maxWidth = 970.px
            }
            key = "${props.trackedRepo.owner}/${props.trackedRepo.name}"
            styledSpan {
                css {
                    margin(0.px, .1.rem)
                    fontWeight = FontWeight.bold
                }
                +"${props.trackedRepo.owner}/${props.trackedRepo.name}"
            }


            if (state.data == null && state.errors != null)  {
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
            } else {
                if (state.data?.repository != null) {
                    issueList {
                        issues = state.data?.repository?.issues?.nodes!!
                        totalCount = state.data?.repository?.issues?.totalCount!!
                    }

                    if (state.data?.repository?.issues?.pageInfo?.hasNextPage!!) {
                        styledDiv {
                            css {
                                display = Display.flex
                                justifyContent = JustifyContent.center
                            }

                            button {
                                attrs {
                                    disabled = state.isLoading
                                    onClickFunction = {
                                        setState {
                                            isLoading = true
                                        }
                                        val endCursor = state.data?.repository?.issues?.pageInfo?.endCursor
                                        val oldIssues = state.data?.repository?.issues?.nodes!!
                                        val mainScope = MainScope()
                                        mainScope.launch {
                                            val graphqlResponse = fetchRepo(props.trackedRepo, endCursor)
                                            val newIssues = graphqlResponse.data?.repository?.issues?.nodes!!
                                            val updatedIssue = mutableListOf<Issue>()
                                            updatedIssue.addAll(oldIssues)
                                            updatedIssue.addAll(newIssues)
                                            val newData = Data(
                                                Repository(
                                                    graphqlResponse.data?.repository?.owner,
                                                    graphqlResponse.data?.repository?.name,
                                                    issues = IssueConnection(
                                                        updatedIssue,
                                                        graphqlResponse.data?.repository?.issues?.totalCount,
                                                        graphqlResponse.data?.repository?.issues?.pageInfo
                                                    )
                                                )
                                            )


                                            setState {
                                                data = newData
                                                errors = graphqlResponse.errors
                                                isLoading = false
                                            }
                                        }
                                    }
                                }
                                +if (state.isLoading) "Loading..." else "Load"
                            }
                        }
                    }
                }
                if (state.errors != null) {
                    styledDiv {
                        css {
                            display = Display.flex
                            justifyContent = JustifyContent.center
                        }
                        div {
                            +"${state.errors?.joinToString(" ")}"
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalJsExport
fun RBuilder.repo(handler: RepoProps.() -> Unit): ReactElement {
    return child(Repo::class) {
        this.attrs(handler)
    }
}