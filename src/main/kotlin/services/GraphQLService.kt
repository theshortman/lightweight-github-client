package services

import GITHUB_ACCESS_TOKEN
import TrackedRepository
import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import model.GraphQLResponse
import model.Query

const val REPOSITORY_QUERY = """query(${'$'}owner: String!,${'$'}name: String!,${'$'}cursor: String){
  repository(owner: ${'$'}owner, name: ${'$'}name) {
    owner{
      login
    }
    name
    issues(states: [OPEN], first: 10, after: ${'$'}cursor orderBy: {field: CREATED_AT, direction: DESC}) {
      nodes {
          id
          title
          number
          createdAt
          url
          labels(last: 5) {
            nodes {
                id
                name
                color
            }
          }
      }
      totalCount
      pageInfo {
        endCursor
        hasNextPage
      }
    }
  }
}"""

val client = HttpClient(Js) {
    install(JsonFeature) {
        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
            isLenient = true
            ignoreUnknownKeys = true
            coerceInputValues = true
        })
    }
}

suspend fun fetchRepo(trackedRepo: TrackedRepository, endCursor: String? = null): GraphQLResponse {

    return client.post() {
        url("https://api.github.com/graphql")
        header("Content-Type", ContentType.Application.Json)
        header("Authorization", "bearer $GITHUB_ACCESS_TOKEN")

        val (owner, name) = trackedRepo
        val variables = buildJsonObject {
            put("owner", owner)
            put("name", name)
            put("cursor", endCursor)
        }

        body = Query(query = REPOSITORY_QUERY, variables = variables)
    }
}