/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2017 Oracle and/or its affiliates. All rights reserved.
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

package javax.servlet.http;

import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** 
 * プッシュされるリクエストを生成します。 
 * 
 * RFC 7540のセクション8.2によればリクエストは、リクエストボディなしでキャッシュ可能かつ安全でなければならないことが約束されています。
 *
 * <p>PushBuilderは、 {@link HttpServletRequest#newPushBuilder()}を呼び出すことで得られます。
 * このメソッドを呼び出すたびに現在のHttpServletRequestに基づくPushBuilderの新しいインスタンスが作成されるかnullを返します。
 * 返されたPushBuilderインスタンスへの変更はその次以降の呼び出しには反映されません。</p>
 *
 * <p>インスタンスは次のように初期化されます。</p>
 *
 * <ul>
 *
 * <li>メソッドは "GET"に初期化されます。</li>
 *
 * <li>現在の{@link HttpServletRequest}の既存のリクエストヘッダーは以下を除いてPushBuilderに追加されます。
 *
 * <ul>
 *   <li>条件付きヘッダー (RFC 7232にて定義)
 *   <li>レンジヘッダー(Rangeヘッダー)
 *   <li>期待ヘッダー(Expectヘッダー)
 *   <li>認証ヘッダー(Authorizationヘッダー)
 *   <li>参照元ヘッダー(Referrerヘッダー)
 * </ul>
 *
 * </li>
 *
 * <li>リクエストが認証された場合は認証ヘッダーにコンテナが生成したトークンが設定され、プッシュされたリクエストに対して同等の認証が行われます。</li>
 *
 * <li>セッションIDは{@code PushBuilder}を作成する前に、新しい{@link HttpSession}を作成するために{@link HttpServletRequest#getSession(boolean)}が呼び出されていない場合、
 * {@link HttpServletRequest#getRequestedSessionId()}から返された値となり、
 * この場合PushBuilderでリクエストされたセッションIDとして新しいセッションIDが使用されます。
 * リクエストから返されるセッションIDはCookieまたはURLの事実上二つの"ソース"から返されることに注意してください。(Cookie：{@link HttpServletRequest#isRequestedSessionIdFromCookie}もしくはURL：{@link HttpServletRequest#isRequestedSessionIdFromURL}で得られる)
 * {@code PushBuilder}のセッションIDはリクエストと同じソースが使用されます。</li>
 * 
 * <li>リファラーヘッダー(Refererヘッダー:原文ママ)は{@link HttpServletRequest#getRequestURL()}と任意の{@link HttpServletRequest#getQueryString()}に設定されます。</li>
 *
 * <li>関連するレスポンスに対してHttpServletResponse.addCookie(Cookie)が呼び出された場合、{@link Cookie#getMaxAge()}が&lt;= 0でない時は対応するCookieヘッダがPushBuilderに追加されます。
 * &lt;= 0の時はCookieはPushBuilderから削除されます。</li>
 *
 * </ul> 
 *
 * <p>{@link #push}メソッドの呼び出しの前に{@link #path}メソッドを{@code PushBuilder}のインスタンスで呼び出す必要があります。
 * そうしないと、そのメソッドで指定されているように{@link#push}から例外がスローされる必要があり失敗します。</p>
 * 
 * <p>PushBuilderは{@link #push()}メソッドが呼び出される前にミューテーターメソッドのチェーン呼び出しによってカスタマイズされ、
 * PushBuilderの現在の状態で非同期プッシュ要求が開始されます。
 * {@link #push()}メソッドが呼び出された後、PushBuilderは別のプッシュで再利用されるかもしれませんが、
 * 実装はpush()から戻る前に{@link#path(String)}および条件付きヘッダー（conditionalヘッダー：RFC 7232で定義）の値がクリアされるようにする必要があります。
 * 他のすべての値は{@link #push()}呼び出しをまたいで保持されます。
 *
 * @since Servlet 4.0
 */
public interface PushBuilder {
    /** 
     * <p>プッシュに使用される(HTTPの)メソッドを設定します。</p>
     * 
     * @throws NullPointerException 引数が {@code null} の場合
     *
     * @throws IllegalArgumentException 引数が空文字列だった場合や、RFC 7231で定義されたキャッシュができないか安全でない、POST、PUT、DELETE、CONNECT、OPTIONS、TRACEメソッドの場合。
     *
     * @param method プッシュに使用する(HTTPの)メソッド
     * @return 自分自身
     */
    public PushBuilder method(String method);
    
    /**
     * プッシュに使用されるクエリ文字列を設定します。  
     *
     * クエリ文字列は {@link #path(String)} 呼び出しに含まれるすべてのクエリ文字列に追加されます。
     * 重複するパラメータはすべて保持しなければいけません。
     * このメソッドは、同じクエリ文字列で複数の{@link #push()}を呼び出すときに {@link #path(String)} のクエリの代わりに使用する必要があります。
     * 
     * @param  queryString プッシュに使用されるクエリ文字列
     * @return 自分自身
     */
    public PushBuilder queryString(String queryString);
    
    /**
     * プッシュに使用されるセッションIDを設定します。
     * セッションIDは関連のあるリクエストと同じ方法で設定されます。
     * (関連のあるリクエストでCookieが使用された場合はCookieとして、関連のあるリクエストでURLパラメーターが使用された場合はURLパラメータとして設定されます)
     *  デフォルトでは、リクエストで指定されたセッションIDまたは新しく生成されたセッションの新しく割り当てられたセッションIDになります。
     * 
     * @param sessionId プッシュに使用されるセッションID
     * @return 自分自身
     */
    public PushBuilder sessionId(String sessionId);
    
    /** 
     * <p>プッシュに使用されるリクエストヘッダーを設定します。すでにヘッダーに同じ名前が含まれていた場合、その値は上書きされます。</p>
     *
     * @param name 設定されるヘッダーの名前
     * @param value 設定されるヘッダーの値
     * @return 自分自身
     */
    public PushBuilder setHeader(String name, String value);
    
    /** 
     * <p>プッシュに使用されるリクエストヘッダーの値を追加します。</p>
     * @param name 追加されるヘッダーの名前
     * @param value 追加されるヘッダーの値
     * @return 自分自身
     */
    public PushBuilder addHeader(String name, String value);

    /** 
     * <p>リクエストヘッダーから指定された名前を削除します。ヘッダーに存在しない場合何もしません。</p>
     *
     * @param name 削除されるヘッダーの名前
     * @return 自分自身
     */
    public PushBuilder removeHeader(String name);

    /**
     * プッシュに使用されるURIパスを設定します。
     * 
     * "/"で始まるパスは絶対パスとして扱われます。そうでない場合は、関連するリクエストのコンテキストパスに相対パスとして扱われます。
     * パスのデフォルト値はありません。{@link #push()}メソッドを毎回呼び出すたびに{@link #path(String)}メソッドも呼び出す必要があります。
     * クエリ文字列が{@code path}引数で渡された場合、その内容は以前に{@link #queryString}メソッドで渡された内容とマージされ、重複が保存される必要があります。 
     *
     * @param path プッシュに使用されるクエリ文字列を含むことがあるURIパス
     * @return 自分自身
     */
    public PushBuilder path(String path);
    
    /**
     * PushBuilderの現在の状態でリソースをプッシュします、このメソッドはノンブロッキングでなければいけません。
     *
     * <p>PushBuilderの現在の状態に基づいてリソースをプッシュします。
     * このメソッドを呼び出してもリソースが実際にプッシュされることが保証されるわけではありません。
     * なぜならばHTTP/2プロトコルではクライアントはプッシュされたリソースの受け入れを拒否することができるからです。</p>
     * 
     * <p>PushBuilderにセッションIDがある場合、プッシュされたリクエストにはCookieまたはURIパラメーターとして適切なセッションIDが含まれます。
     * PushBuilderのクエリ文字列は渡されたクエリ文字列とマージされます。</p>
     * 
     * <p>このメソッドから戻る前にビルダーはパスと条件付きヘッダー（conditionalヘッダー：RFC 7232で定義されています）をnullにしています。
     * 他のすべてのフィールドの値は別のプッシュで再利用することができるようにそのままです。</p>
     * 
     * @throws IllegalStateException インスタンス化されてから、もしくは最後のIllegalStateExceptionが発生しなかった
     * {@code push()} メソッドの呼び出しのから{@link #path}メソッドが呼び出されていなかった場合。
     */
    public void push();
    
    /**
     * プッシュに使用されるメソッドを返します。
     *
     * @return プッシュに使用されるメソッド
     */
    public String getMethod();

    /**
     * プッシュに使用されるクエリ文字列を返します。
     *
     * @return プッシュに使用されるクエリ文字列
     */
    public String getQueryString();

    /**
     * プッシュに使用されるセッションIDを返します。
     * 
     * @return プッシュに使用されるセッションID
     */
    public String getSessionId();

    /**
     * プッシュに使用されるヘッダーの名前のsetを返します。
     *
     * <p>返されたsetは{@code PushBuilder}オブジェクトにから切り離されるため、返されたセットの変更は{@code PushBuilder}オブジェクトに反映されず、その逆もそうです。</p>
     *
     * @return プッシュに使用されるヘッダーの名前のset
     */
    public Set<String> getHeaderNames();

    /**
     * 指定された名前でプッシュに使用されるヘッダーの値を返します。
     * @param name ヘッダーの名前
     *
     * @return 指定された名前でプッシュに使用されるヘッダーの値.
     */
    public String getHeader(String name);

    /**
     * プッシュに使用されるURIのパスを返します。
     *
     * @return プッシュに使用されるURIのパス
     */
    public String getPath();
}
