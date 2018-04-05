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
     * 
     * <p>リクエストのヘッダーに指定された名前が存在しない場合はこのメソッドは-1を返します。
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
     * 返される{@code HttpServletMapping}は呼び出しシーケンスの最初の<code>Servlet</code>を呼び出す原因となったパスに対応します。
     * 現在のアクティブな{@link javax.servlet.Servlet}呼び出しが、{@link javax.servlet.AsyncContext#dispatch}の呼び出しによって行われた場合、
     * 返される{@code HttpServletMapping}は呼び出しシーケンスの最初の<code>Servlet</code>を呼び出す原因となったパスに対応します。
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
     * サーブレットを呼び出すこのリクエストのURLの一部を返します。このパスは"/"文字で始まりサーブレット名またはサーブレットへのパスを含みますが、余分なパス情報やクエリ文字列は含まれません。
     * CGIの変数SCRIPT_NAMEの値と同じです。
     * 
     * <p>このメソッドはこのリクエストの処理に使用されたサーブレットが "/*"パターンを使用して一致した場合は空の文字列（""）を返します。
     * 
     * <p>訳注：CGIはサーブレットが生まれる前にあったウェブアプリケーションを作るための仕組みです。現在はほぼ使われていません。
     * 
     * @return		リクエストURLで指定されている呼び出されるサーブレットの名前またはパスを含む<code>String</code>がデコードされたもの、リクエストの処理に使用されたサーブレットが"/*"パターンを使用して一致した場合は空の文字列
     */
    public String getServletPath();

    /**
     * このリクエストに関連付けられている現在の<code>HttpSession</code>を返します。現在のセッションがなく<code>create</code>がtrueの場合は新しいセッションを返します。
     * 
     * <p><code>create</code>が<code>false</code>で、リクエストに有効な<code>HttpSession</code>がない場合、このメソッドは<code>null</code>を返します。
     * 
     * <p>セッションが正しく維持されていることを確認するにはレスポンスがコミットされる前にこのメソッドを呼び出す必要があります。
     * コンテナがセッションの整合性を維持するためにCookieを使用しており、レスポンスがコミットされた後に新しいセッションを作成するよう要求された場合はIllegalStateExceptionが投げられます。
     *
     * @param create	<code>true</code>の場合、必要に応じてこのリクエストのために新しいセッションを作成する、<code>false</code>の場合、現在のセッションが存在しない場合は<code>null</code>を返す
     *
     * @return 		このリクエストに関連付けられている<code>HttpSession</code>、<code>create</code>が<code>false</code>でリクエストが有効なセッションを持っていない場合は<code>null</code>
     * 
     * @see #getSession()
     */
    public HttpSession getSession(boolean create);

    /**
     * このリクエストに関連付けられている現在のセッションを返します。リクエストがセッションを持たない場合はセッションを作成します。
     *
     * @return		このリクエストに関連付けられている<code>HttpSession</code>
     *
     * @see	#getSession(boolean)
     */
    public HttpSession getSession();

    /**
     * このリクエストに関連付けられている現在のセッションのセッションIDを変更し、新しいセッションIDを返します。
     *
     * @return 新しいセッションID
     *
     * @throws IllegalStateException リクエストに関連付けられたセッションが存在しない
     *
     * @since Servlet 3.1
     */
    public String changeSessionId();

    /**
     * 要求されたセッションIDがまだ有効かどうかを確認します。
     * 
     * <p>クライアントがセッションIDを指定しなかった場合、このメソッドは<code>false</code>を返します。
     *
     * @return			このリクエストが現在のセッションコンテキスト内の有効なセッションのIDを持っている場合は<code>true</code>、そうでない場合は<code>false</code>
     *
     * @see			#getRequestedSessionId
     * @see			#getSession
     * @see			HttpSessionContext
     */
    public boolean isRequestedSessionIdValid();

    /**
     * <p>要求されたセッションIDがHTTP Cookieとしてサーバーに送信されたかどうかを確認します。</p>
     *
     * @return			セッションIDがHTTP Cookieとしてサーバーに送信された場合は<code>true</code>、そうでない場合は<code>false</code>
     *
     * @see         #getSession
     */
    public boolean isRequestedSessionIdFromCookie();

    /**
     * <p>要求されたセッションIDがリクエストURLの一部としてサーバーに送信されたかどうかを確認します。</p>
     *
     * @return セッションIDがリクエストURLの一部としてサーバーに送信された場合は<code>true</code>、そうでない場合は<code>false</code>
     *
     * @see         #getSession
     */
    public boolean isRequestedSessionIdFromURL();

    /**
     * @deprecated	    Java Servlet API Version 2.1 から{@link #isRequestedSessionIdFromURL}に置き換えられました。
     *
     * @return セッションIDがリクエストURLの一部としてサーバーに送信された場合は<code>true</code>、そうでない場合は<code>false</code>
     */
    @Deprecated
    public boolean isRequestedSessionIdFromUrl();

    /**
     * <code>ServletContext</code>用に設定されたコンテナログインメカニズムを使用して、このリクエストを行うユーザーを認証します。
     *
     * <p>このメソッドは引数の<code>HttpServletResponse</code>を変更してコミットするかもしれません。
     *
     * @param response この<code>HttpServletRequest</code>に関連する<code>HttpServletResponse</code>
     *
     * @return <code>getUserPrincipal</code>と<code>getRemoteUser</code>、<code>getAuthType</code>によって返される値としてnull以外の値が設定されている場合に<code>true</code>、
     * 認証が不完全で、基礎となるログインメカニズムがメッセージ（例えばチャレンジ）とHTTPステータスコードをユーザーへのレスポンスとして返す場合は<code>false</code>
     *
     * @throws IOException このリクエストからの読み取り中または指定されたレスポンスへの書き込み中に入出力エラーが発生した場合
     *
     * @throws IllegalStateException ログインメカニズムがレスポンスの変更を試みたときに、すでにコミットされていた場合
     *
     * @throws ServletException 認証に失敗し、呼び出し側がエラーの処理を担当している場合（つまり基礎となるログインメカニズムでメッセージとHTTPステータスコードをユーザーに返すように設定していない場合）
     *
     * @since Servlet 3.0
     */
    public boolean authenticate(HttpServletResponse response)
	throws IOException,ServletException;

    /**
     * 提供されたユーザ名とパスワードを<code>ServletContext</code>用に設定されたウェブコンテナログインメカニズムによって使用されるパスワード検証機構で検証します。
     * 
     * <p>このメソッドは、 <code>ServletContext</code>用に設定されたログイン機構がユーザー名のパスワード検証をサポートしているとき、
     * かつログイン呼び出し時にリクエストの呼び出し元の身元が確立されていないとき（つまり<code>getUserPrincipal</code>、<code>getRemoteUser</code>、<code>getAuthType</code>がnullを返す）、
     * かつ提供された資格証明の検証が成功した場合に<code>ServletException</code>を投げずに処理を戻します。
     * それ以外の場合はこのメソッドは以下に説明するように<code>ServletException</code>を投げます。
     * 
     * <p>訳注：ログインの設定ができていてユーザーがまだログインしていなくてこのメソッドでログインに成功した場合に<code>ServletException</code>を投げずに処理を戻します。
     * 
     * <p>このメソッドが例外を投げないで処理を返すときは<code>getUserPrincipal</code>、<code>getRemoteUser</code>、<code>getAuthType</code>
     * から返される値としてnull以外の値を設定するべきです。
     *
     * @param username ユーザーのログイン識別子に対応する<code>String</code>の値
     *
     * @param password 識別されたユーザーに対応するパスワードの<code>String</code>
     *
     * @exception	ServletException    設定されたログインメカニズムがユーザ名とパスワードの認証をサポートしていない場合、
     *                                  またはnull以外の呼び出し元認証情報が(ログインの前に)すでに設定されている場合、
     *                                  または指定されたユーザ名とパスワードの検証に失敗した場合
     *
     * @since Servlet 3.0
     */
    public void login(String username, String password)
	throws ServletException;

    /**
     * リクエストで<code>getUserPrincipal</code>、<code>getRemoteUser</code>、<code>getAuthType</code>が呼び出されたときに返される値として<code>null</code>を設定します。
     *
     * @exception ServletException ログアウトに失敗した
     *
     * @since Servlet 3.0
     */
    public void logout() throws ServletException;

    /**
     * <code>multipart/form-data</code>によって提供されるこのリクエストのすべての{@link Part}要素を取得します。
     *
     * <p>このリクエストが<code>multipart/form-data</code>だけれども<code>Part</code>要素を含まない場合、返される<code>Collection</code>は空になるでしょう。
     *
     * <p>返された<code>Collection</code>へのいかなる変更もこの<code>HttpServletRequest</code>に影響を与えてはいけません。
     *
     * @return このリクエストの{@link Part}要素の(空の可能性のある)<code>Collection</code>
     *
     * @throws IOException このリクエストの{@link Part}要素の取得中にI/Oエラーが発生した場合
     *
     * @throws ServletException このリクエストが<code>multipart/form-data</code>でない場合
     *
     * @throws IllegalStateException リクエストボディが<code>maxRequestSize</code>より大きい場合、
     * もしくはリクエスト内の<code>Part</code>のいずれかが<code>maxFileSize</code>,より大きい場合、
     * もしくはデプロイメントディスクリプタに<code>@MultipartConfig</code>または<code>multipart-config</code>が存在しない場合
     *
     * @see javax.servlet.annotation.MultipartConfig#maxFileSize
     * @see javax.servlet.annotation.MultipartConfig#maxRequestSize
     *
     * @since Servlet 3.0
     */
    public Collection<Part> getParts() throws IOException, ServletException;

    /**
     * 与えられた名前で{@link Part}を取得します。
     *
     * @param name 要求する<code>Part</code>の名前
     *
     * @return 与えられた名前の<code>Part</code>、このリクエストが<code>multipart/form-data</code>だけど要求された<code>Part</code>が含まれてない場合は<code>null</code>
     *
     * @throws IOException 要求された<code>Part</code>の取得中にI/Oエラーが発生した場合
     * @throws ServletException このリクエストが<code>multipart/form-data</code>でない場合
     * @throws IllegalStateException リクエストボディが<code>maxRequestSize</code>より大きい場合、
     * もしくはリクエスト内の<code>Part</code>のいずれかが<code>maxFileSize</code>,より大きい場合、
     * もしくはデプロイメントディスクリプタに<code>@MultipartConfig</code>または<code>multipart-config</code>が存在しない場合
     *
     * @see javax.servlet.annotation.MultipartConfig#maxFileSize
     * @see javax.servlet.annotation.MultipartConfig#maxRequestSize
     *
     * @since Servlet 3.0
     */
    public Part getPart(String name) throws IOException, ServletException;

    /**
     * 指定されたクラスで<code>HttpUpgradeHandler</code>のインスタンスを作成し、httpプロトコルのアップグレードプロセスに使用します。
     *
     * @param <T> {@code handlerClass}の{@link HttpUpgradeHandler}を拡張する{@code Class}
     * 
     * @param handlerClass アップグレードに使用する<code>HttpUpgradeHandler</code>
     *
     * @return <code>HttpUpgradeHandler</code>のインスタンス
     *
     * @exception IOException アップグレード中にI/Oエラーが発生した
     * @exception ServletException 与えられた<code>handlerClass</code>のインスタンス化に失敗した
     *
     * @see javax.servlet.http.HttpUpgradeHandler
     * @see javax.servlet.http.WebConnection
     *
     * @since Servlet 3.1
     */
    public <T extends HttpUpgradeHandler> T  upgrade(Class<T> handlerClass)
        throws IOException, ServletException;

    /**
     * リクエストのトレーラフィールドを取得します。
     * 
     * <p>返されるMapはHttpServletRequestオブジェクトによって追跡されていないため、返されるMapへの変更はHttpServletRequestオブジェクトには反映されません。
     * その逆もそうです。</p>
     * 
     * <p>{@link #isTrailerFieldsReady()}は例外を発生させずにこのメソッドを安全に呼び出すことができるかどうかを調べるために最初に呼び出す必要があります。</p>
     *
     * @implSpec デフォルト実装は空のMapを返します。
     * 
     * @return プロトコルレベルでの大文字小文字に関係なく、すべてのキーが小文字であるトレーラーフィールドのMap、
     * トレーラーフィールドがないが{@link #isTrailerFieldsReady}がtrueを返す場合は空のMapを返す
     *
     * @throws IllegalStateException if {@link #isTrailerFieldsReady()} is false
     *
     * @since Servlet 4.0
     */
    default public Map<String, String> getTrailerFields() {
        return Collections.emptyMap();
    }

    /**
     * トレーラーフィールドが{@link #getTrailerFields}を使用して読み込み可能かどうかを示すbooleanを返します。
     * 
     * このメソッドは、リクエストにトレーラーがないことがわかっている場合、たとえば、HTTP 1.0などで基本プロトコルがトレーラーフィールドをサポートしていない場合、
     * またはリクエストがHTTP 1.1のチャンクエンコーディングでない場合には即座にtrueを返します。
     * また、次の条件の両方が満たされていればtrueを返します。
     * <ol type="a">
     * <li>アプリケーションはすべてのリクエストデータを読み取り、{@link #getReader}もしくは{@link #getInputStream}からEOFが返された。
     * <li>クライアントによって送信されたすべてのトレーラフィールドが受信された。この場合クライアントがトレーラフィールドをまったく送信していない可能性があることに注意してください。
     * </ol>
     *
     * @implSpec デフォルト実装ではfalseを返します。
     *
     * @return トレーラーフィールドが読み込み可能かを示すboolean
     *
     * @since Servlet 4.0
     */
    default public boolean isTrailerFieldsReady() {
        return true;
    }
}
