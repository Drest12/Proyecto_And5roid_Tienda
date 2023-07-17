package com.example.sistema_modas.adaptadores.busqueda

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R
import com.example.sistema_modas.clases.DetalleVenta


class AdaptadorBuscarDetalleVenta (context: Context?, private val listabuscardetalleventa:List<DetalleVenta>?): BaseAdapter() {
    private val layoutInflater: LayoutInflater
    private var listaFiltrada: List<DetalleVenta>? = null

    init {
        layoutInflater= LayoutInflater.from(context)
        listaFiltrada = listabuscardetalleventa
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
            listabuscardetalleventa
        } else {
            listabuscardetalleventa?.filter { it.precio!!.toLowerCase().contains(texto.toLowerCase())  }

        }
        notifyDataSetChanged()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null) {
            //relacionamos la vista con el layout correspondiente
            //en este caso elemento_lista_categoria
            vista = layoutInflater.inflate(R.layout.elemento_lista_detalleventa, p2, false)
        }
        val objdetalleventa=getItem(p0) as DetalleVenta
        //creamos los controles
        val lstCodDetalleVenta= vista!!.findViewById<TextView>(R.id.lstCodDetalleVenta)
        val lstPrecioDetalleVenta= vista!!.findViewById<TextView>(R.id.lstPrecioDetalleVenta)
        val lstCantidadDetalleVenta= vista!!.findViewById<TextView>(R.id.lstCantidadDetalleVenta)


        //agregamos los valores a la lista
        lstCodDetalleVenta.text="Codigo: "+objdetalleventa.codigo
        lstPrecioDetalleVenta.text="Precio:"+objdetalleventa.precio

        lstCantidadDetalleVenta.text="Talla :"+objdetalleventa.cantidad



        return vista!!
    }
}
