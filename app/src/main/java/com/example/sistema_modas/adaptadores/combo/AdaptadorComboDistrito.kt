package com.example.sistema_modas.adaptadores.combo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R
import com.example.sistema_modas.clases.Distrito

class AdaptadorComboDistrito(context: Context?, private val listadistrito:List<Distrito>?): BaseAdapter() {


    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listadistrito!!.size
    }

    override fun getItem(p0: Int): Any {
        return listadistrito!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            //relacionamos la vista con el layout correspondiente
            //en este caso elemento_lista_categoria
            vista=layoutInflater.inflate(R.layout.elemento_combo_distrito,p2,false)
            val objdistrito=getItem(p0) as Distrito
            //creamos los controles
            val lblNomDis= vista!!.findViewById<TextView>(R.id.lblNomDis)

            //agregamos los valores a la lista
            lblNomDis.text=""+objdistrito.nombre
        }
        return vista!!
    }
}
