package com.takaaki.urcap.ft_monitor.impl.rtde;


public enum ROBOT_MODE {
    ROBOT_MODE_NO_CONTROLLER(-1), /* */
    ROBOT_MODE_DISCONNECTED(0), /* */
    ROBOT_MODE_CONFIRM_SAFETY(1), /* */
    ROBOT_MODE_BOOTING(2), /* */
    ROBOT_MODE_POWER_OFF(3), /* */
    ROBOT_MODE_POWER_ON(4), /* */
    ROBOT_MODE_IDLE(5), /* */
    ROBOT_MODE_BACKDRIVE(6), /* */
    ROBOT_MODE_RUNNING(7), /* */
    ROBOT_MODE_UPDATING_FIRMWARE(8);

    private int ptype;

    private ROBOT_MODE(int ptype) {
        this.ptype = ptype;
    }

    public int getType() {
        return ptype;
    }

}