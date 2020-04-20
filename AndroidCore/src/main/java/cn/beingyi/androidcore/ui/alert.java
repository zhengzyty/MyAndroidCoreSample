package cn.beingyi.androidcore.ui;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;


public class alert {

    public alert(final Context context,final String title, final String text,final String ok) {

        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                AlertDialog dialog = new AlertDialog.Builder(context)

                        .setTitle(title)
                        .setMessage(text)
                        .setCancelable(false)

                        .setPositiveButton(ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();


            }
        });


    }


}


