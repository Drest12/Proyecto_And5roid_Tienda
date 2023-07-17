package com.example.sistema_modas.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R
import com.example.sistema_modas.clases.Cliente


class AdaptadorCliente(context: Context?, private val listacliente: List<Cliente>?): BaseAdapter()
{
    private val layoutInflater: LayoutInflater
    init {
        layoutInflater= LayoutInflater.from(context)
    }
    override fun getCount(): Int {
        return listacliente!!.size
    }

    override fun getItem(p0: Int): Any {
        return listacliente!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            vista=layoutInflater.inflate(R.layout.elemento_lista_cliente,p2,false)

            val objcategoria=getItem(p0) as Cliente
            //creamos los controles
            val lstCodCli=vista!!.findViewById<TextView>(R.id.lstCodCli)
            val lstNomCli=vista!!.findViewById<TextView>(R.id.lstNomCli)
            val lstDni=vista!!.findViewById<TextView>(R.id.lstDni)
            val lstcorreo=vista!!.findViewById<TextView>(R.id.lstcorreo)
            val lstEstCli=vista!!.findViewById<TextView>(R.id.lstEstCli)
            //agregamos valores a los contrales
            lstCodCli.text=""+objcategoria.codigo
            lstNomCli.text=""+objcategoria.nombre

            //lstTelefono.text=""+objcategoria.telefono
            lstDni.text=""+objcategoria.dni
            //lstdireccion.text=""+objcategoria.direccion
            lstcorreo.text=""+objcategoria.correo
            if(objcategoria.estado==true){
                lstEstCli.text="Habilitado"
            }else{
                lstEstCli.text="Deshabilitado"
            }
        }
        return vista!!
    }
}