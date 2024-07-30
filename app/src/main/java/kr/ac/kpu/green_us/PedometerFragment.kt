package kr.ac.kpu.green_us

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kr.ac.kpu.green_us.databinding.FragmentPedometerBinding

class PedometerFragment : Fragment(), SensorEventListener {
    lateinit var binding: FragmentPedometerBinding
    var currentSteps = 0
    lateinit var sensorManager: SensorManager
    lateinit var stepCountSensor: Sensor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPedometerBinding.inflate(inflater, container, false)

        binding.stepCounter.setText(currentSteps.toString())
        binding.pedometerDegree.setProgress(currentSteps)
        sensorManager =
            (activity!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager)!!
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)!!

        // 디바이스에 걸음 센서의 존재 여부 체크
        if (stepCountSensor == null) {
            Toast.makeText(getActivity(), "No Step Sensor", Toast.LENGTH_SHORT).show();
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if(stepCountSensor != null){
            sensorManager.registerListener(this,stepCountSensor,SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // 걸음 센서 이벤트 발생시
        if(event!!.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){

            if(event.values[0]==1.0f){
                // 센서 이벤트가 발생할때 마다 걸음수 증가
                currentSteps++
                binding.stepCounter.setText(currentSteps.toString())
                var progressNum = (currentSteps/100).toInt()
                binding.pedometerDegree.setProgress(progressNum)

            }

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}