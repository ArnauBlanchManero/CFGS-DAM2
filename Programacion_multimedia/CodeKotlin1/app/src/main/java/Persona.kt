class Persona (nombre:String, apellido:String, edad:Int) {
    var nombre = nombre
    var apellido = apellido
    var edad = edad
}

fun main(){
    val persona1 = Persona("Pepito", "Grillo", 7)
    print(persona1.nombre)
    print(persona1.apellido)
    print(persona1.edad)
}
