package dev.epool.themoviedb.api.models.responses

import dev.epool.themoviedb.api.models.ApiVideo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiVideoResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("results")
    val results: List<ApiVideo>
)