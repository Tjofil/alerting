/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 *
 * Modifications Copyright OpenSearch Contributors. See
 * GitHub history for details.
 */

/*
 *   Copyright 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License").
 *   You may not use this file except in compliance with the License.
 *   A copy of the License is located at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   or in the "license" file accompanying this file. This file is distributed
 *   on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *   express or implied. See the License for the specific language governing
 *   permissions and limitations under the License.
 */

package org.opensearch.alerting.action

import org.opensearch.action.ActionRequest
import org.opensearch.action.ActionRequestValidationException
import org.opensearch.action.support.WriteRequest
import org.opensearch.alerting.model.destination.email.EmailGroup
import org.opensearch.common.io.stream.StreamInput
import org.opensearch.common.io.stream.StreamOutput
import org.opensearch.rest.RestRequest
import java.io.IOException

class IndexEmailGroupRequest : ActionRequest {
    var emailGroupID: String
    var seqNo: Long
    var primaryTerm: Long
    var refreshPolicy: WriteRequest.RefreshPolicy
    var method: RestRequest.Method
    var emailGroup: EmailGroup

    constructor(
        emailGroupID: String,
        seqNo: Long,
        primaryTerm: Long,
        refreshPolicy: WriteRequest.RefreshPolicy,
        method: RestRequest.Method,
        emailGroup: EmailGroup
    ) : super() {
        this.emailGroupID = emailGroupID
        this.seqNo = seqNo
        this.primaryTerm = primaryTerm
        this.refreshPolicy = refreshPolicy
        this.method = method
        this.emailGroup = emailGroup
    }

    @Throws(IOException::class)
    constructor(sin: StreamInput) : this(
        sin.readString(), // emailGroupID
        sin.readLong(), // seqNo
        sin.readLong(), // primaryTerm
        WriteRequest.RefreshPolicy.readFrom(sin), // refreshPolicy
        sin.readEnum(RestRequest.Method::class.java), // method
        EmailGroup.readFrom(sin) // emailGroup
    )

    override fun validate(): ActionRequestValidationException? {
        return null
    }

    @Throws(IOException::class)
    override fun writeTo(out: StreamOutput) {
        out.writeString(emailGroupID)
        out.writeLong(seqNo)
        out.writeLong(primaryTerm)
        refreshPolicy.writeTo(out)
        out.writeEnum(method)
        emailGroup.writeTo(out)
    }
}
