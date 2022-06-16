package com.example.calculato;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LongClickEvent implements View.OnLongClickListener {
    private Arithmetics arithmetics;


    public LongClickEvent(Arithmetics arithmetics) {
        this.arithmetics = arithmetics;
    }

    @Override
    public boolean onLongClick(View view) {
        Button button = (Button)view;
        switch (view.getId()) {
            case R.id.numBtn0:
            case R.id.numBtn1:
            case R.id.numBtn2:
            case R.id.numBtn3:
            case R.id.numBtn4:
            case R.id.numBtn5:
            case R.id.numBtn6:
            case R.id.numBtn7:
            case R.id.numBtn8:
            case R.id.numBtn9:
                arithmetics.setHandler(button);
                arithmetics.getHandler_up().post(arithmetics.getRunnable_up());
                break;
            case R.id.backBtn:
                arithmetics.setHandler(button);
                arithmetics.getHandler_down().post(arithmetics.getRunnable_down());
                break;
        }
        Toast.makeText(arithmetics,"LongClick", Toast.LENGTH_LONG).show();
        return false;
    }
}
