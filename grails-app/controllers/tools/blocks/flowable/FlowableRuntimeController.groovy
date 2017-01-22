package tools.blocks.flowable

import grails.transaction.Transactional
import org.flowable.engine.runtime.ProcessInstance
import static org.springframework.http.HttpStatus.NO_CONTENT

class FlowableRuntimeController {

    def flowableRuntimeService

    @Transactional
    def startProcessInstanceByKey() {
        if (params.processDefinitionKey) {
            ProcessInstance processInstance = flowableRuntimeService.startProcessInstanceByKey(params.processDefinitionKey, params.businessKey, params.variables, params.tenantId)
        } else if (params.processDefinitionId) {
            String outcome
            startProcessInstanceById(params.processDefinitionId, params.businessKey, outcome, params.variables, params.processInstanceName)
        } else {
            notFound()
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'flowable.deployment.not.found', default: 'Deployment not found')
                respond false, [status: NO_CONTENT]
            }
            '*'{ render status: NO_CONTENT, text: message(code: 'flowable.deployment.not.found', default: 'Deployment not found') }
        }
    }
}
