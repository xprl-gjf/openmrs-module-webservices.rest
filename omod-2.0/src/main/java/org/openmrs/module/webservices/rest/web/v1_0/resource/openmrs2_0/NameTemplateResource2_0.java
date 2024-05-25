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

import org.openmrs.layout.name.NameSupport;
import org.openmrs.layout.name.NameTemplate;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingReadableResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ConversionException;
import org.openmrs.module.webservices.rest.web.v1_0.helper.LayoutTemplateProvider;

import java.util.Collections;
import java.util.List;

/*
 * WARNING!
 *
 * To maintain backwards-compatibility with earlier API versions,
 * the API method GET /nametemplate must be handled by @see NameTemplateController2_0#get(WebRequest request)
 *
 * This currently works because the NameTemplateController route GET /ws/rest/v1/nametemplate
 * has precedence over the @see MainResourceController route when installed in the DispatcherServlet
 *
 * If for some reason this order of precedence is changed and the route is handled by the MainResourceController
 * calling getAll() on this class, the API call will instead return the list of all known NameTemplates.
 */

@Resource(name = RestConstants.VERSION_1 + "/nametemplate", supportedClass = NameTemplate.class, supportedOpenmrsVersions = {
		"2.0.* - 9.*" })
public class NameTemplateResource2_0 extends BaseDelegatingReadableResource<NameTemplate> {
	
	public static final String LAYOUT_NAME_DEFAULTS = "layout.name.defaults";
	
	public static final String GLOBAL_NAME_TEMPLATE = "global";
	
	@Override
	public List<Representation> getAvailableRepresentations() {
		return Collections.emptyList();     // NameTemplate resource does not support representations
	}
	
	@Override
	public NameTemplate getByUniqueId(String codename) {
		// special-case handling for GET /nametemplate/global; return the system-configured default name template.
		// (duplicates functionality of GET /nametemplate in the hope that the latter may one day be deprecated/retired...)
		if (codename.equalsIgnoreCase(GLOBAL_NAME_TEMPLATE)) {
			return getTemplateProvider().getDefaultLayoutTemplate();
		}
		// return the NameTemplate identified by the given codename.
		return getTemplateProvider().getLayoutTemplateByName(codename);
	}
	
	@Override
	public SimpleObject getAll(RequestContext context) {
		List<NameTemplate> templates = getTemplateProvider().getAllLayoutTemplates();
		SimpleObject rest = new SimpleObject();
		rest.add("results", templates);
		return rest;
	}
	
	@Override
	public NameTemplate newDelegate() {
		return new NameTemplate();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		return getTemplateProvider().getRepresentationDescription();
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.api.Converter#asRepresentation(Object, Representation)
	 */
	@Override
	public SimpleObject asRepresentation(NameTemplate instance, Representation rep) throws ConversionException {
		return getTemplateProvider().asRepresentation(instance);
	}
	
	private LayoutTemplateProvider<NameTemplate> getTemplateProvider() {
		return new LayoutTemplateProvider<>(NameSupport.getInstance(), LAYOUT_NAME_DEFAULTS);
	}
}
