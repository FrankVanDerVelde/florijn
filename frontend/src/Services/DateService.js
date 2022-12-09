import moment from "moment/moment.js";

export class DateService {

    currentWeekOfYear() {
        return moment().week();
    }

    currentDayOfWeek() {
        return moment().isoWeekday();
    }

    weekOfYear(weekNumber) {
        return moment().week(weekNumber);
    }

    dayOfWeek(weekNumber, dayIndex) {
        return moment().week(weekNumber).isoWeekday(dayIndex);
    }

    dayIndex(date) {
        return moment(date).day();
    }

    isoWeekDays(weekNumber) {
        return [1, 2, 3, 4, 5, 6, 7].map(isoWeekDay => {
            return this.#createWeekDayObjectFromIndex(isoWeekDay, weekNumber);
        });
    }

    isoWorkWeekDays(weekNumber) {
        return [1, 2, 3, 4, 5].map(isoWeekDay => {
            return this.#createWeekDayObjectFromIndex(isoWeekDay, weekNumber);
        });
    }

    #createWeekDayObjectFromIndex(isoWeekDay, weekNumber) {
        return {
            weekDayIndex: isoWeekDay,
            date: moment().week(weekNumber).isoWeekday(isoWeekDay)
        }
    }

    isSameDay(date1, date2) {
        const isSameYear = moment(date1).isSame(moment(date2), 'year');
        const isSameDay = moment(date1).isSame(moment(date2), 'day');
        return (isSameYear && isSameDay);
    }

    formatDateRelatively(date) {
        const today = new Date();

        if (date.getDate() === today.getDate() &&
            date.getMonth() === today.getMonth() &&
            date.getFullYear() === today.getFullYear()) {
            return "Vandaag";
        }
        else {
            const days = ["Zo", "Ma", "Di", "Wo", "Do", "Vr", "Za"];
            const months = ["Jan", "Feb", "Mrt", "Apr", "Mei", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"];
            const sameYear = date.getFullYear() === today.getFullYear();

            return days[date.getDay()] + ". " + date.getDate() + " " + months[date.getMonth()] + (sameYear ? "" : ", " + date.getFullYear());
        }
    }

    calculateTimeSpent(from, to) {
        let start = new Date(from);
        let end = new Date(to);

        // calculate time difference in hours and minutes
        let diff = end - start;
        let hours = Math.floor(diff / 1000 / 60 / 60);
        let minutes = Math.floor(diff / 1000 / 60) - (hours * 60);

        return [hours, minutes, hours + (minutes / 60)];
    }

    formatTimeSpent(timeSpent = [0, 0, 0], short = false) {
        if (short) return (timeSpent[2]).toFixed(2) + "h";
        return `${timeSpent[0]}h ${timeSpent[1]}m`;
    }



}