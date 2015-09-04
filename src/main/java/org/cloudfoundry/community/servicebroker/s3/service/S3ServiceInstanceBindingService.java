/*
 * Copyright 2014 the original author or authors.
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
package org.cloudfoundry.community.servicebroker.s3.service;

import org.cloudfoundry.community.servicebroker.exception.ServiceBrokerException;
import org.cloudfoundry.community.servicebroker.exception.ServiceInstanceBindingExistsException;
import org.cloudfoundry.community.servicebroker.model.ServiceInstance;
import org.cloudfoundry.community.servicebroker.model.ServiceInstanceBinding;
import org.cloudfoundry.community.servicebroker.s3.exception.UnsupportedPlanException;
import org.cloudfoundry.community.servicebroker.s3.plan.basic.BasicPlan;
import org.cloudfoundry.community.servicebroker.s3.plan.shared.SharedPlan;
import org.cloudfoundry.community.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author David Ehringer
 */
@Service
public class S3ServiceInstanceBindingService extends S3ServiceInstanceBase implements ServiceInstanceBindingService {

    @Autowired
    public S3ServiceInstanceBindingService(BasicPlan basicPlan, SharedPlan sharedPlan) {
        this.basicPlan = basicPlan;
        this.sharedPlan = sharedPlan;
    }

    @Override
    public ServiceInstanceBinding createServiceInstanceBinding(String bindingId, ServiceInstance serviceInstance,
            String serviceId, String planId, String appGuid) throws ServiceInstanceBindingExistsException,
            ServiceBrokerException {
        try {
            plan = getPlan(planId);
        } catch (UnsupportedPlanException e) {
            throw new ServiceBrokerException(e.getMessage());
        }
        return plan.createServiceInstanceBinding(bindingId, serviceInstance, serviceId, planId, appGuid);
    }

    @Override
    public ServiceInstanceBinding deleteServiceInstanceBinding(String bindingId, ServiceInstance serviceInstance,
            String serviceId, String planId) throws ServiceBrokerException {
        try {
            plan = getPlan(planId);
        } catch (UnsupportedPlanException e) {
            throw new ServiceBrokerException(e.getMessage());
        }
        return plan.deleteServiceInstanceBinding(bindingId, serviceInstance, serviceId, planId);
    }

    @Override
    public ServiceInstanceBinding getServiceInstanceBinding(String id) {
        throw new IllegalStateException("Not implemented");
    }
}
