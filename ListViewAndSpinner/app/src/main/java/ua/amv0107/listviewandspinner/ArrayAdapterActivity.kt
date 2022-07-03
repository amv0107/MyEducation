package ua.amv0107.listviewandspinner

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import ua.amv0107.listviewandspinner.databinding.ActivityArrayAdapterBinding
import ua.amv0107.listviewandspinner.databinding.DialogAddCharacterBinding
import java.util.*

class ArrayAdapterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArrayAdapterBinding
    private lateinit var adapter: ArrayAdapter<Character>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArrayAdapterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListWithArrayAdapter()
        binding.addButton.setOnClickListener { onAddPressed() }
    }

    private fun onAddPressed() {
        val dialogBinding = DialogAddCharacterBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Create character")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") { d, which ->
                val name = dialogBinding.characterNameEditText.text.toString()
                if(name.isNotBlank()) {
                    createCharacter(name)
                }
            }
            .create()
        dialog.show()
    }

    private fun createCharacter(name: String) {
        val character = Character(
            id = UUID.randomUUID().toString(),
            name = name
        )
        adapter.add(character)
    }

    private fun setupListWithArrayAdapter() {
        val data = mutableListOf(
            Character(id = UUID.randomUUID().toString(), name = "Reptile"),
            Character(id = UUID.randomUUID().toString(), name = "Subzero"),
            Character(id = UUID.randomUUID().toString(), name = "Scorpion"),
            Character(id = UUID.randomUUID().toString(), name = "Ridden"),
            Character(id = UUID.randomUUID().toString(), name = "Smoke")
        )
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            data
        )

        binding.listView.adapter = adapter

        binding.listView.setOnItemClickListener { parent, view, position, id ->
            adapter.getItem(position)?.let {
                deleteCharacter(it)
            }
        }
    }

    private fun deleteCharacter(character: Character) {
        val listener = DialogInterface.OnClickListener { dialog, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                adapter.remove(character)
            }
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle("Delete character")
            .setMessage("Are you sure you want to delete this character ")
            .setPositiveButton("Delete", listener)
            .setNegativeButton("Cancel", listener)
            .create()
        dialog.show()
    }

    class Character(
        val id: String,
        val name: String
    ) {
        override fun toString(): String {
            return name
        }
    }
}