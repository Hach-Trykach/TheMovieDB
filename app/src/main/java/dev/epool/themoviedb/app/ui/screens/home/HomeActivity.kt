package dev.epool.themoviedb.app.ui.screens.home

import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import dev.epool.themoviedb.app.R
import dev.epool.themoviedb.app.common.CustomLifeCycleListener
import dev.epool.themoviedb.app.common.TMDBaseActivity
import dev.epool.themoviedb.app.extensions.fixTextCuttingOff
import dev.epool.themoviedb.app.extensions.transaction
import dev.epool.themoviedb.app.ui.screens.movies.MoviesFragmentArgs
import dev.epool.themoviedb.app.ui.screens.searchmovies.SearchMoviesFragmentArgs
import dev.epool.themoviedb.db.entities.DbMovie
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : TMDBaseActivity<HomeActivityArgs>() {

    companion object {
        private const val FRAGMENT_TAG_KEY = "TAG"
    }

    private val pages = mapOf(
        R.id.navigation_popular to { MoviesFragmentArgs(DbMovie.Category.POPULAR).newInstance() },
        R.id.navigation_top_rated to { MoviesFragmentArgs(DbMovie.Category.TOP_RATED).newInstance() },
        R.id.navigation_upcoming to { MoviesFragmentArgs(DbMovie.Category.UPCOMING).newInstance() },
        R.id.navigation_search to { SearchMoviesFragmentArgs().newInstance() }
    )
    private var lastShownFragment: Fragment? = null
    private var lastShownFragmentTag: String? = null

    override fun layoutId() = R.layout.activity_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNavigation.setOnNavigationItemSelectedListener {
            handleNavigation(it.itemId)
            true
        }
        bottomNavigation.fixTextCuttingOff()
        if (savedInstanceState == null) {
            handleNavigation(R.id.navigation_popular)
        } else {
            lastShownFragmentTag = savedInstanceState.getString(FRAGMENT_TAG_KEY)
            lastShownFragmentTag?.let { hideFragmentsExcept(it) }
        }
    }

    private fun hideFragmentsExcept(tag: String) {
        lastShownFragment = supportFragmentManager.findFragmentByTag(tag)
        lastShownFragment?.let { currentFragment ->
            supportFragmentManager.fragments
                .filter { currentFragment != it }
                .forEach { supportFragmentManager.transaction { hide(it) } }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState?.putString(FRAGMENT_TAG_KEY, lastShownFragmentTag)
        super.onSaveInstanceState(outState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        supportFragmentManager.fragments.forEach {
            if (it is CustomLifeCycleListener) {
                it.customOnActivityResult(requestCode, resultCode, data)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleNavigation(@IdRes itemId: Int) {
        val fragmentProvider = pages[itemId] ?: return
        val tag = "$itemId"
        val addedFragment = supportFragmentManager.findFragmentByTag(tag)
        supportFragmentManager.transaction {
            lastShownFragment?.let { hide(it) }
            lastShownFragment = addedFragment?.also { show(it) }
                ?: fragmentProvider().also { add(R.id.container, it, tag) }
            lastShownFragment?.let {
                if (it is CustomLifeCycleListener) it.shown()
            }
            lastShownFragmentTag = tag
        }
    }

}
