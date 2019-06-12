package dev.epool.themoviedb.app.ui.models

import android.os.Parcelable
import dev.epool.themoviedb.api.models.ApiVideo
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UiVideo(
    val id: String,
    val name: String,
    val key: String
) : Parcelable

fun List<ApiVideo>.apiToUiVideos() = map { with(it) { UiVideo(id, name, key) } }