// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
package com.cloud.api.commands;

import org.apache.log4j.Logger;

import com.cloud.api.ApiConstants;
import com.cloud.api.BaseAsyncCmd;
import com.cloud.api.BaseCmd;
import com.cloud.api.IdentityMapper;
import com.cloud.api.Implementation;
import com.cloud.api.Parameter;
import com.cloud.api.ServerApiException;
import com.cloud.api.response.SuccessResponse;
import com.cloud.async.AsyncJob;
import com.cloud.event.EventTypes;
import com.cloud.network.as.AutoScalePolicy;
import com.cloud.user.Account;
import com.cloud.user.UserContext;

@Implementation(description = "Deletes a autoscale policy.", responseObject = SuccessResponse.class)
public class DeleteAutoScalePolicyCmd extends BaseAsyncCmd {
    public static final Logger s_logger = Logger.getLogger(DeleteAutoScalePolicyCmd.class.getName());
    private static final String s_name = "deleteautoscalepolicyresponse";
    // ///////////////////////////////////////////////////
    // ////////////// API parameters /////////////////////
    // ///////////////////////////////////////////////////

    @IdentityMapper(entityTableName = "autoscale_policies")
    @Parameter(name = ApiConstants.ID, type = CommandType.LONG, required = true, description = "the ID of the autoscale policy")
    private Long id;

    // ///////////////////////////////////////////////////
    // ///////////////// Accessors ///////////////////////
    // ///////////////////////////////////////////////////

    public Long getId() {
        return id;
    }

    // ///////////////////////////////////////////////////
    // ///////////// API Implementation///////////////////
    // ///////////////////////////////////////////////////

    @Override
    public String getCommandName() {
        return s_name;
    }

    @Override
    public long getEntityOwnerId() {
        AutoScalePolicy autoScalePolicy = _entityMgr.findById(AutoScalePolicy.class, getId());
        if (autoScalePolicy != null) {
            return autoScalePolicy.getAccountId();
        }

        return Account.ACCOUNT_ID_SYSTEM; // no account info given, parent this command to SYSTEM so ERROR events are
// tracked
    }

    @Override
    public String getEventType() {
        return EventTypes.EVENT_AUTOSCALEPOLICY_DELETE;
    }

    @Override
    public String getEventDescription() {
        return "deleting AutoScale Policy: " + getId();
    }

    @Override
    public void execute() {
        UserContext.current().setEventDetails("AutoScale Policy Id: " + getId());
        boolean result = _autoScaleService.deleteAutoScalePolicy(id);

        if (result) {
            SuccessResponse response = new SuccessResponse(getCommandName());
            this.setResponseObject(response);
        } else {
            s_logger.warn("Failed to delete autoscale policy " + getId());
            throw new ServerApiException(BaseCmd.INTERNAL_ERROR, "Failed to delete AutoScale Policy");
        }
    }

    @Override
    public AsyncJob.Type getInstanceType() {
        return AsyncJob.Type.AutoScalePolicy;
    }
}
