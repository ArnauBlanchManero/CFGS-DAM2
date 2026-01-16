package com.example.botones

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import kotlin.random.Random

var estado_caos = false
private lateinit var chaosRunnable: Runnable

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
        val constraintSet = ConstraintSet()

        val boton1 = Button(this)
        val boton2 = Button(this)
        val boton3 = Button(this)
        val boton4 = Button(this)
        val boton5 = Button(this)

        boton1.text = "Púlsame"
        boton1.id = View.generateViewId()

        boton2.text = "Cambio color"
        boton2.id = View.generateViewId()

        boton3.text = "Me escapo"
        boton3.id = View.generateViewId()

        boton4.text = "Caos"
        boton4.id = View.generateViewId()

        boton5.text = "Modo"
        boton5.id = View.generateViewId()

        boton1.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
        boton2.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
        boton3.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
        boton4.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
        boton5.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)

        layoutPrincipal.addView(boton1)
        layoutPrincipal.addView(boton2)
        layoutPrincipal.addView(boton3)
        layoutPrincipal.addView(boton4)
        layoutPrincipal.addView(boton5)

        boton1.setOnClickListener {
            Toast.makeText(this, "¡Botón pulsado!", Toast.LENGTH_SHORT).show()
        }
        boton2.setOnClickListener {
            cambiarColor(boton2)
        }
        boton3.setOnClickListener {
            mover(boton3, constraintSet, layoutPrincipal)
        }
        boton4.setOnClickListener {
            estado_caos = !estado_caos
            if (estado_caos)
                empezar_caos(layoutPrincipal)
            else {
                findViewById<TextView>(R.id.texto_caos).setText("OFF")
                layoutPrincipal.removeCallbacks(chaosRunnable)
            }
        }
        boton5.setOnClickListener {
            //cambiarModo(boton3, constraintSet, layoutPrincipal)
        }

        constraintSet.clone(layoutPrincipal) // Clonamos el layout actual
        // POSICION BOTON 1
        constraintSet.connect(
            boton1.id, ConstraintSet.TOP,
            ConstraintSet.PARENT_ID, ConstraintSet.TOP
        )
        constraintSet.connect(
            boton1.id, ConstraintSet.BOTTOM,
            boton4.id, ConstraintSet.TOP
        )
        constraintSet.connect(
            boton1.id, ConstraintSet.START,
            ConstraintSet.PARENT_ID, ConstraintSet.START
        )
        constraintSet.connect(
            boton1.id, ConstraintSet.END,
            boton2.id, ConstraintSet.START
        )
        // POSICION BOTON 2
        constraintSet.connect(
            boton2.id, ConstraintSet.TOP,
            ConstraintSet.PARENT_ID, ConstraintSet.TOP
        )
        constraintSet.connect(
            boton2.id, ConstraintSet.BOTTOM,
            boton5.id, ConstraintSet.TOP
        )
        constraintSet.connect(
            boton2.id, ConstraintSet.START,
            boton1.id, ConstraintSet.END
        )
        constraintSet.connect(
            boton2.id, ConstraintSet.END,
            ConstraintSet.PARENT_ID, ConstraintSet.END
        )
        // POSICION BOTON 3
        constraintSet.connect(
            boton3.id, ConstraintSet.TOP,
            ConstraintSet.PARENT_ID, ConstraintSet.TOP
        )
        constraintSet.connect(
            boton3.id, ConstraintSet.START,
            ConstraintSet.PARENT_ID, ConstraintSet.START
        )
        constraintSet.setMargin(
            boton3.id, ConstraintSet.START, 400
        )
        constraintSet.setMargin(
            boton3.id, ConstraintSet.TOP, 270
        )
        // POSICION BOTON 4
        constraintSet.connect(
            boton4.id, ConstraintSet.TOP,
            boton1.id, ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            boton4.id, ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            boton4.id, ConstraintSet.START,
            ConstraintSet.PARENT_ID, ConstraintSet.START
        )
        constraintSet.connect(
            boton4.id, ConstraintSet.END,
            boton5.id, ConstraintSet.START
        )
        // POSICION BOTON 5
        constraintSet.connect(
            boton5.id, ConstraintSet.TOP,
            boton2.id, ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            boton5.id, ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            boton5.id, ConstraintSet.START,
            boton4.id, ConstraintSet.END
        )
        constraintSet.connect(
            boton5.id, ConstraintSet.END,
            ConstraintSet.PARENT_ID, ConstraintSet.END
        )

        constraintSet.applyTo(layoutPrincipal) // Aplicamos las constraints
        chaosRunnable = object : Runnable {
            override fun run() {
                if (!estado_caos) return

                cambiarColor(boton2)
                mover(boton3, constraintSet, layoutPrincipal)

                layoutPrincipal.postDelayed(this, 500)
            }
        }

    }

    fun cambiarColor(boton: Button) {
        val red = Random.nextInt(0, 256)
        val green = Random.nextInt(0, 256)
        val blue = Random.nextInt(0, 256)
        boton.setBackgroundColor(Color.rgb(red, green, blue))
    }
    fun mover(boton: Button, constraintSet: ConstraintSet, layoutPrincipal: ConstraintLayout) {
        constraintSet.clone(layoutPrincipal) // Clonamos el layout actual

        val margenArriba = Random.nextInt(0, (layoutPrincipal.height-boton.height))
        val margenIzquierda = Random.nextInt(0, (layoutPrincipal.width-boton.width))
        constraintSet.setMargin(
            boton.id, ConstraintSet.START, margenIzquierda
        )
        constraintSet.setMargin(
            boton.id, ConstraintSet.TOP, margenArriba
        )
        TransitionManager.beginDelayedTransition(layoutPrincipal)

        constraintSet.applyTo(layoutPrincipal) // Aplicamos las constraints
    }

}

private fun MainActivity.empezar_caos(layoutPrincipal: ConstraintLayout) {
    findViewById<TextView>(R.id.texto_caos).setText("ON")
    layoutPrincipal.post(chaosRunnable)
}
