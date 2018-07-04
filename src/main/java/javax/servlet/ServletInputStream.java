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

import java.io.InputStream;
import java.io.IOException;

/**
 * 一度に1行ずつデータを読み込む効率的なreadLineメソッドを含む、クライアントからのリクエストからバイナリデータを読み込むための入力ストリームを提供します。
 * HTTP POSTやPUTなどのプロトコルでは、<code>ServletInputStream</code>オブジェクトを使用することでクライアントから送信されたデータを読み取ることができます。
 * 
 * <p><code>ServletInputStream</code>オブジェクトは通常、{@link ServletRequest#getInputStream}メソッドを使用して取得されます。
 * 
 * <p>これはサーブレットコンテナが実装する抽象クラスです。このクラスのサブクラスでは<code>java.io.InputStream.read()</code>メソッドを実装する必要があります。
 *
 * @author 	Various
 *
 * @see		ServletRequest 
 *
 */

public abstract class ServletInputStream extends InputStream {



    /**
     * これは抽象クラスなので何もしません。
     *
     */

    protected ServletInputStream() { }

  
  
    
    /**
     * 入力ストリームを一度に1行読み込みます。
     * オフセットから始めて一定のバイト数を読むか、改行文字に到達するまで配列にバイトを読み込みます。
     *
     * <p>このメソッドは最大バイト数を読み取る前に入力ストリームの終端に達した場合は-1を返します。
     *
     *
     *
     * @param b 		データが読み込まれるバイトの配列
     *
     * @param off 		このメソッドが読み込みを開始する文字を指定する整数
     *
     * @param len		読み込む最大バイト数を指定する整数
     *
     * @return			読み込まれた実際のバイト数を示す整数、ストリームの終端に達した場合は-1
     *
     * @exception IOException	I/O例外が発生した
     *
     */
    public int readLine(byte[] b, int off, int len) throws IOException {

	if (len <= 0) {
	    return 0;
	}
	int count = 0, c;

	while ((c = read()) != -1) {
	    b[off++] = (byte)c;
	    count++;
	    if (c == '\n' || count == len) {
		break;
	    }
	}
	return count > 0 ? count : -1;
    }


    /**
     * ストリームのすべてのデータが読み取られた場合はtrueを返し、そうでない場合はfalseを返します。
     *
     * @return この特定の要求のすべてのデータが読み取られた場合は<code>true</code>、そうでない場合は<code>false</code>を返す
     *
     * @since Servlet 3.1
     */
    public abstract boolean isFinished();

    /**
     * ブロッキングせずにデータを読み込むことができるときに<code>true</code>、そうでない場合は<code>false</code>を返す。
     *
     * @return ブロッキングせずにデータを得ることができるときに<code>true</code>、そうでない場合は<code>false</code>を返す
     *
     * @since Servlet 3.1
     */
    public abstract boolean isReady();

    /**
     * <code>ServletInputStream</code>が読み込むことができるときに提供された{@link WriteListener}を実行するように<code>ServletInputStream</code>に指示をします。
     *
     * @param readListener <code>ServletInputStream</code>が読み込み可能な時に通知を受ける必要のある{@link ReadListener}
     *
     * @exception IllegalStateException 以下の条件の一つがtrueの時
     * <ul>
     * <li>関連するリクエストはアップグレードされず、非同期開始も開始されない
     * <li>setReadListenerが同じリクエストのスコープで2回以上呼び出される
     * </ul>
     *
     * @throws NullPointerException if readListener is null
     *
     * @since Servlet 3.1

     */
    public abstract void setReadListener(ReadListener readListener);
}



