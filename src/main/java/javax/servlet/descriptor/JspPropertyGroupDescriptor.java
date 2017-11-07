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
 * このインターフェースはウェブアプリケーションの<code>&lt;jsp-property-group&gt;</code>関連の設定へのアクセス方法を提供します。
 *
 * <p>設定はウェブアプリケーションの<code>web.xml</code>および<code>web-fragment.xml</code>から集められます。
 *
 * @since Servlet 3.0
 */
public interface JspPropertyGroupDescriptor {
    
    /**
     * この<code>JspPropertyGroupDescriptor</code>によって表されるJSPプロパティグループのURLパターンを取得します。
     *
     * <p>返された<code>Collection</code>へのいかなる変更もこの<code>JspPropertyGroupDescriptor</code>には影響してはいけません。
     *
     * @return この<code>JspPropertyGroupDescriptor</code>によって表されるJSPプロパティグループのURLパターンの(空の可能性がある)<code>Collection</code>
     */
    public Collection<String> getUrlPatterns();

    /**
     * この<code>JspPropertyGroupDescriptor</code>によって表されるJSPプロパティグループにマッピングされたJSPページでExpression Language(EL)式を使用可能にするかどうかを指定する<code>el-ignored</code>の値を取得します。
     *
     * @return <code>el-ignored</code>の値、指定されてない場合はnull
     */
    public String getElIgnored();

    /**
     * この<code>JspPropertyGroupDescriptor</code>によって表されるJSPプロパティグループにマッピングされたJSPページのデフォルトページエンコーディングを指定する<code>page-encoding</code>の値を取得します。
     *
     * @return <code>page-encoding</code>の値、指定されてない場合はnull
     */
    public String getPageEncoding();

    /**
     * この<code>JspPropertyGroupDescriptor</code>によって表されるJSPプロパティグループにマッピングされたJSPページでスクリプトを有効にするかどうかを指定する<code>scripting-invalid</code>の値を取得します。
     *
     * @return <code>scripting-invalid</code>の値、指定されてない場合はnull
     */
    public String getScriptingInvalid();

    /**
     * この<code>JspPropertyGroupDescriptor</code>によって表されるJSPプロパティグループにマッピングされたJSPページをJSPドキュメント(XML構文)として扱うかどうかを指定する<code>is-xml</code>の値を取得します。
     *
     * @return <code>is-xml</code>の値、指定されてない場合はnull
     */
    public String getIsXml();

    /**
     * この<code>JspPropertyGroupDescriptor</code>によって表されるJSPプロパティグループの<code>include-prelude</code>の値を取得します。
     * 
     * <p>訳注：ファイルのパスを指定することでそのファイルの内容をJSPの先頭にインクルードすることができます。
     *
     * <p>返された<code>Collection</code>へのいかなる変更もこの<code>JspPropertyGroupDescriptor</code>には影響してはいけません。
     *
     * @return この<code>JspPropertyGroupDescriptor</code>によって表されるJSPプロパティグループの<code>include-prelude</code>の(空の可能性がある)<code>Collection</code>
     */
    public Collection<String> getIncludePreludes();

    /**
     * この<code>JspPropertyGroupDescriptor</code>によって表されるJSPプロパティグループの<code>include-coda</code>の値を取得します。
     * 
     * <p>訳注：ファイルのパスを指定することでそのファイルの内容をJSPの最後にインクルードすることができます。
     *
     * <p>返された<code>Collection</code>へのいかなる変更もこの<code>JspPropertyGroupDescriptor</code>には影響してはいけません。
     *
     * @return この<code>JspPropertyGroupDescriptor</code>によって表されるJSPプロパティグループの<code>include-coda</code>の(空の可能性がある)<code>Collection</code>
     */
    public Collection<String> getIncludeCodas();

    /**
     * この<code>JspPropertyGroupDescriptor</code>によって表されるJSPプロパティグループにマッピングされたJSPページで通常Expression Language(EL)式で予約されている
     * <code>&quot;#{}&quot;</code>という文字列が文字列リテラルとして現れた時に変換エラーを発生させるかどうかを指定する<code>deferred-syntax-allowed-as-literal</code>の値を取得します。
     *
     * @return <code>deferred-syntax-allowed-as-literal</code>の値、指定されてない場合はnull
     */
    public String getDeferredSyntaxAllowedAsLiteral();

    /**
     * この<code>JspPropertyGroupDescriptor</code>によって表されるJSPプロパティグループにマッピングされたJSPページの
     * レスポンスの出力から空白のみを含むテンプレートテキストを除去するべきであるかどうかを指定する<code>trim-directive-whitespaces</code>の値を取得します。
     *
     * @return <code>trim-directive-whitespaces</code>の値、指定されてない場合はnull
     */
    public String getTrimDirectiveWhitespaces();

    /**
     * この<code>JspPropertyGroupDescriptor</code>によって表されるJSPプロパティグループにマッピングされたJSPページのデフォルトのコンテンツタイプを指定する<code>default-content-type</code>の値を取得します。
     *
     * @return <code>default-content-type</code>の値、指定されてない場合はnull
     */
    public String getDefaultContentType();

    /**
     * この<code>JspPropertyGroupDescriptor</code>によって表されるJSPプロパティグループにマッピングされたJSPページのレスポンスバッファのデフォルトサイズを指定する<code>buffer</code>の値を取得します。
     *
     * @return <code>buffer</code>の値、指定されてない場合はnull
     */
    public String getBuffer();

    /**
     * この<code>JspPropertyGroupDescriptor</code>によって表されるJSPプロパティグループにマッピングされた
     * 宣言されていない名前空間のタグが使用されている場合にJSPページの変換時にエラーが発生するかどうかを指定する<code>error-on-undeclared-namespace</code>の値を取得します。
     *
     * @return <code>error-on-undeclared-namespace</code>の値、指定されてない場合はnull
     */
    public String getErrorOnUndeclaredNamespace();
}
