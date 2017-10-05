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
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
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
 *
 *
 * This file incorporates work covered by the following copyright and
 * permission notice:
 *
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package javax.servlet.http;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;
import javax.servlet.ServletResponse;

/**
 * {@link ServletResponse}インターフェースを拡張して、レスポンスを送信する時に使用するHTTP固有の機能を提供します。
 * たとえば、HTTPヘッダーやCookieにアクセスするためのメソッドを持ちます。
 *
 * <p>サーブレットコンテナは<code>HttpServletResponse</code>のオブジェクトを作成し、
 * サーブレットのserviceメソッド(<code>doGet</code>、<code>doPost</code>など)に引数として渡します。
 * 
 * @author	Various
 *
 * @see		javax.servlet.ServletResponse
 *
 */
public interface HttpServletResponse extends ServletResponse {

    /**
     * 指定されたCookieをレスポンスに追加します。このメソッドを複数回呼び出すことで複数のCookieを設定できます。

     *
     * @param cookie クライアントに返されるCookie
     *
     */
    public void addCookie(Cookie cookie);

    /**
     * 名前付きレスポンスヘッダーがすでに設定されているかどうかを示すbooleanを返します。
     * 
     * @param	name	ヘッダー名
     * @return		名前つきレスポンスヘッダーがすでに設定されている場合は<code>true</code>、そうでない場合は<code>false</code>
     */
    public boolean containsHeader(String name);

    /**
     * 指定されたURLをセッションIDを含めてエンコードします。エンコードが不要な場合はURLをそのまま返します。
     * このメソッドの実装にはセッションIDをURLにエンコードする必要があるかどうかを判断するロジックが含まれています。
     * たとえば、ブラウザがCookieをサポートしている場合やセッショントラッキングがオフの場合はURLエンコーディングは不要です。
     * 
     * <p>堅牢なセッショントラッキングのためにはサーブレットが発行するすべてのURLをこのメソッドに通す必要があります。
     * そうしなければCookieをサポートしていないブラウザではURLリライティングを使用できません。
     * 
     * <p>URLが相対的な場合、常に現在のHttpServletRequestから相対的になります。
     *
     * @param	url	エンコードされるURL
     * @return		エンコードが必要な場合はエンコードされたURL、そうでない場合は変更されていないURL
     * @exception IllegalArgumentException URLが不正な場合
     */
    public String encodeURL(String url);

    /**
     * 指定されたURLを{@link #sendRedirect(String)}メソッドで使用するためにエンコードします。
     * エンコードが不要な場合はURLをそのまま返します。
     * このメソッドの実装にはセッションIDをURLにエンコードする必要があるかどうかを判断するロジックが含まれています。
     * たとえば、ブラウザがCookieをサポートしている場合やセッショントラッキングがオフの場合はURLエンコーディングは不要です。
     * この決定を行うためのルールは通常のリンクをエンコードするかどうかを決めるために使用されるルールとは異なる可能性があるため、
     * このメソッドは{@link #encodeURL(String)}メソッドから切り離されています。
     * 
     * <p><code>HttpServletResponse.sendRedirect</code>メソッドに送信されるすべてのURLをこのメソッドに通す必要があります。
     * そうしなければCookieをサポートしていないブラウザではURLリライティングを使用できません。
     *
     * <p>URLが相対的な場合、常に現在のHttpServletRequestから相対的になります。
     *
     * @param	url	エンコードされるURL
     * @return		エンコードが必要な場合はエンコードされたURL、そうでない場合は変更されていないURL
     * @exception IllegalArgumentException URLが不正な場合
     *
     * @see #sendRedirect
     * @see #encodeUrl
     */
    public String encodeRedirectURL(String url);

    /**
     * @deprecated	Version 2.1から {@link #encodeURL(String)}に置き換えられました。
     *
     * @param	url	エンコードされるURL
     * @return		エンコードが必要な場合はエンコードされたURL、そうでない場合は変更されていないURL
     * @exception IllegalArgumentException URLが不正な場合
     */
    @Deprecated
    public String encodeUrl(String url);
    
    /**
     * @deprecated	Version 2.1から {@link #encodeRedirectURL(String)}に置き換えられました。
     *
     * @param	url	エンコードされるURL
     * @return		エンコードが必要な場合はエンコードされたURL、そうでない場合は変更されていないURL
     * @exception IllegalArgumentException URLが不正な場合
     */
    @Deprecated
    public String encodeRedirectUrl(String url);

    /**
     * 指定されたステータスを使用してエラーレスポンスをクライアントに送信し、バッファをクリアします。
     * サーバーはデフォルトでは指定されたメッセージを含むHTML形式のように見えるサーバーエラーページでコンテンツの種類を"text/html"に設定したレスポンスを作成しします。
     * 呼び出し元は現在のレスポンスのエンコーディングとコンテンツタイプに対して安全であることを確認するために
     * メッセージをエスケープまたは再エンコードする<strong>責任を負いません</strong>。
     * コンテナがメッセージを含むエラーページを生成しているため、この安全面はコンテナの責任です。
     * サーバーはクッキーを保持し、有効なレスポンスとしてエラーページを提供するのに必要なヘッダーをクリアまたは更新します。
     *
     * <p>ウェブアプリケーションで渡されたステータスコードに対応するエラーページの宣言が行われていた場合、
     * 提案されたmsgパラメータより優先的にそれが返され、msgパラメータは無視されるでしょう。</p>
     *
     * <p>レスポンスがすでにコミットされていた場合、このメソッドはIllegalStateExceptionを投げます。
     * このメソッドを使用した後はレスポンスははコミットされたものとみなされるべきであり、書き込まれるべきでもありません。
     *
     * @param	sc	エラーステータスコード
     * @param	msg	説明のメッセージ
     * @exception	IOException	I/Oエラーが発生した
     * @exception	IllegalStateException	このメソッドが呼び出される前にレスポンスがコミットされていた
     */
    public void sendError(int sc, String msg) throws IOException;

    /**
     * 指定されたステータスを使用してエラーレスポンスをクライアントに送信し、バッファをクリアします。
     * 
     * サーバーはクッキーを保持し、有効なレスポンスとしてエラーページを提供するのに必要なヘッダーをクリアまたは更新します。
     *
     * ウェブアプリケーションで渡されたステータスコードに対応するエラーページの宣言が行われていた場合、エラーページが返されるでしょう。
     * 
     * <p>レスポンスがすでにコミットされていた場合、このメソッドはIllegalStateExceptionを投げます。
     * このメソッドを使用した後はレスポンスははコミットされたものとみなされるべきであり、書き込まれるべきでもありません。
     *
     * @param	sc	エラーステータスコード
     * @exception	IOException	I/Oエラーが発生した
     * @exception	IllegalStateException	このメソッドが呼び出される前にレスポンスがコミットされていた
     */
    public void sendError(int sc) throws IOException;

    /**
     * 指定されたリダイレクト先のURLを使用してクライアントに一時的なリダイレクトレスポンスを送信し、バッファをクリアします。 
     * バッファはこのメソッドで設定されたデータに置き換えられます。 
     * このメソッドを呼び出すと、ステータスコードが{@link #SC_FOUND} 302 (Found)に設定されます。 
     * このメソッドは相対URLを受け入れることができ、サーブレットコンテナは相対URLを絶対URLに変換してからクライアントにレスポンスを送信するべきです。
     * 先頭に'/'のない相対的なロケーションである場合、コンテナは現在のリクエストURIとの相対的なURLとして解釈します。
     * 先頭に '/'のある相対的なロケーションである場合、コンテナはサーブレットコンテナのルートからの相対的なURLとして解釈します。
     * 先頭に'/'が二つある相対的なロケーションである場合、コンテナはそれをネットワークパス参照として解釈します。(
     * <a href="http://www.ietf.org/rfc/rfc3986.txt">
     * RFC 3986: Uniform Resource Identifier (URI): 一般的構文</a>, section 4.2
     * &quot;相対的参照&quot;を参照してください)
     * 指定されたリダイレクト先のURLを使用してクライアントに一時的なリダイレクトレスポンスを送信し、バッファをクリアします。 
     *
     * <p>レスポンスがすでにコミットされていた場合、このメソッドはIllegalStateExceptionを投げます。
     * このメソッドを使用した後はレスポンスははコミットされたものとみなされるべきであり、書き込まれるべきでもありません。
     *
     * @param		location	リダイレクト先のURL
     * @exception	IOException	I/Oエラーが発生した
     * @exception	IllegalStateException	このメソッドが呼び出される前にレスポンスがコミットされていた
     */
    public void sendRedirect(String location) throws IOException;
    
    /**
     * 指定された名前と日付値を持つレスポンスヘッダーを設定します。
     * 日付はエポックからのミリ秒単位で指定します。
     * ヘッダーがすでに設定されている場合は新しい値が前の値を上書きします。 
     * 値を設定する前にヘッダーの有無を確認するために<code>containsHeader</code>メソッドを使用できます。
     * 
     * @param	name	ヘッダーに値を設定する名前
     * @param	date	割り当てられる日付値
     * 
     * @see #containsHeader
     * @see #addDateHeader
     */
    public void setDateHeader(String name, long date);
    
    /**
     * 指定された名前と日付値を持つレスポンスヘッダーを設定します。
     * 日付はエポックからのミリ秒単位で指定します。
     * このメソッドはレスポンスヘッダーに複数の値を持たせることができます。
     * 
     * @param	name	ヘッダーに値を設定する名前
     * @param	date	追加される日付値
     * 
     * @see #setDateHeader
     */
    public void addDateHeader(String name, long date);
    
    /**
     * 指定された名前と値を持つレスポンスヘッダーを設定します。
     * ヘッダーがすでに設定されている場合は新しい値が前の値を上書きします。 
     * 値を設定する前にヘッダーの有無を確認するために<code>containsHeader</code>メソッドを使用できます。
     * 
     * @param	name	ヘッダーの名前
     * @param	value	ヘッダーに追加する値、8bit文字列(octed string)の場合は RFC2047に従ってエンコードされている必要がある
     *		(http://www.ietf.org/rfc/rfc2047.txt)
     *
     * @see #containsHeader
     * @see #addHeader
     */
    public void setHeader(String name, String value);
    
    /**
     * 指定された名前と値を持つレスポンスヘッダーを設定します。
     * このメソッドはレスポンスヘッダーに複数の値を持たせることができます。
     * 
     * @param	name	ヘッダーの名前
     * @param	value	ヘッダーに追加する値、8bit文字列(octed string)の場合は RFC2047に従ってエンコードされている必要がある
     *		(http://www.ietf.org/rfc/rfc2047.txt)
     *
     * @see #setHeader
     */
    public void addHeader(String name, String value);

    /**
     * 指定された名前と整数値を持つレスポンスヘッダーを設定します。
     * ヘッダーがすでに設定されている場合は新しい値が前の値を上書きします。 
     * 値を設定する前にヘッダーの有無を確認するために<code>containsHeader</code>メソッドを使用できます。
     *
     * @param	name	ヘッダーの名前
     * @param	value	割り当てられる整数
     *
     * @see #containsHeader
     * @see #addIntHeader
     */
    public void setIntHeader(String name, int value);

    /**
     * 指定された名前と整数値を持つレスポンスヘッダーを設定します。
     * このメソッドはレスポンスヘッダーに複数の値を持たせることができます。
     *
     * @param	name	ヘッダーの名前
     * @param	value	割り当てられる整数
     *
     * @see #setIntHeader
     */
    public void addIntHeader(String name, int value);

    /**
     * レスポンスにステータスコードを設定します。
     * 
     * <p>このメソッドは、エラーがない場合のリターンステータスコード(たとえば、{@link #SC_OK}や{@link #SC_MOVED_TEMPORARILY}ステータスコードの場合)
     * を設定するために使用されます。
     * 
     * <p>このメソッドを使用してエラーコードを設定してもコンテナのエラーページメカニズムは実行されないでしょう。
     * エラーがあり、呼び出し側がウェブアプリケーションで定義されたエラーページを呼び出したい場合は、
     * 代わりに{@link #sendError} を使用する必要があります。
     * 
     * <p>このメソッドはすべてのCookieとその他のレスポンスヘッダーをそのまま保持します。
     * 
     * <p>有効なステータスコードは、2XX、3XX、4XX、5XXの範囲のコードです。
     * それ以外のステータスコードはコンテナ固有のものとして扱われます。
     * 
     * @param	sc	ステータスコード
     *
     * @see #sendError
     */
    public void setStatus(int sc);
  

    /**
     * このレスポンスのステータスコードとメッセージを設定します。
     * 
     * @deprecated Version 2.1 からメッセージパラメータのあいまいな意味のために非推奨になりました。
     * ステータスコードを設定するためには{@link #setStatus(int)}を使用してください。
     * エラーと一緒に説明文を送るためには{@link #sendError(int, String)}を使用してください。
     * 
     * @param	sc	ステータスコード
     * @param	sm	ステータスのメッセージ
     */
    @Deprecated
    public void setStatus(int sc, String sm);

    /**
     * このレスポンスの現在のステータスコードを取得します。
     *
     * @return このレスポンスの現在のステータスコード
     *
     * @since Servlet 3.0
     */
    public int getStatus();

    /**
     * 指定された名前でレスポンスヘッダーの値を取得します。
     * 
     * 指定された名前のレスポンスヘッダーが存在し複数の値が含まれている場合は最初に追加した値が返されます。
     * 
     * このメソッドは{@link #setHeader}、{@link #addHeader}、{@link #setDateHeader}、
     * {@link #addDateHeader}、{@link #setIntHeader}、{@link #addIntHeader}を介して設定または追加されたもののみをレスポンスヘッダーとしてみなします。
     *
     * @param name 値を返されるレスポンスヘッダーの名前
     *
     * @return 与えられた名前のレスポンスヘッダーの値、このレスポンスのヘッダーに与えられた名前で値が設定されてない場合は<tt>null</tt>
     *
     * @since Servlet 3.0
     */
    public String getHeader(String name); 

    /**
     * 指定された名前でレスポンスヘッダーの値を取得します。
     *
     * このメソッドは{@link #setHeader}、{@link #addHeader}、{@link #setDateHeader}、
     * {@link #addDateHeader}、{@link #setIntHeader}、{@link #addIntHeader}を介して設定または追加されたもののみをレスポンスヘッダーとしてみなします。
     *
     * <p><code>Collection</code>に対するいかなる変更も<code>HttpServletResponse</code>に影響を与えてはいけません。
     *
     * @param name 値を返されるレスポンスヘッダーの名前
     *
     * @return 与えられた名前のレスポンスヘッダーの値の(空の可能性がある)<code>Collection</code>
     *
     * @since Servlet 3.0
     */			
    public Collection<String> getHeaders(String name); 
    
    /**
     * このレスポンスのヘッダーの名前の一覧を取得します。
     *
     * このメソッドは{@link #setHeader}、{@link #addHeader}、{@link #setDateHeader}、
     * {@link #addDateHeader}、{@link #setIntHeader}、{@link #addIntHeader}を介して設定または追加されたもののみをレスポンスヘッダーとしてみなします。
     *
     * <p><code>Collection</code>に対するいかなる変更も<code>HttpServletResponse</code>に影響を与えてはいけません。
     *
     * @return レスポンスヘッダーの名前の(空の可能性がある)<code>Collection</code>
     *
     * @since Servlet 3.0
     */
    public Collection<String> getHeaderNames();

    /**
     * トレイラーヘッダーのサプライヤを設定します。
     *
     * <p>トレイラーヘッダーのフィールドの値はカンマで区切られたリストとして定義されています。
     * (RFC 7230のセクション3.2.2とセクション4.1.2を参照してください。)</p>
     * 
     * <p>サプライヤはレスポンスの内容を完了させるスレッド/コールの範囲内で呼び出されます。
     * 通常、これはOutputStreamまたはWriterのclose()を呼び出す任意のスレッドになるでしょう。</p>
     *
     * <p>RFC 7230のセクション4.1.2の規定に違反して実行されるトレーラーは無視されます。</p>
     * 
     * <p>RFCでは、提供されたマップの"トレイラー"レスポンスヘッダーのコンマで区切られたリストの値に含まれるすべてのキーの名前を要求しています。
     * アプリケーションにはこの要件が満たされていることを保証する責任があります。
     * これを怠ると相互運用性が損なわれる可能性があります。</p>
     *
     * @implSpec 
     *         デフォルト実装では何も行いません。
     *
     * @param supplier トレイラーヘッダーのサプライヤ
     *
     * @exception IllegalStateException レスポンスがコミットされた後に呼び出された、
     *         もしくはプロトコルがHTTP 1.0であったりプロトコルがHTTP 1.1であってもチャンクエンコーディングでない場合など、
     *         リクエストがトレーラーフィールドをサポートしていなかった
     *
     * @since Servlet 4.0
     */
    default public void setTrailerFields(Supplier<Map<String, String>> supplier) {
    }

    /**
     * トレイラーヘッダーのサプライヤを取得します。
     *
     * @implSpec デフォルト実装ではnullを返します。
     *
     * @return トレイラーヘッダーの<code>Supplier</code>
     * 
     * @since Servlet 4.0
     */
    default public Supplier<Map<String, String>> getTrailerFields() {
        return null;
    }


    /*
     * Server status codes; see RFC 2068.
     */

    /**
     * クライアントが処理を続行できることを示すステータスコード。 (100)
     */
    public static final int SC_CONTINUE = 100;

    /**
     * サーバーがUpgradeヘッダーに従ってプロトコルを切り替えることを示すステータスコード。 (101)
     */
    public static final int SC_SWITCHING_PROTOCOLS = 101;

    /**
     *　リクエストが正常に成功したことを示すステータスコード。 (200)
     */
    public static final int SC_OK = 200;

    /**
     * リクエストが成功し、サーバー上に新しいリソースを作成したことを示すステータスコード。 (201)
     */
    public static final int SC_CREATED = 201;

    /**
     * リクエストが処理のために受け入れられたが完了していないことを示すステータスコード。 (202)
     */
    public static final int SC_ACCEPTED = 202;

    /**
     * クライアントによって提示されたメタ情報がサーバーから発信されていなかったことを示すステータスコード。(203) 
     */
    public static final int SC_NON_AUTHORITATIVE_INFORMATION = 203;

    /**
     * リクエストが成功したが、返されるべき新しい情報が存在しないことを示すステータスコード。 (204)
     */
    public static final int SC_NO_CONTENT = 204;

    /**
     * エージェントがリクエストの送信を引き起こしたドキュメントビューをリセットする<em>必要がある</em>ことを示すステータスコード。 (205)
     */
    public static final int SC_RESET_CONTENT = 205;

    /**
     * サーバがリソースに対する部分的なGETリクエストを満たしたことを示すステータスコード。 (206)
     */
    public static final int SC_PARTIAL_CONTENT = 206;

    /**
     * リクエストされたリソースが、それぞれがそれ自身の特定の位置を有する1組の表現のうちの任意の1つに対応することを示すステータスコード。 (300) 
     */
    public static final int SC_MULTIPLE_CHOICES = 300;

    /**
     * リソースが新しい場所に永続的に移動し、将来の参照ではリクエストに新しいURIを使用する必要があることを示すステータスコード。 (301)
     */
    public static final int SC_MOVED_PERMANENTLY = 301;

    /**
     * リソースが一時的に別の場所に移動したが、将来の参照でも元のURIを使用してリソースにアクセスする必要があることを示すステータスコード。 (302)
     * 
     * 下位互換性のためにこの定義は保持されています。
     * 今では{@link #SC_FOUND}が優先定義になっています。
     */
    public static final int SC_MOVED_TEMPORARILY = 302;

    /**
     * リソースが一時的に異なるURIの下に存在することを示すステータスコード。(302)。
     * 時々リダイレクトが変更される可能性があるため、クライアントは今後のリクエストでも同じリクエストURIを引き続き使用する必要があります。(HTTP/1.1)
     * 302のステータスコードを送るためにはこの変数を使用することをお勧めします。
     */
    public static final int SC_FOUND = 302;

    /**
     * リクエストに対するレスポンスが異なるURIの下で見つけられることを示すステータスコード。 (303)
     */
    public static final int SC_SEE_OTHER = 303;

    /**
     * 条件付きのGET操作が、リソースが利用可能であるが変更されていないことを検出したことを示すステータスコード。 (304)
     */
    public static final int SC_NOT_MODIFIED = 304;

    /**
     * <code><em>Location</em></code>フィールドによって与えられたプロキシを介してリクエストされたリソースにアクセス<em>しなければならない</em> ことを示すステータスコード。 (305)
     */
    public static final int SC_USE_PROXY = 305;

    /**
     * リクエストされたリソースが一時的に異なるURIの下に存在することを示すステータスコード。 (307)
     * 一時URIは、レスポンスのLocationフィールドで指定する<em>必要があります</em> 。
     */
    public static final int SC_TEMPORARY_REDIRECT = 307;

    /**
     * クライアントから送信されたリクエストが構文的に正しくないことを示すステータスコード。 (400)
     */
    public static final int SC_BAD_REQUEST = 400;

    /**
     * リクエストがHTTP認証を要求することを示すステータスコード。 (401)
     */
    public static final int SC_UNAUTHORIZED = 401;

    /**
     * 将来の使用のために予約されたステータスコード。 (402)
     */
    public static final int SC_PAYMENT_REQUIRED = 402;

    /**
     * サーバーがリクエストを理解したがそれを実行することを拒否したことを示すステータスコード。 (403)
     */
    public static final int SC_FORBIDDEN = 403;

    /**
     * リクエストされたリソースが利用可能でないことを示すステータスコード。 (404)
     */
    public static final int SC_NOT_FOUND = 404;

    /**
     * <code><em>Request-Line</em></code>で指定されたメソッドが<code><em>Request-URI</em></code>によって識別されるリソースで許可されていないことを示すステータスコード。 (405)
     */
    public static final int SC_METHOD_NOT_ALLOWED = 405;

    /**
     * リクエストによって識別されたリソースが、リクエストで送信されたacceptヘッダーに従って許容できないコンテンツ特性を有する
     * レスポンスエンティティを生成することしかできないことを示すステータスコード。 (406)
     */
    public static final int SC_NOT_ACCEPTABLE = 406;

    /**
     * クライアントが最初にプロキシで認証されなければならないことを示すステータスコード。 (407)
     */
    public static final int SC_PROXY_AUTHENTICATION_REQUIRED = 407;

    /**
     * サーバーが待機する準備ができた時間内にクライアントがリクエストを生成しなかったことを示すステータスコード。 (408)
     */
    public static final int SC_REQUEST_TIMEOUT = 408;

    /**
     * リソースの現在の状態との競合のためにリクエストを完了できなかったことを示すステータスコード。 (409)
     */
    public static final int SC_CONFLICT = 409;

    /**
     * リソースがもはやサーバーで利用可能でなく、転送先アドレスがわからないことを示すステータスコード。 (410)
     * この状態は永久的であると考えられる<em>必要がある</em>。
     */
    public static final int SC_GONE = 410;

    /**
     * <code><em>Content-Length</em></code>が定義されていないためリクエストを処理できないことを示すステータスコード。 (411)
     */
    public static final int SC_LENGTH_REQUIRED = 411;

    /**
     * リクエストヘッダーフィールドの1つ以上で与えられた前提条件がサーバ上でテストされたときにfalseと評価されたことを示すステータスコード。 (412)
     */
    public static final int SC_PRECONDITION_FAILED = 412;

    /**
     * リクエストエンティティがサーバーが処理できる量よりも大きいため、
     * サーバーがリクエストの処理を拒否していることを示すステータスコード。 (413)
     */
    public static final int SC_REQUEST_ENTITY_TOO_LARGE = 413;

    /**
     * <code><em>Request-URI</em></code>がサーバーが解釈できる長さよりも長いため、
     * サーバーがリクエストの処理を拒否していることを示すステータスコード。 (414)
     */
    public static final int SC_REQUEST_URI_TOO_LONG = 414;

    /**
     * リクエストのエンティティが、リクエストされたリソースへのリクエストされたメソッドでサポートされていないフォーマットのため、
     * サーバーがリクエストの処理を拒否していることを示すステータスコード。 (415)
     */
    public static final int SC_UNSUPPORTED_MEDIA_TYPE = 415;

    /**
     * サーバが要求されたバイト範囲を提供できないことを示すステータスコード。 (416)
     */
    public static final int SC_REQUESTED_RANGE_NOT_SATISFIABLE = 416;

    /**
     * サーバーがExpetリクエストヘッダーで指定された期待値を満たすことができなかったことを示すステータスコード。 (417)
     */
    public static final int SC_EXPECTATION_FAILED = 417;

    /**
     * リクエストを完了できなかったHTTPサーバー内のエラーを示すステータスコード。(500)
     */
    public static final int SC_INTERNAL_SERVER_ERROR = 500;

    /**
     * HTTPサーバーがリクエストを完了するために必要な機能をサポートしていないことを示すステータスコード。 (501)
     */
    public static final int SC_NOT_IMPLEMENTED = 501;

    /**
     * HTTPサーバーがプロキシまたはゲートウェイとして動作している時に参照したサーバーから不正なレスポンスを受信したことを示すステータスコード。(502) 
     */
    public static final int SC_BAD_GATEWAY = 502;

    /**
     * HTTPサーバーが一時的にオーバーロードされ、リクエストを処理できないことを示すステータスコード。 (503)
     */
    public static final int SC_SERVICE_UNAVAILABLE = 503;

    /**
     * サーバがゲートウェイまたはプロキシとして動作している間にアップストリームサーバーから時間に間に合ったレスポンスを受信しなかったことを示すステータスコード。 (504)
     */
    public static final int SC_GATEWAY_TIMEOUT = 504;

    /**
     * サーバーがリクエストメッセージで使用されたHTTPプロトコルのバージョンをサポートしていないかサポートすることを拒否したことを示すステータスコード。 (505)
     */
    public static final int SC_HTTP_VERSION_NOT_SUPPORTED = 505;
}
