package tools.blocks.flowable

import grails.transaction.Transactional
import org.flowable.bpmn.model.BpmnModel
import org.flowable.dmn.api.DecisionTable
import org.flowable.engine.RepositoryService
import org.flowable.engine.app.AppModel
import org.flowable.engine.common.api.FlowableException
import org.flowable.engine.common.api.FlowableObjectNotFoundException
import org.flowable.engine.repository.Deployment
import org.flowable.engine.repository.DeploymentBuilder
import org.flowable.engine.repository.DiagramLayout
import org.flowable.engine.repository.Model
import org.flowable.engine.repository.ProcessDefinition
import org.flowable.engine.task.IdentityLink
import org.flowable.form.api.FormDefinition
import org.flowable.validation.ValidationError

import java.util.zip.ZipInputStream

@Transactional
class FlowableRepositoryService {

    RepositoryService repositoryService

    DeploymentBuilder createDeployment() {
        repositoryService.createDeployment()
    }

    def deploymentsCount() {
        repositoryService.createDeploymentQuery().count()
    }

    def getDeployments() {
        def deployments = repositoryService.createDeploymentQuery().listPage(0, 100)
        deployments
    }

    def getDeployments(int offset, int max) {
        def deployments = repositoryService.createDeploymentQuery().listPage(offset, max)
        deployments
    }

    def deploy(def file, String name, String category, String key) {
        if (!name) {
            name = file.filename ?: 'undefined name'
        }
        //ZipInputStream inputStream = new ZipInputStream(file.inputStream)
        Deployment deployment = repositoryService.createDeployment().name(name).addInputStream(file.filename, file.inputStream).deploy()
        if (category) {
            setDeploymentCategory(deployment.id ,category)
        }
        if (key) {
            setDeploymentKey(deployment.id, key)
        }
        deployment
    }

    def delete(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId)
    }

    def forceDelete(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true)
    }

    def setDeploymentCategory(String deploymentId, String category) {
        repositoryService.setDeploymentCategory(deploymentId, category)
    }

    def setDeploymentKey(String deploymentId, String key) {
        repositoryService.setDeploymentKey(deploymentId, key)
    }

    List<String> getDeploymentResourceNames(String deploymentId){
        repositoryService.getDeploymentResourceNames(deploymentId)
    }

    InputStream getResourceAsStream(String deploymentId, String resourceName) {
        repositoryService.getResourceAsStream(deploymentId, resourceName)
    }

    boolean suspendProcessDefinitionById(String processDefinitionId) {
        repositoryService.suspendProcessDefinitionById(processDefinitionId, false, null)
    }

    boolean suspendProcessDefinitionById(String processDefinitionId, boolean suspendProcessInstances, Date suspensionDate) {
        try {
            repositoryService.suspendProcessDefinitionById(processDefinitionId, suspendProcessInstances, suspensionDate)
        } catch(FlowableObjectNotFoundException | FlowableException e) {
            log.debug(e)
            return false
        }
        return true
    }

    boolean  suspendProcessDefinitionByKey(String processDefinitionKey) {
        suspendProcessDefinitionById(processDefinitionKey, false, null)
    }

    boolean suspendProcessDefinitionByKey(String processDefinitionKey, boolean suspendProcessInstances, Date suspensionDate) {
        try {
            repositoryService.suspendProcessDefinitionByKey(processDefinitionKey, suspendProcessInstances, suspensionDate)
        } catch(FlowableObjectNotFoundException | FlowableException e) {
            log.debug(e)
            return false
        }
        return true

    }

    boolean activateProcessDefinitionById(String processDefinitionId) {
        activateProcessDefinitionById(processDefinitionId, false, null)
    }

    boolean activateProcessDefinitionById(String processDefinitionId, boolean activateProcessInstances, Date activationDate) {
        try {
            repositoryService.activateProcessDefinitionById(processDefinitionId, activateProcessInstances, activationDate)
        } catch(FlowableObjectNotFoundException | FlowableException e) {
            log.debug(e)
            return false
        }
        return true
    }

    boolean activateProcessDefinitionByKey(String processDefinitionKey) {
        activateProcessDefinitionByKey(processDefinitionKey, false, null)
    }

    boolean activateProcessDefinitionByKey(String processDefinitionKey, boolean activateProcessInstances, Date activationDate) {
        try {
            repositoryService.activateProcessDefinitionByKey(processDefinitionKey, activateProcessInstances, activationDate)
        } catch(FlowableObjectNotFoundException | FlowableException e) {
            log.debug(e)
            return false
        }
        return true
    }

    boolean activateProcessDefinitionByKey(String processDefinitionKey, String tenantId) {
        activateProcessDefinitionByKey(processDefinitionKey, false, null, tenantId)
    }

    boolean activateProcessDefinitionByKey(String processDefinitionKey, boolean activateProcessInstances, Date activationDate, String tenantId) {
        try {
            repositoryService.activateProcessDefinitionByKey(processDefinitionKey, activateProcessInstances, activationDate, tenantId)
        } catch(FlowableObjectNotFoundException | FlowableException e) {
            log.debug(e)
            return false
        }
        return true
    }

    InputStream getProcessModel(String processDefinitionId) {
        repositoryService.getProcessModel(processDefinitionId)
    }

    InputStream getProcessDiagramResource(String processDefinitionKey) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .singleResult();
        String diagramResourceName = processDefinition.getDiagramResourceName();
        InputStream imageStream = repositoryService.getResourceAsStream(
                processDefinition.getDeploymentId(), diagramResourceName);
        imageStream
        //repositoryService.getProcessDiagram(processDefinitionId)
    }

    InputStream getProcessDiagram(String processDefinitionId) {
        repositoryService.getProcessDiagram(processDefinitionId)
    }

    ProcessDefinition getProcessDefinition(String processDefinitionId) {
        repositoryService.getProcessDefinition(processDefinitionId)
    }

    boolean isProcessDefinitionSuspended(String processDefinitionId) {
        repositoryService.isProcessDefinitionSuspended(processDefinitionId)
    }

    BpmnModel getBpmnModel(String processDefinitionId) {
        repositoryService.getBpmnModel(processDefinitionId)
    }

    DiagramLayout getProcessDiagramLayout(String processDefinitionId) {
        repositoryService.getProcessDiagramLayout(processDefinitionId)
    }

    Object getAppResourceObject(String deploymentId) {
        repositoryService.getAppResourceObject(deploymentId)
    }

    AppModel getAppResourceModel(String deploymentId) {
        repositoryService.getAppResourceModel(deploymentId)
    }

    Model getModel(String modelId) {
        repositoryService.getModel(modelId)
    }

    byte[] getModelEditorSource(String modelId) {
        repositoryService.getModelEditorSource(modelId)
    }

    byte[] getModelEditorSourceExtra(String modelId) {
        repositoryService.getModelEditorSourceExtra(modelId)
    }

    boolean addCandidateStarterUser(String processDefinitionId, String userId) {
        try {
            repositoryService.addCandidateStarterUser(processDefinitionId, userId)
        } catch (FlowableObjectNotFoundException e) {
            log.debug(e)
            return false
        }
        return true
    }

    boolean addCandidateStarterGroup(String processDefinitionId, String groupId) {
        try {
            repositoryService.addCandidateStarterGroup(processDefinitionId, groupId)
        } catch (FlowableObjectNotFoundException e) {
            log.debug(e)
            return false
        }
        return true
    }

    boolean deleteCandidateStarterUser(String processDefinitionId, String userId) {
        try {
            repositoryService.deleteCandidateStarterUser(processDefinitionId, userId)
        } catch (FlowableObjectNotFoundException e) {
            log.debug(e)
            return false
        }
        return true
    }

    boolean deleteCandidateStarterGroup(String processDefinitionId, String groupId) {
        try {
            repositoryService.deleteCandidateStarterGroup(processDefinitionId, groupId)
        } catch (FlowableObjectNotFoundException e) {
            log.debug(e)
            return false
        }
        return true
    }

    List<IdentityLink> getIdentityLinksForProcessDefinition(String processDefinitionId) {
        repositoryService.getIdentityLinksForProcessDefinition(processDefinitionId)
    }

    List<ValidationError> validateProcess(BpmnModel bpmnModel) {
        repositoryService.validateProcess(bpmnModel)
    }

    List<DecisionTable> getDecisionTablesForProcessDefinition(String processDefinitionId) {
        repositoryService.getDecisionTablesForProcessDefinition(processDefinitionId)
    }

    List<FormDefinition> getFormDefinitionsForProcessDefinition(String processDefinitionId) {
        repositoryService.getFormDefinitionsForProcessDefinition(processDefinitionId)
    }
}
