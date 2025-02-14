/*
 * Copyright (C) 2014-2021 Authlete, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.authlete.common.dto;


import java.io.Serializable;
import java.util.Map;
import com.authlete.common.util.Utils;


/**
 * Request to Authlete's {@code /auth/authorization/issue} API.
 *
 * <blockquote>
 * <dl>
 * <dt><b><code>ticket</code></b> (REQUIRED)</dt>
 * <dd>
 * <p>
 * The ticket issued by Authlete's {@code /auth/authorization} API
 * to the service implementation. It is the value of {@code "ticket"}
 * contained in the response from Authlete's {@code
 * /auth/authorization} API ({@link AuthorizationResponse}).
 * </p>
 * </dd>
 *
 * <dt><b><code>subject</code></b> (CONDITIONALLY REQUIRED)</dt>
 * <dd>
 * <p>
 * The subject (= a user account managed by the service) who has
 * granted authorization to the client application. This parameter
 * is required unless the authorization request has come with
 * {@code response_type=none} (which means the client application
 * did not request any token to be returned). See "<a href=
 * "http://openid.net/specs/oauth-v2-multiple-response-types-1_0.html#none"
 * >4. None Response Type</a>" in <a href=
 * "http://openid.net/specs/oauth-v2-multiple-response-types-1_0.html"
 * >OAuth 2.0 Multiple Response Type Encoding Practices</a> for
 * details about {@code response_type=none}.
 * </p>
 * </dd>
 *
 * <dt><b><code>authTime</code></b> (OPTIONAL)</dt>
 * <dd>
 * <p>
 * The time when the authentication of the end-user occurred.
 * </p>
 * </dd>
 *
 * <dt><b><code>acr</code></b> (OPTIONAL)</dt>
 * <dd>
 * <p>
 * The Authentication Context Class Reference performed for the
 * end-user authentication.
 * </p>
 * </dd>
 *
 * <dt><b><code>claims</code></b> (OPTIONAL)</dt>
 * <dd>
 * <p>
 * The claims of the end-user (= pieces of information about the
 * end-user) in JSON format. See
 * <a href="http://openid.net/specs/openid-connect-core-1_0.html#StandardClaims"
 * >OpenID Connect Core 1.0, 5.1. Standard Claims</a> for details
 * about the format.
 * </p>
 * </dd>
 *
 * <dt><b><code>idtHeaderParams</code></b> (OPTIONAL)</dt>
 * <dd>
 * <p>
 * JSON that represents additional JWS header parameters for ID tokens that may
 * be issued based on the authorization request.
 * </p>
 * </dd>
 *
 * <dt><b><code>properties</code></b> (OPTIONAL)</dt>
 * <dd>
 * <p>
 * Extra properties to associate with an access token and/or an authorization
 * code that may be issued by this request. Note that {@code properties}
 * parameter is accepted only when Content-Type of the request is
 * application/json, so don't use application/x-www-form-urlencoded if you
 * want to specify {@code properties} parameter.
 * </p>
 * </dd>
 *
 * <dt><b><code>scopes</code></b> (OPTIONAL)</dt>
 * <dd>
 * <p>
 * Scopes to associate with an access token and/or an authorization code.
 * If this field is {@code null}, the scopes specified in the original
 * authorization request from the client application are used. In other
 * cases, including the case of an empty array, the specified scopes will
 * replace the original scopes contained in the original authorization
 * request.
 * </p>
 * <p>
 * Even scopes that are not included in the original authorization request
 * can be specified. However, as an exception, <code>"openid"</code> scope
 * is ignored on the server side if it is not included in the original
 * request. It is because the existence of <code>"openid"</code> scope
 * considerably changes the validation steps and because adding
 * <code>"openid"</code> triggers generation of an ID token (although the
 * client application has not requested it) and the behavior is a major
 * violation against the specification.
 * </p>
 * <p>
 * If you add <code>"offline_access"</code> scope although it is not
 * included in the original request, keep in mind that the specification
 * requires explicit consent from the user for the scope (<a href=
 * "http://openid.net/specs/openid-connect-core-1_0.html#OfflineAccess"
 * >OpenID Connect Core 1.0, 11. Offline Access</a>). When
 * <code>"offline_access"</code> is included in the original request, the
 * current implementation of Authlete's {@code /auth/authorization} API
 * checks whether the request has come along with <code>prompt</code>
 * request parameter and the value includes <code>"consent"</code>. However,
 * note that the implementation of Authlete's {@code /auth/authorization/issue}
 * API does not perform such checking if <code>"offline_access"</code> scope
 * is added via this <code>scopes</code> parameter.
 * </p>
 * </dd>
 *
 * <dt><b><code>sub</code></b> (OPTIONAL)</dt>
 * <dd>
 * <p>
 * The value of the {@code sub} claim. If the value of this request parameter
 * is not empty, it is used as the value of the {@code sub} claim. Otherwise,
 * the value of the {@code subject} request parameter is used as the value of
 * the {@code sub} claim. The main purpose of this parameter is to hide the
 * actual value of the subject from client applications.
 * </p>
 * <p>
 * Note that even if this {@code sub} parameter is not empty, the value of the
 * {@code subject} request parameter is used as the value of the subject which
 * is associated with the access token.
 * </p>
 * </dd>
 *
 * <dt><b><code>authorizationDetails</code></b> (OPTIONAL)</dt>
 * <dd>
 * <p>
 * The value of the {@code authorization_details} to associate with the token.
 * If this value is {@code null}, the authorization details on the original
 * request are used. If this value is set, its contents completely override
 * the authorization details set in the original request.
 * </p>
 * </dd>
 * </dl>
 * </blockquote>
 *
 * @see AuthorizationResponse
 *
 * @see <a href="http://openid.net/specs/openid-connect-core-1_0.html#StandardClaims"
 *      >OpenID Connect Core 1.0, 5.1. Standard Claims</a>
 *
 * @author Takahiko Kawasaki
 */
public class AuthorizationIssueRequest implements Serializable
{
    private static final long serialVersionUID = 9L;


    /**
     * The ticket issued by Authlete's endpoint.
     */
    private String ticket;


    /**
     * The subject (end-user) managed by the service.
     */
    private String subject;


    /**
     * The value of the 'sub' claim in an ID token.
     * When this field is empty, 'subject' is used.
     */
    private String sub;


    /**
     * The time when the end-user was authenticated.
     */
    private long authTime;


    /**
     * The authentication context class reference.
     */
    private String acr;


    /**
     * Claims in JSON format.
     */
    private String claims;


    /**
     * Extra properties to associate with an access token and/or
     * an authorization code.
     */
    private Property[] properties;


    /**
     * Scopes to associate with an access token and/or an authorization code.
     * If this field is {@code null}, the scopes specified in the original
     * authorization request from the client application are used. In other
     * cases, including the case of an empty array, the scopes here will
     * replace the original scopes contained in the original request.
     */
    private String[] scopes;


    /**
     * JSON that represents additional JWS header parameters for ID tokens
     * that may be issued based on the authorization request.
     *
     * @since 2.76
     */
    private String idtHeaderParams;


    /**
     * The authorization details to associate with the access token.
     */
    private AuthzDetails authorizationDetails;

    /**
     * Get the value of {@code "ticket"} which is the ticket
     * issued by Authlete's {@code /auth/authorization} API
     * to the service implementation.
     *
     * @return
     *         The ticket.
     */
    public String getTicket()
    {
        return ticket;
    }


    /**
     * Set the value of {@code "ticket"} which is the ticket
     * issued by Authlete's {@code /auth/authorization} API
     * to the service implementation.
     *
     * @param ticket
     *         The ticket.
     *
     * @return
     *         {@code this} object.
     */
    public AuthorizationIssueRequest setTicket(String ticket)
    {
        this.ticket = ticket;

        return this;
    }


    /**
     * Get the value of {@code "subject"} which is the subject
     * (= a user account managed by the service) who has granted
     * authorization to the client application.
     *
     * <p>
     * This {@code subject} property is used as the value of the
     * subject associated with the access token (if one is issued)
     * and as the value of the {@code sub} claim in the ID token
     * (if one is issued).
     * </p>
     *
     * <p>
     * Note that, if {@link #getSub()} returns a non-empty value,
     * it is used as the value of the {@code sub} claim in the ID
     * token. However, even in such a case, the value of the
     * subject associated with the access token is still the value
     * of this {@code subject} property.
     * </p>
     *
     * @return
     *         The subject.
     *
     * @see #getSub()
     */
    public String getSubject()
    {
        return subject;
    }


    /**
     * Set the value of {@code "subject"} which is the subject
     * (= a user account managed by the service) who has granted
     * authorization to the client application.
     *
     * <p>
     * This {@code subject} property is used as the value of the
     * subject associated with the access token (if one is issued)
     * and as the value of the {@code sub} claim in the ID token
     * (if one is issued).
     * </p>
     *
     * <p>
     * Note that, if a non-empty value is set by {@link #setSub(String)}
     * method, the value is used as the value of the {@code sub} claim
     * in the ID token. However, even in such a case, the value of the
     * subject associated with the access token is still the value set
     * by this method.
     * </p>
     *
     * @param subject
     *         The subject.
     *
     * @return
     *         {@code this} object.
     *
     * @since {@link #setSub(String)}
     */
    public AuthorizationIssueRequest setSubject(String subject)
    {
        this.subject = subject;

        return this;
    }


    /**
     * Get the value of the {@code sub} claim that should be used in
     * the ID token which is to be issued. If this method returns
     * {@code null} or its value is empty, the value of the {@code
     * subject} is used. The main purpose of this {@code sub} property
     * is to hide the actual value of the subject from client applications.
     *
     * <p>
     * Note that the value of the {@code subject} request parameter is
     * used as the value of the subject associated with the access token
     * regardless of whether this {@code sub} property is a non-empty
     * value or not.
     * </p>
     *
     * @return
     *         The value of the {@code sub} claim.
     *
     * @since 1.35
     *
     * @see #getSubject()
     */
    public String getSub()
    {
        return sub;
    }


    /**
     * Set the value of the {@code sub} claim that should be used in
     * the ID token which is to be issued. If {@code null} (the default
     * value) or an empty string is given, the value of the {@code
     * subject} is used. The main purpose of this {@code sub} property
     * is to hide the actual value of the subject from client applications.
     *
     * <p>
     * Note that the value of the {@code subject} request parameter is
     * used as the value of the subject associated with the access token
     * regardless of whether this {@code sub} property is a non-empty
     * value or not.
     * </p>
     *
     * @param sub
     *         The value of the {@code sub} claim.
     *
     * @return
     *         {@code this} object.
     *
     * @since 1.35
     *
     * @see #setSubject(String)
     */
    public AuthorizationIssueRequest setSub(String sub)
    {
        this.sub = sub;

        return this;
    }


    /**
     * Get the value of {@code "authTime"} which is the time
     * when the authentication of the end-user occurred.
     *
     * @return
     *         The time when the end-user authentication occurred.
     *         It is the number of seconds since 1970-01-01.
     */
    public long getAuthTime()
    {
        return authTime;
    }


    /**
     * Set the value of {@code "authTime"} which is the time
     * when the authentication of the end-user occurred.
     *
     * @param authTime
     *         The time when the end-user authentication occurred.
     *         It is the number of seconds since 1970-01-01.
     *
     * @return
     *         {@code this} object.
     */
    public AuthorizationIssueRequest setAuthTime(long authTime)
    {
        this.authTime = authTime;

        return this;
    }


    /**
     * Get the value of {@code "acr"} which is the authentication
     * context class reference value which the end-user authentication
     * satisfied.
     *
     * @return
     *         The authentication context class reference.
     */
    public String getAcr()
    {
        return acr;
    }


    /**
     * Set the value of {@code "acr"} which is the authentication
     * context class reference value which the end-user authentication
     * satisfied.
     *
     * @param acr
     *         The authentication context class reference.
     *
     * @return
     *         {@code this} object.
     */
    public AuthorizationIssueRequest setAcr(String acr)
    {
        this.acr = acr;

        return this;
    }


    /**
     * Get the value of {@code "claims"} which is the claims of the subject
     * in JSON format.
     *
     * @return
     *         The claims of the subject in JSON format. See the description
     *         of {@link #setClaims(String)} for details about the format.
     *
     * @see #setClaims(String)
     */
    public String getClaims()
    {
        return claims;
    }


    /**
     * Set the value of {@code "claims"} which is the claims of the subject
     * in JSON format.
     *
     * <p>
     * The service implementation is required to retrieve claims of the subject
     * (= information about the end-user) from its database and format them in
     * JSON format.
     * </p>
     *
     * <p>
     * For example, if <code>"given_name"</code> claim, <code>"family_name"</code>
     * claim and <code>"email"</code> claim are requested, the service implementation
     * should generate a JSON object like the following:
     * </p>
     *
     * <pre>
     * {
     *   "given_name": "Takahiko",
     *   "family_name": "Kawasaki",
     *   "email": "takahiko.kawasaki@example.com"
     * }
     * </pre>
     *
     * <p>
     * and set its String representation by this method.
     * </p>
     *
     * <p>
     * See <a href="http://openid.net/specs/openid-connect-core-1_0.html#StandardClaims"
     * >OpenID Connect Core 1.0, 5.1. Standard Claims</a> for further details
     * about the format.
     * </p>
     *
     * @param claims
     *         The claims of the subject in JSON format.
     *
     * @return
     *         {@code this} object.
     *
     * @see <a href="http://openid.net/specs/openid-connect-core-1_0.html#StandardClaims"
     *      >OpenID Connect Core 1.0, 5.1. Standard Claims</a>
     */
    public AuthorizationIssueRequest setClaims(String claims)
    {
        this.claims = claims;

        return this;
    }


    /**
     * Set the value of {@code "claims"} which is the claims of the subject.
     * The argument is converted into a JSON string and passed to {@link
     * #setClaims(String)} method.
     *
     * @param claims
     *         The claims of the subject. Keys are claim names.
     *
     * @return
     *         {@code this} object.
     *
     * @since 1.24
     */
    public AuthorizationIssueRequest setClaims(Map<String, Object> claims)
    {
        if (claims == null || claims.size() == 0)
        {
            this.claims = null;
            return this;
        }

        String json = Utils.toJson(claims);

        return setClaims(json);
    }


    /**
     * Get the extra properties to associate with an access token and/or
     * an authorization code which will be issued by this request.
     *
     * @return
     *         Extra properties.
     *
     * @since 1.30
     */
    public Property[] getProperties()
    {
        return properties;
    }


    /**
     * Set extra properties to associate with an access token and/or
     * an authorization code which will be issued by this request.
     *
     * <p>
     * Keys of extra properties will be used as labels of top-level
     * entries in a JSON response containing an access token which is
     * returned from an authorization server. An example is
     * {@code example_parameter}, which you can find in <a href=
     * "https://tools.ietf.org/html/rfc6749#section-5.1">5.1. Successful
     * Response</a> in RFC 6749. The following code snippet is an example
     * to set one extra property having {@code example_parameter} as its
     * key and {@code example_value} as its value.
     * </p>
     *
     * <blockquote>
     * <pre>
     * {@link Property}[] properties = { new {@link Property#Property(String, String)
     * Property}("example_parameter", "example_value") };
     * request.{@link #setProperties(Property[]) setProperties}(properties);
     * </pre>
     * </blockquote>
     *
     * <p>
     * Keys listed below should not be used and they would be ignored on
     * the server side even if they were used. It's because they are reserved
     * in <a href="https://tools.ietf.org/html/rfc6749">RFC 6749</a> and
     * <a href="http://openid.net/specs/openid-connect-core-1_0.html"
     * >OpenID Connect Core 1.0</a>.
     * </p>
     *
     * <ul>
     *   <li>{@code access_token}
     *   <li>{@code token_type}
     *   <li>{@code expires_in}
     *   <li>{@code refresh_token}
     *   <li>{@code scope}
     *   <li>{@code error}
     *   <li>{@code error_description}
     *   <li>{@code error_uri}
     *   <li>{@code id_token}
     * </ul>
     *
     * <p>
     * Note that <b>there is an upper limit on the total size of extra properties</b>.
     * On the server side, the properties will be (1) converted to a multidimensional
     * string array, (2) converted to JSON, (3) encrypted by AES/CBC/PKCS5Padding, (4)
     * encoded by base64url, and then stored into the database. The length of the
     * resultant string must not exceed 65,535 in bytes. This is the upper limit, but
     * we think it is big enough.
     * </p>
     *
     * @param properties
     *         Extra properties.
     *
     * @return
     *         {@code this} object.
     *
     * @since 1.30
     */
    public AuthorizationIssueRequest setProperties(Property[] properties)
    {
        this.properties = properties;

        return this;
    }


    /**
     * Get scopes to associate with an authorization code and/or an access token.
     * If this method returns a non-null value, the set of scopes will be used
     * instead of the scopes specified in the original authorization request.
     *
     * @return
     *         Scopes to replace the scopes specified in the original authorization
     *         request. When {@code null} is returned from this method, replacement
     *         is not performed.
     *
     * @since 1.34
     */
    public String[] getScopes()
    {
        return scopes;
    }


    /**
     * Set scopes to associate with an authorization code and/or an access token.
     * If {@code null} (the default value) is set, the scopes specified in the
     * original authorization request from the client application are used. In
     * other cases, including the case of an empty array, the scopes given to
     * this method will replace the original scopes contained in the original
     * request.
     * </p>
     *
     * <p>
     * Even scopes that are not included in the original authorization request
     * can be specified. However, as an exception, <code>"openid"</code> scope
     * is ignored on the server side if it is not included in the original
     * request (to be exact, if <code>"openid"</code> was not included in the
     * {@code parameters} request parameter of /api/auth/authorization API call).
     * It is because the existence of <code>"openid"</code> scope considerably
     * changes the validation steps and because adding <code>"openid"</code>
     * triggers generation of an ID token (although the client application has
     * not requested it) and the behavior is a major violation against the
     * specification.
     * </p>
     *
     * <p>
     * If you add <code>"offline_access"</code> scope although it is not
     * included in the original request, keep in mind that the specification
     * requires explicit consent from the user for the scope (<a href=
     * "http://openid.net/specs/openid-connect-core-1_0.html#OfflineAccess"
     * >OpenID Connect Core 1.0, 11. Offline Access</a>). When
     * <code>"offline_access"</code> is included in the original request, the
     * current implementation of Authlete's /api/auth/authorization API checks
     * whether the request has come along with <code>prompt</code> request
     * parameter and the value includes <code>"consent"</code>. However, note
     * that the implementation of Authlete's /api/auth/authorization/issue API
     * does not perform such checking if <code>"offline_access"</code> scope
     * is added via this <code>scopes</code> parameter.
     * </p>
     *
     * <table border="1" cellpadding="5" style="border-collapse: collapse;">
     *   <tr>
     *     <th>Value</th>
     *     <th>Effect</th>
     *   </tr>
     *   <tr>
     *     <td>{@code null}</td>
     *     <td>
     *       The scopes contained in the original authorization request are used.
     *     </td>
     *   </tr>
     *   <tr>
     *     <td>An empty array</td>
     *     <td>
     *       No scopes are associated with an authorization code and/or an access token.
     *       The scopes contained in the original authorization request are not used.
     *     </td>
     *   </tr>
     *   <tr>
     *     <td>A non-empty array of scope names</td>
     *     <td>
     *       Scopes listed in the array are associated with an authorization code
     *       and/or an access token.
     *     </td>
     *   </tr>
     * </table>
     *
     * @param scopes
     *         Scopes to associate with an authorization code and/or an access
     *         token. If a non-null value is set, the original scopes requested
     *         by the client application are replaced.
     *
     * @return
     *         {@code this} object.
     *
     * @since 1.34
     */
    public AuthorizationIssueRequest setScopes(String[] scopes)
    {
        this.scopes = scopes;

        return this;
    }


    /**
     * Get JSON that represents additional JWS header parameters for ID tokens
     * that may be issued based on the authorization request.
     *
     * @return
     *         JSON that represents additional JWS header parameters for ID tokens.
     *
     * @since 2.76
     */
    public String getIdtHeaderParams()
    {
        return idtHeaderParams;
    }


    /**
     * Set JSON that represents additional JWS header parameters for ID tokens
     * that may be issued based on the authorization request.
     *
     * @param params
     *         JSON that represents additional JWS header parameters for ID tokens.
     *
     * @return
     *         {@code this} object.
     *
     * @since 2.76
     */
    public AuthorizationIssueRequest setIdtHeaderParams(String params)
    {
        this.idtHeaderParams = params;

        return this;
    }


    /**
     * Get the authorization details. This represents the value of the
     * {@code "authorization_details"} request parameter which is defined in
     * <i>"OAuth 2.0 Rich Authorization Requests"</i>. If this parameter is
     * set, it overrides the parameter in the original request.
     *
     * @return
     *         Authorization details.
     *
     * @since 2.99
     */
    public AuthzDetails getAuthorizationDetails()
    {
        return authorizationDetails;
    }


    /**
     * Set the authorization details. This represents the value of the
     * {@code "authorization_details"} request parameter which is defined in
     * <i>"OAuth 2.0 Rich Authorization Requests"</i>. If this parameter is
     * set, it overrides the parameter in the original request.
     *
     * @param authorizationDetails
     *         Authorization details.
     *
     * @return
     *         {@code this} object.
     *
     * @since 2.99
     */
    public AuthorizationIssueRequest setAuthorizationDetails(AuthzDetails authorizationDetails)
    {
        this.authorizationDetails = authorizationDetails;

        return this;
    }
}
