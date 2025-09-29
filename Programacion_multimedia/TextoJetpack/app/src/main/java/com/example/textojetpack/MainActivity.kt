package com.example.textojetpack

import android.media.Image
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //FilasColumnas()
            //ColumnasInternas()
            //Cajas()
            Imagen()
        }
    }
}

@Composable
fun FilasColumnas(){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 40.dp)){
        Text("columna 1")
        Text("columna 2")
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 90.dp)){
        Text("columna 3")
        Text("columna 4")
    }
    Row(modifier = Modifier.padding(70.dp, 45.dp)) {
        Text("fila 1")
        Spacer(modifier = Modifier.width(10.dp))
        Text("fila 2")
    }
    Row(modifier = Modifier.padding(70.dp, 95.dp)) {
        Text("fila 3")
        Spacer(modifier = Modifier.width(10.dp))
        Text("fila 4")
    }
}
@Composable
fun ColumnasInternas(){
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(40.dp))
        Row {
            Text("Segundo Texto")
            Spacer(modifier = Modifier.width(20.dp))
            Text("Tercer Texto")
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)) {
            Text("Primer Texto")
            Text("Segundo Texto")
        }
    }
}

@Composable
fun Cajas(){
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 40.dp)){
        Text("Parte Superior Izquierda", modifier = Modifier.align(Alignment.TopStart))

        Text("Parte Central", modifier = Modifier.align(Alignment.Center))

        Text("Parte Inferior Derecha", modifier = Modifier.align(Alignment.BottomEnd))
    }
}

@Composable
fun Imagen() {
    var colorFondo by remember { mutableStateOf(Color.Blue) }
    var posicionTexto by remember { mutableStateOf(Offset(40f,0f)) }
    var posicionImg by remember { mutableStateOf(Offset(40f,0f)) }
    Box(modifier = Modifier.fillMaxSize().padding(top = 40.dp).background(colorFondo)) {
        Image(
            painter = painterResource(id = R.drawable.turtle),
            contentDescription = "Dibujo de una tortuga",
            modifier = Modifier.align(Alignment.Center).fillMaxSize().offset {
                IntOffset(x = posicionImg.x.toInt(), y = posicionImg.y.toInt())
            }.pointerInput(key1 = Unit) {
                detectDragGestures {
                    change, dragAmount ->
                    change.consume()
                    posicionImg += Offset(x = dragAmount.x, y = dragAmount.y)
                }
            }
        )
        Text(
            text = "La característica más importante del esqueleto de las tortugas es que una gran parte de su columna vertebral está soldada a la parte dorsal del caparazón.",
            fontSize = 22.sp,
            color = Color.Green,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center).offset {
                IntOffset(x = posicionTexto.x.toInt(), y = posicionTexto.y.toInt())
            }.pointerInput(key1 = Unit) {
                detectDragGestures {
                        change, dragAmount ->
                    change.consume()
                    posicionTexto += Offset(x = dragAmount.x, y = dragAmount.y)
                }
            }
        )
        Button(
            onClick = {colorFondo = colorRandom()},
            modifier = Modifier.align(Alignment.BottomCenter)
        ){
            Text(text = "Cambia el fondo")
        }
    }

}

fun colorRandom(): Color{
    var rojo = (0..255).random()
    var verde = (0..255).random()
    var azul = (0..255).random()
    return Color(red = rojo, green = verde,blue = azul)
}
@Preview
@Composable
fun DosTextosVerticalesPreview(){
    Imagen()
}


