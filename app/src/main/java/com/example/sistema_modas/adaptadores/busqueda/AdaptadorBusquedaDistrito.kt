package com.example.sistema_modas.adaptadores.busqueda

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R

import com.example.sistema_modas.clases.Distrito

class AdaptadorBusquedaDistrito (context: Context?, private val listabuscardistrito:List<Distrito>?): BaseAdapter() {
    private val layoutInflater: LayoutInflater
    private var listaFiltrada: List<Distrito>? = null

    init {
        layoutInflater= LayoutInflater.from(context)
        listaFiltrada = listabuscardistrito
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
            listabuscardistrito
        } else {
            listabuscardistrito?.filter { it.nombre!!.toLowerCase().contains(texto.toLowerCase())}

        }
        notifyDataSetChanged()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null) {
            //relacionamos la vista con el layout correspondiente
            //en este caso elemento_lista_categoria
            vista = layoutInflater.inflate(R.layout.elemento_lista_buscar_distrito, p2, false)
        }
        val objdistrito=getItem(p0) as Distrito
        //creamos los controles
        val lstCodDis= vista!!.findViewById<TextView>(R.id.lstCodDis)
        val lstNomDis= vista!!.findViewById<TextView>(R.id.lstNomDis)

        val lstEstDis= vista!!.findViewById<TextView>(R.id.lstEstDis)
        //agregamos los valores a la lista
       lstCodDis.text="Codigo: "+objdistrito.codigo
        lstNomDis.text="Nombre:"+objdistrito.nombre

        if(objdistrito.estado==true){
            lstEstDis.text="Estado: Habilitado"
        }else{
            lstEstDis.text="Estado: Deshabilitado"
        }
        return vista!!
    }
}