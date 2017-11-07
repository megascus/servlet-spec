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
import javax.servlet.ServletResponseWrapper;

/**
 * サーブレットにレスポンスを適合させたい開発者がサブクラス化できるHttpServletResponseインターフェースの便利な実装を提供します。
 * このクラスはWrapperもしくはDecoratorパターンを実装します。
 * メソッドはデフォルトでラップされたリクエストオブジェクトを呼び出します。
 * 
 * @author Various
 * @since Servlet 2.3
 *
 * @see javax.servlet.http.HttpServletResponse
 */

public class HttpServletResponseWrapper extends ServletResponseWrapper implements HttpServletResponse {

    /** 
     * 指定されたレスポンスオブジェクトをラップするレスポンスアダプタを作成します。
     * @throws java.lang.IllegalArgumentException responseがnull
     *
     * @param response ラップされる{@link HttpServletResponse}
     */
    public HttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }
    
    private HttpServletResponse _getHttpServletResponse() {
        return (HttpServletResponse) super.getResponse();
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのaddCookie(Cookie cookie)を呼び出すことです。
     */
    @Override
    public void addCookie(Cookie cookie) {
        this._getHttpServletResponse().addCookie(cookie);
    }

    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのcontainsHeader(String name)を呼び出すことです。
     */
    @Override
    public boolean containsHeader(String name) {
        return this._getHttpServletResponse().containsHeader(name);
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのencodeURL(String url)を呼び出すことです。
     */
    @Override
    public String encodeURL(String url) {
        return this._getHttpServletResponse().encodeURL(url);
    }

    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのencodeRedirectURL(String url)を返すことです。
     */
    @Override
    public String encodeRedirectURL(String url) {
        return this._getHttpServletResponse().encodeRedirectURL(url);
    }

    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのencodeUrl(String url)を呼び出すことです。
     *
     * @deprecated Version 2.1からは代わりに{@link #encodeURL(String url)}を使用してください
     */
    @Deprecated
    @Override
    public String encodeUrl(String url) {
        return this._getHttpServletResponse().encodeUrl(url);
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのencodeRedirectUrl(String url)を返すことです。
     *
     * @deprecated Version 2.1からは代わりに {@link #encodeRedirectURL(String url)}を使用してください
     */
    @Deprecated
    @Override
    public String encodeRedirectUrl(String url) {
        return this._getHttpServletResponse().encodeRedirectUrl(url);
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのsendError(int sc, String msg)を呼び出すことです。
     */
    @Override
    public void sendError(int sc, String msg) throws IOException {
        this._getHttpServletResponse().sendError(sc, msg);
    }

    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのsendError(int sc)を呼び出すことです。
     */
    @Override
    public void sendError(int sc) throws IOException {
        this._getHttpServletResponse().sendError(sc);
    }

    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのsendRedirect(String location)を返すことです。
     */
    @Override
    public void sendRedirect(String location) throws IOException {
        this._getHttpServletResponse().sendRedirect(location);
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのsetDateHeader(String name, long date)を呼び出すことです。
     */
    @Override
    public void setDateHeader(String name, long date) {
        this._getHttpServletResponse().setDateHeader(name, date);
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのaddDateHeader(String name, long date)を呼び出すことです。
     */
    @Override
    public void addDateHeader(String name, long date) {
        this._getHttpServletResponse().addDateHeader(name, date);
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのsetHeader(String name, String value)を返すことです。
     */
    @Override
    public void setHeader(String name, String value) {
        this._getHttpServletResponse().setHeader(name, value);
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのaddHeader(String name, String value)を返すことです。
     */
    @Override
    public void addHeader(String name, String value) {
        this._getHttpServletResponse().addHeader(name, value);
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのsetIntHeader(String name, int value)を呼び出すことです。
     */
    @Override
    public void setIntHeader(String name, int value) {
        this._getHttpServletResponse().setIntHeader(name, value);
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのaddIntHeader(String name, int value)を呼び出すことです。
     */
    @Override
    public void addIntHeader(String name, int value) {
        this._getHttpServletResponse().addIntHeader(name, value);
    }

    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのsetStatus(int sc)を呼び出すことです。
     */
    @Override
    public void setStatus(int sc) {
        this._getHttpServletResponse().setStatus(sc);
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのsetStatus(int sc, String sm)を呼び出すことです。
     * 
     * @deprecated Version 2.1 からメッセージパラメータのあいまいな意味のために非推奨になりました。
     * ステータスコードを設定するためには{@link #setStatus(int)}を使用してください。
     * エラーと一緒に説明文を送るためには{@link #sendError(int, String)}を使用してください。
     */
    @Deprecated
    @Override
    public void setStatus(int sc, String sm) {
        this._getHttpServletResponse().setStatus(sc, sm);
    }

    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトの{@link HttpServletResponse#getStatus}を呼び出すことです。
     *
     * @return このラップされたレスポンスの現在のステータスコード
     */
    @Override
    public int getStatus() {
        return _getHttpServletResponse().getStatus();
    }

    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトの{@link HttpServletResponse#getHeader}を呼び出すことです。
     *
     * @param name 値を返されるレスポンスヘッダーの名前
     *
     * @return 与えられた名前のレスポンスヘッダーの値、このレスポンスのヘッダーに与えられた名前で値が設定されてない場合は<tt>null</tt>
     *
     * @since Servlet 3.0
     */
    @Override
    public String getHeader(String name) {
        return _getHttpServletResponse().getHeader(name);
    }

    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトの{@link HttpServletResponse#getHeaders} を呼び出すことです。
     *
     * <p><code>Collection</code>に対するいかなる変更も<code>HttpServletResponse</code>に影響を与えてはいけません。
     *
     * @param name 値を返されるレスポンスヘッダーの名前
     *
     * @return 与えられた名前のレスポンスヘッダーの値の(空の可能性がある)<code>Collection</code>
     *
     * @since Servlet 3.0
     */                        
    @Override
    public Collection<String> getHeaders(String name) {
        return _getHttpServletResponse().getHeaders(name);
    }

    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトの{@link HttpServletResponse#getHeaderNames}を呼び出すことです。
     *
     * <p><code>Collection</code>に対するいかなる変更も<code>HttpServletResponse</code>に影響を与えてはいけません。
     *
     * @return レスポンスヘッダーの名前の(空の可能性がある)<code>Collection</code>
     *
     * @since Servlet 3.0
     */
    @Override
    public Collection<String> getHeaderNames() {
        return _getHttpServletResponse().getHeaderNames();
    }

    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトの{@link HttpServletResponse#setTrailerFields}を呼び出すことです。
     *
     * @param supplier トレイラーヘッダーのサプライヤ
     *
     * @since Servlet 4.0
     */
    @Override
    public void setTrailerFields(Supplier<Map<String, String>> supplier) {
        _getHttpServletResponse().setTrailerFields(supplier);
    }

    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトの{@link HttpServletResponse#getTrailerFields}を呼び出すことです。
     *
     * @return トレイラーヘッダーのサプライヤ
     *
     * @since Servlet 4.0
     */
    @Override
    public Supplier<Map<String, String>> getTrailerFields() {
        return _getHttpServletResponse().getTrailerFields();
    }
}
