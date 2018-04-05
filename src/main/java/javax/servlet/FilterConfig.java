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

import java.util.Enumeration;

/** 
 * サーブレットコンテナがフィルターの初期化中に情報を渡すために使用するフィルター設定オブジェクトです。
 *
 * @see Filter 
 * @since Servlet 2.3
 */
public interface FilterConfig {

    /** 
     * デプロイメントディスクリプタに定義されたfilter-nameを返します。
     *
     * @return このフィルターの名前
     */
    public String getFilterName();


    /**
     * 呼び出し元が実行中の{@link ServletContext}への参照を返します。
     *
     * @return 呼び出し元がサーブレットコンテナと対話するために使用する {@link ServletContext} のオブジェクト
     * 
     * @see ServletContext
     */
    public ServletContext getServletContext();
    

    /**
     * 初期化パラメーターに含まれる<code>String</code>を返します。<code>null</code>の場合は初期化パラメーターが存在しません。
     *
     * @param name 初期化パラメーターで指定された名称の<code>String</code>
     *
     * @return 初期化パラメーターに含まれる<code>String</code>、<code>null</code>の場合は初期化パラメーターが存在しない
     */
    public String getInitParameter(String name);


    /**
     * フィルターの初期化パラメーターに含まれる名前を<code>String</code>の<code>Enumeration</code>として返します。空の<code>Enumeration</code>の場合、初期化パラメーターは存在しません。
     *
     * @return フィルターの初期化パラメーターに含まれる名前の<code>String</code>の<code>Enumeration</code>
     */
    public Enumeration<String> getInitParameterNames();

}
