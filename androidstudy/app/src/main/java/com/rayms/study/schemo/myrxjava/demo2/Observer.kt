package com.rayms.study.schemo.myrxjava.demo2

/**
 * 抽象观察者
 */
interface Observer<T> {
  /**
   *  用于订阅成功的回调
   */
  fun onSubscribe()

  /**
   * 收到消息
   */
  fun onNext(t : T)

  /**
   * 出现异常
   */
  fun onError(throwable: Throwable)

  /**
   * 从订阅到发送消息全部完成
   */
  fun onComplete()
}