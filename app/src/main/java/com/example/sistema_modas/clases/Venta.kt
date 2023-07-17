package com.example.sistema_modas.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Venta {
    @SerializedName("codigo")
    @Expose
    var codigo:Long=0
    @SerializedName("cantidad")
    @Expose
    var cantidad:String?=null
    @SerializedName("fecha")
    @Expose
    var fecha:String?=null
    @SerializedName("estado")
    @Expose
    var estado:Boolean=false
    @SerializedName("cliente")
    @Expose
    var cliente:Cliente?=null
    @SerializedName("empleado")
    @Expose
    var empleado:Empleado?=null

    constructor()
    constructor(
        codigo: Long,
        cantidad: String?,
        fecha: String?,
        estado: Boolean,
        cliente: Cliente?,
        empleado: Empleado?
    ) {
        this.codigo = codigo
        this.cantidad = cantidad
        this.fecha = fecha
        this.estado = estado
        this.cliente = cliente
        this.empleado = empleado
    }

}