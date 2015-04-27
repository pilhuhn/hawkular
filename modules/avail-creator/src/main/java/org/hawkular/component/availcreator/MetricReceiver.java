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
package org.hawkular.component.availcreator;

import org.hawkular.bus.common.consumer.BasicMessageListener;
import org.hawkular.bus.messages.AvailDataMessage.SingleAvail;
import org.hawkular.bus.messages.MetricDataMessage;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.ConnectionFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Receiver that listens on JMS Topic and checks for metrics *.status.code
 * Listening goes on 'java:/topic/HawkularMetricData'.
 * Then computes availability and forwards that to a topic for availability
 *
 * Requires this in standalone.xml:
 *
 *  <admin-object use-java-context="true"
 *      enabled="true"
 *      class-name="org.apache.activemq.command.ActiveMQTopic"
 *      jndi-name="java:/topic/HawkularAvailData"
 *      pool-name="HawkularAvailData">
 *            <config-property name="PhysicalName">HawkularAvailData</config-property>
 *  </admin-object>
 *
 * @author Heiko W. Rupp
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "HawkularMetricData")
})
public class MetricReceiver extends BasicMessageListener<MetricDataMessage> {

    @javax.annotation.Resource(lookup = "java:/topic/HawkularAvailData")
    javax.jms.Topic topic;

    @javax.annotation.Resource(lookup = "java:/HawkularBusConnectionFactory")
    ConnectionFactory connectionFactory;

    @EJB
    AvailPublisher availPublisher;

    @Override
    public void onBasicMessage(MetricDataMessage message) {

        try {
            MetricDataMessage.MetricData data =message.getMetricData();
            String tenant = data.getTenantId();
            List<MetricDataMessage.SingleMetric> dataList = data.getData();

            List<SingleAvail> outer = new ArrayList<>();

            List<Availability> availabilityList = new ArrayList<>();

            for (MetricDataMessage.SingleMetric item : dataList) {

                String source = item.getSource();
                if (source.endsWith(".status.code")) {
                    double codeD = item.getValue();
                    int code = (int) codeD;

                    String id = source.substring(0, source.indexOf("."));
                    double timestampD = (double) item.getTimestamp();
                    long timestamp = (long) timestampD;

                    String avail = computeAvail(code);

                    SingleAvail ar = new SingleAvail(tenant, id, timestamp, avail);
                    outer.add(ar);
                }
            }
            availPublisher.sendToMetricsViaRest(outer);

            availPublisher.publishToTopic(outer, this);

        } catch (Exception e) {
            e.printStackTrace();  // TODO: Customise this generated block
        }

    }

    /**
     * Do the work of computing the availability from the status code
     * @param code Status code of the web request
     * @return "UP" or "DOWN" accordingly
     */
    private String computeAvail(int code) {
        if (code <= 399) {
            return "UP";
        }
        return "DOWN";
    }

}
