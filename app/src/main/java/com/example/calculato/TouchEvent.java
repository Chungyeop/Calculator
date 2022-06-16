package com.example.calculato;

import android.view.MotionEvent;
import android.view.View;

public class TouchEvent implements View.OnTouchListener {
    private Arithmetics arithmetics;

    public TouchEvent(Arithmetics arithmetics) {
        this.arithmetics = arithmetics;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
        }
        if(motionEvent.getAction() == MotionEvent.ACTION_UP){                               //handler_down이 runnable_down을 removeCallbacks 함
            switch (view.getId()) {
                case R.id.backBtn:
                    arithmetics.getHandler_down().removeCallbacks(arithmetics.getRunnable_down());
                    break;
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
                    arithmetics.getHandler_up().removeCallbacks(arithmetics.getRunnable_up());
                    break;
            }
        }
        return false;
    }
}
