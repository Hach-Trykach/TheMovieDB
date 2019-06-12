package dev.epool.themoviedb.app.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class TMDCoroutineViewModel : ViewModel(), CoroutineScope {

    private val job = Job()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    override val coroutineContext: CoroutineContext = Dispatchers.Main + job + exceptionHandler

    protected abstract fun handleError(throwable: Throwable)

    override fun onCleared() = job.cancel()

}