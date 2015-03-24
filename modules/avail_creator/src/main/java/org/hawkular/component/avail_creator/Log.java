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
package org.hawkular.component.avail_creator;

import org.jboss.logging.LogMessage;
import org.jboss.logging.Logger;
import org.jboss.logging.Message;
import org.jboss.logging.MessageLogger;

/**
 * Log messages of the creator
 *
 * @author Heiko W. Rupp
 */
@MessageLogger(projectCode = "HAWKULAR")
public interface Log {

    Log LOG = Logger.getMessageLogger(Log.class, "org.hawkular.component.avail_creator");

    @LogMessage(level = Logger.Level.WARN)
    @Message(id = 5100, value = "No connection to topic %s possible")
    void wNoTopicConnection(String topicName);

    @LogMessage(level = Logger.Level.WARN)
    @Message(id = 5101, value = "Problem sending availabiliy to Hawkular-Metrics: %s")
    void availPostStatus(String s);
}