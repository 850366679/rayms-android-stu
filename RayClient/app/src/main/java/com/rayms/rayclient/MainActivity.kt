package com.rayms.rayclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.rayms.rayservice.IMyAidlInterface
import com.rayms.rayservice.Person

class MainActivity : AppCompatActivity() {
  private var asInterface: IMyAidlInterface? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    startService()
    setClick()
  }

  private fun setClick() {
    val btn = findViewById<Button>(R.id.btn_getPerson)
    btn.setOnClickListener {
      val person = Person()
      person.name = "张三"
      person.grade = 98
      asInterface?.addPerson(person)
      // Toast.makeText(this, asInterface?.toString() ?: "", Toast.LENGTH_SHORT).show()
      Toast.makeText(this, asInterface?.personList.toString(), Toast.LENGTH_SHORT).show()
    }
  }

  private fun startService() {
    findViewById<Button>(R.id.btn_bindservice).setOnClickListener{
      val intent = Intent()
      intent.action = "com.rayms.testservice"
      intent.`package` = "com.rayms.rayservice"
      // intent.component = ComponentName("com.rayms.rayservice", "com.rayms.rayservice.MyService")
      bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }
  }

  private val connection = object : ServiceConnection {
    override fun onServiceConnected(
      name: ComponentName?,
      service: IBinder?
    ) {
      Log.e("MainActivity", "onServiceConnected success")
      asInterface = IMyAidlInterface.Stub.asInterface(service)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    unbindService(connection)
  }
}