package com.example.sistema_modas

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.sistema_modas.databinding.ActivityMainBinding

class ActividadPrincipal : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //creamos una constante para poder manejar las opciones del menu
        val id=item.itemId

        return when (id) {
            R.id.jmiInicio ->{
                //creamos una constante del fragmento que vamos a cambiar
                val finicio=FragmentoInicio()
                //en el contenedor reemplazamos con el fragmento requerido
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,finicio).commit()
                true
            }

            R.id.jmiCliente->{
                val fcliente=FragmentoCliente()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fcliente).commit()
                true
            }


            R.id.jmiCategoria ->{
                val fcategoria=FragmentoCategoria()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fcategoria).commit()
                true
            }
            R.id.jmiDistrito ->{
                val fdistrito=Fragmento_Distrito()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fdistrito).commit()
                true
            }
            R.id.jmiRol ->{
                val frol=FragmentoRol()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,frol).commit()
                true
            }
            R.id.jmiDetalle ->{
                val fdt=Fragmento_DetalleVenta()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fdt).commit()
                true
            }
            R.id.jmiEmpleado->{
                val fem=Fragmento_Empleado()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fem).commit()
                true
            }

            R.id.jmiPrenda ->{
                val fpre=Fragmento_Prenda()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fpre).commit()
                true
            }
            R.id.jmiVenta ->{
                val fvent=Fragmento_Venta()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fvent).commit()
                true
            }
            R.id.jmiUsuarioRol ->{
                val fusuariorol=FragmentoUsuarioRol()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fusuariorol).commit()
                true
            }
            R.id.jmiBuscarCliente ->{
                val fbuscarcliente=FragmentoBuscarCliente()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fbuscarcliente).commit()
                true
            }
            R.id.jmiBuscarEmpleado ->{
                val fbuscarempleado=FragmentoBuscarEmpleado()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fbuscarempleado).commit()
                true
            }
            R.id.jmiBuscarDistrito ->{
                val fbuscardistrito=FragmentoBuscarDistrito()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fbuscardistrito).commit()
                true
            }
            R.id.jmiBuscarCategoria ->{
                val fbuscarcategoria=FragmentoBuscarCategoria()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fbuscarcategoria).commit()
                true
            }
            R.id.jmiBuscarRol ->{
                val fbuscarrol=FragmentoBuscarRol()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fbuscarrol).commit()
                true
            }
            R.id.jmiBuscarPrenda ->{
                val fbuscarprenda=FragmentoBuscarPrenda()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fbuscarprenda).commit()
                true
            }

            R.id.jmiBuscarVenta ->{
                val fbuscarventaa=FragmentoBuscarVenta()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fbuscarventaa).commit()
                true
            }
            R.id.jmiCerrarSesion->{
                val formulario= Intent(this,ActividadIngreso::class.java)
                startActivity(formulario)
                this.finish()
                true
            }
            R.id.jmiSalir->{
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}