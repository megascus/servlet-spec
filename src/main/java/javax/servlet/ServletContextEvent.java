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
 * これはウェブアプリケーションのServletContextが変更された通知のためのイベントクラスです。
 * 
 * @see ServletContextListener
 *
 * @since Servlet 2.3
 */

public class ServletContextEvent extends java.util.EventObject { 

    private static final long serialVersionUID = -7501701636134222423L;

    /** 
     * 与えられたServletContextからServletContextEventを初期化します。
     *
     * @param source イベントを送ったServletContext
     */
    public ServletContextEvent(ServletContext source) {
        super(source);
    }
    
    /**
     * 変更されたServletContextを返します。
     *
     * @return イベントを送ったServletContext
     */
    public ServletContext getServletContext () { 
        return (ServletContext) super.getSource();
    }
}
