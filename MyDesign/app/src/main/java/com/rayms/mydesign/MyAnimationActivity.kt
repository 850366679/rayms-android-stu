package com.rayms.mydesign

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.rayms.mydesign.myview.CustomView

class MyAnimationActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_my_animation)
    val cv = findViewById<CustomView>(R.id.cv)
    // findViewById.animation = AnimationUtils.loadAnimation(this, R.anim.translate)

    // val ofFloat1 = ObjectAnimator.ofFloat(cv, "translationX", 0f, 300f, 0f)
    // val ofFloat2 = ObjectAnimator.ofFloat(cv, "scaleX", 1f, 2f)
    // val ofFloat3 = ObjectAnimator.ofFloat(cv, "rotationX", 0f, 90f, 0f)
    // val animatorSet = AnimatorSet()
    // animatorSet.setDuration(5000).play(ofFloat1).with(ofFloat2).after(ofFloat3)
    // animatorSet.start()

    val loadAnimator = AnimatorInflater.loadAnimator(this, R.animator.scale)
    loadAnimator.setTarget(cv)
    loadAnimator.start()

    // cv.smoothScrollTo(-400, -800)

    val btn = findViewById<Button>(R.id.btn_change)
    val myView = MyView(btn)
    btn.setOnClickListener {
      ObjectAnimator.ofInt(myView, "width", 500).setDuration(5000).start()
    }
    findViewById<Button>(R.id.btn_value_animator).setOnClickListener {
      myValueAnimator(btn)
    }
  }

  private class MyView(var mTarget: View) {
    fun getWidth() = mTarget.layoutParams.width
    fun setWidth(width: Int) {
      mTarget.layoutParams.width = width
      mTarget.requestLayout()
    }
  }

  private fun myValueAnimator(btn: View) {
    val animator = ValueAnimator.ofFloat(0f, 100f)
    animator.setTarget(btn)
    animator.setDuration(1000).start()
    animator.addUpdateListener {
      println(it.animatedValue)
    }
  }
}