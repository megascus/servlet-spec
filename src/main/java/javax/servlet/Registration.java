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

import java.util.Map;
import java.util.Set;

/**
 * {@link Servlet}や{@link Filter} に詳細な構成をするためのインターフェース。
 *
 * <p>{@link #getClassName}メソッドがnullを返す登録オブジェクトは<i>準備段階</i>とみなされます。
 * サーブレットとフィルタのコンテナ実装固有のクラスは、それぞれ<tt>servlet-class</tt>または<tt>filter-class</tt>要素なしで宣言され、
 * 事前登録(Registration:登録)オブジェクトとして表されます。
 * 事前登録は{@link ServletContext}の<tt>addServlet</tt>メソッドまたは<tt>addFilter</tt>メソッドの1つを呼び出し、
 * サポートするサーブレットまたはフィルターの実装クラス名、クラスオブジェクトかインスタンス、
 * それらと一緒にサーブレット名またはフィルター名（{@link #getName}を使用して取得される)を渡すことによって完了する必要があります。
 * ほとんどの場合、事前登録はコンテナ提供の適切な{@link ServletContainerInitializer}によって完了されます。
 *
 * @since Servlet 3.0
 */
public interface Registration {

    /**
     * このRegistrationによって表されるサーブレットまたはフィルターの名前を取得します。
     *
     * @return このRegistrationによって表されるサーブレットまたはフィルタの名前
     */
    public String getName();

    /**
     * このRegistrationによって表されるサーブレットまたはフィルターの完全修飾クラス名(FQCN)を取得します。
     *
     * @return このRegistrationによって表されるサーブレットまたはフィルタの完全修飾クラス名、このRegistrationが事前登録状態である場合はnull
     */
    public String getClassName();

    /**
     * 指定された名前と値を持つ初期化パラメーターをこのRegistrationで表されるサーブレットまたはフィルターに設定します。
     *
     * @param name 初期化パラメーター名
     * @param value 初期化パラメーターの値
     *
     * @return 更新が成功した場合、つまり指定された名前を持つ初期化パラメータがこのRegistrationで表されるサーブレットまたはフィルターに存在しない場合はtrue、そうでない場合はfalse
     *
     * @throws IllegalStateException この登録が取得されたServletContextがすでに初期化されている場合
     * @throws IllegalArgumentException 指定された名前または値が<tt>null</tt>の場合
     */ 
    public boolean setInitParameter(String name, String value);

    /**
     * このRegistrationオブジェクトによって表されるサーブレットまたはフィルターを初期化するために使用される指定された名前を持つ初期化パラメータの値を取得します。
     *
     * @param name 値が要求されている初期化パラメータの名前
     *
     * @return 指定された名前を持つ初期化パラメータの値、指定された名前を持つ初期化パラメータが存在しない場合は<tt>null</tt>
     */ 
    public String getInitParameter(String name);

    /**
     * 指定された初期化パラメータをこのRegistrationによって表されるサーブレットまたはフィルターに設定します。
     * 
     * <p>初期化パラメータの指定されたMapは、 <i>値毎に</i>処理されます。
     * つまり、Mapに含まれる各初期化パラメータについて、このメソッドは{@link #setInitParameter(String,String)}を呼び出します。
     * このメソッドが指定されたMapのいずれかの初期化パラメータに対してfalseを返すと、更新は実行されず、このメソッドの戻り値として返されます。
     * 同様に、Mapに<tt>null</tt>の名前または値を持つ初期化パラメータが含まれている場合、更新は実行されず、IllegalArgumentExceptionが投げられます。
     * 
     * <p>返されたSetは{@code Registration}オブジェクトによって追跡されていないため、返されたSetへの変更は{@code Registration}オブジェクトに反映されず、その逆もそうです。</p>
     *
     * @param initParameters 初期化パラメーター
     *
     * @return コンフリクトした初期化パラメーターの(空の可能性がある)Set
     *
     * @throws IllegalStateException このRegistratioが取得されたServletContextがすでに初期化されている場合
     * @throws IllegalArgumentException 指定されたMapに<tt>null</tt>の名前または値を持つ初期化パラメータを含まれていた場合
     */ 
    public Set<String> setInitParameters(Map<String, String> initParameters);

    /**
     * このRegistrationオブジェクトによって表されるサーブレットまたはフィルターを初期化するために使用される
     * 現在使用可能な初期化パラメータを含む不変の(空の可能性がある)Mapを取得します。
     *
     * @return このRegistrationオブジェクトによって表されるサーブレットまたはフィルターを初期化するために使用される
     * 現在利用可能な初期化パラメータを含むMap
     */ 
    public Map<String, String> getInitParameters();

    /**
     * {@link ServletContext}の<tt>addServlet</tt>メソッドまたは<tt>addFilter</tt>メソッドの1つを使用して登録された
     * {@link Servlet}または{@link Filter}を詳細に構成するためのインターフェース。
     */
    interface Dynamic extends Registration {

        /**
         * この動的(Dynamic:動的)なRegistrationによって表されるサーブレットまたはフィルターを、
         * 非同期操作をサポートするかどうかを設定します。
         * 
         * <p>デフォルトでは、サーブレットとフィルターは非同期操作をサポートしていません。
         * 
         * <p>このメソッドを呼び出すと、以前の設定が上書きされます。
         *
         * @param isAsyncSupported この動的なRegistrationによって表されるサーブレットまたはフィルターが非同期操作をサポートする場合はtrue、そうでない場合はfalse
         *
         * @throws IllegalStateException この動的なRegistrationが取得されたServletContextがすでに初期化完了していた場合
         */
        public void setAsyncSupported(boolean isAsyncSupported);
    }
}

