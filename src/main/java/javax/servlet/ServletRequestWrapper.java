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

package javax.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * サーブレットにリクエストを適合させたい開発者がサブクラス化できるServletRequestインタフェースの便利な実装を提供します。
 * このクラスはWrapperもしくはDecoratorパターンを実装します。
 * メソッドはデフォルトでラップされたリクエストオブジェクトを呼び出します。
 *
 * @see javax.servlet.ServletRequest
 *
 * @since Servlet 2.3
 */

public class ServletRequestWrapper implements ServletRequest {

    private ServletRequest request;

    /**
     * 指定されたリクエストオブジェクトをラップするServletRequestアダプタを作成します。
     * @throws java.lang.IllegalArgumentException requestがnull
     *
     * @param request ラップされた{@link ServletRequest}
     */
    public ServletRequestWrapper(ServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");   
        }
        this.request = request;
    }


    /**
     * ラップされたリクエストオブジェクトを返します。
     * 
     * @return ラップされた{@link ServletRequest}
     */
    public ServletRequest getRequest() {
        return this.request;
    }


    /**
     * ラップされているリクエストオブジェクトを設定します。
     *
     * @param request 取り付けられる {@link ServletRequest}
     *
     * @throws java.lang.IllegalArgumentException requestがnull
     * 
     */
    public void setRequest(ServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        this.request = request;
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetAttribute(String name)を呼び出すことです。
     */
    public Object getAttribute(String name) {
        return this.request.getAttribute(name);
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetAttributeNames()を返すことです。
     */
    public Enumeration<String> getAttributeNames() {
        return this.request.getAttributeNames();
    }    


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetCharacterEncoding()を返すことです。
     */
    public String getCharacterEncoding() {
        return this.request.getCharacterEncoding();
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトに文字エンコーディングを設定することです。
     * The default behavior of this method is to set the character encoding
     * on the wrapped request object.
     */
    public void setCharacterEncoding(String enc)
            throws UnsupportedEncodingException {
        this.request.setCharacterEncoding(enc);
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetContentLength()を返すことです。
     */
    public int getContentLength() {
        return this.request.getContentLength();
    }

    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetContentLengthLong()を返すことです。
     *
     * @since Servlet 3.1
     */
    public long getContentLengthLong() {
        return this.request.getContentLengthLong();
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetContentType()を返すことです。
     */
    public String getContentType() {
        return this.request.getContentType();
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetInputStream()を返すことです。
     */
    public ServletInputStream getInputStream() throws IOException {
        return this.request.getInputStream();
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetParameter(String name)を返すことです。
     */
    public String getParameter(String name) {
        return this.request.getParameter(name);
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetParameterMap()を返すことです。
     */
    public Map<String, String[]> getParameterMap() {
        return this.request.getParameterMap();
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetParameterNames()を返すことです。
     */
    public Enumeration<String> getParameterNames() {
        return this.request.getParameterNames();
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetParameterValues(String name)を返すことです。
     */
    public String[] getParameterValues(String name) {
        return this.request.getParameterValues(name);
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetProtocol()を返すことです。
     */
    public String getProtocol() {
        return this.request.getProtocol();
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetScheme()を返すことです。
     */
    public String getScheme() {
        return this.request.getScheme();
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetServerName()を返すことです。
     */
    public String getServerName() {
        return this.request.getServerName();
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetServerPort()を返すことです。
     */
    public int getServerPort() {
        return this.request.getServerPort();
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetReader()を返すことです。
     */
    public BufferedReader getReader() throws IOException {
        return this.request.getReader();
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetRemoteAddr()を返すことです。
     */
    public String getRemoteAddr() {
        return this.request.getRemoteAddr();
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetRemoteHost()を返すことです。
     */
    public String getRemoteHost() {
        return this.request.getRemoteHost();
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのsetAttribute(String name, Object o)を返すことです。
     */
    public void setAttribute(String name, Object o) {
        this.request.setAttribute(name, o);
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのremoveAttribute(String name)を呼び出すことです。
     * The default behavior of this method is to call
     * removeAttribute(String name) on the wrapped request object.
     */
    public void removeAttribute(String name) {
        this.request.removeAttribute(name);
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetLocale()を返すことです。
     */
    public Locale getLocale() {
        return this.request.getLocale();
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetLocales()を返すことです。
     */
    public Enumeration<Locale> getLocales() {
        return this.request.getLocales();
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのisSecure()を返すことです。
     */
    public boolean isSecure() {
        return this.request.isSecure();
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetRequestDispatcher(String path)を返すことです。
     */
    public RequestDispatcher getRequestDispatcher(String path) {
        return this.request.getRequestDispatcher(path);
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetRealPath(String path)を返すことです。
     *
     * @deprecated Java Servlet API Version 2.1から{@link ServletContext#getRealPath}を代わりに使用してください。
     */
    @Deprecated
    public String getRealPath(String path) {
        return this.request.getRealPath(path);
    }

    
    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetRemotePort()を返すことです。
     *
     * @since Servlet 2.4
     */    
    public int getRemotePort(){
        return this.request.getRemotePort();
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetLocalName()を返すことです。
     *
     * @since Servlet 2.4
     */
    public String getLocalName(){
        return this.request.getLocalName();
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetLocalAddr()を返すことです。
     *
     * @since Servlet 2.4
     */       
    public String getLocalAddr(){
        return this.request.getLocalAddr();
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトのgetLocalPort()を返すことです。
     *
     * @since Servlet 2.4
     */
    public int getLocalPort(){
        return this.request.getLocalPort();
    }


    /**
     * Gets the servlet context to which the wrapped servlet request was last
     * dispatched.
     *
     * @return the servlet context to which the wrapped servlet request was
     * last dispatched
     *
     * @since Servlet 3.0
     */
    public ServletContext getServletContext() {
        return request.getServletContext();
    }


    /**
     * The default behavior of this method is to invoke
     * {@link ServletRequest#startAsync} on the wrapped request object.
     *
     * @return the (re)initialized AsyncContext
     * 
     * @throws IllegalStateException if the request is within the scope of
     * a filter or servlet that does not support asynchronous operations
     * (that is, {@link #isAsyncSupported} returns false),
     * or if this method is called again without any asynchronous dispatch
     * (resulting from one of the {@link AsyncContext#dispatch} methods),
     * is called outside the scope of any such dispatch, or is called again
     * within the scope of the same dispatch, or if the response has
     * already been closed
     *
     * @see ServletRequest#startAsync
     *
     * @since Servlet 3.0
     */
    public AsyncContext startAsync() throws IllegalStateException {
        return request.startAsync();
    }
    

    /**
     * The default behavior of this method is to invoke
     * {@link ServletRequest#startAsync(ServletRequest, ServletResponse)}
     * on the wrapped request object.
     *
     * @param servletRequest the ServletRequest used to initialize the
     * AsyncContext
     * @param servletResponse the ServletResponse used to initialize the
     * AsyncContext
     *
     * @return the (re)initialized AsyncContext
     *
     * @throws IllegalStateException if the request is within the scope of
     * a filter or servlet that does not support asynchronous operations
     * (that is, {@link #isAsyncSupported} returns false),
     * or if this method is called again without any asynchronous dispatch
     * (resulting from one of the {@link AsyncContext#dispatch} methods),
     * is called outside the scope of any such dispatch, or is called again
     * within the scope of the same dispatch, or if the response has
     * already been closed
     *
     * @see ServletRequest#startAsync(ServletRequest, ServletResponse)
     *
     * @since Servlet 3.0
     */
    public AsyncContext startAsync(ServletRequest servletRequest,
                                   ServletResponse servletResponse)
            throws IllegalStateException {
        return request.startAsync(servletRequest, servletResponse);
    }


    /**
     * Checks if the wrapped request has been put into asynchronous mode.
     *
     * @return true if this request has been put into asynchronous mode,
     * false otherwise
     *
     * @see ServletRequest#isAsyncStarted
     *
     * @since Servlet 3.0
     */
    public boolean isAsyncStarted() {
        return request.isAsyncStarted();
    }


    /**
     * Checks if the wrapped request supports asynchronous operation.
     *
     * @return true if this request supports asynchronous operation, false
     * otherwise
     *
     * @see ServletRequest#isAsyncSupported
     *
     * @since Servlet 3.0
     */
    public boolean isAsyncSupported() {
        return request.isAsyncSupported();
    }


    /**
     * Gets the AsyncContext that was created or reinitialized by the
     * most recent invocation of {@link #startAsync} or
     * {@link #startAsync(ServletRequest,ServletResponse)} on the wrapped
     * request.
     *
     * @return the AsyncContext that was created or reinitialized by the
     * most recent invocation of {@link #startAsync} or
     * {@link #startAsync(ServletRequest,ServletResponse)} on
     * the wrapped request 
     *
     * @throws IllegalStateException if this request has not been put 
     * into asynchronous mode, i.e., if neither {@link #startAsync} nor
     * {@link #startAsync(ServletRequest,ServletResponse)} has been called
     *
     * @see ServletRequest#getAsyncContext
     *
     * @since Servlet 3.0
     */
    public AsyncContext getAsyncContext() {
        return request.getAsyncContext();
    }


    /**
     * Checks (recursively) if this ServletRequestWrapper wraps the given
     * {@link ServletRequest} instance.
     *
     * @param wrapped the ServletRequest instance to search for
     *
     * @return true if this ServletRequestWrapper wraps the
     * given ServletRequest instance, false otherwise
     *
     * @since Servlet 3.0
     */
    public boolean isWrapperFor(ServletRequest wrapped) {
        if (request == wrapped) {
            return true;
        } else if (request instanceof ServletRequestWrapper) {
            return ((ServletRequestWrapper) request).isWrapperFor(wrapped);
        } else {
            return false;
        }
    }


    /**
     * Checks (recursively) if this ServletRequestWrapper wraps a
     * {@link ServletRequest} of the given class type.
     *
     * @param wrappedType the ServletRequest class type to
     * search for
     *
     * @return true if this ServletRequestWrapper wraps a
     * ServletRequest of the given class type, false otherwise
     *
     * @throws IllegalArgumentException if the given class does not
     * implement {@link ServletRequest}
     *
     * @since Servlet 3.0
     */
    public boolean isWrapperFor(Class<?> wrappedType) {
        if (!ServletRequest.class.isAssignableFrom(wrappedType)) {
            throw new IllegalArgumentException("Given class " +
                wrappedType.getName() + " not a subinterface of " +
                ServletRequest.class.getName());
        }
        if (wrappedType.isAssignableFrom(request.getClass())) {
            return true;
        } else if (request instanceof ServletRequestWrapper) {
            return ((ServletRequestWrapper) request).isWrapperFor(wrappedType);
        } else {
            return false;
        }
    }


    /**
     * Gets the dispatcher type of the wrapped request.
     *
     * @return the dispatcher type of the wrapped request
     * 
     * @see ServletRequest#getDispatcherType
     *
     * @since Servlet 3.0
     */
    public DispatcherType getDispatcherType() {
        return request.getDispatcherType();
    }


}

