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
 * セッションに追加または削除されたときにオブジェクトに通知させます。
 * オブジェクトは{@link HttpSessionBindingEvent}オブジェクトによって通知されます。
 * これは、サーブレットのプログラマがセッションから属性を削除したり、セッションが無効化されたり、セッションがタイムアウトしたりした時に発生してもよいです。
 *
 * <p>訳注：このクラスは実際にセッションに追加するクラスに実装させます。
 * @author		Various
 *
 * @see HttpSession
 * @see HttpSessionBindingEvent
 *
 */

public interface HttpSessionBindingListener extends EventListener {

    /**
     * セッションに追加されたことをオブジェクトに通知し、セッションを識別します。
     *
     * @implSpec
     * デフォルト実装では何も行いません。
     * 
     * @param event		セッションを識別するイベント
     *
     * @see #valueUnbound
     *
     */ 
    default public void valueBound(HttpSessionBindingEvent event) {}

    /**
     * セッションから削除されたことをオブジェクトに通知し、セッションを識別します。
     *
     * @implSpec
     * デフォルト実装では何も行いません。
     *
     * @param event		セッションを識別するイベント
     *	
     * @see #valueBound
     *
     */
    default public void valueUnbound(HttpSessionBindingEvent event) {}
}
