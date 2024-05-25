/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs2_0;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.mock.MockHttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Order;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.layout.LayoutTemplate;
import org.openmrs.layout.name.NameTemplate;
import org.openmrs.module.webservices.helper.ServerLogActionWrapper;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.test.Util;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.api.RestService;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs1_8.OrderResource1_8;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

public class NameTemplatesResource2_0Test  extends BaseModuleWebContextSensitiveTest {

    @Autowired
    RestService restService;

    @Autowired
    private MainResourceController mainResourceController;

    RequestContext context;

    NameTemplatesResource2_0 resource;

    @Before
    public void beforeEachTests() throws APIException {
        context = new RequestContext();
        resource = (NameTemplatesResource2_0) Context.getService(RestService.class).getResourceBySupportedClass(NameTemplate.class);
    }

    public String getURI() {
        return "nametemplates";
    }

    /**
     * Verifies that GET /.../nametemplates returns some list of nametemplates,
     * and that the list at least includes the nametemplate set via the
     * `layout.name.template` global property.
     *
     * @throws Exception
     */
    @Test
    public void shouldGetAll() throws Exception {
        SimpleObject all = resource.getAll(context);
        ArrayList<NameTemplate> results = (ArrayList<NameTemplate>) PropertyUtils.getProperty(all, "results");
        NameTemplate foo = results.get(0);
        Util.log("Get all", foo.getCodeName());
        Assert.assertEquals(5, Util.getResultsSize(all));
    }
}
