package com.example.sistema_modas.service

import com.example.sistema_modas.clases.Empleado
import com.example.sistema_modas.clases.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UsuarioService {
    @GET("usuario/custom")
    fun MostrarUsuarioPersonalizado(): Call<List<Usuario>?>?
    @GET("usuario")
    fun MostrarUsuario(): Call<List<Usuario>?>?

    @POST("usuario/login")
    fun ValidarUsuario(@Body u: Usuario?): Call<List<Usuario>?>?
}
