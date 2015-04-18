package com.lml.keyboardtext;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.EditText;

import java.lang.reflect.Method;

public class KeydemoActivity extends Activity {
	private Context ctx;
	private Activity act;
	private EditText edit;

	KeyboardUtil util;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ctx = this;
		act = this;
		edit = (EditText) this.findViewById(R.id.edit1);
		
		if (android.os.Build.VERSION.SDK_INT <= 10) {
			edit.setInputType(InputType.TYPE_NULL);
		} else {
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			try {
				Class<EditText> cls = EditText.class;
				Method setShowSoftInputOnFocus;
//				setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
				setShowSoftInputOnFocus = cls.getMethod("setSoftInputShownOnFocus", boolean.class);//
				setShowSoftInputOnFocus.setAccessible(false);
				setShowSoftInputOnFocus.invoke(edit, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		util=new KeyboardUtil(act, ctx, edit);

		edit.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				util.showKeyboard();
				return false;
			}
		});
		
		

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode==KeyEvent.KEYCODE_BACK){
			if(util.isShow){
				util.hideKeyboard();
			}else{
				finish();
			}
			return false;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	
}
