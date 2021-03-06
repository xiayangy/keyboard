package com.lml.keyboardtext;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class KeyboardUtil {
        private KeyboardView keyboardView;  
        private Keyboard k1; 
        public boolean isupper = false;//是否切换到大写
        boolean isShow=false;
        private EditText ed;  
   
        public KeyboardUtil(Activity act, Context ctx, EditText edit) {  
                this.ed = edit;  
                k1 = new Keyboard(ctx, R.xml.custom);
                keyboardView = (KeyboardView) act.findViewById(R.id.keyboard_view);  
                keyboardView.setKeyboard(k1);  
                keyboardView.setEnabled(true);  
             
                keyboardView.setOnKeyboardActionListener(listener);  
        }  
   
        private OnKeyboardActionListener listener = new OnKeyboardActionListener() {  
                @Override  
                public void swipeUp() {  
                }  
   
                @Override  
                public void swipeRight() {  
                }  
   
                @Override  
                public void swipeLeft() {  
                }  
   
                @Override  
                public void swipeDown() {  
                }  
   
                @Override  
                public void onText(CharSequence text) {  
                }  
   
                @Override  
                public void onRelease(int primaryCode) {  
                }  
   
                @Override  
                public void onPress(int primaryCode) {  
                }  
   
                @Override  
                public void onKey(int primaryCode, int[] keyCodes) {  
                        Editable editable = ed.getText();  
                        int start = ed.getSelectionStart();  
                        if (primaryCode == Keyboard.KEYCODE_CANCEL) {// ���  
                                hideKeyboard();  
                        } else if (primaryCode == Keyboard.KEYCODE_DELETE) {// ����  
                                if (editable != null && editable.length() > 0) {  
                                        if (start > 0) {  
                                                editable.delete(start - 1, start);  
                                        }  
                                }  
                        } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {// ��Сд�л�  
                                changeKey();  
                                keyboardView.setKeyboard(k1);  
                        } else if (primaryCode == 57419) { // go left  
                                if (start > 0) {  
                                        ed.setSelection(start - 1);  
                                }  
                        } else if (primaryCode == 57421) { // go right  
                                if (start < ed.length()) {  
                                        ed.setSelection(start + 1);  
                                }  
                        } else {  
                                editable.insert(start, Character.toString((char) primaryCode));  
                        }  
                }  
        };  
           
        /** 
         * ���̴�Сд�л� 
         */  
        private void changeKey() {  
                List<Key> keylist = k1.getKeys();  
                if (isupper) {//��д�л�Сд  
                        isupper = false;  
                        for(Key key:keylist){  
                                if (key.label!=null && isword(key.label.toString())) {  
                                        key.label = key.label.toString().toLowerCase();  
                                        key.codes[0] = key.codes[0]+32;  
                                }else if(key.label.toString().equals("Сд")){
                                	key.label="��д";
                                }
                                
                        }  
                } else {//Сд�л���д  
                        isupper = true;  
                        for(Key key:keylist){  
                                if (key.label!=null && isword(key.label.toString())) {  
                                        key.label = key.label.toString().toUpperCase();  
                                        key.codes[0] = key.codes[0]-32;  
                                }  else if(key.label.toString().equals("��д")){
                                	key.label="Сд";
                                }
                        }  
                }  
        }  
   
    public void showKeyboard() {  
        int visibility = keyboardView.getVisibility();  
        if (visibility == View.GONE || visibility == View.INVISIBLE) {  
            keyboardView.setVisibility(View.VISIBLE);  
            isShow=true;
        }  
    }  
       
    public void hideKeyboard() {  
        int visibility = keyboardView.getVisibility();  
        if (visibility == View.VISIBLE) {  
            keyboardView.setVisibility(View.INVISIBLE);  
            isShow=false;
        }  
    }  
       
    private boolean isword(String str){  
            String wordstr = "abcdefghijklmnopqrstuvwxyz";  
            if (wordstr.indexOf(str.toLowerCase())>-1) {  
                        return true;  
                }  
            return false;  
    } 
}
