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
import tools.blocks.flowable.FlowableFormService
import tools.blocks.flowable.FlowableRepositoryService
import tools.blocks.flowable.FlowableRuntimeService

/**
 * Created by fgroch on 15.02.17.
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
        springFormEngineConfiguration(org.flowable.form.spring.SpringFormEngineConfiguration) {
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
        formEngine(org.flowable.form.spring.FormEngineFactoryBean) {
            formEngineConfiguration = springFormEngineConfiguration
        }
        repositoryService(processEngine: "getRepositoryService")
        runtimeService(processEngine: "getRuntimeService")

        formService(formEngine: "getFormService")
    }

    FlowableFormService flowableFormService
    FlowableRepositoryService flowableRepositoryService
    FlowableRuntimeService flowableRuntimeService
    Resource r
    Deployment deployment
    ProcessDefinition processDefinition
    String deploymentKey
    String deploymentId
    ProcessInstance processInstance

    def setup() {
        r = new ClassPathResource("testFormData.bpmn20.xml")
    }

    def deployProcess() {
        deployment = flowableRepositoryService.createDeployment().addInputStream("testFormData", r.inputStream).key("testFormData").name("testFormData").deploy()
        deploymentKey = deployment.key
        deploymentId = deployment.id
        def procDefs = flowableRepositoryService.createProcessDefinitionQuery().processDefinitionKey("testFormData").list()
        if (procDefs && procDefs.size() > 0) {
            processDefinition = procDefs.get(0)
        }
    }

    def startProcess() {
        processInstance = flowableRuntimeService.startProcessInstanceByKey(processDefinition.key)
    }

    def cleanup() {

    }
}
