package tools.blocks.flowable

import grails.transaction.Transactional
import org.flowable.engine.RuntimeService
import org.flowable.engine.runtime.ProcessInstance
import org.flowable.form.model.FormModel

@Transactional
class FlowableRuntimeService {

    RuntimeService runtimeService

    //Activation methods

    //...ByKey methods

    //All params method
    ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey, Map<String, Object> variables, String tenantId) {
        if (tenantId && !tenantId.empty) {//versions  tenant
            if (businessKey && !businessKey.empty) {
                startProcessInstanceByKeyAndTenantId(processDefinitionKey, businessKey, variables, tenantId)
            } else {
                startProcessInstanceByKeyAndTenantId(processDefinitionKey, variables, tenantId)
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

    //...ById methods

    ProcessInstance startProcessInstanceById(String processDefinitionId) {
        runtimeService.startProcessInstanceById(processDefinitionId)
    }

    ProcessInstance startProcessInstanceById(String processDefinitionId, String businessKey) {
        runtimeService.startProcessInstanceById(processDefinitionId, businessKey)
    }

    ProcessInstance startProcessInstanceById(String processDefinitionId, Map<String, Object> variables) {
        runtimeService.startProcessInstanceById(processDefinitionId, variables)
    }

    ProcessInstance startProcessInstanceById(String processDefinitionId, String businessKey, Map<String, Object> variables) {
        runtimeService.startProcessInstanceById(processDefinitionId, businessKey, variables)
    }

    ProcessInstance startProcessInstanceWithForm(String processDefinitionId, String outcome, Map<String, Object> variables, String processInstanceName) {
        runtimeService.startProcessInstanceWithForm(processDefinitionId, outcome, variables, processInstanceName)
    }

    //--- other methods ---//

    FormModel getStartFormModel(String processDefinitionId, String processInstanceId) {
        runtimeService.getStartFormModel(processDefinitionId, processInstanceId)
    }

    void deleteProcessInstance(String processInstanceId, String deleteReason) {
        runtimeService.deleteProcessInstance(processInstanceId, deleteReason)
    }

    void updateBusinessKey(String processInstanceId, String businessKey) {
        runtimeService.updateBusinessKey(processInstanceId, businessKey)
    }

    Map<String, Object> getVariables(String executionId) {
        runtimeService.getVariable(executionId)
    }
}
