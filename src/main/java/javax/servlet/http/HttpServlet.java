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
     * HEADリクエストはレスポンスとして本文がないものが返されるリクエストヘッダフィールドのみのGETリクエストです。
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
     *
     * Returns the time the <code>HttpServletRequest</code>
     * object was last modified,
     * in milliseconds since midnight January 1, 1970 GMT.
     * If the time is unknown, this method returns a negative
     * number (the default).
     *
     * <p>Servlets that support HTTP GET requests and can quickly determine
     * their last modification time should override this method.
     * This makes browser and proxy caches work more effectively,
     * reducing the load on server and network resources.
     *
     * @param req   サーブレットに送られた <code>HttpServletRequest</code> のオブジェクト
     *
     * @return  a   <code>long</code> integer specifying
     *                  the time the <code>HttpServletRequest</code>
     *                  object was last modified, in milliseconds
     *                  since midnight, January 1, 1970 GMT, or
     *                  -1 if the time is not known
     */

    protected long getLastModified(HttpServletRequest req) {
        return -1;
    }


    /**
     * 
     *
     * <p>Receives an HTTP HEAD request from the protected
     * <code>service</code> method and handles the
     * request.
     * The client sends a HEAD request when it wants
     * to see only the headers of a response, such as
     * Content-Type or Content-Length. The HTTP HEAD
     * method counts the output bytes in the response
     * to set the Content-Length header accurately.
     *
     * <p>If you override this method, you can avoid computing
     * the response body and just set the response headers
     * directly to improve performance. Make sure that the
     * <code>doHead</code> method you write is both safe
     * and idempotent (that is, protects itself from being
     * called multiple times for one HTTP HEAD request).
     *
     * <p>If the HTTP HEAD request is incorrectly formatted,
     * <code>doHead</code> returns an HTTP "Bad Request"
     * message.
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
     *
     * Called by the server (via the <code>service</code> method)
     * to allow a servlet to handle a POST request.
     *
     * The HTTP POST method allows the client to send
     * data of unlimited length to the Web server a single time
     * and is useful when posting information such as
     * credit card numbers.
     *
     * <p>When overriding this method, read the request data,
     * write the response headers, get the response's writer or output
     * stream object, and finally, write the response data. It's best 
     * to include content type and encoding. When using a
     * <code>PrintWriter</code> object to return the response, set the 
     * content type before accessing the <code>PrintWriter</code> object. 
     *
     * <p>The servlet container must write the headers before committing the
     * response, because in HTTP the headers must be sent before the 
     * response body.
     *
     * <p>Where possible, set the Content-Length header (with the
     * {@link javax.servlet.ServletResponse#setContentLength} method),
     * to allow the servlet container to use a persistent connection 
     * to return its response to the client, improving performance.
     * The content length is automatically set if the entire response fits
     * inside the response buffer.  
     *
     * <p>When using HTTP 1.1 chunked encoding (which means that the response
     * has a Transfer-Encoding header), do not set the Content-Length header. 
     *
     * <p>This method does not need to be either safe or idempotent.
     * Operations requested through POST can have side effects for
     * which the user can be held accountable, for example, 
     * updating stored data or buying items online.
     *
     * <p>If the HTTP POST request is incorrectly formatted,
     * <code>doPost</code> returns an HTTP "Bad Request" message.
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
     * Called by the server (via the <code>service</code> method)
     * to allow a servlet to handle a PUT request.
     *
     * The PUT operation allows a client to 
     * place a file on the server and is similar to 
     * sending a file by FTP.
     *
     * <p>When overriding this method, leave intact
     * any content headers sent with the request (including
     * Content-Length, Content-Type, Content-Transfer-Encoding,
     * Content-Encoding, Content-Base, Content-Language, Content-Location,
     * Content-MD5, and Content-Range). If your method cannot
     * handle a content header, it must issue an error message
     * (HTTP 501 - Not Implemented) and discard the request.
     * For more information on HTTP 1.1, see RFC 2616
     * <a href="http://www.ietf.org/rfc/rfc2616.txt"></a>.
     *
     * <p>This method does not need to be either safe or idempotent.
     * Operations that <code>doPut</code> performs can have side
     * effects for which the user can be held accountable. When using
     * this method, it may be useful to save a copy of the
     * affected URL in temporary storage.
     *
     * <p>If the HTTP PUT request is incorrectly formatted,
     * <code>doPut</code> returns an HTTP "Bad Request" message.
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
     * Called by the server (via the <code>service</code> method)
     * to allow a servlet to handle a DELETE request.
     *
     * The DELETE operation allows a client to remove a document
     * or Web page from the server.
     * 
     * <p>This method does not need to be either safe
     * or idempotent. Operations requested through
     * DELETE can have side effects for which users
     * can be held accountable. When using
     * this method, it may be useful to save a copy of the
     * affected URL in temporary storage.
     *
     * <p>If the HTTP DELETE request is incorrectly formatted,
     * <code>doDelete</code> returns an HTTP "Bad Request"
     * message.
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
     * Called by the server (via the <code>service</code> method)
     * to allow a servlet to handle a OPTIONS request.
     *
     * The OPTIONS request determines which HTTP methods 
     * the server supports and
     * returns an appropriate header. For example, if a servlet
     * overrides <code>doGet</code>, this method returns the
     * following header:
     *
     * <p><code>Allow: GET, HEAD, TRACE, OPTIONS</code>
     *
     * <p>There's no need to override this method unless the
     * servlet implements new HTTP methods, beyond those 
     * implemented by HTTP 1.1.
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
     * Called by the server (via the <code>service</code> method)
     * to allow a servlet to handle a TRACE request.
     *
     * A TRACE returns the headers sent with the TRACE
     * request to the client, so that they can be used in
     * debugging. There's no need to override this method. 
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
