package com.takaaki.urcap.ft_monitor.impl;

import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.contribution.InstallationNodeService;
import com.ur.urcap.api.domain.URCapAPI;

import java.io.InputStream;
import java.util.Locale;

import com.ur.urcap.api.domain.data.DataModel;

public class FTMonitorInstallationNodeService implements InstallationNodeService {

	public FTMonitorInstallationNodeContribution contribution = null;

	public FTMonitorInstallationNodeService() {

	}

	@Override
	public InstallationNodeContribution createInstallationNode(URCapAPI api, DataModel model) {
		if (contribution != null) {
			contribution.dispose();
			contribution = null;
		}

		contribution = new FTMonitorInstallationNodeContribution(api, model);

		return contribution;
	}

	@Override
	public String getTitle() {
		if (Locale.getDefault().getLanguage().equals("jp"))
			return "FT Monitor";
		else
			return "FT Monitor";

	}

	@Override
	public InputStream getHTML() {

		return this.getClass().getResourceAsStream("/com/takaaki/urcap/ft_monitor/impl/installation_en.html");

	}
}
