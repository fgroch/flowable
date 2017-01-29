package tools.blocks

import grails.config.Config
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import org.grails.datastore.gorm.jdbc.DataSourceBuilder
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
}