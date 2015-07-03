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
 * Result of an operation.
 * This needs to be in sync with the version in the agent.
 *
 * @author Heiko W. Rupp
 */
public class OpsResult {
    boolean success;
    String result;
    boolean rolledBack;

    public OpsResult() {
    }

    public OpsResult(boolean success) {
        this.success = success;
    }

    public OpsResult(boolean success, String result, boolean rolledBack) {
        this.success = success;
        this.result = result;
        this.rolledBack = rolledBack;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isRolledBack() {
        return rolledBack;
    }

    public void setRolledBack(boolean rolledBack) {
        this.rolledBack = rolledBack;
    }

    @Override
    public String toString() {
        String s = "OpsResult{success=" + success ;
        if (!success) {
            s += ", result='" + result + '\'' +
                 ", rolledBack=" + rolledBack;
        }
        s += '}';
        return s;
    }
}
