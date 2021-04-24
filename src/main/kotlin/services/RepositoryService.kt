package services

import GITHUB_ACCESS_TOKEN
import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import model.GraphQLResponse
import model.GraphQLQuery

const val REPOSITORY_QUERY = """query(${'$'}owner: String!,${'$'}name: String!,${'$'}cursor: String){
  repository(owner: ${'$'}owner, name: ${'$'}name) {
    issues(states: [OPEN], first: 10, after: ${'$'}cursor, orderBy: {field: CREATED_AT, direction: DESC}) {
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

suspend fun fetchRepo(trackedRepository: String, endCursor: String? = null): GraphQLResponse {

    return client.post() {
        url("https://api.github.com/graphql")
        header("Content-Type", ContentType.Application.Json)
        header("Authorization", "bearer $GITHUB_ACCESS_TOKEN")

        val (owner, name) = trackedRepository.split("/")
        val variables = buildJsonObject {
            put("owner", owner)
            put("name", name)
            put("cursor", endCursor)
        }

        body = GraphQLQuery(query = REPOSITORY_QUERY, variables = variables)
    }
}