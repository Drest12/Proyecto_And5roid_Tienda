package com.example.sistema_modas.adaptadores.combo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R

import com.example.sistema_modas.clases.Venta

class AdaptadorComboVenta (context: Context?, private val listaventa: List<Venta>?): BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listaventa!!.size
    }

    override fun getItem(p1: Int): Any {
        return listaventa!![p1]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            vista=layoutInflater.inflate(R.layout.elemento_combo_venta,p2,false)
            val objventa=getItem(p0) as Venta
            //creamos los controles
            val lblFechaVenta= vista!!.findViewById<TextView>(R.id.lblFechaVenta)

            //agregamos los valores a la lista
            lblFechaVenta.text=""+objventa.fecha
        }
        return vista!!
    }
}