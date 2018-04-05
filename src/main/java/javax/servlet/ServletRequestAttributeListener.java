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
 * ServletRequestの属性が変更された通知イベントを受け取るためのインターフェースです。
 *
 * <p>リクエストはウェブアプリケーションのスコープ内にある間に通知が生成されます。
 * ServletRequestはウェブアプリケーションの最初のサーブレットまたはフィルターに入るときにウェブアプリケーションのスコープに入り、
 * 最後のサーブレットまたはチェーンの最初のフィルタを終了するときにスコープから外れると定義されます。
 * <p>訳注：いわゆるリクエストスコープ
 *
 * <p>これらの通知イベントを受け取るには、実装クラスをWebアプリケーションのデプロイメントディスクリプターで宣言するか、
 * {@link javax.servlet.annotation.WebListener}アノテーションを付けるか、
 * {@link ServletContext}で定義されたaddListenerメソッドの1つを使用して登録する必要があります。
 *
 * <p>このインターフェースの実装が呼び出される順序は定義されていません。
 *
 * @since Servlet 2.4
 */

public interface ServletRequestAttributeListener extends EventListener {

    /**
     * ServletRequestに属性が追加された通知を受け取ります。
     *
     * @param srae 属性が追加されたServletRequestに追加で属性の名前と値を含んだServletRequestAttributeEvent
     *
     * @implSpec
     * デフォルト実装では何もしません
     */
    default public void attributeAdded(ServletRequestAttributeEvent srae) {}

    /**
     * ServletRequestから属性が削除された通知を受け取ります。
     *
     * @param srae 属性が削除されたServletRequestに追加で属性の名前と値を含んだServletRequestAttributeEvent
     *
     * @implSpec
     * デフォルト実装では何もしません
     */
    default public void attributeRemoved(ServletRequestAttributeEvent srae) {}

    /**
     * ServletRequestの属性が置き換えられた通知を受け取ります。
     *
     * @param srae 属性が置き換えられたServletRequestに追加で属性の名前と古い値を含んだServletRequestAttributeEvent
     *
     * @implSpec
     * デフォルト実装では何もしません
     */
    default public void attributeReplaced(ServletRequestAttributeEvent srae) {}
}

