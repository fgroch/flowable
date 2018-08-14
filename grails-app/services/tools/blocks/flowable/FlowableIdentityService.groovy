package tools.blocks.flowable

import grails.transaction.Transactional
import org.flowable.common.engine.api.FlowableObjectNotFoundException
import org.flowable.engine.IdentityService
import org.flowable.idm.api.Group
import org.flowable.idm.api.GroupQuery
import org.flowable.idm.api.NativeGroupQuery
import org.flowable.idm.api.NativeUserQuery
import org.flowable.idm.api.Picture
import org.flowable.idm.api.User
import org.flowable.idm.api.UserQuery

@Transactional
class FlowableIdentityService {

    IdentityService identityService

    User newUser(String userId) {
        identityService.newUser(userId)
    }

    boolean saveUser(User user) {
        try {
            identityService.saveUser(user)
        } catch (RuntimeException e) {
            return false
        }
        return true
    }

    UserQuery createUserQuery() {
        identityService.createUserQuery()
    }

    NativeUserQuery createNativeUserQuery() {
        identityService.createNativeUserQuery()
    }

    def deleteUser(String userId) {
        identityService.deleteUser(userId)
    }

    Group newGroup(String groupId) {
        identityService.newGroup(groupId)
    }

    GroupQuery createGroupQuery() {
        identityService.createGroupQuery()
    }

    NativeGroupQuery createNativeGroupQuery() {
        identityService.createNativeGroupQuery()
    }

    List<Group> getPotentialStarterGroups(String processDefinitionId) {
        identityService.getPotentialStarterGroups()
    }

    List<User> getPotentialStarterUsers(String processDefinitionId) {
        identityService.getPotentialStarterUsers(processDefinitionId)
    }

    boolean saveGroup(Group group) {
        try {
            identityService.saveGroup(group)
        } catch (RuntimeException e) {
            return false
        }
        return true
    }

    def deleteGroup(String groupId) {
        identityService.deleteGroup(groupId)
    }

    boolean createMembership(String userId, String groupId) {
        try {
            identityService.createMembership(userId, groupId)
        } catch (RuntimeException e) {
            return false
        }
        return true
    }

    def deleteMembership(String userId, String groupId) {
        identityService.deleteMembership(userId, groupId)
    }

    boolean checkPassword(String userId, String password) {
        identityService.checkPassword(userId, password)
    }

    def setAuthenticatedUserId(String authenticatedUserId) {
        identityService.setAuthenticatedUserId(authenticatedUserId)
    }

    boolean setUserPicture(String userId, Picture picture) {
        try{
            identityService.setUserPicture(userId, picture)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    Picture getUserPicture(String userId) {
        try {
            identityService.getUserPicture(userId)
        } catch (FlowableObjectNotFoundException e) {
            return false
        }
        return true
    }

    def setUserInfo(String userId, String key, String value) {
        identityService.setUserInfo(userId, key, value)
    }

    String getUserInfo(String userId, String key) {
        identityService.getUserInfo(userId, key)
    }

    List<String> getUserInfoKeys(String userId) {
        identityService.getUserInfoKeys(userId)
    }

    def deleteUserInfo(String userId, String key) {
        identityService.deleteUserInfo(userId, key)
    }
}
