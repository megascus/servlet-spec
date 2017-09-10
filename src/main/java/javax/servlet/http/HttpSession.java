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

import java.util.Enumeration;
import javax.servlet.ServletContext;

/**
 * 複数のページリクエストにわたったりウェブサイトを訪れたユーザーを識別する方法や、
 * ユーザーに関する情報を保管する方法を提供します。
 *
 * <p>サーブレットコンテナはこのインタフェースを使用してHTTPクライアントとHTTPサーバーの間のセッションを作成します。
 * セッションは指定された期間ユーザーからの複数の接続またはページリクエストにわたって永続化されます。
 * 1つのセッションは通常、何回もサイトにアクセスする1人のユーザーに対応しています。
 * サーバーはCookieを使用したりURLを書き換えたりすることでセッションを維持できます。
 *
 * <p>このインタフェースによりサーブレットは以下のようなことができます。
 * <ul>
 * <li>セッション識別子、作成時間、最後にアクセスされた時間などのセッションに関する情報の表示と操作
 * <li>セッションにオブジェクトを格納することでユーザー情報をユーザーの複数の接続にまたがらせる
 * </ul>
 *
 * <p>アプリケーションがセッションにオブジェクトを格納したり削除したりすると、
 * セッションはそのオブジェクトが {@link HttpSessionBindingListener} を実装しているかどうかをチェックします。
 * そうであれば、サーブレットはオブジェクトにセッションに格納されたこと、削除されたことをオブジェクトに通知します。格納、削除のメソッドの完了後に通知が送信されます。 
 * セッションが無効または期限切れになった場合はその後に通知が送信されます。
 * 
 * <p>コンテナが分散コンテナ設定のVM間でセッションを移動した場合は{@link HttpSessionActivationListener}インターフェイスを実装するすべてのセッション属性が通知されます。
 *
 * <p>サーブレットはCookieが意図的にオフになったときなど、クライアントがセッションに参加しないことを選択したケースを処理できる必要があります。 
 * クライアントがセッションに参加するまで、 <code>isNew</code>は<code>true</code>を返します 。
 * クライアントがセッションに参加しないことを選択した場合、 <code>getSession</code>は各リクエストで異なるセッションを返し、<code>isNew</code>は常に<code>true</code>を返します。
 *
 * <p>セッション情報は現在のWebアプリケーション(<code>ServletContext</code>)のみにスコープが設定されているため、あるコンテキストに格納されている情報は別のコンテキストでは直接参照されません。
 *
 * @author	Various
 *
 * @see 	HttpSessionBindingListener
 * @see 	HttpSessionContext
 */

public interface HttpSession {

    /**
     * セッションが作られた日時を1970 年 1 月 1 日 00:00:00 GMT からのミリ秒数で返します。
     *
     * @return				セッションが作られた日時の1970 年 1 月 1 日 00:00:00 GMT からのミリ秒数の<code>long</code>
     *
     * @exception IllegalStateException	もしこのメソッドが無効なセッションで呼び出された場合
     */
    public long getCreationTime();
    
        
    /**
     * このセッションに割り当てられた一意の識別子を含む文字列を返します。 
     * 識別子はサーブレットコンテナによって割り当てられ、実装に依存します。
     * 
     * @return				このセッションに割り当てられた識別子を指定する文字列
     */
    public String getId();
    

    /**
     * このセッションによって関連付けられたクライアントが最後にリクエストを投げ
     * コンテナが受信した日時を1970 年 1 月 1 日 00:00:00 GMT からのミリ秒数で返します。
     *
     * <p>アプリケーションが実行したセッションに関連付けられた値の取得や設定などのアクションはアクセス時間に影響しません。
     *
     * @return				セッションによって関連付けられたクライアントが最後にリクエストを投げた日時の1970 年 1 月 1 日 00:00:00 GMT からのミリ秒数の <code>long</code>
     *
     * @exception IllegalStateException	もしこのメソッドが無効なセッションで呼び出された場合
     */
    public long getLastAccessedTime();
    
    
    /**
     * セッションが所属するServletContextを返します。
     *    
     * @return ウェブアプリケーションの ServletContext のオブジェクト
     * @since Servlet 2.3
     */
    public ServletContext getServletContext();


    /**
     * サーブレットコンテナがこのセッションを無効にするまでのクライアントからのリクエストの間隔を秒単位で指定します。
     *
     * <p>0以下の <tt>interval</tt> の値はセッションがタイムアウトしないほうが良いことを示します。

     *
     * @param interval		秒数を指定する整数
     */    
    public void setMaxInactiveInterval(int interval);


    /**
     * Returns the maximum time interval, in seconds, that 
     * the servlet container will keep this session open between 
     * client accesses. After this interval, the servlet container
     * will invalidate the session.  The maximum time interval can be set
     * with the <code>setMaxInactiveInterval</code> method.
     *
     * <p>A return value of zero or less indicates that the
     * session will never timeout.
     *
     * @return		an integer specifying the number of
     *			seconds this session remains open
     *			between client requests
     *
     * @see		#setMaxInactiveInterval
     */
    public int getMaxInactiveInterval();
    

    /**
     *
     * @deprecated 	As of Version 2.1, this method is
     *			deprecated and has no replacement.
     *			It will be removed in a future
     *			version of the Java Servlet API.
     *
     * @return the {@link HttpSessionContext} for this session.
     */
    @Deprecated
    public HttpSessionContext getSessionContext();
    

    /**
     * Returns the object bound with the specified name in this session, or
     * <code>null</code> if no object is bound under the name.
     *
     * @param name		a string specifying the name of the object
     *
     * @return			the object with the specified name
     *
     * @exception IllegalStateException	もしこのメソッドが無効なセッションで呼び出された場合
     */
    public Object getAttribute(String name);
    
    
    /**
     * @deprecated 	As of Version 2.2, this method is
     * 			replaced by {@link #getAttribute}.
     *
     * @param name		a string specifying the name of the object
     *
     * @return			the object with the specified name
     *
     * @exception IllegalStateException	もしこのメソッドが無効なセッションで呼び出された場合
     */
    @Deprecated
    public Object getValue(String name);
        

    /**
     * Returns an <code>Enumeration</code> of <code>String</code> objects
     * containing the names of all the objects bound to this session. 
     *
     * @return			an <code>Enumeration</code> of 
     *				<code>String</code> objects specifying the
     *				names of all the objects bound to
     *				this session
     *
     * @exception IllegalStateException	もしこのメソッドが無効なセッションで呼び出された場合
     */    
    public Enumeration<String> getAttributeNames();
    

    /**
     * @deprecated 	As of Version 2.2, this method is
     * 			replaced by {@link #getAttributeNames}
     *
     * @return				an array of <code>String</code>
     *					objects specifying the
     *					names of all the objects bound to
     *					this session
     *
     * @exception IllegalStateException	もしこのメソッドが無効なセッションで呼び出された場合
     */
    @Deprecated
    public String[] getValueNames();
    

    /**
     * Binds an object to this session, using the name specified.
     * If an object of the same name is already bound to the session,
     * the object is replaced.
     *
     * <p>After this method executes, and if the new object
     * implements <code>HttpSessionBindingListener</code>,
     * the container calls 
     * <code>HttpSessionBindingListener.valueBound</code>. The container then   
     * notifies any <code>HttpSessionAttributeListener</code>s in the web 
     * application.
     
     * <p>If an object was already bound to this session of this name
     * that implements <code>HttpSessionBindingListener</code>, its 
     * <code>HttpSessionBindingListener.valueUnbound</code> method is called.
     *
     * <p>If the value passed in is null, this has the same effect as calling 
     * <code>removeAttribute()</code>.
     *
     *
     * @param name			the name to which the object is bound;
     *					cannot be null
     *
     * @param value			the object to be bound
     *
     * @exception IllegalStateException	もしこのメソッドが無効なセッションで呼び出された場合
     */
    public void setAttribute(String name, Object value);
    

    /**
     * @deprecated  Version 2.2から{@link #setAttribute}に置き換えられました
     *
     * @param name			the name to which the object is bound;
     *					cannot be null
     *
     * @param value			the object to be bound; cannot be null
     *
     * @exception IllegalStateException	もしこのメソッドが無効なセッションで呼び出された場合
     */
    @Deprecated
    public void putValue(String name, Object value);


    /**
     * Removes the object bound with the specified name from
     * this session. If the session does not have an object
     * bound with the specified name, this method does nothing.
     *
     * <p>After this method executes, and if the object
     * implements <code>HttpSessionBindingListener</code>,
     * the container calls 
     * <code>HttpSessionBindingListener.valueUnbound</code>. The container
     * then notifies any <code>HttpSessionAttributeListener</code>s in the web 
     * application.
     *
     * @param name				セッションから取り除くオブジェクトの名前
     *
     * @exception IllegalStateException	もしこのメソッドが無効なセッションで呼び出された場合
     */
    public void removeAttribute(String name);


    /**
     * @deprecated 	Version 2.2から{@link #removeAttribute}に置き換えられました
     *
     * @param name				セッションから取り除くオブジェクトの名前
     *
     * @exception IllegalStateException	もしこのメソッドが無効なセッションで呼び出された場合
     */
    @Deprecated
    public void removeValue(String name);


    /**
     * このセッションを保存されているすべての値を取り除いてから無効化します。
     *
     * @exception IllegalStateException	すでに無効になっているセッションで呼び出された場合
     */
    public void invalidate();
    
    
    /**
     * クライアントがセッションについてまだ認識していない場合、
     * またはクライアントがセッションに参加しないことを選択した場合は <code>true</code> を返します。
     * たとえば、サーバーがCookieベースのセッションのみを使用し、クライアントがCookieの使用を無効にしていた場合、
     * セッションは各リクエストに対して新しく生成されます。
     *
     * @return 				<code>true</code> ならばサーバーがセッションを作成したが、クライアントはセッションに参加していない
     *
     * @exception IllegalStateException	もしこのメソッドが無効なセッションで呼び出された場合
     */
    public boolean isNew();

}

