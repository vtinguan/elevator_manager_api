package elevator_flow

class ElevatorLog {
    int floor
    Date created
    Queue queue
    int elevatorId

    static constraints = {
        floor(nullable: false, blank: false)
        created(nullable: false, blank: false)
        queue(nullable: false, blank: false)
        elevatorId(nullable: false, blank: false)
    }
}
