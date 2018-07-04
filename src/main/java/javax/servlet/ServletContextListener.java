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

