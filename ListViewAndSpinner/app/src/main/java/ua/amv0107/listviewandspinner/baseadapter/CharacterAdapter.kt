package ua.amv0107.listviewandspinner.baseadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import ua.amv0107.listviewandspinner.databinding.ItemCharacterBinding

typealias OnDeletePressedListener = (Character) -> Unit

class CharacterAdapter(
    private val characters: List<Character>,
    private val onDeletePressedListener: OnDeletePressedListener
): BaseAdapter(), View.OnClickListener {
    override fun getCount(): Int {
        // Общее кол-во элементов в списке
        return characters.size
    }

    override fun getItem(position: Int): Character {
        // Получить элемент по его позиции
        return characters[position]
    }

    override fun getItemId(position: Int): Long {
        // Получить идентификатор элемента по позиции типа Long
        return characters[position].id
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Преобразование элементов данных в элемент списка
        val binding =
            convertView?.tag as ItemCharacterBinding? ?:
            createBinding(parent.context)

        val character = getItem(position)
        binding.titleTextView.text = character.name
        binding.deleteImageView.tag = character
        binding.deleteImageView.visibility = if (character.isCustom) View.VISIBLE else View.GONE

        return binding.root
    }

    private fun createBinding(context: Context): ItemCharacterBinding {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(context))
        binding.deleteImageView.setOnClickListener(this)
        binding.root.tag = binding
        return binding
    }

    override fun onClick(v: View) {
        val character = v.tag as Character
        onDeletePressedListener.invoke(character)
    }
}