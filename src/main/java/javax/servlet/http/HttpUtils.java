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

import javax.servlet.ServletInputStream;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.io.IOException;

/**
 * @deprecated		このインターフェースはServlet API version 2.3で非推奨です。
 *          これらのメソッドはデフォルトのエンコーディングの時のみ有用で、リクエストのインターフェースに移されました。
 */
@Deprecated
public class HttpUtils {

    private static final String LSTRING_FILE =
	"javax.servlet.http.LocalStrings";
    private static ResourceBundle lStrings =
	ResourceBundle.getBundle(LSTRING_FILE);
        
    
    /**
     * 空の<code>HttpUtils</code>のオブジェクトを作成します。
     */
    public HttpUtils() {}
    

    /**
     * クライアントからサーバーに渡されたクエリ文字列を解析し、キーと値のペアを持つ<code>HashTable</code>オブジェクトを生成します。
     * クエリ文字列は、GETメソッドまたはPOSTメソッドによって提供された文字列の形式でなければなりません。つまり、<i>key=value</i>,の形式でキーと値のペアを持つ必要があります。各ペアは&amp;で区切ります。
     *
     * <p>キーはクエリ文字列に違う値を持ちつつ複数回現れることがあります。
     * しかしながらハッシュテーブルではキーは1回だけ表示され、値はクエリ文字列によって送信された複数の値を含む配列になります。
     *
     * <p>ハッシュテーブルのキーと値はデコードされた形式で格納されているので、+文字はスペースに変換され、(<i>%xx</i>のような)16進表記で送られた文字はASCII文字に変換されます。
     *
     * @param s		解析するクエリを含んだ文字列
     *
     * @return		解析されたキーと値のペアで構築された <code>HashTable</code>
     *
     * @exception IllegalArgumentException クエリ文字列が不正だった
     */
    public static Hashtable<String, String[]> parseQueryString(String s) {

        String valArray[] = null;
	
        if (s == null) {
            throw new IllegalArgumentException();
        }

        Hashtable<String, String[]> ht = new Hashtable<String, String[]>();
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(s, "&");
        while (st.hasMoreTokens()) {
        String pair = st.nextToken();
        int pos = pair.indexOf('=');
        if (pos == -1) {
            // XXX
            // should give more detail about the illegal argument
            throw new IllegalArgumentException();
        }
        String key = parseName(pair.substring(0, pos), sb);
        String val = parseName(pair.substring(pos+1, pair.length()), sb);
        if (ht.containsKey(key)) {
            String oldVals[] = ht.get(key);
            valArray = new String[oldVals.length + 1];
            for (int i = 0; i < oldVals.length; i++) {
                valArray[i] = oldVals[i];
            }
            valArray[oldVals.length] = val;
        } else {
            valArray = new String[1];
            valArray[0] = val;
        }
        ht.put(key, valArray);
    }

	return ht;
    }


    /**
     * クライアントがHTTP POSTメソッドと<i>application/x-www-form-urlencoded</i> MIME形式を使用してサーバーに送信するHTMLフォームからのデータを解析します。
     *
     * <p>POSTメソッドによって送信されるデータにはキーと値のペアが含まれます。
     * キーはPOSTデータに違う値を持ちつつ複数回現れることがあります。 
     * しかしながらハッシュテーブルではキーは1回だけ表示され、値ははPOSTメソッドによって送信された複数の値を含む配列になります。
     *
     * <p>ハッシュテーブルのキーと値はデコードされた形式で格納されているので、+文字はスペースに変換され、(<i>%xx</i>のような)16進表記で送られた文字はASCII文字に変換されます。
     *
     * @param len	このメソッドにわたす<code>ServletInputStream</code>のオブジェクトの文字の長さを指定する整数
     *
     * @param in	クライアントから送信されたデータを含む <code>ServletInputStream</code> のオブジェクト
     *
     * @return		解析されたキーと値のペアで構築された <code>HashTable</code>
     *
     * @exception IllegalArgumentException POSTメソッドによって送られたデータが不正だった
     */
    public static Hashtable<String, String[]> parsePostData(int len, 
                ServletInputStream in) {
	// XXX
	// should a length of 0 be an IllegalArgumentException
	
	if (len <=0) {
            // cheap hack to return an empty hash
	    return new Hashtable<String, String[]>(); 
        }

	if (in == null) {
	    throw new IllegalArgumentException();
	}
	
	//
	// Make sure we read the entire POSTed body.
	//
        byte[] postedBytes = new byte [len];
        try {
            int offset = 0;
       
	    do {
		int inputLen = in.read (postedBytes, offset, len - offset);
		if (inputLen <= 0) {
		    String msg = lStrings.getString("err.io.short_read");
		    throw new IllegalArgumentException (msg);
		}
		offset += inputLen;
	    } while ((len - offset) > 0);

	} catch (IOException e) {
	    throw new IllegalArgumentException(e.getMessage());
	}

        // XXX we shouldn't assume that the only kind of POST body
        // is FORM data encoded using ASCII or ISO Latin/1 ... or
        // that the body should always be treated as FORM data.
        //

        try {
            String postedBody = new String(postedBytes, 0, len, "8859_1");
            return parseQueryString(postedBody);
        } catch (java.io.UnsupportedEncodingException e) {
            // XXX function should accept an encoding parameter & throw this
            // exception.  Otherwise throw something expected.
            throw new IllegalArgumentException(e.getMessage());
        }
    }


    /*
     * Parse a name in the query string.
     */
    private static String parseName(String s, StringBuilder sb) {
        sb.setLength(0);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i); 
            switch (c) {
                case '+':
                    sb.append(' ');
                    break;
                case '%':
                    try {
                        sb.append((char) Integer.parseInt(s.substring(i+1, i+3), 
                                16));
                        i += 2;
                    } catch (NumberFormatException e) {
                        // XXX
                        // need to be more specific about illegal arg
                        throw new IllegalArgumentException();
                    } catch (StringIndexOutOfBoundsException e) {
                        String rest  = s.substring(i);
                        sb.append(rest);
                        if (rest.length()==2)
                            i++;
                        }

                        break;
                default:
                    sb.append(c);
                    break;
            }
        }

        return sb.toString();
    }


    /**
     * <code>HttpServletRequest</code>のオブジェクトの情報を使用してクライアントがリクエストを行うために作ったURLを再構成します。
     * 返されるURLにはプロトコルやサーバー名、ポート番号とサーバーのパスが含まれますが、クエリパラメータは含まれません。
     * 
     * <p>このメソッドは文字列ではなく<code>StringBuffer</code>を返すので、例えばクエリパラメータを追加する等、簡単にURLを変更することができます。
     *
     * <p>このメソッドはメッセージをリダイレクトしたりエラーを報告するのに役立ちます。  
     *
     * @param req	クライアントのリクエストを含む <code>HttpServletRequest</code> のオブジェクト
     * 
     * @return		再構築されたURLを含む <code>StringBuffer</code> のオブジェクト
     */
    public static StringBuffer getRequestURL (HttpServletRequest req) {
        StringBuffer url = new StringBuffer();
        String scheme = req.getScheme ();
        int port = req.getServerPort ();
        String urlPath = req.getRequestURI();

        //String		servletPath = req.getServletPath ();
        //String		pathInfo = req.getPathInfo ();

        url.append (scheme);		// http, https
        url.append ("://");
        url.append (req.getServerName ());
        if ((scheme.equals ("http") && port != 80)
        || (scheme.equals ("https") && port != 443)) {
            url.append (':');
            url.append (req.getServerPort ());
        }
        //if (servletPath != null)
        //    url.append (servletPath);
        //if (pathInfo != null)
        //    url.append (pathInfo);
        url.append(urlPath);

        return url;
    }
}



