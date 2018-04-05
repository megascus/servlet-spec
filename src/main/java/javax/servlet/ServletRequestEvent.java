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
 * この種類のイベントはServletRequestのライフサイクルのイベントを示します。
 * 
 * イベントのソースはこのウェブアプリケーションのServletContextです。
 *
 * @see ServletRequestListener
 * @since Servlet 2.4
 */
public class ServletRequestEvent extends java.util.EventObject { 

    private static final long serialVersionUID = -7467864054698729101L;

    private final transient ServletRequest request;

    /** 
     * 与えられたServletContextとServletRequestのためのServletRequestEventを生成します。
     *
     * @param sc       ウェブアプリケーションのServletContext
     * @param request  イベントを送っているServletRequest
     */
    public ServletRequestEvent(ServletContext sc, ServletRequest request) {
        super(sc);
        this.request = request;
    }

    /**
     * 変更されたServletRequestを返します。
     * Returns the ServletRequest that is changing.
     * 
     * @return このイベントに関係する{@link ServletRequest}
     */
    public ServletRequest getServletRequest () { 
        return this.request;
    }

    /**
     * このウェブアプリケーションのServletContextを返します。
     *
     * @return このウェブアプリケーションの{@link ServletContext}
     */
    public ServletContext getServletContext () { 
        return (ServletContext) super.getSource();
    }
}
