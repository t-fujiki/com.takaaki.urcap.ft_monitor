package com.takaaki.urcap.ft_monitor.impl;

import com.takaaki.urcap.ft_monitor.impl.chart.FTPlotChartFrame;
import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.domain.URCapAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.ui.annotation.Input;
import com.ur.urcap.api.ui.component.InputEvent;
import com.ur.urcap.api.ui.component.*;

import static com.ur.urcap.api.ui.component.InputEvent.EventType.ON_PRESSED;

public class FTMonitorInstallationNodeContribution implements InstallationNodeContribution {

	public URCapAPI api;
	public DataModel model;

	private final String ARTIFACTID = "ft_monitor";

	private FTPlotChartFrame ftPlotChartFrame;

	@Input(id = "btnShow")
	private InputButton btnShow;

	@Input(id = "btnShow")
	private void onClick_btnShow(InputEvent event) {
		if (event.getEventType() == ON_PRESSED) {

			if (ftPlotChartFrame == null)
				ftPlotChartFrame = new FTPlotChartFrame();

			ftPlotChartFrame.setVisible(true);
		}
	}

	public FTMonitorInstallationNodeContribution(URCapAPI api, DataModel model) {
		this.api = api;
		this.model = model;

	}

	@Override
	public void openView() {

	}

	@Override
	public void closeView() {

	}

	public boolean isDefined() {
		return false;
	}

	public void dispose() {
		if (ftPlotChartFrame != null) {
			if (ftPlotChartFrame.disposeRTDE()) {
				ftPlotChartFrame.dispose();
				ftPlotChartFrame = null;
			}
		}
	}

	@Override
	public void generateScript(ScriptWriter writer) {

	}

}
