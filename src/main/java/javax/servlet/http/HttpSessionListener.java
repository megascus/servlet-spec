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
 * HttpSessionのライフサイクルが変更された通知を受け取るためのインターフェースです。
 * 
 * <p>通知イベントを受け取るためには、実装クラスをWebアプリケーションのデプロイメントディスクリプタで宣言するか、
 * {@link javax.servlet.annotation.WebListener}アノテーションを付けるか、
 * {@link javax.servlet.ServletContext}で定義されているaddListenerメソッドの1つを使って登録する必要があります。
 *
 * <p>このインターフェースの実装は{@link #sessionCreated}メソッドは宣言された順に、 
 * {@link #sessionDestroyed}メソッドは逆の順序で呼び出されます。
 *
 * @see HttpSessionEvent
 *
 * @since Servlet 2.3
 */
public interface HttpSessionListener extends EventListener {
    
    /**
     * セッションが作成された通知を受け取ります。 
     *
     * @implSpec
     * デフォルト実装では何も行いません。
     *
     * @param se セッションを含む HttpSessionEvent
     */
    default public void sessionCreated(HttpSessionEvent se) {}
    
    /** 
     * セッションが無効化された通知を受け取ります。   
     *
     * @implSpec
     * デフォルト実装では何も行いません。
     *
     * @param se セッションを含む HttpSessionEvent
     */
    default public void sessionDestroyed(HttpSessionEvent se) {}
}
