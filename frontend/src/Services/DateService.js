import moment from "moment/moment.js";

export class DateService {

    currentWeekOfYear() {
        return moment().week();
    }

    weekOfYear(weekNumber) {
        return moment().week(weekNumber);
    }

    isoWeekDays(weekNumber) {
        return [1, 2, 3, 4, 5, 6, 7].map(iosWeekDay => {
            return moment().week(weekNumber).isoWeekday(iosWeekDay);
        });
    }
}