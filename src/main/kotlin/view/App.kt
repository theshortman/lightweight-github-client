package view

import GITHUB_ACCESS_TOKEN
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
import services.Data
import services.GraphQLResponse
import services.Query
import services.repositoryQuery
import styled.css
import styled.styledDiv


//data class view.AppState(var name: String) : RState

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
    var data: Data?
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
//        state = view.AppState("Max")
//    }

    override fun AppState.init() {

        val mainScope = MainScope()
        mainScope.launch {
            val graphqlResponse = testGraphql()
            setState {
                data = graphqlResponse.data
            }
        }
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                position = Position.relative
                paddingTop = 2.em
                minHeight = 100.pct
            }
            if(state.data==null){
                div{
                    +"Loading..."
                }
            }else{
                repo { repository = state.data?.repository }
            }

        }
    }
}

