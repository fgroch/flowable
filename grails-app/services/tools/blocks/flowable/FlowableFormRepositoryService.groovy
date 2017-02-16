package tools.blocks.flowable

import grails.transaction.Transactional
import org.flowable.form.api.FormDefinition
import org.flowable.form.api.FormDefinitionQuery
import org.flowable.form.api.FormDeploymentBuilder
import org.flowable.form.api.FormDeploymentQuery
import org.flowable.form.api.FormRepositoryService
import org.flowable.form.api.NativeFormDefinitionQuery
import org.flowable.form.api.NativeFormDeploymentQuery
import org.flowable.form.model.FormModel

@Transactional
class FlowableFormRepositoryService {

    FormRepositoryService formRepositoryService

    FormDeploymentBuilder createDeployment() {
        formRepositoryService.createDeployment()
    }

    def deleteDeployment(String deploymentId) {
        formRepositoryService.deleteDeployment(deploymentId)
    }

    FormDefinitionQuery createFormDefinitionQuery() {
        formRepositoryService.createFormDefinitionQuery()
    }

    NativeFormDefinitionQuery createNativeFormDefinitionQuery() {
        formRepositoryService.createNativeFormDefinitionQuery()
    }

    def setDeploymentCategory(String deploymentId, String category) {
        formRepositoryService.setDeploymentCategory(deploymentId, category)
    }

    def setDeploymentTenantId(String deploymentId, String newTenantId) {
        formRepositoryService.setDeploymentTenantId(deploymentId, newTenantId)
    }

    List<String> getDeploymentResourceNames(String deploymentId) {
        formRepositoryService.getDeploymentResourceNames(deploymentId)
    }

    InputStream getResourceAsStream(String deploymentId, String resourceName) {
        formRepositoryService.getResourceAsStream(deploymentId, resourceName)
    }

    FormDeploymentQuery createDeploymentQuery() {
        formRepositoryService.createDeploymentQuery()
    }

    NativeFormDeploymentQuery createNativeDeploymentQuery() {
        formRepositoryService.createNativeDeploymentQuery()
    }

    FormDefinition getFormDefinition(String formDefinitionId) {
        formRepositoryService.getFormDefinition(formDefinitionId)
    }

    FormModel getFormModelById(String formDefinitionId) {
        formRepositoryService.getFormModelById(formDefinitionId)
    }

    FormModel getFormModelByKey(String formDefinitionKey) {
        formRepositoryService.getFormModelByKey(formDefinitionKey)
    }

    FormModel getFormModelByKey(String formDefinitionKey, String tenantId) {
        formRepositoryService.getFormModelByKey(formDefinitionKey, tenantId)
    }

    FormModel getFormModelByKeyAndParentDeploymentId(String formDefinitionKey, String parentDeploymentId) {
        formRepositoryService.getFormModelByKeyAndParentDeploymentId(formDefinitionKey, parentDeploymentId)
    }

    FormModel getFormModelByKeyAndParentDeploymentId(String formDefinitionKey, String parentDeploymentId, String tenantId) {
        formRepositoryService.getFormModelByKeyAndParentDeploymentId(formDefinitionKey, parentDeploymentId, tenantId)
    }

    InputStream getFormDefinitionResource(String formDefinitionId) {
        formRepositoryService.getFormDefinitionResource(formDefinitionId)
    }

    def setFormDefinitionCategory(String formDefinitionId, String category) {
        formRepositoryService.setFormDefinitionCategory(formDefinitionId, category)
    }
}
