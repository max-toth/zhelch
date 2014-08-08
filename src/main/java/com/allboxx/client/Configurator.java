package com.allboxx.client;

import javax.websocket.ClientEndpointConfig;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by max_tolstykh on 03/08/14.
 */
public class Configurator extends ClientEndpointConfig.Configurator {
    @Override
    public void beforeRequest(Map<String, List<String>> headers) {
        headers.put("Pragma", Arrays.asList("no-cache"));
        headers.put("Cache-Control", Arrays.asList("no-cache"));
        headers.put("User-Agent", Arrays.asList("operator"));
        headers.put("Sec-WebSocket-Extensions", Arrays.asList("x-webkit-deflate-frame"));
        headers.put("Host",Arrays.asList("allboxx.com"));
        headers.put("Upgrade", Arrays.asList("websocket"));
        headers.put("Connection", Arrays.asList("Upgrade"));
        headers.put("Origin", Arrays.asList("http://allboxx.com"));
        headers.put("Sec-WebSocket-Version",Arrays.asList("13"));


        super.beforeRequest(headers);
    }

    public String getTokenKey() {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");

            byte[] bytes = new byte[1024 / 8];
            sr.nextBytes(bytes);

            return new sun.misc.BASE64Encoder().encode(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
