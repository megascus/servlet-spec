/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2017 Oracle and/or its affiliates. All rights reserved.
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

package javax.servlet.http;

/**
 * <p>現在の{@link HttpServlet} が {@link HttpServletRequest}から呼び出された方法を実行時に見つけることを可能にします。
 * いずれのメソッド呼びだしも呼び出し元をブロックしてはいけません。 
 * 実装はスレッドセーフでなければなりません。
 * インスタンスは不変で{@link HttpServletRequest#getHttpServletMapping}から返されます。</p>
 *
 * <p>以下はマッピングの様々な組み合わせのうちのいくつかの実例です。 以下のサーブレット宣言を考えてみましょう。</p>
 *
 * <pre><code>
 * &lt;servlet&gt;
 *     &lt;servlet-name&gt;MyServlet&lt;/servlet-name&gt;
 *     &lt;servlet-class&gt;MyServlet&lt;/servlet-class&gt;
 * &lt;/servlet&gt;
 * &lt;servlet-mapping&gt;
 *     &lt;servlet-name&gt;MyServlet&lt;/servlet-name&gt;
 *     &lt;url-pattern&gt;/MyServlet&lt;/url-pattern&gt;
 *     &lt;url-pattern&gt;""&lt;/url-pattern&gt;
 *     &lt;url-pattern&gt;*.extension&lt;/url-pattern&gt;
 *     &lt;url-pattern&gt;/path/*&lt;/url-pattern&gt;
 * &lt;/servlet-mapping&gt;
 * </code></pre>
 *
 * <p>さまざまな待ち受けURIパスの値におけるプロパティの期待値はこの表に示すとおりです。 {@code servletName}列は値が常に{@code MyServlet}のため省略されています。</p>
 * 
 * <table border="1">
 *   <caption>さまざまなURIパスにおけるプロパティの期待値</caption>
 *   <tr>
 *     <th>URI Path (in quotes)</th>
 *     <th>matchValue</th>
 *     <th>pattern</th>
 *     <th>{@link MappingMatch}</th>
 *   </tr>
 *   <tr>
 *     <td>""</td>
 *     <td>""</td>
 *     <td>""</td>
 *     <td>CONTEXT_ROOT</td>
 *   </tr>
 *   <tr>
 *     <td>"/index.html"</td>
 *     <td>""</td>
 *     <td>/</td>
 *     <td>DEFAULT</td>
 *   </tr>
 *   <tr>
 *     <td>"/MyServlet"</td>
 *     <td>MyServlet</td>
 *     <td>/MyServlet</td>
 *     <td>EXACT</td>
 *   </tr>
 *   <tr>
 *     <td>"/foo.extension"</td>
 *     <td>foo</td>
 *     <td>*.extension</td>
 *     <td>EXTENSION</td>
 *   </tr>
 *   <tr>
 *     <td>"/path/foo"</td>
 *     <td>foo</td>
 *     <td>/path/*</td>
 *     <td>PATH</td>
 *   </tr>  
 *   
 * </table>
 * 
 * @since 4.0
 */
public interface HttpServletMapping {


    
    /**
     * <p>このリクエストの原因となったURIパスの一部分を返します。</p>
     * 
     * <p>{@link getMappingMatch}の値が{@code CONTEXT_ROOT}または{@code DEFAULT}の場合、このメソッドは空の文字列を返さなければいけません。
     * {@link getMappingMatch}の値が{@code EXACT}の場合、このメソッドはスラッシュが省略されたサーブレットに一致するパスの一部分を返さなければいけません。
     * {@link getMappingMatch}の値が{@code EXTENSION}または{@code PATH}の場合、このメソッドは '*'に一致する値を返す必要があります。
     * 例についてはクラスのjavadocを参照してください。</p>
     * 
     * @return 一致した値
     * 
     * @since 4.0
     */
    public String getMatchValue();

    /**
     * <p>このマッピングの{@code url-pattern}の文字列表現を返します。</p>
     * 
     * <p>{@link getMappingMatch}の値が{@code CONTEXT_ROOT}または{@code DEFAULT}の場合このメソッドは空の文字列を返さなければいけません。
     * {@link getMappingMatch}の値が{@code EXTENSION}の場合、このメソッドはスラッシュを付けずにパターンを返さなければいけません。
     * それ以外の場合は、このメソッドはディスクリプターまたはJavaコンフィギュレーションで指定されているとおりのパターンを返さなければいけません。</p>
     * 
     * @return このマッピングの{@code url-pattern}の文字列表現
     * 
     * @since 4.0
     */
    public String getPattern();
    
    /**
     * <p>このマッピングの{@code servlet-name}の文字列表現を返します。
     * 
     * <p>レスポンスを提供するサーブレットがデフォルトサーブレットである場合、このメソッドからの戻り値はコンテナ固有のデフォルトサーブレットの名前です。</p>
     * 
     * @return このマッピングの{@code servlet-name}の文字列表現
     * 
     * @since 4.0
     */
    public String getServletName();

    /**
     * <p>このインスタンスの{@link MappingMatch} を返します。</p> 
     * 
     * @return このインスタンスの{@link MappingMatch} 
     * 
     * @since 4.0
     */
    public MappingMatch getMappingMatch();
    
}
