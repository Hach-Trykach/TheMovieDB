package dev.epool.themoviedb.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiMovieDetails(
    @SerialName("id")
    val id: Int,
    @SerialName("homepage")
    val homepage: String?,
    @SerialName("genres")
    val genres: List<ApiGenre>
)