package tools.blocks.flowable

import org.flowable.engine.form.FormProperty

class FlowableTagLib {
    static namespace = "flowable"

    def flowableFormService

    def downloadDiagram = { attrs, body ->
        def deploymentKey = attrs.remove('deploymentKey')
        def controller = attrs.remove('controller') ?: 'flowableRepository'
        def action = attrs.remove('action') ?: 'getProcessDiagram'
        def label = attrs.remove('label') ?: ''
        final StringBuilder sb = new StringBuilder()

        if(deploymentKey) {
            def downloadLink = g.createLink(controller: controller, action: action, params: ['deploymentKey': deploymentKey])
            sb.append("<a class='btn btn-default btn-download-diagram' href='${downloadLink}'>${label} <span class='fa fa-download'></span></a>")
        }

        out << sb.toString()
    }

    def uploadDeployment = { attrs, body ->
        def name = attrs.remove('name')
        def category = attrs.remove('category')
        def key = attrs.remove('key')
        def controller = attrs.remove('controller') ?: 'flowableRepository'
        def action = attrs.remove('action') ?: 'deploy'
        final StringBuilder sb = new StringBuilder()

        sb.append("<form id='uploadDeploymentForm' class='MultiFile-intercepted deployment-upload-inline' enctype='multipart/form-data' name='uploadDeploymentForm' method='post' action='/${controller}/${action}'>")
        sb.append("<input id='name' type='hidden' name='name' value='${name}'>")
        sb.append("<input id='category' type='hidden' name='category' value='${category}'>")
        sb.append("<input id='key' type='hidden' name='key' value='${key}'>")
        sb.append("<label class='btn btn-default btn-file'>")
        sb.append("<span class='fa fa-upload'>")
        sb.append("<input class='upload-input' type='file' name='deploymentFile' id='deploymentFile' style='display: none;'>")
        sb.append("</span>")
        sb.append("</label>")
        sb.append("</form>")
        sb.append("<script>")
        sb.append("\$(document).ready(function () {")
        sb.append("\$('.upload-input').on('change', function () {")
        sb.append("\$('#uploadDeploymentForm').submit();")
        sb.append("});")
        sb.append("});")
        sb.append("</script>")

        out << sb.toString()
    }

    def startProcessInstanceButton = { attrs, body ->
        def processDefinitionKey = attrs.remove('processDefinitionKey')
        def businessKey = attrs.remove('businessKey')
        def tenantId = attrs.remove('tenantId')
        def processDefinitionId = attrs.remove('processDefinitionId')
        def processInstanceName = attrs.remove('processInstanceName')
        def iconClass = attrs.remove('iconClass')
        def additionalClasses = attrs.remove('additionalClasses') ?: ''

        def controller = attrs.remove('controller') ?: 'flowableRuntime'
        def action = attrs.remove('action') ?: 'startProcessInstance'
        def label = attrs.remove('label') ?: ''
        final StringBuilder sb = new StringBuilder()
        def claimLink = g.createLink(controller: controller, action: action,
                params: [
                        'processDefinitionKey': processDefinitionKey,
                        'businessKey':businessKey,
                        'tenantId': tenantId,
                        'processDefinitionId': processDefinitionId,
                        'processInstanceName': processInstanceName
                    ])
        sb.append("<a class='btn btn-default btn-claim ${additionalClasses}' href='${claimLink}'>${label} ")
        if (iconClass && !iconClass.empty) {
            sb.append(" <span class='${iconClass}'></span>")
        }
        sb.append("</a>")

        out << sb.toString()
    }

    def claimTaskButton = { attrs, body ->
        def taskId = attrs.remove('taskId')
        def userId = attrs.remove('userId')
        def controller = attrs.remove('controller') ?: 'flowableTask'
        def action = attrs.remove('action') ?: 'claimTask'
        def label = attrs.remove('label') ?: ''
        def iconClass = attrs.remove('iconClass')
        def additionalClasses = attrs.remove('additionalClasses') ?: ''
        final StringBuilder sb = new StringBuilder()

        if(taskId || userId) {
            def claimLink = g.createLink(controller: controller, action: action, params: ['taskId': taskId, 'userId':userId])
            sb.append("<a class='btn btn-default btn-claim ${additionalClasses}' href='${claimLink}'>${label}")
            if (iconClass && !iconClass.empty) {
                sb.append(" <span class='${iconClass}'></span>")
            }
            sb.append("</a>")
        }
//fa fa-sign-in
        out << sb.toString()
    }

    def unclaimTaskButton = { attrs, body ->
        def taskId = attrs.remove('taskId')
        def controller = attrs.remove('controller') ?: 'flowableTask'
        def action = attrs.remove('action') ?: 'unclaimTask'
        def label = attrs.remove('label') ?: ''
        def iconClass = attrs.remove('iconClass')
        def additionalClasses = attrs.remove('additionalClasses') ?: ''
        final StringBuilder sb = new StringBuilder()

        if(taskId) {
            def unclaimLink = g.createLink(controller: controller, action: action, params: ['taskId': taskId])
            sb.append("<a class='btn btn-default btn-unclaim ${additionalClasses}' href='${unclaimLink}'>${label}")
            if (iconClass && !iconClass.empty) {
                sb.append(" <span class='${iconClass}'></span>")
            }
            sb.append("</a>")
        }
//fa fa-sign-out
        out << sb.toString()
    }

    def newTaskButton = { attrs, body ->
        def taskId = attrs.remove('taskId') ?: 0
        def controller = attrs.remove('controller') ?: 'flowableTask'
        def action = attrs.remove('action') ?: 'newTask'
        def label = attrs.remove('label') ?: ''
        def iconClass = attrs.remove('iconClass')
        def additionalClasses = attrs.remove('additionalClasses') ?: ''
        final StringBuilder sb = new StringBuilder()

        def newLink = g.createLink(controller: controller, action: action, params: ['taskId': taskId])
        sb.append("<a class='btn btn-default btn-new ${additionalClasses}' href='${newLink}'>${label}")
        if (iconClass && !iconClass.empty) {
            sb.append(" <span class='${iconClass}'></span>")
        }
        sb.append("</a>")

        out << sb.toString()
    }

    def saveTaskButton = { attrs, body ->
        //fa fa-floppy-o
        //def iconClass = attrs.remove('iconClass')
        //def additionalClasses = attrs.remove('additionalClasses') ?: ''
    }

    def deleteTaskButton = { attrs, body ->
        //
        def taskId = attrs.remove('taskId')
        def deleteReason = attrs.remove('deleteReason')
        def controller = attrs.remove('controller') ?: 'flowableTask'
        def action = attrs.remove('action') ?: 'deleteTask'
        def label = attrs.remove('label') ?: ''
        def iconClass = attrs.remove('iconClass')
        def additionalClasses = attrs.remove('additionalClasses') ?: ''
        final StringBuilder sb = new StringBuilder()

        if(taskId) {
            def deleteLink = g.createLink(controller: controller, action: action, params: ['taskId': taskId, 'deleteReason': deleteReason])
            sb.append("<a class='btn btn-default btn-delete ${additionalClasses}' href='${deleteLink}'>${label} ")
            if (iconClass && !iconClass.empty) {
                sb.append(" <span class='${iconClass}'></span>")
            }
            sb.append("</a>")
        }
//fa fa-trash
        out << sb.toString()
    }

    def completeTaskButton = { attrs, body ->
        def taskId = attrs.remove('taskId')
        def controller = attrs.remove('controller') ?: 'flowableTask'
        def action = attrs.remove('action') ?: 'completeTask'
        def label = attrs.remove('label') ?: ''
        def iconClass = attrs.remove('iconClass')
        def additionalClasses = attrs.remove('additionalClasses') ?: ''
        final StringBuilder sb = new StringBuilder()

        if(taskId) {
            def completeLink = g.createLink(controller: controller, action: action, params: ['taskId': taskId])
            sb.append("<a class='btn btn-default btn-complete ${additionalClasses}' href='${completeLink}'>${label} ")
            if (iconClass && !iconClass.empty) {
                sb.append(" <span class='${iconClass}'></span>")
            }
            sb.append("</a>")
        }

        out << sb.toString()
    }

    def delegateTaskButton = { attrs, body ->

    }

    def resolveTaskButton = { attrs, body ->
        def taskId = attrs.remove('taskId')
        def controller = attrs.remove('controller') ?: 'flowableTask'
        def action = attrs.remove('action') ?: 'resolveTask'
        def label = attrs.remove('label') ?: ''
        def iconClass = attrs.remove('iconClass')
        def additionalClasses = attrs.remove('additionalClasses') ?: ''
        final StringBuilder sb = new StringBuilder()

        if(taskId) {
            def resolveLink = g.createLink(controller: controller, action: action, params: ['taskId': taskId])
            sb.append("<a class='btn btn-default btn-resolve ${additionalClasses}' href='${resolveLink}'>${label} ")
            if (iconClass && !iconClass.empty) {
                sb.append(" <span class='${iconClass}'></span>")
            }
            sb.append("</a>")
        }
//mail-reply
        out << sb.toString()
    }

    def taskForm = { attrs, body ->
        def taskId = attrs.remove('taskId')
        final String s = ""
        if (taskId) {
            def taskFormData = flowableFormService.getTaskFormData()
            s = buildTaskFormControls(attrs, taskFormData)
        }
        out << s
    }

    def beanTaskForm = { attrs, body ->
        def bean = attrs.remove('bean')
        def taskId = attrs.remove('taskId')
        def title = attrs.remove('title') ?: 'Task'
        def controller = attrs.remove('controller') ?: 'flowableTask'
        def action = attrs.remove('action') ?: 'resolveTask'
        final StringBuilder sb = new StringBuilder()
        if (!bean) {
            sb.append("<div><form id ='' name=''>")
            sb.append("</form></div>")
        }
        out << sb.toString()
    }

    def startTaskForm = { attrs, body ->
        def taskId = attrs.remove('taskId')
        def title = attrs.remove('title') ?: 'Task'
        def controller = attrs.remove('controller') ?: 'flowableTask'
        def action = attrs.remove('action') ?: 'resolveTask'

        final StringBuilder sb = new StringBuilder()
        sb.append("<div><form id ='' name=''>")
        sb.append("</form></div>")
        out << sb.toString()
    }

    def beanStartTaskForm = { attrs, body ->
        def bean = attrs.remove('bean')
        def processId = attrs.remove('processId')
        def title = attrs.remove('title') ?: 'Task'
        def controller = attrs.remove('controller') ?: 'flowableTask'
        def action = attrs.remove('action') ?: 'resolveTask'
        final StringBuilder sb = new StringBuilder()
        if (!bean || !processId) {
            sb.append("<div><form id ='' name=''>")
            sb.append("</form></div>")
        }
        out << sb.toString()
    }

    def buildTaskFormControls(attrs, taskFormData) {
        def title = attrs.remove('title') ?: 'Task'
        def controller = attrs.remove('controller') ?: 'flowableTask'
        def action = attrs.remove('action') ?: 'resolveTask'
        final StringBuilder sb = new StringBuilder()
        def formProperties = taskFormData.formProperties
        sb.append("<div><form id ='' name=''>")
        for (FormProperty fp in formProperties) {
            switch (fp.type.name) {
                case "string":
                    sb.append(buildTaskTextInput(fp.properties))
                    break
                case "boolean":
                    sb.append(buildTaskBooleanInput(fp.properties))
                    break
                case "date":
                    sb.append(buildTaskDateInput(fp.properties))
                    break
                case "double":
                    sb.append(buildTaskDoubleInput(fp.properties))
                    break
                case "enum":
                    sb.append(buildTasDropdownInput(fp.properties))
                    break
                case "long":
                    sb.append(buildTaskLongInput(fp.properties))
                    break
            }
        }
        sb.append("</form></div>")
        sb.toString()
    }

    def buildTaskTextInput(attrs) {

    }

    def buildTaskDateInput(attrs) {

    }

    def buildTaskBooleanInput(attrs) {

    }

    def buildTaskLongInput(attrs) {

    }

    def buildTaskDoubleInput(attrs) {

    }

    def buildTasDropdownInput(attrs) {

    }
}
