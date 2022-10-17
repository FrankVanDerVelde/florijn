class LoggedHour {

    #id;
    #participant;
    #startTime;
    #endTime;
    #hourlyRate;
    #status;

    constructor(id, participant, startTime, endTime, hourlyRate, status) {
        this.#id = id;
        this.#participant = participant;
        this.#startTime = startTime;
        this.#endTime = endTime;
        this.#hourlyRate = hourlyRate;
        this.#status = status;
    }

    get id() {
        return this.#id;
    }





}