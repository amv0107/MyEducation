package ua.amv0107.fragmentfromlistview.model

import java.io.Serializable

data class Cat(
    val id: Int,
    val name: String,
    val description: String
): Serializable {
    override fun toString(): String {
        return name
    }
}
