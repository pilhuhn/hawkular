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
package org.hawkular.bus.messages;

import com.google.gson.annotations.Expose;
import org.hawkular.bus.common.BasicMessage;

import java.util.List;

/**
 * A bus message for messages on HawkularAvailData Topic.
 *
 * @author Jay Shaughnessy
 * @author Lucas Ponce
 */

public class AvailDataMessage extends BasicMessage {

    // the basic message body - it will be exposed to the JSON output
    @Expose
    private AvailData availData;

    protected AvailDataMessage() {
    }

    public AvailDataMessage(AvailData metricData) {
        this.availData = metricData;
    }

    public AvailData getAvailData() {
        return availData;
    }

    public void setAvailData(AvailData availData) {
        this.availData = availData;
    }

    public static class AvailData {
        @Expose
        List<SingleAvail> data;

        public AvailData() {
        }

        public List<SingleAvail> getData() {
            return data;
        }

        public void setData(List<SingleAvail> data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "AvailData [data=" + data + "]";
        }
    }

    /**
     * This is meant to parse out an instance of <code>org.rhq.metrics.client.common.SingleMetric</code>
     */
    public static class SingleAvail {
        @Expose
        private String tenantId;
        @Expose
        private String id;
        @Expose
        private long timestamp;
        @Expose
        private String avail;

        public SingleAvail() {
        }

        public SingleAvail(String tenantId, String id, long timestamp, String avail) {
            this.tenantId = tenantId;
            this.id = id;
            this.timestamp = timestamp;
            this.avail = avail;
        }

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getAvail() {
            return avail;
        }

        public void setAvail(String avail) {
            this.avail = avail;
        }

        @Override
        public String toString() {
            return "SingleAvail [tenantId=" + tenantId + ", id=" + id + ", timestamp=" + timestamp + ", avail="
                    + avail + "]";
        }

    }

}
