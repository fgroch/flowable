package tools.blocks.flowable

class FlowableTagLib {
    static namespace = "flowable"

    def download = { attrs, body ->
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
}
