package dev.epool.themoviedb.app.common

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import dev.epool.themoviedb.app.R
import dev.epool.themoviedb.app.extensions.arguments

abstract class TMDBaseActivity<AA : ActivityArgs<*>> : AppCompatActivity() {

    protected var arguments: AA? = null

    @LayoutRes
    abstract fun layoutId(): Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layoutId()?.let {
            setContentView(it)

            findViewById<Toolbar>(R.id.toolbar)?.let { toolbar ->
                setSupportActionBar(toolbar)
                toolbar.setNavigationOnClickListener { onBackPressed() }
            }
        }

        arguments = arguments()
    }

}