package tools.blocks

import grails.transaction.Transactional
import org.flowable.engine.RepositoryService

@Transactional
class FlowableRepositoryService {

    RepositoryService repositoryService

    def deploymentsCount() {
        repositoryService.createDeploymentQuery().count()
    }
}
