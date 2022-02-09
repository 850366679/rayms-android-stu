package com.rayms.study.schemo.myrxjava.demo2

/**
 * 抽象被观察者
 */
interface ObservableSource<T> {
  /**
   * notify() 将给定的Observer订阅功能定义出来
   */
  fun subscribe(observer: Observer<T>)
}