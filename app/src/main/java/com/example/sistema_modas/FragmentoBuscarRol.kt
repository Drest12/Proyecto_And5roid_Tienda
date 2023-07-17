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
import android.widget.Button
import android.widget.ListView
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.example.sistema_modas.adaptadores.busqueda.AdaptadorBuscarRol
import com.example.sistema_modas.adaptadores.busqueda.AdaptadorBusquedaDistrito
import com.example.sistema_modas.clases.Distrito
import com.example.sistema_modas.clases.Rol
import com.example.sistema_modas.remoto.ApiUtil
import com.example.sistema_modas.service.DistritoService
import com.example.sistema_modas.service.RolService
import com.example.sistema_modas.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentoBuscarRol.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentoBuscarRol : Fragment() {
    //declaramos los controles
    private lateinit var txtBusRol: SearchView
    private lateinit var lblCodRol: TextView
    private lateinit var btnHabilitarRol: Button
    private lateinit var btnDeshabilitarRol: Button
    private lateinit var lstRol: ListView

    //cremamos un objeto de la clase categoria
    private val objdistrito= Distrito()

    //creamos variables
    private var cod=0L
    private var fila=-1
    private var poscri=-1
    private var cri=""


    //llamamos al servicio
    private var rolService: RolService?=null

    //creamos una lista de tipo categoria
    private var registroRol:List<Rol>?=null

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
        val raiz=inflater.inflate(R.layout.fragmento_buscar_rol,container,false)
        //creamos los controles
        txtBusRol=raiz.findViewById(R.id.txtBusRol)
        lblCodRol=raiz.findViewById(R.id.lblCodRol)
        btnHabilitarRol=raiz.findViewById(R.id.btnHabilitarRol)
        btnDeshabilitarRol=raiz.findViewById(R.id.btnDeshabilitarRol)
        lstRol=raiz.findViewById(R.id.lstRol)

        //creamos el registro categoria
        registroRol=ArrayList()
        //implementamos el servicio
        rolService= ApiUtil.rolService
        //mostramos las categorias
        MostrarRol(raiz.context)

        //eventos
        txtBusRol.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (lstRol.adapter as AdaptadorBuscarRol).filter(newText ?: "")
                return true
            }
        })

        return raiz
    }

    fun MostrarRol(context: Context?){
        val call= rolService!!.MostrarRol()
        call!!.enqueue(object : Callback<List<Rol>?> {
            override fun onResponse(
                call: Call<List<Rol>?>,
                response: Response<List<Rol>?>
            ) {
                if(response.isSuccessful){
                    registroRol=response.body()
                    lstRol.adapter= AdaptadorBuscarRol(context,registroRol)


                }
            }

            override fun onFailure(call: Call<List<Rol>?>, t: Throwable) {
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
            FragmentoBuscarRol().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
