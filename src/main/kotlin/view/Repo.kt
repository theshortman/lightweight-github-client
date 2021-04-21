package view

import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.serialization.json.Json
import react.*
import react.dom.button
import react.dom.div
import services.*
import styled.css
import styled.styledDiv
import styled.styledSpan
import view.issueList


external interface RepoProps : RProps {
    var trackedRepo: TrackedRepository

}

val client = HttpClient(Js) {
    install(JsonFeature) {
        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            coerceInputValues = true
        })
    }
}


external interface RepoState : RState {
    var data: Data?
}


suspend fun fetchRepo(trackedRepo:TrackedRepository): GraphQLResponse {

    return client.post() {
        url("https://api.github.com/graphql")
        header("Content-Type", ContentType.Application.Json)
        header("Authorization", "bearer $GITHUB_ACCESS_TOKEN")
        val (owner, name) = trackedRepo
        body = Query(query = repositoryQuery(owner, name))
    }
}

@kotlin.js.ExperimentalJsExport
@JsExport
class Repo : RComponent<RepoProps, RepoState>() {
    override fun RepoState.init() {

        val mainScope = MainScope()
        mainScope.launch {
            val graphqlResponse = fetchRepo(props.trackedRepo)
            setState {
                data = graphqlResponse.data
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

            if (state.data == null){
            styledDiv {
                css{
                    display = Display.flex
                    justifyContent = JustifyContent.center
                }
                styledDiv {
                    css{
                        padding(80.px,0.px)
                    }
                    +"Loading..."
                }
            }
            }else {
                issueList {
                    issues = state.data?.repository?.issues?.nodes!!
                    totalCount = state.data?.repository?.issues?.totalCount!!
                }
            }
        }
    }
}

fun RBuilder.repo(handler: RepoProps.() -> Unit): ReactElement {
    return child(Repo::class) {
        this.attrs(handler)
    }
}