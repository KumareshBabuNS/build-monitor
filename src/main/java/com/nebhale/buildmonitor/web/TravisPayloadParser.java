/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nebhale.buildmonitor.web;

import com.nebhale.buildmonitor.domain.Build;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Pattern;

@Component
final class TravisPayloadParser implements WebHookController.PayloadParser {

    private static final Pattern PULL_REQUEST = Pattern.compile(".*/pull/[\\d]+");

    private static final String STATUS_BROKEN = "Broken";

    private static final String STATUS_ERRORED = "Errored";

    private static final String STATUS_FAILED = "Failed";

    private static final String STATUS_FIXED = "Fixed";

    private static final String STATUS_PASSED = "Passed";

    private static final String STATUS_PENDING = "Pending";

    private static final String STATUS_STILL_FAILING = "Still Failing";

    @Override
    public Build.State getState(Map<String, ?> payload) {
        String status = (String) payload.get("status_message");

        if (STATUS_BROKEN.equalsIgnoreCase(status)) {
            return Build.State.FAIL;
        } else if (STATUS_ERRORED.equalsIgnoreCase(status)) {
            return Build.State.FAIL;
        } else if (STATUS_FAILED.equalsIgnoreCase(status)) {
            return Build.State.FAIL;
        } else if (STATUS_FIXED.equalsIgnoreCase(status)) {
            return Build.State.PASS;
        } else if (STATUS_PASSED.equalsIgnoreCase(status)) {
            return Build.State.PASS;
        } else if (STATUS_PENDING.equalsIgnoreCase(status)) {
            return Build.State.IN_PROGRESS;
        } else if (STATUS_STILL_FAILING.equalsIgnoreCase(status)) {
            return Build.State.FAIL;
        } else {
            return Build.State.UNKNOWN;
        }
    }

    @Override
    public String getUri(Map<String, ?> payload) {
        return (String) payload.get("build_url");
    }

    @Override
    public Boolean shouldProcess(Map<String, ?> payload) {
        return isMaster(payload) && !isPullRequest(payload);
    }

    private Boolean isMaster(Map<String, ?> payload) {
        String branch = (String) payload.get("branch");
        return "master".equals(branch);
    }

    private Boolean isPullRequest(Map<String, ?> payload) {
        String compareUrl = (String) payload.get("compare_url");
        return compareUrl != null && PULL_REQUEST.matcher(compareUrl).matches();
    }

}
