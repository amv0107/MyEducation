package ua.amv0107.recyclerview2mvvm.screens

import androidx.lifecycle.ViewModel
import ua.amv0107.recyclerview2mvvm.tasks.Task

class Event<T>(
    private val value: T
) {
    private var handler: Boolean = false

    fun getValue(): T? {
        if (handler) return null
        handler = true
        return value
    }
}

open class BaseViewModel: ViewModel() {

    private val task = mutableListOf<Task<*>>()

    override fun onCleared() {
        super.onCleared()
        task.forEach { it.cancel() }
    }

    fun <T> Task<T>.autoCancel() {
        task.add(this)
    }
}