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
 * ServletRequestで開始された非同期操作の実行コンテキストを表すクラスです。
 * 
 * <p>AsyncContextは{@link ServletRequest#startAsync()}または{@link ServletRequest#startAsync(ServletRequest, ServletResponse)}の呼び出しによって作成および初期化されます。
 * これらのメソッドを繰り返し実行すると、同じAsyncContextインスタンスが返され、適切に再初期化されます。
 * 
 * <p>非同期操作がタイムアウトした場合、コンテナは次の手順を実行する必要があります。
 * <ol>
 * <li>非同期操作が開始されたServletRequestに登録されているすべての{@link AsyncListener}インスタンスの{@link AsyncListener#onTimeout onTimeout}メソッドを実行します。</li>
 * <li>すべてのリスナーが{@link #complete}メソッドや、いかなる{@link #dispatch}メソッドも呼び出さなかった場合は<tt>HttpServletResponse.SC_INTERNAL_SERVER_ERROR</tt>に等しいステータスコードを持つエラーディスパッチを実行します。</li>
 * <li>一致するエラーページが見つからなかった場合、またはエラーページが{@link #complete}や、いかなる{@link #dispatch}メソッドも呼び出さなかった場合は{@link #complete}を呼び出します。</li>
 * </ol>
 *
 * @since Servlet 3.0
 */
public interface AsyncContext {

    /**
     * {@link #dispatch(String)}や{@link #dispatch(ServletContext,String)}のターゲットで使用可能になるオリジナルのリクエストURIを持つリクエスト属性の名前 
     */
    static final String ASYNC_REQUEST_URI = "javax.servlet.async.request_uri";

    /**
     * {@link #dispatch(String)}や{@link #dispatch(ServletContext,String)}のターゲットで使用可能になるオリジナルのコンテキストパスを持つリクエスト属性の名前
     */
    static final String ASYNC_CONTEXT_PATH = "javax.servlet.async.context_path";

    /**
     * {@link #dispatch(String)}や{@link #dispatch(ServletContext,String)}のターゲットで使用可能になるオリジナルの{@link javax.servlet.http.HttpServletMapping}を持つリクエスト属性の名前
     */
    static final String ASYNC_MAPPING = "javax.servlet.async.mapping";

    /**
     * {@link #dispatch(String)}や{@link #dispatch(ServletContext,String)}のターゲットで使用可能になるオリジナルのパス情報を持つリクエスト属性の名前
     */
    static final String ASYNC_PATH_INFO = "javax.servlet.async.path_info";

    /**
     * {@link #dispatch(String)}や{@link #dispatch(ServletContext,String)}のターゲットで使用可能になるオリジナルのサーブレットパスを持つリクエスト属性の名前
     */
    static final String ASYNC_SERVLET_PATH = "javax.servlet.async.servlet_path";

    /**
     * {@link #dispatch(String)}や{@link #dispatch(ServletContext,String)}のターゲットで使用可能になるオリジナルのクエリ文字列を持つリクエスト属性の名前
     */
    static final String ASYNC_QUERY_STRING = "javax.servlet.async.query_string";


    /**
     * {@link ServletRequest#startAsync()} や {@link ServletRequest#startAsync(ServletRequest, ServletResponse)}の呼び出しによってこのAsyncContextの初期化に使用されたリクエストを取得します。
     *
     * @return このAsyncContextの初期化に使用されたリクエスト
     *
     * @exception IllegalStateException  非同期サイクルによって{@link #complete}やいずれかの{@link #dispatch}メソッドが呼び出されていた場合
     */
    public ServletRequest getRequest();


    /**
     * {@link ServletRequest#startAsync()} や {@link ServletRequest#startAsync(ServletRequest, ServletResponse)}の呼び出しによってこのAsyncContextの初期化に使用されたレスポンスを取得します。
     *
     * @return このAsyncContextの初期化に使用されたレスポンス
     *
     * @exception IllegalStateException  非同期サイクルによって{@link #complete}やいずれかの{@link #dispatch}メソッドが呼び出されていた場合
     */
    public ServletResponse getResponse();


    /**
     * このAsyncContextがオリジナルのリクエストとレスポンスのオブジェクトか、アプリケーションでラップされたリクエストとレスポンスのオブジェクトかをチェックします。
     * 
     * <p>この情報は<i>アウトバウンド</i>方向で実行されたフィルターによって使用され、リクエストが非同期実行モードになった後に、
     * それらの<i>インバウンド</i>呼び出しの間に追加したリクエストラッパーおよび/またはレスポンスラッパーが非同期操作の間、保持される必要があるか、または解放される必要があるかどうかを判別します。
     *
     * @return AsyncContextが{@link ServletRequest#startAsync()}を呼び出してオリジナルのリクエストとレスポンスのオブジェクトで初期化されたか、
     * {@link ServletRequest#startAsync(ServletRequest, ServletResponse)}を呼び出してどちらの引数もアプリケーションが提供したラッパーを使用せずに初期化された場合はtrue、そうでない場合はfalse
     */
    public boolean hasOriginalRequestAndResponse();


    /**
     * このAsyncContextのリクエストとレスポンスのオブジェクトをサーブレットコンテナにディスパッチします。
     * 
     * <p>非同期サイクルが{@link ServletRequest#startAsync(ServletRequest, ServletResponse)}で開始され、
     * 渡されたリクエストがHttpServletRequestのインスタンスである場合、
     * ディスパッチは{@link javax.servlet.http.HttpServletRequest#getRequestURI}によって返されたURIに対して行われます。
     * それ以外の場合、ディスパッチはコンテナによって最後にディスパッチされたときのリクエストのURIに対して行われます。
     *
     * <p>次のシーケンスでこれがどのように機能するかを示します。
     * <pre>{@code
     * // REQUEST dispatch to /url/A
     * AsyncContext ac = request.startAsync();
     * ...
     * ac.dispatch(); // ASYNC dispatch to /url/A
     * 
     * // REQUEST to /url/A
     * // FORWARD dispatch to /url/B
     * request.getRequestDispatcher("/url/B").forward(request,response);
     * // Start async operation from within the target of the FORWARD
     * // dispatch
     * ac = request.startAsync();
     * ...
     * ac.dispatch(); // ASYNC dispatch to /url/A
     * 
     * // REQUEST to /url/A
     * // FORWARD dispatch to /url/B
     * request.getRequestDispatcher("/url/B").forward(request,response);
     * // Start async operation from within the target of the FORWARD
     * // dispatch
     * ac = request.startAsync(request,response);
     * ...
     * ac.dispatch(); // ASYNC dispatch to /url/B
     * }</pre>
     *
     * <p>このメソッドはディスパッチ操作が実行されるコンテナ管理スレッドにリクエストとレスポンスのオブジェクトを渡すとすぐに戻ります。
     * <tt>startAsync</tt>を呼び出したコンテナ開始ディスパッチがコンテナに返される前にこのメソッドが呼び出されると、
     * コンテナ開始ディスパッチがコンテナに返されるまでディスパッチ操作は遅延されます。
     *
     * <p>リクエストのディスパッチャータイプは<tt>DispatcherType.ASYNC</tt>に設定されます。
     * {@link RequestDispatcher#forward(ServletRequest, ServletResponse) forward dispatches}とは異なり、
     * レスポンスのバッファとヘッダーはリセットされず、レスポンスがすでにコミットされていてもディスパッチすることはできます。
     *
     * <p>{@link ServletRequest#startAsync()}や{@link ServletRequest#startAsync(ServletRequest, ServletResponse)}が呼び出されない限り、
     * リクエストとレスポンスの制御はディスパッチターゲットに委譲され、ディスパッチターゲットの実行が完了するとレスポンスがクローズされます。
     * 
     * <p>このメソッドの実行中に発生する可能性のあるエラーや例外は、コンテナによって次のように捕捉され、処理されなければなりません。
     * <ol>
     * <li>このAsyncContextが作成されたServletRequestに登録されているすべての{@link AsyncListener}インスタンスを、
     * キャッチされたThrowableを{@link AsyncEvent#getThrowable}を使用して利用可能にした状態で{@link AsyncListener#onError onError}メソッドを呼び出します。</li>
     * <li>リスナーがどれも{@link #complete}も{@link #dispatch}のいずれかも呼び出さない場合、
     * <tt>HttpServletResponse.SC_INTERNAL_SERVER_ERROR</tt>と等しいステータスコードでエラーディスパッチを行い、
     * 上記の<tt>Throwable</tt>を<tt>RequestDispatcher.ERROR_EXCEPTION</tt>リクエスト属性の値として利用可能にします。</li>
     * <li>一致するエラーページが見つからない場合、もしくはエラーページが{@link #complete}や{@link #dispatch}メソッドのいずれも呼び出さない場合は、
     * {@link #complete}を呼び出します。</li>
     * </ol>
     *
     * <p>{@link ServletRequest#startAsync}メソッドの1つを呼び出すことによって開始される非同期サイクルごとに、最大で1つの非同期ディスパッチ操作を行うことができます。
     * 同じ非同期サイクル内で追加の非同期ディスパッチ操作を実行しようとするとIllegalStateExceptionが発生します。
     * その後にstartAsyncがディスパッチされたリクエストで呼び出された場合、dispatchメソッドまたは{@link #complete}メソッドのいずれかが呼び出される可能性があります。
     *
     * @throws IllegalStateException ディスパッチメソッドの1つが呼び出され、ディスパッチの結果までの間にstartAsyncメソッドが呼び出されなかった場合、または{@link #complete}が呼び出された場合
     *
     * @see ServletRequest#getDispatcherType
     */
    public void dispatch();


    /**
     * このAsyncContextのリクエストとレスポンスのオブジェクトを与えられた<tt>path</tt>にディスパッチします。
     * 
     * <p><tt>path</tt>パラメータは{@link ServletRequest#getRequestDispatcher(String)}と、
     * このAsyncContextが初期化された{@link ServletContext}のスコープにて同じ方法で解釈されます。
     * 
     * <p>リクエストのすべてのパスに関連する問合せメソッドは、ディスパッチターゲットを反映する必要がありますが、
     * オリジナルのリクエストURIやコンテキストパス、パス情報、サーブレットパスおよび問合せ文字列はリクエスト属性の{@link #ASYNC_REQUEST_URI}、{@link #ASYNC_CONTEXT_PATH}、{@link #ASYNC_PATH_INFO}、{@link #ASYNC_SERVLET_PATH}、{@link #ASYNC_QUERY_STRING}から復元できるでしょう。
     * これらの属性はディスパッチを繰り返しても、常にオリジナルのパス要素を反映します。
     * 
     * <p>{@link ServletRequest#startAsync}メソッドの1つを呼び出すことによって開始される非同期サイクルごとに、最大で1つの非同期ディスパッチ操作を行うことができます。
     * 同じ非同期サイクル内で追加の非同期ディスパッチ操作を実行しようとするとIllegalStateExceptionが発生します。
     * その後にstartAsyncがディスパッチされたリクエストで呼び出された場合、dispatchメソッドまたは{@link #complete}メソッドのいずれかが呼び出される可能性があります。
     *
     * <p>エラーハンドリングを含め、詳細については{@link #dispatch()}を参照してください。
     *
     * @param path このAsyncContextが初期化されたServletContextのスコープにあるディスパッチターゲットのパス
     *
     * @throws IllegalStateException ディスパッチメソッドの1つが呼び出され、ディスパッチの結果までの間にstartAsyncメソッドが呼び出されなかった場合、または{@link #complete}が呼び出された場合
     *
     * @see ServletRequest#getDispatcherType
     */
    public void dispatch(String path);


    /**
     * このAsyncContextのリクエストとレスポンスのオブジェクトを与えられた<tt>context</tt>のスコープにある与えられた<tt>path</tt>にディスパッチします。
     * 
     * <p><tt>path</tt>パラメータは{@link ServletRequest#getRequestDispatcher(String)}と、
     * 与えられた<tt>context</tt>のスコープであることを除いて同じ方法で解釈されます。
     * 
     * <p>リクエストのすべてのパスに関連する問合せメソッドは、ディスパッチターゲットを反映する必要がありますが、
     * オリジナルのリクエストURIやコンテキストパス、パス情報、サーブレットパスおよび問合せ文字列はリクエスト属性の{@link #ASYNC_REQUEST_URI}、{@link #ASYNC_CONTEXT_PATH}、{@link #ASYNC_PATH_INFO}、{@link #ASYNC_SERVLET_PATH}、{@link #ASYNC_QUERY_STRING}から復元できるでしょう。
     * これらの属性はディスパッチを繰り返しても、常にオリジナルのパス要素を反映します。
     * 
     * <p>{@link ServletRequest#startAsync}メソッドの1つを呼び出すことによって開始される非同期サイクルごとに、最大で1つの非同期ディスパッチ操作を行うことができます。
     * 同じ非同期サイクル内で追加の非同期ディスパッチ操作を実行しようとするとIllegalStateExceptionが発生します。
     * その後にstartAsyncがディスパッチされたリクエストで呼び出された場合、dispatchメソッドまたは{@link #complete}メソッドのいずれかが呼び出される可能性があります。
     *
     * <p>エラーハンドリングを含め、詳細については{@link #dispatch()}を参照してください。
     *
     * @param context ディスパッチターゲットのServletContext
     * @param path 与えられたServletContextのスコープにあるディスパッチターゲットのパス
     *
     * @throws IllegalStateException ディスパッチメソッドの1つが呼び出され、ディスパッチの結果までの間にstartAsyncメソッドが呼び出されなかった場合、または{@link #complete}が呼び出された場合
     *
     * @see ServletRequest#getDispatcherType
     */
    public void dispatch(ServletContext context, String path);


    /**
     * このAsyncContextを初期化するために使用されたリクエストで開始された非同期操作を完了し、
     * このAsyncContextを初期化するために使用されたレスポンスを閉じます。
     * 
     * <p>このAsyncContextが作成されたServletRequestに登録されている{@link AsyncListener}型のリスナーは{@link AsyncListener#onComplete(AsyncEvent) onComplete}メソッドが呼び出されます。
     * 
     * <p>{@link ServletRequest#startAsync()}または{@link ServletRequest#startAsync(ServletRequest, ServletResponse)}を呼び出した後、
     * およびこのクラスの<tt>dispatch</tt>メソッドのうちの1つを呼び出す前ならいつでもこのメソッドを呼び出すことができます。
     *
     * <tt>startAsync</tt>を呼び出したコンテナ開始ディスパッチがコンテナに返される前にこのメソッドが呼び出されると、
     * コンテナによって開始されたディスパッチが完了するまで、コンテナに戻された後までこのメソッドの呼び出しは何も効果を及ぼしません
     * (し、いかなる{@link AsyncListener#onComplete(AsyncEvent)}呼び出しは遅延されます)。
     */
    public void complete();


    /**
     * コンテナがおそらく管理されたスレッドプールから取得したスレッドにディスパッチして<tt>Runnable</tt>を実行させます。
     * コンテナは適切なコンテキスト情報を<tt>Runnable</tt>に伝えることができます。
     *
     * @param run 非同期ハンドラ
     */
    public void start(Runnable run);


    /**
     * 与えられた{@link AsyncListener}を{@link ServletRequest#startAsync}メソッドのいずれかの呼び出しによって開始された最新の非同期サイクルに登録します。
     *
     * <p>指定されたAsyncListenerは、非同期サイクルが正常に完了したとき、タイムアウトしたとき、エラーの結果の時、
     * {@link ServletRequest#startAsync}メソッドのいずれかの呼び出しによって新しい非同期サイクルが開始された時に{@link AsyncEvent}を受け取ります。
     *
     * <p>AsyncListenerインスタンスは追加された順番で通知されます。
     *
     * <p>{@link ServletRequest#startAsync(ServletRequest, ServletResponse)}か{@link ServletRequest#startAsync}が呼びだされると、
     * {@link AsyncListener}に通知された時には正しく同じリクエストとレスポンスのオブジェクトが{@link AsyncEvent}から使用可能になります。
     *
     * @param listener 登録されるAsyncListener
     * 
     * @throws IllegalStateException このメソッドがコンテナが開始したディスパッチの後、{@link ServletRequest#startAsync}メソッドのうちの一つが呼び出された後の間、コンテナに返されるまでに呼ばれた場合
     */
    public void addListener(AsyncListener listener);


    /**
     * 与えられた{@link AsyncListener}を{@link ServletRequest#startAsync}メソッドのいずれかの呼び出しによって開始された最新の非同期サイクルに登録します。
     * 
     * <p>指定されたAsyncListenerは、非同期サイクルが正常に完了したとき、タイムアウトしたとき、エラーの結果の時、
     * {@link ServletRequest#startAsync}メソッドのいずれかの呼び出しによって新しい非同期サイクルが開始された時に{@link AsyncEvent}を受け取ります。
     *
     * <p>AsyncListenerインスタンスは追加された順番で通知されます。
     *
     * <p>与えられたServletRequestとServletResponseのオブジェクトは、
     * 与えられたAsyncListenerに届けられた{@link AsyncEvent}内の各々{@link AsyncEvent#getSuppliedRequest getSuppliedRequest}メソッドと{@link AsyncEvent#getSuppliedResponse getSuppliedResponse}メソッドを使用することで
     * AsyncListenerで使用できるようになります。
     * これらのオブジェクトは与えられたAsyncListenerが登録されてから追加でのラッピングが発生する可能性があるだけではなく、
     * それらに関連したリソースの開放のために使用される可能性もあるため、AsyncEventが配信された時点では読み書きしないでください。
     *
     * @param listener 登録されるAsyncListener
     * @param servletRequest AsyncEventに含まれるようになるServletRequest
     * @param servletResponse AsyncEventに含まれるようになるServletResponse
     *
     * @throws IllegalStateException このメソッドがコンテナが開始したディスパッチの後、{@link ServletRequest#startAsync}メソッドのうちの一つが呼び出された後の間、コンテナに返されるまでに呼ばれた場合
     */
    public void addListener(AsyncListener listener,
                            ServletRequest servletRequest,
                            ServletResponse servletResponse);


    /**
     * 与えられた{@link AsyncListener}クラスをインスタンス化します。
     *
     * <p>返されたAsyncListenerのインスタンスは<code>addListener</code>メソッドのうちの一つを利用してこのAsyncContextに登録する前に、
     * さらにカスタマイズすることもできます。
     *
     * <p>指定されたAsyncListenerはインスタンス化するために使用される引数のないコンストラクタが定義されている必要があります。
     *
     * <p>このメソッドは指定された<tt>clazz</tt>がマネージドビーンを表す場合、リソースインジェクションをサポートします。
     * マネージドビーンとリソースインジェクションについての詳細はJava EEプラットフォームとJSR 299の仕様を参照してください。
     * 
     * <p>このメソッドは、AsyncListenerに適用可能なアノテーションをすべてサポートします。
     *
     * @param <T> インスタンス化されるオブジェクトのクラス
     * @param clazz インスタンス化されるAsyncListenerクラス
     *
     * @return 新しいAsyncListenerのインスタンス
     *
     * @throws ServletException 指定された<tt>clazz</tt>のインスタンス化に失敗した場合
     */
    public <T extends AsyncListener> T createListener(Class<T> clazz)
        throws ServletException; 


    /**
     * このAsyncContextのタイムアウト時間を(ミリ秒単位で)設定します。
     * 
     * <p>タイムアウトはこのAscyncContextがひとたびコンテナが開始したディスパッチが{@link ServletRequest#startAsync}メソッドのうちが呼び出されコンテナに返されるまでの間に適用されます。
     *
     * <p>{@link #complete}メソッドも、いかなるディスパッチメソッドも呼び出されない場合、タイムアウトします。 
     * 0以下のタイムアウト値は、タイムアウトがないことを示します。
     * 
     * <p>{@link #setTimeout}が呼び出されてない場合、コンテナのデフォルトのタイムアウトとして{@link #getTimeout}の呼び出しによって使用可能な値が適用されます。
     *
     * <p>デフォルト値は<code>30000</code>ミリ秒です。
     *
     * @param timeout ミリ秒単位のタイムアウト時間
     *
     * @throws IllegalStateException このメソッドがコンテナが開始したディスパッチの後、{@link ServletRequest#startAsync}メソッドのうちの一つが呼び出された後の間、コンテナに返されるまでに呼ばれた場合
     */
    public void setTimeout(long timeout);


    /**
     * このAsyncContextタイムアウト時間を(ミリ秒単位で)取得します。
     *
     * <p>このメソッドは、コンテナのデフォルトの非同期操作のタイムアウト値、または{@link #setTimeout}の直近の呼び出しに渡されたタイムアウト値を返します。
     *
     * <p>0以下のタイムアウト値はタイムアウトしないことを表します。
     *
     * @return ミリ秒単位のタイムアウト時間
     */
    public long getTimeout();

}
