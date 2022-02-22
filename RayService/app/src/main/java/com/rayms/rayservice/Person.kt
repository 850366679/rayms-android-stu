package com.rayms.rayservice

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator

/**
 * @auther RayMouns
 * @date 2022/2/16
 * @time 10:31.
 */
class Person() : Parcelable {
  var name: String? = null
  var grade: Int? = 0

  constructor(parcel: Parcel) : this() {
    name = parcel.readString()
    grade = parcel.readValue(Int::class.java.classLoader) as? Int
  }

  override fun writeToParcel(
    parcel: Parcel,
    flags: Int
  ) {
    parcel.writeString(name)
    parcel.writeValue(grade)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Creator<Person> {
    override fun createFromParcel(parcel: Parcel): Person {
      return Person(parcel)
    }

    override fun newArray(size: Int): Array<Person?> {
      return arrayOfNulls(size)
    }
  }
}