package com.zl.sys.controller.config.log;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "applogs")
public class AppLogsConfiguration {
	
	private String ignoringUrls = "/;/sitemids;/sitemesh;";
	
	public String getIgnoringUrls() {
		return ignoringUrls;
	}
	public void setIgnoringUrls(String ignoringUrls) {
		this.ignoringUrls = ignoringUrls;
	}
}
