package model

import elevator_flow.ElevatorLog
import elevator_flow.Person
import elevator_flow.PersonQueue
import elevator_flow.Queue
import groovy.time.TimeCategory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import vo.Elevator

import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * Created by tinguan on 18/11/16.
 */

@Scope("request")
@Component
class QueueManager {

    public List<Elevator> elevatorList = new ArrayList<>()

    public createQueue(List<HashMap> csvData) {
        Queue queue = new Queue(created: new Date()).save(failOnError: true)
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        csvData.each {
            Date date = format.parse(it['date'].toString())
            Person person = new Person(name: it['name'], floor: Integer.parseInt(it['floor'].toString()), created: date).save(failOnError: true)
            new PersonQueue(person: person, queue: queue).save(failOnError: true)
        }

        this.manageElevator(queue)
    }

    public manageElevator(Queue queue) {
        this.elevatorList.add(new Elevator(id: 1))
        this.elevatorList.add(new Elevator(id: 2))
        this.elevatorList.add(new Elevator(id: 3))
        this.elevatorList.add(new Elevator(id: 4))

        List<PersonQueue> personQueueList = PersonQueue.findAllByQueue(queue, [sort: "id", order: "asc"])

        personQueueList.each {
            if (it == personQueueList.last()) {
                this.addToElevator(it.person, true)
            }
            this.addToElevator(it.person, false)
        }


    }

    public addToElevator(Person person, boolean isLastPerson) {

        while (true){
            Elevator elevator = this.elevatorList.get(0)
            if (elevator.state == 1) {
                elevator.persons.add(person)
                if (elevator.isFull(elevator) || isLastPerson) {
                    elevator.persons = elevator.persons.sort{it.floor}
                    this.deliverPerson(elevator)
                    elevator.isUsed = true

                    this.elevatorList.remove(0)
                    this.elevatorList << elevator
                }
                break
            } else {
                this.elevatorList.remove(0)
                this.elevatorList << elevator
            }
        }

    }

    public deliverPerson(Elevator elevator) {
        int doorDelay = 0
        int delayElevatorWaitDuration = 0
        for (Person it : elevator.persons) {
            doorDelay += elevator.DOOR_DELAY_SECOND

            ElevatorLog lastElevatorLog = ElevatorLog.findByElevatorId(elevator.id, [sort: "id", order: "desc"])

            PersonQueue pq = PersonQueue.findByPerson(it)
            int seconds = (elevator.SECOND_PER_FLOOR * (it.floor - elevator.state).abs()) + (doorDelay - 20)
            delayElevatorWaitDuration = lastElevatorLog && elevator.state == 1 && elevator.isUsed ? (TimeCategory.minus(lastElevatorLog.created, it.created).minutes * 60) + TimeCategory.minus(lastElevatorLog.created, it.created).seconds : 0
            elevator.state = it.floor
            use(TimeCategory) {
                pq.exit_date = pq.person.created + seconds.seconds + delayElevatorWaitDuration.seconds
                pq.total_time = (TimeCategory.minus(pq.exit_date, pq.person.created).minutes * 60) + TimeCategory.minus(pq.exit_date, pq.person.created).seconds
                pq.transport_time = seconds
                pq.wait_time = delayElevatorWaitDuration
                pq.save(failOnError: true)
            }
            new ElevatorLog(floor: elevator.state, created: pq.exit_date, queue: pq.queue, elevatorId: elevator.id).save(failOnError: true)
        }

        PersonQueue pq = PersonQueue.findByPerson(elevator.persons.get(0), ["sort": "id", order: "desc"])
        elevator.persons = new ArrayList<>()
        ElevatorLog el = ElevatorLog.findByElevatorId(elevator.id, ["sort": "id", order: "desc"])
        int seconds = (elevator.SECOND_PER_FLOOR * elevator.state)
        elevator.state = 1
        use(TimeCategory) {
            new ElevatorLog(floor: elevator.state, created: el.created + seconds.seconds, queue: pq.queue, elevatorId: elevator.id).save(failOnError: true)
        }
    }
}