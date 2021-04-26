package model

import kotlinx.serialization.Serializable

@Serializable
data class GraphQLResponse(val data: Data? = null, val errors: List<Error>? = null)

@Serializable
data class Error(val message: String)

@Serializable
data class Data(val repository: Repository? = null)

@Serializable
data class Repository(val issues: IssueConnection)

@Serializable
data class IssueConnection(
    val nodes: List<Issue>? = null,
    val totalCount: Int,
    val pageInfo: PageInfo
)

@Serializable
data class LabelConnection(
    val nodes: List<Label>? = null
)

interface Node {
    val id: String
}

@Serializable
data class PageInfo(val endCursor: String? = null, val hasNextPage: Boolean)

@Serializable
data class Issue(
    override val id: String,
    val title: String,
    val url: String,
    val number: Int,
    val createdAt: String,
    val labels: LabelConnection? = null,
    val comments: IssueCommentConnection
) : Node

@Serializable
data class IssueCommentConnection(val totalCount: Int)

@Serializable
data class Label(override val id: String, val name: String, val color: String) : Node

