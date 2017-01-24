package tools.blocks.flowable

import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.NOT_MODIFIED

class FlowableTaskController {

    def flowableTaskService

    def claimTask() {
        if (!flowableTaskService.claim(params.taskId, params.userId)) {
            unsuccessfulAction()
        } else {
            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'flowable.claim.ok', default: 'Task claimed')
                    respond false, [status: OK]
                }
                '*'{ render status: OK, text: message(code: 'flowable.claim.ok', default: 'Task claimed') }
            }
        }
    }

    def unclaimTask() {
        if (!flowableTaskService.unclaim(params.taskId)) {
            unsuccessfulAction()
        } else {
            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'flowable.unclaim.ok', default: 'Task unclaimed')
                    respond false, [status: OK]
                }
                '*'{ render status: OK, text: message(code: 'flowable.unclaim.ok', default: 'Task unclaimed') }
            }
        }
    }

    def deleteTask() {
        if (!flowableTaskService.deleteTask(params.taskId, params.deleteReason)) {
            unsuccessfulAction()
        } else {
            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'flowable.delete.ok', default: 'Task deleted')
                    respond false, [status: OK]
                }
                '*'{ render status: OK, text: message(code: 'flowable.delete.ok', default: 'Task deleted') }
            }
        }
    }

    def resolveTask() {
        if (!flowableTaskService.resolveTask(params.taskId)) {
            unsuccessfulAction()
        } else {
            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'flowable.resolve.ok', default: 'Task resolved')
                    respond false, [status: OK]
                }
                '*'{ render status: OK, text: message(code: 'flowable.resolve.ok', default: 'Task resolved') }
            }
        }
    }

    def completeTask() {
        if (!flowableTaskService.complete(params.taskId)) {
            unsuccessfulAction()
        } else {
            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'flowable.complete.ok', default: 'Task completed')
                    respond false, [status: OK]
                }
                '*'{ render status: OK, text: message(code: 'flowable.complete.ok', default: 'Task completed') }
            }
        }
    }

    def delegateTask() {
        if (!flowableTaskService.delegateTask(params.taskId, params.userId)) {
            unsuccessfulAction()
        } else {
            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'flowable.delegate.ok', default: 'Task delegated')
                    respond false, [status: OK]
                }
                '*'{ render status: OK, text: message(code: 'flowable.delegate.ok', default: 'Task delegated') }
            }
        }
    }

    def saveTask() {

    }

    protected void unsuccessfulAction() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'flowable.action.unsuccessful', default: 'Action was unsuccessful')
                respond false, [status: NOT_MODIFIED]
            }
            '*'{ render status: NOT_MODIFIED, text: message(code: 'flowable.action.unsuccessful', default: 'Action was unsuccessful') }
        }
    }
}
