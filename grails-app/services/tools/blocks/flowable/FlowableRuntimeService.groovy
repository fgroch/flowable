package tools.blocks.flowable

import grails.transaction.Transactional
import org.flowable.bpmn.model.FlowNode
import org.flowable.engine.RuntimeService
import org.flowable.engine.common.api.FlowableException
import org.flowable.engine.common.api.FlowableIllegalArgumentException
import org.flowable.engine.common.api.FlowableObjectNotFoundException
import org.flowable.engine.common.api.delegate.event.FlowableEvent
import org.flowable.engine.common.api.delegate.event.FlowableEventListener
import org.flowable.engine.delegate.event.FlowableEngineEventType
import org.flowable.engine.impl.persistence.entity.VariableInstance
import org.flowable.engine.runtime.DataObject
import org.flowable.engine.runtime.Execution
import org.flowable.engine.runtime.ExecutionQuery
import org.flowable.engine.runtime.NativeExecutionQuery
import org.flowable.engine.runtime.NativeProcessInstanceQuery
import org.flowable.engine.runtime.ProcessInstance
import org.flowable.engine.runtime.ProcessInstanceBuilder
import org.flowable.engine.runtime.ProcessInstanceQuery
import org.flowable.engine.task.Event
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

    Map<String, VariableInstance> getVariableInstances(String executionId) {
        runtimeService.getVariableInstances(executionId)
    }

    List<VariableInstance> getVariableInstancesByExecutionIds(Set<String> executionIds) {
        runtimeService.getVariableInstancesByExecutionIds(executionIds)
    }

    Map<String, Object> getVariablesLocal(String executionId) {
        runtimeService.getVariablesLocal(executionId)
    }

    Map<String, VariableInstance> getVariableInstancesLocal(String executionId) {
        runtimeService.getVariableInstancesLocal(executionId)
    }

    Map<String, Object> getVariables(String executionId, Collection<String> variableNames) {
        runtimeService.getVariables(executionId, variableNames)
    }

    Map<String, VariableInstance> getVariableInstances(String executionId, Collection<String> variableNames) {
        runtimeService.getVariableInstances(executionId, variableNames)
    }

    Map<String, Object> getVariablesLocal(String executionId, Collection<String> variableNames) {
        runtimeService.getVariablesLocal(executionId, variableNames)
    }

    Map<String, VariableInstance> getVariableInstancesLocal(String executionId, Collection<String> variableNames) {
        runtimeService.getVariableInstancesLocal(executionId, variableNames)
    }

    def getVariable(String executionId, String variableName) {
        runtimeService.getVariable(executionId, variableName)
    }

    VariableInstance getVariableInstance(String executionId, String variableName) {
        runtimeService.getVariableInstance(executionId, variableName)
    }

    boolean hasVariable(String executionId, String variableName) {
        runtimeService.getVariable(executionId, variableName)
    }

    def getVariableLocal(String executionId, String variableName) {
        runtimeService.getVariableLocal(executionId, variableName)
    }

    VariableInstance getVariableInstanceLocal(String executionId, String variableName) {
        runtimeService.getVariableInstanceLocal(executionId, variableName)
    }

    boolean hasVariableLocal(String executionId, String variableName) {
        runtimeService.hasVariableLocal(executionId, variableName)
    }

    def setVariable(String executionId, String variableName, Object value) {
        runtimeService.setVariable(executionId, variableName, value)
    }

    def setVariableLocal(String executionId, String variableName, Object value) {
        runtimeService.setVariableLocal(executionId, variableName, value)
    }

    def removeVariable(String executionId, String variableName) {
        runtimeService.removeVariable(executionId, variableName)
    }

    def removeVariableLocal(String executionId, String variableName) {
        runtimeService.removeVariableLocal(executionId, variableName)
    }

    def removeVariables(String executionId, Collection<String> variableNames) {
        runtimeService.removeVariables(executionId, variableNames)
    }

    def removeVariablesLocal(String executionId, Collection<String> variableNames) {
        runtimeService.removeVariablesLocal(executionId, variableNames)
    }

    Map<String, DataObject> getDataObjects(String executionId) {
        runtimeService.getDataObjects(executionId)
    }

    Map<String, DataObject> getDataObjects(String executionId, String locale, boolean withLocalizationFallback) {
        runtimeService.getDataObjects(executionId, locale, withLocalizationFallback)
    }

    Map<String, DataObject> getDataObjectsLocal(String executionId) {
        runtimeService.getDataObjectsLocal(executionId)
    }

    Map<String, DataObject> getDataObjectsLocal(String executionId, String locale, boolean withLocalizationFallback) {
        runtimeService.getDataObjectsLocal(executionId, locale, withLocalizationFallback)
    }

    Map<String, DataObject> getDataObjects(String executionId, Collection<String> dataObjectNames) {
        runtimeService.getDataObjects(executionId, dataObjectNames)
    }

    Map<String, DataObject> getDataObjects(String executionId, Collection<String> dataObjectNames, String locale, boolean withLocalizationFallback) {
        runtimeService.getDataObjects(executionId, dataObjectNames, locale, withLocalizationFallback)
    }

    Map<String, DataObject> getDataObjectsLocal(String executionId, Collection<String> dataObjects) {
        runtimeService.getDataObjectsLocal(executionId, dataObjects)
    }

    Map<String, DataObject> getDataObjectsLocal(String executionId, Collection<String> dataObjectNames, String locale, boolean withLocalizationFallback) {
        runtimeService.getDataObjectsLocal(executionId, dataObjectNames, locale, withLocalizationFallback)
    }

    DataObject getDataObject(String executionId, String dataObject) {
        runtimeService.getDataObject(executionId, dataObject)
    }

    DataObject getDataObject(String executionId, String dataObjectName, String locale, boolean withLocalizationFallback) {
        runtimeService.getDataObject(executionId, dataObjectName, locale, withLocalizationFallback)
    }

    DataObject getDataObjectLocal(String executionId, String dataObjectName) {
        runtimeService.getDataObjectLocal(executionId, dataObjectName)
    }

    DataObject getDataObjectLocal(String executionId, String dataObjectName, String locale, boolean withLocalizationFallback) {
        runtimeService.getDataObjectLocal(executionId, dataObjectName, locale, withLocalizationFallback)
    }

    ExecutionQuery createExecutionQuery() {
        runtimeService.createExecutionQuery()
    }

    NativeExecutionQuery createNativeExecutionQuery() {
        runtimeService.createNativeExecutionQuery()
    }

    ProcessInstanceQuery createProcessInstanceQuery() {
        runtimeService.createProcessInstanceQuery()
    }

    NativeProcessInstanceQuery createNativeProcessInstanceQuery() {
        runtimeService.createNativeProcessInstanceQuery()
    }

    boolean suspendProcessInstanceById(String processInstanceId) {
        try {
            runtimeService.suspendProcessInstanceById(processInstanceId)
        } catch (FlowableObjectNotFoundException | FlowableException e) {
            return false
        }
        return true
    }

    boolean activateProcessInstanceById(String processInstanceId) {
        try {
            runtimeService.activateProcessInstanceById(processInstanceId)
        } catch (FlowableObjectNotFoundException | FlowableException e) {
            return false
        }
        return true
    }
    /*
    void signalEventReceived(String signalName);
    void signalEventReceivedWithTenantId(String signalName, String tenantId);
    void signalEventReceivedAsync(String signalName);
    void signalEventReceivedAsyncWithTenantId(String signalName, String tenantId);
    void signalEventReceived(String signalName, Map<String, Object> processVariables);
    void signalEventReceivedWithTenantId(String signalName, Map<String, Object> processVariables, String tenantId);
    void signalEventReceived(String signalName, String executionId);
    void signalEventReceived(String signalName, String executionId, Map<String, Object> processVariables);
    void signalEventReceivedAsync(String signalName, String executionId);
    void messageEventReceived(String messageName, String executionId);
    void messageEventReceived(String messageName, String executionId, Map<String, Object> processVariables);
    void messageEventReceivedAsync(String messageName, String executionId)
    */

    def addEventListener(FlowableEventListener listenerToAdd) {
        runtimeService.addEventListener(listenerToAdd)
    }

    def addEventListener(FlowableEventListener listenerToAdd, FlowableEngineEventType... types) {
        runtimeService.addEventListener(listenerToAdd, types)
    }

    def removeEventListener(FlowableEventListener listenerToRemove) {
        runtimeService.removeEventListener(listenerToRemove)
    }

    boolean dispatchEvent(FlowableEvent event) {
        try {
            runtimeService.dispatchEvent(event)
        } catch (FlowableIllegalArgumentException | FlowableException e) {
            return false
        }
        return true
    }

    boolean setProcessInstanceName(String processInstanceId, String name) {
         try {
             runtimeService.setProcessInstanceName(processInstanceId, name)
         } catch (FlowableObjectNotFoundException e) {
             return false
         }
        return true
    }

    List<Execution> getAdhocSubProcessExecutions(String processInstanceId) {
        runtimeService.getAdhocSubProcessExecutions(processInstanceId)
    }

    List<FlowNode> getEnabledActivitiesFromAdhocSubProcess(String executionId) {
        runtimeService.getEnabledActivitiesFromAdhocSubProcess(executionId)
    }

    Execution executeActivityInAdhocSubProcess(String executionId, String activityId) {
        runtimeService.executeActivityInAdhocSubProcess(executionId, activityId)
    }

    def completeAdhocSubProcess(String executionId) {
        runtimeService.completeAdhocSubProcess(executionId)
    }

    List<Event> getProcessInstanceEvents(String processInstanceId) {
        runtimeService.getProcessInstanceEvents(processInstanceId)
    }
}
