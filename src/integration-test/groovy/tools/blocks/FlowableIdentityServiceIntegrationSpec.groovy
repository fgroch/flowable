package tools.blocks


import grails.test.mixin.integration.Integration
import grails.transaction.*
import org.grails.datastore.gorm.jdbc.DataSourceBuilder
import spock.lang.*
import tools.blocks.flowable.FlowableIdentityService

@Integration
@Rollback
class FlowableIdentityServiceIntegrationSpec extends Specification {

    static loadExternalBeans = true

    def doWithSpring = {


        dataSource(org.springframework.jdbc.datasource.DriverManagerDataSource) {
            return DataSourceBuilder.create()
                    .url(conf.getProperty("environments.${grails.util.Environment.current.name}.flowable.datasource.url"))
                    .username(conf.getProperty("environments.${grails.util.Environment.current.name}.flowable.datasource.username"))
                    .password(conf.getProperty("environments.${grails.util.Environment.current.name}.flowable.datasource.password"))
                    .driverClassName(conf.getProperty("environments.${grails.util.Environment.current.name}.flowable.datasource.driverClassName"))
                    .build();
        }
        transactionManager(org.springframework.jdbc.datasource.DataSourceTransactionManager) {
            dataSource = dataSource
        }
        processEngineConfiguration(org.flowable.spring.SpringProcessEngineConfiguration) {
            transactionManager = transactionManager
            dataSource = dataSource
            databaseSchemaUpdate = true
            asyncExecutorActivate = false
            databaseSchemaUpdate = conf.getProperty("environments.${grails.util.Environment.current.name}.flowable.datasource.dbCreate")
            deploymentResources = conf.getProperty("environments.${grails.util.Environment.current.name}.flowable.deploymentResources")
            deploymentMode = conf.getProperty("environments.${grails.util.Environment.current.name}.flowable.deploymentMode")
        }
        processEngine(org.flowable.spring.ProcessEngineFactoryBean) {
            processEngineConfiguration = processEngineConfiguration
        }
        identityService(processEngine: "getIdentityService")

    }

    /*def doWithConfig(c) {
        c.myConfigValue = 'Hello'
    }*/

    FlowableIdentityService flowableIdentityService

    def setup() {
    }

    def cleanup() {
    }

    def "when service is created identity service is created"() {
        expect:
        flowableIdentityService.identityService != null
    }

    def "when creating user a new user object is created"() {
        when:
        def user = flowableIdentityService.newUser("test")
        then:
        user != null
        user.id == "test"
    }
}
