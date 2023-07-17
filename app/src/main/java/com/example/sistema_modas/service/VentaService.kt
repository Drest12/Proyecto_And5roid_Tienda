package com.example.sistema_modas.service

import com.example.sistema_modas.clases.Categoria
import com.example.sistema_modas.clases.Rol
import com.example.sistema_modas.clases.Venta
import retrofit2.Call
import retrofit2.http.*

interface VentaService {
    @GET("venta")
    fun MostrarVenta(): Call<List<Venta>?>?

    @GET("venta/custom")
    fun MostrarVentaPersonalizado(): Call<List<Venta>?>?

    @POST("venta")
    fun RegistrarVenta(@Body v: Venta?): Call<Venta?>?

    @PUT("venta/{id}")
    fun ActualizarVenta(@Path("id") id:Long, @Body v: Venta?): Call<Venta?>?

    @DELETE("venta/{id}")
    fun EliminarVenta(@Path("id") id:Long): Call<Venta?>?
}