package com.example.sistema_modas.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R
import com.example.sistema_modas.clases.UsuarioRol


class AdaptadorUsuarioRol(context: Context?, private val listausuariorol:List<UsuarioRol>?): BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater= LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listausuariorol!!.size
    }

    override fun getItem(p0: Int): Any {
        return listausuariorol!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            vista=layoutInflater.inflate(R.layout.elemento_lista_usuario,p2,false)
            val objusuariorol=getItem(p0) as UsuarioRol
            //creamos los controles
            val lstCodUsuRol= vista!!.findViewById<TextView>(R.id.lstCodUsuRol)
            val lstUsuUsuRol= vista!!.findViewById<TextView>(R.id.lstUsuUsuRol)
            val lstRolUsuRol= vista!!.findViewById<TextView>(R.id.lstRolUsuaRol)
            val lstEstUsuRol= vista!!.findViewById<TextView>(R.id.lstEstUsuRol)
            //agregamos los valores a la lista
            lstCodUsuRol.text=""+objusuariorol.codigo
            lstUsuUsuRol.text=""+ objusuariorol.usuario!!.usuario
            lstRolUsuRol.text=""+ objusuariorol.rol!!.nombre
            if(objusuariorol.estado==true){
                lstEstUsuRol.text="Habilitado"
            }else{
                lstEstUsuRol.text="Deshabilitado"
            }
        }
        return vista!!
    }
}