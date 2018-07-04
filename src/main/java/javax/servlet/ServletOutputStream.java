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

import java.io.OutputStream;
import java.io.IOException;
import java.io.CharConversionException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * バイナリデータをクライアントに送信するための出力ストリームを提供します。<code>ServletOutputStream</code> オブジェクトは通常、{@link ServletResponse#getOutputStream}メソッドを使用して取得されます。
 * 
 * <p>これは、サーブレットコンテナが実装する抽象クラスです。 このクラスのサブクラスは<code>java.io.OutputStream.write(int)</code>メソッドを実装する必要があります。
 * 
 * @author         Various
 *
 * @see         ServletResponse
 *
 */

public abstract class ServletOutputStream extends OutputStream  {

    private static final String LSTRING_FILE = "javax.servlet.LocalStrings";
    private static ResourceBundle lStrings =
        ResourceBundle.getBundle(LSTRING_FILE);


    
    /**
     * これは抽象クラスなので何もしません。
     */

    protected ServletOutputStream() { }


    /**
     * 復帰改行(CRFL)を使用せずにクライアントに<code>String</code>を書き込みます。
     *
     *
     * @param s                       クライアントに送る<code>String</code>
     *
     * @exception IOException         I/O例外が発生した
     *
     */

    public void print(String s) throws IOException {
        if (s==null) s="null";
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt (i);

            //
            // XXX NOTE:  This is clearly incorrect for many strings,
            // but is the only consistent approach within the current
            // servlet framework.  It must suffice until servlet output
            // streams properly encode their output.
            //
            if ((c & 0xff00) != 0) {        // high order byte must be zero
                String errMsg = lStrings.getString("err.not_iso8859_1");
                Object[] errArgs = new Object[1];
                errArgs[0] = Character.valueOf(c);
                errMsg = MessageFormat.format(errMsg, errArgs);
                throw new CharConversionException(errMsg);
            }
            write (c);
        }
    }



    /**
     * 復帰改行(CRFL)を文字の最後に使用せずにクライアントに<code>boolean</code>を書き込みます。
     *
     * @param b                       クライアントに送る<code>boolean</code>
     *
     * @exception IOException         I/O例外が発生した
     *
     */

    public void print(boolean b) throws IOException {
        String msg;
        if (b) {
            msg = lStrings.getString("value.true");
        } else {
            msg = lStrings.getString("value.false");
        }
        print(msg);
    }



    /**
     * 復帰改行(CRFL)を最後に使用せずにクライアントに文字を書き込みます。
     *
     * @param c                       クライアントに送る文字
     *
     * @exception IOException         I/O例外が発生した
     *
     */

    public void print(char c) throws IOException {
        print(String.valueOf(c));
    }




    /**
     * 復帰改行(CRFL)を最後に使用せずにクライアントにintを書き込みます。
     *
     * @param i                       クライアントに送るint
     *
     * @exception IOException         I/O例外が発生した
     *
     */  

    public void print(int i) throws IOException {
        print(String.valueOf(i));
    }



 
    /**
     * 復帰改行(CRFL)を最後に使用せずにクライアントに<code>long</code>値を書き込みます。

     *
     * @param l                       クライアントに送る<code>long</code>値
     *
     * @exception IOException         I/O例外が発生した
     * 
     */

    public void print(long l) throws IOException {
        print(String.valueOf(l));
    }



    /**
     * 復帰改行(CRFL)を最後に使用せずにクライアントに<code>float</code>値を書き込みます。
     *
     * @param f                       クライアントに送る<code>float</code>値
     *
     * @exception IOException         I/O例外が発生した
     *
     *
     */

    public void print(float f) throws IOException {
        print(String.valueOf(f));
    }



    /**
     * 復帰改行(CRFL)を最後に使用せずにクライアントに<code>double</code>値を書き込みます。
     * 
     * @param d                       クライアントに送る<code>double</code>値
     *
     * @exception IOException         I/O例外が発生した
     *
     */

    public void print(double d) throws IOException {
        print(String.valueOf(d));
    }



    /**
     * クライアントに復帰改行(CRFL)を書き込みます。
     *
     * @exception IOException         I/O例外が発生した
     *
     */

    public void println() throws IOException {
        print("\r\n");
    }



    /**
     * クライアントに<code>String</code>を書き込み、続けて復帰改行(CRFL)を書き込みます。
     *
     *
     * @param s                       クライアントに書き込む<code>String</code>
     *
     * @exception IOException         I/O例外が発生した
     *
     */

    public void println(String s) throws IOException {
        print(s);
        println();
    }




    /**
     * クライアントに<code>boolean</code>値を書き込み、続けて復帰改行(CRFL)を書き込みます。
     *
     *
     * @param b                        クライアントに書き込む<code>boolean</code>値
     *
     * @exception IOException         I/O例外が発生した
     *
     */

    public void println(boolean b) throws IOException {
        print(b);
        println();
    }



    /**
     * クライアントに文字を書き込み、続けて復帰改行(CRFL)を書き込みます。
     *
     * @param c                        クライアントに書き込む文字
     *
     * @exception IOException         I/O例外が発生した
     *
     */

    public void println(char c) throws IOException {
        print(c);
        println();
    }



    /**
     * クライアントに整数を書き込み、続けて復帰改行(CRFL)を書き込みます。
     *
     * @param i                       クライアントに書き込む整数
     *
     * @exception IOException         I/O例外が発生した
     *
     */

    public void println(int i) throws IOException {
        print(i);
        println();
    }



    /**  
     * クライアントに<code>long</code>値を書き込み、続けて復帰改行(CRFL)を書き込みます。
     *
     * @param l                       クライアントに書き込む<code>long</code>値
     *
     * @exception IOException         I/O例外が発生した
     *
     */  

    public void println(long l) throws IOException {
        print(l);
        println();
    }



    /**
     * クライアントに<code>float</code>値を書き込み、続けて復帰改行(CRFL)を書き込みます。
     * 
     * @param f                       クライアントに書き込む<code>float</code>値
     *
     *
     * @exception IOException         I/O例外が発生した
     *
     */

    public void println(float f) throws IOException {
        print(f);
        println();
    }



    /**
     * クライアントに<code>double</code>値を書き込み、続けて復帰改行(CRFL)を書き込みます。
     *
     * @param d                       クライアントに書き込む<code>double</code>値
     *
     * @exception IOException         I/O例外が発生した
     *
     */

    public void println(double d) throws IOException {
        print(d);
        println();
    }

    /**
     * このメソッドはブロックせずにデータを書き込むことができるかどうかを判断するために使用できます。
     *
     * @return この<code>ServletOutputStream</code>に書き込めるときに<code>true</code>、そうでないときは<code>false</code>
     *
     *  @since Servlet 3.1
     */
    public abstract boolean isReady();

    /**
     * <code>ServletOutputStream</code>が書き込むことができるときに提供された{@link WriteListener}を実行するように<code>ServletOutputStream</code>に指示をします。
     *
     *
     * @param writeListener <code>ServletOutputStream</code>が書き込み可能な時に通知を受ける必要のある{@link WriteListener}
     *
     * @exception IllegalStateException 以下の条件の一つがtrueの時
     * <ul>
     * <li>関連するリクエストはアップグレードされず、非同期開始も開始されない
     * <li>setWriteListenerが同じリクエストのスコープで2回以上呼び出される
     * </ul>
     *
     * @throws NullPointerException writeListenerがnull
     *
     * @since Servlet 3.1
     */
    public abstract void setWriteListener(WriteListener writeListener);

}
