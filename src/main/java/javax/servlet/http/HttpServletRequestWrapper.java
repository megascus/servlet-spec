/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 *
 *
 * This file incorporates work covered by the following copyright and
 * permission notice:
 *
 * Copyright 2004 The Apache Software Foundation
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

package javax.servlet.http;

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestWrapper;

/**
 * サーブレットにリクエストを適合させたい開発者がサブクラス化できるHttpServletRequestインターフェースの便利な実装を提供します。
 * 
 * <p>このクラスはWrapperもしくはDecoratorパターンを実装します。
 * メソッドはデフォルトでラップされたリクエストオブジェクトを呼び出します。
 * 
 * @see javax.servlet.http.HttpServletRequest
 * @since Servlet 2.3
 */


public class HttpServletRequestWrapper extends ServletRequestWrapper implements HttpServletRequest {

    /** 
     * 与えられたリクエストをラップしてリクエストオブジェクトを生成します。
     * 
     * @throws java.lang.IllegalArgumentException requestがnull
     
     * @param request ラップされる{@link HttpServletRequest}
     */
    public HttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }
    
    private HttpServletRequest _getHttpServletRequest() {
        return (HttpServletRequest) super.getRequest();
    }

    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetAuthType()を返すことです。
     */
    @Override
    public String getAuthType() {
        return this._getHttpServletRequest().getAuthType();
    }
   
    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetCookies()を返すことです。
     */
    @Override
    public Cookie[] getCookies() {
        return this._getHttpServletRequest().getCookies();
    }

    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetDateHeader(String name)を返すことです。
     */
    @Override
    public long getDateHeader(String name) {
        return this._getHttpServletRequest().getDateHeader(name);
    }
                
    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetHeader(String name)を返すことです。
     */
    @Override
    public String getHeader(String name) {
        return this._getHttpServletRequest().getHeader(name);
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetHeaders(String name)を返すことです。
     */
    @Override
    public Enumeration<String> getHeaders(String name) {
        return this._getHttpServletRequest().getHeaders(name);
    }  

    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetHeaderNames()を返すことです。
     */
    @Override
    public Enumeration<String> getHeaderNames() {
        return this._getHttpServletRequest().getHeaderNames();
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトの getIntHeader(String name)を返すことです。
     */
    @Override
     public int getIntHeader(String name) {
        return this._getHttpServletRequest().getIntHeader(name);
    }

    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetServletMapping()を返すことです。
     */
     @Override
     public HttpServletMapping getHttpServletMapping() {
        return this._getHttpServletRequest().getHttpServletMapping();
    }

    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetMethod()を返すことです。
     */
    @Override
    public String getMethod() {
        return this._getHttpServletRequest().getMethod();
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetPathInfo()を返すことです。
     */
    @Override
    public String getPathInfo() {
        return this._getHttpServletRequest().getPathInfo();
    }

    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetPathTranslated()を返すことです。
     */
    @Override
    public String getPathTranslated() {
        return this._getHttpServletRequest().getPathTranslated();
    }

    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetContextPath()を返すことです。
     */
    @Override
    public String getContextPath() {
        return this._getHttpServletRequest().getContextPath();
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetQueryString()を返すことです。
     */
    @Override
    public String getQueryString() {
        return this._getHttpServletRequest().getQueryString();
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetRemoteUser()を返すことです。
     */
    @Override
    public String getRemoteUser() {
        return this._getHttpServletRequest().getRemoteUser();
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのisUserInRole(String role)を返すことです。
     */
    @Override
    public boolean isUserInRole(String role) {
        return this._getHttpServletRequest().isUserInRole(role);
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetUserPrincipal()を返すことです。
     */
    @Override
    public java.security.Principal getUserPrincipal() {
        return this._getHttpServletRequest().getUserPrincipal();
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetRequestedSessionId()を返すことです。
     */
    @Override
    public String getRequestedSessionId() {
        return this._getHttpServletRequest().getRequestedSessionId();
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetRequestURI()を返すことです。
     */
    @Override
    public String getRequestURI() {
        return this._getHttpServletRequest().getRequestURI();
    }

    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetRequestURL()を返すことです。
     */
    @Override
    public StringBuffer getRequestURL() {
        return this._getHttpServletRequest().getRequestURL();
    }
        
    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetServletPath()を返すことです。
     */
    @Override
    public String getServletPath() {
        return this._getHttpServletRequest().getServletPath();
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetSession(boolean create)を返すことです。
     */
    @Override
    public HttpSession getSession(boolean create) {
        return this._getHttpServletRequest().getSession(create);
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetSession()を返すことです。
     */
    @Override
    public HttpSession getSession() {
        return this._getHttpServletRequest().getSession();
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのchangeSessionId()を返すことです。
     *
     * @since Servlet 3.1
     */
    @Override
    public String changeSessionId() {
        return this._getHttpServletRequest().changeSessionId();
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのisRequestedSessionIdValid()を返すことです。
     */ 
    @Override
    public boolean isRequestedSessionIdValid() {
        return this._getHttpServletRequest().isRequestedSessionIdValid();
    }
     
    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのisRequestedSessionIdFromCookie()を返すことです。
     */
    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return this._getHttpServletRequest().isRequestedSessionIdFromCookie();
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのisRequestedSessionIdFromURL()を返すことです。
     */ 
    @Override
    public boolean isRequestedSessionIdFromURL() {
        return this._getHttpServletRequest().isRequestedSessionIdFromURL();
    }

    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのisRequestedSessionIdFromUrl()を返すことです。
     *
     * @deprecated  Java Servlet API　の Version 4.0 からは代わりに {@link #isRequestedSessionIdFromURL} を使用してください
     */
    @Deprecated
    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return this._getHttpServletRequest().isRequestedSessionIdFromUrl();
    }

    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのauthenticateを呼び出すことです。
     *
     * @since Servlet 3.0
     */
    @Override
    public boolean authenticate(HttpServletResponse response)
            throws IOException, ServletException {
        return this._getHttpServletRequest().authenticate(response);
    }

    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのloginを呼び出すことです。
     * The default behavior of this method is to call login on the wrapped
     * request object.
     *
     * @since Servlet 3.0
     */
    @Override
    public void login(String username, String password)
            throws ServletException {
        this._getHttpServletRequest().login(username,password);
    }

    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのlogoutを呼び出すことです。
     *
     * @since Servlet 3.0
     */
    @Override
    public void logout() throws ServletException {
        this._getHttpServletRequest().logout();
    }

    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetPartsを呼び出すことです。
     *
     * <p>返された<code>Collection</code>へのいかなる変更もこの<code>HttpServletRequestWrapper</code>に影響を与えてはいけません。
     *
     * @since Servlet 3.0
     */
    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        return this._getHttpServletRequest().getParts(); 
    }

    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetPartを呼び出すことです。
     *
     * @since Servlet 3.0
     */
    @Override
    public Part getPart(String name) throws IOException, ServletException {
        return this._getHttpServletRequest().getPart(name); 
    
    }

    /**
     * 指定されたクラスで<code>HttpUpgradeHandler</code>のインスタンスを作成し、httpプロトコルのアップグレードプロセスに使用します。
     *
     * @since Servlet 3.1
     */
    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass)
            throws IOException, ServletException {
        return this._getHttpServletRequest().upgrade(handlerClass);
    }

    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのnewPushBuilderを呼び出すことです。
     *
     * @since Servlet 4.0
     */
    @Override
    public PushBuilder newPushBuilder() {
        return this._getHttpServletRequest().newPushBuilder();
    }

    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetTrailerFieldsを呼び出すことです。
     *
     * @since Servlet 4.0
     */
    @Override
    public Map<String, String> getTrailerFields() {
        return this._getHttpServletRequest().getTrailerFields();
    }

    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのisTrailerFieldsReadyを呼び出すことです。
     *
     * @since Servlet 4.0
     */
    @Override
    public boolean isTrailerFieldsReady() {
        return this._getHttpServletRequest().isTrailerFieldsReady();
    }
}
