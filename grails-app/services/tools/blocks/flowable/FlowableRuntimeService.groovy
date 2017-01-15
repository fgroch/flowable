package tools.blocks.flowable

import grails.transaction.Transactional
import org.flowable.engine.RuntimeService
import org.flowable.engine.common.api.FlowableObjectNotFoundException
import org.flowable.engine.runtime.ProcessInstance
import org.flowable.engine.runtime.ProcessInstanceBuilder
import org.flowable.engine.task.IdentityLink
import org.flowable.form.model.FormModel

@Transactional
class FlowableRuntimeService {

    RuntimeService runtimeService

    ProcessInstanceBuilder createProcessInstanceBuilder() {
        runtimeService.createProcessInstanceBuilder()
    }

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

    //ByMessage methods

    ProcessInstance startProcessInstanceByMessage(String messageName) {
        runtimeService.startProcessInstanceByMessage(messageName)
    }

    ProcessInstance startProcessInstanceByMessageAndTenantId(String messageName, String tenantId) {
        runtimeService.startProcessInstanceByMessage(messageName, tenantId)
    }

    ProcessInstance startProcessInstanceByMessage(String messageName, String businessKey) {
        runtimeService.startProcessInstanceByMessage(messageName, businessKey)
    }

    ProcessInstance startProcessInstanceByMessage(String messageName, Map<String, Object> processVariables) {
        runtimeService.startProcessInstanceByMessage(messageName, processVariables)
    }

    ProcessInstance startProcessInstanceByMessageAndTenantId(String messageName, Map<String, Object> processVariables, String tenantId) {
        runtimeService.startProcessInstanceByKeyAndTenantId(messageName, processVariables, tenantId)
    }

    ProcessInstance startProcessInstanceByMessage(String messageName, String businessKey, Map<String, Object> processVariables) {
        runtimeService.startProcessInstanceByMessage(messageName, businessKey, processVariables)
    }

    ProcessInstance startProcessInstanceByMessageAndTenantId(String messageName, String businessKey, Map<String, Object> processVariables, String tenantId) {
        runtimeService.startProcessInstanceByKeyAndTenantId(messageName, businessKey, processVariables, tenantId)
    }

    //--- other methods ---//

    FormModel getStartFormModel(String processDefinitionId, String processInstanceId) {
        runtimeService.getStartFormModel(processDefinitionId, processInstanceId)
    }

    boolean deleteProcessInstance(String processInstanceId, String deleteReason) {
        try {
            runtimeService.deleteProcessInstance(processInstanceId, deleteReason)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    List<String> getActiveActivityIds(String executionId) {
        runtimeService.getActiveActivityIds(executionId)
    }

    boolean trigger(String executionId) {
        try {
            runtimeService.trigger(executionId)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean trigger(String executionId, Map<String, Object> processVariables) {
        try {
            runtimeService.trigger(executionId, processVariables)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean trigger(String executionId, Map<String, Object> processVariables, Map<String, Object> transientVariables) {
        try {
            runtimeService.trigger(executionId, processVariables, transientVariables)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    void updateBusinessKey(String processInstanceId, String businessKey) {
        runtimeService.updateBusinessKey(processInstanceId, businessKey)
    }

    boolean addUserIdentityLink(String processInstanceId, String userId, String identityLinkType) {
        try {
            runtimeService.addUserIdentityLink(processInstanceId, userId, identityLinkType)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean addGroupIdentityLink(String processInstanceId, String groupId, String identityLinkType) {
        try {
            runtimeService.addGroupIdentityLink(processInstanceId, groupId, identityLinkType)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean addParticipantUser(String processInstanceId, String userId) {
        try {
            runtimeService.addParticipantUser(processInstanceId, userId)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean addParticipantGroup(String processInstanceId, String groupId) {
        try {
            runtimeService.addParticipantGroup(processInstanceId, groupId)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean deleteParticipantUser(String processInstanceId, String userId) {
        try {
            runtimeService.deleteParticipantUser(processInstanceId, userId)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean deleteParticipantGroup(String processInstanceId, String groupId) {
        try {
            runtimeService.deleteParticipantGroup(processInstanceId, groupId)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean deleteUserIdentityLink(String processInstanceId, String userId, String identityLinkType) {
        try {
            runtimeService.deleteUserIdentityLink(processInstanceId, userId, identityLinkType)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean deleteGroupIdentityLink(String processInstanceId, String groupId, String identityLinkType) {
        try {
            runtimeService.deleteGroupIdentityLink(processInstanceId, groupId, identityLinkType)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    List<IdentityLink> getIdentityLinksForProcessInstance(String instanceId) {
        runtimeService.getIdentityLinksForProcessInstance(instanceId)
    }

    Map<String, Object> getVariables(String executionId) {
        runtimeService.getVariable(executionId)
    }
}
