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

package javax.servlet.http;

/**
 * このタイプのイベントは{@link HttpSessionBindingListener}を実装するオブジェクトがセッションに設定や削除された時に送信されるか、
 * セッションの値が設定、削除、もしくは置換されたときにデプロイメントディスクリプタで設定された{@link HttpSessionAttributeListener}に送信されます。
 *
 * <p>セッションは{@link HttpSession#setAttribute(java.lang.String,java.lang.Object)}の呼び出しによってオブジェクトを設定し、 
 * {@link HttpSession#removeAttribute(java.lang.String)}の呼び出しによってオブジェクトを削除します。
 *
 * @author Various
 * 
 * @see HttpSession
 * @see HttpSessionBindingListener
 * @see HttpSessionAttributeListener
 */

public class HttpSessionBindingEvent extends HttpSessionEvent {

    private static final long serialVersionUID = 7308000419984825907L;

    /* The name to which the object is being bound or unbound */
    private String name;
    
    /* The object is being bound or unbound */
    private Object value;
    
    /**
     * セッションに登録、または削除されたことをオブジェクトに通知するイベントを作成します。
     * イベントを受け取るにはオブジェクトが{@link HttpSessionBindingListener}を実装する必要があります。
     * 
     * <p>訳注：クラスのJavaDocにも書いてある通り{@link HttpSessionAttributeListener}を実装したクラスでもイベントを受け取れます。
     *
     * @param session オブジェクトが登録、削除されたセッション
     * @param name オブジェクトが登録、削除された名前
     *
     * @see #getName
     * @see #getSession
     */
    public HttpSessionBindingEvent(HttpSession session, String name) {
        super(session);
        this.name = name;
    }
    
    /**
     * セッションに登録、または削除されたことをオブジェクトに通知するイベントを作成します。
     * イベントを受け取るにはオブジェクトが{@link HttpSessionBindingListener}を実装する必要があります。
     * 
     * <p>訳注：クラスのJavaDocにも書いてある通り{@link HttpSessionAttributeListener}を実装したクラスでもイベントを受け取れます。
     *
     * @param session オブジェクトが登録、削除されたセッション
     * @param name オブジェクトが登録、削除された名前
     * @param value 登録、削除されたオブジェクト
     *
     * @see #getName
     * @see #getSession
     */
    public HttpSessionBindingEvent(HttpSession session, String name, Object value) {
        super(session);
        this.name = name;
        this.value = value;
    }
    
    /** 変更されたセッションを返します。 */
    @Override
    public HttpSession getSession () { 
        return super.getSession();
    }
 
    /**
     * オブジェクトがセッションに登録、削除された名前を返します。
     *
     * @return オブジェクトがセッションに登録、削除された名前を示す文字列
     */
    public String getName() {
        return name;
    }
    
    /**
     * 登録、削除、または置換された属性の値を返します。 
     * 属性が追加された場合は属性の値です。
     * 属性が削除された場合は削除された値です。
     * 属性が置き換えられた場合は古い値です。
     * Returns the value of the attribute that has been added, removed or
     * replaced. If the attribute was added (or bound), this is the value of the
     * attribute. If the attribute was removed (or unbound), this is the value
     * of the removed attribute. If the attribute was replaced, this is the old
     * value of the attribute.
     *
     * @return 登録、削除、または置換された属性の値
     *
     * @since Servlet 2.3
     */
    public Object getValue() {
        return this.value;   
    }
}
