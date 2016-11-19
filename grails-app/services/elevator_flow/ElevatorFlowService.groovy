package elevator_flow

import model.CSVExtractor
import model.QueueManager
import vo.PersonDataResponse
import vo.ResponseData

class ElevatorFlowService {

    private QueueManager queueManager = new QueueManager()
    private CSVExtractor csvExtractor = new CSVExtractor()

    def execute(String path) {
        List<HashMap> csvData = this.csvExtractor.extract(path)
        this.queueManager.createQueue(csvData)

        ResponseData rd = new ResponseData()

        def personQueueList = PersonQueue.findAll()
        personQueueList.each {
            PersonDataResponse pr = new PersonDataResponse(personName: it.person.name,
                    total_time_in_seconds: it.total_time,
                    wait_time_in_seconds: it.wait_time,
                    deliver_time_in_seconds: it.transport_time)
            rd.personDataResponses.add(pr)
        }
        return ["elevatorLog": rd]
    }
}
