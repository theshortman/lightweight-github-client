package services

data class TrackedRepository(val owner: String, val name: String)


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

