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

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

/**
 * Handler for the Queue
 *
 * @author Heiko W. Rupp
 */
@Stateless
@Consumes("application/json")
@Produces("application/json")
@Path("/")
public class QueueHandler {


    @EJB
    OperationQueue queue;


    /** Retrieve one job at a time for the given feed */
    @GET
    @Path("/{feedId}")
    public Response getOperation(@PathParam("feedId")String feedId) {
        OpsItem tmp = queue.getItem(feedId);
        Response.ResponseBuilder builder;

        if (tmp==null) {
            builder = Response.noContent();
        }
        else {
            builder = Response.ok(tmp);
        }

        return builder.build();
    }


    /** Endpoint that all the requests are sent to. */
    @POST
    @Path("/")
    public Response addOperation(String item) {


        Response.ResponseBuilder builder;

        if (item==null || item.isEmpty()) {
            builder = Response.status(Response.Status.BAD_REQUEST);
            builder.entity("Item must not be null or empty");
        }
        else {

            Map bla = new Gson().fromJson(item,Map.class);
            String action = (String) bla.get("action");
            List<Map<String,String>> resources = (List<Map<String, String>>) bla.get("resources");

            for (Map<String,String> res: resources) {

                String feedId = res.get("feedId");
                String id = res.get("id");

                OpsItem opsItem = new OpsItem(feedId, id, action);
                queue.addItem(feedId,opsItem);
            }

            builder = Response.ok();
        }
        return builder.build();

    }
}
