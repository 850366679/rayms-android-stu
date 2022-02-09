package com.rayms.study.schemo.myrxjava.demo1

/**
 * 抽象被观察者
 */
interface Observable {
  fun add(observer: Observer)
  fun remove(observer: Observer)

  // 通知消息发出
  fun notifyObservers()
}