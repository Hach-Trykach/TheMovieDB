package dev.epool.themoviedb.db

import dev.epool.themoviedb.db.entities.DbGenre
import dev.epool.themoviedb.db.entities.DbMovie
import java.util.*

object Constants {

    val movies = arrayOf(
        DbMovie(
            320288,
            "Dark Phoenix",
            6.2,
            Date(2019 - 1900, 5, 5),
            "The X-Men face their most formidable and powerful foe when one of their own, Jean Grey, starts to spiral out of control. During a rescue mission in outer space, Jean is nearly killed when she's hit by a mysterious cosmic force. Once she returns home, this force not only makes her infinitely more powerful, but far more unstable. The X-Men must now band together to save her soul and battle aliens that want to use Grey's new abilities to rule the galaxy.",
            "/kZv92eTc0Gg3mKxqjjDAM73z9cy.jpg",
            "/phxiKFDvPeQj4AbkvJLmuZEieDU.jpg",
            DbMovie.Category.NONE
        ),
        DbMovie(
            420817,
            "Aladdin",
            7.2,
            Date(2019 - 1900, 5, 22),
            "A kindhearted street urchin named Aladdin embarks on a magical adventure after finding a lamp that releases a wisecracking genie while a power-hungry Grand Vizier vies for the same lamp that has the power to make their deepest wishes come true.",
            "/3iYQTLGoy7QnjcUYRJy4YrAgGvp.jpg",
            "/v4yVTbbl8dE1UP2dWu5CLyaXOku.jpg",
            DbMovie.Category.POPULAR
        )
    )

    val genres = arrayOf(
        DbGenre(12, "Adventure"),
        DbGenre(14, "Fantasy")
    )

}