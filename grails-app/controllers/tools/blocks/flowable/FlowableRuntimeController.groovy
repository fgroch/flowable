package tools.blocks.flowable

import grails.transaction.Transactional
import org.flowable.engine.runtime.ProcessInstance
import static org.springframework.http.HttpStatus.NO_CONTENT

class FlowableRuntimeController {

    def flowableRuntimeService

    @Transactional
    def startProcessInstance() {
        ProcessInstance processInstance
        if (params.processDefinitionKey) {
            processInstance = flowableRuntimeService.startProcessInstanceByKey(params.processDefinitionKey, params.businessKey, params.variables, params.tenantId)
        } else if (params.processDefinitionId) {
            String outcome
            processInstance = startProcessInstanceById(params.processDefinitionId, params.businessKey, outcome, params.variables, params.processInstanceName)
        } else {
            notFound()
        }
        if (!processInstance) {
            notStart()
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

    protected void notStart() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'flowable.process.not.start', default: 'Process cannot be started')
                respond false, [status: NO_CONTENT]
            }
            '*'{ render status: NO_CONTENT, text: message(code: 'flowable.process.not.start', default: 'Process cannot be started') }
        }
    }
}
