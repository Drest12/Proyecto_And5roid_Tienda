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
import com.example.sistema_modas.adaptadores.busqueda.AdaptadorBuscarDetalleVenta
import com.example.sistema_modas.adaptadores.busqueda.AdaptadorBuscarVenta
import com.example.sistema_modas.clases.DetalleVenta
import com.example.sistema_modas.clases.Distrito
import com.example.sistema_modas.clases.Venta
import com.example.sistema_modas.remoto.ApiUtil
import com.example.sistema_modas.service.DetalleVentaService
import com.example.sistema_modas.service.VentaService
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
 * Use the [FramentoBuscarDetalleVenta.newInstance] factory method to
 * create an instance of this fragment.
 */
class FramentoBuscarDetalleVenta : Fragment() {
    //declaramos los controles
    private lateinit var txtBusDetalleVenta: SearchView
    private lateinit var lblCodDetalleVenta: TextView

    private lateinit var lstDetalleVenta: ListView

    //cremamos un objeto de la clase categoria
    private val objdistrito= Distrito()

    //creamos variables
    private var cod=0L
    private var fila=-1
    private var poscri=-1
    private var cri=""


    //llamamos al servicio
    private var detalleVentaService: DetalleVentaService?=null

    //creamos una lista de tipo categoria
    private var registroDetalleVenta:List<DetalleVenta>?=null

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
        val raiz=inflater.inflate(R.layout.framento_buscar_detalle_venta,container,false)
        //creamos los controles
        txtBusDetalleVenta=raiz.findViewById(R.id.txtBusDetalleVenta)
        lblCodDetalleVenta=raiz.findViewById(R.id.lblCodDetalleVenta)

        lstDetalleVenta=raiz.findViewById(R.id.lstDetalleVenta)

        //creamos el registro categoria
        registroDetalleVenta=ArrayList()
        //implementamos el servicio
        detalleVentaService= ApiUtil.detalleVentaService
        //mostramos las categorias
        MostrarDetalleVenta(raiz.context)

        //eventos
        txtBusDetalleVenta.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (lstDetalleVenta.adapter as AdaptadorBuscarDetalleVenta).filter(newText ?: "")
                return true
            }
        })

        return raiz
    }

    fun MostrarDetalleVenta(context: Context?){
        val call= detalleVentaService!!.MostrarDetalleVenta()
        call!!.enqueue(object : Callback<List<DetalleVenta>?> {
            override fun onResponse(
                call: Call<List<DetalleVenta>?>,
                response: Response<List<DetalleVenta>?>
            ) {
                if(response.isSuccessful){
                    registroDetalleVenta=response.body()
                    lstDetalleVenta.adapter= AdaptadorBuscarDetalleVenta(context,registroDetalleVenta)


                }
            }

            override fun onFailure(call: Call<List<DetalleVenta>?>, t: Throwable) {
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
            FragmentoBuscarCategoria().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}