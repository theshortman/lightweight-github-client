package services

data class TrackedRepository(val owner: String, val name: String)

fun repositoryQuery(owner: String, name: String) = """{
  repository(owner: "$owner", name: "$name") {
    owner{
      login
    }
    name
    issues(states: [OPEN], first: 10, orderBy: {field: CREATED_AT, direction: DESC}) {
      nodes {
          id
          author {
            login
          }
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

