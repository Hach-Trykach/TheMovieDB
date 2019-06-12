package dev.epool.themoviedb.api.models

interface ApiMovieHelper {

    private companion object {
        private const val IMAGE_PREFIX = "https://image.tmdb.org/t/p/"
    }

    enum class ImageSize { W92, W154, W185, W342, W500, W780, Original }

    val posterPath: String?

    val backdropPath: String?

    val releaseDate: String?

    fun getPosterUrl(size: ImageSize = ImageSize.W185) =
        posterPath?.let { "$IMAGE_PREFIX${size.name.toLowerCase()}$it" }

    fun getBackdropUrl(size: ImageSize = ImageSize.W185) =
        backdropPath?.let { "$IMAGE_PREFIX${size.name.toLowerCase()}$it" }

}