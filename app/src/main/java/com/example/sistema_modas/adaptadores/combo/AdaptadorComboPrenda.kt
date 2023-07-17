package com.example.sistema_modas.adaptadores.combo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R
import com.example.sistema_modas.clases.Prenda


class AdaptadorComboPrenda (context: Context?, private val listaprenda: List<Prenda>?): BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listaprenda!!.size
    }

    override fun getItem(p0: Int): Any {
        return listaprenda!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){

            vista=layoutInflater.inflate(R.layout.elemento_combo_prenda,p2,false)
            val objprenda=getItem(p0) as Prenda
            //creamos los controles
            val lblNomPrenda= vista!!.findViewById<TextView>(R.id.lblNomPrenda)

            //agregamos los valores a la lista
            lblNomPrenda.text=""+objprenda.descripcion
        }
        return vista!!
    }
}
