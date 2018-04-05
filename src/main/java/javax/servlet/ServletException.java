/*
 * Copyright (c) 1997-2018 Oracle and/or its affiliates. All rights reserved.
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


/**
 * サーブレットが問題に遭遇した場合に投げられる一般的な例外を定義します。
 *
 * @author 	Various
 */


public class ServletException extends Exception {

    private Throwable rootCause;





    /**
     * 新しいServletExceptionを生成します。
     *
     */

    public ServletException() {
	super();
    }
    
   

    

    /**
     * 指定されたメッセージで新しいServletExceptionを生成します。
     * メッセージはサーバーログに書き出されるかユーザーに表示されます。
     *
     * @param message 		Exceptionのメッセージのテキストを示す<code>String</code> 
     *
     */

    public ServletException(String message) {
	super(message);
    }
    
   
   
    

    /**
     * サーブレットが例外を投げる必要があるときに通常の操作を妨害した"根本原因"の例外と説明メッセージを含めて新しいServletExceptionを生成します。
     *
     *
     * @param message 		例外のメッセージのテキストを含む<code>String</code>
     *
     * @param rootCause		ServletExceptionを作るのに必要な通常の操作を妨害した<code>Throwable</code>
     *
     */
    
    public ServletException(String message, Throwable rootCause) {
	super(message, rootCause);
	this.rootCause = rootCause;
    }





    /**
     * サーブレットが例外を投げる必要があるときに通常の操作を妨害した"根本原因"の例外を含めて新しいServletExceptionを生成します。
     * この例外のメッセージは元となる例外のローカライズされたメッセージがベースとなります。
     * 
     * <p>このメソッドは<code>Throwable</code>の<code>getLocalizedMessage</code>を呼び出し、ローカライズされた例外のメッセージを取得します。
     * <code>ServletException</code>のサブクラスではこのメソッドをオーバーライドして特定のロケール用にデザインされた例外のメッセージを作ることができます。
     *
     * @param rootCause 	ServletExceptionを作るのに必要な通常の操作を妨害した<code>Throwable</code>
     *
     */

    public ServletException(Throwable rootCause) {
	super(rootCause);
	this.rootCause = rootCause;
    }
  
  
 
 
    
    /**
     * このServletExceptionの原因となった<code>Throwable</code> 
     *
     *
     * @return			このServletExceptionの原因となった<code>Throwable</code> 
     *
     */
    
    public Throwable getRootCause() {
	return rootCause;
    }
}





