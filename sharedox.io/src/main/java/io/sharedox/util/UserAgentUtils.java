package io.sharedox.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author: Sergey Royz
 * Date: 07.04.2014
 */
public class UserAgentUtils {

	private static final String FB_CRAWLER_SIGNATURE = "facebookexternalhit";

	public static boolean isFacebookCrawler(String userAgent) {
		return !StringUtils.isEmpty(userAgent) && userAgent.contains(FB_CRAWLER_SIGNATURE);
	}

}