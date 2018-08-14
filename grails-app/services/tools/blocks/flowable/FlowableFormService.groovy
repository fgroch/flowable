package tools.blocks.flowable

import grails.transaction.Transactional
import org.flowable.form.api.FormInstance
import org.flowable.form.api.FormInstanceInfo
import org.flowable.form.api.FormInstanceQuery
import org.flowable.form.api.FormModel
import org.flowable.form.api.FormService

@Transactional
class FlowableFormService {

    FormService formService

    FormInstanceQuery createFormInstanceQuery() {
        formService.createFormInstanceQuery()
    }

    Map<String, Object> getVariablesFromFormSubmission(FormModel formModel, Map<String, Object> values, String outcome) {
        formService.getVariablesFromFormSubmission(formModel, values, outcome)
    }

    FormInstance createFormInstance(Map<String, Object> values, FormModel formModel, String taskId, String processInstanceId) {
        formService.createFormInstance(values, formModel, taskId, processInstanceId)
    }

    FormModel getFormModelWithVariablesById(String formDefinitionId, String processInstanceId, Map<String, Object> variables) {
        formService.getFormModelWithVariablesById(formDefinitionId, processInstanceId, variables)
    }

    FormModel getFormModelWithVariablesById(String formDefinitionId, String processInstanceId, Map<String, Object> variables, String tenantId) {
        formService.getFormModelWithVariablesById(formDefinitionId, processInstanceId, variables, tenantId)
    }

    FormModel getFormModelWithVariablesByKey(String formDefinitionKey, String processInstanceId, Map<String, Object> variables) {
        formService.getFormModelWithVariablesByKey(formDefinitionKey, processInstanceId, variables)
    }

    FormModel getFormModelWithVariablesByKey(String formDefinitionKey, String processInstanceId, Map<String, Object> variables, String tenantId) {
        formService.getFormModelWithVariablesByKey(formDefinitionKey, processInstanceId, variables, tenantId)
    }

    FormModel getFormModelWithVariablesByKeyAndParentDeploymentId(String formDefinitionKey, String parentDeploymentId,
                                                                  String processInstanceId, Map<String, Object> variables) {
        formService.getFormModelWithVariablesByKeyAndParentDeploymentId(formDefinitionKey, parentDeploymentId, processInstanceId, variables)
    }

    FormModel getFormModelWithVariablesByKeyAndParentDeploymentId(String formDefinitionKey, String parentDeploymentId, String processInstanceId,
                                                                  Map<String, Object> variables, String tenantId) {
        formService.getFormModelWithVariablesByKeyAndParentDeploymentId(formDefinitionKey, parentDeploymentId, processInstanceId, variables, tenantId)
    }

    FormInstanceInfo getFormInstanceModelById(String formInstanceId, Map<String, Object> variables) {
        formService.getFormInstanceModelById(formInstanceId, variables)
    }

    FormInstanceInfo getFormInstanceModelById(String formDefinitionId, String taskId, String processInstanceId, Map<String, Object> variables) {
        formService.getFormInstanceModelById(formDefinitionId, taskId, processInstanceId, variables)
    }

    FormInstanceInfo getFormInstanceModelById(String formDefinitionId, String taskId, String processInstanceId,
                                               Map<String, Object> variables, String tenantId) {
        formService.getFormInstanceModelById(formDefinitionId, taskId, processInstanceId, variables, tenantId)
    }

    FormInstanceInfo getFormInstanceModelByKey(String formDefinitionKey, String taskId, String processInstanceId, Map<String, Object> variables) {
        formService.getFormInstanceModelByKey(formDefinitionKey, taskId, processInstanceId, variables)
    }

    FormInstanceInfo getFormInstanceModelByKey(String formDefinitionKey, String taskId, String processInstanceId,
                                                Map<String, Object> variables, String tenantId) {
        formService.getFormInstanceModelByKey(formDefinitionKey, taskId, processInstanceId, variables, tenantId)
    }

    FormInstanceInfo getFormInstanceModelByKeyAndParentDeploymentId(String formDefinitionKey, String parentDeploymentId,
                                                                     String taskId, String processInstanceId, Map<String, Object> variables) {
        formService.getFormInstanceModelByKeyAndParentDeploymentId(formDefinitionKey, parentDeploymentId, taskId,
                processInstanceId, variables)
    }

    FormInstanceInfo getFormInstanceModelByKeyAndParentDeploymentId(String formDefinitionKey, String parentDeploymentId,
                                                                     String taskId, String processInstanceId, Map<String, Object> variables, String tenantId) {
        formService.getFormInstanceModelByKeyAndParentDeploymentId(formDefinitionKey, parentDeploymentId, taskId,
                processInstanceId, variables, tenantId)
    }
}
