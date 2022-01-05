package com.rayms.study.test

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit.SECONDS

/**
 * 学习线程池的工作原理
 * 为什么Dispatcher的线程池会使用SynchronousQueue
 * 而不是其他的，如：ArrayBlockingQueue
 */
fun main() {
  // arrayBlockingQueueTest()
  synchronousQueueTest()
}

fun arrayBlockingQueueTest() {
  // 数字1：代表等待执行线程数
  val quene = ArrayBlockingQueue<Runnable>(1)
  val poolExecutor = ThreadPoolExecutor(0, Int.MAX_VALUE, 60, SECONDS, quene)
  poolExecutor.execute {
    println("任务一：${Thread.currentThread()}")
    while (true) {
    }
  }

  poolExecutor.execute {
    println("任务二：${Thread.currentThread()}")
  }

  // 如果没有任务三，那么任务二永远不会执行
  // 出现任务三，等待执行的任务数组满了，就会新建一个线程执行任务三
  // 任务三执行完毕之后，就有一个空闲线程，这个时候才回去执行任务二
  // 就出现了：后提交的任务反而先执行
  poolExecutor.execute {
    println("任务三：${Thread.currentThread()}")
  }
}

fun synchronousQueueTest() {
  // SynchronousQueue是一个没有容量的容器
  // 就实现了最大并发，不需要等待
  // 就是因为这个特点，OkHttp的Dispatcher请求线程池使用的就是这种线程池
  val quene = SynchronousQueue<Runnable>()
  val poolExecutor = ThreadPoolExecutor(0, Int.MAX_VALUE, 60, SECONDS, quene)
  poolExecutor.execute {
    println("任务一：${Thread.currentThread()}")
    while (true) {
    }
  }

  poolExecutor.execute {
    println("任务二：${Thread.currentThread()}")
  }

  poolExecutor.execute {
    println("任务三：${Thread.currentThread()}")
  }
}