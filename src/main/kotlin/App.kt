import react.dom.div
import kotlinx.coroutines.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.engine.js.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import react.*
import services.Data
import services.GraphQLResponse
import services.Query
import services.repositoryQuery
import styled.css
import styled.styledDiv


//data class AppState(var name: String) : RState

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


external interface AppState : RState {
    var resp: GraphQLResponse?
}


suspend fun testGraphql(): GraphQLResponse {

    return client.post() {
        url("https://api.github.com/graphql")
        header("Content-Type", ContentType.Application.Json)
        header("Authorization", "bearer $GITHUB_ACCESS_TOKEN")
        body = Query(query = repositoryQuery("ktorio", "ktor"))
    }
}

@JsExport
class App : RComponent<RProps, AppState>() {

//    init {
//        state = AppState("Max")
//    }

    override fun AppState.init() {

        val mainScope = MainScope()
        mainScope.launch {
            val graphqlResponse = testGraphql()
            setState {
                resp = graphqlResponse
            }
        }
    }

    override fun RBuilder.render() {
        div {
            if (state.resp == null) {
                div {
                    +"Loading..."
                }
            } else {
                div {
                    +"${state.resp?.data?.repository?.name}"
                }
                val issues = state.resp?.data?.repository?.issues
                div {
                    + "${issues?.totalCount} Open"
                }
                for (issue in issues?.nodes!!) {
                    div {
                        +"${issue.title} #${issue.number} ${Date(issue.createdAt).toDateString()}"
                    }

                    if (issue.labels.nodes.isNotEmpty()){
                        for (label in issue.labels.nodes){
                            +label.name
                        }
                    }
                }
            }
        }
    }
}

