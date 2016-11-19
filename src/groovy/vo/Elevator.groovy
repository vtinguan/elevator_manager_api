package vo
import elevator_flow.Person

/**
 * Created by tinguan on 18/11/16.
 */

class Elevator {

    public static final int SECOND_PER_FLOOR = 2
    public static final int DOOR_DELAY_SECOND = 20
    public static final int MAX_PEOPLE_PER_ELEVATOR = 8

    public int id
    public ArrayList<Person> persons = new ArrayList<>()
    public int state = 1
    public boolean isUsed = false

    public boolean isFull(Elevator elevator){
        return elevator.persons.size() >= MAX_PEOPLE_PER_ELEVATOR
    }

}
