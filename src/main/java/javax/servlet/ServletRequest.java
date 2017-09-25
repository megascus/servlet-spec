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

import java.io.*;
import java.util.*;

/**
 * サーブレットにクライアントからのリクエストの情報を提供するオブジェクトを定義します。
 * サーブレットコンテナは<code>ServletRequest</code>オブジェクトを作成し、サーブレットの<code>service</code>メソッドへの引数として渡します。
 * 
 * <p><code>ServletRequest</code>オブジェクトはパラメータ名と値、属性、および入力ストリームを含むデータを提供します。
 * <code>ServletRequest</code>を拡張するインタフェースは追加のプロトコル固有のデータを提供できます。
 * （たとえば、HTTPのデータは{@link javax.servlet.http.HttpServletRequest}によって提供されます。）
 * 
 * @author Various
 *
 * @see javax.servlet.http.HttpServletRequest
 *
 */
public interface ServletRequest {

    /**
     * 名前の付いた属性の値を<code>Object</code>として返します。指定された名前の属性が存在しない場合は<code>null</code>を返します。
     * 
     * <p>属性は2つの方法で設定されます。サーブレットコンテナはリクエストに関する利用可能なカスタム情報を生成するために属性を設定してもよいです。
     * たとえば、HTTPSを使用して行われたリクエストの場合、<code>javax.servlet.request.X509Certificate</code>の属性からクライアントの証明書に関する情報を取得できます。
     * 属性は{@link ServletRequest#setAttribute}を使用してプログラムで設定することもできます。 
     * これにより{@link RequestDispatcher} の呼び出しの前に情報をリクエストに埋め込むことができます。
     * 
     * <p>属性名は、パッケージ名と同じ規則に従う必要があります。
     * この仕様では<code>java.*</code>、<code>javax.*</code>、<code>sun.*</code>と一致する名前を予約しています。
     *
     * @param name 属性の名前を示す<code>String</code>
     *
     * @return 属性の値を含む<code>Object</code>、属性が存在しない場合はnull
     */
    public Object getAttribute(String name);
    
    /**
     * このリクエストで利用可能な属性の値を含む<code>Enumeration</code>を返します。
     * このメソッドは利用可能な属性がない場合、空の<code>Enumeration</code>を返します。
     * 
     * @return このリクエストの属性の名前を含む文字列の<code>Enumeration</code>
     */
    public Enumeration<String> getAttributeNames();
    
    /**
     * このリクエストのボディで使用されている文字エンコーディングの名前を返します。 
     * このメソッドはリクエストの文字エンコーディングが指定されていない場合は<code>null</code>を返します 。
     * リクエストの文字エンコーディングを指定する方法は優先度順で次の通りです。
     * <ul>
     * <li>リクエスト毎({@link #getCharacterEncoding}を使用)
     * <li>ウェブアプリ毎 ({@link ServletContext#setRequestCharacterEncoding}やデプロイメントディスクリプタを使用)
     * <li>コンテナ(コンテナにデプロイされたすべてのウェブアプリケーションに設定するためにベンダー固有の方法を使用)
     * </ul>
     * 
     * @return 文字エンコーディングの名前を含む<code>String</code>、指定がない場合は<code>null</code>
     */
    public String getCharacterEncoding();

    /**
     * このリクエストのボディで使用されている文字エンコーディングの名前を上書きします。
     * このメソッドはリクエストパラメータを読み込む前、もしくはgetReader()を使用して入力を読み取る前に呼び出す必要があります。
     * それ以外の場合、効果はありません。
     * 
     * @param env 文字エンコーディングの名前を含む<code>String</code>
     *
     * @throws UnsupportedEncodingException このServletRequestにキャラクターエンコーディングを設定してもよい状態で、指定したエンコーディングが不正な場合。
     */
    public void setCharacterEncoding(String env) throws UnsupportedEncodingException;

    /**
     * リクエストボディの入力ストリームによって利用可能な長さをバイト単位で返します。
     * 長さがわからない場合やInteger.MAX_VALUEより大きい場合は-1を返します。
     * HTTPサーブレットの場合、CGIの変数<code>CONTENT_LENGTH</code>の値と同じです。
     * 
     * <p>訳注：CGIはサーブレットが生まれる前にあったウェブアプリケーションを作るための仕組みです。現在はほぼ使われていません。
     *
     * @return リクエストボディの長さを含むint。長さがわからない場合やInteger.MAX_VALUEより大きい場合は-1を返す
     */
    public int getContentLength();
    
    /**
     * リクエストボディの入力ストリームによって利用可能な長さをバイト単位で返します。
     * 長さがわからない場合は-1を返します。
     * HTTPサーブレットの場合、CGIの変数<code>CONTENT_LENGTH</code>の値と同じです。
     * 
     * <p>訳注：CGIはサーブレットが生まれる前にあったウェブアプリケーションを作るための仕組みです。現在はほぼ使われていません。
     * 
     * @return リクエストボディの長さを含むlong。長さがわからない場合は-1を返す
     *
     * @since Servlet 3.1
     */
    public long getContentLengthLong();
    
    /**
     * リクエストボディのMIME形式を返します。MIME形式が不明の場合は<code>null</code>を返します。
     * HTTPサーブレットの場合、CGI変数の<code>CONTENT_TYPE</code>の値と同じです。
     * 
     * <p>訳注：CGIはサーブレットが生まれる前にあったウェブアプリケーションを作るための仕組みです。現在はほぼ使われていません。
     * 
     * @return リクエストボディのMIME形式を含む<code>String</code>。MIME形式が不明の場合は<code>null</code>を返す
     */
    public String getContentType();
    
    /**
     * リクエストボディを{@link ServletInputStream}を利用してバイナリデータとして取得します。
     * このメソッドと{@link #getReader}はどちらかしか呼び出せません。
     *
     * @return リクエストボディを含む {@link ServletInputStream}のオブジェクト
     *
     * @exception IllegalStateException {@link #getReader} メソッドがこのリクエストですでに呼び出されていた場合
     *
     * @exception IOException I/Oエラーが発生した
     */
    public ServletInputStream getInputStream() throws IOException; 
     
    /**
     * リクエストパラメーターを<code>String</code>として返します。パラメーターが存在しない場合は<code>null</code>を返します。
     * リクエストパラメーターはリクエストと同時に送られる追加の情報です。HTTPサーブレットではリクエストパラメーターはクエリ文字列やフォームからPOSTされたデータを含みます。
     * 
     * <p>このメソッドはパラメーターが確実に一つの場合のみ呼び出すことができます。もし二つ以上あるかもしれない場合は{@link #getParameterValues}を使用してください。
     *
     * <p>もしこのメソッドを複数の値のあるパラメーターに使用した場合、<code>getParameterValues</code>が返す値の最初の値のみを取得できます。
     * 
     * <p>もしHTTP POSTリクエストのようにパラメーターがリクエストボディで送られた場合、
     * {@link #getInputStream}や{@link #getReader}で直接リクエストボディを読み取るとこのメソッドの実行が妨害される可能性があります。
     *
     * @param name パラメーターの名前を指定する<code>String</code>
     *
     * @return パラメーターを代表する単一の値の <code>String</code>
     *
     * @see #getParameterValues
     */
    public String getParameter(String name);
    
    /**
     * リクエストに含まれるパラメーターの名前を含む<code>String</code>の<code>Enumeration</code>を返します。
     * リクエストにパラメーターがない場合は空の<code>Enumeration</code>を返します。
     *
     * @return リクエストに含まれるパラメーターの名前を含む<code>String</code>の<code>Enumeration</code>、
     * リクエストにパラメーターがない場合は空の<code>Enumeration</code>
     */
    public Enumeration<String> getParameterNames();
        
    /**
     * リクエストパラメーターに含まれるすべての値を<code>String</code>オブジェクトの配列として返します。
     *
     * <p>もしパラメーターが単一の値を持つ場合、その配列の長さは1です。
     *
     * @param name 値が要求されているパラメーターの名前を含めた <code>String</code> 
     *
     * @return パラメーターの値が含められた <code>String</code> オブジェクトの配列
     *
     * @see #getParameter
     */
    public String[] getParameterValues(String name);
 
    /**
     * リクエストのパラメーターの java.util.Map を返します。
     * 
     * <p>リクエストパラメーターはリクエストと同時に送られる追加の情報です。
     * HTTPサーブレットではリクエストパラメーターはクエリ文字列やフォームからPOSTされたデータを含みます。
     *
     * @return パラメーターのキーのStringとパラメーターの値のStringの配列を含めるイミュータブルな java.util.Map
     */
    public Map<String, String[]> getParameterMap();
    
    /**
     * リクエストに使用されたプロトコルの名前とバージョンをHTTP/1.1のように<i>protocol/majorVersion.minorVersion</i>の形式で返します。
     * HTTPサーブレットの場合、CGI変数の<code>SERVER_PROTOCOL</code>の値と同じです。
     * 
     * <p>訳注：CGIはサーブレットが生まれる前にあったウェブアプリケーションを作るための仕組みです。現在はほぼ使われていません。
     *
     * @return プロトコルの名前とバージョンを含む<code>String</code>
     */    
    public String getProtocol();
    
    /**
     * リクエストを作るのに使用されたスキーマを返します。例えば<code>http</code>や<code>https</code>、<code>ftp</code>などです。
     * RFC 1738に記載されているようにスキーマが異なる場合はURLの構築に異なるルールが適用されます。
     *
     * @return リクエストを作るのに使用されたスキーマの名前を含む<code>String</code>
     */
    public String getScheme();
    
    /**
     * リクエストが送られたサーバーのホスト名を返します。
     * <code>Host</code>ヘッダーが存在する場合は":"の前の部分、もしくは解決されたサーバー名、サーバーのIPアドレスなどです。
     *
     * @return サーバーの名前を含む <code>String</code>
     */
    public String getServerName();
    
    /**
     * リクエストが送られたサーバーのポート番号を返します。
     * <code>Host</code>ヘッダーが存在する場合は":"の後ろの部分、もしくはクライアントのコネクションを受け付けたサーバーのポートです。
     *
     * @return ポート番号を指定する整数
     */
    public int getServerPort();
    
    /**
     * リクエストボディを<code>BufferedReader</code>.を利用して文字データとして取得します。
     * Readerはリクエストボディに使用されている文字エンコーディングに従って文字データを変換します。
     * このメソッドと{@link #getInputStream}はどちらかしか呼び出せません。
     * 
     * @return リクエストボディを含む <code>BufferedReader</code>  
     *
     * @exception UnsupportedEncodingException  使用されている文字エンコーディングがサポートされておらず、テキストがデコードできなかった場合
     *
     * @exception IllegalStateException {@link #getInputStream} メソッドがこのリクエストですでに呼び出されていた場合
     *
     * @exception IOException I/Oエラーが発生した
     *
     * @see #getInputStream
     */
    public BufferedReader getReader() throws IOException;
    
    /**
     * リクエストを送信したクライアントまたは最後のプロキシのインターネットプロトコル(IP)アドレスを返します。
     * HTTPサーブレットの場合、CGI変数の<code>REMOTE_ADDR</code>の値と同じです。
     * 
     * <p>訳注：CGIはサーブレットが生まれる前にあったウェブアプリケーションを作るための仕組みです。現在はほぼ使われていません。
     *
     * @return クライアントがリクエストを送信したIPアドレスを含む <code>String</code>
     */
    public String getRemoteAddr();
    
    /**
     * リクエストを送信したクライアントまたは最後のプロキシの完全修飾名(FQN)を返します。
     * エンジンがパフォーマンスを向上させるためにホスト名を解決できない、
     * またはホスト名を解決しないことを選択した場合、このメソッドはIPアドレスのドット文字列形式を返します。
     * HTTPサーブレットの場合、CGI変数の <code>REMOTE_HOST</code>の値と同じです。
     * 
     * <p>訳注：CGIはサーブレットが生まれる前にあったウェブアプリケーションを作るための仕組みです。現在はほぼ使われていません。
     *
     * @return クライアントの完全修飾名(FQN)を含む <code>String</code>
     */
    public String getRemoteHost();
    
    /**
     * このリクエストに属性を保存します。
     * 属性はリクエストの度にリセットされます。このメソッドは{@link RequestDispatcher}と組み合わせて使用​​されることがよくあります。
     *
     * <p>属性名はパッケージ名と同じ規則に従う必要があります。
     * java.*やjavax.*、com.sun.*で始まる名前は、Sun Microsystemsが使用するために予約されています。 
     * <p>Objectとしてnullが渡された場合、{@link #removeAttribute}を呼び出されたのと同じ効果を及ぼします。
     * <p>リクエストがサーブレットから<code>RequestDispatcher</code>によって別のウェブアプリケーションにディスパッチされた場合、
     * このメソッドで設定されたオブジェクトは呼び出し側のサーブレットでは正しく取得できないことがあることを注意してください。
     *
     * @param name 属性の名前を示す <code>String</code>
     *
     * @param o 保存する <code>Object</code>
     *
     */
    public void setAttribute(String name, Object o);
    
    /**
     * このリクエストから属性を削除します。属性はリクエストが処理されている間だけ永続化されているため、通常はこのメソッドは不要です。
     * 
     * <p>属性名はパッケージ名と同じ規則に従う必要があります。
     * java.*やjavax.*、com.sun.*で始まる名前は、Sun Microsystemsが使用するために予約されています。 
     *
     * @param name 削除する属性の名前を示す <code>String</code>
     */
    public void removeAttribute(String name);
    
    /**
     * Accept-Languageヘッダーの値からクライアントがコンテンツを受け入れる優先の<code>Locale</code>(ロケール、地域、言語)を返します。
     * クライアントリクエストにAccept-Languageヘッダーが存在しない場合、このメソッドはサーバーのデフォルトのlocaleを返します。
     *
     * @return クライアントの優先 <code>Locale</code>
     */
    public Locale getLocale();
    
    /**
     * Accept-Languageヘッダーの値からクライアントが受け入れられるlocale(ロケール、地域、言語)の優先度の降順で示す<code>Locale</code>オブジェクトの<code>Enumeration</code>を返します。
     * クライアントリクエストにAccept-Languageヘッダーが存在しない場合、このメソッドはサーバーのデフォルトのlocaleである単一の<code>Locale</code>を含む<code>Enumeration</code>を返します。
     *
     * @return クライアントの優先<code>Locale</code>のオブジェクトの<code>Enumeration</code>
     */
    public Enumeration<Locale> getLocales();
    
    /**
     * このリクエストがHTTPSなどのセキュアなチャネルを使用して行われたかどうかを示すbooleanを返します。
     *
     * @return このリクエストがHTTPSなどのセキュアなチャネルを使用して行われたかどうかを示すboolean
     */
    public boolean isSecure();
    
    /**
     *
     * Returns a {@link RequestDispatcher} object that acts as a wrapper for
     * the resource located at the given path.  
     * A <code>RequestDispatcher</code> object can be used to forward
     * a request to the resource or to include the resource in a response.
     * The resource can be dynamic or static.
     *
     * <p>The pathname specified may be relative, although it cannot extend
     * outside the current servlet context.  If the path begins with 
     * a "/" it is interpreted as relative to the current context root.  
     * This method returns <code>null</code> if the servlet container
     * cannot return a <code>RequestDispatcher</code>.
     *
     * <p>The difference between this method and {@link
     * ServletContext#getRequestDispatcher} is that this method can take a
     * relative path.
     *
     * @param path a <code>String</code> specifying the pathname
     * to the resource. If it is relative, it must be
     * relative against the current servlet.
     *
     * @return a <code>RequestDispatcher</code> object that acts as a
     * wrapper for the resource at the specified path,
     * or <code>null</code> if the servlet container cannot
     * return a <code>RequestDispatcher</code>
     *
     * @see RequestDispatcher
     * @see ServletContext#getRequestDispatcher
     */
    public RequestDispatcher getRequestDispatcher(String path);
    
    /**
     * @param path the path for which the real path is to be returned.

     * @return the <i>real</i> path, or <tt>null</tt> if the
     * translation cannot be performed.

     * @deprecated  As of Version 2.1 of the Java Servlet API,
     *    use {@link ServletContext#getRealPath} instead.
     */
    public String getRealPath(String path);
    
    /**
     * Returns the Internet Protocol (IP) source port of the client
     * or last proxy that sent the request.
     *
     * @return an integer specifying the port number
     *
     * @since Servlet 2.4
     */    
    public int getRemotePort();

    /**
     * Returns the host name of the Internet Protocol (IP) interface on
     * which the request was received.
     *
     * @return a <code>String</code> containing the host
     *         name of the IP on which the request was received.
     *
     * @since Servlet 2.4
     */
    public String getLocalName();

    /**
     * Returns the Internet Protocol (IP) address of the interface on
     * which the request  was received.
     *
     * @return a <code>String</code> containing the
     * IP address on which the request was received. 
     *
     * @since Servlet 2.4
     */       
    public String getLocalAddr();

    /**
     * Returns the Internet Protocol (IP) port number of the interface
     * on which the request was received.
     *
     * @return an integer specifying the port number
     *
     * @since Servlet 2.4
     */
    public int getLocalPort();

    /**
     * Gets the servlet context to which this ServletRequest was last
     * dispatched.
     *
     * @return the servlet context to which this ServletRequest was last
     * dispatched
     *
     * @since Servlet 3.0
     */
    public ServletContext getServletContext();

    /**
     * Puts this request into asynchronous mode, and initializes its
     * {@link AsyncContext} with the original (unwrapped) ServletRequest
     * and ServletResponse objects.
     *
     * <p>Calling this method will cause committal of the associated
     * response to be delayed until {@link AsyncContext#complete} is
     * called on the returned {@link AsyncContext}, or the asynchronous
     * operation has timed out.
     *
     * <p>Calling {@link AsyncContext#hasOriginalRequestAndResponse()} on
     * the returned AsyncContext will return <code>true</code>. Any filters
     * invoked in the <i>outbound</i> direction after this request was put
     * into asynchronous mode may use this as an indication that any request
     * and/or response wrappers that they added during their <i>inbound</i>
     * invocation need not stay around for the duration of the asynchronous
     * operation, and therefore any of their associated resources may be
     * released.
     *
     * <p>This method clears the list of {@link AsyncListener} instances
     * (if any) that were registered with the AsyncContext returned by the
     * previous call to one of the startAsync methods, after calling each
     * AsyncListener at its {@link AsyncListener#onStartAsync onStartAsync}
     * method.
     *
     * <p>Subsequent invocations of this method, or its overloaded 
     * variant, will return the same AsyncContext instance, reinitialized
     * as appropriate.
     *
     * @return the (re)initialized AsyncContext
     * 
     * @throws IllegalStateException if this request is within the scope of
     * a filter or servlet that does not support asynchronous operations
     * (that is, {@link #isAsyncSupported} returns false),
     * or if this method is called again without any asynchronous dispatch
     * (resulting from one of the {@link AsyncContext#dispatch} methods),
     * is called outside the scope of any such dispatch, or is called again
     * within the scope of the same dispatch, or if the response has
     * already been closed
     *
     * @see AsyncContext#dispatch()
     * @since Servlet 3.0
     */
    public AsyncContext startAsync() throws IllegalStateException;
 
    /**
     * Puts this request into asynchronous mode, and initializes its
     * {@link AsyncContext} with the given request and response objects.
     *
     * <p>The ServletRequest and ServletResponse arguments must be
     * the same instances, or instances of {@link ServletRequestWrapper} and
     * {@link ServletResponseWrapper} that wrap them, that were passed to the
     * {@link Servlet#service service} method of the Servlet or the
     * {@link Filter#doFilter doFilter} method of the Filter, respectively,
     * in whose scope this method is being called.
     *
     * <p>Calling this method will cause committal of the associated
     * response to be delayed until {@link AsyncContext#complete} is
     * called on the returned {@link AsyncContext}, or the asynchronous
     * operation has timed out.
     *
     * <p>Calling {@link AsyncContext#hasOriginalRequestAndResponse()} on
     * the returned AsyncContext will return <code>false</code>,
     * unless the passed in ServletRequest and ServletResponse arguments
     * are the original ones or do not carry any application-provided wrappers.
     * Any filters invoked in the <i>outbound</i> direction after this
     * request was put into asynchronous mode may use this as an indication
     * that some of the request and/or response wrappers that they added
     * during their <i>inbound</i> invocation may need to stay in place for
     * the duration of the asynchronous operation, and their associated
     * resources may not be released.
     * A ServletRequestWrapper applied during the <i>inbound</i>
     * invocation of a filter may be released by the <i>outbound</i>
     * invocation of the filter only if the given <code>servletRequest</code>,
     * which is used to initialize the AsyncContext and will be returned by
     * a call to {@link AsyncContext#getRequest()}, does not contain said
     * ServletRequestWrapper. The same holds true for ServletResponseWrapper
     * instances. 
     *
     * <p>This method clears the list of {@link AsyncListener} instances
     * (if any) that were registered with the AsyncContext returned by the
     * previous call to one of the startAsync methods, after calling each
     * AsyncListener at its {@link AsyncListener#onStartAsync onStartAsync}
     * method.
     *
     * <p>Subsequent invocations of this method, or its zero-argument
     * variant, will return the same AsyncContext instance, reinitialized
     * as appropriate. If a call to this method is followed by a call to its
     * zero-argument variant, the specified (and possibly wrapped) request
     * and response objects will remain <i>locked in</i> on the returned
     * AsyncContext.
     *
     * @param servletRequest the ServletRequest used to initialize the
     * AsyncContext
     * @param servletResponse the ServletResponse used to initialize the
     * AsyncContext
     *
     * @return the (re)initialized AsyncContext
     * 
     * @throws IllegalStateException if this request is within the scope of
     * a filter or servlet that does not support asynchronous operations
     * (that is, {@link #isAsyncSupported} returns false),
     * or if this method is called again without any asynchronous dispatch
     * (resulting from one of the {@link AsyncContext#dispatch} methods),
     * is called outside the scope of any such dispatch, or is called again
     * within the scope of the same dispatch, or if the response has
     * already been closed
     *
     * @since Servlet 3.0
     */
    public AsyncContext startAsync(ServletRequest servletRequest,
                                   ServletResponse servletResponse)
            throws IllegalStateException;
   
    /**
     * Checks if this request has been put into asynchronous mode.
     *
     * <p>A ServletRequest is put into asynchronous mode by calling
     * {@link #startAsync} or
     * {@link #startAsync(ServletRequest,ServletResponse)} on it.
     * 
     * <p>This method returns <tt>false</tt> if this request was
     * put into asynchronous mode, but has since been dispatched using
     * one of the {@link AsyncContext#dispatch} methods or released
     * from asynchronous mode via a call to {@link AsyncContext#complete}.
     *
     * @return true if this request has been put into asynchronous mode,
     * false otherwise
     *
     * @since Servlet 3.0
     */
    public boolean isAsyncStarted();

    /**
     * Checks if this request supports asynchronous operation.
     *
     * <p>Asynchronous operation is disabled for this request if this request
     * is within the scope of a filter or servlet that has not been annotated
     * or flagged in the deployment descriptor as being able to support
     * asynchronous handling.
     *
     * @return true if this request supports asynchronous operation, false
     * otherwise
     *
     * @since Servlet 3.0
     */
    public boolean isAsyncSupported();

    /**
     * Gets the AsyncContext that was created or reinitialized by the
     * most recent invocation of {@link #startAsync} or
     * {@link #startAsync(ServletRequest,ServletResponse)} on this request.
     *
     * @return the AsyncContext that was created or reinitialized by the
     * most recent invocation of {@link #startAsync} or
     * {@link #startAsync(ServletRequest,ServletResponse)} on
     * this request 
     *
     * @throws IllegalStateException if this request has not been put 
     * into asynchronous mode, i.e., if neither {@link #startAsync} nor
     * {@link #startAsync(ServletRequest,ServletResponse)} has been called
     *
     * @since Servlet 3.0
     */
    public AsyncContext getAsyncContext();

    /**
     * Gets the dispatcher type of this request.
     *
     * <p>The dispatcher type of a request is used by the container
     * to select the filters that need to be applied to the request:
     * Only filters with matching dispatcher type and url patterns will
     * be applied.
     * 
     * <p>Allowing a filter that has been configured for multiple 
     * dispatcher types to query a request for its dispatcher type
     * allows the filter to process the request differently depending on
     * its dispatcher type.
     *
     * <p>The initial dispatcher type of a request is defined as
     * <code>DispatcherType.REQUEST</code>. The dispatcher type of a request
     * dispatched via {@link RequestDispatcher#forward(ServletRequest,
     * ServletResponse)} or {@link RequestDispatcher#include(ServletRequest,
     * ServletResponse)} is given as <code>DispatcherType.FORWARD</code> or
     * <code>DispatcherType.INCLUDE</code>, respectively, while the
     * dispatcher type of an asynchronous request dispatched via
     * one of the {@link AsyncContext#dispatch} methods is given as
     * <code>DispatcherType.ASYNC</code>. Finally, the dispatcher type of a
     * request dispatched to an error page by the container's error handling
     * mechanism is given as <code>DispatcherType.ERROR</code>.
     *
     * @return the dispatcher type of this request
     * 
     * @see DispatcherType
     *
     * @since Servlet 3.0
     */
    public DispatcherType getDispatcherType();

}

