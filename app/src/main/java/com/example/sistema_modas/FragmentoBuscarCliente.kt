package com.example.sistema_modas

import android.annotation.SuppressLint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import android.widget.*
import androidx.fragment.app.FragmentTransaction

import com.example.sistema_modas.adaptadores.busqueda.AdaptadorBuscaCliente
import com.example.sistema_modas.clases.Cliente
import com.example.sistema_modas.remoto.ApiUtil
import com.example.sistema_modas.service.ClienteService
import com.example.sistema_modas.utilidad.Util


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentoBuscarCliente.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentoBuscarCliente : Fragment() {
    //declaramos los controles
    private lateinit var txtBusCli: SearchView
    private lateinit var lblCodCli: TextView
    private lateinit var btnHabilitarCli: Button
    private lateinit var btnDeshabilitarCli: Button
    private lateinit var lstCli: ListView

    //cremamos un objeto de la clase categoria
    private val objcliente= Cliente()

    //creamos variables
    private var cod=0L
    private var fila=-1
    private var poscri=-1
    private var cri=""


    //llamamos al servicio
    private var clienteService: ClienteService?=null

    //creamos una lista de tipo categoria
    private var registrocliente:List<Cliente>?=null

    //creamos un objeto de la clase Util
    private val objutilidad= Util()

    //creams una variable para actualizar el fragmento
    var ft: FragmentTransaction?=null

    private var dialogo: AlertDialog.Builder?=null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val raiz=inflater.inflate(R.layout.fragmento_buscar_cliente,container,false)
        //creamos los controles
        txtBusCli=raiz.findViewById(R.id.txtBusCli)
        lblCodCli=raiz.findViewById(R.id.lblCodCli)
        btnHabilitarCli=raiz.findViewById(R.id.btnHabilitarCli)
        btnDeshabilitarCli=raiz.findViewById(R.id.btnDeshabilitarCli)
        lstCli=raiz.findViewById(R.id.lstCli)

        //creamos el registro categoria
        registrocliente=ArrayList()
        //implementamos el servicio
        clienteService= ApiUtil.clienteService
        //mostramos las categorias
        MostrarCliente(raiz.context)

        //eventos
        txtBusCli.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (lstCli.adapter as AdaptadorBuscaCliente).filter(newText ?: "")
                return true
            }
        })

        return raiz
    }

    fun MostrarCliente(context: Context?){
        val call= clienteService!!.MostrarCliente()
        call!!.enqueue(object : Callback<List<Cliente>?> {
            override fun onResponse(
                call: Call<List<Cliente>?>,
                response: Response<List<Cliente>?>
            ) {
                if(response.isSuccessful){
                    registrocliente=response.body()
                    lstCli.adapter= AdaptadorBuscaCliente(context,registrocliente)


                }
            }

            override fun onFailure(call: Call<List<Cliente>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentoBuscarCliente.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentoBuscarCliente().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
