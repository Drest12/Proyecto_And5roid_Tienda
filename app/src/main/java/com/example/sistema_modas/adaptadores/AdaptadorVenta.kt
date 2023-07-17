package com.example.sistema_modas.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R
import com.example.sistema_modas.clases.Empleado
import com.example.sistema_modas.clases.Venta

class AdaptadorVenta (context: Context?, private val listaVenta:List<Venta>?):
    BaseAdapter() {
    private val layoutInflater: LayoutInflater
    init {
        layoutInflater= LayoutInflater.from(context)
    }
    override fun getCount(): Int {
        return listaVenta!!.size
    }

    override fun getItem(p0: Int): Any {
        return listaVenta!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            vista=layoutInflater.inflate(R.layout.elemento_lista_venta,p2,false)
            val objventa=getItem(p0) as Venta
            //creamos los controles
            val lstCodVenta=vista!!.findViewById<TextView>(R.id.lstCodVenta)
            val lstCantidadVent=vista!!.findViewById<TextView>(R.id.lstCantidadVent)
            val lstfechaVenta=vista!!.findViewById<TextView>(R.id.lstfechaVenta)
            val lstEstVenta=vista!!.findViewById<TextView>(R.id.lstEstVenta)
            //agregamos valores a los contrales
            lstCodVenta.text=""+objventa.codigo
            lstCantidadVent.text=""+objventa.cantidad
            lstfechaVenta.text=""+objventa.fecha

            if(objventa.estado==true){
                lstEstVenta.text="Habilitado"
            }else{
                lstEstVenta.text="Deshabilitado"
            }
        }
        return vista!!
    }
}