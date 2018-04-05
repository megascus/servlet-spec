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
 * ウェブアプリケーションのServletContextの属性が変更された通知のためのイベントクラスです。
 *
 * @see ServletContextAttributeListener
 *
 * @since Servlet 2.3
 */

public class ServletContextAttributeEvent extends ServletContextEvent { 

    private static final long serialVersionUID = -5804680734245618303L;

    private String name;
    private Object value;

    /**
     * 与えられたServletContextと属性名と属性の値でServletContextAttributeEventを生成します。
     *
     * @param source 属性が変更されたServletContext
     * @param name ServletContextの変更された属性の名前
     * @param value ServletContextの変更された属性の値
     */
    public ServletContextAttributeEvent(ServletContext source,
            String name, Object value) {
        super(source);
        this.name = name;
        this.value = value;
    }
	
    /**
     * ServletContextの変更された属性の名前を取得します。
     *
     * @return ServletContextの変更された属性の名前
     */
    public String getName() {
        return this.name;
    }
	
    /**
     * ServletContextの変更された属性の値を取得します。
     *
     * <p>属性が追加された場合は属性の値です。属性が削除された場合は削除された値です。
     * 属性が置き換えられた場合は属性の古い値です。
     *
     * @return ServletContextの変更された属性の値
     */
    public Object getValue() {
        return this.value;   
    }
}

