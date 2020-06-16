package com.ddducn.assignment8;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

public class NameEditText extends androidx.appcompat.widget.AppCompatEditText {
    public NameEditText(Context context) {
        super(context);
    }

    public NameEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NameEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        // clear focus when pressing the back key after finishing inputting the name
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) this.clearFocus();

        return super.onKeyPreIme(keyCode, event);
    }
}
