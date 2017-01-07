package tools.blocks

import grails.transaction.Transactional
import org.flowable.engine.RepositoryService
import org.flowable.engine.common.api.FlowableException
import org.flowable.engine.common.api.FlowableObjectNotFoundException
import org.flowable.engine.repository.Deployment

import java.util.zip.ZipInputStream

@Transactional
class FlowableRepositoryService {

    RepositoryService repositoryService

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
        ZipInputStream inputStream = new ZipInputStream(file.inputStream)
        Deployment deployment = repositoryService.createDeployment().name(name).addZipInputStream(inputStream).deploy()
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
}
