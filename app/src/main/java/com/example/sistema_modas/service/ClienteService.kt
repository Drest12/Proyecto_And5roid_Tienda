package com.example.sistema_modas.service

import com.example.sistema_modas.clases.Categoria
import com.example.sistema_modas.clases.Cliente
import retrofit2.Call
import retrofit2.http.*

interface ClienteService {
    @GET("cliente")
    fun MostrarCliente(): Call<List<Cliente>?>?

    @GET("cliente/custom")
    fun MostrarClientePersonalizado(): Call<List<Cliente>?>?

    @POST("cliente")
    fun RegistrarCliente(@Body cl: Cliente?): Call<Cliente?>?

    @PUT("cliente/{id}")
    fun ActualizarCliente(@Path("id") id:Long, @Body cl: Cliente?): Call<Cliente?>?

    @DELETE("cliente/{id}")
    fun EliminarCliente(@Path("id") id:Long): Call<Cliente?>?
    @GET("cliente/{id}")
    fun BuscarXCodigoCliente(@Path("id") id:Long, @Body cl: Cliente?): Call<Cliente?>?

    @GET("cliente/dni")
    fun BuscarXDniCliente(@Body cl: Cliente?): Call<List<Cliente>?>?

    @GET("cliente/nombre")
    fun BuscarXNombrePaternoCliente(@Body cl: Cliente?): Call<Cliente?>?

    @GET("cliente/apellido")
    fun BuscarXApellidoPCliente(@Body cl: Cliente?): Call<Cliente?>?


}