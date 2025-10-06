fun main (args: Array<String>){
    var numerito:Int =4
    while(numerito<=25){
        println("Aumento el numerito a $numerito")
        numerito += 2
    }
    var vueltas =10
    do{
        println("Decremento el numerito a $vueltas")
        vueltas --
    }while (vueltas!=0)

    for (i in 0..10){
        println(i)
    }

    for (i in 10 downTo 1){
        println(i)
    }

    for (i in 10..30 step 2){
        println(i)
    }

    println("Cálculo IMC")
    print("Introduce tu peso: ")
    val peso=readln().toInt()
    print("Introduce tu altura: ")
    val altura = readln().toInt()

    when{
        peso > 90 && altura < 190 -> println("Eres muy grandote")
        peso < 60 && altura > 160 -> println("Come un poco más")
        peso > 70 && altura < 180 -> println("Se te nota el gym")
        else -> println("Estás bien")
    }

    var diaSemana: Int
    print("Introduce un número del 1 al 7: ")
    diaSemana = readln().toInt()
    when (diaSemana){
        1 -> println("Lunes")
        2 -> println("Martes")
        3 -> println("Miércoles")
        4 -> println("Jueves")
        5 -> println("Viernes")
        6 -> println("Sábado")
        7 -> println("Domingo")
        else -> println("Ningún día")
    }

}