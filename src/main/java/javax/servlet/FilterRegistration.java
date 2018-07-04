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

import java.util.*;

/**
 * {@link Filter} に詳細な構成をするためのインターフェース。
 *
 * @since Servlet 3.0
 */
public interface FilterRegistration extends Registration {

    /**
     * このFilterRegistrationによって表されるフィルターに対して、与えられたサーブレット名とディスパッチャータイプを持つフィルターマッピングを追加します。
     *
     * <p>フィルターマッピングは追加された順番で一致します。
     * 
     * <p><tt>isMatchAfter</tt>パラメータの値に応じて、このFilterRegistrationが取得されたServletContextで<i>宣言された</i>
     * フィルターマッピングの後または前に、与えられたフィルターマッピングを設定します。
     *
     * <p>このメソッドが複数回呼び出された場合、後続の各呼び出しが前の呼び出しの効果に追加されます。
     *
     * @param dispatcherTypes フィルターマッピングのディスパッチャータイプ、
     * nullの場合はデフォルトとして <tt>DispatcherType.REQUEST</tt>が使用される
     * @param isMatchAfter 与えられたフィルターマッピングが宣言されたフィルターマッピングの後で一致する必要がある場合はtrue、
     * このFilterRegistrationが取得されたServletContextで宣言されたフィルターマッピングの前に一致しなければならない場合はfalse
     * @param servletNames フィルターマッピングのサーブレット名
     *
     * @throws IllegalArgumentException <tt>servletNames</tt>がnullもしくは空の場合
     * @throws IllegalStateException このFilterRegistrationが取得されたServletContextがすでに初期化完了していた場合
     */
    public void addMappingForServletNames(
        EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter,
        String... servletNames);

    /**
     * この<code>FilterRegistration</code>の表すフィルターの現在使用可能なサーブレット名のマッピングを取得します。
     * 
     * <p>許可されている場合、返された<code>Collection</code>への変更はこの<code>FilterRegistration</code>に影響してはなりません。
     *
     * @return この<code>FilterRegistration</code>によって表されるフィルターの現在利用可能なサーブレット名のマッピングの(空の可能性のある)<code>Collection</code>
     */
    public Collection<String> getServletNameMappings();

    /**
     * このFilterRegistrationによって表されるフィルターに対して、与えられたURLパターンとディスパッチャータイプを持つフィルターマッピングを追加します。
     *
     * <p>フィルターマッピングは追加された順番で一致します。
     * 
     * <p><tt>isMatchAfter</tt>パラメータの値に応じて、このFilterRegistrationが取得されたServletContextで<i>宣言された</i>
     * フィルターマッピングの後または前に、与えられたフィルターマッピングを設定します。
     *
     * <p>このメソッドが複数回呼び出された場合、後続の各呼び出しが前の呼び出しの効果に追加されます。
     *
     * @param dispatcherTypes フィルターマッピングのディスパッチャータイプ、
     * nullの場合はデフォルトとして <tt>DispatcherType.REQUEST</tt>が使用される
     * @param isMatchAfter 与えられたフィルターマッピングが宣言されたフィルターマッピングの後で一致する必要がある場合はtrue、
     * このFilterRegistrationが取得されたServletContextで宣言されたフィルターマッピングの前に一致しなければならない場合はfalse
     * @param urlPatterns フィルターマッピングのURLパターン
     *
     * @throws IllegalArgumentException <tt>urlPatterns</tt>がnullもしくは空
     * @throws IllegalStateException このFilterRegistrationが取得されたServletContextがすでに初期化完了していた場合
     */
    public void addMappingForUrlPatterns(
        EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter,
        String... urlPatterns);

    /**
     * この<code>FilterRegistration</code>の表すフィルターの現在使用可能なURLパターンマッピングを取得します。
     * 
     * <p>許可されている場合、返された<code>Collection</code>への変更はこの<code>FilterRegistration</code>に影響してはなりません。
     *
     * @return この<code>FilterRegistration</code>によって表されるフィルターの現在利用可能なURLパターンマッピングの(空の可能性のある)<code>Collection</code>
     */
    public Collection<String> getUrlPatternMappings();

    /**
     * {@link ServletContext}の<tt>addFilter</tt>メソッドの1つを使用して登録された
     * {@link Filter}を詳細に構成するためのインターフェース。
     */
    interface Dynamic extends FilterRegistration, Registration.Dynamic {
    }
}

