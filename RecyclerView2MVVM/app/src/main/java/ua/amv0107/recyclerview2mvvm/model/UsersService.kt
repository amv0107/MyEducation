package ua.amv0107.recyclerview2mvvm.model

import com.github.javafaker.Faker
import ua.amv0107.recyclerview2mvvm.UserNotFoundExceptions
import ua.amv0107.recyclerview2mvvm.tasks.SimpleTask
import ua.amv0107.recyclerview2mvvm.tasks.Task
import java.util.*
import java.util.concurrent.Callable

typealias UsersListener = (users: List<User>) -> Unit

class UsersService {

    private var users = mutableListOf<User>()
    private var loader = false
    private val listeners = mutableSetOf<UsersListener>()

    fun loadUsers(): Task<Unit> = SimpleTask<Unit>(Callable {
        Thread.sleep(2000)
        val faker = Faker.instance()
        IMAGES.shuffle()
        users = (1..100).map {
            User(
                id = it.toLong(),
                name = faker.name().name(),
                company = faker.company().name(),
                photo = IMAGES[it % IMAGES.size]
            )
        }.toMutableList()
        loader = true
        notifyChanges()
    })

    fun getById(id: Long): Task<UserDetails> = SimpleTask<UserDetails>(Callable {
        Thread.sleep(2000)
        val user = users.firstOrNull { it.id == id } ?: throw UserNotFoundExceptions()
        return@Callable UserDetails(
            user = user,
            details = Faker.instance().lorem().paragraphs(3).joinToString("\n\n")
        )
    })

    fun deleteUser(user: User): Task<Unit> = SimpleTask<Unit>(Callable {
        /* Проходим по все элементам в списке users и сравниваем с user из входящего параметра нашей
        * функции, если не находим совпадений то присваиваем -1 */
        Thread.sleep(2000)
        val indexToDelete = users.indexOfFirst { it.id == user.id }
        if (indexToDelete != -1) // Если нашли пользователя
        {
            users = ArrayList(users)
            users.removeAt(indexToDelete)// то удаляем по индексу
            notifyChanges()
        }

    })

    fun moveUser(user: User, moveBy: Int): Task<Unit> = SimpleTask<Unit>(Callable {
        // user - кого перемещаем
        // moveBy - куда перемещаем. Если -1 - то вниз по индексу в списке, если 1 - то вверх
        Thread.sleep(2000)
        val oldIndex = users.indexOfFirst { it.id == user.id }
        if (oldIndex == -1) return@Callable
        val newIndex = oldIndex + moveBy
        if (newIndex < 0 || newIndex >= users.size) return@Callable // Если выходим за границы списка то стоп
        users = ArrayList(users)
        Collections.swap(users, oldIndex, newIndex) // Меняем элементы местами
        notifyChanges()
    })

    fun addListener(listener: UsersListener) {
        listeners.add(listener)
        if (loader)
            listener.invoke(users)
    }

    fun removeListener(listener: UsersListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges() {
        if (!loader) return
        listeners.forEach { it.invoke(users) }
    }

    companion object {
        private val IMAGES = mutableListOf(
            "https://images.unsplash.com/photo-1600267185393-e158a98703de?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NjQ0&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1579710039144-85d6bdffddc9?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0Njk1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1488426862026-3ee34a7d66df?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0ODE0&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1620252655460-080dbec533ca?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzQ1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1613679074971-91fc27180061?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzUz&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1485795959911-ea5ebf41b6ae?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzU4&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1545996124-0501ebae84d0?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzY1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/flagged/photo-1568225061049-70fb3006b5be?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0Nzcy&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1567186937675-a5131c8a89ea?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0ODYx&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1546456073-92b9f0a8d413?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0ODY1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800"
        )
    }
}