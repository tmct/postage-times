package uk.co.tmmct.postagetimes;

import java.util.Calendar;
import java.util.Date;

class PostageDatesService {

    static Date getLatestPost(Date latestArrivalDate, int workingDays) {
        Date postDate = latestArrivalDate;
        int workingDaysLeft = workingDays + 1;
        while (workingDaysLeft > 0) {
            if (!dateIsSundayOrBankHoliday(postDate)) {
                workingDaysLeft--;
            }
            postDate = getPreviousDay(postDate);
        }
        return postDate;
    }

    private static boolean dateIsSundayOrBankHoliday(Date date) {
        //TODO bank hols
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    private static Date getPreviousDay(Date latestArrivalDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(latestArrivalDate);
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }
}
