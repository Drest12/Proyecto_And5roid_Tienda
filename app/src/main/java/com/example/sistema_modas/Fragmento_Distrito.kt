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

import com.example.sistema_modas.adaptadores.AdaptadorDistrito

import com.example.sistema_modas.clases.Distrito

import com.example.sistema_modas.databinding.FragmentoDistritoBinding

import com.example.sistema_modas.remoto.ApiUtil

import com.example.sistema_modas.service.DistritoService

import com.example.sistema_modas.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Fragmento_Distrito : Fragment() {
    //declaramos los controles
    private lateinit var txtNom:EditText
    private lateinit var chkDis:CheckBox
    private lateinit var lblCodDis:TextView
    private lateinit var btnRegistrar:Button
    private lateinit var btnActualizar:Button
    private lateinit var btnEliminar:Button
    private lateinit var lstDis:ListView


    private val objdistrito= Distrito()

    //creamos variables
    private var cod=0L
    private var fila=-1
    private var nom=""
    private var est=false


    private var distritoService: DistritoService?=null


    private var registroDistrito:List<Distrito>?=null

    //creamos un objeto de la clase Util
    private val objutilidad=Util()

    //creams una variable para actualizar el fragmento
    var ft:FragmentTransaction?=null

    private var dialogo: AlertDialog.Builder?=null

    private var _binding: FragmentoDistritoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val raiz=inflater.inflate(R.layout.fragmento_distrito,container,false)
        //creamos los controles
        txtNom=raiz.findViewById(R.id.txtNom)
        chkDis=raiz.findViewById(R.id.chkDis)
        lblCodDis=raiz.findViewById(R.id.lblCodDis)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrar)
        btnActualizar=raiz.findViewById(R.id.btnActualizar)
        btnEliminar=raiz.findViewById(R.id.btnEliminar)
        lstDis=raiz.findViewById(R.id.lslDis)


        registroDistrito=ArrayList()
        //implementamos el servicio
        distritoService=ApiUtil.distritoService

        MostrarDistrito(raiz.context)

        //agregamos los eventos a los botones
        btnRegistrar.setOnClickListener {
            if(txtNom.getText().toString()==""){
                objutilidad.MensajeToast(context,"Ingrese el nombre")
                txtNom.requestFocus()
            }else{
                //capturando valores
                nom=txtNom.getText().toString()
                est=if(chkDis.isChecked){
                    true
                }else{
                    false
                }
                //enviando los valores a la clase
                objdistrito.nombre=nom
                objdistrito.estado=est
                //registramos los valores
                RegistrarDistrito(raiz.context,objdistrito)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmDistrito) as ViewGroup)
                val fdistrito=Fragmento_Distrito()
                DialogoCRUD1("Registro de Distrito","Se registro la distrito",fdistrito)

            }
        }

        //para la seleccion de la lista
        lstDis.setOnItemClickListener(AdapterView.OnItemClickListener
        { parent, view, position, id ->
            fila=position
            lblCodDis.setText(""+(registroDistrito as ArrayList<Distrito>).get(fila).codigo)
            txtNom.setText(""+(registroDistrito as ArrayList<Distrito>).get(fila).nombre)
            if((registroDistrito as ArrayList<Distrito>).get(fila).estado){
                chkDis.setChecked(true)
            }else{
                chkDis.setChecked(false)
            }

        }
        )

        btnActualizar.setOnClickListener {
            if(fila>=0){
                cod=lblCodDis.getText().toString().toLong()
                nom=txtNom.getText().toString()
                est=if(chkDis.isChecked){
                    true
                }else{
                    false
                }
                objdistrito.codigo=cod
                objdistrito.nombre=nom
                objdistrito.estado=est

                ActualizarDistrito(raiz.context,objdistrito,cod)

                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmDistrito) as ViewGroup)
                val fdistrito=Fragmento_Distrito()
                DialogoCRUD1("Actualizacion de Distrito","Se actualizo la distrito",fdistrito)

            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstDis.requestFocus()
            }
        }

        btnEliminar.setOnClickListener {
            if(fila>=0){
                cod=lblCodDis.getText().toString().toLong()

                objdistrito.codigo=cod


                EliminarDistrito(raiz.context,cod)

                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmDistrito) as ViewGroup)
                val fdistrito=Fragmento_Distrito()
                DialogoCRUD("Eliminacion de Distrito","Se elimino la distrito",fdistrito)

            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstDis.requestFocus()
            }
        }


        return raiz
    }




    fun MostrarDistrito(context: Context?){
        val call= distritoService!!.MostrarDistritoPersonalizado()
        call!!.enqueue(object :Callback<List<Distrito>?>{
            override fun onResponse(
                call: Call<List<Distrito>?>,
                response: Response<List<Distrito>?>
            ) {
                if(response.isSuccessful){
                    registroDistrito=response.body()
                    lstDis.adapter= AdaptadorDistrito(context,registroDistrito)


                }
            }

            override fun onFailure(call: Call<List<Distrito>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun RegistrarDistrito(context: Context?,c: Distrito?){
        val call= distritoService!!.RegistrarDistrito(c)
        call!!.enqueue(object :Callback<Distrito?>{
            override fun onResponse(call: Call<Distrito?>, response: Response<Distrito?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se registro")
                }
            }

            override fun onFailure(call: Call<Distrito?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para actualizar
    fun ActualizarDistrito(context: Context?, c: Distrito?, id:Long){
        val call= distritoService!!.ActualizarDistrito(id,c)
        call!!.enqueue(object :Callback<Distrito?>{
            override fun onResponse(call: Call<Distrito?>, response: Response<Distrito?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizo")
                }
            }

            override fun onFailure(call: Call<Distrito?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para eliminar
    fun EliminarDistrito(context: Context?,id:Long){
        val call= distritoService!!.EliminarDistrito(id)
        call!!.enqueue(object :Callback<Distrito?>{
            override fun onResponse(call: Call<Distrito?>, response: Response<Distrito?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se elimino")
                }
            }

            override fun onFailure(call: Call<Distrito?>, t: Throwable) {
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