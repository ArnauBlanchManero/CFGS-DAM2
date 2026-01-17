package com.example.botones

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.transition.TransitionManager
import kotlin.random.Random

var estado_caos = false
var modo = true
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
        val imagen_oscuro = ImageView(this)
        val imagen_claro = ImageView(this)

        boton1.text = "Púlsame"
        boton1.id = View.generateViewId()
        boton1.tag = "btn"

        boton2.text = "Cambio color"
        boton2.id = View.generateViewId()
        boton2.tag = "btn"

        boton3.text = "Me escapo"
        boton3.id = View.generateViewId()
        boton3.tag = "btn"

        boton4.text = "Caos"
        boton4.id = View.generateViewId()
        boton4.tag = "btn"

        boton5.text = "Modo"
        boton5.id = View.generateViewId()
        boton5.tag = "btn"

        imagen_oscuro.id = View.generateViewId()
        imagen_oscuro.tag = "img"
        imagen_oscuro.setImageResource(R.drawable.negro_riendo)
        imagen_oscuro.visibility = View.INVISIBLE

        imagen_claro.id = View.generateViewId()
        imagen_claro.tag = "img"
        imagen_claro.setImageResource(R.drawable.mucha_luz)
        imagen_claro.visibility = View.INVISIBLE

        boton1.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
        boton2.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
        boton3.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
        boton4.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
        boton5.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
        imagen_oscuro.layoutParams = ConstraintLayout.LayoutParams(240,180)
        imagen_claro.layoutParams = ConstraintLayout.LayoutParams(240,180)

        layoutPrincipal.addView(boton1)
        layoutPrincipal.addView(boton2)
        layoutPrincipal.addView(boton3)
        layoutPrincipal.addView(boton4)
        layoutPrincipal.addView(boton5)
        layoutPrincipal.addView(imagen_oscuro)
        layoutPrincipal.addView(imagen_claro)

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
            if (modo) {
                cambiarModoOscuro(layoutPrincipal)
                imagen_oscuro.visibility = View.VISIBLE
                imagen_claro.visibility = View.INVISIBLE
            }
            else {
                cambiarModoClaro(layoutPrincipal)
                imagen_claro.visibility = View.VISIBLE
                imagen_oscuro.visibility = View.INVISIBLE
            }
            modo = !modo
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
        // POSICION IMAGEN 1
        constraintSet.connect(
            imagen_oscuro.id, ConstraintSet.TOP,
            findViewById<TextView>(R.id.texto_central).id, ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            imagen_oscuro.id, ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            imagen_oscuro.id, ConstraintSet.START,
            findViewById<TextView>(R.id.texto_caos).id, ConstraintSet.END
        )
        constraintSet.connect(
            imagen_oscuro.id, ConstraintSet.END,
            findViewById<TextView>(R.id.texto_modo).id, ConstraintSet.START
        )
        // POSICION IMAGEN 2
        constraintSet.connect(
            imagen_claro.id, ConstraintSet.TOP,
            findViewById<TextView>(R.id.texto_central).id, ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            imagen_claro.id, ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            imagen_claro.id, ConstraintSet.START,
            findViewById<TextView>(R.id.texto_caos).id, ConstraintSet.END
        )
        constraintSet.connect(
            imagen_claro.id, ConstraintSet.END,
            findViewById<TextView>(R.id.texto_modo).id, ConstraintSet.START
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

private fun MainActivity.cambiarModoOscuro(layoutPrincipal: ConstraintLayout) {
    findViewById<TextView>(R.id.texto_modo).setText("Oscuro")
    layoutPrincipal.setBackgroundColor(Color.rgb(39, 38, 38))
    for (i in 0 .. layoutPrincipal.childCount-1) {
        var elemento = layoutPrincipal.getChildAt(i)
        var etiqueta = elemento.tag
        if (etiqueta.equals("texto"))
            (elemento as TextView).setTextColor(Color.rgb(225,225,225))
        if (etiqueta.equals("btn")) {
            val estilo_boton = GradientDrawable().apply {
                cornerRadius = 70f
                setColor(Color.rgb(100, 100, 100))
            }
            (elemento as Button).background = estilo_boton
            (elemento as Button).setTextColor(Color.rgb(250, 250, 250))
            elemento.elevation = 24f
        }
//        if (etiqueta.equals("img")) {
//            if ((elemento as ImageView).isVisible)
//                elemento.visibility = View.INVISIBLE
//            else
//                elemento.visibility = View.VISIBLE
//        }
    }
}
private fun MainActivity.cambiarModoClaro(layoutPrincipal: ConstraintLayout) {
    findViewById<TextView>(R.id.texto_modo).setText("Claro")
    layoutPrincipal.setBackgroundColor(Color.rgb(240, 240, 240))
    for (i in 0 .. layoutPrincipal.childCount-1) {
        var elemento = layoutPrincipal.getChildAt(i)
        var etiqueta = elemento.tag
        if (etiqueta.equals("texto"))
            (elemento as TextView).setTextColor(Color.rgb(50,50,50))
        if (etiqueta.equals("btn")) {
            val estilo_boton = GradientDrawable().apply {
                cornerRadius = 0f
                setColor(Color.rgb(150, 150, 150))
            }
            (elemento as Button).background = estilo_boton
            (elemento as Button).setTextColor(Color.rgb(25, 25, 25))
            elemento.elevation = 24f
        }
//        if (etiqueta.equals("img")) {
//            if ((elemento as ImageView).isVisible)
//                elemento.visibility = View.INVISIBLE
//            else
//                elemento.visibility = View.VISIBLE
//        }
    }
}

private fun MainActivity.empezar_caos(layoutPrincipal: ConstraintLayout) {
    findViewById<TextView>(R.id.texto_caos).setText("ON")
    layoutPrincipal.post(chaosRunnable)
}
