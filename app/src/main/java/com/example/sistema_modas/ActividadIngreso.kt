package com.example.sistema_modas

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.sistema_modas.clases.Usuario
import com.example.sistema_modas.remoto.ApiUtil
import com.example.sistema_modas.service.UsuarioService
import com.example.sistema_modas.utilidad.Util

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActividadIngreso : AppCompatActivity() {
    //creando controles
    private lateinit var txtUsu:EditText
    private lateinit var txtCla:EditText
    private lateinit var btnIngresar:Button
    private lateinit var btnSalir:Button
    //creamos un objeto de la clase usuario
    val objusuario=Usuario()

    //creamos el servicio
    private var usuarioService: UsuarioService?=null
    //creamos un objeto de la clase Util
    private val objutilidad=Util()
    //declaramos variables para el usuario y la clave
    private var usu=""
    private var cla=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_ingreso)
        //creamos los controles
        txtUsu=findViewById(R.id.txtUsu)
        txtCla=findViewById(R.id.txtCla)
        btnIngresar=findViewById(R.id.btnIngresa)
        btnSalir=findViewById(R.id.btnSalir)
        //creamos una variable para el contexto
        val context=this

        //implementamos el servicio
        usuarioService= ApiUtil.usuarioService

        //agregamos eventos para los botones
        btnIngresar.setOnClickListener {
            if(txtUsu.getText().toString()==""){
                objutilidad.MensajeToast(this,"Ingrese el usuario")
                txtUsu.requestFocus()
            }else if(txtCla.getText().toString()==""){
                objutilidad.MensajeToast(this,"Ingrese la clave")
                txtCla.requestFocus()
            }else{
                //capturamos los valores
                usu=txtUsu.getText().toString()
                cla=txtCla.getText().toString()

                //enviamos los datos a la clase
                objusuario.usuario=usu
                objusuario.clave=cla

                //llamamos al metodo para validar
                ValidarUsuario(context,objusuario)

            }

        }

        btnSalir.setOnClickListener {
            objutilidad.SalirSistema(this)
        }
    }

    //funcion para validar el usuario
    fun ValidarUsuario(context: Context?, u: Usuario?){
        val call= usuarioService!!.ValidarUsuario(u)
        call!!.enqueue(object : Callback<List<Usuario>?> {
            override fun onResponse(call: Call<List<Usuario>?>, response: Response<List<Usuario>?>) {
                if(response.isSuccessful){
                    Log.e("respuesta",""+ response.body()!!.size)

                    if(response.body()!!.size==0){
                        objutilidad.MensajeToast(context,"Usuario o Clave incorrecta")
                        objutilidad.Limpiar(findViewById<View>(R.id.frm) as ViewGroup)
                        txtUsu.requestFocus()
                    }else{
                        if(response.body()!!.get(0).estado==false){
                            objutilidad.MensajeToast(context,"Usuario Deshabilitado")
                        }else{
                            objutilidad.MensajeToast(context,"Bienvenidos al Sistema")
                            //creamos una constante para llamar a la actividad que vamos abrir
                            val formulario=Intent(context,ActividadPrincipal::class.java)
                            //iniciamos la actividad nueva
                            startActivity(formulario)
                            //cerramos la actividad correspondiente
                            finish()
                            Log.e("mensaje","Se valido")
                        }

                    }

                }
            }

            override fun onFailure(call: Call<List<Usuario>?>, t: Throwable) {

                Log.e("Error: ", t.message!!)
            }


        })
    }
}




