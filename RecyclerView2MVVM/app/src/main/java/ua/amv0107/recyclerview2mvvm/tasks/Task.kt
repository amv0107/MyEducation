package ua.amv0107.recyclerview2mvvm.tasks

typealias Callback<T> = (T) -> Unit

interface Task<T> {
    // Выдача результата
    fun onSuccess(callback: Callback<T>): Task<T>

    // Выдача ошибки
    fun onError(callback: Callback<Throwable>): Task<T>

    // Отменить задачу
    fun cancel()

    // Дождаться результата синхронно
    fun await(): T

}