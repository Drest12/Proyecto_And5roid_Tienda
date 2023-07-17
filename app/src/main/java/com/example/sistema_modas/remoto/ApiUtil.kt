package com.example.sistema_modas.remoto

import com.example.sistema_modas.service.*

object ApiUtil {
    //reemplazar localhost por tu direccion IP
    val API_URl="http://192.168.0.2:8091/sistemamoda/"

    val categoriaService: CategoriaService?
        get() =RetrofitClient.getClient(API_URl)?.create(CategoriaService::class.java)
    val rolService: RolService?
        get() =RetrofitClient.getClient(API_URl)?.create(RolService::class.java)
    val distritoService: DistritoService?
        get() =RetrofitClient.getClient(API_URl)?.create(DistritoService::class.java)
    val clienteService: ClienteService?
        get() =RetrofitClient.getClient(API_URl)?.create(ClienteService::class.java)
    val empleadoService: EmpleadoService?
        get() =RetrofitClient.getClient(API_URl)?.create(EmpleadoService::class.java)
    val prendaService: PrendaService?
        get() =RetrofitClient.getClient(API_URl)?.create(PrendaService::class.java)
    val ventaService: VentaService?
        get() =RetrofitClient.getClient(API_URl)?.create(VentaService::class.java)
    val detalleVentaService: DetalleVentaService?
        get() =RetrofitClient.getClient(API_URl)?.create(DetalleVentaService::class.java)
    val usuarioService: UsuarioService?
        get() =RetrofitClient.getClient(API_URl)?.create(UsuarioService::class.java)
    val usuarioRolService: UsuarioRolService?
        get() =RetrofitClient.getClient(API_URl)?.create(UsuarioRolService::class.java)
}
