package com.rayms.study.schemo.myrxjava.demo1

class User(var name: String?) : Observer {
  var message: String? = null
  override fun update(obj: Any?) {
    message = obj as String?
    println("${name}收到消息，消息内容是:${message}")
  }
}