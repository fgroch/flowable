package tools.blocks.flowable

class FlowableTagLib {
    static namespace = "flowable"

    def downloadDiagram = { attrs, body ->
        def deploymentId = attrs.remove('deploymentId')
        def controller = attrs.remove('controller') ?: 'flowableRepository'
        def action = attrs.remove('action') ?: 'getProcessDiagram'
        def label = attrs.remove('label') ?: ''
        final StringBuilder sb = new StringBuilder()

        if(deploymentId) {
            def downloadLink = g.createLink(controller: controller, action: action, params: ['deploymentId': deploymentId])
            sb.append("<a class='btn btn-default' href='${downloadLink}'>${label} <span class='fa fa-download'></span></a>")
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
        sb.append("<input class='upload-input' type='file' name='deplymentFile' id='deplymentFile' style='display: none;'>")
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
}
