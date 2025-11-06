package com.example.dialogos

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.dialogos.R
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dialogos.databinding.ActivityMainBinding
import com.example.dialogos.databinding.DialogoPersonalizadoBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnSimpleAlert.setOnClickListener { showSimpleAlertDialog() }
        binding.btnOkCancel.setOnClickListener { showOkCancelAlertDialog() }
        binding.btnOkNoCancel.setOnClickListener { showOkNoCancelAlertDialog() }
        binding.btnListAlert.setOnClickListener { showListAlertDialog() }
        binding.btnSingleChoiceAlert.setOnClickListener { showSingleChoiceAlertDialog() }
        binding.btnMultiChoiceAlert.setOnClickListener { showMultiChoiceAlertDialog() }
        binding.btnCustomInput.setOnClickListener { showCustomInputAlertDialog() }
    }
    // Unicamente con "ok"
    private fun showSimpleAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("Simple Alert")
            .setMessage("This is a simple alert dialog.")
            .setPositiveButton("OK", null)
            .setIcon(R.drawable.cohete) // Imagen del cohete
            .show()
    }

    // "OK" y "Cancel"
    private fun showOkCancelAlertDialog() {
        AlertDialog.Builder(ContextThemeWrapper(this,R.style.MyAlertDialogTheme))
            .setTitle("Confirmation")
            .setMessage("Are you sure you want to proceed?")
            .setPositiveButton("OK") { dialog, which ->
                // Handle OK button click
                Toast.makeText(this, "OK clicked", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                // Handle Cancel button click
                Toast.makeText(this, "Cancel clicked", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    // "No" - "Cancel" - "OK"
    private fun showOkNoCancelAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("Confirmation")
            .setMessage("Are you sure you want to proceed?")
            .setPositiveButton("OK") { dialog, which ->
                // Handle OK button click
                Toast.makeText(this, "OK clicked", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { dialog, which ->
                // Handle No button click
                Toast.makeText(this, "No clicked", Toast.LENGTH_SHORT).show()
            }
            .setNeutralButton("Cancel") { dialog, which ->
                // Handle Cancel button click
                Toast.makeText(this, "Cancel clicked", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    // Lista de opciones
    private fun showListAlertDialog() {
        val items = arrayOf("Item 1", "Item 2", "Item 3") //este array podría venir cargado de un fichero o una BBDD.
        AlertDialog.Builder(this)
            .setTitle("List Alert")
            .setItems(items) { dialog, which ->
                // Handle item selection
                Toast.makeText(this, "Selected: ${items[which]}", Toast.LENGTH_SHORT).show()
            }

            .show()
    }

    // Lista de opciones con selección única
    private fun showSingleChoiceAlertDialog() {
        val items = arrayOf("Item 1", "Item 2", "Item 3")
        var checkedItem = -1 // No item selected initially
        AlertDialog.Builder(this)
            .setTitle("Single Choice Alert")
            .setSingleChoiceItems(items, checkedItem) { dialog, which -> // checkItem, numero del que selecciona
                checkedItem = which
            }
            .setPositiveButton("OK") { dialog, which ->
                // Handle selection
                Toast.makeText(this, "Selected: ${items[checkedItem]}", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    // Lista de opciones con selección múltiple
    private fun showMultiChoiceAlertDialog() {
        val items = arrayOf("Item 1", "Item 2", "Item 3")
        val selectedItems = booleanArrayOf(false, false, false) // Initially, no items are selected
        AlertDialog.Builder(this)
            .setTitle("Multi Choice Alert")
            .setMultiChoiceItems(items, selectedItems) { dialog, which, isChecked ->
                selectedItems[which] = isChecked
            }
            .setPositiveButton("OK") { dialog, which ->
                // Handle selections
                val selectedItemsList = mutableListOf<String>()
                for (i in selectedItems.indices) {
                    if (selectedItems[i]) {
                        selectedItemsList.add(items[i])
                    }
                }
                Toast.makeText(this, "Selected: $selectedItemsList", Toast.LENGTH_SHORT).show()
            }
            .show()
    }
    private fun showCustomInputAlertDialog() {
        val builder = AlertDialog.Builder(this)
        //val inflater = layoutInflater
        // val dialogLayout = inflater.inflate(R.layout.dialogo_personalizado, null)
        //val editText = dialogLayout.findViewById<EditText>(R.id.editTextData)
        val bindingDialog = DialogoPersonalizadoBinding.inflate(layoutInflater)
        val editText = bindingDialog.editTextData
        builder.setView(bindingDialog.root)
            //builder.setView(dialogLayout)
            .setPositiveButton("OK") { dialogInterface, i ->
                val inputText = editText.text.toString() // Para recibir datos
                // Process the input text
                Toast.makeText(this, "Input: $inputText", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }


}