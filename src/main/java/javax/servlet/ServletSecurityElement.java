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
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;

/**
 * {@link ServletSecurity}アノテーションの値を表現するJavaクラスです。
 *
 * @since Servlet 3.0
 */
public class ServletSecurityElement extends HttpConstraintElement {

    private Collection<String> methodNames;
    private Collection<HttpMethodConstraintElement> methodConstraints;

    /**
     * デフォルトの<code>HttpConstraintElement</code>の値をデフォルトの制約要素として使用し、
     * HTTPメソッド固有の制約要素を使用しないでインスタンスを構築します。
     */
    public ServletSecurityElement() {
        methodConstraints = new HashSet<HttpMethodConstraintElement>();
        methodNames = Collections.emptySet();
    }

    /**
     * デフォルトの制約要素を使用し、
     * HTTPメソッド固有の制約要素を使用しないでインスタンスを構築します。
     *
     * @param constraint <tt>methodConstraints</tt>で表されるもの以外のすべてのHTTPメソッドに適用されるHttpConstraintElement
     */
    public ServletSecurityElement(HttpConstraintElement constraint) {
        super(constraint.getEmptyRoleSemantic(),
                constraint.getTransportGuarantee(),
                constraint.getRolesAllowed());
        methodConstraints = new HashSet<HttpMethodConstraintElement>();
        methodNames = Collections.emptySet();
    }

    /**
     * デフォルトの<code>HttpConstraintElement</code>の値をデフォルトの制約要素としたものとHTTPメソッド固有の制約要素のコレクションを使用してインスタンスを構築します。
     *
     * @param methodConstraints HTTPメソッド固有の制約要素のコレクション
     *
     * @throws IllegalArgumentException 重複したメソッド名が検出された
     */
    public ServletSecurityElement(
            Collection<HttpMethodConstraintElement> methodConstraints) {
        this.methodConstraints = (methodConstraints == null ?
            new HashSet<HttpMethodConstraintElement>() : methodConstraints);
        methodNames = checkMethodNames(this.methodConstraints);
    }

    /**
     * デフォルトの制約要素とHTTPメソッド固有の制約要素のコレクションを使用してインスタンスを構築します。
     *
     * @param constraint <tt>methodConstraints</tt>で表されるもの以外のすべてのHTTPメソッドに適用されるHttpConstraintElement
     * @param methodConstraints HTTPメソッド固有の制約要素のコレクション
     *
     * @throws IllegalArgumentException 重複したメソッド名が検出された
     */
    public ServletSecurityElement(HttpConstraintElement constraint,
            Collection<HttpMethodConstraintElement> methodConstraints) {
        super(constraint.getEmptyRoleSemantic(),
                constraint.getTransportGuarantee(),
                constraint.getRolesAllowed());
        this.methodConstraints = (methodConstraints == null ?
            new HashSet<HttpMethodConstraintElement>() : methodConstraints);
        methodNames = checkMethodNames(this.methodConstraints);
    }

    /**
     * {@link ServletSecurity}アノテーションの値からインスタンスを生成します。
     *
     * @param annotation アノテーションの値
     *
     * @throws IllegalArgumentException 重複したメソッド名が検出された
     */
    public ServletSecurityElement(ServletSecurity annotation) {
        super(annotation.value().value(),
                annotation.value().transportGuarantee(),
                annotation.value().rolesAllowed());
        this.methodConstraints = new HashSet<HttpMethodConstraintElement>();
        for (HttpMethodConstraint constraint :
                annotation.httpMethodConstraints()) {
            this.methodConstraints.add(
                new HttpMethodConstraintElement(
                    constraint.value(),
                    new HttpConstraintElement(constraint.emptyRoleSemantic(),
                        constraint.transportGuarantee(),
                        constraint.rolesAllowed())));
        }
        methodNames = checkMethodNames(this.methodConstraints);
    }

    /**
     * HTTPメソッド固有の制約要素の(空の可能性がある)コレクションを取得します。
     * 
     * <p>許可されている場合、返されたCollectionへの変更はこのServletSecurityElementに影響してはなりません。
     *
     * @return HttpMethodConstraintElementオブジェクトの(空の可能性がある)Collection
     */
    public Collection<HttpMethodConstraintElement> getHttpMethodConstraints() {
        return Collections.unmodifiableCollection(methodConstraints);
    }

    /**
     * HttpMethodConstraintsで指定されたHTTPメソッド名のSetを取得します。
     *
     * <p>許可されている場合、返されたCollectionへの変更はこのServletSecurityElementに影響してはなりません。
     *
     * @return メソッド名のStringのCollection
     */
    public Collection<String> getMethodNames() {
        return Collections.unmodifiableCollection(methodNames);
    }

    /**
     * methodConstraintsの中のメソッド名に重複がないことを確認します。
     *
     * @param methodConstraints
     *
     * @return メソッドの名前のSet
     *
     * @throws IllegalArgumentException 重複したメソッド名が検出された
     */
    private Collection<String> checkMethodNames(
            Collection<HttpMethodConstraintElement> methodConstraints) {
        Collection<String> methodNames = new HashSet<String>();
        for (HttpMethodConstraintElement methodConstraint :
                        methodConstraints) {
            String methodName = methodConstraint.getMethodName();
            if (!methodNames.add(methodName)) {
                throw new IllegalArgumentException(
                    "Duplicate HTTP method name: " + methodName);
            }
        }
        return methodNames;
    }
}
