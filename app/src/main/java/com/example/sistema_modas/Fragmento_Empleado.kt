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


import com.example.sistema_modas.adaptadores.combo.AdaptadorComboDistrito
import com.example.sistema_modas.adaptadores.combo.AdaptadorComboRol
import com.example.sistema_modas.adaptadores.AdaptadorEmpleado
import com.example.sistema_modas.clases.Distrito
import com.example.sistema_modas.clases.Empleado
import com.example.sistema_modas.clases.Rol
import com.example.sistema_modas.databinding.FragmentoEmpleadoBinding
import com.example.sistema_modas.remoto.ApiUtil
import com.example.sistema_modas.service.DistritoService
import com.example.sistema_modas.service.EmpleadoService
import com.example.sistema_modas.service.RolService
import com.example.sistema_modas.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Fragmento_Empleado : Fragment() {
    private lateinit var txtNom: EditText
    private lateinit var editTelefono: EditText
    private lateinit var editDni: EditText
    private lateinit var txtDireccion: EditText
    private lateinit var txtApellido: EditText
    private lateinit var txtContrasenia: EditText
    private lateinit var txtemail: EditText
    private lateinit var chkEst: CheckBox
    private lateinit var cboDis: Spinner
    private lateinit var cboRol: Spinner
    private lateinit var lblCodEmp: TextView
    private lateinit var btnRegistrar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var lslEmp: ListView

    //creamos un objeto de la clase
    val objempleado= Empleado()
    private  val objrol=Rol()
    private val objdistrito= Distrito()


    //declaramos variables
    private var cod=0L
    private var nom=""
    private var tel=""
    private var ape=""
    private var dir=""
    private var dni=""
    private var contra=""
    private var cor=""
    private var est=false
    private var fila=-1
    private var coddis=0L
    private var codrol=0L
    private var nomdis=""
    private var nomrol=""
    private var indice=-1
    private var pos=-1
    //creamos un arraylist de
    private var registrorol:List<Rol>?=null
    private var registroEmpleado:List<Empleado>?=null
    private var registrodistrito:List<Distrito>?=null
    //declaramos el servicio
    private  var empleadoService: EmpleadoService?=null
    private var rolService: RolService?=null
    private var distritoService: DistritoService?=null
    //creamos un obejto de la clase utilidad
    var objutilidad= Util()
    //creamos una transicion para el fragmento
    var ft: FragmentTransaction?=null
    private var dialogo: AlertDialog.Builder?=null
    private var _binding: FragmentoEmpleadoBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val raiz=inflater.inflate(R.layout.fragmento__empleado,container,false)

        txtNom=raiz.findViewById(R.id.txtNom)
        editTelefono=raiz.findViewById(R.id.editTelefono)
        editDni=raiz.findViewById(R.id.editDni)
        txtApellido=raiz.findViewById(R.id.txtApe)
        txtContrasenia=raiz.findViewById(R.id.editcontra)
        cboRol=raiz.findViewById(R.id.cboRol)
        cboDis=raiz.findViewById(R.id.cboDis)
        txtDireccion=raiz.findViewById(R.id.txtDireccion)
        txtemail=raiz.findViewById(R.id.txtemail)
        chkEst=raiz.findViewById(R.id.chkEstEmp)
        lblCodEmp=raiz.findViewById(R.id.lblCodEmp)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrarEmp)
        btnActualizar=raiz.findViewById(R.id.btnActualizarEmp)
        btnEliminar=raiz.findViewById(R.id.btnEliminarEmp)
        lslEmp=raiz.findViewById(R.id.lstEmpl)

        //creamos el arraylist de
        registroEmpleado=ArrayList()
        registrorol=ArrayList()
        registrodistrito=ArrayList()

        //implementamos el servicio
        empleadoService= ApiUtil.empleadoService
        distritoService= ApiUtil.distritoService
        rolService=ApiUtil.rolService
        //mostramos las
        MostrarComboDistrito(raiz.context)
        MostrarComboRol(raiz.context)

        MostrarEmpleado(raiz.context)

        //agregamos los eventos
        btnRegistrar.setOnClickListener {
            if(txtNom.getText().toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingrese el nombre")
                txtNom.requestFocus()
            }

            else if(txtemail.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el email")
                txtemail.requestFocus()
            }
            else if(txtApellido.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el apellido")
                txtApellido.requestFocus()
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
            else if(txtContrasenia.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el contraseña")
                txtContrasenia.requestFocus()
            }
            else if(cboDis.selectedItemPosition==-1){
                objutilidad.MensajeToast(raiz.context,"Seleccione una distrito")
                cboDis.requestFocus()
            }
            else if(cboRol.selectedItemPosition==-1){
                objutilidad.MensajeToast(raiz.context,"Seleccione una rol")
                cboRol.requestFocus()
            }
            else{
                //capturando valores
                nom=txtNom.getText().toString()
                dir=txtDireccion.getText().toString()
                tel=editTelefono.getText().toString()
                dni=editDni.getText().toString()
                ape=txtApellido.getText().toString()
                contra=txtContrasenia.getText().toString()
                cor=txtemail.getText().toString()
                pos=cboDis.selectedItemPosition
                pos=cboRol.selectedItemPosition
                codrol= (registrorol as ArrayList<Rol>).get(pos).codigo
                nomrol= (registrorol as ArrayList<Rol>).get(pos).nombre.toString()
                coddis= (registrodistrito as ArrayList<Distrito>).get(pos).codigo
                nomdis= (registrodistrito as ArrayList<Distrito>).get(pos).nombre.toString()
                est=if(chkEst.isChecked){
                    true
                }else{
                    false
                }
                //enviamos los valores a la clase
                objempleado.nombre=nom
                objempleado.telefono=tel
                objempleado.dni=dni
                objempleado.apellido=ape
                objempleado.contrasenia=contra
                objempleado.direccion=dir
                objempleado.correo=cor
                objdistrito.codigo=coddis
                objrol.codigo=codrol
                objempleado.distrito=objdistrito
                objempleado.rol=objrol
                objempleado.estado=est
                //llamamos al metodo para registra
                RegistrarEmpleado(raiz.context,objempleado)
                //actualizamos el fragmento
                val fcli=Fragmento_Empleado()
                DialogoCRUD1("Registro de Empleado","Se registro la Empleado",fcli)
            }
        }

        lslEmp.setOnItemClickListener { adapterView, view, i, l ->
            fila=i

            lblCodEmp.setText(""+(registroEmpleado as ArrayList<Empleado>).get(fila).codigo)
            txtNom.setText(""+(registroEmpleado as ArrayList<Empleado>).get(fila).nombre)
            txtApellido.setText(""+(registroEmpleado as ArrayList<Empleado>).get(fila).apellido)
            txtContrasenia.setText(""+(registroEmpleado as ArrayList<Empleado>).get(fila).contrasenia)
            txtemail.setText(""+(registroEmpleado as ArrayList<Empleado>).get(fila).correo)
            txtDireccion.setText(""+(registroEmpleado as ArrayList<Empleado>).get(fila).direccion)
            editDni.setText(""+(registroEmpleado as ArrayList<Empleado>).get(fila).dni)
            editTelefono.setText(""+(registroEmpleado as ArrayList<Empleado>).get(fila).telefono)
            for(x in (registrodistrito as ArrayList<Distrito>).indices){
                if((registrodistrito as ArrayList<Distrito>).get(x).nombre== (registroEmpleado as ArrayList<Empleado>).get(fila).distrito?.nombre){
                    indice=x
                }
            }
            for(x in (registrorol as ArrayList<Rol>).indices){
                if((registrorol as ArrayList<Rol>).get(x).nombre== (registroEmpleado as ArrayList<Empleado>).get(fila).rol?.nombre){
                    indice=x
                }
            }
            cboDis.setSelection(indice)
            cboRol.setSelection(indice)
            if((registroEmpleado as ArrayList<Empleado>).get(fila).estado){
                chkEst.setChecked(true)
            }else{
                chkEst.setChecked(false)
            }
        }
        btnActualizar.setOnClickListener {
            if(fila>=0){
                //capturando valores
                cod= lblCodEmp .text.toString().toLong()
                nom=txtNom.getText().toString()
                dir=txtDireccion.getText().toString()
                tel=editTelefono.getText().toString()
                dni=editDni.getText().toString()
                ape=txtApellido.getText().toString()
                contra=txtContrasenia.getText().toString()
                cor=txtemail.getText().toString()
                pos=cboDis.selectedItemPosition
                pos=cboRol.selectedItemPosition
                codrol= (registrorol as ArrayList<Rol>).get(pos).codigo
                nomrol= (registrorol as ArrayList<Rol>).get(pos).nombre.toString()
                coddis= (registrodistrito as ArrayList<Distrito>).get(pos).codigo
                nomdis= (registrodistrito as ArrayList<Distrito>).get(pos).nombre.toString()
                est=if(chkEst.isChecked){
                    true
                }else{
                    false
                }

                //enviamos los valores a la clase
                objempleado.nombre=nom
                objempleado.apellido=ape
                objempleado.correo=cor
                objempleado.telefono=tel
                objempleado.dni=dni
                objempleado.contrasenia=contra
                objempleado.direccion=dir
                objrol.codigo=codrol
                objdistrito.codigo=coddis

                objempleado.rol=objrol
                objempleado.distrito=objdistrito

                objempleado.estado=est
                ActualizarEmpleado(raiz.context,objempleado,cod)
                val fempleado=Fragmento_Empleado()
                DialogoCRUD1("Actualizacion de Empleado","Se actualizo el empleado correctamente",fempleado)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lslEmp.requestFocus()
            }

        }
        btnEliminar.setOnClickListener {
            if(fila>=0){
                cod=lblCodEmp.text.toString().toLong()
                nom=txtNom.text.toString()


                //llamamos a la funcion para registrar
                EliminarEmpleado(raiz.context,cod)
                val fcliente=Fragmento_Empleado()
                DialogoCRUD("Eliminacion de Emplado","Se elimino el empleado correctamente",fcliente)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lslEmp.requestFocus()
            }
        }

        return raiz

    }



    //creamos una funcion para mostrar el combo de la distrito
    fun MostrarComboRol(context: Context?){
            val call= rolService!!.MostrarRolPersonalizado()
        call!!.enqueue(object :Callback<List<Rol>?>{
            override fun onResponse(
                call: Call<List<Rol>?>,
                response: Response<List<Rol>?>
            ) {
                if(response.isSuccessful){
                    registrorol=response.body()
                    cboRol.adapter= AdaptadorComboRol(context,registrorol)
                }
            }
            override fun onFailure(call: Call<List<Rol>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
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
                Log.e("Errores: ", t.message!!)
            }


        })
    }

    //creamos la funcion para mostrar
    fun MostrarEmpleado(context: Context?){
        val call= empleadoService!!.MostrarEmpleadoPersonalizado()
        call!!.enqueue(object : Callback<List<Empleado>?> {
            override fun onResponse(
                call: Call<List<Empleado>?>,
                response: Response<List<Empleado>?>
            ) {
                if(response.isSuccessful){
                    registroEmpleado=response.body()
                    lslEmp.adapter= AdaptadorEmpleado(context,registroEmpleado)
                }
            }

            override fun onFailure(call: Call<List<Empleado>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para registrar
    fun RegistrarEmpleado(context: Context?, emp: Empleado){
        val call= empleadoService!!.RegistrarEmpleado(emp)
        call!!.enqueue(object : Callback<Empleado?> {
            override fun onResponse(call: Call<Empleado?>, response: Response<Empleado?>) {
                if(response.isSuccessful){
                    objutilidad.MensajeToast(context!!,"Se registro la empleado")
                }
            }

            override fun onFailure(call: Call<Empleado?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }
    //creamos una funcion para actualizar producto
    fun ActualizarEmpleado(context: Context?, p: Empleado?, id:Long){
        val call= empleadoService!!.ActualizarEmpleado(id,p)
        call!!.enqueue(object :Callback<Empleado?>{
            override fun onResponse(call: Call<Empleado?>, response: Response<Empleado?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizo")
                }
            }

            override fun onFailure(call: Call<Empleado?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun EliminarEmpleado(context: Context?,id:Long){
        val call= empleadoService!!.EliminarEmpleado(id)
        call!!.enqueue(object :Callback<Empleado?>{
            override fun onResponse(call: Call<Empleado?>, response: Response<Empleado?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se elimino")
                }
            }

            override fun onFailure(call: Call<Empleado?>, t: Throwable) {
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