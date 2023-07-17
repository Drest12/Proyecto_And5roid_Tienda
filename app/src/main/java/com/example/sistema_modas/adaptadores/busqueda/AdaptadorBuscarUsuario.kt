package com.example.sistema_modas.adaptadores.busqueda

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R

import com.example.sistema_modas.clases.Usuario

class AdaptadorBuscarUsuario(context: Context?, private val listabuscarusuario:List<Usuario>?): BaseAdapter() {
    private val layoutInflater: LayoutInflater
    private var listaFiltrada: List<Usuario>? = null

    init {
        layoutInflater= LayoutInflater.from(context)
        listaFiltrada = listabuscarusuario
    }

    override fun getCount(): Int {
        return listaFiltrada!!.size
    }

    override fun getItem(p0: Int): Any {
        return listaFiltrada!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    fun filter(texto: String) {
        listaFiltrada = if (texto.isEmpty()) {
            listabuscarusuario
        } else {
            listabuscarusuario?.filter { it.usuario!!.toLowerCase().contains(texto.toLowerCase())}

        }
        notifyDataSetChanged()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null) {
            //relacionamos la vista con el layout correspondiente
            //en este caso elemento_lista_categoria
            vista = layoutInflater.inflate(R.layout.elemento_listar_buscar_usuario, p2, false)
        }
        val objcategoria=getItem(p0) as Usuario
        //creamos los controles
        val lstCodUsuario= vista!!.findViewById<TextView>(R.id.lstCodUsuario)
        val lstNomUsuario= vista!!.findViewById<TextView>(R.id.lstNomUsuario)
        val lstEstUsuario= vista!!.findViewById<TextView>(R.id.lstEstUsuario)
        //agregamos los valores a la lista
        lstCodUsuario.text="Codigo: "+objcategoria.codigo
        lstNomUsuario.text="Nombre:"+objcategoria.usuario

        if(objcategoria.estado==true){
            lstEstUsuario.text="Estado: Habilitado"
        }else{
            lstEstUsuario.text="Estado: Deshabilitado"
        }
        return vista!!
    }
}