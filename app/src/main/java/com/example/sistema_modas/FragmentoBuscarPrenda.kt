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
import com.example.sistema_modas.adaptadores.busqueda.AdaptadorBuscarEmpleado
import com.example.sistema_modas.adaptadores.busqueda.AdaptadorBuscarPrenda
import com.example.sistema_modas.clases.Empleado
import com.example.sistema_modas.clases.Prenda
import com.example.sistema_modas.remoto.ApiUtil
import com.example.sistema_modas.service.EmpleadoService
import com.example.sistema_modas.service.PrendaService
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
 * Use the [FragmentoBuscarPrenda.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentoBuscarPrenda : Fragment() {
        private lateinit var txtBusPre: SearchView
    private lateinit var lblCodPre: TextView
    private lateinit var btnHabilitarPre: Button
    private lateinit var btnDeshabilitarPre: Button
    private lateinit var lstPre: ListView

    //cremamos un objeto de la clase categoria
    private val objEmpleado= Empleado()

    //creamos variables
    private var cod=0L
    private var fila=-1
    private var poscri=-1
    private var cri=""


    //llamamos al servicio
    private var prendaService: PrendaService?=null

    //creamos una lista de tipo categoria
    private var registroPrenda:List<Prenda>?=null

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
        val raiz=inflater.inflate(R.layout.fragmento_buscar_prenda,container,false)
        //creamos los controles
        txtBusPre=raiz.findViewById(R.id.txtBusPre)
        lblCodPre=raiz.findViewById(R.id.lblCodPre)
        btnHabilitarPre=raiz.findViewById(R.id.btnHabilitarPre)
        btnDeshabilitarPre=raiz.findViewById(R.id.btnDeshabilitarPre)
        lstPre=raiz.findViewById(R.id.lstPre)

        //creamos el registro categoria
        registroPrenda=ArrayList()
        //implementamos el servicio
        prendaService= ApiUtil.prendaService
        //mostramos las categorias
        MostrarPrenda(raiz.context)

        //eventos
        txtBusPre.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (lstPre.adapter as AdaptadorBuscarPrenda).filter(newText ?: "")
                return true
            }
        })

        return raiz
    }

    fun MostrarPrenda(context: Context?){
        val call= prendaService!!.MostrarPrenda()
        call!!.enqueue(object : Callback<List<Prenda>?> {
            override fun onResponse(
                call: Call<List<Prenda>?>,
                response: Response<List<Prenda>?>
            ) {
                if(response.isSuccessful){
                    registroPrenda=response.body()
                    lstPre.adapter= AdaptadorBuscarPrenda(context,registroPrenda)


                }
            }

            override fun onFailure(call: Call<List<Prenda>?>, t: Throwable) {
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
         * @param param2 Parameter 2..
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentoBuscarEmpleado().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
