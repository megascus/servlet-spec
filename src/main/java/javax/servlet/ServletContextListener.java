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
 * ServletContextのライフサイクルの変更イベントの通知を受け取るためのインターフェースです。
 *
 * <p>これらの通知イベントを受け取るには、実装クラスをウェブアプリケーションのデプロイメントディスクリプターで宣言するか、
 * {@link javax.servlet.annotation.WebListener}アノテーションを付けるか、
 * {@link ServletContext}で定義されたaddListenerメソッドの1つを使って登録する必要があります。
 * 
 * <p>このインターフェースの実装は、{@link #contextInitialized}メソッドは宣言された順番で、{@link #contextDestroyed}はその逆の順番で呼び出されます。
 *
 * @see ServletContextEvent
 *
 * @since Servlet 2.3
 */
public interface ServletContextListener extends EventListener {

    /**
     * ウェブアプリケーションの初期化処理がスタートした通知を受け取ります。
     *
     * <p>ウェブアプリケーション内のフィルターやサーブレットが初期化される前にすべてのServletContextListenerはServletContextの初期化を通知されます。
     *
     * @param sce 初期化されているServletContextを含むServletContextEvent
     *
     * @implSpec
     * デフォルト実装では何もしません
     */
    default public void contextInitialized(ServletContextEvent sce) {}

    /**
     * ServletContextがシャットダウンされようとしている通知を受け取ります。
     *
     * <p>ServletContextListenerにServletContextの破棄が通知される前に、すべてのサーブレットとフィルタが破棄されます。
     *
     * @param sce the ServletContextEvent containing the ServletContext
     * that is being destroyed
     *
     * @implSpec
     * デフォルト実装では何もしません
     */
    default public void contextDestroyed(ServletContextEvent sce) {}
}

