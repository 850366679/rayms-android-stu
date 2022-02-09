package com.rayms.study.schemo.myrxjava.demo2

/**
 * 用来创建被观察者对象
 */
class ObservableCreate<T>(val subscribe: ObservableSubscribe<T>) : Observable<T>() {
  override fun subscribeActual(observer: Observer<T>) {
    val emitter = CreateEmitter(observer)
    observer.onSubscribe()
    subscribe.subscribe(emitter)
  }

  class CreateEmitter<T>(val observer: Observer<T>) : Emitter<T> {
    override fun onNext(t: T) {
      observer.onNext(t)
    }

    override fun onError(throwable: Throwable) {
      observer.onError(throwable)
    }

    override fun complete() {
      observer.onComplete()
    }
  }
}