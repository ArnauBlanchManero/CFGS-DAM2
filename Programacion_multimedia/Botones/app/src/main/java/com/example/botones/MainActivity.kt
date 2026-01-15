package com.example.botones

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLayout)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        val layoutPrincipal = findViewById<ConstraintLayout>(R.id.mainLayout)
        val boton1 = Button(this)
        boton1.text = "Púlsame"
        boton1.id = View.generateViewId()

        boton1.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
        val boton2 = Button(this)
        boton2.text = "Cambio color"
        boton2.id = View.generateViewId()

        boton2.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
        layoutPrincipal.addView(boton1)
        layoutPrincipal.addView(boton2)

        boton1.setOnClickListener {
            Toast.makeText(this, "¡Botón pulsado!", Toast.LENGTH_SHORT).show()
        }
        boton2.setOnClickListener {
            val red = Random.nextInt(0, 256)
            val green = Random.nextInt(0, 256)
            val blue = Random.nextInt(0, 256)
            boton2.setBackgroundColor(Color.rgb(red, green, blue))
        }

        val constraintSet = ConstraintSet()
        constraintSet.clone(layoutPrincipal) // Clonamos el layout actual
        constraintSet.connect(
            boton1.id, ConstraintSet.TOP,
            ConstraintSet.PARENT_ID, ConstraintSet.TOP
        )
        constraintSet.connect(
            boton1.id, ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            boton1.id, ConstraintSet.START,
            ConstraintSet.PARENT_ID, ConstraintSet.START
        )
        constraintSet.connect(
            boton1.id, ConstraintSet.END,
            boton2.id, ConstraintSet.START
        )
        constraintSet.connect(
            boton2.id, ConstraintSet.TOP,
            ConstraintSet.PARENT_ID, ConstraintSet.TOP
        )
        constraintSet.connect(
            boton2.id, ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            boton2.id, ConstraintSet.START,
            boton1.id, ConstraintSet.END
        )
        constraintSet.connect(
            boton2.id, ConstraintSet.END,
            ConstraintSet.PARENT_ID, ConstraintSet.END
        )
        constraintSet.applyTo(layoutPrincipal) // Aplicamos las constraints

    }
}