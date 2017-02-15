package tools.blocks.flowable

import grails.transaction.Transactional
import org.flowable.engine.FormService
import org.flowable.engine.form.StartFormData
import org.flowable.engine.form.TaskFormData
import org.flowable.engine.runtime.ProcessInstance

@Transactional
class FlowableFormPropertyService {

    FormService formPropertyService

    StartFormData getStartFormData(String processDefinitionId) {
        formPropertyService.getStartFormData(processDefinitionId)
    }

    def getRenderedStartForm(String processDefinitionId) {
        formPropertyService.getRenderedStartForm(processDefinitionId)
    }

    def getRenderedStartForm(String processDefinitionId, String formEngineName) {
        formPropertyService.getRenderedStartForm(processDefinitionId, formEngineName)
    }

    ProcessInstance submitStartFormData(String processDefinitionId, Map<String, String> properties) {
        formPropertyService.submitStartFormData(processDefinitionId, properties)
    }

    ProcessInstance submitStartFormData(String processDefinitionId, String businessKey, Map<String, String> properties) {
        formPropertyService.submitStartFormData(processDefinitionId, businessKey, properties)
    }

    TaskFormData getTaskFormData(String taskId) {
        formPropertyService.getTaskFormData(taskId)
    }

    def getRenderedTaskForm(String taskId) {
        formPropertyService.getRenderedTaskForm(taskId)
    }

    def getRenderedTaskForm(String taskId, String formEngineName) {
        formPropertyService.getRenderedTaskForm(taskId, formEngineName)
    }

    def submitTaskFormData(String taskId, Map<String, String> properties) {
        formPropertyService.submitTaskFormData(taskId, properties)
    }

    def saveFormData(String taskId, Map<String, String> properties) {
        formPropertyService.saveFormData(taskId, properties)
    }

    String getStartFormKey(String processDefinitionId) {
        formPropertyService.getStartFormKey(processDefinitionId)
    }

    String getTaskFormKey(String processDefinitionId, String taskDefinitionKey) {
        formPropertyService.getTaskFormKey(processDefinitionId, taskDefinitionKey)
    }
}
