package lopez.laura.mydigimind.ui.dashboard

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_dashboard.*
import lopez.laura.mydigimind.R
import lopez.laura.mydigimind.ui.Task
import lopez.laura.mydigimind.ui.home.HomeFragment
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val btntime : Button = root.findViewById(R.id.btn_time)
        btntime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                btn_time.text = SimpleDateFormat("HH:mm")
                        .format(cal.time)
            }

            TimePickerDialog(root.context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true).show()
            }

        val btnsave :Button = root.findViewById(R.id.btn_save)
        val ettask : EditText = root.findViewById(R.id.et_task)
        val bttime : Button = root.findViewById(R.id.btn_time)
        btnsave.setOnClickListener {
            var title = ettask.text.toString()
            var time = bttime.text.toString()
            var days = ArrayList<String>()

            val check_Monday : CheckBox = root.findViewById(R.id.checkMonday)
            val check_Tuesday : CheckBox = root.findViewById(R.id.checkTuesday)
            val check_Wednesday : CheckBox = root.findViewById(R.id.checkWednesday)
            val check_Thursday : CheckBox = root.findViewById(R.id.checkThursday)
            val check_Friday : CheckBox = root.findViewById(R.id.checkFriday)
            val check_Saturday : CheckBox = root.findViewById(R.id.checkSaturday)
            val check_Sunday : CheckBox = root.findViewById(R.id.checkSunday)

            if(check_Monday.isChecked)days.add("Monday")
            if(check_Tuesday.isChecked)days.add("Tuesday")
            if(check_Wednesday.isChecked)days.add("Wednesday")
            if(check_Thursday.isChecked)days.add("Thursday")
            if(check_Friday.isChecked)days.add("Friday")
            if(check_Saturday.isChecked)days.add("Saturday")
            if(check_Sunday.isChecked)days.add("Sunday")

            var task = Task(title, days, time)
            HomeFragment.tasks.add(task)
            Toast.makeText(root.context, "new task added", Toast.LENGTH_SHORT).show()
        }

        return root
    }
}