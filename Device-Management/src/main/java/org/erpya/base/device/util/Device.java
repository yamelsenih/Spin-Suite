/*************************************************************************************
 * Product: Spin-Suite (Mobile Suite)                       		                 *
 * Copyright (C) 2012-2018 E.R.P. Consultores y Asociados, C.A.                      *
 * Contributor(s): Yamel Senih ysenih@erpya.com				  		                 *
 * Contributor(s): Carlos Parada cparada@erpya.com				  		             *
 * This program is free software: you can redistribute it and/or modify              *
 * it under the terms of the GNU General Public License as published by              *
 * the Free Software Foundation, either version 3 of the License, or                 *
 * (at your option) any later version.                                               *
 * This program is distributed in the hope that it will be useful,                   *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                    *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                     *
 * GNU General Public License for more details.                                      *
 * You should have received a copy of the GNU General Public License                 *
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.            *
 ************************************************************************************/
package org.erpya.base.device.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Generic implementation of Device
 * @author Yamel Senih, ysenih@erpya.com, ERPCyA http://www.erpya.com
 */
public class Device implements IDevice {


    public Device(IDeviceType deviceType) {
        this.deviceType = deviceType;
        this.deviceTypeId = deviceType.getDeviceTypeId();
    }

    /** Device Type ID  */
    private String deviceTypeId = null;
    /** Device ID   */
    private String deviceId = null;
    /** Device Name */
    private String name = null;
    /** Device Type own */
    private IDeviceType deviceType = null;
    /** Available   */
    private boolean isAvailable = false;
    /** MAC Address */
    private String macAddress = null;
    /**	DeviceManager List	*/
    private Map<String, ConfigValue> deviceConfigValueMap = new HashMap<String, ConfigValue>();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getType() {
        return deviceType.getType();
    }

    @Override
    public Map<String, ConfigValue> getDeviceTypeConfig() {
        return deviceType.getDeviceTypeConfig();
    }

    @Override
    public String getHandlerClass() {
        return deviceType.getHandlerClass();
    }

    @Override
    public String getDeviceTypeId() {
        return deviceTypeId;
    }

    @Override
    public String getDeviceId() {
        return deviceId;
    }

    @Override
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public void setDeviceTypeId(String deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    @Override
    public Map<String, ConfigValue> getDeviceConfig() {
        return deviceType.getDeviceTypeConfig();
    }

    @Override
    public ConfigValue getConfigValue(String key) {
        return deviceConfigValueMap.get(key);
    }

    @Override
    public void addConfigValue(String key, Object value) {
        deviceConfigValueMap.put(key, new ConfigValue(value));
    }

    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public void setAvalilable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String getAddress() {
        return macAddress;
    }

    @Override
    public void setAddress(String address) {
        this.macAddress = address;
    }

    @Override
    public String toString() {
        return "Device{" +
                "deviceId='" + deviceId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
