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

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton keeping the queues for the various feeds.
 *
 * This is currently not persisted and thus does not survive a
 * server restart.
 *
 * @author Heiko W. Rupp
 */
@Startup
@Singleton
public class OperationQueue {

    private Map<String,List<OpsItem>> queuesByFeed;
    private Map<String,OpsItem> outstanding;
    private Map<String,List<ResolvedItem>> resolvedItems;

    @PostConstruct
    private void init() {
        queuesByFeed = new HashMap<>();
        outstanding = new HashMap<>();
        resolvedItems = new HashMap<>();
    }

    public void addItem(String feedId, OpsItem item) {
        List<OpsItem> list;
        list = queuesByFeed.get(feedId);
        if (list==null) {
            list = new ArrayList<>();
            queuesByFeed.put(feedId,list);
        }
        list.add(item);
    }

    public OpsItem getItem(String feedId) {
        if (queuesByFeed.isEmpty() || queuesByFeed.get(feedId)==null) {
            return null;
        }

        List<OpsItem> queue = queuesByFeed.get(feedId);

        if (queue.isEmpty()) {
            return null;
        }

        OpsItem tmp = queue.remove(0);
        outstanding.put(tmp.getId(),tmp);
        return tmp;
    }

    public OpsItem getOutstanding(String id) {

        if (!outstanding.containsKey(id)) {
            return null;
        }
        OpsItem item = outstanding.remove(id);
        return item;
    }

    public boolean isOutstanding(String id ) {
        return outstanding.containsKey(id);
    }

    public void addResolved(String tenantId, ResolvedItem item) {

        List<ResolvedItem> items = resolvedItems.get(tenantId);
        if (items==null) {
            items = new ArrayList<>();
            resolvedItems.put(tenantId,items);
        }
        items.add(item);
    }

    public List<ResolvedItem> getResolvedItems(String feedId) {
        if (!resolvedItems.containsKey(feedId)) {
            return Collections.EMPTY_LIST;
        }

        List<ResolvedItem> items = resolvedItems.remove(feedId);
        return items;
    }

    public boolean isResolved(String id) {
        return resolvedItems.containsKey(id);
    }
}
