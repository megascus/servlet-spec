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
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
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
 */

package javax.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * 総称的なプロトコルに依存しないフィルターを定義します。
 * Webで使用するHTTPのフィルターを作成したい場合は {@link javax.servlet.http.HttpFilter} を継承してください。
 * 
 * <p><code>GenericFilter</code> は <code>Filter</code> と <code>FilterConfig</code> の二つのインターフェースを実装します。
 * <code>GenericFilter</code> を直接継承してもよいですが、<code>HttpFilter</code> などのプロトコルに依存したサブクラスを継承するのがより一般的です。
 * 
 * <p><code>GenericFilter</code> はフィルターを簡単に作成できるようにします。
 * これはライフサイクルメソッドのうち <code>init</code> と <code>destroy</code> の単純な(何もしない)バージョンと、
 * <code>FilterConfig</code> インターフェースのメソッドを提供します。
 * 
 * <p><code>GenericFilter</code> を継承してフィルターを作成する場合はabstractな<code>doFilter</code>メソッドだけを実装する必要があります。
 *
 * @author 	Various
 * 
 * @since Servlet 4.0
 */

 
public abstract class GenericFilter 
    implements Filter, FilterConfig, java.io.Serializable
{
    private static final String LSTRING_FILE = "javax.servlet.LocalStrings";
    private static final ResourceBundle lStrings =
        ResourceBundle.getBundle(LSTRING_FILE);

    private transient FilterConfig config;
    

    /**
     *
     * <p>何もしません。フィルターのすべての初期化は <code>init</code> メソッドで行われます。</p>
     *
     * @since Servlet 4.0
     */
    public GenericFilter() { }
    
    
    /**
     * <p>名前付き初期化パラメーターに含まれる値の<code>String</code>を返します。 存在しない場合は<code>null</code>を返します。
     * {@link FilterConfig#getInitParameter}を参照してください。</p>
     * 
     * <p>このメソッドは簡便さのために提供されています。 これはフィルターの <p>FilterConfig</p> のオブジェクトから名前付きパラメーターを取得します。
     *
     * @param name 		初期化パラメータの名前を指定する<code>String</code>
     *
     * @return String 		初期化パラメータの値の<code>String</code>
     *
     * @since Servlet 4.0
     *
     */ 
    @Override
    public String getInitParameter(String name) {
        FilterConfig fc = getFilterConfig();
        if (fc == null) {
            throw new IllegalStateException(
                lStrings.getString("err.filter_config_not_initialized"));
        }

        return fc.getInitParameter(name);
    }
    
    
   /**
    * <p>フィルターの初期化パラメーターの名前を<code>String</code>オブジェクトの<code>Enumeration</code>として返します。
    * フィルターに初期化パラメーターがない場合は空の<code>Enumeration</code>を返します。 
    * {@link FilterConfig#getInitParameterNames}を参照してください。</p>
    *
    * <p>このメソッドは簡便さのために提供されています。
    * これはフィルターの <code>FilterConfig</code> のオブジェクトからパラメーター名を取得します。
    *
    * @return Enumeration 	フィルターの初期化パラメータの名前を含んだ<code>String</code>オブジェクトの<code>Enumeration</code>
    *
    * @since Servlet 4.0
    */
    @Override
    public Enumeration<String> getInitParameterNames() {
        FilterConfig fc = getFilterConfig();
        if (fc == null) {
            throw new IllegalStateException(
                lStrings.getString("err.filter_config_not_initialized"));
        }

        return fc.getInitParameterNames();
    }   
     

    /**
     * <p>このフィルターの {@link FilterConfig} のオブジェクトを返します。</p>
     *
     * @return FilterConfig 	このフィルターが初期化された {@link FilterConfig} のオブジェクト
     *
     * @since Servlet 4.0
     */    
    public FilterConfig getFilterConfig() {
	return config;
    }
 
    
    /**
     * このサーブレットが動いている{@link ServletContext}を返します。
     * {@link ServletConfig#getServletContext}を参照してください。
     *
     * <p>このメソッドは簡便さのために提供されています。
     * これはサーブレットの <code>FilterConfig</code>のオブジェクトからコンテキストを取得します。
     *
     *
     * @return ServletContext 	<code>init</code>でこのフィルターに渡された <code>ServletContext</code> のオブジェクト
     *
     * @since Servlet 4.0
     */
    @Override
    public ServletContext getServletContext() {
        FilterConfig sc = getFilterConfig();
        if (sc == null) {
            throw new IllegalStateException(
                lStrings.getString("err.filter_config_not_initialized"));
        }

        return sc.getServletContext();
    }


    /**
     * <p>サーブレットがサービスに組込まれるときにサーブレットコンテナに呼出されます。
     * {@link Filter#init}を参照してください。</p>
     * 
     * <p>この実装はサーブレットコンテナから受け取った{@link FilterConfig}オブジェクトをあとで使用するために格納します。
     * このメソッドの振る舞いをオーバーライドする場合は <code>super.init(config)</code> を呼び出してください。
     * 
     * @param config 			このフィルターの設定情報を含む<code>FilterConfig</code> のオブジェクト
     *
     * @exception ServletException 	サーブレットの通常の処理で例外が発生した
     * 
     * @see 				UnavailableException
     *
     * @since Servlet 4.0
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
	this.config = config;
	this.init();
    }

    /**
     * <p><code>super.init(config)</code>を呼ばなくてもオーバーライドできるようにするための便利メソッドです。</p>
     *
     * <p>{@link #init(FilterConfig)}をオーバーライドしなくても
     * 単にこのメソッドをオーバーライドすれば <code>GenericFilter.init(FilterConfig config)</code> によって呼び出されます。
     * <code>FilterConfig</code> オブジェクトは {@link #getFilterConfig} から引き続き取得できます。
     * 
     * @exception ServletException 	サーブレットの通常の処理で例外が発生した
     *
     * @since Servlet 4.0
     */
    public void init() throws ServletException {

    }
    

    /**
     * <p>このフィルターのインスタンスの名前を返します。
     * {@link FilterConfig#getFilterName} を参照してください。</p>
     *
     * @return          このフィルターのインスタンスの名前
     *
     * @since Servlet 4.0
     */
    @Override
    public String getFilterName() {
        FilterConfig sc = getFilterConfig();
        if (sc == null) {
            throw new IllegalStateException(
                lStrings.getString("err.servlet_config_not_initialized"));
        }

        return sc.getFilterName();
    }
}
