package com.takaaki.urcap.ft_monitor.impl.chart;


public class FTData {

    private int length;

    private double[] datas;
    private String name;
    private String unit;

    public FTData(int length) {

        this.length = length;
        this.datas = new double[length];
        this.name = "Fz";
        this.unit = "N";

        for (int i = 0; i < length; i++) {
            datas[i] = 0;
        }

    }

    public void ResetData() {
        for (int i = 0; i < length; i++) {
            datas[i] = 0;
        }

    }

    public void updateData(double value) {

        double[] buffer = new double[length];

        for (int j = 0; j < buffer.length; j++)
            buffer[j] = datas[j];

        datas[0] = value;

        for (int j = 1; j < buffer.length; j++)
            datas[j] = buffer[j - 1];

    }

    public double[] getDatas() {
        return datas;
    }

    public String getContent() {
        return name + " [" + unit + "]";
    }

}
