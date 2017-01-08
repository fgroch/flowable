package tools.blocks.flowable

import grails.transaction.Transactional
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest

import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NOT_MODIFIED
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

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
        def ret = false
        if (params.force) {
            ret = flowableRepositoryService.forceDelete(params.deploymentId)
        } else {
            ret = flowableRepositoryService.delete(params.deploymentId)
        }
        if (ret) {
            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'flowable.deployment.deleted', default: 'Deployment deleted')
                    respond ret, [status: OK]
                }
                '*'{ render status: OK, text: message(code: 'flowable.deployment.deleted', default: 'Deployment deleted') }
            }
        } else {
            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'flowable.deployment.not.deleted', default: 'Deployment not deleted')
                    respond ret, [status: NOT_MODIFIED]
                }
                '*'{ render status: NOT_MODIFIED, text: message(code: 'flowable.deployment.not.deleted', default: 'Deployment not deleted') }
            }
        }

    }

    @Transactional
    def suspendProcessDefinition() {
        def deploymentKey = params.deploymentKey
        def deploymentId = params.deploymentId
        def suspendProcessInstances = params.suspendProcessInstances ?: false
        def suspensionDate = params.suspensionDate
        def executed = false
        def ret = false
        if (deploymentId) {
            ret = flowableRepositoryService.suspendProcessDefinitionById(deploymentId, suspendProcessInstances, suspensionDate)
            executed = true
        }

        if (deploymentKey && !executed) {
            ret = flowableRepositoryService.suspendProcessDefinitionByKey(deploymentKey, suspendProcessInstances, suspensionDate)
            executed = true
        }

        if (executed) {
            if (ret) {
                request.withFormat {
                    form multipartForm {
                        flash.message = message(code: 'flowable.deployment.suspended', default: 'Deployment suspended')
                        respond ret, [status: OK]
                    }
                    '*' { respond executed, [status: OK] }
                }
            } else {
                request.withFormat {
                    form multipartForm {
                        flash.message = message(code: 'flowable.deployment.not.suspended', default: 'Deployment not suspended')
                        respond ret, [status: NOT_MODIFIED]
                    }
                    '*' { respond executed, [status: NOT_MODIFIED] }
                }
            }
        } else {
            notFound()
        }
    }

    @Transactional
    def activateProcessDefinition() {
        def deploymentKey = params.deploymentKey
        def deploymentId = params.deploymentId
        def activateProcessInstances = params.activateProcessInstances ?: false
        def activationDate = params.activationDate
        def tenantId = params.tenantId
        def executed = false
        def ret = false

        if (deploymentId) {
            ret = flowableRepositoryService.activateProcessDefinitionById(deploymentId, activateProcessInstances, activationDate)
            executed = true
        }

        if (deploymentKey && !executed) {
            ret = flowableRepositoryService.activateProcessDefinitionByKey(deploymentKey, activateProcessInstances, activationDate, tenantId)
            executed = true
        }

        if (executed) {
            if (ret) {
                request.withFormat {
                    form multipartForm {
                        flash.message = message(code: 'flowable.deployment.activated', default: 'Deployment activated')
                        respond ret, [status: OK]
                    }
                    '*' { respond executed, [status: OK] }
                }
            } else {
                request.withFormat {
                    form multipartForm {
                        flash.message = message(code: 'flowable.deployment.not.activated', default: 'Deployment not activated')
                        respond ret, [status: NOT_MODIFIED]
                    }
                    '*' { respond executed, [status: NOT_MODIFIED] }
                }
            }
        } else {
            notFound()
        }
    }

    def isProcessDefinitionSuspended() {
        if (!params.deploymentId) {
            notFound()
            return
        }
        def ret = flowableRepositoryService.isProcessDefinitionSuspended(deploymentId)
        respond executed, [status: OK]
    }

    def getProcessDiagram() {

        if (!params.deploymentId) {
            notFound()
            return
        }
        def inputStream = flowableRepositoryService.getProcessDiagram(params.deploymentId)
        if (inputStream) {

            ['Content-disposition': "${params.containsKey('inline') ? 'inline' : 'attachment'};filename=\"$params.deploymentId + '.png'\"",
             'Cache-Control': 'private',
             'Pragma': ''].each {k, v ->
                response.setHeader(k, v)
            }
            response.contentType = 'image/png'
            //response.contentType = 'application/octet-stream'
            response.outputStream << inputStream
            return
        }
        response.status = NOT_FOUND
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'flowable.empty.deployment', default: 'No deployment set')
                respond false, [status: NO_CONTENT]
            }
            '*'{ render status: NO_CONTENT, text: message(code: 'flowable.empty.deployment', default: 'No deployment set') }
        }
    }
}
