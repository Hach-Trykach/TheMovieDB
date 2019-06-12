package dev.epool.themoviedb.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiMovie(
    @SerialName("vote_count")
    val voteCount: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("video")
    val video: Boolean,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("title")
    val title: String,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("poster_path")
    override val posterPath: String?,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("backdrop_path")
    override val backdropPath: String?,
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("overview")
    val overview: String,
    @SerialName("release_date")
    override val releaseDate: String?
) : ApiMovieHelper