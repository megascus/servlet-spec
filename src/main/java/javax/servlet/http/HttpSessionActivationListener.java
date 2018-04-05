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
 * セッションに保存されたオブジェクトは、セッションが非活性化する、活性化するコンテナイベントの通知を受け取ることができます。
 * コンテナはVM間でセッションを移動したり、セッションを永続化したときにセッションに保存されたすべての属性のうち HttpSessionActivationListener を実装したものに通知を送ることが必須要件です。
 * 
 * 訳注：サーブレットコンテナの実装によってはメモリ上のセッションが肥大化したときにセッションの一部をディスクに退避(非活性化)したり、退避したセッションをメモリ上に復元(活性化)する機能を持っている場合があります。
 * 同様に、複数のサーブレットコンテナ(VM)でクラスタリングされているときに他のサーブレットコンテナに送信(非活性化)され、受け取る(活性化する機能を持っていることもあります。
 *
 * @since Servlet 2.3
 */

public interface HttpSessionActivationListener extends EventListener { 

    /**
     * セッションが非活性化されようとしているという通知です。
     * 
     * @implSpec
     * デフォルト実装では何も行いません。
     * 
     * @param se セッションの非活性化をしめす {@link HttpSessionEvent}
     */
    default public void sessionWillPassivate(HttpSessionEvent se) {}

    /**
     * セッションが活性化されたという通知です。
     *
     * @implSpec
     * デフォルト実装では何も行いません。
     * 
     * @param se セッションの活性化をしめす {@link HttpSessionEvent}
     */
    default public void sessionDidActivate(HttpSessionEvent se) {}
} 
