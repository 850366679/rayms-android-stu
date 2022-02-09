package com.rayms.study.schemo.myfacyory

/**
 * @auther RayMouns
 * @date 2022/2/9
 * @time 14:46.
 */
abstract class ComputerFactory {
  abstract fun <T : Computer?> createComputer(clz: Class<T>?): T?
}
