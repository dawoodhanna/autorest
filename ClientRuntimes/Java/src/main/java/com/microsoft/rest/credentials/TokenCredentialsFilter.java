/**
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 *
 */

package com.microsoft.rest.credentials;

import com.microsoft.rest.core.pipeline.ServiceRequestContext;
import com.microsoft.rest.core.pipeline.ServiceRequestFilter;

import java.util.concurrent.ExecutorService;

public class TokenCredentialsFilter implements ServiceRequestFilter {
    private TokenCredentials credentials;

    public TokenCredentialsFilter(TokenCredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public void filter(ServiceRequestContext request) {
        ExecutorService service = null;

        try {
            request.setHeader("Authorization", credentials.getScheme() + " " + credentials.getToken());
        } catch (Exception e) {
            // silently fail
        } finally {
            if (service != null) {
                service.shutdown();
            }
        }
    }
}