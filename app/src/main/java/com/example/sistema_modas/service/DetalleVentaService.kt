package com.example.sistema_modas.service

import com.example.sistema_modas.clases.DetalleVenta

import retrofit2.Call
import retrofit2.http.*

interface DetalleVentaService {
    @GET("detalleventa")
    fun MostrarDetalleVenta(): Call<List<DetalleVenta>?>?

    @POST("detalleventa")
    fun RegistrarDetalleVenta(@Body c: DetalleVenta?): Call<DetalleVenta?>?

    @PUT("detalleventa/{id}")
    fun ActualizarDetalleVenta(@Path("id") id:Long, @Body c: DetalleVenta?): Call<DetalleVenta?>?

    @DELETE("detalleventa/{id}")
    fun EliminarDetalleVenta(@Path("id") id:Long): Call<DetalleVenta?>?
}