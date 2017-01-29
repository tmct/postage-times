package uk.co.tmmct.postagetimes;

import android.app.Activity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class MainActivity extends Activity implements DatePicker.OnDateChangedListener {

    DateTime today;

    private static DateTime getDateFromYMD(int year, int month, int day) {
        return new DateTime().withYear(year).withMonthOfYear(month).withDayOfMonth(day);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutThings();
    }

    private void layoutThings() {
        today = DateTime.now().withTimeAtStartOfDay();

        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        datePicker.init(
                today.getYear(),
                today.getMonthOfYear() - 1,
                today.getDayOfMonth(),
                this);
        showPostageTimes(today);
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int year, int monthIndex, int day) {
        DateTime latestArrivalDate = getDateFromYMD(year, monthIndex + 1, day);

        showPostageTimes(latestArrivalDate);
    }

    private void showPostageTimes(DateTime latestArrivalDate) {
        DateTime latestFirstClassPost = PostageDatesService.getLatestPost(latestArrivalDate, 1);
        setFirstClassFields(latestFirstClassPost);

        DateTime latestSecondClassPost = PostageDatesService.getLatestPost(latestArrivalDate, 3);
        setSecondClassFields(latestSecondClassPost);
    }

    private void setFirstClassFields(DateTime latestPostDate) {
        TextView firstClassDate = (TextView) findViewById(R.id.first_class_date);
        firstClassDate.setText(prettyPrintDate(latestPostDate));
        String daysLeftText = getDaysLeftText(latestPostDate);
        TextView firstClassDaysLeft = (TextView) findViewById(R.id.first_class_days_left);
        firstClassDaysLeft.setText(daysLeftText);
    }

    private String getDaysLeftText(DateTime latestPostDate) {
        Integer daysLeft = getDaysLeft(latestPostDate);
        return String.format("%s day%s left", daysLeft, daysLeft == 1 ? "" : "s");
    }

    private Integer getDaysLeft(DateTime latestPostDate) {
        return Days.daysBetween(today.toLocalDate(), latestPostDate.toLocalDate()).getDays();
    }

    private void setSecondClassFields(DateTime latestPostDate) {
        TextView secondClassDate = (TextView) findViewById(R.id.second_class_date);
        secondClassDate.setText(prettyPrintDate(latestPostDate));
        String daysLeftText = getDaysLeftText(latestPostDate);
        TextView secondClassDaysLeft = (TextView) findViewById(R.id.second_class_days_left);
        secondClassDaysLeft.setText(daysLeftText);
    }

    private String prettyPrintDate(DateTime date) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("E d MMMM");
        return date.toString(fmt);
    }
}
