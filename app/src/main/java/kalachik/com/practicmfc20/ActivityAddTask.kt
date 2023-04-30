package kalachik.com.practicmfc20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class ActivityAddTask : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var dbref: DatabaseReference
    var id = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val saveText = findViewById<Button>(R.id.btnSave)

        saveText.setOnClickListener {

            auth = Firebase.auth
            var user = auth.currentUser
            val db = FirebaseFirestore.getInstance()
            if (user != null) {
                db.collection("users").document(user.uid)
                    .get()
                val userid = user.uid
                dbref = FirebaseDatabase.getInstance().reference.child(userid).push().child("taskName")
                val newtask = findViewById<EditText>(R.id.textTask)
                val task = newtask.text.toString()

                dbref.setValue(task).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(baseContext, "Запись добавлена!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, ActivityEmployee::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

}