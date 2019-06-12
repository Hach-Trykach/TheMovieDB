package dev.epool.themoviedb.app.common

import android.content.Intent

interface CustomLifeCycleListener {

    fun shown() {}

    fun customOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}

}