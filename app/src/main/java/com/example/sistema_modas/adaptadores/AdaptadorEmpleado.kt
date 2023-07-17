package com.example.sistema_modas.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sistema_modas.R
import com.example.sistema_modas.clases.Empleado


class AdaptadorEmpleado (context: Context?, private val listaEmpleado:List<Empleado>?):
    BaseAdapter() {
    private val layoutInflater: LayoutInflater
    init {
        layoutInflater= LayoutInflater.from(context)
    }
    override fun getCount(): Int {
        return listaEmpleado!!.size
    }

    override fun getItem(p0: Int): Any {
        return listaEmpleado!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            vista=layoutInflater.inflate(R.layout.elemento_lista_empleado,p2,false)
            val objempleado=getItem(p0) as Empleado
            //creamos los controles
            val lstCodEmp=vista!!.findViewById<TextView>(R.id.lstCodEmp)
            val lstNomEmp=vista!!.findViewById<TextView>(R.id.lstNomEmp)
            val lstDni=vista!!.findViewById<TextView>(R.id.lstDni)
            val lstcorreo=vista!!.findViewById<TextView>(R.id.lstcorreo)
            val lstEstEmp=vista!!.findViewById<TextView>(R.id.lstEstEmp)
            //agregamos valores a los contrales
            lstCodEmp.text=""+objempleado.codigo
            lstNomEmp.text=""+objempleado.nombre
            lstDni.text=""+objempleado.dni
            lstcorreo.text=""+objempleado.correo
            if(objempleado.estado==true){
                lstEstEmp.text="Habilitado"
            }else{
                lstEstEmp.text="Deshabilitado"
            }
        }
        return vista!!
    }
}