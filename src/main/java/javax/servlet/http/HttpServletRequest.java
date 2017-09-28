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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;

/**
 * {@link javax.servlet.ServletRequest}を拡張してHTTPサーブレットのためのリクエストの情報を提供します。
 *
 * <p>サーブレットコンテナは<code>HttpServletRequest</code>を作成してサーブレットの<code>service</code>(<code>doGet</code>、<code>doPost</code>、など)メソッドの引数として渡します。
 *
 *
 * @author 	Various
 */

public interface HttpServletRequest extends ServletRequest {

    /**
     * ベーシック認証のための識別文字列です。
     * "BASIC"
     */
    public static final String BASIC_AUTH = "BASIC";

    /**
     * フォーム認証のための識別文字列です。
     * "FORM"
     */
    public static final String FORM_AUTH = "FORM";

    /**
     * クライアント証明書認証のための識別文字列です。
     * "CLIENT_CERT"
     */
    public static final String CLIENT_CERT_AUTH = "CLIENT_CERT";

    /**
     * ダイジェスト認証のための識別文字列です。
     * "DIGEST"
     */
    public static final String DIGEST_AUTH = "DIGEST";

    /**
     * サーブレットを保護するために使用される認証方式の名前を返します。
     * すべてのサーブレットコンテナは、ベーシック認証、フォーム認証およびクライアントの証明書認証をサポートしており、さらにダイジェスト認証をサポートしているかもしれません。
     * サーブレットが認証されていない場合は<code>null</code>を返します。
     *
     * <p>CGIの変数<code>AUTH_TYPE</code>の値と同じです。
     * 
     * <p>訳注：CGIはサーブレットが生まれる前にあったウェブアプリケーションを作るための仕組みです。現在はほぼ使われていません。
     *
     * @return		静的メンバー変数BASIC_AUTH、FORM_AUTH、CLIENT_CERT_AUTH、DIGEST_AUTH(==で比較もできる)のうちの一つ、
     * または認証スキームを示すコンテナ固有の文字列のいずれか、リクエストが認証されなかった場合はnull
     */
    public String getAuthType();

    /**
     * このリクエストでクライアントが送信したすべての<code>Cookie</code>オブジェクトを含む配列を返します。 このメソッドは、送信されたCookieがない場合は<code>null</code>を返します。
     *
     * @return		このリクエストでクライアントが送信したすべての<code>Cookie</code>オブジェクトを含む配列、送信されたCookieがない場合は<code>null</code>
     */
    public Cookie[] getCookies();

    /**
     * リクエストヘッダーの指定された値の<code>Date</code>オブジェクトを表す<code>long</code> 値を返します。
     * このメソッドは<code>If-Modified-Since</code>のような日付を含むヘッダーに使用します。
     * 
     * <p>返される日付は1970 年 1 月 1 日 00:00:00 GMT からのミリ秒数です。
     * ヘッダー名は大文字小文字を区別しません。
     *
     * <p>リクエストのヘッダーに指定された名前が存在しない場合はこのメソッドは-1を返します。
     * もしヘッダーを日付に変換できない場合、このメソッドは<code>IllegalArgumentException</code>を投げます。
     *
     * @param name		ヘッダーの名前を指定する<code>String</code>
     *
     * @return			指定されたヘッダーの日付の1970 年 1 月 1 日 00:00:00 GMT からのミリ秒数を表す<code>long</code>値、リクエストのヘッダーに名前が含まれない場合は-1
     *
     * @exception	IllegalArgumentException	ヘッダーの値を日付に変換できなかった
     */
    public long getDateHeader(String name);

    /**
     * リクエストヘッダーの指定された値を<code>String</code>として返します。
     * リクエストが指定された名前のヘッダーを含まない場合は<code>null</code>を返します。
     * もしヘッダーに同じ名前で複数の値が含まれていた場合、このメソッドはリクエストの最初のヘッダーを返します。
     * ヘッダー名は大文字小文字を区別しません。
     * このメソッドは任意のリクエストヘッダーに対して使用できます。
     *
     * @param name		ヘッダー名を指定する名前の<code>String</code>
     *
     * @return			リクエストのヘッダーの値を含む<code>String</code>、リクエストのヘッダーがその名前を持たない場合は<code>null</code>
     */
    public String getHeader(String name);

    /**
     * リクエストヘッダーの指定された値を<code>String</code>オブジェクトの<code>Enumeration</code>として返します。
     * 
     * <p><code>Accept-Language</code>のようないくつかのヘッダーはクライアントからカンマで区切られたリストとして送信されるのではなく、異なる値を持つ複数のヘッダーとして送信されます。
     *
     * <p>リクエストに指定した名前のヘッダーが含まれない場合はこのメソッドは空の<code>Enumeration</code>を返します。
     * ヘッダー名は大文字小文字を区別しません。
     * このメソッドは任意のリクエストヘッダーに対して使用できます。
     *
     * @param name		ヘッダーの名前を示す <code>String</code>
     *
     * @return			要求されたヘッダーの値を含むEnumeration、リクエストにその名前のヘッダーがない場合は空のEnumeration、コンテナがヘッダー情報へのアクセスを許可しない場合はnull
     */
    public Enumeration<String> getHeaders(String name);

    /**
     * リクエストに含まれるすべてのヘッダーの名前のEnumerationを返します。
     * リクエストにヘッダーがない場合はこのメソッドは空のEnumerationを返します。
     *
     * <p>サーブレットコンテナによってはサーブレットはこのメソッドを使用してヘッダーにアクセスすることを許されていません。その場合このメソッドは<code>null</code>を返します。
     *
     * @return			このリクエストで送られたすべてのヘッダ名のEnumeration、リクエストにヘッダーがない場合は空のEnumeration、サーブレットコンテナがサーブレットでこのメソッドの使用を許可しない場合はnull
     */
    public Enumeration<String> getHeaderNames();

    /**
     * リクエストヘッダーの指定された値を<code>int</code>として返します。
     * p>リクエストのヘッダーに指定された名前が存在しない場合はこのメソッドは-1を返します。
     * ヘッダーを整数に変換できない場合、このメソッドは<code>NumberFormatException</code>を投げます。
     *
     * <p>ヘッダー名は大文字小文字を区別しません。
     *
     * @param name		ヘッダーの名前を示す <code>String</code>
     *
     * @return		    リクエストヘッダーの値を表す整数、リクエストのヘッダーに名前が含まれない場合は-1
     *
     * @exception	NumberFormatException		ヘッダーの値を<code>int</code>に変換できなかった
     */
    public int getIntHeader(String name);

    /**
     * <p>この{@code HttpServletRequest}が呼び出された{@link HttpServlet}の{@link HttpServletMapping}を返します。
     * 該当する{@link javax.servlet.Filter}のマッピングは結果に表示されません。
     * 現在のアクティブな{@link javax.servlet.Servlet}呼び出しが、{@link ServletRequest#getRequestDispatcher}後の{@link RequestDispatcher#forward}の呼び出しによって行われた場合、
     * 返される{@code HttpServletMapping}は{@link RequestDispatcher}を取得するために使用されたパスに対応します。
     * 現在のアクティブな{@link javax.servlet.Servlet}呼び出しが、{@link ServletRequest#getRequestDispatcher}後の{@link RequestDispatcher#include}の呼び出しによって行われた場合、
     * 返される{@code HttpServletMapping}は呼び出しシーケンスの最初の{@link Servlet}を呼び出す原因となったパスに対応します。
     * 現在のアクティブな{@link javax.servlet.Servlet}呼び出しが、{@link javax.servlet.AsyncContext#dispatch}の呼び出しによって行われた場合、
     * 返される{@code HttpServletMapping}は呼び出しシーケンスの最初の{@link Servlet}を呼び出す原因となったパスに対応します。
     * {@code HttpServletMapping}に関連する追加のリクエストの属性については、{@link javax.servlet.RequestDispatcher#FORWARD_MAPPING}、
     * {@link javax.servlet.RequestDispatcher#INCLUDE_MAPPING}、{@link javax.servlet.AsyncContext#ASYNC_MAPPING}を参照してください。
     * 現在のアクティブな{@link javax.servlet.Servlet}呼び出しが、{@link javax.servlet.ServletContext#getNamedDispatcher}の呼び出しによって行われた場合、
     * 返された{@code HttpServletMapping}はこのリクエストに最後に適用されたマッピングのパスに対応するものです。</p>
     * 
     * <p>返されるオブジェクトは不変です。 Servlet 4.0準拠の実装では、このメソッドをオーバーライドする必要があります。</p>
     * 
     * @implSpec デフォルトの実装ではMatchValue、PatternおよびServletNameとしての空の文字列を返し、MappingMatchとしてnullを返す{@code HttpServletMapping}が返されます。
     *
     * @return 現在のリクエストが呼び出された方法を示す{@code HttpServletMapping}のインスタンス
     * 
     * @since 4.0
     */
    
    default public HttpServletMapping getHttpServletMapping() {
        return new HttpServletMapping() {
            @Override
            public String getMatchValue() {
                return "";
            }

            @Override
            public String getPattern() {
                return "";
            }

            @Override
            public String getServletName() {
                return "";
            }

            @Override
            public MappingMatch getMappingMatch() {
               return null;
            }

            @Override
            public String toString() {
                return "MappingImpl{" + "matchValue=" + getMatchValue()
                        + ", pattern=" + getPattern() + ", servletName=" 
                        + getServletName() + ", mappingMatch=" + getMappingMatch() 
                        + "} HttpServletRequest {" + HttpServletRequest.this.toString()
                        + '}';
            }
            
            
            
        };
    }
    
    /**
     * リクエストが行われたHTTPのメソッドの名前を返します。たとえば、GETやPOST、PUTです。
     * CGIの変数REQUEST_METHODの値と同じです。
     * 
     * <p>訳注：CGIはサーブレットが生まれる前にあったウェブアプリケーションを作るための仕組みです。現在はほぼ使われていません。
     *
     * @return			リクエストが行われたメソッドの名前を示す<code>String</code>
     */
    public String getMethod();

    /**
     * Returns any extra path information associated with
     * the URL the client sent when it made this request.
     * The extra path information follows the servlet path
     * but precedes the query string and will start with
     * a "/" character.
     *
     * <p>This method returns <code>null</code> if there
     * was no extra path information.
     *
     * <p>Same as the value of the CGI variable PATH_INFO.
     *
     * @return		a <code>String</code>, decoded by the
     *			web container, specifying
     *			extra path information that comes
     *			after the servlet path but before
     *			the query string in the request URL;
     *			or <code>null</code> if the URL does not have
     *			any extra path information
     */
    public String getPathInfo();

    /**
     * Returns any extra path information after the servlet name
     * but before the query string, and translates it to a real
     * path. Same as the value of the CGI variable PATH_TRANSLATED.
     *
     * <p>If the URL does not have any extra path information,
     * this method returns <code>null</code> or the servlet container
     * cannot translate the virtual path to a real path for any reason
     * (such as when the web application is executed from an archive).
     *
     * The web container does not decode this string.
     *
     * @return		a <code>String</code> specifying the
     *			real path, or <code>null</code> if
     *			the URL does not have any extra path
     *			information
     */
    public String getPathTranslated();

    /**
     * Instantiates a new instance of {@link PushBuilder} for issuing server
     * push responses from the current request. This method returns null
     * if the current connection does not support server push, or server
     * push has been disabled by the client via a
     * {@code SETTINGS_ENABLE_PUSH} settings frame value of {@code 0} (zero).
     *
     * @implSpec
     * The default implementation returns null.
     *
     * @return a {@link PushBuilder} for issuing server push responses
     * from the current request, or null if push is not supported
     *
     * @since Servlet 4.0
     */
     default public PushBuilder newPushBuilder() {
         return null;
     }

    /**
     * Returns the portion of the request URI that indicates the context
     * of the request. The context path always comes first in a request
     * URI. The path starts with a "/" character but does not end with a "/"
     * character. For servlets in the default (root) context, this method
     * returns "". The container does not decode this string.
     *
     * <p>It is possible that a servlet container may match a context by
     * more than one context path. In such cases this method will return the
     * actual context path used by the request and it may differ from the
     * path returned by the
     * {@link javax.servlet.ServletContext#getContextPath()} method.
     * The context path returned by
     * {@link javax.servlet.ServletContext#getContextPath()}
     * should be considered as the prime or preferred context path of the
     * application.
     *
     * @return		a <code>String</code> specifying the
     *			portion of the request URI that indicates the context
     *			of the request
     *
     * @see javax.servlet.ServletContext#getContextPath()
     */
    public String getContextPath();

    /**
     * Returns the query string that is contained in the request
     * URL after the path. This method returns <code>null</code>
     * if the URL does not have a query string. Same as the value
     * of the CGI variable QUERY_STRING.
     *
     * @return		a <code>String</code> containing the query
     *			string or <code>null</code> if the URL
     *			contains no query string. The value is not
     *			decoded by the container.
     */
    public String getQueryString();

    /**
     * Returns the login of the user making this request, if the
     * user has been authenticated, or <code>null</code> if the user
     * has not been authenticated.
     * Whether the user name is sent with each subsequent request
     * depends on the browser and type of authentication. Same as the
     * value of the CGI variable REMOTE_USER.
     *
     * @return		a <code>String</code> specifying the login
     *			of the user making this request, or <code>null</code>
     *			if the user login is not known
     */
    public String getRemoteUser();

    /**
     * Returns a boolean indicating whether the authenticated user is included
     * in the specified logical "role".  Roles and role membership can be
     * defined using deployment descriptors.  If the user has not been
     * authenticated, the method returns <code>false</code>.
     *
     * <p>The role name "*" should never be used as an argument in calling
     * <code>isUserInRole</code>. Any call to <code>isUserInRole</code> with
     * "*" must return false.
     * If the role-name of the security-role to be tested is "**", and
     * the application has NOT declared an application security-role with
     * role-name "**", <code>isUserInRole</code> must only return true if
     * the user has been authenticated; that is, only when
     * {@link #getRemoteUser} and {@link #getUserPrincipal} would both return
     * a non-null value. Otherwise, the container must check
     * the user for membership in the application role.
     *
     * @param role		a <code>String</code> specifying the name
     *				of the role
     *
     * @return		a <code>boolean</code> indicating whether
     *			the user making this request belongs to a given role;
     *			<code>false</code> if the user has not been
     *			authenticated
     */
    public boolean isUserInRole(String role);

    /**
     * Returns a <code>java.security.Principal</code> object containing
     * the name of the current authenticated user. If the user has not been
     * authenticated, the method returns <code>null</code>.
     *
     * @return		a <code>java.security.Principal</code> containing
     *			the name of the user making this request;
     *			<code>null</code> if the user has not been
     *			authenticated
     */
    public java.security.Principal getUserPrincipal();

    /**
     * Returns the session ID specified by the client. This may
     * not be the same as the ID of the current valid session
     * for this request.
     * If the client did not specify a session ID, this method returns
     * <code>null</code>.
     *
     * @return		a <code>String</code> specifying the session
     *			ID, or <code>null</code> if the request did
     *			not specify a session ID
     *
     * @see     #isRequestedSessionIdValid
     */
    public String getRequestedSessionId();

    /**
     * Returns the part of this request's URL from the protocol
     * name up to the query string in the first line of the HTTP request.
     * The web container does not decode this String.
     * For example:
     *
     * <table summary="Examples of Returned Values">
     * <tr align=left><th>First line of HTTP request      </th>
     * <th>     Returned Value</th>
     * <tr><td>POST /some/path.html HTTP/1.1<td><td>/some/path.html
     * <tr><td>GET http://foo.bar/a.html HTTP/1.0
     * <td><td>/a.html
     * <tr><td>HEAD /xyz?a=b HTTP/1.1<td><td>/xyz
     * </table>
     *
     * <p>To reconstruct an URL with a scheme and host, use
     * {@link HttpUtils#getRequestURL}.
     *
     * @return		a <code>String</code> containing
     *			the part of the URL from the
     *			protocol name up to the query string
     *
     * @see     HttpUtils#getRequestURL
     */
    public String getRequestURI();

    /**
     * Reconstructs the URL the client used to make the request.
     * The returned URL contains a protocol, server name, port
     * number, and server path, but it does not include query
     * string parameters.
     *
     * <p>If this request has been forwarded using
     * {@link javax.servlet.RequestDispatcher#forward}, the server path in the
     * reconstructed URL must reflect the path used to obtain the
     * RequestDispatcher, and not the server path specified by the client.
     *
     * <p>Because this method returns a <code>StringBuffer</code>,
     * not a string, you can modify the URL easily, for example,
     * to append query parameters.
     *
     * <p>This method is useful for creating redirect messages
     * and for reporting errors.
     *
     * @return		a <code>StringBuffer</code> object containing
     *			the reconstructed URL
     */
    public StringBuffer getRequestURL();

    /**
     * Returns the part of this request's URL that calls
     * the servlet. This path starts with a "/" character
     * and includes either the servlet name or a path to
     * the servlet, but does not include any extra path
     * information or a query string. Same as the value of
     * the CGI variable SCRIPT_NAME.
     *
     * <p>This method will return an empty string ("") if the
     * servlet used to process this request was matched using
     * the "/*" pattern.
     *
     * @return		a <code>String</code> containing
     *			the name or path of the servlet being
     *			called, as specified in the request URL,
     *			decoded, or an empty string if the servlet
     *			used to process the request is matched
     *			using the "/*" pattern.
     */
    public String getServletPath();

    /**
     * Returns the current <code>HttpSession</code>
     * associated with this request or, if there is no
     * current session and <code>create</code> is true, returns
     * a new session.
     *
     * <p>If <code>create</code> is <code>false</code>
     * and the request has no valid <code>HttpSession</code>,
     * this method returns <code>null</code>.
     *
     * <p>To make sure the session is properly maintained,
     * you must call this method before
     * the response is committed. If the container is using cookies
     * to maintain session integrity and is asked to create a new session
     * when the response is committed, an IllegalStateException is thrown.
     *
     * @param create	<code>true</code> to create
     *			a new session for this request if necessary;
     *			<code>false</code> to return <code>null</code>
     *			if there's no current session
     *
     * @return 		the <code>HttpSession</code> associated
     *			with this request or <code>null</code> if
     * 			<code>create</code> is <code>false</code>
     *			and the request has no valid session
     *
     * @see #getSession()
     */
    public HttpSession getSession(boolean create);

    /**
     * Returns the current session associated with this request,
     * or if the request does not have a session, creates one.
     *
     * @return		the <code>HttpSession</code> associated
     *			with this request
     *
     * @see	#getSession(boolean)
     */
    public HttpSession getSession();

    /**
     * Change the session id of the current session associated with this
     * request and return the new session id.
     *
     * @return the new session id
     *
     * @throws IllegalStateException if there is no session associated
     * with the request
     *
     * @since Servlet 3.1
     */
    public String changeSessionId();

    /**
     * Checks whether the requested session ID is still valid.
     *
     * <p>If the client did not specify any session ID, this method returns
     * <code>false</code>.
     *
     * @return			<code>true</code> if this
     *				request has an id for a valid session
     *				in the current session context;
     *				<code>false</code> otherwise
     *
     * @see			#getRequestedSessionId
     * @see			#getSession
     * @see			HttpSessionContext
     */
    public boolean isRequestedSessionIdValid();

    /**
     * <p>Checks whether the requested session ID was conveyed to the
     * server as an HTTP cookie.</p>
     *
     * @return			<code>true</code> if the session ID
     *				was conveyed to the server an an HTTP
     *				cookie; otherwise, <code>false</code>
     *
     * @see         #getSession
     */
    public boolean isRequestedSessionIdFromCookie();

    /**
     * <p>Checks whether the requested session ID was conveyed to the
     * server as part of the request URL.</p>
     *
     * @return <code>true</code> if the session ID was conveyed to the
     *				server as part of a URL; otherwise,
     *				<code>false</code>
     *
     * @see         #getSession
     */
    public boolean isRequestedSessionIdFromURL();

    /**
     * @deprecated		As of Version 2.1 of the Java Servlet
     *				API, use {@link #isRequestedSessionIdFromURL}
     *				instead.
     *
     * @return <code>true</code> if the session ID was conveyed to the
     *				server as part of a URL; otherwise,
     *				<code>false</code>
     */
    @Deprecated
    public boolean isRequestedSessionIdFromUrl();

    /**
     * Use the container login mechanism configured for the
     * <code>ServletContext</code> to authenticate the user making
     * this request.
     *
     * <p>This method may modify and commit the argument
     * <code>HttpServletResponse</code>.
     *
     * @param response The <code>HttpServletResponse</code>
     * associated with this <code>HttpServletRequest</code>
     *
     * @return <code>true</code> when non-null values were or have been
     * established as the values returned by <code>getUserPrincipal</code>,
     * <code>getRemoteUser</code>, and <code>getAuthType</code>. Return
     * <code>false</code> if authentication is incomplete and the underlying
     * login mechanism has committed, in the response, the message (e.g.,
     * challenge) and HTTP status code to be returned to the user.
     *
     * @throws IOException if an input or output error occurred while
     * reading from this request or writing to the given response
     *
     * @throws IllegalStateException if the login mechanism attempted to
     * modify the response and it was already committed
     *
     * @throws ServletException if the authentication failed and
     * the caller is responsible for handling the error (i.e., the
     * underlying login mechanism did NOT establish the message and
     * HTTP status code to be returned to the user)
     *
     * @since Servlet 3.0
     */
    public boolean authenticate(HttpServletResponse response)
	throws IOException,ServletException;

    /**
     * Validate the provided username and password in the password validation
     * realm used by the web container login mechanism configured for the
     * <code>ServletContext</code>.
     *
     * <p>This method returns without throwing a <code>ServletException</code>
     * when the login mechanism configured for the <code>ServletContext</code>
     * supports username password validation, and when, at the time of the
     * call to login, the identity of the caller of the request had
     * not been established (i.e, all of <code>getUserPrincipal</code>,
     * <code>getRemoteUser</code>, and <code>getAuthType</code> return null),
     * and when validation of the provided credentials is successful.
     * Otherwise, this method throws a <code>ServletException</code> as
     * described below.
     *
     * <p>When this method returns without throwing an exception, it must
     * have established non-null values as the values returned by
     * <code>getUserPrincipal</code>, <code>getRemoteUser</code>, and
     * <code>getAuthType</code>.
     *
     * @param username The <code>String</code> value corresponding to
     * the login identifier of the user.
     *
     * @param password The password <code>String</code> corresponding
     * to the identified user.
     *
     * @exception	ServletException    if the configured login mechanism
     *                                      does not support username
     *                                      password authentication, or if a
     *                                      non-null caller identity had
     *                                      already been established (prior
     *                                      to the call to login), or if
     *                                      validation of the provided
     *                                      username and password fails.
     *
     * @since Servlet 3.0
     */
    public void login(String username, String password)
	throws ServletException;

    /**
     * Establish <code>null</code> as the value returned when
     * <code>getUserPrincipal</code>, <code>getRemoteUser</code>,
     * and <code>getAuthType</code> is called on the request.
     *
     * @exception ServletException if logout fails
     *
     * @since Servlet 3.0
     */
    public void logout() throws ServletException;

    /**
     * Gets all the {@link Part} components of this request, provided
     * that it is of type <code>multipart/form-data</code>.
     *
     * <p>If this request is of type <code>multipart/form-data</code>, but
     * does not contain any <code>Part</code> components, the returned
     * <code>Collection</code> will be empty.
     *
     * <p>Any changes to the returned <code>Collection</code> must not
     * affect this <code>HttpServletRequest</code>.
     *
     * @return a (possibly empty) <code>Collection</code> of the
     * <code>Part</code> components of this request
     *
     * @throws IOException if an I/O error occurred during the retrieval
     * of the {@link Part} components of this request
     *
     * @throws ServletException if this request is not of type
     * <code>multipart/form-data</code>
     *
     * @throws IllegalStateException if the request body is larger than
     * <code>maxRequestSize</code>, or any <code>Part</code> in the
     * request is larger than <code>maxFileSize</code>, or there is no
     * <code>@MultipartConfig</code> or <code>multipart-config</code> in
     * deployment descriptors
     *
     * @see javax.servlet.annotation.MultipartConfig#maxFileSize
     * @see javax.servlet.annotation.MultipartConfig#maxRequestSize
     *
     * @since Servlet 3.0
     */
    public Collection<Part> getParts() throws IOException, ServletException;

    /**
     * Gets the {@link Part} with the given name.
     *
     * @param name the name of the requested <code>Part</code>
     *
     * @return The <code>Part</code> with the given name, or
     * <code>null</code> if this request is of type
     * <code>multipart/form-data</code>, but does not
     * contain the requested <code>Part</code>
     *
     * @throws IOException if an I/O error occurred during the retrieval
     * of the requested <code>Part</code>
     * @throws ServletException if this request is not of type
     * <code>multipart/form-data</code>
     * @throws IllegalStateException if the request body is larger than
     * <code>maxRequestSize</code>, or any <code>Part</code> in the
     * request is larger than <code>maxFileSize</code>, or there is no
     * <code>@MultipartConfig</code> or <code>multipart-config</code> in
     * deployment descriptors
     *
     * @see javax.servlet.annotation.MultipartConfig#maxFileSize
     * @see javax.servlet.annotation.MultipartConfig#maxRequestSize
     *
     * @since Servlet 3.0
     */
    public Part getPart(String name) throws IOException, ServletException;

    /**
     * Creates an instance of <code>HttpUpgradeHandler</code> for a given
     * class and uses it for the http protocol upgrade processing.
     *
     * @param <T> The {@code Class}, which extends {@link
     * HttpUpgradeHandler}, of the {@code handlerClass}.

     * @param handlerClass The <code>HttpUpgradeHandler</code> class used for the upgrade.
     *
     * @return an instance of the <code>HttpUpgradeHandler</code>
     *
     * @exception IOException if an I/O error occurred during the upgrade
     * @exception ServletException if the given <code>handlerClass</code> fails to
     * be instantiated
     *
     * @see javax.servlet.http.HttpUpgradeHandler
     * @see javax.servlet.http.WebConnection
     *
     * @since Servlet 3.1
     */
    public <T extends HttpUpgradeHandler> T  upgrade(Class<T> handlerClass)
        throws IOException, ServletException;

    /**
     * Get the request trailer fields.
     *
     * <p>The returned map is not backed by the {@code HttpServletRequest} object,
     * so changes in the returned map are not reflected in the
     * {@code HttpServletRequest} object, and vice-versa.</p>
     * 
     * <p>{@link #isTrailerFieldsReady()} should be called first to determine
     * if it is safe to call this method without causing an exception.</p>
     *
     * @implSpec
     * The default implementation returns an empty map.
     * 
     * @return A map of trailer fields in which all the keys are in lowercase,
     * regardless of the case they had at the protocol level. If there are no
     * trailer fields, yet {@link #isTrailerFieldsReady} is returning true,
     * the empty map is returned.
     *
     * @throws IllegalStateException if {@link #isTrailerFieldsReady()} is false
     *
     * @since Servlet 4.0
     */
    default public Map<String, String> getTrailerFields() {
        return Collections.emptyMap();
    }

    /**
     * Return a boolean indicating whether trailer fields are ready to read
     * using {@link #getTrailerFields}.
     *
     * This methods returns true immediately if it is known that there is no
     * trailer in the request, for instance, the underlying protocol (such
     * as HTTP 1.0) does not supports the trailer fields, or the request is
     * not in chunked encoding in HTTP 1.1.
     * And the method also returns true if both of the following conditions
     * are satisfied:
     * <ol type="a">
     *   <li> the application has read all the request data and an EOF
     *        indication has been returned from the {@link #getReader}
     *        or {@link #getInputStream}.
     *   <li> all the trailer fields sent by the client have been received.
     *        Note that it is possible that the client has sent no trailer fields.
     * </ol>
     *
     * @implSpec
     * The default implementation returns false.
     *
     * @return a boolean whether trailer fields are ready to read
     *
     * @since Servlet 4.0
     */
    default public boolean isTrailerFieldsReady() {
        return true;
    }
}
