package com.example.sistema_modas.adaptadores.busqueda

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R

import com.example.sistema_modas.clases.Rol

class AdaptadorBuscarRol (context: Context?, private val listabuscarrol:List<Rol>?): BaseAdapter() {
    private val layoutInflater: LayoutInflater
    private var listaFiltrada: List<Rol>? = null

    init {
        layoutInflater= LayoutInflater.from(context)
        listaFiltrada = listabuscarrol
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
            listabuscarrol
        } else {
            listabuscarrol?.filter { it.nombre!!.toLowerCase().contains(texto.toLowerCase())}

        }
        notifyDataSetChanged()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null) {
            //relacionamos la vista con el layout correspondiente
            //en este caso elemento_lista_categoria
            vista = layoutInflater.inflate(R.layout.elemento_listar_buscar_rol, p2, false)
        }
        val objcategoria=getItem(p0) as Rol
        //creamos los controles
        val lstCodRol= vista!!.findViewById<TextView>(R.id.lstCodRol)
        val lstNomRol= vista!!.findViewById<TextView>(R.id.lstNomRol)

        val lstEstRol= vista!!.findViewById<TextView>(R.id.lstEstRol)
        //agregamos los valores a la lista
        lstCodRol.text="Codigo: "+objcategoria.codigo
        lstNomRol.text="Nombre:"+objcategoria.nombre

        if(objcategoria.estado==true){
            lstEstRol.text="Estado: Habilitado"
        }else{
            lstEstRol.text="Estado: Deshabilitado"
        }
        return vista!!
    }
}