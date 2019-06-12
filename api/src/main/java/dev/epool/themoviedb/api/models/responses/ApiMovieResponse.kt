package dev.epool.themoviedb.api.models.responses

import dev.epool.themoviedb.api.models.ApiMovie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiMovieResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("total_results")
    val totalResults: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("results")
    val results: List<ApiMovie>
)