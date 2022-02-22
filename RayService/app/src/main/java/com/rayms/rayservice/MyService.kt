package com.rayms.rayservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.ArrayList

class MyService : Service() {
  private val persons: MutableList<Person>? = null

  override fun onBind(intent: Intent): IBinder {
    Log.e("MyService", "success onBind")
    return iBinder
  }

  private var iBinder: IBinder = object : IMyAidlInterface.Stub() {
    override fun addPerson(person: Person?) {
      person?.let { persons?.add(it) }
    }

    override fun getPersonList(): MutableList<Person>? {
      return persons
    }
  }

  override fun onCreate() {
    super.onCreate()
    Log.e("MyService", "onCreate success")
  }
}