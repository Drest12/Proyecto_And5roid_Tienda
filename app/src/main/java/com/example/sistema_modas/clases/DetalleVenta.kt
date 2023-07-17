package com.example.sistema_modas.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DetalleVenta {
    @SerializedName("codigo")
    @Expose
    var codigo:Long=0
    @SerializedName("cantidad")
    @Expose
    var cantidad:String?=null
    @SerializedName("precio")
    @Expose
    var precio:String?=null
    @SerializedName("venta")
    @Expose
    var venta:Venta?=null
    @SerializedName("prenda")
    @Expose
    var prenda:Prenda?=null

    constructor()
    constructor(
        codigo: Long,
        cantidad: String?,
        precio: String?,
        venta: Venta?,
        prenda: Prenda?
    ) {
        this.codigo = codigo
        this.cantidad = cantidad
        this.precio = precio
        this.venta = venta
        this.prenda = prenda
    }


}