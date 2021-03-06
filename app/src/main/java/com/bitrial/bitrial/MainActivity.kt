package com.bitrial.bitrial

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.AttrRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.bitrial.bitrial.databinding.ActivityMainBinding

fun Fragment.getThemeColor(@AttrRes key: Int): Int {
    val colorString = TypedValue()
    requireContext().theme.resolveAttribute(key, colorString, true)

    return Color.parseColor("${colorString.coerceToString()}")
}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val navController
        get() = findNavController(R.id.fragmentContainerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Requester necesita un contexto para funcionar, lo ponemos a MainActivity
        // porque es la actividad visible en todo momento
        Requester.setContext(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.menuFragment -> {
                AlertDialog.Builder(this).setMessage("Desea salir de la aplicacion?")
                    .setPositiveButton("Si") { _, _ -> finish() }
                    .setNegativeButton("No") { _, _ -> }
                    .show()
            }

            R.id.cardFragment -> {
                AlertDialog.Builder(this).setMessage("Desea volver al menu principal?")
                    .setPositiveButton("Si") { _, _ -> navController.navigate(R.id.menuFragment) }
                    .setNegativeButton("No") { _, _ -> }
                    .show()
            }

            R.id.submitQuestionFragment -> { navController.navigate(R.id.menuFragment) }
        }
    }
}