package com.example.appmusicandroid.View

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.appmusicandroid.R

class Playlist : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)

        val mainLayout: LinearLayout = findViewById(R.id.LinearLlista)

        findViewById<LinearLayout>(R.id.NewPlaylist).setOnClickListener {
            mostrarDialogo(mainLayout)
        }

        mainLayout.setOnClickListener {
            val intent = Intent(this, ListaPlaylist::class.java)
            startActivity(intent)
        }
    }

    private fun agregarNuevoLinearLayout(mainLayout: LinearLayout, nombrePlaylist: String) {
        val nuevoLinearLayout = LinearLayout(this)
        nuevoLinearLayout.layoutParams = LinearLayout.LayoutParams(
            1000,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 60, 0, 0)
        }
        with(nuevoLinearLayout) {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER

            addView(ImageView(this@Playlist).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setPadding(0, 0, 16, 0)
                }
                setImageResource(R.drawable.listsongs)
            })

            val nestedLinearLayout = LinearLayout(this@Playlist).apply {
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
                orientation = LinearLayout.VERTICAL

                addView(TextView(this@Playlist).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    setTextColor(resources.getColor(R.color.black))
                    text = nombrePlaylist
                    textSize = 16f
                })

                addView(TextView(this@Playlist).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    text = "Num. Cançons - 00:00"
                    textSize = 12f
                })
            }

            addView(nestedLinearLayout)
            addView(ImageView(this@Playlist).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                setImageResource(R.drawable.more)
                setOnClickListener{
                    mostrarMenuPopup(it, nombrePlaylist, mainLayout)
                }
            })
        }

        mainLayout.addView(nuevoLinearLayout)
    }

    private fun mostrarMenuPopup(view: View, nombrePlaylist: String, mainLayout: LinearLayout) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.menu_playlist_options)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.opcion1 -> {
                    eliminarLinearLayout(mainLayout, nombrePlaylist)
                    true
                }
                R.id.opcion2 -> {
                    cambiarNombrePlaylist(mainLayout, nombrePlaylist)
                    true
                }
                // Agrega más opciones según sea necesario
                else -> false
            }
        }

        popupMenu.show()
    }

    private fun eliminarLinearLayout(mainLayout: LinearLayout, nombrePlaylist: String) {
        for (i in 0 until mainLayout.childCount) {
            val childView = mainLayout.getChildAt(i)
            if (childView is LinearLayout) {
                val nestedLayout = childView.getChildAt(1)
                if (nestedLayout is LinearLayout) {
                    val textView = nestedLayout.getChildAt(0)
                    if (textView is TextView && textView.text == nombrePlaylist) {
                        mainLayout.removeViewAt(i)
                        break
                    }
                }
            }
        }
    }

    private fun cambiarNombrePlaylist(mainLayout: LinearLayout, oldNombrePlaylist: String) {
        val inputDialog = AlertDialog.Builder(this).apply {
            setTitle("Cambiar nombre de la playlist")
            val editTextNombre = EditText(this@Playlist)
            editTextNombre.setText(oldNombrePlaylist)
            setView(editTextNombre)

            setPositiveButton("Cambiar") { dialog, which ->
                val newNombrePlaylist = editTextNombre.text.toString()
                if (newNombrePlaylist.isNotEmpty() && newNombrePlaylist != oldNombrePlaylist) {
                    for (i in 0 until mainLayout.childCount) {
                        val childView = mainLayout.getChildAt(i)
                        if (childView is LinearLayout) {
                            val nestedLayout = childView.getChildAt(1)
                            if (nestedLayout is LinearLayout) {
                                val textView = nestedLayout.getChildAt(0)
                                if (textView is TextView && textView.text.toString() == oldNombrePlaylist) {
                                    textView.text = newNombrePlaylist
                                    childView.setOnClickListener{
                                        mostrarMenuPop  mup(it, newNombrePlaylist, mainLayout)
                                    }
                                    break
                                }
                            }
                        }
                    }
                }
            }

            setNegativeButton("Cancelar") { dialog, which -> dialog.cancel() }
        }.show()
    }

    private fun mostrarDialogo(mainLayout: LinearLayout) {
        AlertDialog.Builder(this).apply {
            setTitle("Nom de la playlist")
            val editTextNombre = EditText(this@Playlist)
            setView(editTextNombre)

            setPositiveButton("Crear") { dialog, which ->
                val nombrePlaylist = editTextNombre.text.toString()
                agregarNuevoLinearLayout(mainLayout, nombrePlaylist)
            }

            setNegativeButton("Cancelar") { dialog, which -> dialog.cancel() }
        }.show()
    }
}