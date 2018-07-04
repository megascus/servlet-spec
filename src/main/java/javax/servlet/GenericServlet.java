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
import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * 総称的なプロトコルに依存しないサーブレットを定義します。
 * Webで使用するHTTPのサーブレットを作成したい場合は {@link javax.servlet.http.HttpServlet} を継承してください。
 *
 * <p><code>GenericServlet</code> は <code>Servlet</code>と <code>ServletConfig</code> の二つのインターフェースを実装します。 
 * <code>GenericServlet</code> を直接継承してもよいですが、<code>HttpServlet</code> などのプロトコルに依存したサブクラスを継承するのがより一般的です。
 *
 * <p><code>GenericServlet</code> はサーブレットを簡単に作成できるようにします。
 * これはライフサイクルメソッドのうち<code>init</code> と <code>destroy</code> の単純な(何もしない)バージョンと、
 * <code>ServletConfig</code> インターフェースのメソッドを提供します。
 * <code>GenericServlet</code> は <code>ServletContext</code> インターフェースに定義された <code>log</code> メソッドも実装しています。
 *
 * <p><code>GenericServlet</code> を継承してサーブレットを作成する場合はabstractな<code>service</code>メソッドだけを実装する必要があります。
 *
 * @author 	Various
 */

 
public abstract class GenericServlet 
    implements Servlet, ServletConfig, java.io.Serializable
{
    private static final String LSTRING_FILE = "javax.servlet.LocalStrings";
    private static ResourceBundle lStrings =
        ResourceBundle.getBundle(LSTRING_FILE);

    private transient ServletConfig config;
    

    /**
     * 何もしません。サーブレットのすべての初期化は <code>init</code> メソッドで行われます。
     *
     */
    public GenericServlet() { }
    
    
    /**
     * サーブレットがサービスから取り除かれるときにサーブレットコンテナから呼び出されます。
     * {@link Servlet#destroy} を参照してください。
     *
     * 
     */
    public void destroy() {
    }
    
    
    /**
     * 名前付き初期化パラメーターに含まれる値の<code>String</code>を返します。
     * 存在しない場合は<code>null</code>を返します。
     * {@link ServletConfig#getInitParameter}を参照してください。
     *
     * <p>このメソッドは簡便さのために提供されています。
     * これはサーブレットの <code>ServletConfig</code> のオブジェクトから名前付きパラメーターを取得します。
     *
     * @param name 		初期化パラメータの名前を指定する<code>String</code>
     *
     * @return String 		初期化パラメータの値の<code>String</code>
     *
     */ 
    public String getInitParameter(String name) {
        ServletConfig sc = getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(
                lStrings.getString("err.servlet_config_not_initialized"));
        }

        return sc.getInitParameter(name);
    }
    
    
   /**
    * サーブレットの初期化パラメーターの名前を<code>String</code>オブジェクトの<code>Enumeration</code>として返します。
    * サーブレットに初期化パラメーターがない場合は空の<code>Enumeration</code>を返します。 
    * {@link ServletConfig#getInitParameterNames}を参照してください。
    *
    * <p>このメソッドは簡便さのために提供されています。
    * これはサーブレットの <code>ServletConfig</code> のオブジェクトからパラメーター名を取得します。
    *
    *
    * @return Enumeration 	サーブレットの初期化パラメータの名前を含んだ<code>String</code>オブジェクトの<code>Enumeration</code>
    */
    public Enumeration<String> getInitParameterNames() {
        ServletConfig sc = getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(
                lStrings.getString("err.servlet_config_not_initialized"));
        }

        return sc.getInitParameterNames();
    }   
     

    /**
     * サーブレットの {@link ServletConfig} のオブジェクトを返します。
     *
     * @return ServletConfig 	このサーブレットが初期化された <code>ServletConfig</code> のオブジェクト
     */    
    public ServletConfig getServletConfig() {
	return config;
    }
 
    
    /**
     * このサーブレットが動いている{@link ServletContext}を返します。
     * {@link ServletConfig#getServletContext}を参照してください。
     *
     * <p>このメソッドは簡便さのために提供されています。
     * これはサーブレットの <code>ServletConfig</code> のオブジェクトからコンテキストを取得します。
     *
     *
     * @return ServletContext 	<code>init</code>に渡された <code>ServletContext</code> のオブジェクト
     */
    public ServletContext getServletContext() {
        ServletConfig sc = getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(
                lStrings.getString("err.servlet_config_not_initialized"));
        }

        return sc.getServletContext();
    }


    /**
     * サーブレットの情報を返します。作者やバージョン、コピーライトなどです。
     * デフォルトでは空文字列を返します。
     * このメソッドをオーバーライドして意味のある値を返すようにします。
     * {@link Servlet#getServletInfo}を参照してください。
     *
     *
     * @return String 		このサーブレットの説明、デフォルトでは空文字列
     */    
    public String getServletInfo() {
	return "";
    }


    /**
     * サーブレットがサービスに組込まれるときにサーブレットコンテナに呼出されます。
     * {@link Servlet#init}を参照してください。
     * 
     * <p>この実装はサーブレットコンテナから受け取った{@link ServletConfig}オブジェクトをあとで使用するために格納します。 
     * このメソッドの振る舞いをオーバーライドする場合は <code>super.init(config)</code> を呼び出してください。
     *
     *
     * @param config 			このサーブレットの設定情報を含む<code>ServletConfig</code> のオブジェクト
     *
     * @exception ServletException 	サーブレットの通常の処理で例外が発生した
     * 
     * @see 				UnavailableException
     */
    public void init(ServletConfig config) throws ServletException {
	this.config = config;
	this.init();
    }


    /**
     * <code>super.init(config)</code>を呼ばなくてもオーバーライドできるようにするための便利メソッドです。
     *
     * <p>{@link #init(ServletConfig)}をオーバーライドしなくても
     * 単にこのメソッドをオーバーライドすれば <code>GenericServlet.init(ServletConfig config)</code> によって呼び出されます。
     * <code>ServletConfig</code> オブジェクトは {@link#getServletConfig} から引き続き取得できます。
     *
     * @exception ServletException 	サーブレットの通常の処理で例外が発生した
     */
    public void init() throws ServletException {

    }
    

    /**
     * 指定されたメッセージをサーブレットの名前を先頭に付けてログファイルに書き込みます。
     * {@link ServletContext#log(String)} を参照してください。
     *
     * @param msg 	ログファイルに書き込まれるメッセージ
     */     
    public void log(String msg) {
	getServletContext().log(getServletName() + ": "+ msg);
    }
   
   
    /**
     * 指定されたメッセージと<code>Throwable</code>のスタックトレースをサーブレットの名前を先頭に付けてログファイルに書き込みます。
     * {@link ServletContext#log(String, Throwable)} を参照してください。
     *
     *
     * @param message 		エラーや例外を説明する <code>String</code>
     *
     * @param t			<code>java.lang.Throwable</code>
     */   
    public void log(String message, Throwable t) {
	getServletContext().log(getServletName() + ": " + message, t);
    }
    
    
    /**
     * サーブレットがリクエストに応答できるようにサーブレットコンテナによって呼び出されます。
     * {@link Servlet#service} を参照してください。
     * 
     * <p>このメソッドはabstractで定義されているので、
     * <code>HttpServlet</code>などのサブクラスでオーバーライドする必要があります。
     *
     * @param req 	クライアントのリクエストが含まれる<code>ServletRequest</code>のオブジェクト
     *
     * @param res 	サーブレットのレスポンスが含まれる <code>ServletResponse</code>のオブジェクト
     *
     * @exception ServletException 	サーブレットの通常の処理で例外が発生した
     *
     * @exception IOException 		I/Oの例外が発生した
     */

    public abstract void service(ServletRequest req, ServletResponse res)
	throws ServletException, IOException;
    

    /**
     * サーブレットのインスタンスの名前を返します。
     * {@link ServletConfig#getServletName}を参照してください。
     *
     * @return          サーブレットのインスタンスの名前
     */
    public String getServletName() {
        ServletConfig sc = getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(
                lStrings.getString("err.servlet_config_not_initialized"));
        }

        return sc.getServletName();
    }
}
