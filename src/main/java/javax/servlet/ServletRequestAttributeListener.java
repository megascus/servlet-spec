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
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
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
 *
 *
 * This file incorporates work covered by the following copyright and
 * permission notice:
 *
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

