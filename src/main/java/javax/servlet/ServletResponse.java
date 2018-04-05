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

package javax.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;


/**
 * サーブレットがクライアントにレスポンスを送るのを手助けするオブジェクトを定義します。
 * サーブレットコンテナは<code>ServletResponse</code>オブジェクトを作成し、サーブレットの<code>service</code>メソッドに渡します。
 *
 * <p>レスポンスのMIMEボディでバイナリデータを送るには{@link #getOutputStream}が返す{@link ServletOutputStream}を使用してください。
 * 文字データを送るには{@link #getWriter}が返す<code>PrintWriter</code>オブジェクトを使用してください。
 * バイナリとテキストデータを混在させる、例えば、マルチパートのレスポンスを作成するのであれば<code>ServletOutputStream</code>を使用して文字列の部分は手動で管理します。
 * 
 * <p>レスポンスのMIMEボディの文字コードは以下のような設定で指定することができます。
 * <ul>
 * <li>リクエスト毎
 * <li>ウェブアプリケーション毎({@link ServletContext#setRequestCharacterEncoding}やデプロイメントディスクリプタを使用)
 * <li>コンテナ毎(ベンダー固有の設定を使用してコンテナにデプロイされたすべてのアプリケーションに適用)
 * </ul>
 * 先に述べられた設定のうち複数が使用された場合、プライオリティは記述された順序どおりです。
 * 
 * リクエストごとに、レスポンスの文字コードは{@link #setCharacterEncoding}および{@link #setContentType} メソッドを使用して明示的に指定するか、
 * 暗黙的に{@link #setLocale}メソッドを使用して指定できます。
 * 明示的(explicit)な指定が暗黙(implicit)の指定よりも優先されます。
 * 文字セットが明示的に指定されていない場合はISO-8859-1が使用されます。
 *  
 * <code>setCharacterEncoding</code>や<code>setContentType</code>、<code>setLocale</code>メソッドは
 * <code>getWriter</code>を呼び出す前かつ文字エンコーディングが使用されるレスポンスをコミットする前にs呼び出す必要があります。
 * 
 * <p>MIMEの詳細情報については、<a href="http://www.ietf.org/rfc/rfc2045.txt">RFC 2045</a>などのインターネットRFCを参照してください。
 * SMTPやHTTPなどのプロトコルでMIMEのプロファイルを定義しており、これらの標準はまだ進化し続けています。
 *
 * @author Various
 *
 * @see ServletOutputStream
 */
 
public interface ServletResponse {
    
    /**
     * このレスポンスで送信されるボディに使用されている文字エンコーディング（MIME文字セット）の名前を返します。
     * レスポンスの文字エンコーディングは以下のような方法の降順で指定することができます。
     * <ul>
     * <li>リクエスト毎
     * <li>ウェブアプリケーション毎({@link ServletContext#setRequestCharacterEncoding}やデプロイメントディスクリプタを使用)
     * <li>コンテナ毎(ベンダー固有の設定を使用してコンテナにデプロイされたすべてのアプリケーションに適用)
     * </ul>
     * これらのメソッドのうち最初の結果が返されます。
     * リクエストごとに、レスポンスの文字セットは{@link setCharacterEncoding} および{@link setContentType}メソッドを使用して明示的に指定することも、
     * {@link setLocale}メソッドを暗黙的に使用して明示的に指定することもできます。明示的な指定は暗黙の指定よりも優先されます。
     * <code>getWriter</code>が呼び出された後もしくはレスポンスがコミットされた後にこれらの方法が呼び出されても文字エンコーディングには影響しません。
     * 文字エンコーディングが指定されていない場合は<code>ISO-8859-1</code>が返されます。
     * <p>MIMEと文字エンコーディングの詳細情報については、<a href="http://www.ietf.org/rfc/rfc2047.txt">RFC 2047</a>を参照してください。
     *
     * @return 文字エンコーディングの名前を示す<code>String</code>、例として<code>UTF-8</code>
     */
    public String getCharacterEncoding();
    
    /**
     * レスポンスの中で送られるMIMEボディに使用されるコンテンツタイプを返します。
     * レスポンスがコミットされる前に{@link #setContentType}を使用して適切なコンテンツタイプが指定されていなければいけません。
     * コンテンツタイプが指定されていない場合、このメソッドはnullを返します。
     * コンテンツタイプが指定されており、文字エンコーディングが明示的または{@link #getCharacterEncoding}や{@link #getWriter}が呼び出され暗黙的に指定されている場合は、
     * 返された文字列にcharsetパラメータが含まれます。
     * 文字エンコーディングが指定されていない場合、charsetパラメータは省略されます。
     *
     * @return コンテンツタイプを示す<code>String</code>、例として<code>text/html; charset=UTF-8</code>、もしくはnull
     *
     * @since Servlet 2.4
     */
    public String getContentType();
    
    

    /**
     * レスポンスのバイナリデータを書き出すのに適した{@link ServletOutputStream}を返します。
     * サーブレットコンテナはバイナリデータをエンコードしません。
     *
     * <p>{@link ServletOutputStream#flush()}を呼び出すことでレスポンスをコミットします。
     *
     * このメソッドか{@link #getWriter}はどちらかのみを呼び出すことが出来ます。
     * {@link #reset}メソッドが呼び出されてない限り両方を呼び出すことは出来ません。
     *
     * @return バイナリデータを書き出すための{@link ServletOutputStream} 
     *
     * @exception IllegalStateException このレスポンスで<code>getWriter</code>メソッドがすでに呼び出されている場合
     *
     * @exception IOException I/Oエラーが発生した
     *
     * @see #getWriter
     * @see #reset
     */
    public ServletOutputStream getOutputStream() throws IOException;
    
    /**
     * 文字のテキストをクライアントに送信することが出来る<code>PrintWriter</code>のオブジェクトを返します。
     * この<code>PrintWriter</code>は{@link #getCharacterEncoding}から返される文字エンコーディングを使用します。
     * <code>getCharacterEncoding</code>で取得されるレスポンスの文字エンコーディングが指定されていない(つまり、メソッドがデフォルト値<code>ISO-8859-1</code>を返す)場合、
     * <code>getWriter</code>の文字エンコーディングを<code>ISO-8859-1</code>に更新します。
     * 
     * <p>{@link PrintWriter#flush()}を呼び出すことでレスポンスをコミットします。
     * このメソッドか{@link #getOutputStream}はどちらかのみを呼び出すことが出来ます。
     * {@link #reset}メソッドが呼び出されてない限り両方を呼び出すことは出来ません。
     * 
     * @return 文字データをクライアントに返すことができる <code>PrintWriter</code>オブジェクト
     *
     * @exception java.io.UnsupportedEncodingException <code>getCharacterEncoding</code>から返された文字エンコーディングが使用できない文字エンコーディングだった
     *
     * @exception IllegalStateException このレスポンスで<code>getOutputStream</code>メソッドがすでに呼び出されている場合
     *
     * @exception IOException I/Oエラーが発生した
     *
     * @see #getOutputStream
     * @see #setCharacterEncoding
     * @see #reset
     */
    public PrintWriter getWriter() throws IOException;
    
    /**
     * クライアントに送られるレスポンスの文字エンコーディング(MIME charset)を、例えばUTF-8に設定します。
     * もし文字エンコーディングがすでに{@link ServletContext#setResponseCharacterEncoding(String)}やデプロイメントディスクリプタや
     * {@link #setContentType(String)}メソッドや{@link #setLocale(Locale)}メソッドで設定されている場合、
     * このメソッドはそれらの値を上書きします。
     * {@link #setContentType}を<code>text/html</code>という<code>String</code>で呼び出し
     * このメソッドを<code>UTF-8</code>という<code>String</code>で呼び出すことは、
     * {@link #setContentType}を<code>text/html; charset=UTF-8</code>という<code>String</code>で呼び出すことと同等です。
     * 
     * <p>このメソッドは文字エンコーディングを変更するために繰り返し呼び出すことが出来ます。
     * このメソッドは<code>getWriter</code>が呼び出された後もしくはレスポンスがコミットされた後に呼び出しても何も行いません。
     * 
     * <p>プロトコルが文字エンコーディングをクライアントに送信する方法を提供している場合、
     * コンテナはサーブレットレスポンスのwriterに使用されている文字エンコーディングをクライアントに送信する必要があります。
     * HTTPの場合は文字エンコーディングはテキストのメディアタイプにおいて<code>Content-Type</code>ヘッダーの一部分として伝えられます。
     * サーブレットでコンテンツタイプが指定されていない場合、文字エンコーディングはHTTPヘッダーを介して伝えられないことに注意してください。 
     * しかしながらサーブレットレスポンスのwriterを使用して書き込まれたテキストをエンコードするために使用されています。
     *
     * @param charset IANA 定義の文字セットを示す文字列
     * (http://www.iana.org/assignments/character-sets)
     *
     * @see #setContentType
     * @see #setLocale
     *
     * @since Servlet 2.4
     */
    public void setCharacterEncoding(String charset);
    
    /**
     * レスポンス内のコンテンツ本文の長さを設定します。HTTPサーブレットではHTTP Content-Lengthヘッダーを設定します。
     *
     * @param len クライアントに返されるコンテンツの長さを指定するinteger、Content-Lengthヘッダーを設定する
     */
    public void setContentLength(int len);
    
    /**
     * レスポンス内のコンテンツ本文の長さを設定します。HTTPサーブレットではHTTP Content-Lengthヘッダーを設定します。
     *
     * @param len クライアントに返されるコンテンツの長さを指定するlong、Content-Lengthヘッダーを設定する
     *
     * @since Servlet 3.1
     */
    public void setContentLengthLong(long len);

    /**
     * クライアントに送られるコンテンツタイプをレスポンスがまだコミットされていない場合に設定します。
     * コンテンツタイプには文字エンコーディングの指定を含めることが出来ます。例えば、<code>text/html;charset=UTF-8</code>です。
     * レスポンスの文字エンコーディングは<code>getWriter</code>が呼び出される前にこのメソッドが呼び出された場合にのみ指定されたコンテンツタイプから設定されます。
     * 
     * <p>このメソッドはコンテンツタイプと文字エンコーディングを変更するために繰り返し呼び出されることがあります。
     * このメソッドはレスポンスがコミットされた後に呼び出しても何も行いません。
     * このメソッドは<code>getWriter</code>が呼び出された後もしくはレスポンスがコミットされた後に呼び出しても文字エンコーディングの設定を行いません。
     * 
     * <p>プロトコルが文字エンコーディングをクライアントに送信する方法を提供している場合、
     * コンテナはサーブレットレスポンスのwriterに使用されている文字エンコーディングをクライアントに送信する必要があります。
     * HTTPの場合は文字エンコーディングはテキストのメディアタイプにおいて<code>Content-Type</code>ヘッダーの一部分として伝えられます。
     *
     * @param type コンテンツのMIMEタイプを示す<code>String</code>
     *
     * @see #setLocale
     * @see #setCharacterEncoding
     * @see #getOutputStream
     * @see #getWriter
     *
     */

    public void setContentType(String type);
    

    /**
     * レスポンスボディの優先バッファサイズを設定します。
     * サーブレットコンテナは最低でも要求されたサイズ以上のバッファを使用します。
     * 使用される実際のバッファサイズは、 <code>getBufferSize</code>を使用して取得することができます。
     *
     * <p>バッファを大きくすると実際に何かが送信される前に多くのコンテンツを書き込むことができ、
     * サーブレットに適切なステータスコードとヘッダーを設定できる期間が長くなります。
     * バッファを小さくするとサーバーのメモリー負荷が減少し、クライアントはデータの受信をより素早く開始できます
     *
     * <p>このメソッドは、レスポンスボディの内容が書き込まれる前に呼び出される必要があります。
     * コンテンツが書き込まれている場合、またはレスポンスオブジェクトがコミットされている場合、このメソッドは<code>IllegalStateException</code>を投げます。
     *
     * @param size 好ましいバッファーサイズ
     *
     * @exception IllegalStateException コンテンツがすでに書き込まれた後にこのメソッドが呼び出された
     *
     * @see 		#getBufferSize
     * @see 		#flushBuffer
     * @see 		#isCommitted
     * @see 		#reset
     */
    public void setBufferSize(int size);
   
    /**
     * レスポンスに使用される実際のバッファサイズを返します。
     * バッファリングを使用しない場合、このメソッドは0を返します。
     *
     * @return 実際に使用されているバッファーサイズ
     *
     * @see #setBufferSize
     * @see #flushBuffer
     * @see #isCommitted
     * @see #reset
     */
    public int getBufferSize();
    
    /**
     * バッファ内のすべてのコンテンツを強制的にクライアントに書き出します。
     * このメソッドを呼び出すと自動的にレスポンスがコミットされます。
     * つまりステータスコードとヘッダーが書き込まれます。
     *
     * @see #setBufferSize
     * @see #getBufferSize
     * @see #isCommitted
     * @see #reset

     * @throws IOException バッファーを完全にフラッシュすることができなかった
     *
     */
    public void flushBuffer() throws IOException;
    
    /**
     * ヘッダーやステータスコードをクリアせずにレスポンスのバッファの内容をクリアします。
     * レスポンスがすでにコミットされている場合、このメソッドは<code>IllegalStateException</code>を投げます。
     *
     * @see #setBufferSize
     * @see #getBufferSize
     * @see #isCommitted
     * @see #reset
     *
     * @since Servlet 2.3
     */

    public void resetBuffer();
    
    /**
     * レスポンスがすでにコミットされているかどうかを示すbooleanを返します。
     * コミットされたレスポンスはステータスコード及びにヘッダーがすでに書き込まれています。
     *
     * @return  レスポンスがすでにコミットされているかどうかを示すboolean
     *
     * @see #setBufferSize
     * @see #getBufferSize
     * @see #flushBuffer
     * @see #reset
     *
     */
    public boolean isCommitted();
    
    /**
     * バッファに存在するすべてのデータとステータスコード、ヘッダーをクリアします。
     * {@link #getWriter}や{@link #getOutputStream}を呼び出した状態もクリアされます。
     * 例えば、{@link #getWriter}、{@link #reset}、{@link #getOutputStream}の順で呼び出すことは適正な方法です。
     * このメソッドの前に{@link #getWriter}や{@link #getOutputStream}が呼び出されていた場合、
     * 対応する返されたWriterまたはOutputStreamは古いオブジェクトとなり、このオブジェクトを使用する動作は未定義です。
     * レスポンスがコミットされている場合、このメソッドは<code>IllegalStateException</code>を投げます。
     *
     * @exception IllegalStateException レスポンスがすでにコミットされていた
     *
     * @see #setBufferSize
     * @see #getBufferSize
     * @see #flushBuffer
     * @see #isCommitted
     */
    public void reset();
    
    /**
     * レスポンスがまたコミットされてない場合にレスポンスにロケールを設定します。
     * また、{@link #setContentType}や{@link #setCharacterEncoding}を使用して文字エンコーディングが明示的に設定されておらず、
     * <code>getWriter</code>がまだ呼び出されていない場合かつレスポンスがまだコミットされてない場合にレスポンスの文字エンコーディングをロケールから適切に設定します。
     * デプロイメントディスクリプタに<code>locale-encoding-mapping-list</code>要素が含まれ、
     * 与えられたロケールに対して文字エンコーディングへのマッピングが用意されている場合はそれが使用されます。
     * そうでない場合はロケールから文字文字エンコーディングへのマッピングはコンテナに依存します。
     * <p>このメソッドは文字エンコーディングを変更するために繰り返し呼び出されることがあります。
     * このメソッドはレスポンスがコミットされた後に呼び出しても何も行いません。
     * このメソッドはすでに{@link #setContentType}がcharsetの指定を伴う呼び出しをされている場合、{@link #setCharacterEncoding}が呼び出されている場合、
     * <code>getWriter</code>が呼び出されている場合、レスポンスがコミットされている場合は、レスポンスに文字エンコーディングの設定を行いません。
     * <p>プロトコルが文字エンコーディングをクライアントに送信する方法を提供している場合、
     * コンテナはサーブレットレスポンスのwriterに使用されている文字エンコーディングをクライアントに送信する必要があります。
     * HTTPの場合は文字エンコーディングはテキストのメディアタイプにおいて<code>Content-Type</code>ヘッダーの一部分として伝えられます。
     * サーブレットでコンテンツタイプが指定されていない場合、文字エンコーディングはHTTPヘッダーを介して伝えられないことに注意してください。 
     * しかしながらサーブレットレスポンスのwriterを使用して書き込まれたテキストをエンコードするために使用されています。
     * 
     * @param loc レスポンスのロケール
     *
     * @see #getLocale
     * @see #setContentType
     * @see #setCharacterEncoding
     */
    public void setLocale(Locale loc);
    
    /**
     * {@link #setLocale}メソッドを使用してこのレスポンスに指定されたロケールを返します。
     * レスポンスがコミットされた後に<code>setLocale</code>に対して行われた呼び出しは何も行いません。
     * ロケールが指定されていない場合、コンテナのデフォルトのロケールが返されます。
     *
     * @return このレスポンスのロケール
     * 
     * @see #setLocale
     */
    public Locale getLocale();

}





