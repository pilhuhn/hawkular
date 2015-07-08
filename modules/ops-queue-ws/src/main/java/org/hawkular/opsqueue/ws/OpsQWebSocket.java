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
package org.hawkular.opsqueue.ws;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * TODO document me
 *
 * @author Heiko W. Rupp
 */
@SuppressWarnings("unused")
@ServerEndpoint("/")
public class OpsQWebSocket {


    @OnOpen
    public void init(Session client) {
        Log.LOG.bla("New connection from " + client);

    }


    @OnMessage
    public String echo(String message, Session client) {
        Log.LOG.bla("Msg " + message + " from client " + client);

        // Sort out where to put the stuff and who to foward it.

        // For now we just echo it

        return message;
    }

    @OnClose
    public void clientClose(Session client, CloseReason reason) {
        Log.LOG.bla("Client " + client + " does not like us anymore: " + reason);

    }

}
