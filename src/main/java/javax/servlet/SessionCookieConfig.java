/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2017-2017 Oracle and/or its affiliates. All rights reserved.
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

/**
 * セッションの追跡に使用されるCookieのさまざまなプロパティを設定するために使用されるクラスです。
 *
 * <p>このクラスのインスタンスは{@link ServletContext#getSessionCookieConfig}を呼び出すことで取得されます。
 *
 * @since Servlet 3.0
 */
public interface SessionCookieConfig {

    /**
     * この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>によって表されるアプリケーションのために
     * 作成されたセッショントラッキングCookieに割り当てられる名前を設定します。
     * 
     * <p>注：セッショントラッキングCookieの名前を変更すると、
     * Cookie名がデフォルトの<tt>JSESSIONID</tt>であるとみなされている他の層(たとえば、ロードバランシングフロントエンド)
     * が壊れる可能性があります。したがって、慎重に行う必要がります。
     *
     * @param name 使われるCookieの名前
     *
     * @throws IllegalStateException この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>がすでに初期化完了している場合
     */
    public void setName(String name);


    /**
     * この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>によって表されるアプリケーションのために
     * 作成されたセッショントラッキングCookieに割り当てられる名前を取得します。
     *
     * <p>デフォルトでは<tt>JSESSIONID</tt>がCookie名として使用されます。
     * 
     * @return {@link #setName}で設定されたCookieの名前、{@link #setName}が呼び出されてない場合は<tt>null</tt>
     *
     * @see javax.servlet.http.Cookie#getName()
     */
    public String getName();


    /**
     * この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>によって表されるアプリケーションのために
     * 作成されたセッショントラッキングCookieに割り当てられるドメイン名を設定します。
     *
     * @param domain 使用されるCookieのドメイン
     *
     * @throws IllegalStateException この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>がすでに初期化完了している場合
     *
     * @see javax.servlet.http.Cookie#setDomain(String)
     */
    public void setDomain(String domain);


    /**
     * この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>によって表されるアプリケーションのために
     * 作成されたセッショントラッキングCookieに割り当てられるドメイン名を取得します。
     *
     * @return {@link #setDomain}で設定されたCookieのドメイン、{@link #setDomain}が呼び出されてない場合は<tt>null</tt>
     *
     * @see javax.servlet.http.Cookie#getDomain()
     */
    public String getDomain();


    /**
     * この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>によって表されるアプリケーションのために
     * 作成されたセッショントラッキングCookieに割り当てられるパスを設定します。
     *
     * @param path 使用されるCookieのパス
     *
     * @throws IllegalStateException この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>がすでに初期化完了している場合
     *
     * @see javax.servlet.http.Cookie#setPath(String)
     */
    public void setPath(String path);


    /**
     * この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>によって表されるアプリケーションのために
     * 作成されたセッショントラッキングCookieに割り当てられるパスを取得します。
     * 
     * <p>デフォルトではこの<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>のコンテキストパスが使用されます。
     *
     * @return {@link #setPath}で設定されたCookieのパス、{@link #setPath}が呼び出されてない場合は<tt>null</tt>
     *
     * @see javax.servlet.http.Cookie#getPath()
     */
    public String getPath();


    /**
     * この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>によって表されるアプリケーションのために
     * 作成されたセッショントラッキングCookieに割り当てられるコメントを設定します。
     *
     * <p>このメソッドを呼び出すと副作用としてセッショントラッキングCookieの<code>Version</code>属性に<code>1</code>が設定されます。
     * 
     * @param comment 使用されるCookieのコメント
     *
     * @throws IllegalStateException この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>がすでに初期化完了している場合
     *
     * @see javax.servlet.http.Cookie#setComment(String)
     * @see javax.servlet.http.Cookie#getVersion
     */
    public void setComment(String comment);


    /**
     * この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>によって表されるアプリケーションのために
     * 作成されたセッショントラッキングCookieに割り当てられるコメントを取得します。
     *
     * @return {@link #setComment}で設定されたCookieのコメント、{@link #setComment}が呼び出されてない場合は<tt>null</tt>
     *
     * @see javax.servlet.http.Cookie#getComment()
     */
    public String getComment();


    /**
     * この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>によって表されるアプリケーションのために
     * 作成されたセッショントラッキングCookieを<i>HttpOnly</i>としてマークもしくはアンマークします。
     *
     * <p>Cookieは<tt>HttpOnly</tt>属性を追加されることで<tt>HttpOnly</tt>としてマークされます。
     * <i>HttpOnly</i>のクッキーはクライアント側のスクリプトコードに公開されないと規定されているため、
     * 特定の種類のクロスサイトスクリプティング攻撃を緩和できます。
     *
     * @param httpOnly この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>によって表されるアプリケーションのために
     * 作成されたセッショントラッキングCookieを<i>HttpOnly</i>としてマークしたいのならばtrue、そうでないならfalse
     *
     * @throws IllegalStateException この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>がすでに初期化完了している場合
     *
     * @see javax.servlet.http.Cookie#setHttpOnly(boolean)
     */
    public void setHttpOnly(boolean httpOnly);


    /**
     * この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>によって表されるアプリケーションのために
     * 作成されたセッショントラッキングCookieを<i>HttpOnly</i>としてマークされているかチェックします。
     *
     * @return この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>によって表されるアプリケーションのために
     * 作成されたセッショントラッキングCookieが<i>HttpOnly</i>としてマークされているのならばtrue、そうでないならfalse
     *
     * @see javax.servlet.http.Cookie#isHttpOnly()
     */
    public boolean isHttpOnly();


    /**
     * この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>によって表されるアプリケーションのために
     * 作成されたセッショントラッキングCookieを<i>secure</i>としてマークもしくはアンマークします。
     *
     * <p>セッショントラッキングCookieを<tt>secure</tt>としてマークする場合の1つの使用例は、
     * セッションを開始したリクエストがHTTP経由で来た場合でも、WebコンテナのフロントエンドとしてSSLオフロードロードバランサが置かれている形態をサポートすることです。
     * この場合はクライアントとロードバランサ間のトラフィックはHTTPS経由となりますが、ロードバランサとWebコンテナ間のトラフィックはHTTP経由となります。
     *
     * @param secure この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>によって表されるアプリケーションのために
     * 作成されたセッショントラッキングCookieが対応するセッションを開始したリクエストがHTTPSの代わりにプレーンHTTPを使用していても
     * <i>secure</i>であるとマークされているならばtrue、対応するセッションを開始したリクエストがsecureであった場合にのみ
     * <i>secure</i>としてマークされるならばfalse
     *
     * @throws IllegalStateException この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>がすでに初期化完了している場合
     *
     * @see javax.servlet.http.Cookie#setSecure(boolean)
     * @see ServletRequest#isSecure()
     */
    public void setSecure(boolean secure);


    /**
     * この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>によって表されるアプリケーションのために
     * 作成されたセッショントラッキングCookieが対応するセッションを開始したリクエストがHTTPSの代わりにプレーンHTTPを使用していても
     * <i>secure</i>であるとマークされているかどうかチェックします。
     *
     * @return この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>によって表されるアプリケーションのために
     * 作成されたセッショントラッキングCookieが対応するセッションを開始したリクエストがHTTPSの代わりにプレーンHTTPを使用していても
     * <i>secure</i>であるとマークされているならばtrue、対応するセッションを開始したリクエストがsecureであった場合にのみ
     * <i>secure</i>としてマークされるならばfalse
     *
     * @see javax.servlet.http.Cookie#getSecure()
     * @see ServletRequest#isSecure()
     */
    public boolean isSecure();


    /**
     * この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>によって表されるアプリケーションのために
     * 作成されたセッショントラッキングCookieにの生存期間(を秒数で)を設定します。
     *
     * @param maxAge この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>によって表されるアプリケーションのために
     * 作成されたセッショントラッキングCookieの生存期間(の秒数)
     *
     * @throws IllegalStateException この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>がすでに初期化完了している場合
     *
     * @see javax.servlet.http.Cookie#setMaxAge
     */
    public void setMaxAge(int maxAge);


    /**
     * この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>によって表されるアプリケーションのために
     * 作成されたセッショントラッキングCookieの生存期間(を秒数で)を取得します。
     *
     * <p>デフォルトでは<tt>-1</tt>を返します。
     *
     * @return この<tt>SessionCookieConfig</tt>が取得された<tt>ServletContext</tt>によって表されるアプリケーションのために
     * 作成されたセッショントラッキングCookieの生存期間(の秒数)、もしくは<tt>-1</tt>(デフォルト)
     *
     * @see javax.servlet.http.Cookie#getMaxAge
     */
    public int getMaxAge();
}
