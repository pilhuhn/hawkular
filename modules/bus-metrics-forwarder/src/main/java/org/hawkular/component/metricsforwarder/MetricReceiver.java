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
package org.hawkular.component.metricsforwarder;

import org.hawkular.bus.messages.MetricDataMessage;
import org.hawkular.bus.common.consumer.BasicMessageListener;
import org.hawkular.metrics.core.api.MetricId;
import org.hawkular.metrics.core.api.MetricsService;
import org.hawkular.metrics.core.api.NumericMetric;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Receiver that listens on JMS Topic and checks for metrics *.status.code
 * Listening goes on 'java:/topic/HawkularMetricData'.
 *
 * @author Heiko W. Rupp
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "HawkularMetricData")
})
@SuppressWarnings("unused")
public class MetricReceiver extends BasicMessageListener<MetricDataMessage> {


    @Inject
    MetricsService service;

    @Override
    public void onBasicMessage(MetricDataMessage message) {

        try {

            MetricDataMessage.MetricData metricData = message.getMetricData();
            List<MetricDataMessage.SingleMetric> sourceList = metricData.getData();
            String tenant = metricData.getTenantId();

            // Copy the data into the target format expected by the Metrics-api
            List<NumericMetric> targeList = new ArrayList<>();
            for (MetricDataMessage.SingleMetric sm : sourceList) {

                MetricId metricId = new MetricId(sm.getSource());

                NumericMetric nm = new NumericMetric(tenant,metricId);
                nm.addData(sm.getTimestamp(),sm.getValue());

                targeList.add(nm);

            }

            service.addNumericData(targeList);

        } catch (Exception e) {
            e.printStackTrace();  // TODO: Customise this generated block
        }

    }


}
