package com.example.sistema_modas.adaptadores.busqueda

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R

import com.example.sistema_modas.clases.Venta

class AdaptadorBuscarVenta (context: Context?, private val listabuscarventa:List<Venta>?): BaseAdapter() {
    private val layoutInflater: LayoutInflater
    private var listaFiltrada: List<Venta>? = null

    init {
        layoutInflater= LayoutInflater.from(context)
        listaFiltrada = listabuscarventa
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
            listabuscarventa
        } else {
            listabuscarventa?.filter { it.fecha!!.toLowerCase().contains(texto.toLowerCase())  }

        }
        notifyDataSetChanged()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null) {
            //relacionamos la vista con el layout correspondiente
            //en este caso elemento_lista_categoria
            vista = layoutInflater.inflate(R.layout.elemento_listar_buscar_venta, p2, false)
        }
        val objventa=getItem(p0) as Venta
        //creamos los controles
        val lstCodVenta= vista!!.findViewById<TextView>(R.id.lstCodVenta)
        val lstfechaVenta= vista!!.findViewById<TextView>(R.id.lstfechaVenta)
        val lstCantidadVenta= vista!!.findViewById<TextView>(R.id.lstCantidadVenta)

        val lstEstPre= vista!!.findViewById<TextView>(R.id.lstEstVenta)
        //agregamos los valores a la lista
        lstCodVenta.text="Codigo: "+objventa.codigo
        lstfechaVenta.text="Fecha:"+objventa.fecha
        lstCantidadVenta.text="Cantidad :"+objventa.cantidad


        if(objventa.estado==true){
            lstEstPre.text="Estado: Habilitado"
        }else{
            lstEstPre.text="Estado: Deshabilitado"
        }
        return vista!!
    }
}
