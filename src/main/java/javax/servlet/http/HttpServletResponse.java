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
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;
import javax.servlet.ServletResponse;

/**
 * {@link ServletResponse}インターフェースを拡張して、レスポンスを送信する時に使用するHTTP固有の機能を提供します。
 * たとえば、HTTPヘッダーやCookieにアクセスするためのメソッドを持ちます。
 *
 * <p>サーブレットコンテナは<code>HttpServletResponse</code>のオブジェクトを作成し、
 * サーブレットのserviceメソッド(<code>doGet</code>、<code>doPost</code>など)に引数として渡します。
 * 
 * @author	Various
 *
 * @see		javax.servlet.ServletResponse
 *
 */
public interface HttpServletResponse extends ServletResponse {

    /**
     * 指定されたCookieをレスポンスに追加します。このメソッドを複数回呼び出すことで複数のCookieを設定できます。

     *
     * @param cookie クライアントに返されるCookie
     *
     */
    public void addCookie(Cookie cookie);

    /**
     * 名前付きレスポンスヘッダーがすでに設定されているかどうかを示すbooleanを返します。
     * 
     * @param	name	ヘッダー名
     * @return		名前つきレスポンスヘッダーがすでに設定されている場合は<code>true</code>、そうでない場合は<code>false</code>
     */
    public boolean containsHeader(String name);

    /**
     * 指定されたURLをセッションIDを含めてエンコードします。エンコードが不要な場合はURLをそのまま返します。
     * このメソッドの実装にはセッションIDをURLにエンコードする必要があるかどうかを判断するロジックが含まれています。
     * たとえば、ブラウザがCookieをサポートしている場合やセッショントラッキングがオフの場合はURLエンコーディングは不要です。
     * 
     * <p>堅牢なセッショントラッキングのためにはサーブレットが発行するすべてのURLをこのメソッドに通す必要があります。
     * そうしなければCookieをサポートしていないブラウザではURLリライティングを使用できません。
     * 
     * <p>URLが相対的な場合、常に現在のHttpServletRequestから相対的になります。
     *
     * @param	url	エンコードされるURL
     * @return		エンコードが必要な場合はエンコードされたURL、そうでない場合は変更されていないURL
     * @exception IllegalArgumentException URLが不正な場合
     */
    public String encodeURL(String url);

    /**
     * 指定されたURLを{@link #sendRedirect(String)}メソッドで使用するためにエンコードします。
     * エンコードが不要な場合はURLをそのまま返します。
     * このメソッドの実装にはセッションIDをURLにエンコードする必要があるかどうかを判断するロジックが含まれています。
     * たとえば、ブラウザがCookieをサポートしている場合やセッショントラッキングがオフの場合はURLエンコーディングは不要です。
     * この決定を行うためのルールは通常のリンクをエンコードするかどうかを決めるために使用されるルールとは異なる可能性があるため、
     * このメソッドは{@link #encodeURL(String)}メソッドから切り離されています。
     * 
     * <p><code>HttpServletResponse.sendRedirect</code>メソッドに送信されるすべてのURLをこのメソッドに通す必要があります。
     * そうしなければCookieをサポートしていないブラウザではURLリライティングを使用できません。
     *
     * <p>URLが相対的な場合、常に現在のHttpServletRequestから相対的になります。
     *
     * @param	url	エンコードされるURL
     * @return		エンコードが必要な場合はエンコードされたURL、そうでない場合は変更されていないURL
     * @exception IllegalArgumentException URLが不正な場合
     *
     * @see #sendRedirect
     * @see #encodeUrl
     */
    public String encodeRedirectURL(String url);

    /**
     * @deprecated	Version 2.1から {@link #encodeURL(String)}に置き換えられました。
     *
     * @param	url	エンコードされるURL
     * @return		エンコードが必要な場合はエンコードされたURL、そうでない場合は変更されていないURL
     * @exception IllegalArgumentException URLが不正な場合
     */
    @Deprecated
    public String encodeUrl(String url);
    
    /**
     * @deprecated	Version 2.1から {@link #encodeRedirectURL(String)}に置き換えられました。
     *
     * @param	url	エンコードされるURL
     * @return		エンコードが必要な場合はエンコードされたURL、そうでない場合は変更されていないURL
     * @exception IllegalArgumentException URLが不正な場合
     */
    @Deprecated
    public String encodeRedirectUrl(String url);

    /**
     * 指定されたステータスを使用してエラーレスポンスをクライアントに送信し、バッファをクリアします。
     * サーバーはデフォルトでは指定されたメッセージを含むHTML形式のように見えるサーバーエラーページでコンテンツの種類を"text/html"に設定したレスポンスを作成しします。
     * 呼び出し元は現在のレスポンスのエンコーディングとコンテンツタイプに対して安全であることを確認するために
     * メッセージをエスケープまたは再エンコードする<strong>責任を負いません</strong>。
     * コンテナがメッセージを含むエラーページを生成しているため、この安全面はコンテナの責任です。
     * サーバーはクッキーを保持し、有効なレスポンスとしてエラーページを提供するのに必要なヘッダーをクリアまたは更新します。</p>
     *
     * <p>ウェブアプリケーションで渡されたステータスコードに対応するエラーページの宣言が行われていた場合、
     * 提案されたmsgパラメータより優先的にそれが返され、msgパラメータは無視されるでしょう。</p>
     *
     * <p>レスポンスがすでにコミットされていた場合、このメソッドはIllegalStateExceptionを投げます。
     * このメソッドを使用した後はレスポンスははコミットされたものとみなされるべきであり、書き込まれるべきでもありません。
     *
     * @param	sc	エラーステータスコード
     * @param	msg	説明のメッセージ
     * @exception	IOException	I/Oエラーが発生した
     * @exception	IllegalStateException	このメソッドが呼び出される前にレスポンスがコミットされていた
     */
    public void sendError(int sc, String msg) throws IOException;

    /**
     * 指定されたステータスを使用してエラーレスポンスをクライアントに送信し、バッファをクリアします。
     * 
     * サーバーはクッキーを保持し、有効なレスポンスとしてエラーページを提供するのに必要なヘッダーをクリアまたは更新します。
     *
     * ウェブアプリケーションで渡されたステータスコードに対応するエラーページの宣言が行われていた場合、エラーページが返されるでしょう。
     * 
     * <p>レスポンスがすでにコミットされていた場合、このメソッドはIllegalStateExceptionを投げます。
     * このメソッドを使用した後はレスポンスははコミットされたものとみなされるべきであり、書き込まれるべきでもありません。
     *
     * @param	sc	エラーステータスコード
     * @exception	IOException	I/Oエラーが発生した
     * @exception	IllegalStateException	このメソッドが呼び出される前にレスポンスがコミットされていた
     */
    public void sendError(int sc) throws IOException;

    /**
     * 指定されたリダイレクト先のURLを使用してクライアントに一時的なリダイレクトレスポンスを送信し、バッファをクリアします。 
     * バッファはこのメソッドで設定されたデータに置き換えられます。 
     * このメソッドを呼び出すと、ステータスコードが{@link #SC_FOUND} 302 (Found)に設定されます。 
     * このメソッドは相対URLを受け入れることができ、サーブレットコンテナは相対URLを絶対URLに変換してからクライアントにレスポンスを送信するべきです。
     * 先頭に'/'のない相対的なロケーションである場合、コンテナは現在のリクエストURIとの相対的なURLとして解釈します。
     * 先頭に '/'のある相対的なロケーションである場合、コンテナはサーブレットコンテナのルートからの相対的なURLとして解釈します。
     * 先頭に'/'が二つある相対的なロケーションである場合、コンテナはそれをネットワークパス参照として解釈します。(
     * <a href="http://www.ietf.org/rfc/rfc3986.txt">
     * RFC 3986: Uniform Resource Identifier (URI): 一般的構文</a>, section 4.2
     * &quot;相対的参照&quot;を参照してください)
     * 指定されたリダイレクト先のURLを使用してクライアントに一時的なリダイレクトレスポンスを送信し、バッファをクリアします。 
     *
     * <p>レスポンスがすでにコミットされていた場合、このメソッドはIllegalStateExceptionを投げます。
     * このメソッドを使用した後はレスポンスははコミットされたものとみなされるべきであり、書き込まれるべきでもありません。
     *
     * @param		location	リダイレクト先のURL
     * @exception	IOException	I/Oエラーが発生した
     * @exception	IllegalStateException	このメソッドが呼び出される前にレスポンスがコミットされていた
     */
    public void sendRedirect(String location) throws IOException;
    
    /**
     * 指定された名前と日付値を持つレスポンスヘッダーを設定します。
     * 日付はエポックからのミリ秒単位で指定します。
     * ヘッダーがすでに設定されている場合は新しい値が前の値を上書きします。 
     * 値を設定する前にヘッダーの有無を確認するために<code>containsHeader</code>メソッドを使用できます。
     * 
     * @param	name	ヘッダーに値を設定する名前
     * @param	date	割り当てられる日付値
     * 
     * @see #containsHeader
     * @see #addDateHeader
     */
    public void setDateHeader(String name, long date);
    
    /**
     * 指定された名前と日付値を持つレスポンスヘッダーを設定します。
     * 日付はエポックからのミリ秒単位で指定します。
     * このメソッドはレスポンスヘッダーに複数の値を持たせることができます。
     * 
     * @param	name	ヘッダーに値を設定する名前
     * @param	date	追加される日付値
     * 
     * @see #setDateHeader
     */
    public void addDateHeader(String name, long date);
    
    /**
     * 指定された名前と値を持つレスポンスヘッダーを設定します。
     * ヘッダーがすでに設定されている場合は新しい値が前の値を上書きします。 
     * 値を設定する前にヘッダーの有無を確認するために<code>containsHeader</code>メソッドを使用できます。
     * 
     * @param	name	ヘッダーの名前
     * @param	value	ヘッダーに追加する値、8bit文字列(octed string)の場合は RFC2047に従ってエンコードされている必要がある
     *		(http://www.ietf.org/rfc/rfc2047.txt)
     *
     * @see #containsHeader
     * @see #addHeader
     */
    public void setHeader(String name, String value);
    
    /**
     * 指定された名前と値を持つレスポンスヘッダーを設定します。
     * このメソッドはレスポンスヘッダーに複数の値を持たせることができます。
     * 
     * @param	name	ヘッダーの名前
     * @param	value	ヘッダーに追加する値、8bit文字列(octed string)の場合は RFC2047に従ってエンコードされている必要がある
     *		(http://www.ietf.org/rfc/rfc2047.txt)
     *
     * @see #setHeader
     */
    public void addHeader(String name, String value);

    /**
     * 指定された名前と整数値を持つレスポンスヘッダーを設定します。
     * ヘッダーがすでに設定されている場合は新しい値が前の値を上書きします。 
     * 値を設定する前にヘッダーの有無を確認するために<code>containsHeader</code>メソッドを使用できます。
     *
     * @param	name	ヘッダーの名前
     * @param	value	割り当てられる整数
     *
     * @see #containsHeader
     * @see #addIntHeader
     */
    public void setIntHeader(String name, int value);

    /**
     * 指定された名前と整数値を持つレスポンスヘッダーを設定します。
     * このメソッドはレスポンスヘッダーに複数の値を持たせることができます。
     *
     * @param	name	ヘッダーの名前
     * @param	value	割り当てられる整数
     *
     * @see #setIntHeader
     */
    public void addIntHeader(String name, int value);

    /**
     * レスポンスにステータスコードを設定します。
     * 
     * <p>このメソッドは、エラーがない場合のリターンステータスコード(たとえば、{@link #SC_OK}や{@link #SC_MOVED_TEMPORARILY}ステータスコードの場合)
     * を設定するために使用されます。
     * 
     * <p>このメソッドを使用してエラーコードを設定してもコンテナのエラーページメカニズムは実行されないでしょう。
     * エラーがあり、呼び出し側がウェブアプリケーションで定義されたエラーページを呼び出したい場合は、
     * 代わりに{@link #sendError} を使用する必要があります。
     * 
     * <p>このメソッドはすべてのCookieとその他のレスポンスヘッダーをそのまま保持します。
     * 
     * <p>有効なステータスコードは、2XX、3XX、4XX、5XXの範囲のコードです。
     * それ以外のステータスコードはコンテナ固有のものとして扱われます。
     * 
     * @param	sc	ステータスコード
     *
     * @see #sendError
     */
    public void setStatus(int sc);
  

    /**
     * @deprecated As of version 2.1, due to ambiguous meaning of the 
     * message parameter. To set a status code 
     * use <code>setStatus(int)</code>, to send an error with a description
     * use <code>sendError(int, String)</code>.
     *
     * Sets the status code and message for this response.
     * 
     * @param	sc	ステータスコード
     * @param	sm	ステータスのメッセージ
     */
    @Deprecated
    public void setStatus(int sc, String sm);

    /**
     * このレスポンスの現在のステータスコードを取得します。
     *
     * @return このレスポンスの現在のステータスコード
     *
     * @since Servlet 3.0
     */
    public int getStatus();

    /**
     * Gets the value of the response header with the given name.
     * 
     * <p>If a response header with the given name exists and contains
     * multiple values, the value that was added first will be returned.
     *
     * <p>This method considers only response headers set or added via
     * {@link #setHeader}, {@link #addHeader}, {@link #setDateHeader},
     * {@link #addDateHeader}, {@link #setIntHeader}, or
     * {@link #addIntHeader}, respectively.
     *
     * @param name the name of the response header whose value to return
     *
     * @return the value of the response header with the given name,
     * or <tt>null</tt> if no header with the given name has been set
     * on this response
     *
     * @since Servlet 3.0
     */
    public String getHeader(String name); 

    /**
     * Gets the values of the response header with the given name.
     *
     * <p>This method considers only response headers set or added via
     * {@link #setHeader}, {@link #addHeader}, {@link #setDateHeader},
     * {@link #addDateHeader}, {@link #setIntHeader}, or
     * {@link #addIntHeader}, respectively.
     *
     * <p>Any changes to the returned <code>Collection</code> must not 
     * affect this <code>HttpServletResponse</code>.
     *
     * @param name the name of the response header whose values to return
     *
     * @return a (possibly empty) <code>Collection</code> of the values
     * of the response header with the given name
     *
     * @since Servlet 3.0
     */			
    public Collection<String> getHeaders(String name); 
    
    /**
     * Gets the names of the headers of this response.
     *
     * <p>This method considers only response headers set or added via
     * {@link #setHeader}, {@link #addHeader}, {@link #setDateHeader},
     * {@link #addDateHeader}, {@link #setIntHeader}, or
     * {@link #addIntHeader}, respectively.
     *
     * <p>Any changes to the returned <code>Collection</code> must not 
     * affect this <code>HttpServletResponse</code>.
     *
     * @return a (possibly empty) <code>Collection</code> of the names
     * of the headers of this response
     *
     * @since Servlet 3.0
     */
    public Collection<String> getHeaderNames();

    /**
     * Sets the supplier of trailer headers.
     *
     * <p>The trailer header field value is defined as a comma-separated list
     * (see Section 3.2.2 and Section 4.1.2 of RFC 7230).</p>
     *
     * <p>The supplier will be called within the scope of whatever thread/call
     * causes the response content to be completed. Typically this will
     * be any thread calling close() on the output stream or writer.</p>
     *
     * <p>The trailers that run afoul of the provisions of section 4.1.2 of
     * RFC 7230 are ignored.</p>
     *
     * <p>The RFC requires the name of every key that is to be in the
     * supplied Map is included in the comma separated list that is the value
     * of the "Trailer" response header.  The application is responsible for
     * ensuring this requirement is met.  Failure to do so may lead to
     * interoperability failures.</p>
     *
     * @implSpec
     * The default implementation is a no-op.
     *
     * @param supplier the supplier of trailer headers
     *
     * @exception IllegalStateException if it is invoked after the response has
     *         has been committed,
     *         or the trailer is not supported in the request, for instance,
     *         the underlying protocol is HTTP 1.0, or the response is not
     *         in chunked encoding in HTTP 1.1.
     *
     * @since Servlet 4.0
     */
    default public void setTrailerFields(Supplier<Map<String, String>> supplier) {
    }

    /**
     * トレイラーヘッダーのサプライヤを取得します。
     *
     * @implSpec デフォルト実装ではnullを返します。
     *
     * @return <code>Supplier</code> of trailer headers
     * 
     * @since Servlet 4.0
     */
    default public Supplier<Map<String, String>> getTrailerFields() {
        return null;
    }


    /*
     * Server status codes; see RFC 2068.
     */

    /**
     * Status code (100) indicating the client can continue.
     */
    public static final int SC_CONTINUE = 100;

    /**
     * Status code (101) indicating the server is switching protocols
     * according to Upgrade header.
     */
    public static final int SC_SWITCHING_PROTOCOLS = 101;

    /**
     * Status code (200) indicating the request succeeded normally.
     */
    public static final int SC_OK = 200;

    /**
     * Status code (201) indicating the request succeeded and created
     * a new resource on the server.
     */
    public static final int SC_CREATED = 201;

    /**
     * Status code (202) indicating that a request was accepted for
     * processing, but was not completed.
     */
    public static final int SC_ACCEPTED = 202;

    /**
     * Status code (203) indicating that the meta information presented
     * by the client did not originate from the server.
     */
    public static final int SC_NON_AUTHORITATIVE_INFORMATION = 203;

    /**
     * Status code (204) indicating that the request succeeded but that
     * there was no new information to return.
     */
    public static final int SC_NO_CONTENT = 204;

    /**
     * Status code (205) indicating that the agent <em>SHOULD</em> reset
     * the document view which caused the request to be sent.
     */
    public static final int SC_RESET_CONTENT = 205;

    /**
     * Status code (206) indicating that the server has fulfilled
     * the partial GET request for the resource.
     */
    public static final int SC_PARTIAL_CONTENT = 206;

    /**
     * Status code (300) indicating that the requested resource
     * corresponds to any one of a set of representations, each with
     * its own specific location.
     */
    public static final int SC_MULTIPLE_CHOICES = 300;

    /**
     * Status code (301) indicating that the resource has permanently
     * moved to a new location, and that future references should use a
     * new URI with their requests.
     */
    public static final int SC_MOVED_PERMANENTLY = 301;

    /**
     * Status code (302) indicating that the resource has temporarily
     * moved to another location, but that future references should
     * still use the original URI to access the resource.
     *
     * This definition is being retained for backwards compatibility.
     * SC_FOUND is now the preferred definition.
     */
    public static final int SC_MOVED_TEMPORARILY = 302;

    /**
    * Status code (302) indicating that the resource reside
    * temporarily under a different URI. Since the redirection might
    * be altered on occasion, the client should continue to use the
    * Request-URI for future requests.(HTTP/1.1) To represent the
    * status code (302), it is recommended to use this variable.
    */
    public static final int SC_FOUND = 302;

    /**
     * Status code (303) indicating that the response to the request
     * can be found under a different URI.
     */
    public static final int SC_SEE_OTHER = 303;

    /**
     * Status code (304) indicating that a conditional GET operation
     * found that the resource was available and not modified.
     */
    public static final int SC_NOT_MODIFIED = 304;

    /**
     * Status code (305) indicating that the requested resource
     * <em>MUST</em> be accessed through the proxy given by the
     * <code><em>Location</em></code> field.
     */
    public static final int SC_USE_PROXY = 305;

     /**
     * Status code (307) indicating that the requested resource 
     * resides temporarily under a different URI. The temporary URI
     * <em>SHOULD</em> be given by the <code><em>Location</em></code> 
     * field in the response.
     */
    public static final int SC_TEMPORARY_REDIRECT = 307;

    /**
     * Status code (400) indicating the request sent by the client was
     * syntactically incorrect.
     */
    public static final int SC_BAD_REQUEST = 400;

    /**
     * Status code (401) indicating that the request requires HTTP
     * authentication.
     */
    public static final int SC_UNAUTHORIZED = 401;

    /**
     * Status code (402) reserved for future use.
     */
    public static final int SC_PAYMENT_REQUIRED = 402;

    /**
     * Status code (403) indicating the server understood the request
     * but refused to fulfill it.
     */
    public static final int SC_FORBIDDEN = 403;

    /**
     * Status code (404) indicating that the requested resource is not
     * available.
     */
    public static final int SC_NOT_FOUND = 404;

    /**
     * Status code (405) indicating that the method specified in the
     * <code><em>Request-Line</em></code> is not allowed for the resource
     * identified by the <code><em>Request-URI</em></code>.
     */
    public static final int SC_METHOD_NOT_ALLOWED = 405;

    /**
     * Status code (406) indicating that the resource identified by the
     * request is only capable of generating response entities which have
     * content characteristics not acceptable according to the accept
     * headers sent in the request.
     */
    public static final int SC_NOT_ACCEPTABLE = 406;

    /**
     * Status code (407) indicating that the client <em>MUST</em> first
     * authenticate itself with the proxy.
     */
    public static final int SC_PROXY_AUTHENTICATION_REQUIRED = 407;

    /**
     * Status code (408) indicating that the client did not produce a
     * request within the time that the server was prepared to wait.
     */
    public static final int SC_REQUEST_TIMEOUT = 408;

    /**
     * Status code (409) indicating that the request could not be
     * completed due to a conflict with the current state of the
     * resource.
     */
    public static final int SC_CONFLICT = 409;

    /**
     * Status code (410) indicating that the resource is no longer
     * available at the server and no forwarding address is known.
     * This condition <em>SHOULD</em> be considered permanent.
     */
    public static final int SC_GONE = 410;

    /**
     * Status code (411) indicating that the request cannot be handled
     * without a defined <code><em>Content-Length</em></code>.
     */
    public static final int SC_LENGTH_REQUIRED = 411;

    /**
     * Status code (412) indicating that the precondition given in one
     * or more of the request-header fields evaluated to false when it
     * was tested on the server.
     */
    public static final int SC_PRECONDITION_FAILED = 412;

    /**
     * Status code (413) indicating that the server is refusing to process
     * the request because the request entity is larger than the server is
     * willing or able to process.
     */
    public static final int SC_REQUEST_ENTITY_TOO_LARGE = 413;

    /**
     * Status code (414) indicating that the server is refusing to service
     * the request because the <code><em>Request-URI</em></code> is longer
     * than the server is willing to interpret.
     */
    public static final int SC_REQUEST_URI_TOO_LONG = 414;

    /**
     * Status code (415) indicating that the server is refusing to service
     * the request because the entity of the request is in a format not
     * supported by the requested resource for the requested method.
     */
    public static final int SC_UNSUPPORTED_MEDIA_TYPE = 415;

    /**
     * Status code (416) indicating that the server cannot serve the
     * requested byte range.
     */
    public static final int SC_REQUESTED_RANGE_NOT_SATISFIABLE = 416;

    /**
     * Status code (417) indicating that the server could not meet the
     * expectation given in the Expect request header.
     */
    public static final int SC_EXPECTATION_FAILED = 417;

    /**
     * Status code (500) indicating an error inside the HTTP server
     * which prevented it from fulfilling the request.
     */
    public static final int SC_INTERNAL_SERVER_ERROR = 500;

    /**
     * Status code (501) indicating the HTTP server does not support
     * the functionality needed to fulfill the request.
     */
    public static final int SC_NOT_IMPLEMENTED = 501;

    /**
     * Status code (502) indicating that the HTTP server received an
     * invalid response from a server it consulted when acting as a
     * proxy or gateway.
     */
    public static final int SC_BAD_GATEWAY = 502;

    /**
     * Status code (503) indicating that the HTTP server is
     * temporarily overloaded, and unable to handle the request.
     */
    public static final int SC_SERVICE_UNAVAILABLE = 503;

    /**
     * Status code (504) indicating that the server did not receive
     * a timely response from the upstream server while acting as
     * a gateway or proxy.
     */
    public static final int SC_GATEWAY_TIMEOUT = 504;

    /**
     * Status code (505) indicating that the server does not support
     * or refuses to support the HTTP protocol version that was used
     * in the request message.
     */
    public static final int SC_HTTP_VERSION_NOT_SUPPORTED = 505;
}
