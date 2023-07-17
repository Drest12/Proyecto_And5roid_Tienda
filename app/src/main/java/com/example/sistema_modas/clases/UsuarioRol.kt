package com.example.sistema_modas.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UsuarioRol {
    @SerializedName("codigo")
    @Expose
    var codigo: Long = 0
    @SerializedName("usuario")
    @Expose
    var usuario: Usuario? = null
    @SerializedName("rol")
    @Expose
    var rol: Rol? = null
    @SerializedName("estado")
    @Expose
    var estado = false
}