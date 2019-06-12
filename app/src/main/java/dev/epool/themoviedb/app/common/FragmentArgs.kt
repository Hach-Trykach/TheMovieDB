package dev.epool.themoviedb.app.common

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import dev.epool.themoviedb.app.common.Constants.ARGS_KEY_SUFFIX

abstract class FragmentArgs<T : Fragment>(
    private val fragmentClass: Class<T>
) : Parcelable {

    fun newInstance(): T = fragmentClass.newInstance().apply {
        arguments = Bundle().apply {
            putParcelable(fragmentClass.simpleName + ARGS_KEY_SUFFIX, this@FragmentArgs)
        }
    }

}