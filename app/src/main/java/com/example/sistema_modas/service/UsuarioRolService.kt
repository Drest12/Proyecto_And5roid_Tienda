package com.example.sistema_modas.service

import com.example.sistema_modas.clases.Categoria
import com.example.sistema_modas.clases.UsuarioRol
import retrofit2.Call
import retrofit2.http.*

interface UsuarioRolService {
    @GET("usuario-rol")
    fun MostrarUsuarioRol(): Call<List<UsuarioRol>?>?

    @GET("usuario-rol/custom")
    fun MostrarUsuarioRolPersonalizado(): Call<List<UsuarioRol>?>?

    @POST("usuario-rol")
    fun RegistrarUsuarioRol(@Body ur: UsuarioRol?): Call<UsuarioRol?>?

    @PUT("usuario-rol/{id}")
    fun ActualizarUsuarioRol(@Path("id") id:Long, @Body ur: UsuarioRol?): Call<UsuarioRol?>?

    @DELETE("usuario-rol/{id}")
    fun EliminarUsuarioRol(@Path("id") id:Long): Call<UsuarioRol?>?
}