package com.example.sistema_modas.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R
import com.example.sistema_modas.clases.Categoria

class AdaptadorCategoria(context: Context?, private val listacategoria:List<Categoria>?):
    BaseAdapter() {
    private val layoutInflater:LayoutInflater
    init {
        layoutInflater= LayoutInflater.from(context)
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
            vista=layoutInflater.inflate(R.layout.elemento_lista_categoria,p2,false)
            val objcategoria=getItem(p0) as Categoria
            //creamos los controles
            val lstCodCat=vista!!.findViewById<TextView>(R.id.lstCodCat)
            val lstNomCat=vista!!.findViewById<TextView>(R.id.lstNomCat)
            val lstEstCat=vista!!.findViewById<TextView>(R.id.lstEstCat)
            //agregamos valores a los contrales
            lstCodCat.text=""+objcategoria.codigo
            lstNomCat.text=""+objcategoria.nombre
            if(objcategoria.estado==true){
                lstEstCat.text="Habilitado"
            }else{
                lstEstCat.text="Deshabilitado"
            }
        }
        return vista!!
    }
}
