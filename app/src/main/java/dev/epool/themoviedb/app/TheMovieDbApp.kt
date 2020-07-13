package dev.epool.themoviedb.app

import android.app.Application
import android.content.Context
import com.squareup.picasso.Picasso
import dev.epool.themoviedb.app.common.TMDViewModelProviderFactory

class TheMovieDbApp : Application() {

    companion object {

        fun from(context: Context) = context.applicationContext as TheMovieDbApp
    }

    lateinit var viewModelProviderFactory: TMDViewModelProviderFactory

    override fun onCreate() {
        super.onCreate()
        viewModelProviderFactory = TMDViewModelProviderFactory(this)
        Picasso.get().setIndicatorsEnabled(BuildConfig.DEBUG)
    }
}