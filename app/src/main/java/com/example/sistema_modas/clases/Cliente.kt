package com.example.sistema_modas.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Cliente {
    @SerializedName("codigo")
    @Expose
    var codigo:Long=0
    @SerializedName("nombre")
    @Expose
    var nombre:String?=null
    @SerializedName("apellido")
    @Expose
    var apellido:String?=null
    @SerializedName("telefono")
    @Expose
    var telefono:String?=null
    @SerializedName("dni")
    @Expose
    var dni:String?=null
    @SerializedName("genero")
    @Expose
    var genero:String?=null
    @SerializedName("direccion")
    @Expose
    var direccion:String?=null
    @SerializedName("correo")
    @Expose
    var correo:String?=null
    @SerializedName("estado")
    @Expose
    var estado:Boolean=false
    @SerializedName("distrito")
    @Expose
    var distrito:Distrito?=null


    constructor()
    constructor(
        codigo: Long,
        nombre: String?,
        apellido: String?,
        telefono: String?,
        dni: String?,
        genero: String?,
        direccion: String?,
        correo: String?,
        estado: Boolean,
        distrito: Distrito?
    ) {
        this.codigo = codigo
        this.nombre = nombre
        this.apellido = apellido
        this.telefono = telefono
        this.dni = dni
        this.genero = genero
        this.direccion = direccion
        this.correo = correo
        this.estado = estado
        this.distrito = distrito
    }


}