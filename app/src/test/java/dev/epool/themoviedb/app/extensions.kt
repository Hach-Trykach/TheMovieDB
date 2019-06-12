package dev.epool.themoviedb.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.junit.Assert

fun <T> LiveData<T>.assertValues(vararg expectedValues: T, valuesTrigger: () -> Unit) {
    val actualValues = mutableListOf<T>()
    val observer = object : Observer<T> {
        override fun onChanged(value: T) {
            actualValues += value
            if (expectedValues.size == actualValues.size) {
                removeObserver(this)
                Assert.assertEquals(expectedValues.toList(), actualValues)
            }
        }
    }
    observeForever(observer)

    valuesTrigger()
}