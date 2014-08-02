package com.allboxx.client;

import javax.websocket.ClientEndpointConfig;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by max_tolstykh on 03/08/14.
 */
public class Configurator extends ClientEndpointConfig.Configurator {
    @Override
    public void beforeRequest(Map<String, List<String>> headers) {
        headers.put("user-agent", Arrays.asList("operator"));
        super.beforeRequest(headers);
    }
}
