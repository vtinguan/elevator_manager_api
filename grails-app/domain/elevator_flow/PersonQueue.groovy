package elevator_flow

class PersonQueue {
    Person person
    Queue queue
    int total_time
    int wait_time
    int transport_time
    Date exit_date

    static constraints = {
        person(nullable: false, blank: false)
        queue(nullable: false, blank: false)
        total_time(nullable: true, blank: true)
        wait_time(nullable: true, blank: true)
        transport_time(nullable: true, blank: true)
        exit_date(nullable: true, blank: true)
    }
}
