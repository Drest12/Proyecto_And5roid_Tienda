package com.example.sistema_modas.service

import com.example.sistema_modas.clases.Categoria
import com.example.sistema_modas.clases.Rol
import retrofit2.Call
import retrofit2.http.*

interface RolService {

    @GET("rol")
    fun MostrarRol(): Call<List<Rol>?>?

    @GET("rol/custom")
    fun MostrarRolPersonalizado(): Call<List<Rol>?>?

    @POST("rol")
    fun RegistrarRol(@Body r: Rol?): Call<Rol?>?

    @PUT("rol/{id}")
    fun ActualizarRol(@Path("id") id:Long, @Body r: Rol?): Call<Rol?>?

    @DELETE("rol/{id}")
    fun EliminarRol(@Path("id") id:Long): Call<Rol?>?
}