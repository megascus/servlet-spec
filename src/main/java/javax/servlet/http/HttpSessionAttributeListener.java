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

package javax.servlet.http;

import java.util.EventListener;

/**
 * HttpSessionの属性が変更された通知イベントを受け取るためのインターフェイスです。
 *
 * <p>通知イベントを受け取るためには、実装クラスをWebアプリケーションのデプロイメントディスクリプタで宣言するか、
 * {@link javax.servlet.annotation.WebListener}アノテーションを付けるか、
 * {@link javax.servlet.ServletContext}で定義されているaddListenerメソッドの1つを使って登録する必要があります。
 *
 * <p>このインターフェースの実装が呼び出される順序は定義されていません。
 *
 * @since Servlet 2.3
 */

public interface HttpSessionAttributeListener extends EventListener {

    /**
     * セッションの属性が追加された通知を受け取ります。
     *
     * @param event セッションと、セッションに追加された属性の名前と値を含むHttpSessionBindingEvent
     */
    default public void attributeAdded(HttpSessionBindingEvent event) {}

    /**
     * セッションの属性が削除された通知を受け取ります。
     *
     * @param event セッションと、セッションから削除された属性の名前と値を含むHttpSessionBindingEvent
     */
    default public void attributeRemoved(HttpSessionBindingEvent event) {}

    /**
     * セッションの属性が変更された通知を受け取ります。
     *
     * @param event セッションと、セッションから変更された属性の名前と(古い)値を含むHttpSessionBindingEvent
     */
    default public void attributeReplaced(HttpSessionBindingEvent event) {}

}

