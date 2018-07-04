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

/**
 * ServletRequestで開始された非同期操作({@link ServletRequest#startAsync()} または{@link ServletRequest#startAsync(ServletRequest, ServletResponse)}の呼び出し)
 * が完了した、もしくはタイムアウトした、エラーが発生したときに発生するイベントです。
 *
 * @since Servlet 3.0
 */
public class AsyncEvent { 

    private AsyncContext context;
    private ServletRequest request;
    private ServletResponse response;
    private Throwable throwable;


    /**
     * 与えられたAsyncContextからAsyncEventを生成します。
     *
     * @param context このAsyncEventと共に配信されるAsyncContext
     */
    public AsyncEvent(AsyncContext context) {
        this(context, context.getRequest(), context.getResponse(), null);
    }

    /**
     * 与えられたAsyncContextとServletRequestとServletResponseからAsyncEventを生成します。
     *
     * @param context このAsyncEventと共に配信されるAsyncContext
     * @param request このAsyncEventと共に配信されるServletRequest
     * @param response このAsyncEventと共に配信されるServletResponse
     */
    public AsyncEvent(AsyncContext context, ServletRequest request,
            ServletResponse response) {
        this(context, request, response, null);
    }

    /**
     * 与えられたAsyncContextとThrowableからAsyncEventを生成します。
     *
     * @param context このAsyncEventと共に配信されるAsyncContext
     * @param throwable このAsyncEventと共に配信されるThrowable
     */
    public AsyncEvent(AsyncContext context, Throwable throwable) {
        this(context, context.getRequest(), context.getResponse(), throwable);
    }

    /**
     * 与えられたAsyncContextとServletRequestとServletResponseとThrowableからAsyncEventを生成します。
     *
     * @param context このAsyncEventと共に配信されるAsyncContext
     * @param request このAsyncEventと共に配信されるServletRequest
     * @param response このAsyncEventと共に配信されるServletResponse
     * @param throwable このAsyncEventと共に配信されるThrowable
     */
    public AsyncEvent(AsyncContext context, ServletRequest request,
            ServletResponse response, Throwable throwable) {
        this.context = context;
        this.request = request;
        this.response = response;
        this.throwable = throwable;
    }

    /**
     * このAsyncEventからAsyncContextを取得します。
     *
     * @return このAsyncEventの初期化に使用されたAsyncContext
     */
    public AsyncContext getAsyncContext() {
        return context;
    }

    /**
     * このAsyncEventからServletRequestを取得します。
     *
     * <p>このAsyncEventが配信されているAsyncListenerが{@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)}を使用して追加された場合、
     * 返されるServletRequestは上記のメソッドに提供されるServletRequestと同じになります。
     * AsyncListenerが{@link AsyncContext#addListener(AsyncListener)}を使用して追加された場合、このメソッドはnullを返す必要があります。
     *
     * @return このAsyncEventの初期化に使用されたServletRequest、ServletRequest無しでこのAsyncEventが初期化された場合はnull
     */
    public ServletRequest getSuppliedRequest() {
        return request;
    }

    /**
     * このAsyncEventからServletResponseを取得します。
     *
     * <p>このAsyncEventが配信されているAsyncListenerが{@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)}を使用して追加された場合、
     * 返されるServletResponseは上記のメソッドに提供されるServletResponseと同じになります。
     * AsyncListenerが{@link AsyncContext#addListener(AsyncListener)}を使用して追加された場合、このメソッドはnullを返す必要があります。
     *
     * @return このAsyncEventの初期化に使用されたServletResponse、ServletResponse無しでこのAsyncEventが初期化された場合はnull
     */
    public ServletResponse getSuppliedResponse() {
        return response;
    }

    /**
     * このAsyncEventからThrowableを取得します。
     *
     * @return このAsyncEventの初期化に使用されたThrowable、Throwable無しでこのAsyncEventが初期化された場合はnull
     */
    public Throwable getThrowable() {
        return throwable;
    }

}

