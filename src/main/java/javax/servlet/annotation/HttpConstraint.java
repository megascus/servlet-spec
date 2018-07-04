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

package javax.servlet.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.servlet.annotation.ServletSecurity.EmptyRoleSemantic;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;

/**
 * このアノテーションは{@link ServletSecurity}アノテーション内にて<b>存在しない</b>{@link HttpMethodConstraint}属性に対応する
 * すべてのHTTPプロトコルのメソッドに適用されるセキュリティ制約を表すために{@link ServletSecurity}アノテーション内で使用されます。
 * 
 * <code>@HttpConstraint</code>がすべてデフォルトの値を生じさせるものを返し最低でも一つの{@link HttpMethodConstraint}がすべてのデフォルト値とは違う値を返す特殊な場合では、
 * <code>@HttpConstraint</code>はセキュリティ制約が適用されるHTTPプロトコルのメソッドのいずれにも適用されるセキュリティ制約がないことを表します。
 * この例外はこのような<code>@HttpConstraint</code>の仕様化されていない使用方法を行える可能性が
 * そのようなメソッドに対する保護されていないアクセスを明示的に確立する制約をもたらさない事を保証するために作られました。　
 * (制約によってカバーされるのとは異ならないだろうと仮定すれば)
 *
 * @since Servlet 3.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpConstraint {

    /**
     * デフォルトの認可セマンティックです。
     * 
     * この値は<code>rolesAllowed</code>が空でない配列を返すときは意味がないので、
     * <tt>rolesAllowed</tt>に空でない配列が指定されているときは指定しないでください。
     *
     * @return <code>rolesAllowed</code>が空の(長さがゼロの)配列を返すときに適用される{@link EmptyRoleSemantic}
     */
    EmptyRoleSemantic value() default EmptyRoleSemantic.PERMIT;

    /**
     * リクエストが到着する接続によって満たされなければならないデータ保護要件です。(つまり、SSL/TLSが必要かどうか)
     * 
     * @return 接続によって提供されなければならないデータ保護方法を示す{@link TransportGuarantee}
     */
    TransportGuarantee transportGuarantee() default TransportGuarantee.NONE;

    /**
     * 許可されたロールの名前です。
     * 
     * rolesAllowedに現れる重複したロール名に意味はなく、アノテーションの実行時処理中に破棄されることがあります。
     * 文字列<tt>"*"</tt>はロール名として特別な意味を持ちません。(rolesAllowedで存在する必要があります)
     *
     * @return 0個以上のロール名の配列。
     * 配列に要素がゼロの場合、<code>value</code>メソッドによって返される<code>EmptyRoleSemantic</code>に依存することを意味します。
     * <code>value</code>が<tt>DENY</tt>を返し、<code>rolesAllowed</code>が長さゼロの配列を返す場合、
     * 認証状況と身元とは無関係にアクセスが拒否されます。
     * 逆に、<code>value</code>が<code>PERMIT</code>返す場合、認証状況と身元とは無関係にアクセスが許可されることを示します。
     * 配列に1つ以上のロールの名前が含まれている場合、
     * (<code>value</code>メソッドによって返された<code>EmptyRoleSemantic</code>とは独立して)名前の付けられたロールの少なくとも1つのメンバーシップに依存してアクセスすることを示します。
     */
    String[] rolesAllowed() default {};
}
