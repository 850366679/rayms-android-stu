package com.rayms.study.schemo.myrxjava.demo2

fun main() {
  Observable.create(object : ObservableSubscribe<String> {
    override fun subscribe(emitter: Emitter<String>) {
      emitter.onNext("紧急通知")
    }
  }).subscribe(object : Observer<String> {
    override fun onSubscribe() {
      println("订阅成功")
    }

    override fun onNext(t: String) {
      println("收到消息： $t")
    }

    override fun onError(throwable: Throwable) {
    }

    override fun onComplete() {
    }
  })
}