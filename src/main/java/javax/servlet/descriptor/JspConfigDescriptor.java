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

package javax.servlet.descriptor;

import java.util.Collection;

/**
 * このインターフェースはウェブアプリケーションの<code>&lt;jsp-config&gt;</code>関連の設定へのアクセス方法を提供します。
 *
 * <p>設定はウェブアプリケーションの<code>web.xml</code>および<code>web-fragment.xml</code>から集められます。
 * 
 * <p>訳注：このインターフェース及びにこのインターフェースから返されるすべてのオブジェクトは読み取り専用です。いかなる変更を加えてもウェブアプリケーションの設定は変更できません。
 *
 * @since Servlet 3.0
 */
public interface JspConfigDescriptor {

    /**
     * この<code>JspConfigDescriptor</code>によって表される<code>&lt;jsp-config&gt;</code>の配下の<code>&lt;taglib&gt;</code>を取得します。
     *
     * <p>返された<code>Collection</code>へのいかなる変更もこの<code>JspConfigDescriptor</code>には影響してはいけません。
     *
     * @return この<code>JspConfigDescriptor</code>によって表される<code>&lt;jsp-config&gt;</code>の配下の<code>&lt;taglib&gt;</code>の(空の可能性がある)<code>Collection</code>
     */
    public Collection<TaglibDescriptor> getTaglibs();

    /**
     * この<code>JspConfigDescriptor</code>によって表される<code>&lt;jsp-config&gt;</code>の配下の<code>&lt;jsp-property-group&gt;</code>を取得します。
     *
     * <p>返された<code>Collection</code>へのいかなる変更もこの<code>JspConfigDescriptor</code>には影響してはいけません。
     *
     * @return この<code>JspConfigDescriptor</code>によって表される<code>&lt;jsp-config&gt;</code>の配下の<code>&lt;jsp-property-group&gt;</code>の(空の可能性がある)<code>Collection</code>
     */
    public Collection<JspPropertyGroupDescriptor> getJspPropertyGroups();
}
