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
     * ウェブアプリケーションのServletRequestの属性が変更された通知のためのイベントクラスです。
     * @see ServletRequestAttributeListener
     * @since Servlet 2.4
     */

public class ServletRequestAttributeEvent extends ServletRequestEvent { 

    private static final long serialVersionUID = -1466635426192317793L;

    private String name;
    private Object value;

     /**
      * 与えられたこのウェブアプリケーションのServletContext、属性が変更されたServletRequestとその属性の名前と値でServletRequestAttributeEventを生成します。
      *
      * @param sc		イベントが送られているServletContext
      * @param request		イベントが送られているServletRequest
      * @param name		ServletRequestの属性の名前
      * @param value		ServletRequestの属性の値
      */
    public ServletRequestAttributeEvent(ServletContext sc, ServletRequest request, String name, Object value) {
        super(sc, request);
        this.name = name;
        this.value = value;
    }

    /**
      * ServletRequestの変更された属性の名前を取得します。
      *
      * @return		ServletRequestの変更された属性の名前
      */
    public String getName() {
        return this.name;
    }

    /**
     * 属性の追加された、削除された、置き換えられた値を返します。
     * 
     * 属性が追加された場合は属性の値です。属性が削除された場合は削除された値です。
     * 属性が置き換えられた場合は属性の古い値です。
     *
     * @return		ServletRequestの変更された属性の値
     */
    public Object getValue() {
        return this.value;   
    }
}
