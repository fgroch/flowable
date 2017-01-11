package tools.blocks.flowable

import grails.transaction.Transactional
import org.flowable.engine.FlowableTaskAlreadyClaimedException
import org.flowable.engine.TaskService
import org.flowable.engine.common.api.FlowableException
import org.flowable.engine.common.api.FlowableObjectNotFoundException
import org.flowable.engine.impl.persistence.entity.VariableInstance
import org.flowable.engine.task.IdentityLink
import org.flowable.engine.task.Task
import org.flowable.form.model.FormModel

@Transactional
class FlowableTaskService {

    TaskService taskService

    Task newTask() {
        taskService.newTask()
    }

    Task newTask(String taskId) {
        taskService.newTask(taskId)
    }

    def saveTask(Task task) {
        taskService.saveTask()
    }

    boolean deleteTask(String taskId) {
        try {
            taskService.deleteTask()
        } catch (FlowableObjectNotFoundException | FlowableException e) {
            return false
        }
        return true
    }

    boolean deleteTasks(Collection<String> taskIds) {
        try {
            taskService.deleteTasks(taskIds)
        } catch (FlowableObjectNotFoundException | FlowableException e) {
            return false
        }
        return true
    }

    boolean deleteTask(String taskId, boolean cascade) {
        try {
            taskService.deleteTask(taskId, cascade)
        } catch (FlowableObjectNotFoundException | FlowableException e) {
            return false
        }
        return true
    }

    boolean deleteTasks(Collection<String> taskIds, boolean cascade) {
        try {
            taskService.deleteTasks(taskIds, cascade)
        } catch (FlowableObjectNotFoundException | FlowableException e) {
            return false
        }
        return true
    }

    boolean deleteTask(String taskId, String deleteReason) {
        try {
            taskService.deleteTask(taskId, deleteReason)
        } catch (FlowableObjectNotFoundException | FlowableException e) {
            return false
        }
        return true
    }

    boolean deleteTasks(Collection<String> taskIds, String deleteReason) {
        try {
            taskService.deleteTasks(taskIds, deleteReason)
        } catch (FlowableObjectNotFoundException | FlowableException e) {
            return false
        }
        return true
    }

    boolean claim(String taskId, String userId) {
        try {
            taskService.claim(taskId, userId)
        } catch (FlowableException | FlowableTaskAlreadyClaimedException e) {
            return false
        }
        return true
    }

    boolean claim(String taskId) {
        try {
            taskService.unclaim(taskId)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean complete(String taskId) {
        complete(taskId, null)
    }

    boolean delegateTask(String taskId, String userId) {
        try {
            taskService.delegateTask(taskId, userId)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean resolveTask(String taskId) {
        try {
            taskService.resolveTask(taskId)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean resolveTask(String taskId, Map<String, Object> variables) {
        try {
            taskService.resolveTask(taskId, variables)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean resolveTask(String taskId, Map<String, Object> variables, Map<String, Object> transientVariables) {
        try {
            taskService.resolveTask(taskId, variables, transientVariables)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean complete(String taskId, Map<String, Object> variables) {
        try {
            taskService.complete(taskId, variables)
        } catch (FlowableObjectNotFoundException | FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean complete(String taskId, Map<String, Object> variables, Map<String, Object> transientVariables) {
        try {
            taskService.complete(taskId, variables, transientVariables)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean complete(String taskId, Map<String, Object> variables, boolean localScope) {
        try {
            taskService.complete(taskId, variables, localScope)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    def completeTaskWithForm(String taskId, String formDefinitionId, String outcome, Map<String, Object> variables) {
        taskService.completeTaskWithForm(taskId, formDefinitionId, outcome, variables)
    }

    def completeTaskWithForm(String taskId, String formDefinitionId, String outcome,
                             Map<String, Object> variables, Map<String, Object> transientVariables) {
        taskService.completeTaskWithForm(taskId, formDefinitionId, outcome, variables, transientVariables)
    }

    def completeTaskWithForm(String taskId, String formDefinitionId, String outcome,
                             Map<String, Object> variables, boolean localScope) {
        taskService.completeTaskWithForm(taskId, formDefinitionId, outcome, variables, localScope)
    }

    FormModel getTaskFormModel(String taskId) {
        taskService.getTaskFormModel(taskId)
    }

    boolean setAssignee(String taskId, String userId) {
        try {
            taskService.setAssignee(taskId, userId)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean setOwner(String taskId, String userId) {
        try {
            taskService.setOwner(taskId, userId)
        } catch(FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    List<IdentityLink> getIdentityLinksForTask(String taskId) {
        taskService.getIdentityLinksForTask(taskId)
    }

    boolean addCandidateUser(String taskId, String userId) {
        try {
            taskService.addCandidateUser(taskId, userId)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean addCandidateGroup(String taskId, String groupId) {
        try {
            taskService.addCandidateGroup(taskId, groupId)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean addUserIdentityLink(String taskId, String userId, String identityLinkType) {
        try {
            taskService.addUserIdentityLink(taskId, userId, identityLinkType)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean addGroupIdentityLink(String taskId, String groupId, String identityLinkType) {
        try {
            taskService.addGroupIdentityLink(taskId, groupId, identityLinkType)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean deleteCandidateUser(String taskId, String userId) {
        try {
            taskService.deleteCandidateUser(taskId, userId)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean deleteCandidateGroup(String taskId, String groupId) {
        try{
            taskService.deleteCandidateGroup(taskId, groupId)
        } catch(FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean deleteUserIdentityLink(String taskId, String userId, String identityLinkType) {
        try {
            taskService.deleteUserIdentityLink(taskId, userId, identityLinkType)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean deleteGroupIdentityLink(String taskId, String groupId, String identityLinkType) {
        try {
            taskService.deleteGroupIdentityLink(taskId, groupId, identityLinkType)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean setPriority(String taskId, int priority) {
        try {
            taskService.setPriority(taskId, priority)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    boolean setDueDate(String taskId, Date dueDate) {
        try {
            taskService.setDueDate(taskId, dueDate)
        } catch (FlowableException e) {
            return false
        }
        return true
    }

    def setVariable(String taskId, String variableName, Object value) {
        taskService.setVariable(taskId, variableName, value)
    }

    def setVariables(String taskId, Map<String, ? extends Object> variables) {
        taskService.setVariables(taskId, variables)
    }

    def setVariableLocal(String taskId, String variableName, Object value) {
        taskService.setVariableLocal(taskId, variableName, value)
    }

    def setVariablesLocal(String taskId, Map<String, ? extends Object> variables) {
        taskService.setVariablesLocal(taskId, variables)
    }

    def getVariable(String taskId, String variableName) {
        taskService.getVariable(taskId, variableName)
    }

    VariableInstance getVariableInstance(String taskId, String variableName) {
        taskService.getVariableInstance(taskId, variableName)\
    }

    boolean hasVariable(String taskId, String variableName) {
        taskService.hasVariable(taskId, variableName)
    }

    def getVariableLocal(String taskId, String variableName) {
        taskService.getVariableLocal(taskId, variableName)
    }

    VariableInstance getVariableInstanceLocal(String taskId, String variableName) {
        taskService.getVariableInstanceLocal(taskId, variableName)
    }

    boolean hasVariableLocal(String taskId, String variableName) {
        taskService.hasVariableLocal(taskId, variableName)
    }

    //returns Map<String, Object>
    def getVariables(String taskId) {
        taskService.getVariables(taskId)
    }

    //returns Map<String, VariableInstance>
    def getVariableInstances(String taskId) {
        taskService.getVariableInstances(taskId)
    }

    //Map<String, VariableInstance>
    def getVariableInstances(String taskId, Collection<String> variableNames) {
        taskService.getVariableInstances(taskId, variableNames)
    }

    //Map<String, Object>
    def getVariablesLocal(String taskId) {
        taskService.getVariablesLocal(taskId)
    }

    //Map<String, Object>
    def getVariables(String taskId, Collection<String> variableNames) {
        taskService.getVariables(taskId, variableNames)
    }

    //Map<String, Object>
    def getVariablesLocal(String taskId, Collection<String> variableNames) {
        taskService.getVariablesLocal(taskId, variableNames)
    }

    //List<VariableInstance>
    def getVariableInstancesLocalByTaskIds(Set<String> taskIds) {
        taskService.getVariableInstancesLocalByTaskIds(taskIds)
    }

    //Map<String, VariableInstance>
    def getVariableInstancesLocal(String taskId) {
        taskService.getVariableInstancesLocal(taskId)
    }

    //Map<String, VariableInstance>
    def getVariableInstancesLocal(String taskId, Collection<String> variableNames) {
        taskService.getVariableInstancesLocal(taskId, variableNames)
    }


}
