package tools.blocks.flowable

import grails.transaction.Transactional
import org.flowable.engine.FlowableTaskAlreadyClaimedException
import org.flowable.engine.TaskService
import org.flowable.engine.common.api.FlowableException
import org.flowable.engine.common.api.FlowableObjectNotFoundException
import org.flowable.engine.task.Task

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
}
