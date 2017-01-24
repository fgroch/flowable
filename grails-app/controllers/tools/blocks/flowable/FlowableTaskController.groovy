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

    }

    def deleteTask() {

    }

    def resolveTask() {

    }

    def completeTask() {

    }

    def delegateTask() {

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
