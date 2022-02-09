package com.rayms.study.schemo.myrxjava.demo2

/**
 * 具体被观察者
 */
abstract class Observable<T> : ObservableSource<T> {

  override fun subscribe(observer: Observer<T>) {
    subscribeActual(observer)
  }

  protected abstract fun subscribeActual(observer: Observer<T>)

  companion object {
    fun <T> create(subscribe: ObservableSubscribe<T>): Observable<T> {
      return ObservableCreate(subscribe)
    }
  }
}