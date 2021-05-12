package lopez.laura.mydigimind

import android.R.attr.password
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth
        val btn_registrarse: Button = findViewById(R.id.btn_registrarse)
        val btn_contra: TextView = findViewById(R.id.tv_olvidasteContra)
        val btn_ingresar: Button = findViewById(R.id.btn_ingresar)

        btn_registrarse.setOnClickListener{
            val intent: Intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        btn_contra.setOnClickListener{
            val intent: Intent = Intent(this, ContraseniaActivity::class.java)
            startActivity(intent)
        }

        btn_ingresar.setOnClickListener{
            valida_ingreso()
        }
    }

    private fun valida_ingreso(){
        val et_correo: EditText = findViewById(R.id.et_correo)
        val et_contra: EditText = findViewById(R.id.et_contra)

        var correo: String = et_correo.text.toString()
        var contra: String = et_contra.text.toString()

        if(!correo.isNullOrBlank() && !contra.isNullOrBlank()){
            ingresaFirebase(correo, contra)
        }else{
            Toast.makeText(this, "Ingresar datos",
                Toast.LENGTH_SHORT).show()
        }

    }

    private fun ingresaFirebase(correo:String, contra:String){
        auth.signInWithEmailAndPassword(correo, contra)
            .addOnCompleteListener(this){task ->
                fun onComplete(task: Task<AuthResult?>) {
                    if (task.isSuccessful) {
                        val user: FirebaseUser = auth.getCurrentUser()
                        val intent: Intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {

                        Toast.makeText(
                            this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }
}