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
import com.example.sistema_modas.adaptadores.AdaptadorDetalleVenta
import com.example.sistema_modas.adaptadores.combo.AdaptadorComboPrenda
import com.example.sistema_modas.adaptadores.combo.AdaptadorComboVenta
import com.example.sistema_modas.clases.*
import com.example.sistema_modas.databinding.FragmentoDetalleVentaBinding

import com.example.sistema_modas.remoto.ApiUtil
import com.example.sistema_modas.service.*
import com.example.sistema_modas.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Fragmento_DetalleVenta : Fragment() {
    private lateinit var txtcantidad: EditText
    private lateinit var txtprecio: EditText
    private lateinit var cboprenda: Spinner
    private lateinit var cboventa: Spinner
    private lateinit var lblCodDT: TextView
    private lateinit var btnRegistrar: Button
    private lateinit var btnActualizar: Button
    private lateinit var lsldt: ListView

    //creamos un objeto de la clase
    val objdetalleventa= DetalleVenta()
    private  val objprenda=Prenda()
    private val objventa= Venta()


    //declaramos variables
    private var cod=0L
    private var cantidad=""
    private var precio=""
    private var fila=-1
    private var codprenda=0L
    private var codventa=0L
    private var nombreprenda=""
    private var nombreventa=""
    private var indice=-1
    private var pos=-1
    //creamos un arraylist de
    private var registroVenta:List<Venta>?=null
    private var registroDetalleVenta:List<DetalleVenta>?=null
    private var registroPrenda:List<Prenda>?=null
    //declaramos el servicio
    private  var prendaService: PrendaService?=null
    private var ventaService: VentaService?=null
    private var detalleVentaService: DetalleVentaService?=null
    //creamos un obejto de la clase utilidad
    var objutilidad= Util()
    //creamos una transicion para el fragmento
    var ft: FragmentTransaction?=null
    private var dialogo: AlertDialog.Builder?=null
    private var _binding: FragmentoDetalleVentaBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val raiz=inflater.inflate(R.layout.fragmento__detalle_venta,container,false)

        txtcantidad=raiz.findViewById(R.id.txtcantidad)
        txtprecio=raiz.findViewById(R.id.txtprecio)
        lblCodDT=raiz.findViewById(R.id.lblCodDT)
        cboventa=raiz.findViewById(R.id.cboventa)
        cboprenda=raiz.findViewById(R.id.cboprenda)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrarDT)
        btnActualizar=raiz.findViewById(R.id.btnActualizarDT)
        lsldt=raiz.findViewById(R.id.lsldt)

        //creamos el arraylist de
        registroVenta=ArrayList()
        registroDetalleVenta=ArrayList()
        registroPrenda=ArrayList()

        //implementamos el servicio
        ventaService= ApiUtil.ventaService
        detalleVentaService= ApiUtil.detalleVentaService
        prendaService=ApiUtil.prendaService
        //mostramos las
        MostrarComboPrenda(raiz.context)
        MostrarComboVenta(raiz.context)

        MostrarDetalleVenta(raiz.context)

        //agregamos los eventos
        btnRegistrar.setOnClickListener {
            if(txtprecio.getText().toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingrese el precio")
                txtprecio.requestFocus()
            }

            else if(txtcantidad.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el cantidad")
                txtcantidad.requestFocus()
            }


            else if(cboventa.selectedItemPosition==-1){
                objutilidad.MensajeToast(raiz.context,"Seleccione una venta")
                cboventa.requestFocus()
            }
            else if(cboprenda.selectedItemPosition==-1){
                objutilidad.MensajeToast(raiz.context,"Seleccione una prenda")
                cboprenda.requestFocus()
            }
            else{
                //capturando valores
                precio=txtprecio.getText().toString()
                cantidad=txtcantidad.getText().toString()

                pos=cboventa.selectedItemPosition
                pos=cboprenda.selectedItemPosition
                codventa= (registroVenta as ArrayList<Venta>).get(pos).codigo
                nombreventa= (registroVenta as ArrayList<Venta>).get(pos).fecha.toString()
                codprenda= (registroPrenda as ArrayList<Prenda>).get(pos).codigo
                nombreprenda= (registroPrenda as ArrayList<Prenda>).get(pos).descripcion.toString()

                //enviamos los valores a la clase
                objdetalleventa.precio=precio
                objdetalleventa.cantidad=cantidad

                objprenda.codigo=codprenda
                objventa.codigo=codventa
                objdetalleventa.venta=objventa
                objdetalleventa.prenda=objprenda

                //llamamos al metodo para registra
                RegistrarDetalleVenta(raiz.context,objdetalleventa)
                //actualizamos el fragmento
                val fcli=Fragmento_DetalleVenta()
                DialogoCRUD("Registro de Detalle Venta","Se registro la Detalle de Venta",fcli)
            }
        }

        lsldt.setOnItemClickListener { adapterView, view, i, l ->
            fila=i

           lblCodDT.setText(""+(registroDetalleVenta as ArrayList<DetalleVenta>).get(fila).codigo)
            txtprecio.setText(""+(registroDetalleVenta as ArrayList<DetalleVenta>).get(fila).precio)
            txtcantidad.setText(""+(registroDetalleVenta as ArrayList<DetalleVenta>).get(fila).cantidad)


            for(x in (registroPrenda as ArrayList<Prenda>).indices){
                if((registroPrenda as ArrayList<Prenda>).get(x).descripcion== (registroDetalleVenta as ArrayList<DetalleVenta>).get(fila).prenda?.descripcion){
                    indice=x
                }
            }
            for(x in (registroVenta as ArrayList<Venta>).indices){
            if((registroVenta as ArrayList<Venta>).get(x).fecha== (registroDetalleVenta as ArrayList<DetalleVenta>).get(fila).venta?.fecha){
                indice=x
            }
        }
            cboventa.setSelection(indice)
            cboprenda.setSelection(indice)

        }
        btnActualizar.setOnClickListener {
            if(fila>=0){
                //capturando valores
                cod= lblCodDT .text.toString().toLong()
                precio=txtprecio.getText().toString()
                cantidad=txtcantidad.getText().toString()
                pos=cboventa.selectedItemPosition
                pos=cboprenda.selectedItemPosition
                codprenda= (registroPrenda as ArrayList<Prenda>).get(pos).codigo
                nombreprenda= (registroPrenda as ArrayList<Prenda>).get(pos).descripcion.toString()
                codventa= (registroVenta as ArrayList<Venta>).get(pos).codigo
                nombreventa= (registroVenta as ArrayList<Venta>).get(pos).fecha.toString()


                //enviamos los valores a la clase
                objdetalleventa.precio=precio
                objdetalleventa.cantidad=cantidad

                objprenda.codigo=codprenda
                objventa.codigo=codventa
                objdetalleventa.venta=objventa
                objdetalleventa.prenda=objprenda
                ActualizarDetalleVenta(raiz.context,objdetalleventa,cod)
                val fdetalle=Fragmento_DetalleVenta()
                DialogoCRUD("Actualizacion de Detalle Venta","Se actualizo el venta correctamente",fdetalle)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lsldt.requestFocus()
            }

        }



        return raiz

    }



    //creamos una funcion para mostrar el combo de la distrito
    fun MostrarComboPrenda(context: Context?){
        val call= prendaService!!.MostrarPrendaPersonalizado()
        call!!.enqueue(object :Callback<List<Prenda>?>{
            override fun onResponse(
                call: Call<List<Prenda>?>,
                response: Response<List<Prenda>?>
            ) {
                if(response.isSuccessful){
                    registroPrenda=response.body()
                    cboprenda.adapter= AdaptadorComboPrenda(context,registroPrenda)
                }
            }
            override fun onFailure(call: Call<List<Prenda>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para mostrar el combo de la distrito
    fun MostrarComboVenta(context: Context?){
        val call= ventaService!!.MostrarVentaPersonalizado()
        call!!.enqueue(object :Callback<List<Venta>?>{
            override fun onResponse(
                call: Call<List<Venta>?>,
                response: Response<List<Venta>?>
            ) {
                if(response.isSuccessful){
                    registroVenta=response.body()
                    cboventa.adapter= AdaptadorComboVenta(context,registroVenta)
                }
            }
            override fun onFailure(call: Call<List<Venta>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos la funcion para mostrar
    fun MostrarDetalleVenta(context: Context?){
        val call= detalleVentaService!!.MostrarDetalleVenta()
        call!!.enqueue(object : Callback<List<DetalleVenta>?> {
            override fun onResponse(
                call: Call<List<DetalleVenta>?>,
                response: Response<List<DetalleVenta>?>
            ) {
                if(response.isSuccessful){
                    registroDetalleVenta=response.body()
                    lsldt.adapter= AdaptadorDetalleVenta(context,registroDetalleVenta)
                }
            }
            override fun onFailure(call: Call<List<DetalleVenta>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }
        })
    }

    //creamos una funcion para registrar
    fun RegistrarDetalleVenta(context: Context?, emp: DetalleVenta){
        val call= detalleVentaService!!.RegistrarDetalleVenta(emp)
        call!!.enqueue(object : Callback<DetalleVenta?> {
            override fun onResponse(call: Call<DetalleVenta?>, response: Response<DetalleVenta?>) {
                if(response.isSuccessful){
                    objutilidad.MensajeToast(context!!,"Se registro la Detalle Venta")
                }
            }

            override fun onFailure(call: Call<DetalleVenta?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }


    //creamos una funcion para actualizar producto
    fun ActualizarDetalleVenta(context: Context?, p: DetalleVenta?, id:Long){
        val call= detalleVentaService!!.ActualizarDetalleVenta(id,p)
        call!!.enqueue(object :Callback<DetalleVenta?>{
            override fun onResponse(call: Call<DetalleVenta?>, response: Response<DetalleVenta?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizo")
                }
            }

            override fun onFailure(call: Call<DetalleVenta?>, t: Throwable) {
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

