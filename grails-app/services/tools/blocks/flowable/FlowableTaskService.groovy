package tools.blocks.flowable

import grails.transaction.Transactional
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
}
