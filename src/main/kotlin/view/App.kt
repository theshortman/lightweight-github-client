package view

import react.dom.div
import kotlinx.coroutines.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.engine.js.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import kotlinx.css.*
import react.*
import services.*
import styled.css
import styled.styledDiv

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

