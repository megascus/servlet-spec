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
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity.EmptyRoleSemantic;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;

/**
 * {@link HttpConstraint}アノテーションの値を表現するJavaクラスです。
 *
 * @since Servlet 3.0
 */
public class HttpConstraintElement {

    private EmptyRoleSemantic emptyRoleSemantic;
    private TransportGuarantee transportGuarantee;
    private String[] rolesAllowed;

    /**
     * デフォルトのHTTP制約属性を生成します。
     */
    public HttpConstraintElement() {
        this(EmptyRoleSemantic.PERMIT);
    }

    /**
     * <tt>EmptyRoleSemantic.DENY</tt>を確立するための簡便なコンストラクタです。
     *
     * @param semantic EmptyRoleSemantic.DENY である必要があるセマンティック
     */
    public HttpConstraintElement(EmptyRoleSemantic semantic) {
        this(semantic, TransportGuarantee.NONE, new String[0]);
    }

    /**
     * 空でないgetRolesAllowedと/もしくは<tt>TransportGuarantee.CONFIDENTIAL</tt>を確立するためのコンストラクタです。
     *
     * @param guarantee <tt>TransportGuarantee.NONE</tt>もしくは<tt>TransportGuarantee.CONFIDENTIAL</tt>
     * @param roleNames アクセスが許容されるロールの名前
     */
    public HttpConstraintElement(TransportGuarantee guarantee,
            String... roleNames) {
        this(EmptyRoleSemantic.PERMIT, guarantee, roleNames);
    }

    /**
     * getEmptyRoleSemanticとgetRolesAllowed、getTransportGuaranteeのすべてを確立するためのコンストラクタです。
     *
     * @param semantic <tt>EmptyRoleSemantic.DENY</tt>もしくは<tt>EmptyRoleSemantic.PERMIT</tt>
     * @param guarantee <tt>TransportGuarantee.NONE</tt>もしくは<tt>TransportGuarantee.CONFIDENTIAL</tt>
     * @param roleNames アクセスが許可されるロールの名前、そのセマンティックが存在しない場合は<tt>EmptyRoleSemantic.DENY</tt>
     */
    public HttpConstraintElement(EmptyRoleSemantic semantic,
            TransportGuarantee guarantee, String... roleNames) {
        if (semantic == EmptyRoleSemantic.DENY && roleNames.length > 0) {
            throw new IllegalArgumentException(
                "Deny semantic with rolesAllowed");
        }
        this.emptyRoleSemantic = semantic;
        this.transportGuarantee = guarantee;
        this.rolesAllowed = copyStrings(roleNames);
    }

    /**
     * デフォルトの認可セマンティックを取得します。
     * 
     * <p>この値は<code>getRolesAllowed</code>が空でない配列を返すときは意味がないので、
     * <code>getRolesAllowed</code>に空でない配列が指定されているときは指定しないでください。
     *
     * @return <code>getRolesAllowed</code>が空の(長さがゼロの)配列を返すときに適用される{@link EmptyRoleSemantic}
     */
    public EmptyRoleSemantic getEmptyRoleSemantic() {
        return this.emptyRoleSemantic;
    }

    /**
     * トランスポート層での接続で満たす必要のあるデータ保護要件(SSL/TLSが必要かどうか)を取得します。
     *
     * @return 接続で提供する必要のあるデータ保護を示す{@link TransportGuarantee}
     */
    public TransportGuarantee getTransportGuarantee() {
        return this.transportGuarantee;
    }

    /**
     * 権限のあるロールの名前を取得します。
     *
     * <p>getRolesAllowedに現れる重複したロール名に意味はなく、破棄されることがあります。
     * 文字列<tt>"*"</tt>はロール名として特別な意味を持ちません。(getRolesAllowedで存在する必要があります)
     *
     * @return ロール名の(空の可能性がある)配列。
     * 配列が空の場合、{@link #getEmptyRoleSemantic}によって返される値に依存することを意味します。
     * その値が<tt>DENY</tt>で、<code>getRolesAllowed</code>が空の配列を返す場合、
     * 認証状況と身元とは無関係にアクセスが拒否されます。
     * 逆に、その値が<code>PERMIT</code>の場合、認証状況と身元とは無関係にアクセスが許可されることを示します。
     * 配列に1つ以上のロールの名前が含まれている場合、
     * ({@link #getEmptyRoleSemantic}の値とは独立して)名前の付けられたロールの少なくとも1つのメンバーシップに依存してアクセスすることを示します。
     */
    public String[] getRolesAllowed() {
        return copyStrings(this.rolesAllowed);
    }

    private String[] copyStrings(String[] strings) {
        String[] arr = null;
        if (strings != null) {
            int len = strings.length;
            arr = new String[len];
            if (len > 0) {
                System.arraycopy(strings, 0, arr, 0, len);
            }
        }

        return ((arr != null) ? arr : new String[0]);
    }
}
