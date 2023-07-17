package com.example.sistema_modas.adaptadores.busqueda

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R
import com.example.sistema_modas.clases.Categoria


class AdaptadorBusquedaCategoria (context: Context?, private val listabuscarcategoria:List<Categoria>?): BaseAdapter() {
    private val layoutInflater: LayoutInflater
    private var listaFiltrada: List<Categoria>? = null

    init {
        layoutInflater= LayoutInflater.from(context)
        listaFiltrada = listabuscarcategoria
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
            listabuscarcategoria
        } else {
            listabuscarcategoria?.filter { it.nombre!!.toLowerCase().contains(texto.toLowerCase())}

        }
        notifyDataSetChanged()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null) {
            //relacionamos la vista con el layout correspondiente
            //en este caso elemento_lista_categoria
            vista = layoutInflater.inflate(R.layout.elemento_lista_buscar_categoria, p2, false)
        }
        val objcategoria=getItem(p0) as Categoria
        //creamos los controles
        val lstCodCat= vista!!.findViewById<TextView>(R.id.lstCodCat)
        val lstNomCat= vista!!.findViewById<TextView>(R.id.lstNomCat)

        val lstEstCat= vista!!.findViewById<TextView>(R.id.lstEstCat)
        //agregamos los valores a la lista
        lstCodCat.text="Codigo: "+objcategoria.codigo
        lstNomCat.text="Nombre:"+objcategoria.nombre

        if(objcategoria.estado==true){
            lstEstCat.text="Estado: Habilitado"
        }else{
            lstEstCat.text="Estado: Deshabilitado"
        }
        return vista!!
    }
}