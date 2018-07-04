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
 * サーブレットにレスポンスを適合させたい開発者がサブクラス化できるServletResponseインターフェースの便利な実装を提供します。
 * このクラスはWrapperもしくはDecoratorパターンを実装します。
 * メソッドはデフォルトでラップされたリクエストオブジェクトを呼び出します。
 * 
 * @author Various
 * @since Servlet 2.3
 *
 * @see javax.servlet.ServletResponse
 */

 
public class ServletResponseWrapper implements ServletResponse {
	private ServletResponse response;
	/**
     * 指定されたレスポンスオブジェクトをラップするServletResponseアダプタを作成します。
	 * @throws java.lang.IllegalArgumentException responseがnull
     * @param response ラップされる{@link ServletResponse}
     *
	 */


	public ServletResponseWrapper(ServletResponse response) {
	    if (response == null) {
		throw new IllegalArgumentException("Response cannot be null");
	    }
	    this.response = response;
	}

	/**
	* ラップされたServletResponseのオブジェクトを返します。
        *
        * @return ラップされた{@link ServletResponse}
	*/

	public ServletResponse getResponse() {
		return this.response;
	}	
	
	
	/**
	* ラップされているレスポンスを設定します。
	* @throws java.lang.IllegalArgumentException responseがnull
        *
        * @param response 取り付けられる {@link ServletResponse}
	*/
	
	public void setResponse(ServletResponse response) {
	    if (response == null) {
		throw new IllegalArgumentException("Response cannot be null");
	    }
	    this.response = response;
	}

    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのsetCharacterEncoding(String charset)を呼び出すことです。
     *
     * @since Servlet 2.4
     */

    public void setCharacterEncoding(String charset) {
	this.response.setCharacterEncoding(charset);
    }

    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのgetCharacterEncoding()を返すことです。
     */

    public String getCharacterEncoding() {
	return this.response.getCharacterEncoding();
	}
    
    
	  /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのgetOutputStream()を返すことです。
     */

    public ServletOutputStream getOutputStream() throws IOException {
	return this.response.getOutputStream();
    }  
      
     /**
     *  このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのgetWriter()を返すことです。
     */


    public PrintWriter getWriter() throws IOException {
	return this.response.getWriter();
	}
    
    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのsetContentLength(int len)を呼び出すことです。
     */

    public void setContentLength(int len) {
	this.response.setContentLength(len);
    }

    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのsetContentLengthLong(long len)を呼び出すことです。
     */

    public void setContentLengthLong(long len) {
        this.response.setContentLengthLong(len);
    }

    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのsetContentType(String type)を呼び出すことです。
     */

    public void setContentType(String type) {
	this.response.setContentType(type);
    }

    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのgetContentType()を返すことです。
     *
     * @since Servlet 2.4
     */

    public String getContentType() {
	return this.response.getContentType();
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのsetBufferSize(int size)を呼び出すことです。
     */
    public void setBufferSize(int size) {
	this.response.setBufferSize(size);
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのgetBufferSize()を返すことです。
     */
    public int getBufferSize() {
	return this.response.getBufferSize();
    }

    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのflushBuffer()を呼び出すことです。
     */

    public void flushBuffer() throws IOException {
	this.response.flushBuffer();
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのisCommitted()を返すことです。
     */
    public boolean isCommitted() {
	return this.response.isCommitted();
    }

    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのreset()を呼び出すことです。
     */

    public void reset() {
	this.response.reset();
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのresetBuffer()を呼び出すことです。
     */
     
    public void resetBuffer() {
	this.response.resetBuffer();
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのsetLocale(Locale loc)を呼び出すことです。
     */

    public void setLocale(Locale loc) {
	this.response.setLocale(loc);
    }
    
    /**
     * このメソッドのデフォルトの動作はラップされたレスポンスオブジェクトのgetLocale()を返すことです。
     */
    public Locale getLocale() {
	return this.response.getLocale();
    }


    /**
     * このServletResponseWrapperが与えられた{@link ServletResponse}のインスタンスをラップしているかどうかを(再帰的に)チェックします。
     *
     * @param wrapped 探すServletResponseのインスタンス
     *
     * @return 与えられたServletResponseのインスタンスをServletResponseWrapperがラップしているならtrue、そうでないならfalse
     *
     * @since Servlet 3.0
     */
    public boolean isWrapperFor(ServletResponse wrapped) {
        if (response == wrapped) {
            return true;
        } else if (response instanceof ServletResponseWrapper) {
            return ((ServletResponseWrapper) response).isWrapperFor(wrapped);
        } else {
            return false;
        }
    }


    /**
     * このServletResponseWrapperが与えられたClassの型の{@link ServletResponse} をラップしているかどうかを(再帰的に)チェックします。
     *
     * @param wrappedType 探すServletResponseのclassの型
     *
     * @return 与えられたClassの型の{@link ServletResponse}をServletResponseWrapperがラップしているならtrue、そうでないならfalse
     *
     * @throws IllegalArgumentException 与えられたclassが{@link ServletResponse}を実装していない場合
     *
     * @since Servlet 3.0
     */
    public boolean isWrapperFor(Class<?> wrappedType) {
        if (!ServletResponse.class.isAssignableFrom(wrappedType)) {
            throw new IllegalArgumentException("Given class " +
                wrappedType.getName() + " not a subinterface of " +
                ServletResponse.class.getName());
        }
        if (wrappedType.isAssignableFrom(response.getClass())) {
            return true;
        } else if (response instanceof ServletResponseWrapper) {
            return ((ServletResponseWrapper) response).isWrapperFor(wrappedType);
        } else {
            return false;
        }
    }

}





