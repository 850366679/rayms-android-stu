package com.rayms.mydesign.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import java.util.ArrayList;

/**
 * @auther RayMouns
 * @date 2022/2/22
 * @time 15:24.
 */
public class TestView extends View {
  private ArrayList<Integer> aa = new ArrayList<>();
  public TestView(Context context) {
    super(context);
  }

  public TestView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);

  }
}
