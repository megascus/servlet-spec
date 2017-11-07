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
 * サーブレットにリクエストを適合させたい開発者がサブクラス化できるServletRequestインターフェースの便利な実装を提供します。
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
     * @param request ラップされる{@link ServletRequest}
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
     * ラップされたサーブレットリクエストが最後にディスパッチされたサーブレットコンテキストを取得します。
     *
     * @return ラップされたサーブレットリクエストが最後にディスパッチされたサーブレットコンテキスト
     *
     * @since Servlet 3.0
     */
    public ServletContext getServletContext() {
        return request.getServletContext();
    }


    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトの{@link ServletRequest#startAsync}を実行することです。
     *
     * @return (再)初期化されたAsyncContext
     * 
     * @throws IllegalStateException このリクエストが非同期操作をサポートしないフィルタまたはサーブレットのスコープ内にある場合({@link #isAsyncSupported}がfalseを返す場合)、
     * またはこのメソッドが非同期ディスパッチ({@link AsyncContext#dispatch}メソッドの一つで行われる)なしで再度呼び出されディスパッチの範囲外で呼び出された場合、
     * または同じディスパッチの範囲内で再度呼び出された場合、またはレスポンスがすでに閉じられている場合
     *
     * @see ServletRequest#startAsync
     *
     * @since Servlet 3.0
     */
    public AsyncContext startAsync() throws IllegalStateException {
        return request.startAsync();
    }
    

    /**
     * このメソッドのデフォルトの動作はラップされたリクエストオブジェクトの{@link ServletRequest#startAsync(ServletRequest, ServletResponse)}を実行することです。
     *
     * @param servletRequest AsyncContextの初期化に使用するServletRequest
     * @param servletResponse AsyncContextの初期化に使用するServletResponse
     *
     * @return (再)初期化されたAsyncContext
     *
     * @throws IllegalStateException このリクエストが非同期操作をサポートしないフィルタまたはサーブレットのスコープ内にある場合({@link #isAsyncSupported}がfalseを返す場合)、
     * またはこのメソッドが非同期ディスパッチ({@link AsyncContext#dispatch}メソッドの一つで行われる)なしで再度呼び出されディスパッチの範囲外で呼び出された場合、
     * または同じディスパッチの範囲内で再度呼び出された場合、またはレスポンスがすでに閉じられている場合
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
     * ラップされたリクエストが非同期モードになっているかどうかをチェックします。
     *
     * @return ラップされたリクエストが非同期モードになっているならtrue、そうでないならfalse
     *
     * @see ServletRequest#isAsyncStarted
     *
     * @since Servlet 3.0
     */
    public boolean isAsyncStarted() {
        return request.isAsyncStarted();
    }


    /**
     * ラップされたリクエストが非同期操作をサポートしているかどうかをチェックします。
     *
     * @return ラップされたリクエストが非同期操作をサポートしているならtrue、そうでないならfalse
     *
     * @see ServletRequest#isAsyncSupported
     *
     * @since Servlet 3.0
     */
    public boolean isAsyncSupported() {
        return request.isAsyncSupported();
    }


    /**
     * ラップされたリクエストで{@link #startAsync}または{@link #startAsync(ServletRequest,ServletResponse)}の最新の呼び出しによって作成または再初期化されたAsyncContextを取得します。
     *
     * @return ラップされたリクエストで{@link #startAsync}または{@link #startAsync(ServletRequest,ServletResponse)}の最新の呼び出しによって作成または再初期化されたAsyncContext
     *
     * @throws IllegalStateException このリクエストが非同期モードになっていない場合、つまり{@link #startAsync}も{@link #startAsync(ServletRequest,ServletResponse)}も呼び出されていない場合
     *
     * @see ServletRequest#getAsyncContext
     *
     * @since Servlet 3.0
     */
    public AsyncContext getAsyncContext() {
        return request.getAsyncContext();
    }


    /**
     * このServletRequestWrapperが与えられた{@link ServletRequest}のインスタンスをラップしているかどうかを(再帰的に)チェックします。
     *
     * @param wrapped 探すServletRequestのインスタンス
     *
     * @return 与えられたServletRequestのインスタンスをServletRequestWrapperがラップしているならtrue、そうでないならfalse
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
     * このServletRequestWrapperが与えられたClassの型の{@link ServletRequest}をラップしているかどうかを(再帰的に)チェックします。
     *
     * @param wrappedType 探すServletRequestのclassの型
     *
     * @return 与えられたClassの型の{@link ServletRequest}をServletRequestWrapperがラップしているならtrue、そうでないならfalse
     *
     * @throws IllegalArgumentException 与えられたclassが{@link ServletRequest}を実装していない場合
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
     * ラップされたリクエストのdispatcher typeを取得します。
     *
     * @return ラップされたリクエストのdispatcher type
     * 
     * @see ServletRequest#getDispatcherType
     *
     * @since Servlet 3.0
     */
    public DispatcherType getDispatcherType() {
        return request.getDispatcherType();
    }


}

