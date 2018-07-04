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

import java.io.IOException;

/**
 * クライアントからリクエストを受け取り、サーバー上の任意のリソース(サーブレット、HTMLファイル、JSPファイルなど)を送信するオブジェクトを定義します。
 * サーブレットコンテナは特定のパスまたは特定の名前で指定されたサーバーリソースのラッパーとして使用される<code>RequestDispatcher</code>オブジェクトを作成します。
 * 
 * <p>このインターフェースはサーブレットをラップするためのものですが、
 * サーブレットコンテナは任意の種類のリソースをラップするための<code>RequestDispatcher</code>オブジェクトを作成できます。
 *
 * @author Various
 *
 * @see ServletContext#getRequestDispatcher(java.lang.String)
 * @see ServletContext#getNamedDispatcher(java.lang.String)
 * @see ServletRequest#getRequestDispatcher(java.lang.String)
 */
 
public interface RequestDispatcher {

    /**
     * {@link #forward(ServletRequest,ServletResponse) forward}を使用したときにオリジナルのリクエストURIが使用可能になるリクエストの属性の名前
     */
    static final String FORWARD_REQUEST_URI = "javax.servlet.forward.request_uri";

    /**
     * {@link #forward(ServletRequest,ServletResponse) forward}を使用したときにオリジナルのコンテキストパスが使用可能になるリクエストの属性の名前
     */
    static final String FORWARD_CONTEXT_PATH = "javax.servlet.forward.context_path";

    /**
     * {@link #forward(ServletRequest,ServletResponse) forward}を使用したときにオリジナルの{@link javax.servlet.http.HttpServletMapping}が使用可能になるリクエストの属性の名前
     * 
     * @since 4.0
     */
    static final String FORWARD_MAPPING = "javax.servlet.forward.mapping";

    /**
     * {@link #forward(ServletRequest,ServletResponse) forward}を使用したときにオリジナルのパス情報が使用可能になるリクエストの属性の名前
     */
    static final String FORWARD_PATH_INFO = "javax.servlet.forward.path_info";

    /**
     * {@link #forward(ServletRequest,ServletResponse) forward}を使用したときにオリジナルのサーブレットパスが使用可能になるリクエストの属性の名前
     */
    static final String FORWARD_SERVLET_PATH = "javax.servlet.forward.servlet_path";

    /**
     * {@link #forward(ServletRequest,ServletResponse) forward}を使用したときにオリジナルのクエリー文字列が使用可能になるリクエストの属性の名前
     */
    static final String FORWARD_QUERY_STRING = "javax.servlet.forward.query_string";

    /**
     * {@link #include(ServletRequest,ServletResponse) include}を使用したときにオリジナルのリクエストURIが使用可能になるリクエストの属性の名前
     */
    static final String INCLUDE_REQUEST_URI = "javax.servlet.include.request_uri";

    /**
     * {@link #include(ServletRequest,ServletResponse) include}を使用したときにオリジナルのコンテキストパスが使用可能になるリクエストの属性の名前
     */
    static final String INCLUDE_CONTEXT_PATH = "javax.servlet.include.context_path";

    /**
     * {@link #include(ServletRequest,ServletResponse) include}を使用したときにオリジナルのパス情報が使用可能になるリクエストの属性の名前
     */
    static final String INCLUDE_PATH_INFO = "javax.servlet.include.path_info";

    /**
     * {@link #include(ServletRequest,ServletResponse) include}を使用したときにオリジナルの{@link javax.servlet.http.HttpServletMapping}が使用可能になるリクエストの属性の名前
     */
    static final String INCLUDE_MAPPING = "javax.servlet.include.mapping";

    /**
     * {@link #include(ServletRequest,ServletResponse) include}を使用したときにオリジナルのサーブレットパスが使用可能になるリクエストの属性の名前
     */
    static final String INCLUDE_SERVLET_PATH = "javax.servlet.include.servlet_path";

    /**
     * {@link #include(ServletRequest,ServletResponse) include}を使用したときにオリジナルのクエリー文字列が使用可能になるリクエストの属性の名前
     */
    static final String INCLUDE_QUERY_STRING = "javax.servlet.include.query_string";

    /**
     * エラーディスパッチのときに伝播されるExceptionオブジェクトが保存されるリクエストの属性の名前
     */
    public static final String ERROR_EXCEPTION = "javax.servlet.error.exception";

    /**
     * エラーディスパッチのときに伝播されるExceptionオブジェクトの型が保存されるリクエストの属性の名前
     */
    public static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";

    /**
     * エラーディスパッチのときに伝播されるExceptionのメッセージが保存されるリクエストの属性の名前
     */
    public static final String ERROR_MESSAGE = "javax.servlet.error.message";

    /**
     * エラーディスパッチのときに伝播されるエラーの原因となったリクエストURIが保存されるリクエストの属性の名前
     */
    public static final String ERROR_REQUEST_URI = "javax.servlet.error.request_uri";

    /**
     * エラーディスパッチのときに伝播されるエラーの原因となったサーブレットの名前が保存されるリクエストの属性の名前
     */
    public static final String ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name";

    /**
     * エラーディスパッチのときに伝播されるエラーの原因となったレスポンスのステータスコードが保存されるリクエストの属性の名前
     */
    public static final String ERROR_STATUS_CODE = "javax.servlet.error.status_code";


    /**
     * サーブレットからのリクエストをサーバー上の別のリソース(サーブレット、JSPファイル、またはHTMLファイル)に転送します。
     * このメソッドはあるサーブレットがリクエストの事前処理を行い、別のリソースがレスポンスを生成することを可能にします。
     *
     * <p><code>getRequestDispatcher()</code>で取得した<code>RequestDispatcher</code>に対して、
     * <code>ServletRequest</code>オブジェクトはターゲットリソースのパスと一致するように調整されたパス要素とパラメーターを持ちます。
     *
     * <p><code>forward</code>はレスポンスがクライアントにコミットされる前に(レスポンスボディの出力がフラッシュされる前に)呼び出される必要があります。
     * レスポンスがすでにコミットされている場合、このメソッドは<code>IllegalStateException</code>を投げます。
     * レスポンスバッファ内のコミットされていない出力は、転送の前に自動的にクリアされます。
     *
     * <p>リクエストとレスポンスのパラメーターは呼び出し側サーブレットのサービスメソッドに渡されたものと同じオブジェクトであるか、
     * それらをラップする{@link ServletRequestWrapper}クラスまたは{@link ServletResponseWrapper}クラスのサブクラスでなければなりません。
     *
     * <p>このメソッドは与えられたリクエストのdispatcher typeに<code>DispatcherType.FORWARD</code>を設定します。
     *
     * @param request サーブレットのクライアントが作成したリクエストを表す {@link ServletRequest} オブジェクト
     *
     * @param response サーブレットがクライアントへ返すレスポンスを表す {@link ServletResponse} オブジェクト
     *
     * @throws ServletException ターゲットのリソースがこの例外を投げた
     *
     * @throws IOException ターゲットのリソースがこの例外を投げた
     *
     * @throws IllegalStateException レスポンスがすでにコミットされている場合
     *
     * @see ServletRequest#getDispatcherType
     */
    public void forward(ServletRequest request, ServletResponse response)
        throws ServletException, IOException;

    /**
     * レスポンスにリソース(サーブレット、JSPページ、HTMLファイル)の内容を含めます。本質的に、このメソッドはプログラムによるサーバーサイドインクルードを可能にします。
     *
     * <p>{@link ServletResponse}オブジェクトには呼び出し元から変更されないままのパス要素やパラメーターを持ちます。
     * インクルードするサーブレットはレスポンスのステータスコードを変更したり、ヘッダーを設定することはできません。変更しようとする試みはすべて無視されます。
     *
     * <p>リクエストとレスポンスのパラメーターは呼び出し側サーブレットのサービスメソッドに渡されたものと同じオブジェクトであるか、
     * それらをラップする{@link ServletRequestWrapper}クラスまたは{@link ServletResponseWrapper}クラスのサブクラスでなければなりません。
     *
     * <p>このメソッドは与えられたリクエストのdispatcher typeに<code>DispatcherType.INCLUDE</code>を設定します。
     *
     * @param request クライアントのリクエストを含む {@link ServletRequest} オブジェクト
     *
     * @param response サーブレットのレスポンスを含む {@link ServletResponse} オブジェクト
     *
     * @throws ServletException インクルードしたリソースがこの例外を投げた
     *
     * @throws IOException インクルードしたリソースがこの例外を投げた
     *
     * @see ServletRequest#getDispatcherType
     */
    public void include(ServletRequest request, ServletResponse response)
        throws ServletException, IOException;
}








