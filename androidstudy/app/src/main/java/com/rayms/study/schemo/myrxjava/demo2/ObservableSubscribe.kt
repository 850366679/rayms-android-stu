package com.rayms.study.schemo.myrxjava.demo2

/**
 * 为每一个订阅的观察者提供发消息的功能
 */
interface ObservableSubscribe<T> {
  fun subscribe(emitter: Emitter<T>)
}