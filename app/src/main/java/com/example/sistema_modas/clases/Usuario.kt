package com.example.sistema_modas.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
class Usuario {
    @SerializedName("codigo")
    @Expose
    var codigo: Long = 0
    @SerializedName("usuario")
    @Expose
    var usuario: String? = null
    @SerializedName("clave")
    @Expose
    var clave: String? = null
    @SerializedName("estado")
    @Expose
    var estado = false
    constructor(){}
    constructor(codigo: Long, usuario: String?, clave: String?, estado: Boolean) {
        this.codigo = codigo
        this.usuario = usuario
        this.clave = clave
        this.estado = estado
    }

}