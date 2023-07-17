package com.example.sistema_modas.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Prenda {
    @SerializedName("codigo")
    @Expose
    var codigo:Long=0
    @SerializedName("descripcion")
    @Expose
    var descripcion:String?=null
    @SerializedName("precio")
    @Expose
    var precio:String?=null
    @SerializedName("talla")
    @Expose
    var talla:String?=null
    @SerializedName("cantidad")
    @Expose
    var cantidad:String?=null
    @SerializedName("estado")
    @Expose
    var estado:Boolean=false
    @SerializedName("categoria")
    @Expose
    var categoria:Categoria?=null

    constructor()
    constructor(
        codigo: Long,
        descripcion: String?,
        precio: String?,
        talla: String?,
        cantidad: String?,
        estado: Boolean,
        categoria: Categoria?
    ) {
        this.codigo = codigo
        this.descripcion = descripcion
        this.precio = precio
        this.talla = talla
        this.cantidad = cantidad
        this.estado = estado
        this.categoria = categoria
    }

}