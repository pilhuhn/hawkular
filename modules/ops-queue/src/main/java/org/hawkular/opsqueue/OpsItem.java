/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hawkular.opsqueue;

/**
 * One item that is queued.
 *
 * @author Heiko W. Rupp
 */
public class OpsItem {

    private String id;
    String feedId;
    String resourceId;
    String action;
    private String tenantId;

    public OpsItem(String id, String feedId, String resourceId, String action, String tenantId) {
        this.id = id;
        this.feedId = feedId;
        this.resourceId = resourceId;
        this.action = action;
        this.tenantId = tenantId;
    }

    public String getId() {
        return id;
    }

    public String getFeedId() {
        return feedId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getAction() {
        return action;
    }

    public String getTenantId() {
        return tenantId;
    }
}
