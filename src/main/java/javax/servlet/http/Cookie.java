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

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * サーブレットによってWebブラウザに送信され、ブラウザによって保存され、後でサーバーに返される少量の情報であるCookieを作成します。
 * Cookieの値でクライアントを一意に識別できるので、Cookieは一般にセッション管理に使用されます。
 * 
 * <p>Cookieは名前や単一の値、コメント、パス、ドメイン修飾子、最大経過時間、バージョン番号などのオプションの属性を持ちます。
 * 一部のWebブラウザではオプショナルな属性の処理方法にバグがあるためサーブレットの相互運用性を向上させるために余分に使用します
 *
 * <p>サーブレットは {@link HttpServletResponse#addCookie} メソッドを使用してHTTPレスポンスヘッダーにフィールドを追加し、ブラウザにCookieを1つずつ送信します。
 * ブラウザは各Webサーバーごとに20個のCookieをサポートし、合計300個のCookieをサポートし、Cookieサイズはそれぞれ4 KBに制限されるはずです。
 * 
 * <p>ブラウザはHTTPのリクエストヘッダーにフィールドを追加することによって、サーブレットにCookieを返します。
 * Cookieは {@link HttpServletRequest#getCookies} メソッドを使用することでリクエストから取得できます。
 * いくつかのCookieは同じ名前でもパス属性が異なるかもしれません。
 * 
 * <p>CookieはCookieを使用するWebページのキャッシュに影響します。
 * HTTP 1.0ではこのクラスで作成されたCookieを使用するページはキャッシュされません。
 * このクラスはHTTP 1.1で定義されたキャッシュコントロールをサポートしていません。
 *
 * <p>このクラスはバージョン0（Netscape準拠）とバージョン1（RFC2109準拠）の両方のCookie仕様をサポートします。
 * デフォルトでは最高の相互運用性を確保するためにバージョン0のCookieが作成されます。
 * 
 * <p>訳注：実際には2017年時点での最新のCookieの仕様であるRFC6265に従っているように見えます。
 * 例えばhttpOnly属性はバージョン0、バージョン1には存在しません。
 * ただし、version属性自体はRFC6265には存在しません。
 *
 * @author	Various
 */
public class Cookie implements Cloneable, Serializable {

    private static final long serialVersionUID = -6454587001725327448L;

    private static final String TSPECIALS;

    private static final String LSTRING_FILE =
        "javax.servlet.http.LocalStrings";

    private static ResourceBundle lStrings =
        ResourceBundle.getBundle(LSTRING_FILE);

    static {
        if (Boolean.valueOf(System.getProperty("org.glassfish.web.rfc2109_cookie_names_enforced", "true"))) {
            TSPECIALS = "/()<>@,;:\\\"[]?={} \t";
        } else {
            TSPECIALS = ",; ";
        }
    }
    
    //
    // The value of the cookie itself.
    //
    
    private String name;	// NAME= ... "$Name" style is reserved
    private String value;	// value of NAME

    //
    // Attributes encoded in the header's cookie fields.
    //
    
    private String comment;	// ;Comment=VALUE ... describes cookie's use
				// ;Discard ... implied by maxAge < 0
    private String domain;	// ;Domain=VALUE ... domain that sees cookie
    private int maxAge = -1;	// ;Max-Age=VALUE ... cookies auto-expire
    private String path;	// ;Path=VALUE ... URLs that see the cookie
    private boolean secure;	// ;Secure ... e.g. use SSL
    private int version = 0;	// ;Version=1 ... means RFC 2109++ style
    private boolean isHttpOnly = false;

    /**
     * 指定された名前と値を持つCookieを生成します。
     *
     * <p>名前はRFC 2109に準拠していなければなりません。
     * ただしベンダーはオリジナルのNetscapeのCookie仕様に準拠したCookie名を受け付ける設定オプションを提供することがあります。
     *
     * <p>Cookieは作成された後に名前を変更することはできません。
     *
     * <p>Cookieの値は作成した後に <code>setValue</code> メソッドで変更可能です。
     *
     * <p>デフォルトではCookieはNetscapeのCookie仕様に従って作成されます。
     * バージョンは <code>setVersion</code> メソッドで変更できます。
     *
     * @param name Cookieの名前
     *
     * @param value Cookieの値
     *
     * @throws IllegalArgumentException	Cookieの名前がnullまたは空であるか、不正な文字（コンマ、スペース、セミコロンなど）が含まれている場合、
     * もしくはCookieプロトコルで使用するために予約されているトークンと一致する場合
     *
     * @see #setValue
     * @see #setVersion
     */
    public Cookie(String name, String value) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException(
                    lStrings.getString("err.cookie_name_blank"));
        }
        if (!isToken(name) ||
                name.equalsIgnoreCase("Comment") || // rfc2019
                name.equalsIgnoreCase("Discard") || // 2019++
                name.equalsIgnoreCase("Domain") ||
                name.equalsIgnoreCase("Expires") || // (old cookies)
                name.equalsIgnoreCase("Max-Age") || // rfc2019
                name.equalsIgnoreCase("Path") ||
                name.equalsIgnoreCase("Secure") ||
                name.equalsIgnoreCase("Version") ||
                name.startsWith("$")) {
            String errMsg = lStrings.getString("err.cookie_name_is_token");
            Object[] errArgs = new Object[1];
            errArgs[0] = name;
            errMsg = MessageFormat.format(errMsg, errArgs);
            throw new IllegalArgumentException(errMsg);
        }

        this.name = name;
        this.value = value;
    }

    /**
     * Cookieの目的を説明するコメントを設定します。
     * ブラウザがCookieをユーザに表示する場合に便利です。
     * コメントはNetscapeのCookie仕様に準拠したVersion 0のCookieでサポートされていません。

     *
     * @param purpose		ユーザーに表示するコメントの <code>String</code>
     *
     * @see #getComment
     */
    public void setComment(String purpose) {
        comment = purpose;
    }

    /**
     * このCookieの目的を説明するコメントを返します。Cookieにコメントがない場合は <code>null</code> を返します。
     *
     * @return Cookieのコメント、存在しない場合は <code>null</code>
     *
     * @see #setComment
     */ 
    public String getComment() {
        return comment;
    }
    
    /**
     *
     * このCookieを送信する必要があるドメインを設定します。
     *
     * <p>ドメイン名の形式はRFC 2109で規定されています。
     * ドメイン名は <code>.foo.com</code> のように"."で始まります。
     * この場合、Cookie は指定された Domain Name System (DNS) のゾーン内のサーバから見えるようになります。
     * 例えば、www.foo.com からは見えるけれど、a.b.foo.com からは見えないというようにです。
     * デフォルトではCookieは送信したサーバー自身にのみ返されます。
     * 
     * <p>訳注：RFC 6265 ではドメイン名は"."から始まる必要はありません。
     * また、foo.com と指定した場合は www.foo.com からも a.b.foo.com からも参照できますので、先のJavaDocの説明は間違えていると考えられます。
     *
     * @param domain Cookieが参照できるドメイン名;RFC 2109に従っている必要がある
     *
     * @see #getDomain
     */
    public void setDomain(String domain) {
        this.domain = domain.toLowerCase(Locale.ENGLISH); // IE allegedly needs this
    }

    /**
     * このCookieに設定されたドメイン名を取得します。
     *
     * <p>ドメイン名はRFC 2109に従ってフォーマットされています。
     *
     * @return このCookieのドメイン名
     *
     * @see #setDomain
     */ 
    public String getDomain() {
        return domain;
    }

    /**
     * Cookieの最長の存続期間を設定します。
     *
     * <p>正の値はCookieが指定秒後に失効することを示します。
     * 注意として、値はCookieの現在の経過時間ではなくCookieが期限切れになるまでの<i>最長の</i>経過時間です。
     *
     * <p>負の値はCookieが永続的に保存されずWebブラウザが終了したときに削除されることを意味します。
     * 値が0の場合はCookieは削除されます。
     *
     * @param expiry		正の値の場合Cookieの最長の存続期間の秒数;負の数の場合はWebブラウザが終了したときに削除される;0の場合は消される
     *
     * @see #getMaxAge
     */
    public void setMaxAge(int expiry) {
        maxAge = expiry;
    }

    /**
     * Cookieの最長の存続期間を取得します。
     *
     * デフォルトでは-1が返されます。これはブラウザが終了するまでCookieが保持されることを示します。
     *
     * @return			正の値の場合Cookieの最長の存続期間の秒数;負の数の場合はWebブラウザが終了するまで保持されるという意味
     *
     * @see #setMaxAge
     */
    public int getMaxAge() {
        return maxAge;
    }
    
    /**
     * クライアントがCookieを返す必要があるpathを指定します。
     *
     * <p>Cookieはここで指定したサブディレクトリを含むディレクトリ内のすべてのページで参照することができます。
     * CookieのpathはCookieを設定したサーブレットを含む必要があります。
     * 例えば<i>/catalog</i>と設定すると、そのCookieはサーバー上の<i>/catalog</i>以下のすべてのディレクトリで参照できます。
     *
     * <p>Cookieのパス名を設定する方法の詳細についてはRFC 2109（インターネットで入手可能）を参照してください。
     * 
     * <p>訳注：RFC 6265（インターネットで入手可能）を参照したほうが良いでしょう。
     *
     * @param uri		パス文字列
     *
     * @see #getPath
     */
    public void setPath(String uri) {
        path = uri;
    }

    /**
     * ブラウザがサーバーにCookieを返すpathを返します。
     * Cookieはサーバー上のこのpathの配下すべてで参照できます。
     *
     * @return		サーブレットの名前を含むpathの文字列、例えば<i>/catalog</i>
     *
     * @see #setPath
     */ 
    public String getPath() {
        return path;
    }

    /**
     * HTTPSまたはSSLなどの安全なプロトコルを使用してのみCookieを送信する必要があるかどうかをブラウザに示します。
     *
     * <p>デフォルト値は <code>false</code> です。
     *
     * @param flag もし <code>true</code>ならばブラウザは安全なプロトコルを使用してのみCookieを送信する; 
     * もし <code>false</code>ならばどんなプロトコルでも送信する
     *
     * @see #getSecure
     */
    public void setSecure(boolean flag) {
        secure = flag;
    }

    /**
     * ブラウザが安全なプロトコルを使用してのみCookieを送信する場合は<code>true</code>を返し、どんなプロトコルでもCookieを送信する場合は<code>false</code>を返します。
     *
     * @return ブラウザが安全なプロトコルを使うなら<code>true</code>、そうでないなら<code>false</code>。 
     *
     * @see #setSecure
     */
    public boolean getSecure() {
        return secure;
    }

    /**
     * Cookieの名前を返します。名前は作成した後に変更することはできません。
     *
     * @return Cookieの名前
     */
    public String getName() {
        return name;
    }

    /**
     * Cookieに新しい値を設定します。
     * 
     * <p>もしあなたがバイナリデータを使いたいなら、BASE64でエンコードをしてみてもよいです。
     * 
     * <p>バージョン0のCookieでは値に空白、鉤かっこ、丸かっこ、中かっこ、等号、コンマ、二重引用符、スラッシュ、疑問符、アットマーク、コロン、およびセミコロンを含めない方が良いです。
     * 空の値は、すべてのブラウザで同じように動作しないことがあります。
     * 
     * <p>訳注：バージョン0はもう使われてないはずなのでこの記述は無視してよいです。
     *
     * @param newValue Cookieの新しい値
     *
     * @see #getValue
     */
    public void setValue(String newValue) {
        value = newValue;
    }

    /**
     * Cookieの現在の値を取得します。
     *
     * @return Cookieの現在の値
     *
     * @see #setValue
     */
    public String getValue() {
        return value;
    }

    /**
     * このCookieが準拠しているCookieのプロトコルのバージョンを返します。
     * バージョン1の場合はRFC 2109に準拠しています。
     * バージョン0の場合は元のNetscapeの仕様に準拠しています。
     * ブラウザが提供するCookieはブラウザのCookieのバージョンを使用して識別します。
     * 
     * @return			Cookieが元のNetscapeの仕様に準拠している場合は0;CookieがRFC 2109に準拠している場合は1
     *
     * @see #setVersion
     */
    public int getVersion() {
        return version;
    }

    /**
     * このCookieが準拠しているCookieのプロトコルのバージョンを設定します。
     * 
     * <p>バージョン0は、元のNetscape Cookie仕様に準拠しています。バージョン1はRFC 2109に準拠しています。
     *
     * <p>RFC 2109はまだ誕生してから時間がたっていないので、バージョン1は実験的なものとみなしてください。本番サイトではまだ使用しないでください。
     * 
     * <p>訳注：RFC 2109は2017年現在はすでに失効しているぐらいに古い仕様です。
     * 2017年現在有効なRFC 6265ではversion属性は存在しないので、この値がどう使用されるかはアプリケーションサーバーの仕様を確認したほうが良いでしょう。
     *
     * @param v	Cookieが元のNetscapeの仕様に準拠する必要がある場合は0;CookieがRFC 2109に準拠する必要がある場合は1
     *
     * @see #getVersion
     */
    public void setVersion(int v) {
        version = v;
    }

    /*
     * Tests a string and returns true if the string counts as a 
     * reserved token in the Java language.
     * 
     * @param value the <code>String</code> to be tested
     *
     * @return <code>true</code> if the <code>String</code> is a reserved
     * token; <code>false</code> otherwise
     */
    private boolean isToken(String value) {
        int len = value.length();
        for (int i = 0; i < len; i++) {
            char c = value.charAt(i);
            if (c < 0x20 || c >= 0x7f || TSPECIALS.indexOf(c) != -1) {
                return false;
            }
        }

        return true;
    }

    /**
     * 標準の <code>java.lang.Object.clone</code> をオーバーライドしてこのCookieのコピーを返します。
     */
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Cookie に <i>HttpOnly</i> 属性を設定します。
     *
     * <tt>isHttpOnly</tt>にtrueを設定すると、このCookieは <tt>HttpOnly</tt> 属性がつけられることで <i>HttpOnly</i> とマークされます。
     *
     * <p><i>HttpOnly</i> のCookieはクライアント側のスクリプトコードに公開されないことになっているため、
     * 特定の種類のクロスサイトスクリプティング攻撃を緩和するのに役立ちます。
     *
     * @param isHttpOnly Cookieが <i>HttpOnly</i> の場合はture、それ以外の場合はfalse
     *
     * @since Servlet 3.0
     */
    public void setHttpOnly(boolean isHttpOnly) {
        this.isHttpOnly = isHttpOnly;
    }
 
    /**
     * Cookieに <i>HttpOnly</i> 属性がついているかどうかを確認します。
     *
     * @return trueならCookieに <i>HttpOnly</i> 属性がついている、そうでなければfalse
     *
     * @since Servlet 3.0
     */
    public boolean isHttpOnly() {
        return isHttpOnly;
    }
}

