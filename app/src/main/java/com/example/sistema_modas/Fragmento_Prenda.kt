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
import com.example.sistema_modas.adaptadores.AdaptadorPrenda
import com.example.sistema_modas.adaptadores.combo.AdaptadorComboCategoria
import com.example.sistema_modas.clases.Categoria
import com.example.sistema_modas.clases.Cliente
import com.example.sistema_modas.clases.Distrito


import com.example.sistema_modas.clases.Prenda

import com.example.sistema_modas.databinding.FragmentoPrendaBinding
import com.example.sistema_modas.remoto.ApiUtil
import com.example.sistema_modas.service.CategoriaService
import com.example.sistema_modas.service.PrendaService
import com.example.sistema_modas.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Fragmento_Prenda : Fragment() {
    private lateinit var txtNompre: EditText
    private lateinit var txtprecio: EditText
    private lateinit var txttalla: EditText
    private lateinit var txtcantidad: EditText
    private lateinit var cboCate: Spinner
    private lateinit var chkEst: CheckBox
    private lateinit var lblCodprenda: TextView
    private lateinit var btnRegistrarPre: Button
    private lateinit var btnActualizarPre: Button
    private lateinit var btnEliminarPre: Button
    private lateinit var lslPre: ListView

    //creamos un objeto de la clase
    val objPrenda= Prenda()
    private val objCategoria= Categoria()

    //declaramos variables
    private var cod=0L
    private var des=""
    private var precio=""
    private var talla=""
    private var cantidad=""
    private var est=false
    private var fila=-1
    private var codcat=0L
    private var nomcat=""
    private var indice=-1
    private var pos=-1

    //declaramos el servicio
    private var prendaService: PrendaService?=null
    private var categoriaService: CategoriaService?=null
    //creamos un arraylist de
    private var registroprenda:List<Prenda>?=null
    private var registrocategoria:List<Categoria>?=null
    //creamos un obejto de la clase utilidad
    var objutilidad= Util()
    //creamos una transicion para el fragmento
    var ft: FragmentTransaction?=null
    private var dialogo: AlertDialog.Builder?=null
    private var _binding: FragmentoPrendaBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val raiz=inflater.inflate(R.layout.fragmento__prenda,container,false)
        //creamos los controles
        txtNompre=raiz.findViewById(R.id.txtNompre)
        txttalla=raiz.findViewById(R.id.txttalla)
        txtcantidad=raiz.findViewById(R.id.txtcantidad)
        txtprecio=raiz.findViewById(R.id.txtprecio)
        chkEst=raiz.findViewById(R.id.chEstPre)
        cboCate=raiz.findViewById(R.id.cboCate)
        lblCodprenda=raiz.findViewById(R.id.lblCodprendra)
        btnRegistrarPre=raiz.findViewById(R.id.btnResgistrarPre)
        btnActualizarPre=raiz.findViewById(R.id.btnActualizarPre)
        btnEliminarPre=raiz.findViewById(R.id.btnEliminarPre)
        lslPre=raiz.findViewById(R.id.lstpre)


        registrocategoria=ArrayList()
        registroprenda=ArrayList()
        //implementamos el servicio
        categoriaService=ApiUtil.categoriaService
        prendaService=ApiUtil.prendaService

        MostrarComboCategoria(raiz.context)
        MostrarPrenda(raiz.context)

        //agregamos los eventos a los botones
        btnRegistrarPre.setOnClickListener {
            if(txtNompre.getText().toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingrese el descripcion")
                txtNompre.requestFocus()
            }
            else if(txtcantidad.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el cantidad")
                txtcantidad.requestFocus()
            }
            else if(txtprecio.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el precio")
                txtprecio.requestFocus()
            }
            else if(txttalla.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el talla")
                txttalla.requestFocus()
            }

            else if(cboCate.selectedItemPosition==-1){
                objutilidad.MensajeToast(raiz.context,"Seleccione una categoria")
                cboCate.requestFocus()
            }else{
                //capturando valores
                des=txtNompre.getText().toString()
                precio=txtprecio.getText().toString()
                cantidad=txtcantidad.getText().toString()
                talla=txttalla.getText().toString()
                pos=cboCate.selectedItemPosition
                codcat= (registrocategoria as ArrayList<Categoria>).get(pos).codigo
                nomcat= (registrocategoria as ArrayList<Categoria>).get(pos).nombre.toString()

                est=if(chkEst.isChecked){
                    true
                }else{
                    false
                }
                //enviando los valores a la clase
                objPrenda.descripcion=des
                objPrenda.talla=talla
                objPrenda.cantidad=cantidad
                objPrenda.precio=precio
                objPrenda.estado=est
                objCategoria.codigo=codcat
                objPrenda.categoria=objCategoria

                //registramos los valores
                RegistrarPrenda(raiz.context,objPrenda)

                val fpranda=Fragmento_Prenda()
                DialogoCRUD1("Registro de prenda","Se registro la prenda",fpranda)

            }
        }

        //para la seleccion de la lista
        lslPre.setOnItemClickListener(AdapterView.OnItemClickListener
        { parent, view, position, id ->
            fila=position
            lblCodprenda.setText(""+(registroprenda as ArrayList<Prenda>).get(fila).codigo)
            txtNompre.setText(""+(registroprenda as ArrayList<Prenda>).get(fila).descripcion)
            txtprecio.setText(""+(registroprenda as ArrayList<Prenda>).get(fila).precio)
            txttalla.setText(""+(registroprenda as ArrayList<Prenda>).get(fila).talla)
            txtcantidad.setText(""+(registroprenda as ArrayList<Prenda>).get(fila).cantidad)
            for(x in (registrocategoria as ArrayList<Categoria>).indices){
                if((registrocategoria as ArrayList<Categoria>).get(x).nombre== (registroprenda as ArrayList<Prenda>).get(fila).categoria?.nombre){
                    indice=x
                }
            }
            cboCate.setSelection(indice)
            if((registroprenda as ArrayList<Prenda>).get(fila).estado){
                chkEst.setChecked(true)
            }else{
                chkEst.setChecked(false)
            }

        }
        )
        btnActualizarPre.setOnClickListener {
            if(fila>=0){
                //capturando valores
                cod= lblCodprenda.text.toString().toLong()
                des=txtNompre.getText().toString()
                precio=txtprecio.getText().toString()
                cantidad=txtcantidad.getText().toString()
                talla=txttalla.getText().toString()
                pos=cboCate.selectedItemPosition
                codcat= (registrocategoria as ArrayList<Categoria>).get(pos).codigo
                nomcat= (registrocategoria as ArrayList<Categoria>).get(pos).nombre.toString()
                est=if(chkEst.isChecked){
                    true
                }else{
                    false
                }

                //enviamos los valores a la clase
                objPrenda.descripcion=des
                objPrenda.talla=talla
                objPrenda.cantidad=cantidad
                objPrenda.precio=precio
                objPrenda.estado=est
                objCategoria.codigo=codcat
                objPrenda.categoria=objCategoria
                //llamamos a la funcion para registrar
                ActualizarPrenda(raiz.context,objPrenda,cod)
                val fPrenda=Fragmento_Prenda()
                DialogoCRUD1("Actualizacion de Prenda","Se actualizo el prenda correctamente",fPrenda)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lslPre.requestFocus()
            }

        }
        btnEliminarPre.setOnClickListener {
            if(fila>=0){
                cod=lblCodprenda.text.toString().toLong()
                des=txtNompre.text.toString()


                //llamamos a la funcion para registrar
                EliminarPrenda(raiz.context,cod)
                val fcliente=Fragmento_Prenda()
                DialogoCRUD("Eliminacion de Prenda","Se elimino el prenda correctamente",fcliente)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lslPre.requestFocus()
            }
        }




        return raiz
    }





    //creamos una funcion para mostrar el combo de la categoria
    fun MostrarComboCategoria(context: Context?){
        val call= categoriaService!!.MostrarCategoriaPersonalizado()
        call!!.enqueue(object : Callback<List<Categoria>?> {
            override fun onResponse(
                call: Call<List<Categoria>?>,
                response: Response<List<Categoria>?>
            ) {
                if(response.isSuccessful){
                    registrocategoria=response.body()
                    cboCate.adapter= AdaptadorComboCategoria(context,registrocategoria)
                }
            }
            override fun onFailure(call: Call<List<Categoria>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }
        })
    }
    //creamos la funcion para mostrar
    fun MostrarPrenda(context: Context?){
        val call= prendaService!!.MostrarPrendaPersonalizado()
        call!!.enqueue(object : Callback<List<Prenda>?> {
            override fun onResponse(
                call: Call<List<Prenda>?>,
                response: Response<List<Prenda>?>
            ) {
                if(response.isSuccessful){
                    registroprenda=response.body()
                    lslPre.adapter= AdaptadorPrenda(context,registroprenda)
                }
            }
            override fun onFailure(call: Call<List<Prenda>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }
        })
    }
    //creamos una funcion para registrar
    fun RegistrarPrenda(context: Context?, pr: Prenda?){
        val call= prendaService!!.RegistrarPrenda(pr)
        call!!.enqueue(object : Callback<Prenda?> {
            override fun onResponse(call: Call<Prenda?>, response: Response<Prenda?>) {
                if(response.isSuccessful){
                    objutilidad.MensajeToast(context!!,"Se registro la prenda")
                }
            }

            override fun onFailure(call: Call<Prenda?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }
    //creamos una funcion para actualizar producto
    fun ActualizarPrenda(context: Context?, pr: Prenda?, id:Long){
        val call= prendaService!!.ActualizarPrenda(id,pr)
        call!!.enqueue(object :Callback<Prenda?>{
            override fun onResponse(call: Call<Prenda?>, response: Response<Prenda?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizo")
                }
            }

            override fun onFailure(call: Call<Prenda?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun EliminarPrenda(context: Context?,id:Long){
        val call= prendaService!!.EliminarPrenda(id)
        call!!.enqueue(object :Callback<Prenda?>{
            override fun onResponse(call: Call<Prenda?>, response: Response<Prenda?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se elimino")
                }
            }

            override fun onFailure(call: Call<Prenda?>, t: Throwable) {
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