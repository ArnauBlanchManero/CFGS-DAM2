class Profesor constructor(nombre:String, edad:Int) {
    val nombre: String = nombre
    val edad: Int = edad


    override fun toString(): String {
        return "Nombre: " + nombre + " Edad: " + edad
    }
}