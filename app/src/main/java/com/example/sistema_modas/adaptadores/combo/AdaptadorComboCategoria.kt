package com.example.sistema_modas.adaptadores.combo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R
import com.example.sistema_modas.clases.Categoria


class AdaptadorComboCategoria (context: Context?, private val listacategoria:List<Categoria>?): BaseAdapter() {


    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listacategoria!!.size
    }

    override fun getItem(p0: Int): Any {
        return listacategoria!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            //relacionamos la vista con el layout correspondiente
            //en este caso elemento_lista_categoria
            vista=layoutInflater.inflate(R.layout.elemento_combo_categoria,p2,false)
            val objcategoria=getItem(p0) as Categoria
            //creamos los controles
            val lblNomCat= vista!!.findViewById<TextView>(R.id.lblNomCat)

            //agregamos los valores a la lista
            lblNomCat.text=""+objcategoria.nombre
        }
        return vista!!
    }
}
