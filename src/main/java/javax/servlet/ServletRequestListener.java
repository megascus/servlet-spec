/*
 * Copyright (c) 1997-2018 Oracle and/or its affiliates. All rights reserved.
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

import java.util.EventListener;

/**
 * ServletRequestがウェブアプリケーションのスコープに入ろうとしているのと外れようとしているのの通知イベントを受け取るためのインターフェースです。
 *
 * <p>ServletRequestはウェブアプリケーションの最初のサーブレットまたはフィルタに入るときにWebアプリケーションのスコープに入り、
 * 最後のサーブレットまたはチェーン内の最初のフィルタを終了するときに範囲外になると定義されます。
 *
 * <p>これらの通知イベントを受け取るには、実装クラスをウェブアプリケーションのデプロイメントディスクリプターで宣言するか、
 * {@link javax.servlet.annotation.WebListener}アノテーションを付けるか、
 * {@link ServletContext}で定義されたaddListenerメソッドの1つを使って登録する必要があります。
 * 
 * <p>このインターフェースの実装は、{@link #requestInitialized(ServletRequestEvent)}メソッドは宣言された順番で、
 * {@link #requestDestroyed(ServletRequestEvent)}はその逆の順番で呼び出されます。
 *
 * @since Servlet 2.4
 */

public interface ServletRequestListener extends EventListener {

    /**
     * ServletRequestがウェブアプリケーションのスコープから外れようとしている通知を受け取ります。
￥     *
     * @param sre ServletRequestとこのウェブアプリケーションをあらわすServletContextの含まれたServletRequestEvent
     *
     * @implSpec
     * デフォルト実装では何もしません
     */
    default public void requestDestroyed(ServletRequestEvent sre) {}

    /**
     * ServletRequestがウェブアプリケーションのスコープに入ろうとしている通知を受け取ります。
     *
     * @param sre ServletRequestとこのウェブアプリケーションをあらわすServletContextの含まれたServletRequestEvent
     *
     * @implSpec
     * デフォルト実装では何もしません
     */
    default public void requestInitialized(ServletRequestEvent sre) {}
}
