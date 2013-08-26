package ar.com.eoconsulting.utils.documentlibrary;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;


/**
 * Document Library Folder utils.
 *
 * @author Mariano Ruiz
 */
public abstract class DLFileUtils {


	/**
	 * Return the full path of the file.
	 * If file is <code>null</code> return a empty String
	 */
	public static String getPath(DLFileEntry dlFileEntry)
			throws PortalException, SystemException {

		if(dlFileEntry==null) {
			return "";
		}
		if(dlFileEntry.getFolderId()==0L) {
			return dlFileEntry.getTitle();
		}
		return dlFileEntry.getFolder().getPath() + "/" + dlFileEntry.getTitle();
	}
}
