package uk.co.tmmct.postagetimes;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

class PostageDatesService {

    static DateTime getLatestPost(DateTime latestArrivalDate, int workingDays) {
        DateTime postDate = latestArrivalDate;
        int workingDaysLeft = workingDays + 1;
        while (workingDaysLeft > 0) {
            if (!dateIsSundayOrBankHoliday(postDate)) {
                workingDaysLeft--;
            }
            postDate = postDate.minusDays(1);
        }
        return postDate;
    }

    private static boolean dateIsSundayOrBankHoliday(DateTime date) {
        //TODO bank hols
        return date.getDayOfWeek() == DateTimeConstants.SUNDAY;
    }
}
