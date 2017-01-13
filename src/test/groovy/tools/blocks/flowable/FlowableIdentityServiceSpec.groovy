package tools.blocks.flowable

import grails.test.mixin.TestFor
import org.flowable.engine.IdentityService
import org.flowable.idm.api.User
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(FlowableIdentityService)
class FlowableIdentityServiceSpec extends Specification {

    IdentityService identityService

    def setup() {
        identityService = Mock()
        service.identityService = identityService
    }

    def cleanup() {
    }

    def "when service is created identity service is created"() {
        expect:
            service.identityService != null
    }

    def "when creating user a new user object is created"() {
        when:
            User user = service.newUser("test")
        then:
            service.identityService != null
            user != null
            user.id == "test"
    }
}
