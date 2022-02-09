package com.rayms.study.schemo.myrxjava.demo1

fun main() {
  val user = User("rayms")
  val user2 = User("zhangl")
  val user3 = User("lis")
  val user4 = User("yueyue")

  val service = WechatService()
  service.add(user)
  service.add(user2)
  service.add(user3)
  service.add(user4)

  service.pushMessage("紧急通知")
}