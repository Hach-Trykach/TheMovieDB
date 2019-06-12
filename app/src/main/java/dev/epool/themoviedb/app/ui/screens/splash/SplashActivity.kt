package dev.epool.themoviedb.app.ui.screens.splash

import android.os.Bundle
import dev.epool.themoviedb.app.common.TMDBaseActivity
import dev.epool.themoviedb.app.ui.screens.home.HomeActivityArgs

class SplashActivity : TMDBaseActivity<SplashActivityArgs>() {

    override fun layoutId(): Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        HomeActivityArgs().launch(this)
        finishAffinity()
    }

}
