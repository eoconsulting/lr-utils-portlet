package ar.com.eoconsulting.utils.portlet;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortletURLFactoryUtil;


/**
 * Portlet utils methods.
 *
 * @author Mariano Ruiz
 */
public abstract class PortletUtils {

	/**
	 * Invalid characters for asset words.<br/>
	 * Extracted from {@link com.liferay.portlet.asset.util.AssetUtil}
	 */
	public static final char[] INVALID_CHARACTERS = new char[] {
		CharPool.AMPERSAND, CharPool.APOSTROPHE, CharPool.AT,
		CharPool.BACK_SLASH, CharPool.CLOSE_BRACKET, CharPool.CLOSE_CURLY_BRACE,
		CharPool.COLON, CharPool.COMMA, CharPool.EQUAL, CharPool.GREATER_THAN,
		CharPool.FORWARD_SLASH, CharPool.LESS_THAN, CharPool.NEW_LINE,
		CharPool.OPEN_BRACKET, CharPool.OPEN_CURLY_BRACE, CharPool.PERCENT,
		CharPool.PIPE, CharPool.PLUS, CharPool.POUND, CharPool.QUESTION,
		CharPool.QUOTE, CharPool.RETURN, CharPool.SEMICOLON, CharPool.SLASH,
		CharPool.STAR, CharPool.TILDE
	};

	public static String getModalURL(
			HttpServletRequest request,
			PageContext pageContext,
			LiferayPortletResponse liferayPortletResponse,
			ThemeDisplay themeDisplay,
			String mvcPath,
			String titleKey,
			int width)
					throws WindowStateException, PortletModeException {

		PortletURL portletURL = PortletURLFactoryUtil.create(
				request, themeDisplay.getPortletDisplay().getId(),
				themeDisplay.getPlid(),
				javax.portlet.PortletRequest.RENDER_PHASE);

		portletURL.setWindowState(LiferayWindowState.POP_UP);
		portletURL.setPortletMode(PortletMode.VIEW);
		portletURL.setParameter("mvcPath", mvcPath);

		String title = LanguageUtil.get(pageContext, titleKey);
		String modalURL = "javascript:Liferay.Util.openWindow("
			+ "{dialog: {align: Liferay.Util.Window.ALIGN_CENTER, after: {"
			+ "render: function(event) {this.set('y', this.get('y') + 50);}}, width: " + width + "}, "
			+ "id: '" + liferayPortletResponse.getNamespace() + "editAsset', "
			+ "title: '" + title + "', "
			+ "uri:'" + HtmlUtil.escapeURL(portletURL.toString()) + "'});";

		return modalURL;
	}

	/**
	 * Return <code>true</code> if characters are valid for asset words.<br/>
	 * Extracted from {@link com.liferay.portlet.asset.util.AssetUtil}
	 */
	public static boolean isValidAssetWord(String word) {
		if (Validator.isNull(word)) {
			return false;
		}
		else {
			char[] wordCharArray = word.toCharArray();

			for (char c : wordCharArray) {
				for (char invalidChar : INVALID_CHARACTERS) {
					if (c == invalidChar) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
