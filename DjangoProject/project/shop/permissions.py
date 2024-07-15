from rest_framework.permissions import BasePermission

class IsGroupMember(BasePermission):
    def has_permission(self, request, view):
        # Check if the user belongs to the specific group
        return request.user.groups.filter(name='Admins').exists()