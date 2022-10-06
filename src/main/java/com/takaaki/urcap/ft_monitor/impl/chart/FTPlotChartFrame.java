package com.takaaki.urcap.ft_monitor.impl.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.TickUnits;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import com.takaaki.urcap.ft_monitor.impl.rtde.RTDEClientOfActualTCP;

public class FTPlotChartFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public JFreeChart chart;
    public ChartPanel chartPanel;
    public int scale;
    double upper;
    double lower;
    int width = 1000;
    int height = 600;
    final int frequency = 20;
    final FTData ftData;
    final FTPlotChartFrame thisFrame;

    final Color FRAME_BACKGROUND = new Color(156, 231, 156);

    final Font TITLE_FONT = new Font("Lucida Sans", Font.BOLD, 30);
    final Font TICK_LABEL_FONT = new Font("Lucida Sans", Font.PLAIN, 15);
    final Font BUTTON_FONT1 = new Font("Lucida Sans", Font.PLAIN, 10);
    final Font BUTTON_FONT2 = new Font("Lucida Sans", Font.PLAIN, 15);

    RTDEClientOfActualTCP rtdeClient;

    public FTPlotChartFrame() {
        setUndecorated(true);
        this.ftData = new FTData(1000);
        thisFrame = this;

        scale = 8;

        rtdeClient = new RTDEClientOfActualTCP(frequency) {

            @Override
            public void onGetValues(double[] actual_TCP_pose, double[] actual_TCP_speed, double[] actual_TCP_force) {

                ftData.updateData(actual_TCP_force[2]);

                if (thisFrame.isVisible())
                    setChart(ftData, scale);
            }

        };

        rtdeClient.start();

    }

    public boolean disposeRTDE() {
        if (rtdeClient != null) {
            rtdeClient.interrupt();

            while (rtdeClient.isAlive()) {
                Thread.yield();
            }

            rtdeClient = null;
        }

        return true;
    }

    public void setChart(FTData ftData, int scale) {

        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries series = new XYSeries(ftData.getContent());

        double[] datas = ftData.getDatas();

        for (int j = 0; j < datas.length; j++) {
            series.add((double) j, datas[datas.length - 1 - j]);
        }

        dataset.addSeries(series);

        ValueAxis xAxis = new NumberAxis();
        ValueAxis yAxis = new NumberAxis();
        XYItemRenderer renderer = new StandardXYItemRenderer();

        TickUnits xTickUnit = new TickUnits();
        xTickUnit.add(new NumberTickUnit(100.0));

        xAxis.setStandardTickUnits(xTickUnit);

        upper = scale;
        lower = -1 * scale;

        xAxis.setRange(0, 1000);
        if (lower == 0 && upper == 0) {
            yAxis.setAutoRange(true);
        } else {
            yAxis.setLowerBound(lower);
            yAxis.setUpperBound(upper);
        }

        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            renderer.setSeriesStroke(i, new BasicStroke(1));
        }

        XYPlot xyPlot = new XYPlot(dataset, xAxis, yAxis, renderer);

        chart = new JFreeChart("Force Torque Plotter", (Plot) xyPlot);
        chart.setBackgroundPaint(FRAME_BACKGROUND);
        chart.getTitle().setFont(TITLE_FONT);
        chart.setPadding(new RectangleInsets(0, 10, 40, 10));

        if (chartPanel == null) {
            chartPanel = new ChartPanel(chart, width, height, width, height,
                    ChartPanel.DEFAULT_MAXIMUM_DRAW_WIDTH, chartPanel.DEFAULT_MAXIMUM_DRAW_HEIGHT,
                    chartPanel.DEFAULT_BUFFER_USED, true, true, true, true, true);

            chartPanel.setLayout(null);
            chartPanel.setBorder(new LineBorder(Color.BLACK, 2));

            // Reset
            JButton btnDataReset = new JButton("<html>Data<br>Reset</html>");
            btnDataReset.setFont(BUTTON_FONT1);

            btnDataReset.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    if (!((JButton) mouseEvent.getSource()).isEnabled())
                        return;

                    thisFrame.ftData.ResetData();

                }

            });

            chartPanel.add(btnDataReset);

            // Close
            JButton btnClose = new JButton("Close");
            btnClose.setFont(BUTTON_FONT2);

            btnClose.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    if (!((JButton) mouseEvent.getSource()).isEnabled())
                        return;

                    getFrame(mouseEvent).setVisible(false);

                }

            });

            chartPanel.add(btnClose);

            JButton btnScaleUp = new JButton("<html>&#9650;</html>");
            btnScaleUp.setFont(BUTTON_FONT2);

            btnScaleUp.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    if (!((JButton) mouseEvent.getSource()).isEnabled())
                        return;

                    if (thisFrame.scale <= 32)
                        thisFrame.scale = thisFrame.scale * 2;

                }

            });

            chartPanel.add(btnScaleUp);

            JButton btnScaleDown = new JButton("<html>&#9660;</html>");
            btnScaleDown.setFont(BUTTON_FONT2);

            btnScaleDown.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    if (!((JButton) mouseEvent.getSource()).isEnabled())
                        return;

                    if (thisFrame.scale >= 2)
                        thisFrame.scale = thisFrame.scale / 2;

                }

            });

            chartPanel.add(btnScaleDown);

            this.add(chartPanel);
            this.pack();
            btnDataReset.setBounds(this.getWidth() - 240, 5, 60, 30);
            btnClose.setBounds(this.getWidth() - 100, 5, 80, 30);
            btnScaleUp.setBounds(10, 5, 50, 25);
            btnScaleDown.setBounds(65, 5, 50, 25);

            this.setAlwaysOnTop(true);
            this.setLocationRelativeTo(null);

        } else {

            chartPanel.setChart(chart);
        }
    }

    private JFrame getFrame(MouseEvent e) {
        Component comp = (Component) e.getComponent();
        return (JFrame) SwingUtilities.getWindowAncestor(comp);
    }
}