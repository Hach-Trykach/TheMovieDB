package dev.epool.themoviedb.app.common

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import dev.epool.themoviedb.app.common.Constants.ARGS_KEY_SUFFIX

abstract class ActivityArgs<T : Activity>(
    private val activityClass: Class<T>
) : Parcelable {

    fun launch(activity: Activity, vararg animatedPairViews: Pair<View, String>) {
        if (animatedPairViews.isEmpty()) {
            activity.startActivity(intent(activity))
        } else {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                *animatedPairViews
            )
            activity.startActivity(intent(activity), options.toBundle())
        }
    }

    private fun intent(activity: Activity): Intent = Intent(activity, activityClass)
        .putExtra(activityClass.simpleName + ARGS_KEY_SUFFIX, this)

}