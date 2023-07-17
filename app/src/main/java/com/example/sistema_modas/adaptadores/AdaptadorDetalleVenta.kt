package com.example.sistema_modas.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R
import com.example.sistema_modas.clases.DetalleVenta



class AdaptadorDetalleVenta (context: Context?, private val listadetalleventa:List<DetalleVenta>?):
    BaseAdapter() {
    private val layoutInflater: LayoutInflater
    init {
        layoutInflater= LayoutInflater.from(context)
    }
    override fun getCount(): Int {
        return listadetalleventa!!.size
    }

    override fun getItem(p0: Int): Any {
        return listadetalleventa!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            vista=layoutInflater.inflate(R.layout.elemento_lista_detalleventa,p2,false)
            val objdetalleventa=getItem(p0) as DetalleVenta
            //creamos los controles
            val lstCodDT=vista!!.findViewById<TextView>(R.id.lstCodDT)
            val lstPrecioDT=vista!!.findViewById<TextView>(R.id.lstPrecioDT)
            val lstCantDT=vista!!.findViewById<TextView>(R.id.lstCantDT )
            //agregamos valores a los contrales
            lstCodDT.text=""+objdetalleventa.codigo
            lstPrecioDT.text=""+objdetalleventa.precio
            lstCantDT.text=""+objdetalleventa.cantidad

        }
        return vista!!
    }
}