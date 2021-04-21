package services

data class TrackedRepository(val owner: String, val name: String)

fun repositoryQuery(owner: String, name: String, endCursor:String?=null) = """{
  repository(owner: "$owner", name: "$name") {
    owner{
      login
    }
    name
    issues(states: [OPEN], first: 10, after: $endCursor orderBy: {field: CREATED_AT, direction: DESC}) {
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

