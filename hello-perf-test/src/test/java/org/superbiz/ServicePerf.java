/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.superbiz;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;

import java.net.URI;

public class ServicePerf extends Assert {

    public static void main(String[] args) throws Exception {
        final ServicePerf perf = new ServicePerf("http://localhost:8080/hello/");
        perf.get();
    }

    private final CloseableHttpClient httpClient;
    private final URI webappUri;

    public ServicePerf(String webappUrl) {
        webappUri = URI.create(webappUrl);
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(30);
        httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
    }



    public void get() throws Exception {
        // GET
        {
            final HttpGet get = new HttpGet(webappUri.resolve("hello"));
            try (final CloseableHttpResponse response = httpClient.execute(get)) {
                final String content = EntityUtils.toString(response.getEntity());
                assertEquals(200, response.getStatusLine().getStatusCode());
            }
        }

    }



}
