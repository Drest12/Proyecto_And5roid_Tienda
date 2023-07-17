package com.example.sistema_modas

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

import com.example.sistema_modas.adaptadores.AdaptadorRol

import com.example.sistema_modas.clases.Rol

import com.example.sistema_modas.databinding.FragmentoRolBinding
import com.example.sistema_modas.remoto.ApiUtil

import com.example.sistema_modas.service.RolService
import com.example.sistema_modas.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentoRol : Fragment() {
    //declaramos los controles
    private lateinit var txtNom:EditText
    private lateinit var chkEst:CheckBox
    private lateinit var lblCodRol:TextView
    private lateinit var btnRegistrar:Button
    private lateinit var btnActualizar:Button
    private lateinit var btnEliminar:Button
    private lateinit var lstRol:ListView
    //creamos un objeto de la clase
    val objrol= Rol()
    //creamos variables
    private var cod=0L
    private var fila=-1
    private var nom=""
    private var est=false

    //declaramos el servicio
    private var rolService: RolService?=null

    //creamos un arraylist de
    private var registrorol:List<Rol>?=null

    private var dialogo: AlertDialog.Builder?=null

    //creamos un obejto de la clase utilidad
    var objutilidad= Util()

    //creamos una transicion para el fragmento
    var ft: FragmentTransaction?=null


    private var _binding: FragmentoRolBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val raiz=inflater.inflate(R.layout.fragmento_rol,container,false)
        //creamos los controles
        txtNom=raiz.findViewById(R.id.txtNom)
        chkEst=raiz.findViewById(R.id.chkEst)
        lblCodRol=raiz.findViewById(R.id.lblCodRol)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrar)
        btnActualizar=raiz.findViewById(R.id.btnActualizar)
        btnEliminar=raiz.findViewById(R.id.btnEliminar)
        lstRol=raiz.findViewById(R.id.lslRol)


        //creamos el arraylist de
        registrorol=ArrayList()

        //implementamos el servicio
        rolService= ApiUtil.rolService

        //mostramos las categorias
        MostrarRol(raiz.context)

        //agregamos los eventos a los botones
        btnRegistrar.setOnClickListener {
            if(txtNom.getText().toString()==""){
                objutilidad.MensajeToast(context,"Ingrese el nombre")
                txtNom.requestFocus()
            }else{
                //capturando valores
                nom=txtNom.getText().toString()
                est=if(chkEst.isChecked){
                    true
                }else{
                    false
                }
                //enviando los valores a la clase
                objrol.nombre=nom
                objrol.estado=est
                //registramos los valores
                RegistrarRol(raiz.context,objrol)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmrol) as ViewGroup)
                val fcrol=FragmentoRol()
                DialogoCRUD1("Registro de Rol","Se registro la rol",fcrol)

            }
        }

        //para la seleccion de la lista
        lstRol.setOnItemClickListener(AdapterView.OnItemClickListener
        { parent, view, position, id ->
            fila=position
            lblCodRol.setText(""+(registrorol as ArrayList<Rol>).get(fila).codigo)
            txtNom.setText(""+(registrorol as ArrayList<Rol>).get(fila).nombre)
            if((registrorol as ArrayList<Rol>).get(fila).estado){
                chkEst.setChecked(true)
            }else{
                chkEst.setChecked(false)
            }

        }
        )

        btnActualizar.setOnClickListener {
            if(fila>=0){
                cod=lblCodRol.getText().toString().toLong()
                nom=txtNom.getText().toString()
                est=if(chkEst.isChecked){
                    true
                }else{
                    false
                }
                objrol.codigo=cod
                objrol.nombre=nom
                objrol.estado=est

                ActualizarRol(raiz.context,objrol,cod)

                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmrol) as ViewGroup)
                val fcrol=FragmentoRol()
                DialogoCRUD1("Actualizacion de Rol","Se actualizo la rol",fcrol)

            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstRol.requestFocus()
            }
        }

        btnEliminar.setOnClickListener {
            if(fila>=0){
                cod=lblCodRol.getText().toString().toLong()

                objrol.codigo=cod


                EliminarRol(raiz.context,cod)

                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmrol) as ViewGroup)
                val fcrol=FragmentoRol()
                DialogoCRUD("Eliminacion de Rol","Se elimino la rol",fcrol)

            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstRol.requestFocus()
            }
        }


        return raiz
    }




    fun MostrarRol(context: Context?){
        val call= rolService!!.MostrarRolPersonalizado()
        call!!.enqueue(object :Callback<List<Rol>?>{
            override fun onResponse(
                call: Call<List<Rol>?>,
                response: Response<List<Rol>?>
            ) {
                if(response.isSuccessful){
                    registrorol=response.body()
                    lstRol.adapter=AdaptadorRol(context,registrorol)


                }
            }

            override fun onFailure(call: Call<List<Rol>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }


    fun RegistrarRol(context: Context?,c: Rol?){
        val call= rolService!!.RegistrarRol(c)
        call!!.enqueue(object :Callback<Rol?>{
            override fun onResponse(call: Call<Rol?>, response: Response<Rol?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se registro")
                }
            }

            override fun onFailure(call: Call<Rol?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para actualizar
    fun ActualizarRol(context: Context?,c: Rol?,id:Long){
            val call= rolService!!.ActualizarRol(id,c)
        call!!.enqueue(object :Callback<Rol?>{
            override fun onResponse(call: Call<Rol?>, response: Response<Rol?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizo")
                }
            }

            override fun onFailure(call: Call<Rol?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para eliminar
    fun EliminarRol(context: Context?,id:Long){
        val call= rolService!!.EliminarRol(id)
        call!!.enqueue(object :Callback<Rol?>{
            override fun onResponse(call: Call<Rol?>, response: Response<Rol?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se elimino")
                }
            }

            override fun onFailure(call: Call<Rol?>, t: Throwable) {
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