package com.rayms.study.schemo.myrxjava.demo1

class WechatService : Observable {
  private var list: ArrayList<Observer>? = null
  private var message: String? = null

  init {
    list = arrayListOf()
  }

  override fun add(observer: Observer) {
    list?.add(observer)
  }

  override fun remove(observer: Observer) {
    list?.remove(observer)
  }

  override fun notifyObservers() {
    list?.forEach {
      it.update(message)
    }
  }

  fun pushMessage(message: String) {
    println("微信推送了一条消息：${message}")
    this.message = message
    notifyObservers()
  }
}