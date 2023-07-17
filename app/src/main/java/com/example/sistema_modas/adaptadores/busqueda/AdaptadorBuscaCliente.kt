package com.example.sistema_modas.adaptadores.busqueda

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R
import com.example.sistema_modas.clases.Cliente

class AdaptadorBuscaCliente (context: Context?, private val listabuscarcliente:List<Cliente>?): BaseAdapter() {
    private val layoutInflater: LayoutInflater
    private var listaFiltrada: List<Cliente>? = null

    init {
        layoutInflater= LayoutInflater.from(context)
        listaFiltrada = listabuscarcliente
    }

    override fun getCount(): Int {
        return listaFiltrada!!.size
    }

    override fun getItem(p0: Int): Any {
        return listaFiltrada!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    fun filter(texto: String) {
        listaFiltrada = if (texto.isEmpty()) {
            listabuscarcliente
        } else {
            listabuscarcliente?.filter { it.nombre!!.toLowerCase().contains(texto.toLowerCase()) ||it.apellido!!.toLowerCase().contains(texto.toLowerCase())  ||it.dni!!.contains(texto)}

        }
        notifyDataSetChanged()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null) {
            //relacionamos la vista con el layout correspondiente
            //en este caso elemento_lista_categoria
            vista = layoutInflater.inflate(R.layout.elemento_listar_buscar_cliente, p2, false)
        }
        val objcliente=getItem(p0) as Cliente
        //creamos los controles
        val lstCodCli= vista!!.findViewById<TextView>(R.id.lstCodCli)
        val lstNomCli= vista!!.findViewById<TextView>(R.id.lstNomCli)
        val lstApeCli= vista!!.findViewById<TextView>(R.id.lstApeCli)

        val lstDniCli= vista!!.findViewById<TextView>(R.id.lstDniCli)
        val lstDirCli= vista!!.findViewById<TextView>(R.id.lstDirCli)
        val lstEstCli= vista!!.findViewById<TextView>(R.id.lstEstCli)
        //agregamos los valores a la lista
        lstCodCli.text="Codigo: "+objcliente.codigo
        lstNomCli.text="Nombre:"+objcliente.nombre
        lstApeCli.text="Apellido :"+objcliente.apellido

        lstDniCli.text="Dni:"+objcliente.dni
        lstDirCli.text="Direccion:"+objcliente.direccion
        if(objcliente.estado==true){
            lstEstCli.text="Estado: Habilitado"
        }else{
            lstEstCli.text="Estado: Deshabilitado"
        }
        return vista!!
    }
}

