package kalachik.com.practicmfc20

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kalachik.com.recyclerpack.MyAdapter
import kalachik.com.recyclerpack.Task

class ActivityEmployee : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var dbref: DatabaseReference
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var taskArrayList: ArrayList<Task>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)

        auth = Firebase.auth
        val logout = findViewById<Button>(R.id.btnExit)

        logout.setOnClickListener {

            auth.signOut()
            Toast.makeText(
                baseContext, "Вы вышли",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        val user = auth.currentUser

        if (user != null) {

            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(user.uid)
                .get()
                .addOnSuccessListener { document ->

                    if (document != null) {
                        val name = user.displayName
                        val userInfoTextView = findViewById<TextView>(R.id.textFIO)
                        userInfoTextView.text = "Добро пожаловать, $name\n"

                    }

                }
                .addOnFailureListener { exception ->

                    Toast.makeText(
                        baseContext, "Имя пользователя не установленно",
                        Toast.LENGTH_SHORT
                    ).show()

                }
        }


        taskRecyclerView = findViewById(R.id.task_recyclerView)
        taskRecyclerView.layoutManager = LinearLayoutManager(this)
        taskRecyclerView.setHasFixedSize(true)
        taskArrayList = arrayListOf<Task>()
        val del = findViewById<Button>(R.id.btnDel)
        del.setOnClickListener {
            auth = Firebase.auth
            var user = auth.currentUser
            val db = FirebaseFirestore.getInstance()
            if (user != null) {
                db.collection("users").document(user.uid)
                    .get()
                val userid = user.uid
                dbref = FirebaseDatabase.getInstance().reference.child(userid)
                dbref.removeValue().addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(baseContext, "Все удалено!!!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, ActivityEmployee::class.java)
                        startActivity(intent)

                    }
                }
            }
        }
        taskArrayList.clear()
        getTaskData()


    }

    override fun onBackPressed() {
        Toast.makeText(baseContext, "Чтобы выйти из аккаунта нажмите на кнопку внизу", Toast.LENGTH_LONG).show()
    }
    private fun getTaskData() {
        val user = auth.currentUser
        if (user != null) {

            val uid = user.uid
            dbref = FirebaseDatabase.getInstance().getReference(uid)
            dbref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {

                        for (taskSnapshot in snapshot.children) {

                            val task = taskSnapshot.getValue(Task::class.java)
                            taskArrayList.add(task!!)

                        }

                        taskRecyclerView.adapter = MyAdapter(taskArrayList)
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(baseContext, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        }

        val addtask = findViewById<Button>(R.id.btnAddTask)

        addtask.setOnClickListener {
            val intent = Intent(this, ActivityAddTask::class.java)
            startActivity(intent)
        }
    }

}
