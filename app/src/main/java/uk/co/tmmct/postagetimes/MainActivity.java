package uk.co.tmmct.postagetimes;

import android.app.Activity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity implements DatePicker.OnDateChangedListener {

    Date today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutThings();
    }

    private void layoutThings() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        today = calendar.getTime();

        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        datePicker.init(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                this);
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
        Date latestArrivalDate = getDateFromYMD(year, month, day);

        Date latestFirstClassPost = PostageDatesService.getLatestPost(latestArrivalDate, 1);
        setFirstClassFields(latestFirstClassPost);

        Date latestSecondClassPost = PostageDatesService.getLatestPost(latestArrivalDate, 3);
        setSecondClassFields(latestSecondClassPost);
    }

    private void setSecondClassFields(Date latestPostDate) {
        TextView secondClassDate = (TextView) findViewById(R.id.second_class_date);
        secondClassDate.setText(latestPostDate.toString());
        TextView secondClassDaysLeft = (TextView) findViewById(R.id.second_class_days_left);
        secondClassDaysLeft.setText("Which is soon I guess");
    }

    private void setFirstClassFields(Date latestPostDate) {
        TextView firstClassDate = (TextView) findViewById(R.id.first_class_date);
        firstClassDate.setText(latestPostDate.toString());
        TextView firstClassDaysLeft = (TextView) findViewById(R.id.first_class_days_left);
        firstClassDaysLeft.setText("Which is soon I guess");
    }

    private static Date getDateFromYMD(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
