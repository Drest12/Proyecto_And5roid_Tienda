package com.example.sistema_modas.service

import com.example.sistema_modas.clases.Categoria
import com.example.sistema_modas.clases.Distrito
import retrofit2.Call
import retrofit2.http.*

interface DistritoService {
    @GET("distrito")
    fun MostrarDistrito(): Call<List<Distrito>?>?

    @GET("distrito/custom")
    fun MostrarDistritoPersonalizado(): Call<List<Distrito>?>?

    @POST("distrito")
    fun RegistrarDistrito(@Body c: Distrito?): Call<Distrito?>?

    @PUT("distrito/{id}")
    fun ActualizarDistrito(@Path("id") id:Long, @Body c: Distrito?): Call<Distrito?>?

    @DELETE("distrito/{id}")
    fun EliminarDistrito(@Path("id") id:Long): Call<Distrito?>?
}