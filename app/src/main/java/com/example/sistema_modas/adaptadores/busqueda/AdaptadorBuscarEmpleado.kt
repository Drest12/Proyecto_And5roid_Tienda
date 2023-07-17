package com.example.sistema_modas.adaptadores.busqueda

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R
import com.example.sistema_modas.clases.Cliente

import com.example.sistema_modas.clases.Empleado

class AdaptadorBuscarEmpleado (context: Context?, private val listabuscarempleado:List<Empleado>?): BaseAdapter() {
    private val layoutInflater: LayoutInflater
    private var listaFiltrada: List<Empleado>? = null

    init {
        layoutInflater= LayoutInflater.from(context)
        listaFiltrada = listabuscarempleado
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
            listabuscarempleado
        } else {
            listabuscarempleado?.filter { it.nombre!!.toLowerCase().contains(texto.toLowerCase()) ||it.apellido!!.toLowerCase().contains(texto.toLowerCase())  ||it.dni!!.contains(texto)}

        }
        notifyDataSetChanged()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null) {
            //relacionamos la vista con el layout correspondiente
            //en este caso elemento_lista_categoria
            vista = layoutInflater.inflate(R.layout.elemento_listar_buscar_empleado, p2, false)
        }
        val objempleado=getItem(p0) as Empleado
        //creamos los controles
        val lstCodEmp= vista!!.findViewById<TextView>(R.id.lstCodEmp)
        val lstNomEmp= vista!!.findViewById<TextView>(R.id.lstNomEmp)
        val lstApeEmp= vista!!.findViewById<TextView>(R.id.lstApeEmp)
        val lstTelEmp=vista!!.findViewById<TextView>(R.id.lstTelefonoEmp)
        val lstDniEmp= vista!!.findViewById<TextView>(R.id.lstDniEmp)
        val lstDirEmp= vista!!.findViewById<TextView>(R.id.lstDirEmp)

        val lstCorreoEmp=vista!!.findViewById<TextView>(R.id.lstCorreoEmp)
        val lstEstEmp= vista!!.findViewById<TextView>(R.id.lstEstEmp)
        //agregamos los valores a la lista
        lstCodEmp.text="Codigo: "+objempleado.codigo
        lstNomEmp.text="Nombre:"+objempleado.nombre
        lstApeEmp.text="Apellido :"+objempleado.apellido
        lstTelEmp.text="Telefono : "+objempleado.telefono
        lstCorreoEmp.text="Correo  : "+objempleado.correo


        lstDniEmp.text="Dni:"+objempleado.dni
        lstDirEmp.text="Direccion:"+objempleado.direccion
        if(objempleado.estado==true){
            lstEstEmp.text="Estado: Habilitado"
        }else{
            lstEstEmp.text="Estado: Deshabilitado"
        }
        return vista!!
    }
}
