package com.example.sistema_modas.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R

import com.example.sistema_modas.clases.Distrito

class AdaptadorDistrito (context: Context?, private val listaDistrito:List<Distrito>?):
    BaseAdapter() {
    private val layoutInflater: LayoutInflater
    init {
        layoutInflater= LayoutInflater.from(context)
    }
    override fun getCount(): Int {
        return listaDistrito!!.size
    }

    override fun getItem(p0: Int): Any {
        return listaDistrito!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            vista=layoutInflater.inflate(R.layout.elemento_lista_distrito,p2,false)
            val objdistrito=getItem(p0) as Distrito
            //creamos los controles
            val lstCodCat=vista!!.findViewById<TextView>(R.id.lstCodDis)
            val lstNomCat=vista!!.findViewById<TextView>(R.id.lstNomDis)
            val lstEstCat=vista!!.findViewById<TextView>(R.id.lstEstDis)
            //agregamos valores a los contrales
            lstCodCat.text=""+objdistrito.codigo
            lstNomCat.text=""+objdistrito.nombre
            if(objdistrito.estado==true){
                lstEstCat.text="Habilitado"
            }else{
                lstEstCat.text="Deshabilitado"
            }
        }
        return vista!!
    }
}