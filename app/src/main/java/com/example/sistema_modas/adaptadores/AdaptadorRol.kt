package com.example.sistema_modas.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R
import com.example.sistema_modas.clases.Categoria

import com.example.sistema_modas.clases.Rol

class AdaptadorRol (context: Context?, private val listarol:List<Rol>?): BaseAdapter()
{
    private val layoutInflater: LayoutInflater
    init {
        layoutInflater= LayoutInflater.from(context)
    }
    override fun getCount(): Int {
        return listarol!!.size
    }

    override fun getItem(p0: Int): Any {
        return listarol!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            vista=layoutInflater.inflate(R.layout.elemento_lista_rol,p2,false)
            val objcategoria=getItem(p0) as Rol
            //creamos los controles
            val lstCodRol=vista!!.findViewById<TextView>(R.id.lstCodRol)
            val lstNomRol=vista!!.findViewById<TextView>(R.id.lstNomRol)
            val lstEstRol=vista!!.findViewById<TextView>(R.id.lstEstRol)
            //agregamos valores a los contrales
            lstCodRol.text=""+objcategoria.codigo
            lstNomRol.text=""+objcategoria.nombre
            if(objcategoria.estado==true){
                lstEstRol.text="Habilitado"
            }else{
                lstEstRol.text="Deshabilitado"
            }
        }
        return vista!!
    }
}