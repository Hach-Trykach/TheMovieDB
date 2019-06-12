package dev.epool.themoviedb.app.extensions

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.ferfalk.simplesearchview.SimpleSearchView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.squareup.picasso.Picasso
import dev.epool.themoviedb.api.models.ApiGenre
import dev.epool.themoviedb.api.models.ApiMovie
import dev.epool.themoviedb.app.R
import dev.epool.themoviedb.app.TheMovieDbApp
import dev.epool.themoviedb.app.common.Constants.ARGS_KEY_SUFFIX
import dev.epool.themoviedb.app.ui.models.UiGenre
import dev.epool.themoviedb.db.entities.DbGenre
import dev.epool.themoviedb.db.entities.DbMovie
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


//region Activity Extensions

fun <T : Parcelable> AppCompatActivity.arguments(): T? =
    intent.getParcelableExtra(javaClass.simpleName + ARGS_KEY_SUFFIX)

inline fun <reified T : ViewModel> AppCompatActivity.viewModel(): T = ViewModelProviders.of(
    this,
    TheMovieDbApp.from(this).viewModelProviderFactory
)[T::class.java]

fun AppCompatActivity.openUrl(url: String) =
    startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))

//endregion

//region Fragment Extensions

fun <T : Parcelable> Fragment.arguments(): T? =
    arguments?.getParcelable(javaClass.simpleName + ARGS_KEY_SUFFIX)

inline fun <reified T : ViewModel> Fragment.viewModel(): T = ViewModelProviders.of(
    this,
    TheMovieDbApp.from(requireContext()).viewModelProviderFactory
)[T::class.java]

//endregion

//region View Extensions

fun ImageView.loadUrl(url: String?) =
    Picasso.get().load(url ?: "INVALID_URL").error(R.mipmap.ic_launcher).into(this)

fun CollapsingToolbarLayout.setTitleTextColor(@ColorInt textColor: Int) = with(textColor) {
    setCollapsedTitleTextColor(this)
    setExpandedTitleColor(this)
}

fun ChipGroup.addChipsWithText(texts: List<String>) {
    if (childCount > 0) return
    texts.forEachIndexed { index, text ->
        val chipGroup = LayoutInflater.from(context).inflate(R.layout.genre_chip, this) as ChipGroup
        val chip = chipGroup[index] as Chip
        chip.text = text
    }
}

fun RecyclerView.asPager() = LinearSnapHelper().attachToRecyclerView(this)

fun BottomNavigationView.fixTextCuttingOff() {
    val menuView = getChildAt(0) as BottomNavigationMenuView
    (0 until menuView.childCount).forEach { index ->
        val item = menuView.getChildAt(index) as BottomNavigationItemView
        val activeLabel = item.findViewById<View>(R.id.largeLabel)
        if (activeLabel is TextView) {
            activeLabel.setPadding(0, 0, 0, 0)
        }
    }
}

fun FragmentManager.transaction(transaction: FragmentTransaction.() -> Unit) {
    with(beginTransaction()) {
        transaction()
        commit()
    }
}

fun SimpleSearchView.customize() {
    findViewById<ImageButton>(com.ferfalk.simplesearchview.R.id.buttonBack).isVisible = false
    if (editText.layoutParams is ViewGroup.MarginLayoutParams) {
        val p = editText.layoutParams as ViewGroup.MarginLayoutParams
        p.marginStart += 16.px
        editText.requestLayout()
    }
}

val SimpleSearchView.editText: EditText
    get() = findViewById(com.ferfalk.simplesearchview.R.id.searchEditText)

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

//endregion

fun String.toDate(): Date? =
    if (isNotEmpty())
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .parse(this)
    else null

fun Date.format(): String = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault())
    .format(this)

fun List<ApiMovie>.apiToDbMovies(category: DbMovie.Category = DbMovie.Category.NONE) = map {
    with(it) {
        DbMovie(
            id,
            title,
            voteAverage,
            releaseDate?.toDate(),
            overview,
            posterPath,
            backdropPath,
            category
        )
    }
}.toTypedArray()

fun List<ApiGenre>.apiToDbGenres() = map { with(it) { DbGenre(id, name) } }

fun List<DbGenre>.dbToUiGenres() = map { with(it) { UiGenre(id, name) } }
