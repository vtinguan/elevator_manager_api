package elevator_flow

import grails.test.mixin.Mock
import model.QueueManager
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

/**
 * Created by tinguan on 19/11/16.
 */
@Mock([PersonQueue, Person, Queue, ElevatorLog])
class QueueManagerSpec extends Specification {

    @Autowired
    QueueManager queueManager = new QueueManager()

    void 'test flow'(){
        setup:
        def map = [["name":"1", "date" : "8/31/2016 10:00:00", "floor" : "20"],
            ["name":"2", "date" : "8/31/2016 10:00:04", "floor" : "11"],
            ["name":"3", "date" : "8/31/2016 10:00:10", "floor" : "21"],
            ["name":"4", "date" : "8/31/2016 10:00:10", "floor" : "15"],
            ["name":"5", "date" : "8/31/2016 10:00:11", "floor" : "11"],
            ["name":"6", "date" : "8/31/2016 10:00:18", "floor" : "17"],
            ["name":"7", "date" : "8/31/2016 10:00:20", "floor" : "13"],
            ["name":"8", "date" : "8/31/2016 10:00:23", "floor" : "6"],
            ["name":"9", "date" : "8/31/2016 10:00:29", "floor" : "21"]]
        when:
        this.queueManager.createQueue(map)
        then:
        def personQueueList = PersonQueue.findAll()
        personQueueList.get(0).exit_date.toString() == "Wed Aug 31 10:02:06 BRT 2016"
        personQueueList.get(0).wait_time == 0
        personQueueList.get(7).exit_date.toString() == "Wed Aug 31 10:00:33 BRT 2016"
        personQueueList.get(7).wait_time == 0
    }
}
