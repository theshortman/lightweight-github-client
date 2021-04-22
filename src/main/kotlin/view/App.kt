package view

import kotlinx.css.*
import react.*
import styled.css
import styled.styledDiv
import trackedRepositories

//val client = HttpClient(Js) {
//    install(JsonFeature) {
//        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
//            prettyPrint = true
//            isLenient = true
//            ignoreUnknownKeys = true
//            coerceInputValues = true
//        })
//    }
//}
//
//
//external interface AppState : RState {
//    var data: Data?
//}
//
//
//suspend fun testGraphql(): GraphQLResponse {
//
//    return client.post() {
//        url("https://api.github.com/graphql")
//        header("Content-Type", ContentType.Application.Json)
//        header("Authorization", "bearer $GITHUB_ACCESS_TOKEN")
//        val (owner, name) = trackedRepositories.last()
//        body = Query(query = repositoryQuery(owner,name))
//    }
//}

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

