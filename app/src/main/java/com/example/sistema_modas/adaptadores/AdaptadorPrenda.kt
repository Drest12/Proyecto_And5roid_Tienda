package com.example.sistema_modas.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R
import com.example.sistema_modas.clases.Prenda

class AdaptadorPrenda (context: Context?, private val listaprenda: List<Prenda>?): BaseAdapter()
{
    private val layoutInflater: LayoutInflater
    init {
        layoutInflater= LayoutInflater.from(context)
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
            vista=layoutInflater.inflate(R.layout.elemento_lista_prenda,p2,false)
            val objprenda=getItem(p0) as Prenda
            //creamos los controles
            val lstcodpre=vista!!.findViewById<TextView>(R.id.lstcodpre)
            val lstdespre=vista!!.findViewById<TextView>(R.id.lstdespre)
            val lstpreciopre=vista!!.findViewById<TextView>(R.id.lstpreciopre)
            val lsttallapre=vista!!.findViewById<TextView>(R.id.lsttallapre)
            val lstcantidadpre=vista!!.findViewById<TextView>(R.id.lstcantidadpre)
            val lstEstPre=vista!!.findViewById<TextView>(R.id.lstEstPre)
            //agregamos valores a los contrales
            lstcodpre.text=""+objprenda.codigo
            lstdespre.text=""+objprenda.descripcion
            lsttallapre.text=""+objprenda.talla
            lstpreciopre.text=""+objprenda.precio
            lstcantidadpre.text=""+objprenda.precio
            if(objprenda.estado==true){
                lstEstPre.text="Habilitado"
            }else{
                lstEstPre.text="Deshabilitado"
            }
        }
        return vista!!
    }
}