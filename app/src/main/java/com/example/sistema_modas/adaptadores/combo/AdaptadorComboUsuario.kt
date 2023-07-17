package com.example.sistema_modas.adaptadores.combo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R
import com.example.sistema_modas.clases.Usuario


class AdaptadorComboUsuario(context: Context?, private val listausuario:List<Usuario>?): BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listausuario!!.size
    }

    override fun getItem(p0: Int): Any {
        return listausuario!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            vista=layoutInflater.inflate(R.layout.elemento_combo_usuario,p2,false)
            val objusuario=getItem(p0) as Usuario
            val lblNomUsu= vista!!.findViewById<TextView>(R.id.lblNomUsu)
            lblNomUsu.text=""+objusuario.usuario
        }
        return vista!!
    }
}
