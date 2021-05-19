package lopez.laura.mydigimind.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.task_view.view.*
import lopez.laura.mydigimind.R
import lopez.laura.mydigimind.ui.Task

class HomeFragment : Fragment() {
    var tasks = ArrayList<Task>()
    private var adaptador: AdaptadorTareas? = null
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var storage: FirebaseFirestore
    private lateinit var usuario: FirebaseAuth

    companion object{
        var tasks = ArrayList<Task>()
        var first = true
    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        tasks=ArrayList()
        storage= FirebaseFirestore.getInstance()
        usuario= FirebaseAuth.getInstance()
        fillTask()

        if(first){
            fillTask()
            first = false
        }


        adaptador = AdaptadorTareas(root.context, tasks)

        val grid_v : GridView = root.findViewById(R.id.gridView)
        grid_v.adapter = adaptador

        return root
    }

    fun fillTask(){
        storage.collection("actividades").whereEqualTo("email", usuario.currentUser.email)
                .get().addOnSuccessListener {
                    it.forEach {
                        var dias=ArrayList<String>()
                        if(it.getBoolean("lu")==true){
                            dias.add("Monday")
                        }
                        if(it.getBoolean("ma")==true){
                            dias.add("Tuesday")
                        }
                        if(it.getBoolean("mi")==true){
                            dias.add("Wednesday")
                        }
                        if(it.getBoolean("ju")==true){
                            dias.add("Thursday")
                        }
                        if(it.getBoolean("vi")==true){
                            dias.add("Friday")
                        }
                        if(it.getBoolean("sa")==true){
                            dias.add("Saturday")
                        }
                        if(it.getBoolean("do")==true){
                            dias.add("Sunday")
                        }
                        tasks!!.add(Task(it.getString("actividad")!!, dias, it.getString("tiempo")!!))
                    }
                    adaptador = AdaptadorTareas(context!!, tasks)
                    gridView.adapter=adaptador

                }.addOnFailureListener {
                    Toast.makeText(context, "Error intetente de nuevo", Toast.LENGTH_SHORT).show()
                }


        tasks.add(Task("Practice 1", arrayListOf("Tuesday"), "17:30"))
        tasks.add(Task("Practice 2", arrayListOf("Monday", "Sunday"), "17:30"))
        tasks.add(Task("Practice 3", arrayListOf("Wednesday"), "14:00"))
        tasks.add(Task("Practice 4", arrayListOf("Saturday"), "11:00"))
        tasks.add(Task("Practice 5", arrayListOf("Friday"), "13:00"))
        tasks.add(Task("Practice 6", arrayListOf("Thursday"), "10:40"))
        tasks.add(Task("Practice 7", arrayListOf("Monday"), "12:00"))
    }

    private class AdaptadorTareas: BaseAdapter {
        var tasks = ArrayList<Task>()
        var contexto: Context? = null

        constructor(contexto: Context, tasks: ArrayList<Task>){
            this.contexto = contexto
            this.tasks = tasks
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var task = tasks[position]
            var inflator = LayoutInflater.from(contexto)
            var vista = inflator.inflate(R.layout.task_view, null)

            vista.tv_title.setText(task.title)
            vista.tv_time.setText(task.time)
            vista.tv_days.setText(task.days.toString())

            return vista
        }

        override fun getItem(position: Int): Any {
            return tasks[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return tasks.size
        }

    }
}