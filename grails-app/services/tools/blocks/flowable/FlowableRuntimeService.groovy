package tools.blocks.flowable

import grails.transaction.Transactional
import org.flowable.engine.RuntimeService
import org.flowable.engine.runtime.ProcessInstance

@Transactional
class FlowableRuntimeService {

    RuntimeService runtimeService

    //Activation methods

    //All params method
    ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey, Map<String, Object> variables, String tenantId) {
        if (tenantId && !tenantId.empty) {//versions  tenant
            if (businessKey && !businessKey.empty) {
                startProcessInstanceByKeyAndTenantId(processDefinitionKey, businessKey, variables, tenantId)
            } else {
                startProcessInstanceByKeyAndTenantId(processDefinitionKey, tenantId)
            }
        } else {
            if (businessKey && !businessKey.empty) {
                startProcessInstanceByKey(processDefinitionKey, businessKey, variables)
            } else {
                startProcessInstanceByKey(processDefinitionKey, variables)
            }
        }
    }

    ProcessInstance startProcessInstanceByKey(String processDefinitionKey) {
        runtimeService.startProcessInstanceByKey(processDefinitionKey)
    }

    ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey) {
        runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey)
    }

    ProcessInstance startProcessInstanceByKey(String processDefinitionKey, Map<String, Object> variables) {
        runtimeService.startProcessInstanceByKey(processDefinitionKey, variables)
    }

    ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey, Map<String, Object> variables) {
        runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables)
    }

    ProcessInstance startProcessInstanceByKeyAndTenantId(String processDefinitionKey, String tenantId) {
        runtimeService.startProcessInstanceByKeyAndTenantId(processDefinitionKey, tenantId)
    }

    ProcessInstance startProcessInstanceByKeyAndTenantId(String processDefinitionKey, String businessKey, String tenantId) {
        runtimeService.startProcessInstanceByKeyAndTenantId(processDefinitionKey, businessKey, tenantId)
    }

    ProcessInstance startProcessInstanceByKeyAndTenantId(String processDefinitionKey, Map<String, Object> variables, String tenantId) {
        runtimeService.startProcessInstanceByKeyAndTenantId(processDefinitionKey, variables, tenantId)
    }

    ProcessInstance startProcessInstanceByKeyAndTenantId(String processDefinitionKey, String businessKey, Map<String, Object> variables, String tenantId) {
        runtimeService.startProcessInstanceByKeyAndTenantId(processDefinitionKey, businessKey, variables, tenantId)
    }
}
