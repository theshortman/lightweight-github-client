package model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

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
    override val nodes: List<Issue>,
    override val totalCount: Int,
    override val pageInfo: PageInfo
) : Collection

@Serializable
data class LabelConnection(
    override val nodes: List<Label>,
    override val totalCount: Int? = null,
    override val pageInfo: PageInfo? = null
) : Collection

interface Collection {
    val nodes: List<Node>
    val totalCount: Int?
    val pageInfo: PageInfo?
}

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
    val labels: LabelConnection
) : Node

@Serializable
data class Label(override val id: String, val name: String, val color: String) : Node

