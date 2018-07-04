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
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.servlet.*;


/**
 * <p>Webサイトに適したHTTPサーブレットを作成するためにサブクラスが作成される抽象クラスを提供します。
 * <code>HttpServlet</code>のサブクラスはひとつ以上のメソッドをオーバーライドする必要があります。通常は以下のうちのどれかです。</p>
 * 
 * <ul>
 * <li> <code>doGet</code>、サーブレットがHTTP GETリクエストをサポートしている場合
 * <li> <code>doPost</code>、HTTP POSTリクエスト用
 * <li> <code>doPut</code>、HTTP PUTリクエスト用
 * <li> <code>doDelete</code>、HTTP DELETETリクエスト用
 * <li> <code>init</code> と <code>destroy</code>、サーブレットのライフサイクルで保持されているリソースの管理用
 * <li> <code>getServletInfo</code>、サーブレットが自身に関する情報を提供するために使用する
 * </ul>
 *
 * <code>service</code>メソッドをオーバーライドする理由はほとんどありません。
 * <code>service</code>メソッドは各HTTPのリクエストタイプの処理メソッド（上記の<code>do</code><i>XXX</i>メソッド）に標準HTTPリクエストをディスパッチして処理します。
 * 
 * 同様に、 <code>doOptions</code>メソッドと<code>doTrace</code> メソッドをオーバーライドする理由もほとんどありません。
 * 
 * <p>サーブレットは通常、サーバー上でマルチスレッドで実行されるため、フィルタは同時に行われるリクエストを処理し、共有リソースへのアクセスを同期するよう注意しなければなりません。
 * 
 * 共有リソースにはインスタンス変数、クラス変数などのインメモリデータや、ファイル、データベース接続、ネットワーク接続などの外部オブジェクトが含まれます。
 * Javaプログラムで複数のスレッドを処理する方法の詳細は、<a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/">Java Tutorial on Multithreaded Programming</a>を参照してください。
 *
 * @author  Various
 */

public abstract class HttpServlet extends GenericServlet
{
    private static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_HEAD = "HEAD";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_OPTIONS = "OPTIONS";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_TRACE = "TRACE";

    private static final String HEADER_IFMODSINCE = "If-Modified-Since";
    private static final String HEADER_LASTMOD = "Last-Modified";
    
    private static final String LSTRING_FILE =
        "javax.servlet.http.LocalStrings";
    private static ResourceBundle lStrings =
        ResourceBundle.getBundle(LSTRING_FILE);
   
    
    /**
     * <p>このクラスはabstractクラスなので何もしません。</p>
     * 
     * <p>訳注：理由になってない・・・・・
     */

    public HttpServlet() { }
    

    /**
     * サーブレットがGETリクエストを処理できるようにするために(serviceメソッド経由で)サーバーによって呼び出されます。
     * 
     * <p>GETリクエストをサポートするためにこのメソッドをオーバーライドすると、HTTP HEADリクエストも自動的にサポートされます。
     * HEADリクエストはレスポンスとしてボディがないものが返されるリクエストヘッダフィールドのみのGETリクエストです。
     * 
     * <p>このメソッドをオーバーライドするときは、リクエストデータを読み込み、レスポンスヘッダーを書き、
     * レスポンスのWriterまたはOutputStream オブジェクトを取得し、最後にレスポンスデータを書き込みます。
     * コンテンツタイプとエンコーディングを含めることをお勧めします。
     * <code>PrintWriter</code>オブジェクトを使用してレスポンスを返す場合は<code>PrintWriter</code>オブジェクトにアクセスする前にコンテンツタイプを設定します。
     * 
     * <p>サーブレットコンテナはレスポンスをコミットする前にヘッダーを書き込まなければいけません。
     * HTTPではレスポンスボディの前にヘッダーを送信する必要があるからです。
     * 
     * 可能であれば({@link javax.servlet.ServletResponse#setContentLength}メソッドを使用して)Content-Lengthヘッダーを設定し、
     * サーブレットコンテナが永続的な接続を使用して応答をクライアントに返すようにし、パフォーマンスを向上させます。
     * コンテンツの長さはレスポンス全体がレスポンスバッファ内に収まる場合には自動的に設定されます。
     * 
     * <p>HTTP 1.1チャンクエンコーディング(応答にTransfer-Encodingヘッダーがあることを意味します)を使用する場合は、
     * Content-Lengthヘッダーを設定しないでください。
     * 
     * <p>GETメソッドは安全である必要があります。つまりユーザーが責任を負うべき副作用はありません。
     * たとえばほとんどの検索フォームは副作用がありません。
     * クライアントのリクエストが格納されたデータを変更することを意図している場合、リクエストは他のHTTPメソッドを使用する必要があります。
     * 
     * GETメソッドは冪等である必要があります。つまり安全に繰り返すことができます。
     * 時にはメソッドを安全にすることも、メソッドを冪等にします。
     * たとえば、検索を繰り返すことは安全かつ冪等ですが、オンラインで製品を購入したりデータを修正することは安全でもなく冪等でもありません。
     * 
     * <p>リクエストのフォーマットが正しくない場合、<code>doGet</code>はHTTP "Bad Request"メッセージを返します。
     * 
     * @param req   クライアントからのリクエストを含む{@link HttpServletRequest}オブジェクト
     *
     * @param resp  クライアントに返すレスポンスを含む{@link HttpServletResponse}オブジェクト
     * 
     * @throws IOException   サーブレットがGETリクエストを処理しているときにI/Oエラーが発生した
     *
     * @throws ServletException  GETのためのリクエストが処理できなかった
     *
     * @see javax.servlet.ServletResponse#setContentType
     */

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_get_not_supported");
        if (protocol.endsWith("1.1")) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
        }
    }


    /**
     * <code>HttpServletRequest</code>オブジェクトが最後に変更された時刻を1970 年 1 月 1 日 00:00:00 GMT からのミリ秒数で返します。
     * 時刻が判らない場合、このメソッドは負の数を返します。(デフォルトです)
     *
     * <p>HTTP GETリクエストをサポートし最後の変更時刻を迅速に判断できるサーブレットはこのメソッドをオーバーライドする必要があります。
     * これによりブラウザやプロキシのキャッシュがより効果的に機能し、サーバーやネットワークリソースの負荷が軽減されます。
     * 
     * <p>訳注：<code>HttpServletRequest</code>オブジェクトでなく、リクエストに応じてこのサーブレットが示すリソースが最後に変更された時刻を返すのが正しい。
     *
     * @param req   サーブレットに送られた <code>HttpServletRequest</code> のオブジェクト
     *
     * @return <code>HttpServletRequest</code>オブジェクトが最後に変更された時刻の1970 年 1 月 1 日 00:00:00 GMT からのミリ秒数の<code>long</code>、不明な場合は-1
     */

    protected long getLastModified(HttpServletRequest req) {
        return -1;
    }


    /**
     * protectedな<code>service</code>メソッドからHTTP HEADリクエストを受け取り、リクエストを処理します。
     * 
     * クライアントはContent-TypeやContent-Lengthなどのレスポンスのヘッダーに含まれる情報だけを参照したいときにHEADリクエストを送ります。
     * HTTP HEADメソッドはContent-Lengthヘッダを正確に設定するためにレスポンスの出力バイト数をカウントします。
     * 
     * <p> このメソッドをオーバーライドすると、レスポンスボディボディの計算を回避しレスポンスヘッダーを直接設定することでパフォーマンスを向上させることができます。
     * 書き込む<code>doHead</code>メソッドが安全かつ冪等である（つまり、一回のHTTP HEAD要求に対して複数回呼び出されないようにする）ことを確認してください。
     *
     * <p>HTTP HEADリクエストのフォーマットが正しくない場合、<code>doHead</code>はHTTP "Bad Request"メッセージを返します。
     *
     * @param req   クライアントからのリクエストを含む{@link HttpServletRequest}オブジェクト
     *
     * @param resp  クライアントに返すレスポンスを含む{@link HttpServletResponse}オブジェクト
     *
     * @throws IOException   I/Oエラーが発生した
     *
     * @throws ServletException  HEADのためのリクエストが処理できなかった
     */
    protected void doHead(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        NoBodyResponse response = new NoBodyResponse(resp);
        
        doGet(req, response);
        response.setContentLength();
    }


    /**
     * サーブレットがPOSTリクエストを処理できるようにするために(serviceメソッド経由で)サーバーによって呼び出されます。
     *
     * HTTP POSTメソッドを使用するとクライアントウェブサーバーに無制限のデータを一度に送信できます。
     * クレジットカード番号などの情報を送信するときにも便利です。
     *
     * <p>このメソッドをオーバーライドするときは、リクエストデータを読み込み、レスポンスヘッダーを書き、
     * レスポンスのWriterまたはOutputStream オブジェクトを取得し、最後にレスポンスデータを書き込みます。
     * コンテンツタイプとエンコーディングを含めることをお勧めします。
     * <code>PrintWriter</code>オブジェクトを使用してレスポンスを返す場合は<code>PrintWriter</code>オブジェクトにアクセスする前にコンテンツタイプを設定します。
     * 
     * <p>サーブレットコンテナはレスポンスをコミットする前にヘッダーを書き込まなければいけません。
     * HTTPではレスポンスボディの前にヘッダーを送信する必要があるからです。
     *
     * 可能であれば({@link javax.servlet.ServletResponse#setContentLength}メソッドを使用して)Content-Lengthヘッダーを設定し、
     * サーブレットコンテナが永続的な接続を使用して応答をクライアントに返すようにし、パフォーマンスを向上させます。
     * コンテンツの長さはレスポンス全体がレスポンスバッファ内に収まる場合には自動的に設定されます。
     *
     * <p>HTTP 1.1のチャンクエンコーディング（レスポンスにTransfer-Encodingヘッダーがあることを意味します）を使用する場合は
     * Content-Lengthヘッダーを設定しないでください。
     *
     * <p>このメソッドは安全でも冪等でもある必要はありません。
     * POSTによって行われる操作にはユーザーが責任を負う可能性のある副作用があります。
     * 例えば、保存されたデータの更新や、オンラインでの商品の購入などです。
     *
     * <p>HTTP POSTリクエストのフォーマットが正しくない場合、<code>doPost</code>はHTTP "Bad Request"メッセージを返します。
     *
     *
     * @param req   クライアントからのリクエストを含む{@link HttpServletRequest}オブジェクト
     *
     * @param resp  クライアントに返すレスポンスを含む{@link HttpServletResponse}オブジェクト
     * 
     * @throws IOException   サーブレットがリクエストを処理しているときにI/Oエラーが発生した
     *
     * @throws ServletException  POSTのためのリクエストが処理できなかった
     *
     * @see javax.servlet.ServletOutputStream
     * @see javax.servlet.ServletResponse#setContentType
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_post_not_supported");
        if (protocol.endsWith("1.1")) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
        }
    }


    /**
     * サーブレットがPUTリクエストを処理できるようにするために(serviceメソッド経由で)サーバーによって呼び出されます。
     *
     * PUT操作ではクライアントはFTPでファイルを送信する場合と同様にサーバーにファイルを置くことができます。
     *
     * <p>このメソッドをオーバーライドするときは、
     * リクエストで送信されたコンテンツヘッダー(Content-Length、Content-Type、Content-Transfer-Encoding、
     * Content-Encoding、Content-Base、Content-Language、Content-Location、
     * Content-MD5、Content-Rangeが含まれる)を完全にそのままにしてください。
     * メソッドがコンテンツヘッダーを処理できない場合はエラーメッセージ(HTTP 501 - Not Implemented) を発行しリクエストを破棄する必要があります。
     * HTTP 1.1のより詳細な情報については、<a href="http://www.ietf.org/rfc/rfc2616.txt">RFC 2616</a>を参照してください。
     * .
     *
     * <p>このメソッドは安全でも冪等でもある必要はありません。
     * <code>doPut</code>によって行われる操作にはユーザーが責任を負う可能性のある副作用があります。
     * このメソッドを使用する場合は、影響を受けるURLのコピーをテンポラリ領域に保存すると便利です。
     *
     * <p>HTTP PUTリクエストのフォーマットが正しくない場合、<code>doPut</code>はHTTP "Bad Request"メッセージを返します。
     *
     * @param req   クライアントからのリクエストを含む{@link HttpServletRequest}オブジェクト
     *
     * @param resp  クライアントに返すレスポンスを含む{@link HttpServletResponse}オブジェクト
     *
     * @throws IOException   サーブレットがPUTリクエストを処理しているときにI/Oエラーが発生した
     *
     * @throws ServletException  PUTのためのリクエストが処理できなかった
     */
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_put_not_supported");
        if (protocol.endsWith("1.1")) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
        }
    }


    /**
     * サーブレットがDELETEリクエストを処理できるようにするために(serviceメソッド経由で)サーバーによって呼び出されます。
     *
     * DELETEの操作ではクライアントはサーバーからドキュメントもしくはウェブページを削除できます。
     * 
     * <p>このメソッドは安全でも冪等でもある必要はありません。
     * DELETEによって要求された操作にはユーザーが責任を負う可能性のある副作用があります。
     * このメソッドを使用する場合は、影響を受けるURLのコピーをテンポラリ領域に保存すると便利です。
     * 
     * <p>HTTP DELETEリクエストのフォーマットが正しくない場合、<code>doDelete</code>はHTTP "Bad Request"メッセージを返します。
     *
     *
     * @param req   クライアントからのリクエストを含む{@link HttpServletRequest}オブジェクト
     *
     * @param resp  クライアントに返すレスポンスを含む{@link HttpServletResponse}オブジェクト                        
     *
     * @throws IOException   サーブレットがDELETEリクエストを処理しているときにI/Oエラーが発生した
     *
     * @throws ServletException  DELETEのためのリクエストが処理できなかった
     */
    protected void doDelete(HttpServletRequest req,
                            HttpServletResponse resp)
        throws ServletException, IOException
    {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_delete_not_supported");
        if (protocol.endsWith("1.1")) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
        }
    }
    

    private Method[] getAllDeclaredMethods(Class<? extends HttpServlet> c) {

        Class<?> clazz = c;
        Method[] allMethods = null;

        while (!clazz.equals(HttpServlet.class)) {
            Method[] thisMethods = clazz.getDeclaredMethods();
            if (allMethods != null && allMethods.length > 0) {
                Method[] subClassMethods = allMethods;
                allMethods =
                    new Method[thisMethods.length + subClassMethods.length];
                System.arraycopy(thisMethods, 0, allMethods, 0,
                                 thisMethods.length);
                System.arraycopy(subClassMethods, 0, allMethods, thisMethods.length,
                                 subClassMethods.length);
            } else {
                allMethods = thisMethods;
            }

            clazz = clazz.getSuperclass();
        }

        return ((allMethods != null) ? allMethods : new Method[0]);
    }


    /**
     * サーブレットがOPTIONSリクエストを処理できるようにするために(serviceメソッド経由で)サーバーによって呼び出されます。
     * 
     *  OPTIONS要求はサーバーがサポートするHTTPメソッドを測定し適切なヘッダーを返します。
     * たとえば、サーブレットが<code>doGet</code>メソッドをオーバーライドする場合、このメソッドは次のヘッダーを返します。
     *
     * <p><code>Allow: GET, HEAD, TRACE, OPTIONS</code>
     * 
     * <p>サーブレットがHTTP 1.1で実装されているものよりも新しく制定されたHTTPメソッドを実装していない限り、
     * このメソッドをオーバーライドする必要はありません。
     *
     * <p>訳注：HTTP/2でもHTTPのメソッドは増えていないのでこのドキュメントの内容は有効です。
     *
     * @param req   クライアントからのリクエストを含む{@link HttpServletRequest}オブジェクト
     *
     * @param resp  クライアントに返すレスポンスを含む{@link HttpServletResponse}オブジェクト                             
     *
     * @throws IOException   サーブレットがOPTIONSリクエストを処理しているときにI/Oエラーが発生した
     *
     * @throws ServletException  OPTIONSのためのリクエストが処理できなかった
     */
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        Method[] methods = getAllDeclaredMethods(this.getClass());
        
        boolean ALLOW_GET = false;
        boolean ALLOW_HEAD = false;
        boolean ALLOW_POST = false;
        boolean ALLOW_PUT = false;
        boolean ALLOW_DELETE = false;
        boolean ALLOW_TRACE = true;
        boolean ALLOW_OPTIONS = true;
        
        for (int i=0; i<methods.length; i++) {
            String methodName = methods[i].getName();
            
            if (methodName.equals("doGet")) {
                ALLOW_GET = true;
                ALLOW_HEAD = true;
            } else if (methodName.equals("doPost")) {
                ALLOW_POST = true;
            } else if (methodName.equals("doPut")) {
                ALLOW_PUT = true;
            } else if (methodName.equals("doDelete")) {
                ALLOW_DELETE = true;
            }
            
        }
        
        // we know "allow" is not null as ALLOW_OPTIONS = true
        // when this method is invoked
        StringBuilder allow = new StringBuilder();
        if (ALLOW_GET) {
            allow.append(METHOD_GET);
        }
        if (ALLOW_HEAD) {
            if (allow.length() > 0) {
                allow.append(", ");
            }
            allow.append(METHOD_HEAD);
        }
        if (ALLOW_POST) {
            if (allow.length() > 0) {
                allow.append(", ");
            }
            allow.append(METHOD_POST);
        }
        if (ALLOW_PUT) {
            if (allow.length() > 0) {
                allow.append(", ");
            }
            allow.append(METHOD_PUT);
        }
        if (ALLOW_DELETE) {
            if (allow.length() > 0) {
                allow.append(", ");
            }
            allow.append(METHOD_DELETE);
        }
        if (ALLOW_TRACE) {
            if (allow.length() > 0) {
                allow.append(", ");
            }
            allow.append(METHOD_TRACE);
        }
        if (ALLOW_OPTIONS) {
            if (allow.length() > 0) {
                allow.append(", ");
            }
            allow.append(METHOD_OPTIONS);
        }
        
        resp.setHeader("Allow", allow.toString());
    }
    
    
    /**
     * サーブレットがTRACEリクエストを処理できるようにするために(serviceメソッド経由で)サーバーによって呼び出されます。
     *
     * TRACEは、TRACEリクエストとともに送信されたヘッダーをクライアントに戻しデバッグに使用できるようにします。
     * このメソッドをオーバーライドする必要はありません。
     *
     * @param req   クライアントからのリクエストを含む{@link HttpServletRequest}オブジェクト
     *
     * @param resp  クライアントに返すレスポンスを含む{@link HttpServletResponse}オブジェクト                            
     *
     * @throws IOException   サーブレットがTRACEリクエストを処理しているときにI/Oエラーが発生した
     *
     * @throws ServletException  TRACEのためのリクエストが処理できなかった
     */
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) 
        throws ServletException, IOException
    {
        
        int responseLength;

        String CRLF = "\r\n";
        StringBuilder buffer = new StringBuilder("TRACE ").append(req.getRequestURI())
            .append(" ").append(req.getProtocol());

        Enumeration<String> reqHeaderEnum = req.getHeaderNames();

        while( reqHeaderEnum.hasMoreElements() ) {
            String headerName = reqHeaderEnum.nextElement();
            buffer.append(CRLF).append(headerName).append(": ")
                .append(req.getHeader(headerName));
        }

        buffer.append(CRLF);

        responseLength = buffer.length();

        resp.setContentType("message/http");
        resp.setContentLength(responseLength);
        ServletOutputStream out = resp.getOutputStream();
        out.print(buffer.toString());
    }


    /**
     * publicな<code>service</code>メソッドから標準のHTTPリクエストを受け取りこのクラスで定義されている<code>do</code><i>XXX</i>メソッドにディスパッチします。
     * このメソッドは{@link javax.servlet.Servlet#service}メソッドのHTTPに特化したバージョンです。
     * このメソッドをオーバーライドする必要はありません。
     *
     * @param req   クライアントからのリクエストを含む{@link HttpServletRequest}オブジェクト
     *
     * @param resp  クライアントに返すレスポンスを含む{@link HttpServletResponse}オブジェクト                               
     *
     * @throws IOException   サーブレットがHTTPリクエストを処理しているときにI/Oエラーが発生した
     *
     * @throws ServletException  HTTPリクエストが処理できなかった
     * 
     * @see javax.servlet.Servlet#service
     */
    protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        String method = req.getMethod();

        if (method.equals(METHOD_GET)) {
            long lastModified = getLastModified(req);
            if (lastModified == -1) {
                // servlet doesn't support if-modified-since, no reason
                // to go through further expensive logic
                doGet(req, resp);
            } else {
                long ifModifiedSince = req.getDateHeader(HEADER_IFMODSINCE);
                if (ifModifiedSince < lastModified) {
                    // If the servlet mod time is later, call doGet()
                    // Round down to the nearest second for a proper compare
                    // A ifModifiedSince of -1 will always be less
                    maybeSetLastModified(resp, lastModified);
                    doGet(req, resp);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                }
            }

        } else if (method.equals(METHOD_HEAD)) {
            long lastModified = getLastModified(req);
            maybeSetLastModified(resp, lastModified);
            doHead(req, resp);

        } else if (method.equals(METHOD_POST)) {
            doPost(req, resp);
            
        } else if (method.equals(METHOD_PUT)) {
            doPut(req, resp);
            
        } else if (method.equals(METHOD_DELETE)) {
            doDelete(req, resp);
            
        } else if (method.equals(METHOD_OPTIONS)) {
            doOptions(req,resp);
            
        } else if (method.equals(METHOD_TRACE)) {
            doTrace(req,resp);
            
        } else {
            //
            // Note that this means NO servlet supports whatever
            // method was requested, anywhere on this server.
            //

            String errMsg = lStrings.getString("http.method_not_implemented");
            Object[] errArgs = new Object[1];
            errArgs[0] = method;
            errMsg = MessageFormat.format(errMsg, errArgs);
            
            resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, errMsg);
        }
    }
    

    /*
     * Sets the Last-Modified entity header field, if it has not
     * already been set and if the value is meaningful.  Called before
     * doGet, to ensure that headers are set before response data is
     * written.  A subclass might have set this header already, so we
     * check.
     */
    private void maybeSetLastModified(HttpServletResponse resp,
                                      long lastModified) {
        if (resp.containsHeader(HEADER_LASTMOD))
            return;
        if (lastModified >= 0)
            resp.setDateHeader(HEADER_LASTMOD, lastModified);
    }
   
    
    /**
     * クライアントからのリクエストをprotectedな<code>service</code>メソッドにディスパッチします。このメソッドをオーバーライドする必要はありません。
     * 
     * @param req   クライアントからのリクエストを含む{@link HttpServletRequest}オブジェクト
     *
     * @param res  クライアントに返すレスポンスを含む{@link HttpServletResponse}オブジェクト                              
     *
     * @throws IOException   サーブレットがHTTPリクエストを処理しているときにI/Oエラーが発生した
     *
     * @throws ServletException  HTTPリクエストが処理できなかったか、受け取ったパラメーターがそれぞれ{@link HttpServletRequest}か{@link HttpServletResponse}のインスタンスでなかった場合。
     * 
     * @see javax.servlet.Servlet#service
     */
    @Override
    public void service(ServletRequest req, ServletResponse res)
        throws ServletException, IOException
    {
        HttpServletRequest  request;
        HttpServletResponse response;
        
        if (!(req instanceof HttpServletRequest &&
                res instanceof HttpServletResponse)) {
            throw new ServletException("non-HTTP request or response");
        }

        request = (HttpServletRequest) req;
        response = (HttpServletResponse) res;

        service(request, response);
    }
}


/*
 * A response that includes no body, for use in (dumb) "HEAD" support.
 * This just swallows that body, counting the bytes in order to set
 * the content length appropriately.  All other methods delegate directly
 * to the wrapped HTTP Servlet Response object.
 */
// file private
class NoBodyResponse extends HttpServletResponseWrapper {

    private static final ResourceBundle lStrings
        = ResourceBundle.getBundle("javax.servlet.http.LocalStrings");

    private NoBodyOutputStream noBody;
    private PrintWriter writer;
    private boolean didSetContentLength;
    private boolean usingOutputStream;

    // file private
    NoBodyResponse(HttpServletResponse r) {
        super(r);
        noBody = new NoBodyOutputStream();
    }

    // file private
    void setContentLength() {
        if (!didSetContentLength) {
            if (writer != null) {
                writer.flush();
            }
            setContentLength(noBody.getContentLength());
        }
    }

    @Override
    public void setContentLength(int len) {
        super.setContentLength(len);
        didSetContentLength = true;
    }

    @Override
    public void setContentLengthLong(long len) {
        super.setContentLengthLong(len);
        didSetContentLength = true;
    }

    @Override
    public void setHeader(String name, String value) {
        super.setHeader(name, value);
        checkHeader(name);
    }

    @Override
    public void addHeader(String name, String value) {
        super.addHeader(name, value);
        checkHeader(name);
    }

    @Override
    public void setIntHeader(String name, int value) {
        super.setIntHeader(name, value);
        checkHeader(name);
    }

    @Override
    public void addIntHeader(String name, int value) {
        super.addIntHeader(name, value);
        checkHeader(name);
    }

    private void checkHeader(String name) {
        if ("content-length".equalsIgnoreCase(name)) {
            didSetContentLength = true;
        }
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {

        if (writer != null) {
            throw new IllegalStateException(
                lStrings.getString("err.ise.getOutputStream"));
        }
        usingOutputStream = true;

        return noBody;
    }

    @Override
    public PrintWriter getWriter() throws UnsupportedEncodingException {

        if (usingOutputStream) {
            throw new IllegalStateException(
                lStrings.getString("err.ise.getWriter"));
        }

        if (writer == null) {
            OutputStreamWriter w = new OutputStreamWriter(
                noBody, getCharacterEncoding());
            writer = new PrintWriter(w);
        }

        return writer;
    }
}


/*
 * Servlet output stream that gobbles up all its data.
 */
// file private
class NoBodyOutputStream extends ServletOutputStream {

    private static final String LSTRING_FILE =
        "javax.servlet.http.LocalStrings";
    private static ResourceBundle lStrings =
        ResourceBundle.getBundle(LSTRING_FILE);

    private int contentLength = 0;

    // file private
    NoBodyOutputStream() {}

    // file private
    int getContentLength() {
        return contentLength;
    }

    @Override
    public void write(int b) {
        contentLength++;
    }

    @Override
    public void write(byte buf[], int offset, int len)
        throws IOException
    {
        if (buf == null) {
            throw new NullPointerException(
                    lStrings.getString("err.io.nullArray"));
        }

        if (offset < 0 || len < 0 || offset+len > buf.length) {
            String msg = lStrings.getString("err.io.indexOutOfBounds");
            Object[] msgArgs = new Object[3];
            msgArgs[0] = Integer.valueOf(offset);
            msgArgs[1] = Integer.valueOf(len);
            msgArgs[2] = Integer.valueOf(buf.length);
            msg = MessageFormat.format(msg, msgArgs);
            throw new IndexOutOfBoundsException(msg);
        }

        contentLength += len;
    }


    public boolean isReady() {
        return false;
    }

    public void setWriteListener(WriteListener writeListener) {

    }
}
