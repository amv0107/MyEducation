package ua.amv0107.recyclerview2mvvm.model

data class User(
    val id: Long,
    val photo: String,
    val name: String,
    val company: String
)

data class UserDetails(
    val user: User,
    val details: String
)
