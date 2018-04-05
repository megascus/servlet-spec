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
 * ServletContextの属性が変更された通知イベントを受け取るためのインターフェースです。
 *
 * <p>これらの通知イベントを受け取るには、実装クラスをWebアプリケーションのデプロイメントディスクリプターで宣言するか、
 * {@link javax.servlet.annotation.WebListener}アノテーションを付けるか、
 * {@link ServletContext}で定義されたaddListenerメソッドの1つを使用して登録する必要があります。
 * 
 * <p>このインターフェースの実装が呼び出される順序は定義されていません。
 *
 * @see ServletContextAttributeEvent
 *
 * @since Servlet 2.3
 */

public interface ServletContextAttributeListener extends EventListener {

    /**
     * ServletContextに属性が追加された通知を受け取ります。
     *
     * @param event 属性が追加されたServletContextに追加で属性の名前と値を含んだServletContextAttributeEvent
     *
     * @implSpec
     * デフォルト実装では何もしません
     */
    default public void attributeAdded(ServletContextAttributeEvent event) {}

    /**
     * ServletContextから属性が削除された通知を受け取ります。
     *
     * @param event 属性が削除されたServletContextに追加で属性の名前と値を含んだServletContextAttributeEvent
     *
     * @implSpec
     * デフォルト実装では何もしません
     */
    default public void attributeRemoved(ServletContextAttributeEvent event) {}

    /** 
     * ServletContextの属性が置き換えられた通知を受け取ります。
     *
     * @param event 属性が置き換えられたServletContextに追加で属性の名前と古い値を含んだServletContextAttributeEvent
     *
     * @implSpec
     * デフォルト実装では何もしません
     */
    default public void attributeReplaced(ServletContextAttributeEvent event) {}
}

