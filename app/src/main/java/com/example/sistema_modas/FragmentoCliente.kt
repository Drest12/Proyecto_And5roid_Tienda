package com.example.sistema_modas
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import com.example.sistema_modas.adaptadores.AdaptadorCliente
import com.example.sistema_modas.adaptadores.combo.AdaptadorComboDistrito
import com.example.sistema_modas.clases.Cliente
import com.example.sistema_modas.clases.Distrito
import com.example.sistema_modas.databinding.FragmentoClienteBinding
import com.example.sistema_modas.remoto.ApiUtil
import com.example.sistema_modas.service.ClienteService
import com.example.sistema_modas.service.DistritoService
import com.example.sistema_modas.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentoCliente : Fragment() {

    private lateinit var txtNom: EditText
    private lateinit var txtApe: EditText
    private lateinit var editTelefono: EditText
    private lateinit var editDni: EditText
    private lateinit var txtGen: EditText
    private lateinit var txtDireccion: EditText
    private lateinit var txtemail: EditText
    private lateinit var chkEst: CheckBox
    private lateinit var cboDis: Spinner
    private lateinit var lblCodCli: TextView
    private lateinit var btnRegistrar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var lslCli: ListView

    //creamos un objeto de la clase
    val objcli= Cliente()
    private val objdistrito= Distrito()

    //declaramos variables
    private var cod=0L
    private var nom=""
    private var ape=""
    private var tel=""
    private var dir=""
    private var dni=""
    private var gen=""
    private var cor=""
    private var est=false
    private var fila=-1
    private var coddis=0L
    private var nomdis=""
    private var indice=-1
    private var pos=-1

    //declaramos el servicio
    private var clienteService: ClienteService?=null
    private var distritoService: DistritoService?=null

    //creamos un arraylist de
    private var registrocliente:List<Cliente>?=null
    private var registrodistrito:List<Distrito>?=null



    //creamos un obejto de la clase utilidad
    var objutilidad= Util()


    //creamos una transicion para el fragmento
    var ft: FragmentTransaction?=null

    private var dialogo: AlertDialog.Builder?=null
    private var _binding: FragmentoClienteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val raiz=inflater.inflate(R.layout.fragmento_cliente,container,false)

        txtNom=raiz.findViewById(R.id.txtNom)
        txtApe=raiz.findViewById(R.id.txtApe)
        editTelefono=raiz.findViewById(R.id.editTelefono)
        editDni=raiz.findViewById(R.id.editDni)
        txtGen=raiz.findViewById(R.id.txtGen)
        cboDis=raiz.findViewById(R.id.cboRol)
        txtDireccion=raiz.findViewById(R.id.txtDireccion)
        txtemail=raiz.findViewById(R.id.txtemail)
        chkEst=raiz.findViewById(R.id.chkEst)
        lblCodCli=raiz.findViewById(R.id.lblCodCli)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrar)
        btnActualizar=raiz.findViewById(R.id.btnActualizar)
        btnEliminar=raiz.findViewById(R.id.btnEliminar)
        lslCli=raiz.findViewById(R.id.lslCli)

        //creamos el arraylist de
        registrocliente=ArrayList()
        registrodistrito=ArrayList()

        //implementamos el servicio
        clienteService= ApiUtil.clienteService
        distritoService=ApiUtil.distritoService

        //mostramos las
        MostrarComboDistrito(raiz.context)
        MostrarCliente(raiz.context)

        //agregamos los eventos
        btnRegistrar.setOnClickListener {
            if(txtNom.getText().toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingrese el nombre")
                txtNom.requestFocus()
            }
            else if(txtApe.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el apellido")
                txtApe.requestFocus()
            }
            else if(txtemail.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el email")
                txtemail.requestFocus()
            }
            else if(txtDireccion.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el direccion")
                txtDireccion.requestFocus()
            }
            else if(editTelefono.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el telefono")
                editTelefono.requestFocus()
            }
            else if(editDni.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el dni")
                editDni.requestFocus()
            }
            else if(txtGen.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el genero")
                txtGen.requestFocus()
            }
            else if(cboDis.selectedItemPosition==-1){
                objutilidad.MensajeToast(raiz.context,"Seleccione una distrito")
                cboDis.requestFocus()
            }
            else{
                //capturando valores
                nom=txtNom.getText().toString()
                ape=txtApe.getText().toString()
                dir=txtDireccion.getText().toString()
                tel=editTelefono.getText().toString()
                dni=editDni.getText().toString()
                gen=txtGen.getText().toString()
                cor=txtemail.getText().toString()
                pos=cboDis.selectedItemPosition
                coddis= (registrodistrito as ArrayList<Distrito>).get(pos).codigo
                nomdis= (registrodistrito as ArrayList<Distrito>).get(pos).nombre.toString()
                est=if(chkEst.isChecked){
                    true
                }else{
                    false
                }
                //enviamos los valores a la clase
                objcli.nombre=nom
                objcli.apellido=ape
                objcli.telefono=tel
                objcli.dni=dni
                objcli.genero=gen
                objcli.direccion=dir
                objcli.correo=cor
                objdistrito.codigo=coddis
                objcli.distrito=objdistrito
                objcli.estado=est
                //llamamos al metodo para registrar
                RegistrarCliente(raiz.context,objcli)
               //actualizamos el fragmento
                val fcli=FragmentoCliente()
                DialogoCRUD1("Registro de Cliente","Se registro la cliente",fcli)
            }
        }

        lslCli.setOnItemClickListener { adapterView, view, i, l ->
            fila=i

                lblCodCli.setText(""+(registrocliente as ArrayList<Cliente>).get(fila).codigo)
                txtNom.setText(""+(registrocliente as ArrayList<Cliente>).get(fila).nombre)
                txtApe.setText(""+(registrocliente as ArrayList<Cliente>).get(fila).apellido)
                txtGen.setText(""+(registrocliente as ArrayList<Cliente>).get(fila).genero)
                txtemail.setText(""+(registrocliente as ArrayList<Cliente>).get(fila).correo)
                txtDireccion.setText(""+(registrocliente as ArrayList<Cliente>).get(fila).direccion)
                editDni.setText(""+(registrocliente as ArrayList<Cliente>).get(fila).dni)
                editTelefono.setText(""+(registrocliente as ArrayList<Cliente>).get(fila).telefono)
            for(x in (registrodistrito as ArrayList<Distrito>).indices){
                if((registrodistrito as ArrayList<Distrito>).get(x).nombre== (registrocliente as ArrayList<Cliente>).get(fila).distrito?.nombre){
                    indice=x
                }
            }
            cboDis.setSelection(indice)
            if((registrocliente as ArrayList<Cliente>).get(fila).estado){
                    chkEst.setChecked(true)
                }else{
                    chkEst.setChecked(false)
                }
            }
        btnActualizar.setOnClickListener {
            if(fila>=0){
                //capturando valores
                cod= lblCodCli.text.toString().toLong()
                nom=txtNom.getText().toString()
                ape=txtApe.getText().toString()
                dir=txtDireccion.getText().toString()
                tel=editTelefono.getText().toString()
                dni=editDni.getText().toString()
                gen=txtGen.getText().toString()
                cor=txtemail.getText().toString()
                pos=cboDis.selectedItemPosition
                coddis= (registrodistrito as ArrayList<Distrito>).get(pos).codigo
                nomdis= (registrodistrito as ArrayList<Distrito>).get(pos).nombre.toString()
                est=if(chkEst.isChecked){
                    true
                }else{
                    false
                }

                //enviamos los valores a la clase
                objcli.nombre=nom
                objcli.apellido=ape
                objcli.telefono=tel
                objcli.dni=dni
                objcli.genero=gen
                objcli.direccion=dir
                objcli.correo=cor
                objdistrito.codigo=coddis
                objcli.distrito=objdistrito
                objcli.estado=est
                //llamamos a la funcion para registrar
                ActualizarCliente(raiz.context,objcli,cod)
                val fCliente=FragmentoCliente()
                    DialogoCRUD1("Actualizacion de Cliente","Se actualizo el cliente correctamente",fCliente)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lslCli.requestFocus()
            }

        }
        btnEliminar.setOnClickListener {
            if(fila>=0){
                cod=lblCodCli.text.toString().toLong()
                nom=txtNom.text.toString()


                //llamamos a la funcion para registrar
                EliminarCliente(raiz.context,cod)
                val fcliente=FragmentoCliente()
                DialogoCRUD("Eliminacion de Cliente","Se elimino el cliente correctamente",fcliente)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lslCli.requestFocus()
            }
        }



        return raiz

    }





    //creamos una funcion para mostrar el combo de la distrito
    fun MostrarComboDistrito(context: Context?){
        val call= distritoService!!.MostrarDistritoPersonalizado()
        call!!.enqueue(object :Callback<List<Distrito>?>{
            override fun onResponse(
                call: Call<List<Distrito>?>,
                response: Response<List<Distrito>?>
            ) {
                if(response.isSuccessful){
                    registrodistrito=response.body()
                    cboDis.adapter= AdaptadorComboDistrito(context,registrodistrito)


                }
            }

            override fun onFailure(call: Call<List<Distrito>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }


    //creamos la funcion para mostrar
    fun MostrarCliente(context: Context?){
        val call= clienteService!!.MostrarClientePersonalizado()
        call!!.enqueue(object : Callback<List<Cliente>?> {
            override fun onResponse(
                call: Call<List<Cliente>?>,
                response: Response<List<Cliente>?>
            ) {
                if(response.isSuccessful){
                    registrocliente=response.body()
                    lslCli.adapter= AdaptadorCliente(context,registrocliente)
                }
            }

            override fun onFailure(call: Call<List<Cliente>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para registrar
    fun RegistrarCliente(context: Context?, cl: Cliente?){
        val call= clienteService!!.RegistrarCliente(cl)
        call!!.enqueue(object : Callback<Cliente?> {
            override fun onResponse(call: Call<Cliente?>, response: Response<Cliente?>) {
                if(response.isSuccessful){
                    objutilidad.MensajeToast(context!!,"Se registro la Cliente")
                }
            }

            override fun onFailure(call: Call<Cliente?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }
    //creamos una funcion para actualizar producto
    fun ActualizarCliente(context: Context?,p: Cliente?,id:Long){
        val call= clienteService!!.ActualizarCliente(id,p)
        call!!.enqueue(object :Callback<Cliente?>{
            override fun onResponse(call: Call<Cliente?>, response: Response<Cliente?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizo")
                }
            }

            override fun onFailure(call: Call<Cliente?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun EliminarCliente(context: Context?,id:Long){
        val call= clienteService!!.EliminarCliente(id)
        call!!.enqueue(object :Callback<Cliente?>{
            override fun onResponse(call: Call<Cliente?>, response: Response<Cliente?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se elimino")
                }
            }

            override fun onFailure(call: Call<Cliente?>, t: Throwable) {
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