package tools.blocks.flowable

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.GroovyPageUnitTestMixin} for usage instructions
 */
@TestFor(FlowableTagLib)
class FlowableTagLibSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "resolveTaskButton as method"() {
        given:
            def attr = [taskId:1, iconClass:"fa fa-test"]
        expect:
            tagLib.resolveTaskButton(attr, null).toString() == "<a class='btn btn-default btn-resolve ' href='/flowableTask/resolveTask?taskId=1'>  <span class='fa fa-test'></span></a>"
    }

    void "claimTaskButton as method"() {
        given:
        def attr = [taskId:1, iconClass:"fa fa-test", userId:"test"]
        expect:
        tagLib.claimTaskButton(attr, null).toString() == "<a class='btn btn-default btn-claim ' href='/flowableTask/claimTask?taskId=1&userId=test'> <span class='fa fa-test'></span></a>"
    }

    void "unclaimTaskButton as method"() {
        given:
        def attr = [taskId:1, iconClass:"fa fa-test", userId:"test"]
        expect:
        tagLib.unclaimTaskButton(attr, null).toString() == "<a class='btn btn-default btn-unclaim ' href='/flowableTask/unclaimTask?taskId=1'> <span class='fa fa-test'></span></a>"
    }

    void "completeTask as method"() {
        given:
        def attr = [taskId:1, iconClass:"fa fa-test"]
        expect:
        tagLib.completeTaskButton(attr, null).toString() == "<a class='btn btn-default btn-complete ' href='/flowableTask/completeTask?taskId=1'>  <span class='fa fa-test'></span></a>"
    }
}
