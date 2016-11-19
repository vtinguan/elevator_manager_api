package elevator_flow

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONObject

class ElevatorController {

    def elevatorFlowService

    def execute() {
        JSONObject param = request.JSON as JSONObject
        String path = param.path
        def response = elevatorFlowService.execute(path)
        render response as JSON
    }
}
