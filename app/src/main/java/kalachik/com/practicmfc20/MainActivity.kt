package kalachik.com.practicmfc20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            Toast.makeText(baseContext, "С возвращением",
                Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ActivityEmployee::class.java)
                startActivity(intent)
        }

        val register = findViewById<Button>(R.id.btnReg)

        register.setOnClickListener {

            val intent = Intent(this, ActivityAddEmp::class.java)
            startActivity(intent)
        }

        val login = findViewById<Button>(R.id.btnEnter)

        login.setOnClickListener {
            val email = findViewById<EditText>(R.id.etLog)
            val pass = findViewById<EditText>(R.id.etPass)
            val email1 = email.text.toString()
            val password = pass.text.toString()

            if (email.text.isEmpty() || pass.text.isEmpty()){
                Toast.makeText(baseContext, "Пожалуйста, заполните все поля!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email1, password)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {

                        Toast.makeText(baseContext, "Вы вошли успешно!",
                            Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, ActivityEmployee::class.java)
                            startActivity(intent)

                    }
                    else {

                        Toast.makeText(baseContext, "Ошибка входа. Попробуйте еще раз",
                            Toast.LENGTH_SHORT).show()

                    }
                }
                .addOnFailureListener{

                    Toast.makeText(baseContext, "Ошибка входа. ${it.localizedMessage}",
                        Toast.LENGTH_SHORT).show()

                }
        }
    }
}



