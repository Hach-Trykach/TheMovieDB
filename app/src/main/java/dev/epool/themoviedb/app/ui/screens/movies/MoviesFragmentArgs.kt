package dev.epool.themoviedb.app.ui.screens.movies

import dev.epool.themoviedb.app.common.FragmentArgs
import dev.epool.themoviedb.db.entities.DbMovie
import kotlinx.android.parcel.Parcelize

@Parcelize
class MoviesFragmentArgs(
    val category: DbMovie.Category
) : FragmentArgs<MoviesFragment>(MoviesFragment::class.java)