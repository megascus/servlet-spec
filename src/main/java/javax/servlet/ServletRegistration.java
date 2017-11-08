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
 * {@link Servlet} に詳細な構成をするためのインターフェース。
 *
 * @since Servlet 3.0
 */
public interface ServletRegistration extends Registration {

    /**
     * このServletRegistrationによって表されるサーブレットに対して、与えられたURLパターンを指定してサーブレットマッピングを追加します。
     *
     * <p>指定されたURLパターンのいずれかがすでに別のサーブレットにマッピングされている場合、更新は実行されません。
     *
     * <p>このメソッドが複数回呼び出された場合、後続の各呼び出しが前の呼び出しの効果に追加されます。
     * 
     * <p>返されるSetはServletRegistrationオブジェクトによって追跡されないため、返されたSetへの変更はServletRegistrationオブジェクトに反映されず、その逆もそうです。</p>
     *
     * @param urlPatterns サーブレットマッピングのURLパターン
     *
     * @return 別のサーブレットにすでにマッピングされているURLパターンの(空の可能性がある)Set
     *
     * @throws IllegalArgumentException <tt>urlPatterns</tt>がnull
     * or empty
     * @throws IllegalStateException このServletRegistrationが取得されたServletContextがすでに初期化完了していた場合
     */
    public Set<String> addMapping(String... urlPatterns);

    /**
     * このServletRegistrationによって表されるサーブレットの現在使用可能なマッピングを取得します。
     *
     * <p>許可されている場合、返された<code>Collection</code>への変更はこの<code>ServletRegistration</code>に影響してはなりません。
     *
     * @return この<code>ServletRegistration</code>によって表されるサーブレットの現在利用可能なマッピングの(空の可能性のある)<code>Collection</code>
     */
    public Collection<String> getMappings();

    /**
     * この<code>ServletRegistration</code>によって表されるサーブレットのrunAsロールの名前を取得します。
     * 
     * @return runAsロールの名前、サーブレットが呼び出し元として実行するように設定されている場合はnull
     */
    public String getRunAsRole();

    /**
     * {@link ServletContext}の<tt>addServlet</tt>メソッドの1つを使用して登録された
     * {@link Servlet}を詳細に構成するためのインターフェース。
     */
    interface Dynamic extends ServletRegistration, Registration.Dynamic {

        /**
         * この動的(Dynamic:動的)なServletRegistrationによって表されるServletの<code>loadOnStartup</code>優先度を設定します。
         *
         * <p>ゼロ以上の<tt>loadOnStartup</tt>の値はサーブレットの初期化優先度をコンテナに示します。
         * この場合、コンテナは、ServletContextの初期化フェーズ{@link ServletContextListener#contextInitialized}メソッドで{@link ServletContextListener#contextInitialized}用にコンフィグレーションされたServletContextListenerオブジェクトのすべてを呼び出した後）でServletをインスタンス化して初期化する必要があります。
         * この場合、コンテナはサーブレットコンテキストの初期化フェーズの間、すなわち、そのServletContextに設定されたすべてのServletContextListenerオブジェクトの{@link ServletContextListener#contextInitialized}メソッドを呼び出した後に、このサーブレットをインスタンス化し、初期化する必要があります。
         *
         * <p><tt>loadOnStartup</tt>が負の数の場合、コンテナはサーブレットを遅延させてインスタンス化し、初期化することができます。
         *
         * <p><tt>loadOnStartup</tt>のデフォルト値は<code>-1</code>です。
         *
         * <p>このメソッドの呼び出しは前の設定を上書きします。
         *
         * @param loadOnStartup サーブレットの初期化優先度
         *
         * @throws IllegalStateException このServletRegistrationが取得されたServletContextがすでに初期化完了していた場合
         */
        public void setLoadOnStartup(int loadOnStartup);

        /**
         * この<code>ServletRegistration</code>のために定義されたマッピングに適用される{@link ServletSecurityElement}を設定します。
         *
         * <p>このメソッドはこの<code>ServletRegistration</code>が取得された<code>ServletContext</code>が初期化完了された時点までに追加されたすべてのマッピングに適用されます。
         * 
         * <p>もし、このServletRegistrationのURLパターンがポータブルなデプロイメントディスクリプタで設定された<code>security-constraint</code>内のターゲットとして正確に存在する場合、
         * このメソッドはそのパターンの<code>security-constraint</code>は変更せず、そのパターンを戻り値の中に含めるでしょう。
         * 
         * <p>もし、このServletRegistrationのURLパターンが{@link javax.servlet.annotation.ServletSecurity}アノテーションやこのメソッドの前回の呼び出し内のターゲットとして正確に存在する場合、
         * このメソッドはそのパターンのセキュリティ制約を置き換えるでしょう。
         * 
         * <p>もし、このServletRegistrationのURLパターンが{@link javax.servlet.annotation.ServletSecurity}アノテーションやこのメソッドの前回の呼び出し内のターゲットとしても、
         * ポータブルなデプロイメントディスクリプタで設定された<code>security-constraint</code>内のターゲットとしても存在しない場合、
         * このメソッドは引数の<code>ServletSecurityElement</code>のパターンのセキュリティ制約を設定します。
         *
         * <p>返されたSetは{@code Dynamic}オブジェクトから独立しているため、返されたSetへの変更は{@code Dynamic}オブジェクトに影響を与えませんし、逆もそうです。</p>
         * 
         * @param constraint このServletRegistrationにマッピングされたパターンに適用される{@link ServletSecurityElement}
         * 
         * @return ポータブルなデプロイメントディスクリプタで設定された<code>security-constraint</code>内のターゲットとして正確に存在するURLパターンの(空の可能性のある)Set、
         * このメソッドは、返されたSetに含まれるパターンには影響を与えない
         * 
         * @throws IllegalArgumentException <tt>constraint</tt>がnull
         * 
         * @throws IllegalStateException このServletRegistrationが取得されたServletContextがすでに初期化完了していた場合
         */
        public Set<String> setServletSecurity(ServletSecurityElement constraint);

        /**
         * この<code>ServletRegistration</code>のために定義されたマッピングに適用される{@link MultipartConfigElement}を設定します。
         * このメソッドが複数回呼び出された場合、連続する各呼び出しは前の呼び出しの効果よりも優先されます。
         *
         * @param multipartConfig ServletRegistrationにマッピングされたパターンに適用される{@link MultipartConfigElement}
         *
         * @throws IllegalArgumentException <tt>multipartConfig</tt>がnull
         *
         * @throws IllegalStateException このServletRegistrationが取得されたServletContextがすでに初期化完了していた場合
         */
        public void setMultipartConfig(
            MultipartConfigElement multipartConfig);

        /**
         * この<code>ServletRegistration</code>の<code>runAs</code>ロールの名前を設定します。
         *
         * @param roleName <code>runAs</code>ロールの名前
         *
         * @throws IllegalArgumentException <tt>roleName</tt>がnull
         *
         * @throws IllegalStateException このServletRegistrationが取得されたServletContextがすでに初期化完了していた場合
         */
        public void setRunAsRole(String roleName);

    }

}

