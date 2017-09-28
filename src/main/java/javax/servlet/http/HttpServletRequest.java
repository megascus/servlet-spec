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
     * このリクエストを行ったときにクライアントが送信したURLに関連付けられた拡張パス情報を返します。
     * 拡張パス情報はサーブレットのパスの後ろからクエリ文字列の前までで"/"文字で始まります。
     *
     * <p>このメソッドは拡張パス情報が存在しない場合は<code>null</code>を返します。
     *
     * <p>CGIの変数PATH_INFOの値と同じです。
     * 
     * <p>訳注：CGIはサーブレットが生まれる前にあったウェブアプリケーションを作るための仕組みです。現在はほぼ使われていません。
     * 
     * @return		ウェブコンテナによってデコードされ、サーブレットパスの後からリクエストURLのクエリ文字列の前までの拡張パス情報を指定する<code>String</code>、URLに拡張パス情報がない場合は<code>null</code>
     */
    public String getPathInfo();

    /**
     * サーブレット名の後ろからクエリ文字列の前の拡張パス情報を実際のパスに変換したものを返します。
     * CGIの変数PATH_TRANSLATEDの値と同じです。
     * 
     * <p>URLに拡張パス情報がない場合、このメソッドはnullを返します。
     * サーブレットコンテナは何らかの理由で(ウェブアプリケーションがアーカイブから実行されたときなど)仮想パスを実際のパスに変換できない場合があります。
     * 
     * ウェブコンテナはこの文字列をデコードしません。
     * 
     * <p>訳注：CGIはサーブレットが生まれる前にあったウェブアプリケーションを作るための仕組みです。現在はほぼ使われていません。
     *
     * @return		実際のパスを示す<code>String</code>、URLに拡張パスの情報がない場合は<code>null</code>
     */
    public String getPathTranslated();

    /**
     * 現在のリクエストからサーバープッシュレスポンスを発行するための{@link PushBuilder}の新しいインスタンスを作成します。
     * 現在の接続がサーバープッシュをサポートしていないか、またはサーバープッシュが{@code SETTINGS_ENABLE_PUSH}設定フレーム値0(ゼロ)でクライアントによって無効化されている場合、このメソッドはnullを返します。
     *
     * @implSpec デフォルト実装ではnullを返します。
     *
     * @return 現在のリクエストからサーバープッシュレスポンスを発行するための{@link PushBuilder}、プッシュがサポートされていない場合はnull
     *
     * @since Servlet 4.0
     */
     default public PushBuilder newPushBuilder() {
         return null;
     }

    /**
     * リクエストURIからリクエストのコンテキストを示す部分を返します。
     * コンテキストパスは常にリクエストURIの最初に来ます。
     * パスは "/"文字で始まりますが、 "/"文字で終わらないパスです。 
     * デフォルト(ルート)コンテキストのサーブレットの場合、このメソッドは ""を返します。
     * コンテナはこの文字列をデコードしません。

     * <p>サーブレットコンテナが複数のコンテキストパスによってコンテキストに一致する可能性があります。
     * そのような場合、このメソッドはリクエストによって使用される実際のコンテキストパスを返し、
     * それは{@link javax.servlet.ServletContext#getContextPath()}メソッドによって返されるパスと異なる場合があります。
     * {@link javax.servlet.ServletContext#getContextPath()}によって返されるコンテキストパスはアプリケーションのプライマリコンテキストパスまたは優先コンテキストパスと見なす必要があります。
     *
     * @return		リクエストURIからリクエストのコンテキストを示す部分を示す <code>String</code>
     *
     * @see javax.servlet.ServletContext#getContextPath()
     */
    public String getContextPath();

    /**
     * リクエストURLからパスの後に含まれるクエリ文字列を返します。
     * このメソッドは、URLにクエリ文字列がない場合はnullを返します。
     * CGIの変数QUERY_STRINGの値と同じです。
     * 
     * <p>訳注：CGIはサーブレットが生まれる前にあったウェブアプリケーションを作るための仕組みです。現在はほぼ使われていません。
     * 
     * @return		クエリ文字列を含む<code>String</code>、URLにクエリ文字列が含まれない場合は<code>null</code>、この値はコンテナによってデコードされない
     */
    public String getQueryString();

    /**
     * ユーザーが認証されている場合にこのリクエストを行うユーザーのログイン名を返します。ユーザーが認証されていない場合はnullを返します。
     * 後続のリクエストごとにユーザー名を送信するかどうかはブラウザと認証タイプに依存します。 CGIの変数REMOTE_USERの値と同じです。
     * 
     * <p>訳注：CGIはサーブレットが生まれる前にあったウェブアプリケーションを作るための仕組みです。現在はほぼ使われていません。
     *
     * @return		このリクエストを行ったユーザーのログイン名を示す <code>String</code>、ユーザーのログイン名が不明な場合は<code>null</code>
     */
    public String getRemoteUser();

    /**
     * 認証されたユーザが指定された論理的な"ロール"に含まれているかどうかを示すbooleanを返します。
     * ロールとロールのメンバーシップはデプロイメントディスクリプタを使用して定義できます。
     * ユーザーが認証されていない場合、このメソッドは<code>false</code>を返します。
     *
     * <p>ロール名 "*"は<code>isUserInRole</code>を呼び出す時に引数として使用されるべきではありません。
     * <code>isUserInRole</code>の"*"での呼び出しはfalseを返す必要があります。
     * テスト対象のセキュリティロールのロール名が"**"でアプリケーションがロール名が「**」のアプリケーションセキュリティロールを宣言していない場合、
     * <code>isUserInRole</code>は、ユーザーが認証されている場合のみtrueを返す必要があります。つまり、{@link #getRemoteUser}と{@link #getUserPrincipal}が両方ともnull以外の値を返す場合のみです。
     * それ以外の場合、コンテナはユーザーのアプリケーションロールのメンバーシップを確認する必要があります。
     *
     * @param role		ロールの名前を示す<code>String</code>
     *
     * @return		このリクエストを行うユーザが指定されたロールに属しているかどうかを示す<code>boolean</code>、ユーザーが認証されていない場合は<code>false</code>
     */
    public boolean isUserInRole(String role);

    /**
     * 現在の認証されたユーザの名前を含む<code>java.security.Principal</code>のオブジェクトを返します。
     * ユーザーが認証されていない場合、このメソッドは<code>null</code>を返します。
     *
     * @return		このリクエストを送るユーザの名前を含む<code>java.security.Principal</code>、ユーザーが認証されていない場合は<code>null</code>
     */
    public java.security.Principal getUserPrincipal();

    /**
     * クライアントによって指定されたセッションIDを返します。
     * これはこのリクエストの現在の有効なセッションIDと同じではない可能性があります。
     * クライアントがセッションIDを指定しなかった場合、このメソッドは<code>null</code>を返します。
     *
     * @return		指定されたセッションIDの<code>String</code>、リクエストがセッションIDを指定しない場合は<code>null</code>
     * 
     * @see     #isRequestedSessionIdValid
     */
    public String getRequestedSessionId();

    /**
     * このリクエストのURLのうち、プロトコル名からHTTPリクエストの最初の行のクエリ文字列までの部分を返します。
     * ウェブコンテナはこの文字列をデコードしません。
     * <p>例えば：
     *
     * <table summary="戻り値の例">
     * <tr align=left><th>HTTPリクエストの最初の行          </th>
     * <th>              戻り値</th>
     * <tr><td>POST /some/path.html HTTP/1.1<td><td>/some/path.html
     * <tr><td>GET http://foo.bar/a.html HTTP/1.0
     * <td><td>/a.html
     * <tr><td>HEAD /xyz?a=b HTTP/1.1<td><td>/xyz
     * </table>
     *
     * <p>スキームとホストでURLを再構築するには、 {@link HttpUtils#getRequestURL}を使用してください。
     * 
     * <p>訳注：{@link HttpUtils#getRequestURL}は非推奨なので代替APIの{@link #getRequestURL}を使用してください。
     *
     * @return		プロトコル名からクエリ文字列までのURLの部分を含む<code>String</code>
     *
     * @see     HttpUtils#getRequestURL
     */
    public String getRequestURI();

    /**
     * クライアントがリクエストに使用したURLを再構成します。
     * 返されるURLには、プロトコルとサーバー名、ポート番号、サーバーパスが含まれますが、クエリ文字列パラメーターは含まれません。
     * 
     * <p>このリクエストが{@link javax.servlet.RequestDispatcher#forward}を使用して転送された場合、再構築されたURLのサーバーパスはクライアントによって指定されたサーバーパスではなくRequestDispatcherを取得するために使用されたパスを反映しなければいけません。
     * 
     * <p>このメソッドは文字列ではなく<code>StringBuffer</code>を返すので、たとえばクエリパラメータを追加したりとURLを簡単に変更できます。
     *
     * <p>このメソッドは、リダイレクトメッセージの作成やエラーの報告に便利です。
     *
     * @return		再構成されたURLを含む<code>StringBuffer</code>のオブジェクト
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
