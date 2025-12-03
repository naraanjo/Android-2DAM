package activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.camera2.pipe.core.Log
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebasehoy.R
import com.example.firebasehoy.databinding.ActivityHomeBinding
import com.example.firebasehoy.modelo.User
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Random


class Home : AppCompatActivity() {


    var miArray: ArrayList<User> =
        ArrayList()  //Este será el arrayList que se usará para el adapter del RecyclerView o de la ListView.

    //Valores fake.
    val nombres = listOf("Ragnar", "Ivar", "Lagertha", "Floki")
    val apellidos = listOf("Lothbrok", "Sin huesos", "Piel de Hierro", "Semi diosa")
    val edades = listOf(18, 23, 45, 67, 34, 47, 41)

    lateinit var binding: ActivityHomeBinding
    private lateinit var firebaseauth: FirebaseAuth
    val TAG = "ALN"
    val db = Firebase.firestore

    //infla el menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Maneja los clics en el menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Lleva a otra actividad (simulado)
                finish()
                // val intent = Intent(this, MainActivity::class.java)
                //startActivity(intent)
                true
            }

            R.id.action_camera -> {
                Toast.makeText(this, "Cámara seleccionada", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.action_gallery -> {
                Toast.makeText(this, "Galería seleccionada", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.action_settings -> {
                Toast.makeText(this, "Configuración seleccionada", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.action_help -> {
                Toast.makeText(this, "Ayuda seleccionada", Toast.LENGTH_SHORT).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Configura el Toolbar
        val toolbar = binding.toolbar2
        setSupportActionBar(toolbar)
        // Establece un título personalizado
        supportActionBar?.title = "Home"

        // Agregar un ícono como logo
        //supportActionBar?.setLogo(R.drawable.ic_my_logo)
        supportActionBar?.setDisplayUseLogoEnabled(true)

        // Habilita la flecha de retroceso
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Para la autenticación, de cualquier tipo.
        firebaseauth = FirebaseAuth.getInstance()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Recuperamos los datos del login.
        binding.txtEmail.text = intent.getStringExtra("email").toString()
        binding.txtProveedor.text = intent.getStringExtra("provider").toString()
        binding.txtNombre.text = intent.getStringExtra("nombre").toString()

        binding.btCerrarSesion.setOnClickListener {
            //Log.e(TAG, firebaseauth.currentUser.toString())

            //Con las siguientes líneas cierras sesión y el usuario tiene que volver a logearse en la ventana anterior.
            firebaseauth.signOut()
            val signInClient = Identity.getSignInClient(this)
            signInClient.signOut()
            // Log.e(TAG, "Cerrada sesión completamente")
            finish()
            // Si descomentas el siguiente bloque, olvidas al usuario, limpiando cualquier referencia persistente, es decir lo elimina de Firebase
            //comprobadlo en Firebase, como ha desaparecido.

            /*firebaseauth.currentUser?.delete()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseauth.signOut()
                    Log.e(TAG,"Cerrada sesión completamente")
                } else {
                    Log.e(TAG,"Hubo algún error al cerrar la sesión")
                }
            }
            */
        }
        binding.btVolver.setOnClickListener {
            // Log.e(TAG, firebaseauth.currentUser.toString())
            firebaseauth.signOut()
            finish()
        }

        binding.btGuardar.setOnClickListener {
            //Se guardarán en modo HashMap (clave, valor).
            var user = hashMapOf(
                "provider" to binding.txtProveedor.text,
                "email" to binding.txtEmail.text.toString(),
                "name" to binding.edNombre.text.toString(),
                "age" to binding.edEdad.text.toString(),
                "roles" to arrayListOf(1, 2, 3),
                "timestamp" to FieldValue.serverTimestamp()
            )

            // Si no existe el documento lo crea, si existe lo remplaza.
            // Opción Recomendada: Usar .add()
// Esto crea un documento nuevo cada vez con un ID único automático
            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "Guardado con ID: ${documentReference.id}", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                }


            //Otra forma
            //Si no existe el documento lo crea, si existe añade otro. Las id serán asignadas automáticamente.
          /*  db.collection("users")
                .add(user)
                .addOnSuccessListener {
                    Toast.makeText(this, "Almacenado", Toast.LENGTH_SHORT).show()
                    // LOG
                }
                .addOnFailureListener { e ->
                    // LOG
                }
            // this.crearUnoAutomaticoConIndiceAleatorio()
            this.crearUnoAutomaticoConIndiceNoAleatorio()
            this.crearCamposArray()*/


        }

        binding.btEliminar.setOnClickListener {
            //Buscamos antes si existe un campo con ese email en un documento.
            val id = db.collection("users")
                .whereEqualTo("email", binding.txtEmail.text.toString())
                .get()
                .addOnSuccessListener { result ->
                    //En result, vienen los que cumplen la condición (si no pongo nada es it)
                    //Con esto borramos el primero.
                    //db.collection("users").document(result.elementAt(0).id).delete().toString()
                    //Con esto borramos todos. No olvidar que id aquí no es una Primarykey, puede repetirse.
                    for (document in result) {
                        db.collection("users")
                            .document(document.id)
                            .delete()
                            .toString() //lo importante aquí es el delete. el toString es pq además devuelve un mensaje con lo sucedido.
                    }

                    Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        this,
                        "No se ha encontrado el documento a eliminar",
                        Toast.LENGTH_SHORT
                    ).show()
                }

        }

        //https://cloud.google.com/firestore/docs/query-data/queries?hl=es-419#kotlin+ktxandroid_3
        binding.btRecuperarTodos.setOnClickListener {
            //Sin Corrutinas
            //var al = ArrayList<String>()
//            db.collection("users")
//                //.whereEqualTo("name",binding.edNombre.text)
//                .get()
//                .addOnSuccessListener {
//                    for (document in it) {
//                        al.add(document.data.toString())
//                        Log.d(TAG, "${document.id} => ${document.data}")
////                        var us = User(document.get("age") as String,document.get("first") as String,document.get("last") as String,document.get("roles") as ArrayList<Int>)
////                        this.miArray.add(us)
//                        Log.d(TAG, al.toString())//Se va construyendo el array, y lo mostramos
//                    }
//
//                }
//                .addOnFailureListener { exception ->
//                    Log.w(TAG, "Error getting documents.", exception)
//                }
//            Log.e(TAG, "Aunque el código va después de la llamada el ArrayList está vacío: ${al.toString()}")

            //Observamos que esto nos da un AL vacío porque la consulta es asíncrona y necesitaremos hacerla con una corrutina.


            //Corrutinas 1.
            var al = ArrayList<String>()
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val querySnapshot = db.collection("users")
                        .get()
                        .await()

                    val results = mutableListOf<String>()
                    for (document in querySnapshot.documents) {
                        //Log.d(TAG, "${document.id} => ${document.data}")
                        al.add(document.data.toString())
                    }

                    // Realiza acciones en el hilo principal
                    launch(Dispatchers.Main) {
                        // Procesa los resultados aquí
                        //      TAG,
                        //     "Esto está en el hilo principal rellenado después de la corrutina: ${al.toString()}"
                        //)
                    }
                } catch (e: Exception) {
                    // Maneja errores aquí
                    e.printStackTrace()
                }
            }
//
//            //Corrutinas 2.
//            runBlocking {
//                val job : Job = launch(context = Dispatchers.Default) {
//                    val datos : QuerySnapshot = getDataFromFireStore() as QuerySnapshot //Obtenermos la colección
//                    obtenerDatos(datos as QuerySnapshot?)  //'Destripamos' la colección y la metemos en nuestro ArrayList
//
//                }
//                //Con este método el hilo principal de onCreate se espera a que la función acabe y devuelva la colección con los datos.
//                job.join() //Esperamos a que el método acabe: https://dzone.com/articles/waiting-for-coroutines
//            }
//            Log.e(TAG,"----------------")
//            for(e in miArray){//mostramos el array para demostrar que trae datos.
//                Log.d(TAG,e.toString())
//            }
//            Log.e(TAG,"----------------")
//            //Aquí se pondría en el setAdapter del RecyclerView.
//            Toast.makeText(this, "Datos cargados, consulta el log con la etiqueta ACSCO",Toast.LENGTH_SHORT).show()

        }//Fin del btnRecuperar.Onclick


    }//fin onCreate

    /**
     * Este método es una función suspend que esperará a que la consulta se realiza. Será llamada
     * en un scope (entorno) de corrutinas. Hilos (ver onCreate, runBlocking).
     */
    suspend fun getDataFromFireStore(): QuerySnapshot? {
        return try {
            val data = db.collection("users")
                //.whereEqualTo("name", "Lagertha")
                .whereGreaterThanOrEqualTo(
                    "age",
                    18
                )  //https://firebase.google.com/docs/firestore/query-data/order-limit-data?hl=es-419
                .orderBy("age", Query.Direction.DESCENDING)
                //.limit(4) //Limita la cantidad de elementos mostrados.
                .get()
                .await()
            data
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Este método crea datos al azar con índice aleatorio y usando la opción add.
     */
    fun crearUnoAutomaticoConIndiceAleatorio() {
        // Create a new user with a first and last name
        val randomNombre = nombres.random()
        val randomApellido = apellidos.random()
        val edadRandom = edades.random()

        var user = hashMapOf(
            "first" to randomNombre,
            "last" to randomApellido,
            "age" to edadRandom
        )

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
        /*  .addOnSuccessListener {
              Log.e(TAG, "Documento añadido con ID: ${it.id}")
          }
          .addOnFailureListener { e ->
              Log.w(TAG, "Error adding document", e.cause)
          }*/
    }

    //----------------------------------------------------------------------------------------
    /**
     * Este método crea datos al azar usando set y poniendo como índice del documento el DNI.
     */
    fun crearUnoAutomaticoConIndiceNoAleatorio() {
        // Create a new user with a first and last name
        val randomNombre = nombres.random()
        val randomApellido = apellidos.random()
        val edadRandom = edades.random()
        val random = Random()
        val DNI = random.nextInt(100)

        var user = hashMapOf(
            "first" to randomNombre,
            "last" to randomApellido,
            "age" to edadRandom
        )

        // Add a new document with a generated ID
        db.collection("users")
            .document(DNI.toString())  //Si hubiera un campo duplicado, lo reemplaza.
            .set(user)
        /*    .addOnSuccessListener {
                Log.e(TAG, "Documento añadido.")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e.cause)
            }*/
    }

    /**
     * Este método crea un documento que dentro tiene un campo de tipo array (para los roles).
     */
    fun crearCamposArray() {
        // Create a new user with a first and last name
        val randomNombre = nombres.random()
        val randomApellido = apellidos.random()
        val edadRandom = edades.random()
        val random = Random()
        val DNI = random.nextInt(100)

        var user = hashMapOf(
            "first" to randomNombre,
            "last" to randomApellido,
            "age" to edadRandom,
            "roles" to arrayListOf(1, 2, 3)
        )

        // Add a new document with a generated ID
        db.collection("users")
            .document(DNI.toString())  //Si hubiera un campo duplicado, lo remplaza.
            .set(user)
        /*  .addOnSuccessListener {
              Log.e(TAG, "Documento añadido.")
          }
          .addOnFailureListener { e ->
              Log.w(TAG, "Error adding document", e.cause)
          }*/
    }

    /**
     * Función que recuperará los datos obtenidos del método: getDataFromFireStore().
     * Llamada también desde el entorno de corrutinas: (ver onCreate, runBlocking).
     */
    private fun obtenerDatos(datos: QuerySnapshot?) {
        for (dc: DocumentChange in datos?.documentChanges!!) {
            if (dc.type == DocumentChange.Type.ADDED) {
                //miAr.add(dc.document.toObject(User::class.java))
                var roles: ArrayList<Int>

                if (dc.document.get("roles") != null) {
                    roles = dc.document.get("roles") as ArrayList<Int>
                } else {
                    roles = arrayListOf()
                }
                var al = User(
                    dc.document.get("age").toString(),
                    dc.document.get("first").toString(),
                    dc.document.get("last").toString(),
                    roles
                )
                //Log.e(TAG, al.toString())
                miArray.add(al)
            }
        }
    }


}
