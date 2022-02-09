package com.rayms.study.schemo.myrxjava.demo2

/**
 * 定义发送消息的发射器
 */
interface Emitter<T> {
  // 发送消息
  fun onNext(t: T)

  // 发送异常
  fun onError(throwable: Throwable)

  // 发送完成
  fun complete()
}