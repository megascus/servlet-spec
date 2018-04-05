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
 * <code>ServletRequest</code>を拡張するインターフェースは追加のプロトコル固有のデータを提供できます。
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
     * 指定されたパスにあるリソースのラッパーとして機能する{@link RequestDispatcher}のオブジェクトを返します。 
     * <code>RequestDispatcher</code>オブジェクトを使用することでリクエストを別のリソースに転送したり、リソースをレスポンスに含めることができます。
     * リソースは動的なものでも静的なものでもかまいません。
     * 
     * <p>指定されたパス名は相対パスにすることができますが現在のサーブレットのコンテキスト外には拡張できません。
     * パスが "/"で始まる場合、現在のコンテキストルートからの相対パスとして解釈されます。 サーブレットコンテナが<code>RequestDispatcher</code>を返せない場合、このメソッドはnullを返します 。
     * 
     * <p>このメソッドと{@link ServletContext#getRequestDispatcher}の違いはこのメソッドが相対パスを取ることができることです。
     *
     * @param path リソースへのパス名を指定する <code>String</code>、相対パスの場合は現在のサーブレットからの相対パスでなければならない
     *
     * @return 指定されたパスにあるリソースのラッパーとして機能する<code>RequestDispatcher</code>のオブジェクト、サーブレットコンテナが<code>RequestDispatcher</code>を返せない場合はnull
     *
     * @see RequestDispatcher
     * @see ServletContext#getRequestDispatcher
     */
    public RequestDispatcher getRequestDispatcher(String path);
    
    /**
     * @param path 実際のパスが返されるパス

     * @return <i>実際の</i>パス、変換できない場合は<tt>null</tt>

     * @deprecated  Version 2.1 から{@link ServletContext#getRealPath}に置き換えられました
     */
    public String getRealPath(String path);
    
    /**
     * リクエストを送信したクライアントまたは最後のプロキシのインターネットプロトコル(IP)送信元ポートを返します。
     *
     * @return ポート番号を指定する整数
     *
     * @since Servlet 2.4
     */    
    public int getRemotePort();

    /**
     * リクエストを受信したインターフェースのインターネットプロトコル(IP)ホスト名を返します。
     *
     * @return リクエストを受信したIPのホスト名を含む <code>String</code>
     *
     * @since Servlet 2.4
     */
    public String getLocalName();

    /**
     * リクエストを受信したインターフェースのインターネットプロトコル(IP)アドレスを返します。
     *
     * @return リクエストを受信したIPアドレスを含む <code>String</code>
     *
     * @since Servlet 2.4
     */       
    public String getLocalAddr();

    /**
     * リクエストを受信したインターフェースのインターネットプロトコル(IP)ポート番号を返します。
     *
     * @return ポート番号を指定する整数
     *
     * @since Servlet 2.4
     */
    public int getLocalPort();

    /**
     * このServletRequestが最後にディスパッチされたサーブレットコンテキストを返します。
     *
     * @return このServletRequestが最後にディスパッチされたサーブレットコンテキスト
     *
     * @since Servlet 3.0
     */
    public ServletContext getServletContext();

    /**
     * このリクエストを非同期実行モードにし、オリジナルの(ラップされてない)ServletRequestとServletResponseのオブジェクトで{@link AsyncContext}を初期化します。
     *
     * <p>このメソッドを呼び出すと返された{@link AsyncContext}の{@link AsyncContext#complete}が呼び出されるか非同期操作がタイムアウトになるまで関連するレスポンスのコミットが遅延されます。
     *
     * <p>返されたAsyncContextで{@link AsyncContext#hasOriginalRequestAndResponse()}を呼び出すと<code>true</code>が返されます。
     * このリクエストが非同期モードにされた後に<i>アウトバウンド</i>方向に呼び出されたフィルターは
     * <i>インバウンド</i>呼び出し中に追加したすべてのリクエストおよび/またはレスポンスラッパーが非同期操作の間、呼び出しを滞留させる必要はありません、
     * つまり、それらの関連リソースのいずれかが解放される可能性があります。
     *
     * <p>このメソッドは各{@link AsyncListener}をその{@link AsyncListener#onStartAsync onStartAsync}メソッドで呼び出した後、
     * startAsyncメソッドのうちの1つの最新の呼び出しによって返されたAsyncContextに登録された{@link AsyncListener}インスタンス(が存在する場合)のリストをクリアします。
     *
     * <p>その後にこのメソッドまたはオーバーロードされたメソッドを呼び出すと必要に応じて再初期化された同じAsyncContextのインスタンスが返されます。
     *
     * @return (再)初期化されたAsyncContext
     * 
     * @throws IllegalStateException このリクエストが非同期操作をサポートしないフィルタまたはサーブレットのスコープ内にある場合({@link #isAsyncSupported}がfalseを返す場合)、
     * またはこのメソッドが非同期ディスパッチ({@link AsyncContext#dispatch}メソッドの一つで行われる)なしで再度呼び出されディスパッチの範囲外で呼び出された場合、
     * または同じディスパッチの範囲内で再度呼び出された場合、またはレスポンスがすでに閉じられている場合
     *
     * @see AsyncContext#dispatch()
     * @since Servlet 3.0
     */
    public AsyncContext startAsync() throws IllegalStateException;
 
    /**
     * このリクエストを非同期実行モードにし、与えられたServletRequestとServletResponseのオブジェクトで{@link AsyncContext}を初期化します。
     *
     * <p>引数のServletRequestとServletResponseはサーブレットの{@link Servlet#service service}
     * メソッドもしくはフィルターの{@link Filter#doFilter doFilter}メソッドの各々が呼び出されたスコープでの同じインスタンスであるか、
     * {@link ServletRequestWrapper}や{@link ServletResponseWrapper}でそれらをラップしたものである必要があります。
     *
     * <p>このメソッドを呼び出すと返された{@link AsyncContext}の{@link AsyncContext#complete}が呼び出されるか非同期操作がタイムアウトになるまで関連するレスポンスのコミットが遅延されます。
     *
     * <p>引数として渡されたServletRequestとServletResponseがオリジナルのものもしくはアプリケーションが提供するラッパーでない限りは返されたAsyncContextで{@link AsyncContext#hasOriginalRequestAndResponse()}を呼び出すと<code>false</code>が返されます。
     * このリクエストが非同期モードにされた後に<i>アウトバウンド</i>方向に呼び出されたいくつかのフィルターは
     * <i>インバウンド</i>呼び出し中に追加したすべてのリクエストおよび/またはレスポンスラッパーが非同期操作の間、呼び出しを滞留させる必要があるかもしれず、それらの関連リソースは解放されないかもしれません。
     * フィルターの<i>インバウンド</i>呼び出し中に適用されたServletRequestWrapperは与えられた<code>ServletRequest</code>
     * (ServletRequestWrapperを含みません)がAsyncContextを初期化し{@link AsyncContext#getRequest()}の呼び出しによって返される場合のみ解放されます。
     *
     * <p>このメソッドは各{@link AsyncListener}をその{@link AsyncListener#onStartAsync onStartAsync}メソッドで呼び出した後、
     * startAsyncメソッドのうちの1つの最新の呼び出しによって返されたAsyncContextに登録された{@link AsyncListener}インスタンス(が存在する場合)のリストをクリアします。
     *
     * <p>その後にこのメソッドまたはオーバーロードされた引数のないメソッドを呼び出すと必要に応じて再初期化された同じAsyncContextのインスタンスが返されます。
     *  このメソッドの呼び出しのあとに引数のないメソッドの呼び出しがあった場合、
     * 指定された（場合によってはラップされた）リクエストおよびレスポンスのオブジェクトは返されたAsyncContextに<i>ロック</i>されたままになります。
     *
     * @param servletRequest AsyncContextの初期化に使用するServletRequest
     * 
     * @param servletResponse AsyncContextの初期化に使用するServletResponse
     *
     * @return (再)初期化されたAsyncContext
     * 
     * @throws IllegalStateException このリクエストが非同期操作をサポートしないフィルタまたはサーブレットのスコープ内にある場合({@link #isAsyncSupported}がfalseを返す場合)、
     * またはこのメソッドが非同期ディスパッチ({@link AsyncContext#dispatch}メソッドの一つで行われる)なしで再度呼び出されディスパッチの範囲外で呼び出された場合、
     * または同じディスパッチの範囲内で再度呼び出された場合、またはレスポンスがすでに閉じられている場合
     *
     * @since Servlet 3.0
     */
    public AsyncContext startAsync(ServletRequest servletRequest,
                                   ServletResponse servletResponse)
            throws IllegalStateException;
   
    /**
     * このリクエストが非同期モードになっているかどうかをチェックします。
     *
     * <p>ServletRequestは{@link #startAsync}や{@link #startAsync(ServletRequest,ServletResponse)}の呼び出しで非同期モードになります。
     * 
     * <p>このメソッドは非同期実行モードになってから{@link AsyncContext#dispatch}を使ってディスパッチされている場合や
     * {@link AsyncContext#complete}が呼ばれて非同期実行モードから開放されている場合には<tt>false</tt>を返します。
     *
     * @return このリクエストが非同期モードになっている場合はtrue、そうでない場合はfalse
     *
     * @since Servlet 3.0
     */
    public boolean isAsyncStarted();

    /**
     * このリクエストが非同期操作をサポートしているかどうかをチェックします。
     *
     * <p>このリクエストが非同期処理をサポートできるとアノテーションがつけられていないかデプロイメントディスクリプタにフラグが立てられていないフィルターまたはサーブレットのスコープ内にある場合、
     * このリクエストに対して非同期操作は無効になります。
     *
     * @return true if this request supports asynchronous operation, false
     * otherwise
     *
     * @since Servlet 3.0
     */
    public boolean isAsyncSupported();

    /**
     * このリクエストで{@link #startAsync}または{@link #startAsync(ServletRequest,ServletResponse)}の最新の呼び出しによって作成または再初期化されたAsyncContextを取得します。
     *
     * @return このリクエストで{@link #startAsync}または{@link #startAsync(ServletRequest,ServletResponse)}の最新の呼び出しによって作成または再初期化されたAsyncContext
     *
     * @throws IllegalStateException このリクエストが非同期モードになっていない場合、つまり{@link #startAsync}も{@link #startAsync(ServletRequest,ServletResponse)}も呼び出されていない場合
     *
     * @since Servlet 3.0
     */
    public AsyncContext getAsyncContext();

    /**
     * このリクエストのdispatcher typeを取得します。
     *
     * <p>リクエストのdispatcher typeは、リクエストに適用する必要があるフィルターを選択するためにコンテナによって使用されます。
     * 一致するdispatcher typeとURLパターンを持つフィルターのみが適用されます。
     * 
     * <p>リクエストのdispatcher typeを照会するように設定されたフィルターを複数のdispatcher typeに対して許可すると、フィルターはdispatcher typeに応じてリクエストを個別に処理できます。
     * 
     * <p>リクエストのdispatcher typeの初期値は<code>DispatcherType.REQUEST</code>として定義されます。
     * {@link RequestDispatcher#forward(ServletRequest, ServletResponse)}または{@link RequestDispatcher#include(ServletRequest, ServletResponse)}を介してディスパッチされたリクエストのdispatcher typeは
     * それぞれ<code>DispatcherType.FORWARD</code>または<code>DispatcherType.INCLUDE</code>として与えられ、
     * {@link AsyncContext#dispatch}メソッドで行われる非同期リクエストのdispatcher typeは<code>DispatcherType.ASYNC</code>として与えられます。 
     * 最後にコンテナのエラー処理メカニズムによってエラーページにディスパッチされたリクエストのdispatcher typeは<code>DispatcherType.ERROR</code>として与えられます。
     *
     *
     * @return リクエストのdispatcher type
     * 
     * @see DispatcherType
     *
     * @since Servlet 3.0
     */
    public DispatcherType getDispatcherType();

}

