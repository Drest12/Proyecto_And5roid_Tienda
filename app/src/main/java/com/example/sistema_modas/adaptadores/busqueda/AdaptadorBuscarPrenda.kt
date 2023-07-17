package com.example.sistema_modas.adaptadores.busqueda

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R

import com.example.sistema_modas.clases.Prenda

class AdaptadorBuscarPrenda (context: Context?, private val listabuscarprenda:List<Prenda>?): BaseAdapter() {
    private val layoutInflater: LayoutInflater
    private var listaFiltrada: List<Prenda>? = null

    init {
        layoutInflater= LayoutInflater.from(context)
        listaFiltrada = listabuscarprenda
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
            listabuscarprenda
        } else {
            listabuscarprenda?.filter { it.descripcion!!.toLowerCase().contains(texto.toLowerCase()) ||it.talla!!.toLowerCase().contains(texto.toLowerCase())  ||it.precio!!.contains(texto)}

        }
        notifyDataSetChanged()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null) {
            //relacionamos la vista con el layout correspondiente
            //en este caso elemento_lista_categoria
            vista = layoutInflater.inflate(R.layout.elemento_listar_buscar_prenda, p2, false)
        }
        val objprenda=getItem(p0) as Prenda
        //creamos los controles
        val lstCodPre= vista!!.findViewById<TextView>(R.id.lstCodPre)
        val lstdescripcionPre= vista!!.findViewById<TextView>(R.id.lstdescripcionPre)
        val lstTallaPre= vista!!.findViewById<TextView>(R.id.lstTallaPre)

        val lstPrecioPre= vista!!.findViewById<TextView>(R.id.lstPrecioPre)
        val lstCantidadPre= vista!!.findViewById<TextView>(R.id.lstCantidadPre)
        val lstEstPre= vista!!.findViewById<TextView>(R.id.lstEstPre)
        //agregamos los valores a la lista
        lstCodPre.text="Codigo: "+objprenda.codigo
        lstdescripcionPre.text="Nombre:"+objprenda.descripcion
        lstTallaPre.text="Talla :"+objprenda.talla

        lstPrecioPre.text="Precio:"+objprenda.precio
        lstCantidadPre.text="Cantidad:"+objprenda.cantidad
        if(objprenda.estado==true){
            lstEstPre.text="Estado: Habilitado"
        }else{
            lstEstPre.text="Estado: Deshabilitado"
        }
        return vista!!
    }
}
