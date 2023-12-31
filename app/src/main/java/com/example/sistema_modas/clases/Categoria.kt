package com.example.sistema_modas.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Categoria {
    @SerializedName("codigo")
    @Expose
    var codigo:Long=0
    @SerializedName("nombre")
    @Expose
    var nombre:String?=null
    @SerializedName("estado")
    @Expose
    var estado:Boolean=false

    //constructores
    constructor(){}
    constructor(codigo: Long, nombre: String?, estado: Boolean) {
        this.codigo = codigo
        this.nombre = nombre
        this.estado = estado
    }
}