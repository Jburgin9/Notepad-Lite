package org.quietlip.mvvmnotes.utilis;

import android.text.TextWatcher;

public class ButtonWatchers {
    private static TextWatcher enableSaveAndDelete;

//    public static void enableSaveAndDelete(Button saveBtn, Button deleteBtn) {
//        enableSaveAndDelete = new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.d(TAG, "beforeTextChanged: ");
//
//            }
//            /*
//             *Bug... it only takes note field for the buttons to be active
//             */
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d(TAG, "onTextChanged: ");
//                saveBtn.setEnabled(!TextUtils.isEmpty(noteDisplayEt.getText().toString()) && !TextUtils.isEmpty(noteTitleEt.getText().toString()));
//                deleteBtn.setEnabled(!TextUtils.isEmpty(noteDisplayEt.getText().toString()) && !TextUtils.isEmpty(noteTitleEt.getText().toString()));
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                Log.d(TAG, "afterTextChanged: ");
//                if (s.length() > 0) {
//                    saveBtn.setEnabled(true);
//                    deleteBtn.setEnabled(true);
//                } else {
//                    saveBtn.setEnabled(false);
//                    deleteBtn.setEnabled(false);
//                }
//            }
//        };
//    }
}
