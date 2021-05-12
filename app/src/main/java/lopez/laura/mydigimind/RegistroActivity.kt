package lopez.laura.mydigimind

import android.R.attr.password
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegistroActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        auth = Firebase.auth
        //auth = Firabase.auth
        val btn_registrar: Button = findViewById(R.id.btn_registrar)

        btn_registrar.setOnClickListener{
            valida_registro()
        }
    }

    private fun valida_registro(){
        val et_correo: EditText = findViewById(R.id.et_correo_reg)
        val et_contra1: EditText = findViewById(R.id.et_contra_reg)
        val et_contra2: EditText = findViewById(R.id.et_contra2_reg)

        var correo: String = et_correo.text.toString()
        var contra1: String = et_contra1.text.toString()
        var contra2: String = et_contra2.text.toString()

        if(!correo.isNullOrBlank() && !contra1.isNullOrBlank() &&
            !contra2.isNullOrBlank()){

            if(contra1 == contra2){

                registrarFirebase(correo, contra1)

            }else{
                Toast.makeText(this, "Las contraseña no coinciden",
                    Toast.LENGTH_SHORT).show()
            }

        }else{
            Toast.makeText(this, "Ingresar datos",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun registrarFirebase(correo:String, contasenia:String){
        auth.createUserWithEmailAndPassword(correo, contasenia)
            .addOnCompleteListener(this){ task->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(this, "Se ha creado correctamente el usuario",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

            }
    }


}