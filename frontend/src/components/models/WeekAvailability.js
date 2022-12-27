export class WeekAvailability {

    days = [];

    add(dayIndex, availability) {
        this.days.push({
            dayIndex: dayIndex,
            availability: availability
        });
    }

    getAvailabilities(dayIndex) {
        return this.days
            .filter(day => day.dayIndex === dayIndex)
            .map(day => day.availability);
    }

    clear() {
        this.days = [];
    }
}