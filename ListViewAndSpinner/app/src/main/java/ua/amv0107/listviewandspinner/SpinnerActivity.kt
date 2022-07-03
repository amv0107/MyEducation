package ua.amv0107.listviewandspinner

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import ua.amv0107.listviewandspinner.databinding.ActivitySpinnerBinding
import ua.amv0107.listviewandspinner.databinding.DialogAddCharacterBinding
import kotlin.random.Random

class SpinnerActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpinnerBinding

    val data = mutableListOf(
        Character(id = 1, name = "Reptile", isCustom = false),
        Character(id = 2, name = "Subzero", isCustom = false),
        Character(id = 3, name = "Scorpion", isCustom = false),
        Character(id = 4, name = "Ridden", isCustom = false),
        Character(id = 5, name = "Smoke", isCustom = false)
    )

    private lateinit var adapter: CharacterAdapterForSpinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpinnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner()

        binding.addButton.setOnClickListener { onAddPressed() }
    }

    private fun setupSpinner() {
        adapter = CharacterAdapterForSpinner(data) {
            deleteCharacter(it)
        }

        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val character = data[position]
                binding.characterInfoTextView.text = getString(R.string.character_info, character.name, character.id)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                binding.characterInfoTextView.text = ""
            }
        }
    }

    private fun deleteCharacter(character: Character) {
        val listener = DialogInterface.OnClickListener { dialog, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                data.remove(character)
                adapter.notifyDataSetChanged()
            }
        }
        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.delete_character)
            .setMessage(getString(R.string.delete_message, character.name))
            .setPositiveButton(R.string.delete, listener)
            .setNegativeButton(R.string.cancel, listener)
            .create()
        dialog.show()
    }

    private fun onAddPressed(){
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
            id = Random.nextLong(),
            name = name,
            isCustom = true
        )
        data.add(character)
     adapter.notifyDataSetChanged() }
}