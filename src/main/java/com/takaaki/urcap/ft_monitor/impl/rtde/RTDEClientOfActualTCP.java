package com.takaaki.urcap.ft_monitor.impl.rtde;

import java.io.IOException;

public abstract class RTDEClientOfActualTCP extends RTDEClient {

    public RTDEClientOfActualTCP(int frequency) {
        super("192.168.2.10", frequency, "Actual TCP");

        addOutput(RTDEOutput.actual_TCP_pose);
        addOutput(RTDEOutput.actual_TCP_speed);
        addOutput(RTDEOutput.actual_TCP_force);

    }

    public abstract void onGetValues(double[] actual_TCP_pose, double[] actual_TCP_speed, double[] actual_TCP_force);

    @Override
    public void onReceive(Object[] values) throws IOException {

        double[] actual_TCP_pose = (double[]) values[0];
        double[] actual_TCP_speed = (double[]) values[1];
        double[] actual_TCP_force = (double[]) values[2];

        onGetValues(actual_TCP_pose, actual_TCP_speed, actual_TCP_force);
    }

    @Override
    public Object[] onSend() throws IOException {

        return null;
    }

}