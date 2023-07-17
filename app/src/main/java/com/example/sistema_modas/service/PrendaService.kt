package com.example.sistema_modas.service


import com.example.sistema_modas.clases.Prenda
import retrofit2.Call
import retrofit2.http.*

interface PrendaService {
    @GET("prenda")
    fun MostrarPrenda(): Call<List<Prenda>?>?

    @GET("prenda/custom")
    fun MostrarPrendaPersonalizado(): Call<List<Prenda>?>?

    @POST("prenda")
    fun RegistrarPrenda(@Body pr: Prenda?): Call<Prenda?>?

    @PUT("prenda/{id}")
    fun ActualizarPrenda(@Path("id") id:Long, @Body pr: Prenda?): Call<Prenda?>?

    @DELETE("prenda/{id}")
    fun EliminarPrenda(@Path("id") id:Long): Call<Prenda?>?
}