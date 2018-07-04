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

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EnumSet;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;
import javax.servlet.descriptor.JspConfigDescriptor;

/**
 * サーブレットがサーブレットコンテナと通信するために使用する一連のメソッドを定義します。
 * 例として、ファイルのMIMEタイプを取得したり、要求をディスパッチしたり、ログファイルに書き込んだりすることができます。
 * 
 * <p>Java仮想マシンごとの"ウェブアプリケーション"ごとに1つのコンテキストがあります。
 * ("ウェブアプリケーション"は、<code>/catalog</code>などのようにサーバーのURL名前空間の特定のサブセットの下に<code>.war</code>ファイル経由でインストールことができるサーブレットとコンテンツの集合です。)
 * 
 * <p>デプロイメントディスクリプタに"distributed"とつけられたウェブアプリケーションの場合は、各仮想マシンごとに1つのコンテキストインスタンスが存在します。
 * この状況では、グローバルな情報を共有するための場所として<code>ServletContext</code>を使用することはできません（その情報は真にグローバルではない方が良いため）。
 * 代わりにデータベースのような外部リソースを使用してください。
 * 
 * <p><code>ServletContext</code>オブジェクトは{@link ServletConfig}オブジェクト内に含まれており、サーブレットが初期化されたときにウェブサーバーがそれらを提供します。
 *
 * @author 	Various
 *
 * @see 	Servlet#getServletConfig
 * @see 	ServletConfig#getServletContext
 */

public interface ServletContext {

    /**
     * <tt>ServletContext</tt>のためにサーブレットコンテナによって提供される(<tt>java.io.File</tt>e型の)プライベートテンポラリディレクトリを格納する<tt>ServletContext</tt>の属性の名です。
     */
    public static final String TEMPDIR = "javax.servlet.context.tempdir";


    /**
     * ウェブフラグメント名で順序付けられた(なし<code>&lt;others/&gt;</code>が使用されていない<code>&lt;absolute-ordering&gt;</code>の場合は除外されることがある)
     * <code>WEB-INF/lib</code>内のJARファイルの名前のリストを含む(<code>java.util.List&lt;java.lang.String&gt;</code>型の)JARファイルの名前のリストを持つ<code>ServletContext</code>の属性の名前です。
     * 絶対順序または相対順序が指定されていない場合はnullです。
     */
    public static final String ORDERED_LIBS =
        "javax.servlet.context.orderedLibs";


    /**
     * このウェブアプリケーションのコンテキストパスを返します。
     *
     * <p>コンテキストパスはリクエストのコンテキストを選択するために使用されるリクエストURIの一部分です。
     * コンテキストパスは、常にリクエストURIの最初に来ます。
     * このコンテキストがWebサーバーのURL名前空間の基底にマッピングされる"ルート"コンテキストの場合このパスは空の文字列になります。
     * それ以外の場合、コンテキストがサーバーの名前空間の基底にマッピングされていない場合、パスは"/"で始まりますが"/"で終わることはありません。
     * 
     * <p>サーブレットコンテナは一つのコンテキストを複数のコンテキストパスにて一致させることができます。
     * このような場合、{@link javax.servlet.http.HttpServletRequest#getContextPath()}はリクエストによって使用される実際のコンテキストパスを返し、
     * このメソッドによって返されるパスとは異なる場合があります。
     * このメソッドによって返されるコンテキストパスはアプリケーションのプライマリもしくは優先コンテキストパスと見なす必要があります。
     *
     * @return ウェブアプリケーションのコンテキスパス、ルートコンテキストの場合は""
     *
     * @see javax.servlet.http.HttpServletRequest#getContextPath()
     *
     * @since Servlet 2.5
     */
    public String getContextPath();


    /**
     * サーバー上の指定されたURLに対応する<code>ServletContext</code>オブジェクトを返します。
     * 
     * <p>このメソッドは、サーブレットがサーバーのさまざまな部分のコンテキストにアクセスできるようにし、
     * 必要に応じてコンテキストから{@link RequestDispatcher}オブジェクトを取得できるようにします。
     * 指定されたパスは<tt>/</tt>で始まる必要があり、
     * サーバーのドキュメントルートからの相対パスとして解釈され、
     * このコンテナでホストされている他のWebアプリケーションのコンテキストルートと照合されます。
     * 
     * <p>セキュリティを意識した環境ではサーブレットコンテナは指定されたURLに対して<code>null</code>を返すことがあります。
     *
     * @param uripath 	このコンテナ内のほかのウェブアプリケーションのコンテキストパスを示す<code>String</code>
     * @return		URLの名前に対応する<code>ServletContext</code>のオブジェクト、存在しないかコンテナがアクセス制限を臨んだ場合はnull
     *
     * @see 		RequestDispatcher
     */
    public ServletContext getContext(String uripath);


    /**
     * このサーブレットコンテナがサポートするServlet APIのメジャーバージョンを返します。
     * バージョン4.0に準拠するすべての実装でこのメソッドは4を返す必要があります。
     *
     * @return 4
     */
    public int getMajorVersion();


    /**
     * このサーブレットコンテナがサポートするServlet APIのマイナーバージョンを返します。
     * バージョン4.0に準拠するすべての実装でこのメソッドは0を返す必要があります。
     *
     * @return 0
     */
    public int getMinorVersion();


    /**
     * このServletContextが表すアプリケーションが基づいているServlet仕様のメジャーバージョンを取得します。
     * 
     * <p>返される値はサーブレットコンテナがサポートするサーブレット仕様のメジャーバージョンを返す{@link #getMajorVersion}とは異なる場合があります。
     *
     * @return このServletContextが表すアプリケーションが基づいているServlet仕様のメジャーバージョン
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 3.0
     */
    public int getEffectiveMajorVersion();


    /**
     * このServletContextが表すアプリケーションが基づいているServlet仕様のマイナーバージョンを取得します。
     * 
     * <p>返される値はサーブレットコンテナがサポートするサーブレット仕様のマイナーバージョンを返す{@link #getMinorVersion}とは異なる場合があります。
     *
     * @return このServletContextが表すアプリケーションが基づいているServlet仕様のマイナーバージョン
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 3.0
     */
    public int getEffectiveMinorVersion();


    /**
     * 指定されたファイルのMIMEタイプを返します。MIMEタイプが不明な場合はnullを返します。
     * MIMEタイプはサーブレットコンテナの設定とアプリケーションのデプロイメントディスクリプタでの指定で決められます。
     * 一般的なMIMEタイプとしては<code>text/html</code>や<code>image/gif</code>を含みます。
     *
     * @param file ファイルの名前を示す<code>String</code>
     *
     * @return ファイルのMIMEタイプを示す<code>String</code>
     */
    public String getMimeType(String file);


    /**
     * 最も長いサブパスが指定された引数のパスと一致するウェブアプリケーション内のリソースへのディレクトリに似た形式ですべてのパスのリストを返します。

     *
     * <p>サブディレクトリパスを示すパスは<tt>/</tt>で終わります。
     *
     * <p>返されるパスは<tt>/</tt>から始まる、ウェブアプリケーションのルートからの相対パス、
     * もしくはウェブアプリケーションの<tt>/WEB-INF/lib</tt>ディレクトリ内のJARファイルの<tt>/META-INF/resources</tt>ディレクトリからの相対パスです。
     *
     * <p>返されたSetは{@code ServletContext}オブジェクトによって追跡されないため、
     * 返されたSetの変更は{@code ServletContext}オブジェクトに反映されず、その逆もそうです。</p>
     *
     * <p>例として、ウェブアプリケーションが以下のとおりファイルを含むとします。
     *
     * <pre>{@code
     *   /welcome.html
     *   /catalog/index.html
     *   /catalog/products.html
     *   /catalog/offers/books.html
     *   /catalog/offers/music.html
     *   /customer/login.jsp
     *   /WEB-INF/web.xml
     *   /WEB-INF/classes/com.acme.OrderServlet.class
     *   /WEB-INF/lib/catalog.jar!/META-INF/resources/catalog/moreOffers/books.html
     * }</pre>
     *
     * <tt>getResourcePaths("/")</tt>は
     * <tt>{"/welcome.html", "/catalog/", "/customer/", "/WEB-INF/"}</tt>を返し、
     * <tt>getResourcePaths("/catalog/")</tt>は
     * <tt>{"/catalog/index.html", "/catalog/products.html",
     * "/catalog/offers/", "/catalog/moreOffers/"}</tt>を返すでしょう。
     *
     * @param path <tt>/</tt>から始まる必要のあるマッチしたリソースで使用されている部分的なパス
     * 
     * @return ディレクトリ内を一覧化したものを含むSet、ウェうアプリケーション内に指定されたパスで始まるリソースが存在しない場合はnull
     * a Set containing the directory listing, or null if there
     * are no resources in the web application whose path
     * begins with the supplied path.
     *
     * @since Servlet 2.3
     */
    public Set<String> getResourcePaths(String path);


    /**
     * 指定されたパスにマッピングされたリソースへのURLを返します。
     *
     * <p>パスは<tt>/</tt>で始まり、現在のコンテキストルートからの相対パスとして解決できるか
     * ウェブアプリケーションの<tt>/WEB-INF/lib</tt>ディレクトリ内のJARファイルの<tt>/META-INF/resources</tt>ディレクトリからの相対パス
     * である必要があります。
     * このメソッドはリクエストされたリソースを探すのに、最初にウェブアプリケーションのドキュメントルートを参照します。
     * 次に<tt>/WEB-INF/lib</tt>の中のJARファイルの中身を参照します。
     * <tt>/WEB-INF/lib</tt>の中のJARファイルを探す順番は未定義です。
     * 
     * <p>このメソッドを使用するとサーブレットコンテナは任意のソースからサーブレットでリソースを使用できるようになります。
     * リソースは、ローカルやリモートのファイルシステム、データベース、<code>.war</code>ファイル内に配置できます。
     *
     * <p>サーブレットコンテナはリソースにアクセスするために必要なURLハンドラや<code>URLConnection</code>オブジェクトを実装する必要があります。
     *
     * <p>このメソッドはパスにリソースがマップされていない場合は<code>null</code>を返します。
     *
     * <p>一部のコンテナではURLクラスのメソッドを使用することでこのメ​​ソッドから返されたURLに書き込むことができます。
     *
     * <p>リソースの内容が直接返されるため、<code>.jsp</code>ページを要求するとJSPのソースコードが返されることに注意してください。
     * 実行結果をインクルードするには代わりに<code>RequestDispatcher</code>を使用します。
     *
     * <p>このメソッドはクラスローダーをベースにリソースを探す<code>java.lang.Class.getResource</code>とは目的が違います。
     * このメソッドはクラスローダーを使用しません。
     *
     * @param path リソースのパスを示す<code>String</code>
     *
     * @return パスの位置のリソース、そのパスにリソースが存在しない場合は<code>null</code>
     *
     * @exception MalformedURLException パスが正しい形式でなかった
     */
    public URL getResource(String path) throws MalformedURLException;


    /**
     * パスの所在地のリソースを<code>InputStream</code>オブジェクトとして返します。
     *
     * <p><code>InputStream</code>内のデータはどのようなタイプでも長さでもできます。
     * パスは<code>getResource</code>と同じルールに従って指定される必要があります。
     * このメソッドは指定されたパスにリソースが存在しない場合は<code>null</code>を返します。
     *
     * <p><code>getResource</code>メソッドで使用可能なコンテンツタイプやコンテンツの長さなどのようなメタ情報はこのメソッドを使用すると失われます。
     *
     * <p>サーブレットコンテナはリソースにアクセスするために必要なURLハンドラや<code>URLConnection</code>オブジェクトを実装する必要があります。
     *
     * <p>このメソッドはクラスローダーを使用する<code>java.lang.Class.getResourceAsStream</code>とは違います。
     * このメソッドはサーブレットコンテナにクラスローダーを使用せずに任意の場所からサーブレットでリソースを利用できるようにします。
     *
     *
     * @param path 	リソースのパスを示す<code>String</code>
     *
     * @return 		サーブレットに返す<code>InputStream</code>、指定されたパスにリソースが存在しない場合は<code>null</code>
     */
    public InputStream getResourceAsStream(String path);


    /**
     * 指定されたパスにあるリソースのラッパーとして機能する{@link RequestDispatcher}のオブジェクトを返します。 
     * <code>RequestDispatcher</code>オブジェクトを使用することでリクエストを別のリソースに転送したり、リソースをレスポンスに含めることができます。
     * リソースは動的なものでも静的なものでもかまいません。
     * 
     * <p>指定されたパス名は<tt>/</tt>で始まる必要があり、現在のコンテキストルートからの相対パスとして解釈されます。
     * 
     * <p>このメソッドは<code>ServletContext</code>が<code>RequestDispatcher</code>を返せなかった場合は<code>null</code>を返します。
     *
     * @param path 	リソースのパス名を示す<code>String</code>
     *
     * @return 		指定されたパスにあるリソースのラッパーとして機能する<code>RequestDispatcher</code>のオブジェクト、
     *              <code>ServletContext</code>が<code>RequestDispatcher</code>を返せなかった場合は<code>null</code>
     *
     * @see 		RequestDispatcher
     * @see 		ServletContext#getContext
     */
    public RequestDispatcher getRequestDispatcher(String path);


    /**
     * 指定された名前のサーブレットのラッパーとして動作する{@link RequestDispatcher}オブジェクトを返します。
     *
     * <p>サーブレット(およびJSPページ)にはサーバー管理やウェブアプリケーションのデプロイメントディスクリプタを使用して名前を付けることができます。 
     * サーブレットのインスタンスは{@link ServletConfig#getServletName}を使用してその名前を取得できます。
     *
     * <p>このメソッドは<code>ServletContext</code>が何らかの理由で<code>RequestDispatcher</code>を返せなかった場合は<code>null</code>を返します。
     *
     * @param name 	ラップするサーブレットの名前を示す<code>String</code>
     *
     * @return 		指定された名前のサーブレットのラッパーとして動作する<code>RequestDispatcher</code>オブジェクト、
     *              <code>ServletContext</code>が<code>RequestDispatcher</code>を返せなかった場合は<code>null</code>
     *
     * @see 		RequestDispatcher
     * @see 		ServletContext#getContext
     * @see 		ServletConfig#getServletName
     */
    public RequestDispatcher getNamedDispatcher(String name);


    /**
     * @deprecated	Java Servlet API 2.1以降での直接の代替手段はありません。
     *
     * <p>このメソッドはもともとは<code>ServletContext</code>からサーブレットを受け取るために定義されていました。
     * このバージョンでは、このメソッドは必ず<code>null</code>を返し、バイナリ互換性のためにのみ残されています。
     * このメソッドはJava Servlet APIの未来のバージョンでは完全に削除される予定です。
     *
     * <p>このメソッドの代わりに、サーブレットは<code>ServletContext</code>クラスを利用して情報を共有し、
     * 一般的なサーブレットでないクラスのメソッドを呼び出すことで共有したビジネスロジックを実行できます。
     * In lieu of this method, servlets can share information using the
     * <code>ServletContext</code> class and can perform shared business logic
     * by invoking methods on common non-servlet classes.
     *
     * @param name サーブレットの名前
     * @return 指定された名前の {@code javax.servlet.Servlet Servlet}
     * @throws ServletException サーブレットの通常の処理で例外が発生した
     */
    @Deprecated
    public Servlet getServlet(String name) throws ServletException;


    /**
     * @deprecated	Java Servlet API 2.0以降での代替手段はありません。
     *
     * <p>このメソッドはもともとは<code>ServletContext</code>に登録されたすべてのサーブレットの<code>Enumeration</code>を返すものとして定義されていました。
     * このバージョンでは、このメソッドは必ず空の<code>Enumeration</code>を返し、バイナリ互換性のためにのみ残されています。
     * このメソッドはJava Servlet APIの未来のバージョンでは完全に削除される予定です。
     *
     * @return {@code javax.servlet.Servlet Servlet}の<code>Enumeration</code>
     */
    @Deprecated
    public Enumeration<Servlet> getServlets();


    /**
     * @deprecated	Java Servlet API 2.1以降での代替手段はありません。
     *
     * <p>このメソッドはもともとは<code>ServletContext</code>に登録されたすべてのサーブレット名前の<code>Enumeration</code>を返すものとして定義されていました。
     * このバージョンでは、このメソッドは必ず空の<code>Enumeration</code>を返し、バイナリ互換性のためにのみ残されています。
     * このメソッドはJava Servlet APIの未来のバージョンでは完全に削除される予定です。
     *
     * @return {@code javax.servlet.Servlet Servlet}の名前の<code>Enumeration</code>
     */
    @Deprecated
    public Enumeration<String> getServletNames();


    /**
     * 指定されたメッセージをログファイルに書き込みます、通常はイベントログです。
     * ログファイルの名前と書式はサーブレットコンテナによって固有のものです。
     *
     * @param msg 	ログファイルに書き込まれるメッセージを示す<code>String</code>
     */
    public void log(String msg);


    /**
     * @deprecated	Java Servlet API 2.1から代わりに
     * 			{@link #log(String message, Throwable throwable)}
     *			を使用してください
     *
     * <p>このメソッドはもともとは例外のスタックトレースと例外の説明文をサーブレットのログファイルに書き込むために定義されていました。
     *
     * @param exception <code>Exception</code> エラー
     * @param msg 例外を説明する<code>String</code>
     */
    @Deprecated
    public void log(Exception exception, String msg);


    /**
     * サーブレットのログファイルに指定された<code>Throwable</code>に対する説明文とスタックトレースを書き込みます。
     * ログファイルの名前と書式はサーブレットコンテナによって固有のものですが、通常はイベントログです。
     *
     * @param message 		エラーや例外を説明する<code>String</code>
     *
     * @param throwable 	エラーは例外の<code>Throwable</code>
     */
    public void log(String message, Throwable throwable);


    /**
     * 指定された<i>仮想の</i>パスに対応する<i>実際の</i>パスを返します。
     *
     * <p>例えば、<tt>path</tt>がリクエストからの形式が<tt>http://&lt;host&gt;:&lt;port&gt;/&lt;contextPath&gt;/index.html</tt>
     * (<tt>&lt;contextPath&gt;</tt>はこのServletContextに対応するコンテキストパス)にマップされている<tt>/index.html</tt>と等しいとき、
     * このメソッドはサーバーのファイルシステムの絶対ファイルパスを返すでしょう。
     *
     * <p>返される実際のパスは適切なパス区切り文字を含め、
     * サーブレットコンテナが実行されているコンピュータとオペレーティングシステムに適した形式になります。
     * 
     * <p>アプリケーションの<tt>/WEB-INF/lib</tt>ディレクトリ内にバンドルされるJARファイル内の<tt>/META-INF/resources</tt>
     * に含まれるリソースはコンテナが含まれるJARファイルをアンパックしたときにのみ考慮される必要があります。
     * その場合は、パスとしてアンパックされた位置が返されるべきです。
     *
     * <p>サーブレットコンテナが指定された指定された<i>仮想の</i>パスを<i>実際の</i>パスに変換できなかった場合は<code>null</code>を返します。
     *
     * @param path <i>実際の</i>パスに変換する<i>仮想の</i>パス
     *
     * @return <i>実際の</i>パス、変換が動作しない場合は<tt>null</tt>
     */
    public String getRealPath(String path);


    /**
     * サーブレットが動作しているサーブレットコンテナの名前とバージョンを返します。
     *
     * <p>返される文字列の形式は<i>servername</i>/<i>versionnumber</i>です。
     * 例えば、JavaServer Web Development Kitは<code>JavaServer Web Dev Kit/1.0</code>を返します。
     *
     * <p>サーブレットコンテナはプライマリ文字列の後ろのカッコ内に<code>JavaServer Web Dev Kit/1.0 (JDK 1.1.6; Windows NT 4.0 x86)</code>
     * のように追加の情報を出力するかもしれません。
     *
     *
     * @return 		最低でもサーブレットコンテナの名前とバージョン番号が含まれる<code>String</code>
     */
    public String getServerInfo();


    /**
     * 指定されたコンテキスト全体のための初期化パラメーターの値を含む<code>String</code> を返します。パラメーターが存在しない場合はnullを返します。
     * 
     * <p>このメソッドは、ウェブアプリケーション全体に有用な設定情報を利用可能にすることができます。
     * たとえば、ウェブマスターの電子メールアドレスまたは重要なデータを保持するシステムの名前を提供することができます。
     *
     * @param	name	値が要求されているパラメーターの名前を含む<code>String</code>
     *
     * @return コンテキストの初期化パラメーターの値を含む<code>String</code>。コンテキストの初期化パラメーターが存在しない場合は<code>null</code>
     *
     * @throws NullPointerException 引数の{@code name}が{@code null}
     *
     * @see ServletConfig#getInitParameter
     */
    public String getInitParameter(String name);


    /**
     * コンテキストの初期化パラメーターの名前を<code>String</code>オブジェクトの<code>Enumeration</code>として返します。
     * コンテキストに初期化パラメーターがない場合は空の<code>Enumeration</code>を返します。
     *
     * @return 		コンテキストの初期化パラメーターの名前を含む<code>String</code>オブジェクトの<code>Enumeration</code>
     *
     * @see ServletConfig#getInitParameter
     */
    public Enumeration<String> getInitParameterNames();


    /**
     * このServletContextのコンテキスト初期化パラメーターの値を指定された名前で設定します。
     *
     * @param name 設定するコンテキスト初期化パラメーターの名前
     * @param value 設定するコンテキスト初期化パラメーターの値
     *
     * @return このServletContextのコンテキスト初期化パラメーターを指定された名前と値で正常に設定できた場合はtrue、
     *         ServletContextにすでに指定された名前で値がすでに存在していた場合は設定できずfalse
     *
     * @throws IllegalStateException このServletContextが初期化完了していた場合
     *
     * @throws NullPointerException パラメーターの名前が{@code null}
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 3.0
     */
    public boolean setInitParameter(String name, String value);


    /**
     * 指定された名前のサーブレットコンテナの属性を返します。属性が存在しない場合は<code>null</code>を返します。
     *
     * <p>属性を使用することでサーブレットコンテナはこのインターフェースによってまだ提供されていない追加情報をサーブレットに与えることができます。
     * 属性の情報についてはサーバーのドキュメントを参照してください。
     * サポートされている属性の一覧は<code>getAttributeNames</code>を使用して取得できます。
     *
     * <p>属性は<code>java.lang.Object</code>か、そのサブクラスとして返されます。
     *
     * <p>属性名は、パッケージ名と同じ規則に従う必要があります。
     * Java Servlet API specificationでは<code>java.*</code>、<code>javax.*</code>、<code>sun.*</code>と一致する名前を予約しています。
     *
     * @param name 	属性の名前を指定する<code>String</code>
     *
     * @return 属性の値を含む<code>Object</code>、指定された名前と一致する属性が存在しない場合はnull
     *
     * @see 		ServletContext#getAttributeNames
     *
     * @throws NullPointerException 引数の{@code name}が{@code null}
     *
     */
    public Object getAttribute(String name);


    /**
     * このServletContextで使用可能な属性の名前の含まれた<code>Enumeration</code>を返します。
     *
     * <p>属性の名前から値を取得するためには{@link #getAttribute}メソッドを使用してください。
     *
     * @return 		属性の名前の<code>Enumeration</code>
     *
     * @see		#getAttribute
     */
    public Enumeration<String> getAttributeNames();


    /**
     * このServletContextに指定された属性の名前でオブジェクトを設定します。
     * もし指定された名前がすでに属性として使用されている場合は、このメソッドは新しい属性として置き換えます。
     * <p><code>ServletContext</code>にリスナーが設定されている場合、コンテナはリスナーに応じてそれらを通知します。

     * <p>もし値としてnullが渡された場合、<code>removeAttribute()</code>を呼び出した場合と同じ効果を及ぼします。
     *
     * <p>属性名は、パッケージ名と同じ規則に従う必要があります。
     * Java Servlet API specificationでは<code>java.*</code>、<code>javax.*</code>、<code>sun.*</code>と一致する名前を予約しています。
     *
     * @param name 	属性の名前を指定する<code>String</code>
     *
     * @param object 	属性に設定する値を表す<code>Object</code>
     *
     * @throws NullPointerException パラメーター名が{@code null}
     *
     */
    public void setAttribute(String name, Object object);


    /**
     * このServletContextから指定された名前の属性を取り除きます。
     * 取り除かれた後、続いて{@link #getAttribute}を呼び出して属性の値を取得しようとすると<code>null</code>を返すでしょう。
     *
     * <p><code>ServletContext</code>にリスナーが設定されている場合、コンテナはリスナーに応じてそれらを通知します。
     *
     * @param name	取り除かれる属性の名前を指定する<code>String</code>
     */
    public void removeAttribute(String name);


    /**
     * ウェブアプリケーションのデプロイメントディスクリプタのdisplay-name要素で指定されているこのServletContext
     * に対応するこのウェブアプリケーションの名前を返します。
     *
     * @return このウェブアプリケーションの名前、デプロイメントディスクリプタで宣言されてない場合はnull
     *
     * @since Servlet 2.3
     */
    public String getServletContextName();


    /**
     * このServletContextに指定された名前とクラス名でサーブレットを追加します。
     * 
     * <p>登録されたサーブレットは返された{@link ServletRegistration}オブジェクトで詳細な設定を行うこともできます。
     * 
     * <p>指定された<tt>className</tt>はこのサーブレットコンテキストに対応するウェブアプリケーションに関連付けられたクラスローダーを使用してロードされます。
     * 
     * <p>ServletContextが指定された<tt>servletName</tt>のサーブレットのための準備段階のServletRegistrationをすでに含んでいた場合、
     * ServletRegistrationは(指定された<tt>className</tt>を割り当てることで)完了し、返します。
     *
     * <p>このメソッドは指定された<tt>className</tt>のクラスについている
     * {@link javax.servlet.annotation.ServletSecurity}、
     * {@link javax.servlet.annotation.MultipartConfig}、
     * <tt>javax.annotation.security.RunAs</tt>、
     * <tt>javax.annotation.security.DeclareRoles</tt>アノテーションの情報を読み取り、処理します。
     * 加えて、このメソッドは指定された<tt>className</tt>がマネージドビーンを表す場合、リソースインジェクションをサポートします。
     * マネージドビーンとリソースインジェクションについての詳細はJava EEプラットフォームとJSR 299の仕様を参照してください。
     * 
     * @param servletName サーブレットの名前
     * @param className サーブレットの完全修飾クラス名(FQCN)
     *
     * @return 登録されたサーブレットを詳細に設定するためのServletRegistrationオブジェクト、
     * ServletContextにすでに指定された<tt>servletName</tt>のサーブレットのためのServletRegistrationが完了したものが含まれている場合は<tt>null</tt>
     *
     * @throws IllegalStateException このServletContextが初期化完了している場合
     *
     * @throws IllegalArgumentException <code>servletName</code>がnullもしくは空の文字列
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 3.0
     */
    public ServletRegistration.Dynamic addServlet(
        String servletName, String className);


    /**
     * 指定されたサーブレットのインスタンスをこのServletContextに指定された<tt>servletName</tt>の下で登録します。
     *
     * <p>登録されたサーブレットは返された{@link ServletRegistration}オブジェクトで詳細に設定することができます。
     * 
     * <p>ServletContextが指定された<tt>servletName</tt>のサーブレットのための準備段階のServletRegistrationをすでに含んでいた場合、
     * ServletRegistrationは(指定されたサーブレットのインスタンスを指定された<tt>className</tt>で割り当てることで)完了し、返します。
     *
     * @param servletName サーブレットの名前
     * @param servlet 登録するサーブレットのインスタンス
     *
     * @return 指定されたサーブレットを詳細に設定するために使用されるServletRegistrationのオブジェクト、
     * ServletContextが指定された<tt>servletName</tt>のサーブレットのための完了したServletRegistrationを含んでいる場合や
     * 同じサーブレットのインスタンスがこのServletContextや同じコンテナのほかのServletContextにすでに含まれている場合は<tt>null</tt>
     *
     * @throws IllegalStateException このServletContextが初期化完了している場合
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @throws IllegalArgumentException 指定されたサーブレットのインスタンスが{@link SingleThreadModel}を実装しているか、<code>servletName</code>がnullもしくは空の文字列
     *
     * @since Servlet 3.0
     */
    public ServletRegistration.Dynamic addServlet(
        String servletName, Servlet servlet);


    /**
     * 指定された名前とクラスタイプをこのServletContextに追加します。
     *
     * <p>登録されたサーブレットは返された{@link ServletRegistration}オブジェクトで詳細に設定することができます。
     *
     * <p>ServletContextが指定された<tt>servletName</tt>のサーブレットのための準備段階のServletRegistrationをすでに含んでいた場合、
     * ServletRegistrationは(指定された<tt>servletClass</tt>を<tt>className</tt>で割り当てることで)完了し、返します。
     *
     * <p>このメソッドは指定された<tt>servletClass</tt>についている
     * {@link javax.servlet.annotation.ServletSecurity}、
     * {@link javax.servlet.annotation.MultipartConfig}、
     * <tt>javax.annotation.security.RunAs</tt>、
     * <tt>javax.annotation.security.DeclareRoles</tt>アノテーションの情報を読み取り、処理します。
     * 加えて、このメソッドは指定された<tt>className</tt>がマネージドビーンを表す場合、リソースインジェクションをサポートします。
     * マネージドビーンとリソースインジェクションについての詳細はJava EEプラットフォームとJSR 299の仕様を参照してください。
     *
     * @param servletName サーブレットの名前
     * @param servletClass インスタンス化されるサーブレットのクラスオブジェクト
     *
     * @return 登録されたサーブレットを詳細に設定するためのServletRegistrationオブジェクト、
     * ServletContextにすでに指定された<tt>servletName</tt>のサーブレットのためのServletRegistrationが完了したものが含まれている場合は<tt>null</tt>
     *
     * @throws IllegalStateException このServletContextが初期化完了している場合
     *
     * @throws IllegalArgumentException <code>servletName</code>がnullもしくは空の文字列
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 3.0
     */
    public ServletRegistration.Dynamic addServlet(String servletName,
        Class <? extends Servlet> servletClass);


    /**
     * 指定されたJSPファイルのサーブレットをこのServletContextに追加します。
     *
     * <p>登録されたサーブレットは返された{@link ServletRegistration}オブジェクトで詳細に設定することができます。
     *
     * <p>ServletContextが指定された<tt>servletName</tt>のサーブレットのための準備段階のServletRegistrationをすでに含んでいた場合、
     * ServletRegistrationは(指定された<tt>jspFile</tt>を割り当てることで)完了し、返します。
     *
     * @param servletName サーブレットの名前
     * @param jspFile "/"で始まるウェブアプリケーション内のJSPファイルのフルパス
     *
     * @return 登録されたサーブレットを詳細に設定するためのServletRegistrationオブジェクト、
     * ServletContextにすでに指定された<tt>servletName</tt>のサーブレットのためのServletRegistrationが完了したものが含まれている場合は<tt>null</tt>
     *
     * @throws IllegalStateException このServletContextが初期化完了している場合
     *
     * @throws IllegalArgumentException <code>servletName</code>がnullもしくは空の文字列
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 4.0
     */
    public ServletRegistration.Dynamic addJspFile(
        String servletName, String jspFile);


    /**
     * 指定されたサーブレットクラスをインスタンス化します。
     *
     * <p>返されたサーブレットのインスタンスはServletContextの{@link #addServlet(String,Servlet)}を呼び出して登録する前に詳細なカスタマイズができます。
     *
     * <p>指定されたサーブレットはインスタンス化するために使用される引数のないコンストラクタが定義されている必要があります。
     *
     * <p>このメソッドは指定された<tt>clazz</tt>についている
     * {@link javax.servlet.annotation.ServletSecurity}、
     * {@link javax.servlet.annotation.MultipartConfig}、
     * <tt>javax.annotation.security.RunAs</tt>、
     * <tt>javax.annotation.security.DeclareRoles</tt>アノテーションの情報を読み取り、処理します。
     * 加えて、このメソッドは指定された<tt>clazz</tt>がマネージドビーンを表す場合、リソースインジェクションをサポートします。
     * マネージドビーンとリソースインジェクションについての詳細はJava EEプラットフォームとJSR 299の仕様を参照してください。
     * @param <T> 作られるサーブレットのクラス
     * @param clazz インスタンス化されるサーブレットクラス
     *
     * @return 新しいサーブレットのインスタンス
     *
     * @throws ServletException 指定された<tt>clazz</tt>のインスタンス化に失敗した
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 3.0
     */
    public <T extends Servlet> T createServlet(Class<T> clazz)
        throws ServletException;


    /**
     * 指定された<tt>servletName</tt>のサーブレットに対応するServletRegistrationを取得します。
     *
     * @return 指定された<tt>servletName</tt>のサーブレットの(完了、もしくは準備段階の)ServletRegistration、
     * その名前の下でServletRegistrationが存在しない場合はnull
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @param servletName サーブレットの名前
     * @since Servlet 3.0
     */
    public ServletRegistration getServletRegistration(String servletName);


    /**
     * このServletContextに登録されているすべてのサーブレットに対応する(サーブレットの名前がキーで空の可能性のある)ServletRegistrationオブジェクトのMapを取得します。
     *
     * <p>返されたMapは設定ファイルで定義されたサーブレットやアノテーションが付けられたサーブレットに対応するServletRegistrationオブジェクトと同じように
     * <tt>addServlet</tt>メソッドのいずれかや <tt>addJspFile</tt>メソッドで追加されたすべてに対応するすべてのServletRegistrationオブジェクトを含みます。
     *
     * <p>許可されている場合、返されたMapへの変更はこのServletContextに影響してはなりません。
     *
     * @return このServletContextに現在登録されているすべてのサーブレットに対応する(完了、もしくは準備段階の)ServletRegistrationオブジェクトのMap
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 3.0
     */
    public Map<String, ? extends ServletRegistration> getServletRegistrations();


    /**
     * このServletContextに指定された名前とクラス名でフィルターを追加します。
     * 
     * <p>登録されたフィルターは返された{@link FilterRegistration}オブジェクトで詳細な設定を行うこともできます。
     * 
     * <p>指定された<tt>className</tt>はこのサーブレットコンテキストに対応するウェブアプリケーションに関連付けられたクラスローダーを使用してロードされます。

     * <p>ServletContextが指定された<tt>filterName</tt>のフィルターのための準備段階のFilterRegistrationをすでに含んでいた場合、
     * FilterRegistrationは(指定された<tt>className</tt>を割り当てることで)完了し、返します。
     *
     * <p>このメソッドは指定された<tt>className</tt>のクラスがマネージドビーンを表す場合、リソースインジェクションをサポートします。
     * マネージドビーンとリソースインジェクションについての詳細はJava EEプラットフォームとJSR 299の仕様を参照してください。
     * 
     * @param filterName フィルターの名前
     * @param className フィルターの完全修飾クラス名(FQCN)
     *
     * @return 登録されたフィルターを詳細に設定するためのFilterRegistrationオブジェクト、
     * ServletContextにすでに指定された<tt>filterName</tt>のフィルターのためのFilterRegistrationが完了したものが含まれている場合は<tt>null</tt>
     *
     * @throws IllegalStateException このServletContextが初期化完了している場合
     *
     * @throws IllegalArgumentException <code>filterName</code>がnullもしくは空の文字列
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 3.0
     */
    public FilterRegistration.Dynamic addFilter(
        String filterName, String className);


    /**
     * 指定されたフィルターのインスタンスをこのServletContextに指定された<tt>filterName</tt>の下で登録します。
     *
     * <p>登録されたフィルターは返された{@link FilterRegistration}オブジェクトで詳細に設定することができます。
     * 
     * <p>ServletContextが指定された<tt>filterName</tt>のフィルターのための準備段階のFilterRegistrationをすでに含んでいた場合、
     * FilterRegistrationは(指定されたフィルターのインスタンスを指定された<tt>className</tt>で割り当てることで)完了し、返します。
     *
     * @param filterName フィルターの名前
     * @param filter 登録されるフィルターのインスタンス
     *
     * @return 指定されたフィルターを詳細に設定するために使用されるFilterRegistrationのオブジェクト、
     * ServletContextが指定された<tt>filterName</tt>のフィルターのための完了したFilterRegistrationを含んでいる場合や
     * 同じフィルターのインスタンスがこのServletContextや同じコンテナのほかのServletContextにすでに含まれている場合は<tt>null</tt>
     *
     * @throws IllegalStateException このServletContextが初期化完了している場合
     *
     * @throws IllegalArgumentException <code>filterName</code>がnullもしくは空の文字列
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 3.0
     */
    public FilterRegistration.Dynamic addFilter(
        String filterName, Filter filter);


    /**
     * 指定された名前とクラスタイプをこのServletContextに追加します。
     *
     * <p>登録されたフィルターは返された{@link FilterRegistration}オブジェクトで詳細に設定することができます。
     *
     * <p>ServletContextが指定された<tt>filterClass</tt>のフィルターのための準備段階のFilterRegistrationをすでに含んでいた場合、
     * FilterRegistrationは(指定された<tt>filterClass</tt>の名前で割り当てることで)完了し、返します。
     *
     * <p>このメソッドは指定された<tt>filterClass</tt>がマネージドビーンを表す場合、リソースインジェクションをサポートします。
     * マネージドビーンとリソースインジェクションについての詳細はJava EEプラットフォームとJSR 299の仕様を参照してください。
     *
     * @param filterName フィルターの名前
     * @param filterClass インスタンス化されるフィルターのクラスオブジェクト
     *
     * @return 登録されたフィルターを詳細に設定するためのFilterRegistrationオブジェクト、
     * ServletContextにすでに指定された<tt>filterName</tt>のフィルターのためのFilterRegistrationが完了したものが含まれている場合は<tt>null</tt>
     *
     * @throws IllegalStateException このServletContextが初期化完了している場合
     *
     * @throws IllegalArgumentException <code>filterName</code>がnullもしくは空の文字列
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 3.0
     */
    public FilterRegistration.Dynamic addFilter(String filterName,
        Class <? extends Filter> filterClass);


    /**
     * 指定されたフィルタークラスをインスタンス化します。
     *
     * <p>返されたフィルターのインスタンスはServletContextの{@link #addFilter(String,Filter)}を呼び出して登録する前に詳細なカスタマイズができます。
     *
     * <p>指定されたフィルターはインスタンス化するために使用される引数のないコンストラクタが定義されている必要があります。
     *
     * <p>このメソッドは指定された<tt>clazz</tt>がマネージドビーンを表す場合、リソースインジェクションをサポートします。
     * マネージドビーンとリソースインジェクションについての詳細はJava EEプラットフォームとJSR 299の仕様を参照してください。
     *
     * @param <T> 作られるフィルターのクラス
     * @param clazz インスタンス化されるフィルタークラス
     *
     * @return 新しいフィルターのインスタンス
     *
     * @throws ServletException 指定された<tt>clazz</tt>のインスタンス化に失敗した
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 3.0
     */
    public <T extends Filter> T createFilter(Class<T> clazz)
        throws ServletException;


    /**
     * 指定された<tt>filterName</tt>のフィルターに対応するFilterRegistrationを取得します。
     *
     * @param filterName フィルターの名雨
     * @return 指定された<tt>filterName</tt>のフィルターの(完了、もしくは準備段階の)FilterRegistration、
     * その名前の下でFilterRegistrationが存在しない場合はnull
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 3.0
     */
    public FilterRegistration getFilterRegistration(String filterName);


    /**
     * このServletContextに登録されているすべてのフィルターに対応する(フィルターの名前がキーで空の可能性のある)FilterRegistrationオブジェクトのMapを取得します。
     *
     * <p>返されたMapは設定ファイルで定義されたフィルターやアノテーションが付けられたフィルターに対応するFilterRegistrationオブジェクトと同じように
     * <tt>addFilter</tt>メソッドのいずれかで追加されたすべてに対応するすべてのFilterRegistrationオブジェクトを含みます。
     *
     * <p>返されたMapへの変更はこのServletContextに影響してはなりません。
     *
     * @return このServletContextに現在登録されているすべてのフィルターに対応する(完了、もしくは準備段階の)FilterRegistrationオブジェクトのMap
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 3.0
     */
    public Map<String, ? extends FilterRegistration> getFilterRegistrations();


    /**
     * この<tt>ServletContext</tt>のために作成されるセッション追跡Cookieのさまざまなプロパティを設定する事ができる{@link SessionCookieConfig}オブジェクトを取得します。
     *
     * <p>このメソッドを繰り返し読んだ場合、<tt>SessionCookieConfig</tt>の同じインスタンスが返されます。
     *
     * @return この<tt>ServletContext</tt>のために作成されるセッション追跡Cookieのさまざまなプロパティを設定する事ができる{@link SessionCookieConfig}オブジェクトを取得します。

     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 3.0
     */
    public SessionCookieConfig getSessionCookieConfig();


    /**
     * この<tt>ServletContext</tt>で有効になるセッション追跡方法を設定します。
     *
     * <p>指定された<tt>sessionTrackingModes</tt>はこの<tt>ServletContext</tt>のこのメソッドの以前の呼び出しによって設定されたセッション追跡モードを置き換えます。
     *
     * @param sessionTrackingModes この<tt>ServletContext</tt>で有効になるセッション追跡方法
     *
     * @throws IllegalStateException このServletContextが初期化完了している場合
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @throws IllegalArgumentException <tt>sessionTrackingModes</tt>で<tt>SessionTrackingMode.SSL</tt>が<tt>SessionTrackingMode.SSL</tt>以外のセッション追跡方法と組み合わせて指定された、
     * サーブレットコンテナでサポートされていないセッション追跡方法を<tt>sessionTrackingModes</tt>で指定した
     *
     * @since Servlet 3.0
     */
    public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes);


    /**
     * この<tt>ServletContext</tt>でデフォルトでサポートされるセッション追跡方法を取得します。
     *
     * <p>{@code ServletContext}オブジェクトによって返されたSetは{@code ServletContext}への参照を持っていないので変更は{@code ServletContext}へ影響を与えません。
     * 逆もそうです。</p>
     *
     * @return この<tt>ServletContext</tt>でデフォルトでサポートされるセッション追跡方法
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 3.0
     */
    public Set<SessionTrackingMode> getDefaultSessionTrackingModes();


    /**
     * この<tt>ServletContext</tt>で有効なセッション追跡方法を取得します。
     * 
     * 有効なセッション追跡方法は{@link #setSessionTrackingModes setSessionTrackingModes}で提供されています。
     * 
     * <p>{@code ServletContext}オブジェクトによって返されたSetは{@code ServletContext}への参照を持っていないので変更は{@code ServletContext}へ影響を与えません。
     * 逆もそうです。</p>
     *
     * @return この<tt>ServletContext</tt>で有効なセッション追跡方法
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 3.0
     */
    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes();


    /**
     * このServletContextに指定されたクラスの名前のリスナーを追加します。
     *
     * <p>指定された名前のクラスはこのサーブレットコンテキストに対応するウェブアプリケーションのクラスローダーからロードされ、
     * 以下のインターフェースのうち一つ以上を実装する必要があります。
     * <ul>
     * <li>{@link ServletContextAttributeListener}
     * <li>{@link ServletRequestListener}
     * <li>{@link ServletRequestAttributeListener}
     * <li>{@link javax.servlet.http.HttpSessionAttributeListener}
     * <li>{@link javax.servlet.http.HttpSessionIdListener}
     * <li>{@link javax.servlet.http.HttpSessionListener}
     * </ul>
     *
     * <p>このServletContextが{@link ServletContainerInitializer#onStartup}に渡されている場合、
     * 指定された名前のクラスは先に一覧化されたインターフェースに加えて{@link ServletContextListener}を実装することもできます。
     *
     * <p>このメソッド呼び出しの一環として、コンテナは指定されたクラス名を持つ必要なインターフェイスの1つが実装されていることを保証されたクラスをロードする必要があります。
     *
     * <p>指定された名前のクラスが呼び出される順序が宣言した順序に対応するリスナーのインターフェースを実装している場合
     * (つまり、{@link ServletRequestListener}、{@link ServletContextListener}、{@link javax.servlet.http.HttpSessionListener}を実装している場合)
     * 新しいリスナーはそのインターフェースのリスナーの順序付きリストの最後に追加されます。
     * 
     * <p>このメソッドは指定された<tt>className</tt>がマネージドビーンを表す場合、リソースインジェクションをサポートします。
     * マネージドビーンとリソースインジェクションについての詳細はJava EEプラットフォームとJSR 299の仕様を参照してください。
     * 
     * @param className the fully qualified class name of the listener
     *
     * @throws IllegalArgumentException 指定された名前のクラスが先のインターフェースを全く実装していない、
     * ServletContextが{@link ServletContainerInitializer#onStartup}に渡された時に指定された名前のクラスが{@link ServletContextListener}を実装していない
     *
     * @throws IllegalStateException このServletContextが初期化完了している場合
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 3.0
     */
    public void addListener(String className);


    /**
     * 指定されたリスナーをこのServletContextに追加します。
     *
     * <p>指定されたリスナーは以下のインターフェースのうち一つ以上を実装する必要があります。
     * <ul>
     * <li>{@link ServletContextAttributeListener}
     * <li>{@link ServletRequestListener}
     * <li>{@link ServletRequestAttributeListener}
     * <li>{@link javax.servlet.http.HttpSessionAttributeListener}
     * <li>{@link javax.servlet.http.HttpSessionIdListener}
     * <li>{@link javax.servlet.http.HttpSessionListener}
     * </ul>
     *
     * <p>このServletContextが{@link ServletContainerInitializer#onStartup}に渡されている場合、
     * 指定されたリスナーは先に一覧化されたインターフェースに加えて{@link ServletContextListener}を実装することもできます。
     *
     * <p>指定されたリスナーが呼び出される順序が宣言した順序に対応するリスナーのインターフェースを実装している場合
     * (つまり、{@link ServletRequestListener}、{@link ServletContextListener}、{@link javax.servlet.http.HttpSessionListener}を実装している場合)
     * 新しいリスナーはそのインターフェースのリスナーの順序付きリストの最後に追加されます。
     *
     * @param <T> 追加されるEventListenerのクラス
     * @param t 追加されるリスナー
     *
     * @throws IllegalArgumentException 指定されたリスナーが先のインターフェースを全く実装していない、
     * ServletContextが{@link ServletContainerInitializer#onStartup}に渡された時に指定されたリスナーが{@link ServletContextListener}を実装していない
     *
     * @throws IllegalStateException このServletContextが初期化完了している場合
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 3.0
     */
    public <T extends EventListener> void addListener(T t);


    /**
     * 指定されたクラスの型のリスナーをこのServletContextに追加します。
     *
     * <p>指定された<tt>listenerClass</tt>は以下のインターフェースのうち一つ以上を実装する必要があります。
     * <ul>
     * <li>{@link ServletContextAttributeListener}
     * <li>{@link ServletRequestListener}
     * <li>{@link ServletRequestAttributeListener}
     * <li>{@link javax.servlet.http.HttpSessionAttributeListener}
     * <li>{@link javax.servlet.http.HttpSessionIdListener}
     * <li>{@link javax.servlet.http.HttpSessionListener}
     * </ul>
     *
     * <p>このServletContextが{@link ServletContainerInitializer#onStartup}に渡されている場合、
     * 指定された<tt>listenerClass</tt>は先に一覧化されたインターフェースに加えて{@link ServletContextListener}を実装することもできます。
     *
     * <p>指定された<tt>listenerClass</tt>が呼び出される順序が宣言した順序に対応するリスナーのインターフェースを実装している場合
     * (つまり、{@link ServletRequestListener}、{@link ServletContextListener}、{@link javax.servlet.http.HttpSessionListener}を実装している場合)
     * 新しいリスナーはそのインターフェースのリスナーの順序付きリストの最後に追加されます。
     * 
     * <p>このメソッドは指定された<tt>listenerClass</tt>がマネージドビーンを表す場合、リソースインジェクションをサポートします。
     * マネージドビーンとリソースインジェクションについての詳細はJava EEプラットフォームとJSR 299の仕様を参照してください。
     * 
     * @param listenerClass インスタンス化されるリスナークラス
     *
     * @throws IllegalArgumentException 指定された<tt>listenerClass</tt>が先のインターフェースを全く実装していない、
     * ServletContextが{@link ServletContainerInitializer#onStartup}に渡された時に指定された<tt>listenerClass</tt>が{@link ServletContextListener}を実装していない
     *
     * @throws IllegalStateException このServletContextが初期化完了している場合
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 3.0
     */
    public void addListener(Class <? extends EventListener> listenerClass);


    /**
     * 指定されたEventListenerクラスをインスタンス化します。
     *
     * <p>指定したEventListenerのクラスは
     * {@link ServletContextListener}、
     * {@link ServletContextAttributeListener}、
     * {@link ServletRequestListener}、
     * {@link ServletRequestAttributeListener}、
     * {@link javax.servlet.http.HttpSessionAttributeListener}、
     * {@link javax.servlet.http.HttpSessionIdListener}、
     * {@link javax.servlet.http.HttpSessionListener}
     * のインターフェースのうち最低でも一つ実装する必要があります。
     *
     * <p>返されたEventListenerインスタンスは{@link #addListener(EventListener)}の呼び出しによってこのServletContextに登録する前に、
     * 詳細なカスタマイズをすることができます。
     * 
     * <p>指定されたEventListenerクラスはインスタンス化に使用するための引数を持たないコンストラクタが定義されている必要があります。
     * 
     * <p>このメソッドは指定された<tt>clazz</tt>がマネージドビーンを表す場合、リソースインジェクションをサポートします。
     * マネージドビーンとリソースインジェクションについての詳細はJava EEプラットフォームとJSR 299の仕様を参照してください。
     *
     * @param <T> 作成されるEventListenerのクラス
     * @param clazz インスタンス化されるEventListenerクラス
     *
     * @return 新しいEventListenerのインスタンス
     *
     * @throws ServletException 指定された<tt>clazz</tt>のインスタンス化に失敗した
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @throws IllegalArgumentException 指定したEventListenerのクラスが
     * {@link ServletContextListener}、
     * {@link ServletContextAttributeListener}、
     * {@link ServletRequestListener}、
     * {@link ServletRequestAttributeListener}、
     * {@link javax.servlet.http.HttpSessionAttributeListener}、
     * {@link javax.servlet.http.HttpSessionIdListener}、
     * {@link javax.servlet.http.HttpSessionListener}
     * のいずれのインターフェースも実装していなかった場合
     *
     * @since Servlet 3.0
     */
    public <T extends EventListener> T createListener(Class<T> clazz)
        throws ServletException;


    /**
     * このServletContextに対応するウェブアプリケーションの<code>web.xml</code>と<code>web-fragment.xml</code>ディスクリプターファイルから集約された<code>&lt;jsp-config&gt;</code>に関連する設定を取得します。
     *
     * @return このServletContextに対応するウェブアプリケーションの<code>web.xml</code>と<code>web-fragment.xml</code>ディスクリプターファイルから集約された<code>&lt;jsp-config&gt;</code>に関連する設定、そのような設定が存在しない場合はnull
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @see javax.servlet.descriptor.JspConfigDescriptor
     *
     * @since Servlet 3.0
     */
    public JspConfigDescriptor getJspConfigDescriptor();


    /**
     * このサーブレットコンテキストに対応するウェブアプリケーションのクラスローダーを取得します。
     *
     * <p>セキュリティマネージャが存在し、
     * 呼び出し元のクラスローダーが要求されたクラスローダーと同じか継承元でない場合、
     * セキュリティマネージャの<code>checkPermission</code>メソッドは<code>RuntimePermission("getClassLoader")</code>のパーミッションで
     * クラスローダーへのアクセス権が付与されているかどうかチェックをするために呼び出されます。
     *
     * @return このサーブレットコンテキストに対応するウェブアプリケーションのクラスローダー
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @throws SecurityException セキュリティマネージャーがクラスローダーへのアクセスのリクエストを拒否した
     *
     * @since Servlet 3.0
     */
    public ClassLoader getClassLoader();


    /**
     * <code>isUserInRole</code>での検証に使用されるロール名を定義します。
     *
     * <p>{@link ServletRegistration}の{@link ServletRegistration.Dynamic#setServletSecurity setServletSecurity}メソッドや、
     * {@link ServletRegistration.Dynamic#setRunAsRole setRunAsRole}メソッドの呼び出しの結果、暗黙的に定義されているロールを定義する必要はありません。
     *
     * @param roleNames 定義されるロール名
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @throws IllegalArgumentException 引数のロール名のいずれかがnullかからの文字列だった
     *
     * @throws IllegalStateException このServletContextが初期化完了している場合
     *
     * @since Servlet 3.0
     */
    public void declareRoles(String... roleNames);


    /**
     * ServletContextがデプロイされている論理ホストの設定名を返します。
     * 
     * サーブレットコンテナは複数の論理ホストをサポートすることができます。
     * このメソッドでは論理ホスト上に配置されているすべてのサーブレットコンテキストに同じ名前を返され、
     * このメソッドによって返された名前は論理ホストごとに安定してまったく別であり、、
     * サーバー構成情報と論理ホストの関連付けに使用するのに適しています。
     * 返される値は、論理ホストのネットワークアドレスまたはホスト名と同等であることが要求されて<B>いません</B>。
     *
     * The returned value is NOT expected or required to be equivalent to a network address or hostname of the logical host.
     *
     * @return ServletContextがデプロイされている論理ホストの設定名を含む <code>String</code>
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 3.1
     */
    public String getVirtualServerName();


    /**
     * この<tt>ServletContext</tt>でデフォルトでサポートするセッションがタイムアウトするまでの分を取得します。
     *
     * @return この<tt>ServletContext</tt>でデフォルトでサポートするセッションがタイムアウトするまでの分
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 4.0
     */
    public int getSessionTimeout();


    /**
     * このServletContextにセッションがタイムアウトするまでの分を設定します。
     *
     * @param sessionTimeout セッションがタイムアウトする分
     *
     * @throws IllegalStateException このServletContextが初期化完了している場合
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 4.0
     */
    public void setSessionTimeout(int sessionTimeout);


    /**
     * この<tt>ServletContext</tt>でデフォルトでサポートするレスポンスの文字エンコーディングを取得します。
     * このメソッドはデプロイメントディスクリプタやコンテナ固有の(コンテナ上のすべてのアプリケーション向けの)設定でリクエストの文字エンコーディングが指定されていない場合はnullを返します。
     *
     * @return この<tt>ServletContext</tt>でデフォルトでサポートするリクエストの文字エンコーディング
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 4.0
     */
    public String getRequestCharacterEncoding();


    /**
     * このServletContextにリクエストの文字エンコーディングを設定します。
     *
     * @param encoding リクエストの文字エンコーディング
     *
     * @throws IllegalStateException このServletContextが初期化完了している場合
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 4.0
     */
    public void setRequestCharacterEncoding(String encoding);


    /**
     * この<tt>ServletContext</tt>でデフォルトでサポートするレスポンスの文字エンコーディングを取得します。
     * このメソッドはデプロイメントディスクリプタやコンテナ固有の(コンテナ上のすべてのアプリケーション向けの)設定でレスポンスの文字エンコーディングが指定されていない場合はnullを返します。
     *
     * @return この<tt>ServletContext</tt>でデフォルトでサポートするレスポンスの文字エンコーディング
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 4.0
     */
    public String getResponseCharacterEncoding();


    /**
     * このServletContextにレスポンスの文字エンコーディングを設定します。
     *
     * @param encoding レスポンスの文字エンコーディング
     *
     * @throws IllegalStateException このServletContextが初期化完了している場合
     *
     * @throws UnsupportedOperationException このServletContextが<code>web.xml</code>や<code>web-fragment.xml</code>で宣言されておらず、
     * {@link javax.servlet.annotation.WebListener}アノテーションもついてない{@link ServletContextListener}の
     * {@link ServletContextListener#contextInitialized}メソッドに渡された場合
     *
     * @since Servlet 4.0
     */
    public void setResponseCharacterEncoding(String encoding);
}
