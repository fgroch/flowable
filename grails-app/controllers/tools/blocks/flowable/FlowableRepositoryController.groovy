package tools.blocks.flowable

import grails.transaction.Transactional
import org.flowable.common.engine.api.FlowableObjectNotFoundException
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest

import static org.springframework.http.HttpStatus.*

import static org.grails.web.servlet.mvc.OutputAwareHttpServletResponse.*

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
        if (!params.deploymentFile || !(params.deploymentFile instanceof StandardMultipartHttpServletRequest.StandardMultipartFile)) {
            emptyDeployment()
            return
        }
        def key = params.key
        def category = params.category
        String name = params.name ?: ''
        def deployment = flowableRepositoryService.deploy(params.deploymentFile, name, category, key)
        respond(deployment: deployment)
    }

    @Transactional
    def delete() {
        if (!params.deploymentId) {
            emptyDeployment()
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
                    respond ret, [status: SC_OK]
                }
                '*'{ render status: SC_OK, text: message(code: 'flowable.deployment.deleted', default: 'Deployment deleted') }
            }
        } else {
            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'flowable.deployment.not.deleted', default: 'Deployment not deleted')
                    respond ret, [status: SC_NOT_MODIFIED]
                }
                '*'{ render status: SC_NOT_MODIFIED, text: message(code: 'flowable.deployment.not.deleted', default: 'Deployment not deleted') }
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
                        respond ret, [status: SC_OK]
                    }
                    '*' { respond executed, [status: SC_OK] }
                }
            } else {
                request.withFormat {
                    form multipartForm {
                        flash.message = message(code: 'flowable.deployment.not.suspended', default: 'Deployment not suspended')
                        respond ret, [status: SC_NOT_MODIFIED]
                    }
                    '*' { respond executed, [status: SC_NOT_MODIFIED] }
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
                        respond ret, [status: SC_OK]
                    }
                    '*' { respond executed, [status: SC_OK] }
                }
            } else {
                request.withFormat {
                    form multipartForm {
                        flash.message = message(code: 'flowable.deployment.not.activated', default: 'Deployment not activated')
                        respond ret, [status: SC_NOT_MODIFIED]
                    }
                    '*' { respond executed, [status: SC_NOT_MODIFIED] }
                }
            }
        } else {
            notFound()
        }
    }

    def isProcessDefinitionSuspended() {
        if (!params.deploymentId) {
            emptyDeployment()
            return
        }
        def ret = flowableRepositoryService.isProcessDefinitionSuspended(params.deploymentId)
        respond ret, [status: SC_OK]
    }

    def getProcessDiagram() {

        if (!params.deploymentKey) {
            emptyDeployment()
            return
        }
        def inputStream
        try {
            inputStream = flowableRepositoryService.getProcessDiagramResource(params.deploymentKey)
            if (inputStream) {

                ['Content-disposition': "${params.containsKey('inline') ? 'inline' : 'attachment'};filename=\"${params.deploymentKey}.png\"",
                 'Cache-Control'      : 'private',
                 'Pragma'             : ''].each { k, v ->
                    response.setHeader(k, v)
                }
                response.contentType = 'image/png'
                //response.contentType = 'application/octet-stream'
                response.outputStream << inputStream
                return
            }
        } catch (FlowableObjectNotFoundException e) {
            log.debug("Diagram not found for download", e)
        }
        //respond([:], status: NOT_FOUND)
        //response.status = SC_NOT_FOUND
        notFound()
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'flowable.deployment.not.found', default: 'Deployment not found')
                respond false, [status: NO_CONTENT]
            }
            '*'{ render status: NO_CONTENT, text: message(code: 'flowable.deployment.not.found', default: 'Deployment not found') }
        }
    }

    protected void emptyDeployment() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'flowable.empty.deployment', default: 'No deployment set')
                respond false, [status: NO_CONTENT]
            }
            '*'{ render status: NO_CONTENT, text: message(code: 'flowable.empty.deployment', default: 'No deployment set') }
        }
    }
}
