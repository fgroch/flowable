package tools.blocks

import grails.config.Config
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import org.grails.datastore.gorm.jdbc.DataSourceBuilder
import org.grails.io.support.ClassPathResource
import org.grails.io.support.GrailsResourceUtils
import org.grails.io.support.Resource
import spock.lang.Specification
import tools.blocks.flowable.FlowableRepositoryService

/**
 * Created by fgroch on 29.01.17.
 */
@Integration
@Rollback
class FlowableRepositoryServiceIntegrationSpec extends Specification {

    static loadExternalBeans = true

    def doWithSpring = {
        Config conf = grailsApplication.config

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
        repositoryService(processEngine: "getRepositoryService")

    }

    FlowableRepositoryService flowableRepositoryService

    def setup() {

    }

    def cleanup() {

    }

    def "when service is created repository service is created"() {
        expect:
        flowableRepositoryService.repositoryService != null
    }

    def "when process is deployed deployment id won't be null"() {
        when:
            Resource r = new ClassPathResource("testX.bpmn20.xml")
            System.out.println(r.exists())
            System.out.println(GrailsResourceUtils.toURI("testX.bpmn20.xml"))
            URI uri = GrailsResourceUtils.toURI("testX.bpmn20.xml")
            String deploymentId = flowableRepositoryService.createDeployment().addInputStream("testX", r.inputStream).name("testX").deploy().id
        then:
            deploymentId != null
    }
}