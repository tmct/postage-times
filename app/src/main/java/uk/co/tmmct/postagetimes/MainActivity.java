package uk.co.tmmct.postagetimes;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;

import java.util.Calendar;

public class MainActivity extends Activity implements DatePicker.OnDateChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutThings();
    }

    private void layoutThings() {
        Calendar calendar = Calendar.getInstance();
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), this);
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        Log.d("?!?!?!", "onDateChanged: ");
    }
}
