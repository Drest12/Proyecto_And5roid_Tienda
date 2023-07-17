package com.example.sistema_modas.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Empleado {
    @SerializedName("codigo")
    @Expose
    var codigo:Long=0
    @SerializedName("nombre")
    @Expose
    var nombre:String?=null
    @SerializedName("telefono")
    @Expose
    var telefono:String?=null
    @SerializedName("apellido")
    @Expose
    var apellido:String?=null
    @SerializedName("dni")
    @Expose
    var dni:String?=null
    @SerializedName("contrasenia")
    @Expose
    var contrasenia:String?=null
    @SerializedName("direccion")
    @Expose
    var direccion:String?=null
    @SerializedName("correo")
    @Expose
    var correo:String?=null
    @SerializedName("estado")
    @Expose
    var estado:Boolean=false

    @SerializedName("rol")
    @Expose
    var rol:Rol?=null

    @SerializedName("distrito")
    @Expose
    var distrito:Distrito?=null

    constructor()
    constructor(
        codigo: Long,
        nombre: String?,
        telefono: String?,
        dni: String?,
        contrasenia: String?,
        direccion: String?,
        correo: String?,
        estado: Boolean,
        rol: Rol?,
        distrito: Distrito?
    ) {
        this.codigo = codigo
        this.nombre = nombre
        this.telefono = telefono
        this.dni = dni
        this.contrasenia = contrasenia
        this.direccion = direccion
        this.correo = correo
        this.estado = estado
        this.rol = rol
        this.distrito = distrito
    }

}