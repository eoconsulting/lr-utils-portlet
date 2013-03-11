package ar.com.eoconsulting.utils.organization;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.liferay.portal.NoSuchOrganizationException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;


/**
 * Organizations utils.
 *
 * @author Mariano Ruiz
 */
public abstract class OrganizationUtils {

	private static Log _log = LogFactoryUtil.getLog(OrganizationUtils.class);

	/**
	 * @return <code>true</code> if <code>parentOrganizationName</code> is ancestor of <code>organization</code>.
	 */
	public static boolean isAncestorOrganization(Organization organization, String parentOrganizationName)
			throws PortalException, SystemException {

		Organization parentOrganization = organization.getParentOrganization();
		while(parentOrganization != null) {
			if(parentOrganization.getName().equals(parentOrganizationName)) {
				return true;
			}
			parentOrganization = parentOrganization.getParentOrganization();
		}
		return false;
	}

	/**
	 * Get all users email address of the organization.
	 */
	public static ArrayList<InternetAddress> getUsersEmailAddress(long organizationId) throws SystemException, PortalException {
		List<User> users = UserLocalServiceUtil.getOrganizationUsers(organizationId);
		ArrayList<InternetAddress> addresses = new ArrayList<InternetAddress>(users.size());
		String emails = "";
		for(User user : users) {
			try {
				if(user.getStatus()==WorkflowConstants.STATUS_APPROVED) {
					InternetAddress address = new InternetAddress(user.getEmailAddress());
					addresses.add(address);
					if(_log.isTraceEnabled()) {
						emails += " " + user.getEmailAddress();
					}
				}
			} catch(AddressException e) {
				throw new SystemException(e);
			}
		}
		if(_log.isTraceEnabled()) {
			_log.trace("Emails from organization " +
					OrganizationLocalServiceUtil.getOrganization(organizationId).getName()
					+ ":" + emails);
		}
		return addresses;
	}

	/**
	 * Return empty string if <code>organizationId==null</code>,
	 * else the name of the organization, or <code>"(" + organizationId + ")"</code>
	 * if not exist.
	 */
	public static String getOrganizationName(Long organizationId) {
		if(organizationId==null) {
			return "";
		}
		try {
			return OrganizationLocalServiceUtil.getOrganization(organizationId).getName();
		} catch(NoSuchOrganizationException e) {
			return "(" + organizationId + ")";
		} catch (PortalException e) {
			throw new RuntimeException(e);
		} catch (SystemException e) {
			throw new RuntimeException(e);
		}
	}
}
