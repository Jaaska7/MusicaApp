package com.example.appmusicandroid.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.appmusicandroid.R
import com.example.appmusicandroid.ViewModel.CurrentViewModel

class ListaPlaylist : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory).get(CurrentViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_playlist)

        val button: Button = findViewById(R.id.btnYourButton)

        button.setOnClickListener {
            showSongsPopup()
        }

        getMusic()
    }

    private fun getMusic() {
        viewModel.musicList.observe(this) {
            if (it.size > 0) {
                println("lista " + it.size)
            }
        }
    }

    private fun showSongsPopup() {
        val songList = getLocalSongsList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, songList)
        val listView = ListView(this)
        listView.adapter = adapter
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Lista de Canciones Locales")
            .setView(listView)
            .setPositiveButton("Cerrar") { dialog, _ ->
                dialog.dismiss()
            }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun getLocalSongsList(): List<String> {
        val musicList = mutableListOf<String>()

        return musicList
    }
}
