package tools.blocks

import grails.config.Config
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import org.flowable.engine.repository.Deployment
import org.flowable.engine.repository.ProcessDefinition
import org.flowable.engine.runtime.ProcessInstance
import org.grails.datastore.gorm.jdbc.DataSourceBuilder
import org.grails.io.support.ClassPathResource
import org.grails.io.support.Resource
import spock.lang.Specification
import tools.blocks.flowable.FlowableRepositoryService
import tools.blocks.flowable.FlowableRuntimeService
import tools.blocks.flowable.FlowableTaskService

/**
 * Created by fgroch on 03.02.17.
 */
@Integration
@Rollback
class FlowableFormServiceIntegrationSpec extends Specification {
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
        runtimeService(processEngine: "getRuntimeService")
        formService(processEngine: "getFormService")

    }

    FlowableRepositoryService flowableRepositoryService
    FlowableRuntimeService flowableRuntimeService
    FlowableTaskService flowableTaskService
    Resource r
    Deployment deployment
    ProcessDefinition processDefinition
    String deploymentKey
    String deploymentId
    ProcessInstance processInstance

    def setup() {
        r = new ClassPathResource("testX.bpmn20.xml")
    }

    def deployProcess() {
        deployment = flowableRepositoryService.createDeployment().addInputStream("testX", r.inputStream).key("testX").name("testX").deploy()
        deploymentKey = deployment.key
        deploymentId = deployment.id
        processDefinition = flowableRepositoryService.createProcessDefinitionQuery().processDefinitionKey("testX").singleResult()
    }

    def startProcess() {
        processInstance = flowableRuntimeService.startProcessInstanceByKey(processDefinition.key)
    }

    def cleanup() {

    }
}
