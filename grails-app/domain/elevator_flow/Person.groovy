package elevator_flow

class Person {
    String name
    Date created
    int floor
    static constraints = {
        name(nullable: false, blank: false)
        created(nullable: false, blank: false)
        floor(nullable: false, blank: false)
    }
}
