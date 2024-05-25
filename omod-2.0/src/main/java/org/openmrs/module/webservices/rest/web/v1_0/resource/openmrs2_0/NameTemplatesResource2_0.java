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

import org.openmrs.layout.name.NameTemplate;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.resource.api.Listable;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

/**
 * API endpoint to get a list of all available NameTemplates.
 *
 * Unfortunately, there already exists a "/nametemplate" endpoint - see NameTemplateController2_0
 * so to avoid breaking the existing API, we use '/nametemplates' as the endpoint for the list.
 */
@Resource(name = RestConstants.VERSION_1 + "/nametemplates", supportedClass = NameTemplate.class, supportedOpenmrsVersions = {
		"2.0.* - 9.*" })
public class NameTemplatesResource2_0 implements Listable {
	
	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		NameTemplateResource2_0 templateResource = new NameTemplateResource2_0();
		return templateResource.getAll(context);
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/nametemplates";
	}
}
