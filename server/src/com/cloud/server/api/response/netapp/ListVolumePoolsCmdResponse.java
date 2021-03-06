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
package com.cloud.server.api.response.netapp;

import com.cloud.api.ApiConstants;
import com.cloud.api.response.BaseResponse;
import com.cloud.serializer.Param;
import com.google.gson.annotations.SerializedName;

public class ListVolumePoolsCmdResponse extends BaseResponse {
	@SerializedName(ApiConstants.ID) @Param(description="pool id")
    private Long id;
	@SerializedName(ApiConstants.NAME) @Param(description="pool name")
    private String name;
	
	@SerializedName(ApiConstants.ALGORITHM) @Param(description="pool algorithm")
    private String algorithm;
	
	
	public Long getId() {
		return id;
	}
	
	
	public String getName() {
		return name;
	}
	
	public String getAlgorithm() {
		return algorithm;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
}
