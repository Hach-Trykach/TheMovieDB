package dev.epool.themoviedb.app.ui.models

import android.os.Parcelable
import dev.epool.themoviedb.api.models.ApiGenre
import dev.epool.themoviedb.db.entities.DbGenre
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UiGenre(
    val id: Int,
    val name: String
) : Parcelable

fun ApiGenre.apiToUiGenre() = UiGenre(id, name)

fun DbGenre.dbToUiGenre() = UiGenre(id, name)