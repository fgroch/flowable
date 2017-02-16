package tools.blocks.flowable

import grails.config.Config
import grails.plugins.*
import org.grails.datastore.gorm.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Configuration

//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder

class FlowableGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.2.3 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Flowable" // Headline display name of the plugin
    def author = "Filip Grochowski"
    def authorEmail = "fgroch@gmail.com"
    def description = '''\
Brief summary/description of the plugin.
'''
    def profiles = ['web']

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/flowable"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
//    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    Closure doWithSpring() {
        {->
            final Config conf = grailsApplication.config
            //System.out.println(conf)
            dataSource(org.springframework.jdbc.datasource.DriverManagerDataSource) {
                return DataSourceBuilder.create()
                    .url(conf.getProperty("environments.${grails.util.Environment.current.name}.flowable.datasource.url"))
                    //.url("jdbc:h2:tcp://localhost/~/flowable")
                    //.url("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=1000")
                    .username(conf.getProperty("environments.${grails.util.Environment.current.name}.flowable.datasource.username"))
                    .password(conf.getProperty("environments.${grails.util.Environment.current.name}.flowable.datasource.password"))
                    .driverClassName(conf.getProperty("environments.${grails.util.Environment.current.name}.flowable.datasource.driverClassName"))
                    .build();
                //org.springframework.jdbc.datasource.DriverManagerDataSource
                //org.springframework.jdbc.datasource.SimpleDriverDataSource
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
            formEngineConfiguration(org.flowable.form.spring.SpringFormEngineConfiguration) {
                transactionManager = transactionManager
                dataSource = dataSource
                databaseSchemaUpdate = true
                databaseSchemaUpdate = conf.getProperty("environments.${grails.util.Environment.current.name}.flowable.datasource.dbCreate")
                deploymentResources = conf.getProperty("environments.${grails.util.Environment.current.name}.flowable.deploymentResources")
                deploymentMode = conf.getProperty("environments.${grails.util.Environment.current.name}.flowable.deploymentMode")
            }
            processEngine(org.flowable.spring.ProcessEngineFactoryBean) {
                processEngineConfiguration = processEngineConfiguration
            }
            formEngine(org.flowable.form.spring.FormEngineFactoryBean) {
                formEngineConfiguration = formEngineConfiguration
            }
            repositoryService(processEngine: "getRepositoryService")
            taskService(processEngine: "getTaskService")
            historyService(processEngine: "getHistoryService")
            managementService(processEngine: "getManagementService")
            identityService(processEngine: "getIdentityService")
            runtimeService(processEngine: "getRuntimeService")
            formPropertyService(processEngine: "getFormService")

            formService(formEngine: "getFormService")
            formRepositoryService(formEngine: "getFormRepositoryService")

        }
    }

    void doWithDynamicMethods() {
        // TODO Implement registering dynamic methods to classes (optional)
    }

    void doWithApplicationContext() {
        // TODO Implement post initialization spring config (optional)
    }

    void onChange(Map<String, Object> event) {
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    void onConfigChange(Map<String, Object> event) {
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    void onShutdown(Map<String, Object> event) {
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
