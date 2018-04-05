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
 * サーブレットコンテナが初期化中にサーブレットに情報を渡すために使用するサーブレット設定オブジェクトです。
 */
 public interface ServletConfig {
    
    /**
     * サーブレットのインスタンスの名前を返します。
     * 
     * 名前はサーバの管理ツールから設定してもよいし、Webアプリケーションのデプロイメントディスクリプタで割り当ててもよいです。 
     * もしくは、登録されていない (つまり無名の) サーブレットのインスタンスであったときはサーブレットのクラス名になります。   
     *
     * @return	サーブレットのインスタンスの名前
     */
    public String getServletName();


    /**
     * 呼び出し元が実行中の {@link ServletContext} を返します。
     *
     * @return	呼び出し側がサーブレットコンテナと対話するために使用する {@link ServletContext} のオブジェクト
     * 
     * @see ServletContext
     */
    public ServletContext getServletContext();

    
    /**
     * 与えられた名前で初期化パラメーターを取得します。
     *
     * @param name 値を取得したい初期化パラメーターの名前
     *
     * @return 初期化パラメーターの値の<code>String</code>、<code>null</code>の場合は存在しない
     */
    public String getInitParameter(String name);


    /**
     * サーブレットの初期化パラメーターの名前を<code>String</code>オブジェクトの<code>Enumeration</code>として返します。
     * サーブレットに初期化パラメーターがない場合は空の<code>Enumeration</code>を返します。
     *
     * @return サーブレットの初期化パラメーターの名前を含んだ<code>String</code>オブジェクトの<code>Enumeration</code>
     */
    public Enumeration<String> getInitParameterNames();

}
