/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2017 Oracle and/or its affiliates. All rights reserved.
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

import java.io.*;
import java.util.*;

/**
 * <p>このクラスはPOSTリクエストの<code>multipart/form-data</code>内で受け取られたパートまたはフォームのアイテムを表します。
 * 
 * @since Servlet 3.0
 */
public interface Part {

    /**
     * パートのコンテンツを <tt>InputStream</tt> として取得します。
     * 
     * @return パートのコンテンツの <tt>InputStream</tt> 
     * @throws IOException コンテンツを<tt>InputStream</tt> として取得する際にエラーが発生した場合
     */
    public InputStream getInputStream() throws IOException;

    /**
     * このパートのコンテンツタイプを取得します。
     *
     * @return このパートのコンテンツタイプ
     */
    public String getContentType();

    /**
     * パートの名前を取得します。
     *
     * @return パートの名前の <tt>String</tt>
     */
    public String getName();

    /**
     * クライアントから示されたファイルの名前を取得します。
     *
     * @return サブミットされたファイル名
     *
     * @since Servlet 3.1
     */
    public String getSubmittedFileName();

    /**
     * このファイルのサイズを返します。
     *
     * @return このパートのバイト長を示す<code>long</code>
     */
    public long getSize();

    /**
     * このアップロードされたアイテムをディスクに書き込むための便利メソッドです。
     * 
     * <p>このメソッドは同じパートに対して複数回呼び出された場合の成功は保証されません。
     * これにより特定の実装では、例えば可能であれば元のすべてのデータをコピーするのではなくファイルの名前変更を使用することでパフォーマンス上のメリットが得られます。
     *
     * @param fileName アップロードされたパーツを格納する必要のある場所。
     * 値はファイル名もしくはパスのどちらかでよいです。
     * ファイルシステム内のファイルの実際の位置は、 {@link javax.servlet.MultipartConfigElement#getLocation()}からの相対パスです。 
     * 絶対パスは指定されたとおりに使用され、 <code>getLocation()</code>からの相対パスです。(訳注：JavaDocが間違っている？相対パスではありませんというのが正しそうな気がする)
     * 
     *  注意：これはシステムに依存する文字列であり、URI表記はすべてのシステムで受け入れられなくてもよいです。
     * 移植性のためにはこの文字列はFileもしくはPath APIを使用して生成する必要があります。
     *
     * @throws IOException エラーが起きた場合
     */
    public void write(String fileName) throws IOException;

    /**
     * 関連する一時ファイルなど、ストレージ上に存在するファイルアイテムを削除します。
     *
     * @throws IOException エラーが起きた場合
     */
    public void delete() throws IOException;

    /**
     * MIMEヘッダーから指定された値を<code>String</code>として返します。
     * パートが指定された名前をヘッダーに持たない場合、このメソッドは<code>null</code>を返します。
     * パートが同じ名前で重複したヘッダーを持っていた場合、このメソッドは最初のヘッダーを返します。
     * ヘッダー名は大文字と小文字を区別しません。
     * 任意のリクエストのヘッダーでこのメソッドを使用できます。
     *
     * @param name		ヘッダー名を指定する <code>String</code>
     *
     * @return			要求されたヘッダーの値を含む<code>String</code>、
     *              パートにその名前のヘッダーがない場合は<code>null</code>
     */
    public String getHeader(String name);

    /**
     * 指定された名前でパートのヘッダーの値を取得します。
     *
     * <p>返された<code>Collection</code>に対するいかなる変更もこの<code>Part</code>に影響を与えてはいけません。
     *
     * <p>パートのヘッダー名は大文字小文字を区別しません。
     *
     * @param name 値が返されるヘッダー名
     *
     * @return ヘッダーの指定された名前の値の(空の可能性のある) <code>Collection</code>
     */
    public Collection<String> getHeaders(String name);

    /**
     * このパートのヘッダーの名前を取得します。
     *
     * <p>いくつかのサーブレットコンテナはこのメソッドを使用してヘッダーにアクセスする許可しません。
     * その場合このメソッドは<code>null</code>を返します。
     *
     * <p>返された<code>Collection</code>に対するいかなる変更もこの<code>Part</code>に影響を与えてはいけません。
     *
     * @return このパートのヘッダー名の(空の可能性のある) <code>Collection</code>
     */
    public Collection<String> getHeaderNames();

}
