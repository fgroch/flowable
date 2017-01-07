package tools.blocks.flowable

import grails.transaction.Transactional
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest

import static org.springframework.http.HttpStatus.NO_CONTENT

class FlowableRepositoryController {

    def flowableRepositoryService

    def deploymentsCount() {
        flowableRepositoryService.deploymentsCount()
    }

    def getDeployments() {
        params.offset = params.offset ?: 0
        params.max = Math.min(max ?: 10, 100)
        flowableRepositoryService.getDeployments(params.offset, params.max)
    }

    @Transactional
    def deploy() {
        if (!params.file || !(params.file instanceof StandardMultipartHttpServletRequest.StandardMultipartFile)) {
            notFound()
            return
        }
        def key = params.key ?: null
        def category = params.category ?: null
        String name = params.name ?: ''
        def deployment = flowableRepositoryService.deploy(params.file, name, category, key)
        respond(deployment: deployment)
    }

    @Transactional
    def delete() {
        if (!params.deploymentId) {
            notFound()
            return
        }
        if (params.force) {
            flowableRepositoryService.forceDelete(params.deploymentId)
        } else {
            flowableRepositoryService.delete(params.deploymentId)
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'flowable.empty.upload.deployment', default: 'No file attached as deployment')
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NO_CONTENT, text: message(code: 'flowable.empty.upload.deployment', default: 'No file attached as deployment') }
        }
    }
}
