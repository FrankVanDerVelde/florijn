import moment from "moment/moment.js";

export class DateService {

    currentWeekOfYear() {
        return moment().week();
    }

    weekOfYear(weekNumber) {
        return moment().week(weekNumber);
    }

    dayOfWeek(weekNumber, dayIndex) {
        return moment().week(weekNumber).isoWeekday(dayIndex);
    }

    isoWeekDays(weekNumber) {
        return [1, 2, 3, 4, 5, 6, 7].map(isoWeekDay => {
            return {
                weekDayIndex: isoWeekDay,
                date:  moment().week(weekNumber).isoWeekday(isoWeekDay)
            }
        });
    }

    isSameDay(date1, date2) {
        const isSameYear = moment(date1).isSame(moment(date2), 'year');
        const isSameDay = moment(date1).isSame(moment(date2), 'day');
        return (isSameYear && isSameDay);
    }
}