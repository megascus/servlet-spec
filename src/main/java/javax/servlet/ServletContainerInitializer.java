/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2017-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
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
 */

package javax.servlet;

import java.util.Set;

/**
 * Webアプリケーションの起動フェーズがライブラリ/ランタイムに通知され、それに応じてサーブレットやフィルター、リスナーの必要とするプログラマティックな登録を実行するためのインターフェースです。
 * 
 * <p>このインターフェースの実装には{@link javax.servlet.annotation.HandlesTypes HandlesTypes}アノテーションを付けることができ、
 * アノテーションによって指定されたクラスタイプを実装、拡張、またはアノテーションをつけられたアプリケーションクラスのSetを({@link #onStartup(Set, ServletContext)}メソッドで)受け取るために使用します。
 * 
 * <p>このインターフェースの実装が<tt>HandlesTypes</tt>アノテーションを使用しない場合、またはアプリケーション内のクラスのどれもがアノテーションで指定されたものと一致しない場合は
 * コンテナはクラスのSetとして<tt>null</tt>を{@link #onStartup(Set, ServletContext)}に渡します。
 * 
 * <p>アプリケーションのクラスを調べて<tt>ServletContainerInitializer</tt>の<tt>HandlesTypes</tt>アノテーションで指定された基準と一致するかどうかを確認するときに、
 * コンテナはアプリケーションのオプショナルなJARファイルが見つからない場合にクラスローディングの問題に遭遇する可能性があります。 
 * コンテナはこれらのタイプのクラスローディングの失敗がアプリケーションの正常な動作を妨げるかどうかを判断する立場にないため、それらを無視する必要があると同時にログを記録するような設定オプションを提供します。
 * 
 * <p>このインターフェースの実装はJARファイル内の<tt>META-INF/services</tt>ディレクトリにあるこのインターフェースの完全修飾クラス名の名前が付けられたリソースによって宣言され、
 * ランタイムのサービスプロバイダルックアップメカニズムまたはそれと意味的に同等のコンテナ固有のメカニズムを使用して検出されます。
 * どちらの場合でも、絶対順序から除外されたWebフラグメントJARファイルの<tt>ServletContainerInitializer</tt>サービスは無視する必要があり、
 * これらのサービスが検出される順序はアプリケーションのクラスローダー委譲モデルに従う必要があります。
 *
 * @see javax.servlet.annotation.HandlesTypes
 *
 * @since Servlet 3.0
 */
public interface ServletContainerInitializer {

    /**
     * 与えられた<tt>ServletContext</tt>が表すアプリケーションの起動をこの<tt>ServletContainerInitializer</tt>に通知します。
     * 
     * <p>この<tt>ServletContainerInitializer</tt>がアプリケーションの<tt>WEB-INF/lib</tt>内のJARファイルに入っているならば、
     * この<tt>onStartup</tt>メソッドは含まれているアプリケーションの起動中に1回だけ実行されるでしょう。
     * この<tt>ServletContainerInitializer</tt>が<tt>WEB-INF/lib</tt>ディレクトリ以外のJARファイルに入っているが、
     * 上記のように引き続き発見可能であるならば、この<tt>onStartup</tt> はアプリケーションが起動するたびに実行されるでしょう。
     *
     * @param c {@link javax.servlet.annotation.HandlesTypes HandlesTypes}アノテーションで指定されたクラス型を拡張もしくは実装、またはアノテーションのつけられたアプリケーション内のクラスのSet、
     * 一致するものがない、またはこの<tt>ServletContainerInitializer</tt>に{@link javax.servlet.annotation.HandlesTypes HandlesTypes}アノテーションが付けられていない場合はnull
     *
     * @param ctx 起動中に<tt>c</tt>に含まれるクラスが見つかったウェブアプリケーションの<tt>ServletContext</tt>
     *
     * @throws ServletException エラーが発生した場合
     */
    public void onStartup(Set<Class<?>> c, ServletContext ctx)
        throws ServletException; 
}
