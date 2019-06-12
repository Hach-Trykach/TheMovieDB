package dev.epool.themoviedb.app.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import dev.epool.themoviedb.app.extensions.arguments

abstract class TMDBaseFragment<FA : FragmentArgs<*>> : Fragment() {

    protected var arguments: FA? = null

    @LayoutRes
    abstract fun layoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId(), container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments = arguments()
    }

}