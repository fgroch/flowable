package tools.blocks

import grails.transaction.Transactional
import org.flowable.engine.RepositoryService
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

    def deploy(def file, String name) {
        if (!name) {
            name = file.filename ?: 'undefined name'
        }
        ZipInputStream inputStream = new ZipInputStream(file.inputStream)
        Deployment deployment = repositoryService.createDeployment().name(name).addZipInputStream(inputStream).deploy()
        deployment
    }

    def delete(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId)
    }

    def delete(String deploymentId, boolean cascade) {
        repositoryService.deleteDeployment(deploymentId, cascade)
    }
}
