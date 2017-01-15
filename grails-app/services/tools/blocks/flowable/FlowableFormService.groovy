package tools.blocks.flowable

import grails.transaction.Transactional
import org.flowable.engine.FormService
import org.flowable.engine.form.StartFormData
import org.flowable.engine.form.TaskFormData
import org.flowable.engine.runtime.ProcessInstance

@Transactional
class FlowableFormService {

    FormService formService

    StartFormData getStartFormData(String processDefinitionId) {
        formService.getStartFormData(processDefinitionId)
    }

    def getRenderedStartForm(String processDefinitionId) {
        formService.getRenderedStartForm(processDefinitionId)
    }

    def getRenderedStartForm(String processDefinitionId, String formEngineName) {
        formService.getRenderedStartForm(processDefinitionId, formEngineName)
    }

    ProcessInstance submitStartFormData(String processDefinitionId, Map<String, String> properties) {
        formService.submitStartFormData(processDefinitionId, properties)
    }

    ProcessInstance submitStartFormData(String processDefinitionId, String businessKey, Map<String, String> properties) {
        formService.submitStartFormData(processDefinitionId, businessKey, properties)
    }

    TaskFormData getTaskFormData(String taskId) {
        formService.getTaskFormData(taskId)
    }

    def getRenderedTaskForm(String taskId) {
        formService.getRenderedTaskForm(taskId)
    }

    def getRenderedTaskForm(String taskId, String formEngineName) {
        formService.getRenderedTaskForm(taskId, formEngineName)
    }

    def submitTaskFormData(String taskId, Map<String, String> properties) {
        formService.submitTaskFormData(taskId, properties)
    }

    def saveFormData(String taskId, Map<String, String> properties) {
        formService.saveFormData(taskId, properties)
    }

    String getStartFormKey(String processDefinitionId) {
        formService.getStartFormKey(processDefinitionId)
    }

    String getTaskFormKey(String processDefinitionId, String taskDefinitionKey) {
        formService.getTaskFormKey(processDefinitionId, taskDefinitionKey)
    }
}
