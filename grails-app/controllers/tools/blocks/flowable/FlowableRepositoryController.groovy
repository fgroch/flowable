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
        def deployment = flowableRepositoryService.deploy(params.file, params.name ?: '')
        respond(deployment: deployment)
    }

    @Transactional
    def delete() {
        if (!params.deploymentId) {
            notFound()
            return
        }
        if (params.cascade) {
            flowableRepositoryService.delete(params.deploymentId, params.cascade)
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
