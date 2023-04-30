package kalachik.com.practicmfc20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class ActivityAddEmp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_emp)

        auth = Firebase.auth
        val register = findViewById<Button>(R.id.btnRegister)

        register.setOnClickListener {

            val email = findViewById<EditText>(R.id.email)
            val pass = findViewById<EditText>(R.id.password)
            val phone = findViewById<EditText>(R.id.phone)
            val name = findViewById<EditText>(R.id.name)

            val email1 = email.text.toString()
            val password = pass.text.toString()
            val phone1 = phone.text.toString()
            val name1 = name.text.toString()

            if (email.text.isEmpty() || pass.text.isEmpty() || phone.text.isEmpty()) {
                Toast.makeText(baseContext, "Ошибка регистрации! Проверьте поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email1, password)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext, "Аккаунт успешно создан!",
                            Toast.LENGTH_SHORT
                        ).show()
                        auth = Firebase.auth
                        var user = auth.currentUser
                        val db = FirebaseFirestore.getInstance()
                        if (user != null) {
                            db.collection("users").document(user.uid)
                                .get()
                            val userid = user.uid
//спросить как можно сократить
                            dbRef = FirebaseDatabase.getInstance().reference.child(userid).child("1").child("taskName")
                            val hellotext1 ="Это заметки! Здесь ты можешь добавлять"
                            val hellotext2 ="Или удалять записи, которые тебе не нужны!"
                            dbRef.setValue(hellotext1).addOnCompleteListener {
                            }
                            dbRef = FirebaseDatabase.getInstance().reference.child(userid).child("2").child("taskName")
                            dbRef.setValue(hellotext2).addOnCompleteListener {
                            }
                        }
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(name1)
                            .build()

                        user?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { task ->
                            }

                        val userUpdates = hashMapOf<String, Any>(
                            "phone" to phone1
                        )



                        db.collection("users").document(user?.uid.toString())
                            .set(userUpdates)
                            .addOnSuccessListener { documentReference ->
                            }
                            .addOnFailureListener { e ->
                            }



                        auth.signOut()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            baseContext, "Ошибка!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
                .addOnFailureListener {
                    Toast.makeText(this, "Ошибка: ${it.localizedMessage}", Toast.LENGTH_SHORT)
                        .show()
                }

        }

    }
}
