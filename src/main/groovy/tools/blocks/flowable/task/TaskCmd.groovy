package tools.blocks.flowable.task

import grails.validation.Validateable
import org.flowable.task.api.DelegationState
import org.flowable.task.api.Task

/**
 * Created by fgroch on 21.01.17.
 */
class TaskCmd implements Validateable {
    String Id
    String name
    String description
    int priority
    String owner
    String assignee
    String processInstanceId
    String executionId
    String processDefinitionId
    Date createTime
    String taskDefinitionKey
    Date dueDate
    String category
    String parentTaskId
    String tenantId
    String formKey
    Map<String, Object> taskLocalVariables
    Map<String, Object> processVariables
    Date claimTime
    DelegationState delegationState

    void mapFromEntity(final Task task) {
        this.properties = task.properties
    }

    void mapToEntity(final Task task) {
        task.properties = this.properties
    }
}
