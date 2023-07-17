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
import android.widget.AdapterView
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.example.sistema_modas.adaptadores.AdaptadorCategoria
import com.example.sistema_modas.clases.Categoria
import com.example.sistema_modas.databinding.FragmentoCategoriaBinding
import com.example.sistema_modas.remoto.ApiUtil
import com.example.sistema_modas.service.CategoriaService
import com.example.sistema_modas.utilidad.Util

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentoCategoria : Fragment() {
    //declaramos los controles
    private lateinit var txtNom:EditText
    private lateinit var chkEst:CheckBox
    private lateinit var lblCodCat:TextView
    private lateinit var btnRegistrar:Button
    private lateinit var btnActualizar:Button
    private lateinit var btnEliminar:Button
    private lateinit var lstCat:ListView

    //cremamos un objeto de la clase categoria
    private val objcategoria=Categoria()

    //creamos variables
    private var cod=0L
    private var fila=-1
    private var nom=""
    private var est=false

    //llamamos al servicio
    private var categoriaService:CategoriaService?=null

    //creamos una lista de tipo categoria
    private var registrocategoria:List<Categoria>?=null

    //creamos un objeto de la clase Util
    private val objutilidad=Util()

    //creams una variable para actualizar el fragmento
    var ft:FragmentTransaction?=null

    private var dialogo: AlertDialog.Builder?=null

    private var _binding: FragmentoCategoriaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val raiz=inflater.inflate(R.layout.fragmento_categoria,container,false)
        //creamos los controles
        txtNom=raiz.findViewById(R.id.txtNom)
        chkEst=raiz.findViewById(R.id.chkEst)
        lblCodCat=raiz.findViewById(R.id.lblCodCat)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrar)
        btnActualizar=raiz.findViewById(R.id.btnActualizar)
        btnEliminar=raiz.findViewById(R.id.btnEliminar)
        lstCat=raiz.findViewById(R.id.lslCat)

        //creamos el registro categoria
        registrocategoria=ArrayList()
        //implementamos el servicio
        categoriaService=ApiUtil.categoriaService
        //mostramos las categorias
        MostrarCategoria(raiz.context)

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
                objcategoria.nombre=nom
                objcategoria.estado=est
                //registramos los valores
                RegistrarCategoria(raiz.context,objcategoria)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmCategoria) as ViewGroup)
                val fcategoria=FragmentoCategoria()
                DialogoCRUD1("Registro de Categoria","Se registro la categoria",fcategoria)

            }
        }

        //para la seleccion de la lista
        lstCat.setOnItemClickListener(AdapterView.OnItemClickListener
        { parent, view, position, id ->
            fila=position
            lblCodCat.setText(""+(registrocategoria as ArrayList<Categoria>).get(fila).codigo)
            txtNom.setText(""+(registrocategoria as ArrayList<Categoria>).get(fila).nombre)
            if((registrocategoria as ArrayList<Categoria>).get(fila).estado){
                chkEst.setChecked(true)
            }else{
                chkEst.setChecked(false)
            }

        }
        )

        btnActualizar.setOnClickListener {
            if(fila>=0){
                cod=lblCodCat.getText().toString().toLong()
                nom=txtNom.getText().toString()
                est=if(chkEst.isChecked){
                    true
                }else{
                    false
                }
                objcategoria.codigo=cod
                objcategoria.nombre=nom
                objcategoria.estado=est

                ActualizarCategoria(raiz.context,objcategoria,cod)

                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmCategoria) as ViewGroup)
                val fcategoria=FragmentoCategoria()
                DialogoCRUD1("Actualizacion de Categoria","Se actualizo la categoria",fcategoria)

            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstCat.requestFocus()
            }
        }

        btnEliminar.setOnClickListener {
            if(fila>=0){
                cod=lblCodCat.getText().toString().toLong()

                objcategoria.codigo=cod


                EliminarCategoria(raiz.context,cod)

                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmCategoria) as ViewGroup)
                val fcategoria=FragmentoCategoria()
                DialogoCRUD("Eliminacion de Categoria","Se elimino la categoria",fcategoria)

            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstCat.requestFocus()
            }
        }


        return raiz
    }



    //creamos la funcion para mostrar categorias
    fun MostrarCategoria(context: Context?){
        val call= categoriaService!!.MostrarCategoriaPersonalizado()
        call!!.enqueue(object :Callback<List<Categoria>?>{
            override fun onResponse(
                call: Call<List<Categoria>?>,
                response: Response<List<Categoria>?>
            ) {
                if(response.isSuccessful){
                    registrocategoria=response.body()
                    lstCat.adapter=AdaptadorCategoria(context,registrocategoria)


                }
            }

            override fun onFailure(call: Call<List<Categoria>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para registrar categoria
    fun RegistrarCategoria(context: Context?,c: Categoria?){
        val call= categoriaService!!.RegistrarCategoria(c)
        call!!.enqueue(object :Callback<Categoria?>{
            override fun onResponse(call: Call<Categoria?>, response: Response<Categoria?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se registro")
                }
            }

            override fun onFailure(call: Call<Categoria?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para actualizar
    fun ActualizarCategoria(context: Context?,c: Categoria?,id:Long){
        val call= categoriaService!!.ActualizarCategoria(id,c)
        call!!.enqueue(object :Callback<Categoria?>{
            override fun onResponse(call: Call<Categoria?>, response: Response<Categoria?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizo")
                }
            }

            override fun onFailure(call: Call<Categoria?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para eliminar
    fun EliminarCategoria(context: Context?,id:Long){
        val call= categoriaService!!.EliminarCategoria(id)
        call!!.enqueue(object :Callback<Categoria?>{
            override fun onResponse(call: Call<Categoria?>, response: Response<Categoria?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se elimino")
                }
            }

            override fun onFailure(call: Call<Categoria?>, t: Throwable) {
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