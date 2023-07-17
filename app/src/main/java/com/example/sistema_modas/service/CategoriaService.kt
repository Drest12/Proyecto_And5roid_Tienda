package com.example.sistema_modas.service

import com.example.sistema_modas.clases.Categoria
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CategoriaService {
    @GET("categoria")
    fun MostrarCategoria(): Call<List<Categoria>?>?

    @GET("categoria/custom")
    fun MostrarCategoriaPersonalizado(): Call<List<Categoria>?>?

    @POST("categoria")
    fun RegistrarCategoria(@Body c: Categoria?): Call<Categoria?>?

    @PUT("categoria/{id}")
    fun ActualizarCategoria(@Path("id") id:Long,@Body c: Categoria?): Call<Categoria?>?

    @DELETE("categoria/{id}")
    fun EliminarCategoria(@Path("id") id:Long): Call<Categoria?>?

}