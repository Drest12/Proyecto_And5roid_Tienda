package com.example.sistema_modas.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Rol {
    @SerializedName("codigo")
    @Expose
    var codigo:Long=0
    @SerializedName("rol")
    @Expose
    var nombre:String?=null
    @SerializedName("estado")
    @Expose
    var estado:Boolean=false

    constructor()
    constructor(codigo: Long, nombre: String?, estado: Boolean) {
        this.codigo = codigo
        this.nombre = nombre
        this.estado = estado
    }

}