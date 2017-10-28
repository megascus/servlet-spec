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
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * このアノテーションはサーブレットの実装クラスで使用され、サーブレットコンテナによってHTTPプロトコルのメッセージで行われるセキュリティ制約を指定します。 
 * サーブレットコンテナはアノテーションの付いたクラスにマップされたサーブレットにマップされたURLパターンにこれらの制約を適用します。
 *
 * @since Servlet 3.0
 */

@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServletSecurity {

    /**
     * Defines the access semantic to be applied to an empty rolesAllowed array.
     */
    enum EmptyRoleSemantic {
        /**
         * access is to be permitted independent of authentication state and
         * identity.
         */
        PERMIT,
        /**
         * access is to be denied independent of authentication state and
         * identity.
         */
        DENY
    }

    /**
     * Defines the data protection requirements that must be satisfied by
     * the transport
     */
    enum TransportGuarantee {
        /**
         * no protection of user data must be performed by the transport.
         */
        NONE,
        /**
         * All user data must be encrypted by the transport (typically
         * using SSL/TLS).
         */
        CONFIDENTIAL
    }

    /**
     * <tt>httpMethodConstraints</tt>によって返された配列で<b>表されていない</b>
     * すべてのHTTPメソッドに適用される保護を定義する{@link HttpConstraint}を取得します。
     *
     * @return <code>HttpConstraint</code>オブジェクト
     */
    HttpConstraint value() default @HttpConstraint;

    /**
     * HTTPメソッド固有の制約を取得します。
     * 各{@link HttpMethodConstraint}はHTTPプロトコルメソッドの名前を付け、それに適用される保護を定義します。
     *
     * @return 1つのHTTPプロトコルメソッドに適用される保護を定義する{@link HttpMethodConstraint}要素の配列。
     * 任意のHTTPメソッド名に対して、返される配列内に対応する要素は最大でも1つである必要があります。
     * 返される配列の長さがゼロの場合、HTTPメソッド固有の制約が定義されていないことを示します。
     */

  
    HttpMethodConstraint[] httpMethodConstraints() default {};
}
