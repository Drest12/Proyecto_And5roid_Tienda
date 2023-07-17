package com.example.sistema_modas


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.example.sistema_modas.adaptadores.AdaptadorUsuarioRol
import com.example.sistema_modas.adaptadores.combo.AdaptadorComboRol
import com.example.sistema_modas.adaptadores.combo.AdaptadorComboUsuario
import com.example.sistema_modas.clases.Rol
import com.example.sistema_modas.clases.Usuario
import com.example.sistema_modas.clases.UsuarioRol
import com.example.sistema_modas.remoto.ApiUtil
import com.example.sistema_modas.service.RolService
import com.example.sistema_modas.service.UsuarioRolService
import com.example.sistema_modas.service.UsuarioService
import com.example.sistema_modas.utilidad.Util

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentoUsuarioRol : Fragment() {

    private lateinit var cboUsu: Spinner
    private lateinit var cboRol: Spinner
    private lateinit var chkEstUsuRol: CheckBox
    private lateinit var lblCodUsuRol: TextView
    private lateinit var btnRegistrarUsuRol: Button
    private lateinit var btnActualizarUsuRol: Button
    private lateinit var btnEliminarUsuRol: Button
    private lateinit var lstUsuRol: ListView

    private var cod=0L
    private var codusu=0L
    private var nomusu=""
    private var codrol=0L
    private var nomrol=""
    private var est=false
    private var fila=-1
    private var indice=-1
    private var pos1=-1
    private var pos2=-1

    private val objusuario= Usuario()
    private val objrol= Rol()
    private val objusuariorol= UsuarioRol()

    private var dialogo: AlertDialog.Builder?=null
    private var ft: FragmentTransaction?=null

    private var usuarioService: UsuarioService?=null
    private var rolService: RolService?=null
    private var usuariorolService: UsuarioRolService?=null

    private var registrousuario:List<Usuario>?=null
    private var registrorol:List<Rol>?=null
    private var registrousuariorol:List<UsuarioRol>?=null

    private val objutilidad= Util()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val raiz=inflater.inflate(R.layout.fragmento_usuario_rol, container, false)

        cboUsu=raiz.findViewById(R.id.cboUsu)
        cboRol=raiz.findViewById(R.id.cboRol)
        lblCodUsuRol=raiz.findViewById(R.id.lblUsuRol)
        chkEstUsuRol=raiz.findViewById(R.id.chkEstUsuRol)
        btnRegistrarUsuRol=raiz.findViewById(R.id.btnRegistrarUsuRol)
        btnActualizarUsuRol=raiz.findViewById(R.id.btnActualizarUsuRol)
        btnEliminarUsuRol=raiz.findViewById(R.id.btnEliminarUsuRol)
        lstUsuRol=raiz.findViewById(R.id.lstUsuRol)

        registrousuario=ArrayList()
        registrorol=ArrayList()
        registrousuariorol=ArrayList()

        usuarioService= ApiUtil.usuarioService
        rolService= ApiUtil.rolService
        usuariorolService= ApiUtil.usuarioRolService

        MostrarComboUsuario(raiz.context)
        MostrarComboRol(raiz.context)
        MostrarUsuarioRol(raiz.context)

        btnRegistrarUsuRol.setOnClickListener {
            pos1=cboUsu.selectedItemPosition
            codusu= (registrousuario as ArrayList<Usuario>).get(pos1).codigo
            nomusu= (registrousuario as ArrayList<Usuario>).get(pos1).usuario.toString()
            pos2=cboRol.selectedItemPosition
            codrol= (registrorol as ArrayList<Rol>).get(pos2).codigo
            nomrol= (registrorol as ArrayList<Rol>).get(pos2).nombre.toString()
            est=if(chkEstUsuRol.isChecked){
                true
            }else{
                false
            }

            objusuario.codigo=codusu
            objusuariorol.usuario=objusuario

            objrol.codigo=codrol
            objusuariorol.rol=objrol

            objusuariorol.estado=est

            RegistrarUsuarioRol(raiz.context,objusuariorol)
            val fusuariorol=FragmentoUsuarioRol()
            DialogoCRUD("Registro de Usuario y Rol","Se registro el usuario-rol correctamente",fusuariorol)



        }

        return raiz
    }
    //creamos una funcion para mostrar el combo de usuarios
    fun MostrarComboUsuario(context: Context?){
        val call= usuarioService!!.MostrarUsuarioPersonalizado()
        call!!.enqueue(object : Callback<List<Usuario>?> {
            override fun onResponse(
                call: Call<List<Usuario>?>,
                response: Response<List<Usuario>?>
            ) {
                if(response.isSuccessful){
                    registrousuario=response.body()
                    cboUsu.adapter= AdaptadorComboUsuario(context,registrousuario)


                }
            }

            override fun onFailure(call: Call<List<Usuario>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para mostrar el combo de roles
    fun MostrarComboRol(context: Context?){
        val call= rolService!!.MostrarRolPersonalizado()
        call!!.enqueue(object : Callback<List<Rol>?> {
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

    //creamos una funcion para mostrar los producto
    fun MostrarUsuarioRol(context: Context?){
        val call= usuariorolService!!.MostrarUsuarioRolPersonalizado()
        call!!.enqueue(object :Callback<List<UsuarioRol>?>{
            override fun onResponse(
                call: Call<List<UsuarioRol>?>,
                response: Response<List<UsuarioRol>?>
            ) {
                if(response.isSuccessful){
                    registrousuariorol=response.body()
                    lstUsuRol.adapter= AdaptadorUsuarioRol(context,registrousuariorol)
                }
            }

            override fun onFailure(call: Call<List<UsuarioRol>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para registrar usuario rol
    fun RegistrarUsuarioRol(context: Context?,ur: UsuarioRol?){
        val call= usuariorolService!!.RegistrarUsuarioRol(ur)
        call!!.enqueue(object :Callback<UsuarioRol?>{
            override fun onResponse(call: Call<UsuarioRol?>, response: Response<UsuarioRol?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se registro")
                }
            }

            override fun onFailure(call: Call<UsuarioRol?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }
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
        dialogo!!.show()
    }

}
