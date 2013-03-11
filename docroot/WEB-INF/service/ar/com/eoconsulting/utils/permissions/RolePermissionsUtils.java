package ar.com.eoconsulting.utils.permissions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;


/**
 * Permissions utils.
 *
 * @author Mariano Ruiz
 */

public abstract class RolePermissionsUtils {

	/**
	 * Get role ids has permissions with actions over the resource.
	 */
	public static long[] getRoleHasPermissions(long companyId, String name, int scope, String primKey)
	        throws SystemException {

		List<Long> roleList = new ArrayList<Long>();
		List<ResourcePermission> resourcePermissions =
				ResourcePermissionLocalServiceUtil.getResourcePermissions(companyId, name, scope, primKey);

		for(ResourcePermission r : resourcePermissions) {
			if(r.getActionIds()!=0) {
				roleList.add(r.getRoleId());
			}
		}

		long[] list = new long[roleList.size()];
		for(int i=0; i<list.length; i++) {
			list[i] = roleList.get(i);
		}
		return list;
	}

	public static Map<Long, String[]> getRoleIdsToActionIds(
			long companyId, String name, int scope, String primKey, long roleIds[], Collection<String> actionIds)
					throws PortalException, SystemException {

		Map<Long, String[]> roleIdsToActionIds1 = new HashMap<Long, String[]>();
		Map<Long, Set<String>> roleIdsToActionIds2 = ResourcePermissionLocalServiceUtil.getAvailableResourcePermissionActionIds(
				companyId,
				name,
				scope,
				primKey,
				roleIds, actionIds);
		for(Long roleId : roleIdsToActionIds2.keySet()) {
			roleIdsToActionIds1.put(roleId, roleIdsToActionIds2.get(roleId).toArray(new String[0]));
		}
		return roleIdsToActionIds1;
	}
}
