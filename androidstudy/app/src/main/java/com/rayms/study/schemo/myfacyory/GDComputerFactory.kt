package com.rayms.study.schemo.myfacyory

/**
 * @auther RayMouns
 * @date 2022/2/9
 * @time 14:52.
 */
class GDComputerFactory : ComputerFactory() {
  override fun <T : Computer?> createComputer(clz: Class<T>?): T? {
    var computer: Computer? = null
    val className = clz?.name
    try {
      computer = Class.forName(className).newInstance() as Computer?
    } catch (e: Exception) {
      e.printStackTrace()
    }
    return computer as T?
  }
}