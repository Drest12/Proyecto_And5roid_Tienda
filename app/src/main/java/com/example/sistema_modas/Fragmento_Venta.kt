package com.example.sistema_modas

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

import com.example.sistema_modas.adaptadores.AdaptadorVenta
import com.example.sistema_modas.adaptadores.combo.AdaptadorComboCliente
import com.example.sistema_modas.adaptadores.combo.AdaptadorComboEmpleado

import com.example.sistema_modas.clases.*

import com.example.sistema_modas.databinding.FragmentoVentaBinding
import com.example.sistema_modas.remoto.ApiUtil
import com.example.sistema_modas.service.*
import com.example.sistema_modas.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Fragmento_Venta : Fragment() {
    private lateinit var txtCanVenta: EditText
    private  lateinit var txtFechaVenta: EditText
    private lateinit var chkEst: CheckBox
    private lateinit var cbocli: Spinner
    private lateinit var cboemp: Spinner
    private lateinit var lblCodVenta: TextView
    private lateinit var btnRegistrar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var lslVenta: ListView
    //creamos un objeto de la clase
    val objventa= Venta()
    private  val objempleado= Empleado()
    private val objcliente= Cliente()
    //declaramos variables
    private var cod=0L
    private var cantidad=""
    private var fecha=""
    private var est=false
    private var fila=-1
    private var codempleado=0L
    private var codcliente=0L
    private var nomempleado=""
    private var nomcliente=""
    private var indice=-1
    private var pos=-1

    //creamos un arraylist de
    private var registroVenta:List<Venta>?=null
    private var registroEmpleado:List<Empleado>?=null
    private var registroCliente:List<Cliente>?=null
    //declaramos el servicio
    private  var empleadoService: EmpleadoService?=null
    private var clienteService: ClienteService?=null
    private var ventaService: VentaService?=null

    //creamos un obejto de la clase utilidad
    var objutilidad= Util()
    //creamos una transicion para el fragmento
    var ft: FragmentTransaction?=null
    private var dialogo: AlertDialog.Builder?=null
        private var _binding: FragmentoVentaBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val raiz=inflater.inflate(R.layout.fragmento__venta,container,false)

        txtCanVenta=raiz.findViewById(R.id.txtCanventa)
        txtFechaVenta=raiz.findViewById(R.id.txtFechaventa)

        cbocli=raiz.findViewById(R.id.cbocli)
        cboemp=raiz.findViewById(R.id.cboemp)

        chkEst=raiz.findViewById(R.id.chkEstVenta)
        lblCodVenta=raiz.findViewById(R.id.lblCodVenta)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrarVenta)
        btnActualizar=raiz.findViewById(R.id.btnActualizarVenta)
        btnEliminar=raiz.findViewById(R.id.btnEliminarVenta)
        lblCodVenta=raiz.findViewById(R.id.lblCodVenta)
        lslVenta=raiz.findViewById(R.id.lslventa)

        //creamos el arraylist de
        registroEmpleado=ArrayList()
        registroVenta=ArrayList()
        registroCliente=ArrayList()

        //implementamos el servicio
        empleadoService= ApiUtil.empleadoService
        ventaService= ApiUtil.ventaService
        clienteService= ApiUtil.clienteService
        //mostramos las
        MostrarComboEmpleado(raiz.context)
        MostrarComboCliente(raiz.context)

        MostrarVenta(raiz.context)

        //agregamos los eventos
        btnRegistrar.setOnClickListener {
            if(txtCanVenta.getText().toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingrese el cantidad")
                txtCanVenta.requestFocus()
            }

            else if(txtFechaVenta.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el fecha")
                txtFechaVenta.requestFocus()
            }


            else if(cbocli.selectedItemPosition==-1){
                objutilidad.MensajeToast(raiz.context,"Seleccione una cliente")
                cbocli.requestFocus()
            }
            else if(cboemp.selectedItemPosition==-1){
                objutilidad.MensajeToast(raiz.context,"Seleccione una empleado")
                cboemp.requestFocus()
            }
            else{
                //capturando valores
                cantidad=txtCanVenta.getText().toString()
                fecha=txtFechaVenta.getText().toString()

                pos=cbocli.selectedItemPosition
                pos=cboemp.selectedItemPosition
                codcliente= (registroCliente as ArrayList<Cliente>).get(pos).codigo
                nomcliente= (registroCliente as ArrayList<Cliente>).get(pos).nombre.toString()
                codempleado= (registroEmpleado as ArrayList<Empleado>).get(pos).codigo
                nomempleado= (registroEmpleado as ArrayList<Empleado>).get(pos).nombre.toString()
                est=if(chkEst.isChecked){
                    true
                }else{
                    false
                }
                //enviamos los valores a la clase
                objventa.fecha=fecha
                objventa.cantidad=cantidad

                objempleado.codigo=codempleado
                objcliente.codigo=codcliente
                objventa.cliente=objcliente
                objventa.empleado=objempleado
                objventa.estado=est
                //llamamos al metodo para registra
                RegistrarVenta(raiz.context,objventa)
                //actualizamos el fragmento
                val fcli=Fragmento_Venta()
                DialogoCRUD1("Registro de Venta","Se registro la Venta",fcli)
            }
        }

        lslVenta.setOnItemClickListener { adapterView, view, i, l ->
            fila=i

            lblCodVenta.setText(""+(registroVenta as ArrayList<Venta>).get(fila).codigo)
            txtCanVenta.setText(""+(registroVenta as ArrayList<Venta>).get(fila).cantidad)
            txtFechaVenta.setText(""+(registroVenta as ArrayList<Venta>).get(fila).fecha)

            for(x in (registroCliente as ArrayList<Cliente>).indices){
                if((registroCliente as ArrayList<Cliente>).get(x).nombre== (registroVenta as ArrayList<Venta>).get(fila).cliente?.nombre){
                    indice=x
                }
            }
            for(x in (registroEmpleado as ArrayList<Empleado>).indices){
                if((registroEmpleado as ArrayList<Empleado>).get(x).nombre== (registroVenta as ArrayList<Venta>).get(fila).empleado?.nombre){
                    indice=x
                }
            }
            cboemp.setSelection(indice)
            cbocli.setSelection(indice)
            if((registroVenta as ArrayList<Venta>).get(fila).estado){
                chkEst.setChecked(true)
            }else{
                chkEst.setChecked(false)
            }
        }

        btnActualizar.setOnClickListener {
            if(fila>=0){
                //capturando valores
                cod= lblCodVenta .text.toString().toLong()
                cantidad=txtCanVenta.getText().toString()
                fecha=txtFechaVenta.getText().toString()
                pos=cbocli.selectedItemPosition
                pos=cboemp.selectedItemPosition
                codcliente= (registroCliente as ArrayList<Cliente>).get(pos).codigo
                nomcliente= (registroCliente as ArrayList<Cliente>).get(pos).nombre.toString()
                codempleado= (registroEmpleado as ArrayList<Empleado>).get(pos).codigo
                nomempleado= (registroEmpleado as ArrayList<Empleado>).get(pos).nombre.toString()
                est=if(chkEst.isChecked){
                    true
                }else{
                    false
                }

                //enviamos los valores a la clase
                objventa.fecha=fecha
                objventa.cantidad=cantidad

                objempleado.codigo=codempleado
                objcliente.codigo=codcliente
                objventa.cliente=objcliente
                objventa.empleado=objempleado
                objventa.estado=est
                ActualizarVenta(raiz.context,objventa,cod)
                val fempleado=Fragmento_Venta()
                DialogoCRUD1("Actualizacion de Venta","Se actualizo el venta correctamente",fempleado)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lslVenta.requestFocus()
            }

        }
        btnEliminar.setOnClickListener {
            if(fila>=0){
                cod=lblCodVenta.text.toString().toLong()
                cantidad=txtCanVenta.text.toString()


                //llamamos a la funcion para registrar
                EliminarVenta(raiz.context,cod)
                val fcliente=Fragmento_Venta()
                DialogoCRUD("Eliminacion de Venta","Se elimino el venta correctamente",fcliente)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lslVenta.requestFocus()
            }
        }




        return raiz

    }
    //creamos una funcion para mostrar el combo de la distrito
    fun MostrarComboCliente(context: Context?){
        val call= clienteService!!.MostrarClientePersonalizado()
        call!!.enqueue(object : Callback<List<Cliente>?> {
            override fun onResponse(
                call: Call<List<Cliente>?>,
                response: Response<List<Cliente>?>
            ) {
                if(response.isSuccessful){
                    registroCliente=response.body()
                    cbocli.adapter= AdaptadorComboCliente(context,registroCliente)
                }
            }
            override fun onFailure(call: Call<List<Cliente>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }
    //creamos una funcion para mostrar el combo de la distrito
    fun MostrarComboEmpleado(context: Context?){
        val call= empleadoService!!.MostrarEmpleadoPersonalizado()
        call!!.enqueue(object :Callback<List<Empleado>?>{
            override fun onResponse(
                call: Call<List<Empleado>?>,
                response: Response<List<Empleado>?>
            ) {
                if(response.isSuccessful){
                    registroEmpleado=response.body()
                    cboemp.adapter= AdaptadorComboEmpleado(context,registroEmpleado)
                }
            }
            override fun onFailure(call: Call<List<Empleado>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }
    //creamos la funcion para mostrar
    fun MostrarVenta(context: Context?){
        val call= ventaService!!.MostrarVentaPersonalizado()
        call!!.enqueue(object : Callback<List<Venta>?> {
            override fun onResponse(
                call: Call<List<Venta>?>,
                response: Response<List<Venta>?>
            ) {
                if(response.isSuccessful){
                    registroVenta=response.body()
                    lslVenta.adapter= AdaptadorVenta(context,registroVenta)
                }
            }

            override fun onFailure(call: Call<List<Venta>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para registrar
    fun RegistrarVenta(context: Context?, ven: Venta){
        val call= ventaService!!.RegistrarVenta(ven)
        call!!.enqueue(object : Callback<Venta?> {
            override fun onResponse(call: Call<Venta?>, response: Response<Venta?>) {
                if(response.isSuccessful){
                    objutilidad.MensajeToast(context!!,"Se registro la venta")
                }
            }

            override fun onFailure(call: Call<Venta?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }
    //creamos una funcion para actualizar producto
    fun ActualizarVenta(context: Context?, p: Venta?, id:Long){
        val call= ventaService!!.ActualizarVenta(id,p)
        call!!.enqueue(object :Callback<Venta?>{
            override fun onResponse(call: Call<Venta?>, response: Response<Venta?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizo")
                }
            }

            override fun onFailure(call: Call<Venta?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun EliminarVenta(context: Context?,id:Long){
        val call= ventaService!!.EliminarVenta(id)
        call!!.enqueue(object :Callback<Venta?>{
            override fun onResponse(call: Call<Venta?>, response: Response<Venta?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se elimino")
                }
            }

            override fun onFailure(call: Call<Venta?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }


    //creamos una funcion para los cuadros de dialogo del CRUD
    fun DialogoCRUD(titulo:String,mensaje:String,fragmento:Fragment){
        dialogo= AlertDialog.Builder(context)
        dialogo!!.setTitle(titulo)
        dialogo!!.setMessage(mensaje)
        dialogo!!.setCancelable(false)
        dialogo!!.setPositiveButton("Ok"){
                dialogo,which->
            ft=fragmentManager?.beginTransaction()
            ft?.replace(R.id.contenedor,fragmento,null)
            ft?.addToBackStack(null)
            ft?.commit()
        }
        dialogo!!.setNegativeButton("Cancelar") { dialogo, which ->

        }
        dialogo!!.show()
    }

    //creamos una funcion para los cuadros de dialogo del CRUD
    fun DialogoCRUD1(titulo:String,mensaje:String,fragmento:Fragment){
        dialogo= AlertDialog.Builder(context)
        dialogo!!.setTitle(titulo)
        dialogo!!.setMessage(mensaje)
        dialogo!!.setCancelable(false)
        dialogo!!.setPositiveButton("Ok"){
                dialogo,which->
            ft=fragmentManager?.beginTransaction()
            ft?.replace(R.id.contenedor,fragmento,null)
            ft?.addToBackStack(null)
            ft?.commit()
        }
        dialogo!!.show()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}