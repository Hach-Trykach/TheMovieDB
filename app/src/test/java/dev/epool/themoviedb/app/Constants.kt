package dev.epool.themoviedb.app

import dev.epool.themoviedb.api.models.ApiMovie

object Constants {

    val apiMovies = listOf(
        ApiMovie(
            voteCount = 416,
            id = 320288,
            video = false,
            voteAverage = 6.1,
            title = "Dark Phoenix",
            popularity = 413.008,
            posterPath = "/kZv92eTc0Gg3mKxqjjDAM73z9cy.jpg",
            originalLanguage = "en",
            originalTitle = "Dark Phoenix",
            genreIds = listOf(878, 28),
            backdropPath = "/phxiKFDvPeQj4AbkvJLmuZEieDU.jpg",
            adult = false,
            overview = "The X-Men face their most formidable and powerful foe when one of their own, Jean Grey, starts to spiral out of control. During a rescue mission in outer space, Jean is nearly killed when she's hit by a mysterious cosmic force. Once she returns home, this force not only makes her infinitely more powerful, but far more unstable. The X-Men must now band together to save her soul and battle aliens that want to use Grey's new abilities to rule the galaxy.",
            releaseDate = "2019-06-05"
        )
    )

}
