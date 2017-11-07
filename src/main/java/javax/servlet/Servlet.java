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


/**
 * 定義されたメソッドをすべてのサーブレットは実装しなければいけません。
 *
 * <p>サーブレットはウェブサーバーで動く小さなJavaのプログラムです。
 * サーブレットは通常、HTTP(HyperText Transfer Protocol)を通じてウェブクライアントからリクエストを受け取りレスポンスを返します。 
 *
 * <p>このインターフェースを実装するために一般的なサーブレットの場合は<code>javax.servlet.GenericServlet</code>を継承するか、
 * HTTPに特化したサーブレットの場合は<code>javax.servlet.http.HttpServlet</code>を継承することができます。
 *
 * <p>このインターフェースは、サーブレットを初期化する、リクエストを処理する、サーブレットをサーバーから取り除くメソッドが定義されています。
 * これらはライフサイクルメソッドとして知られており、以下のような順番で呼び出されます。
 * <ol>
 * <li>サーブレットのインスタンスが生成されると<code>init</code>メソッドにより初期化されます。
 * <li>クライアントから呼び出される度に<code>service</code>メソッドが実行されます。
 * <li>このサーブレットがサービスから取り除かれると<code>destroy</code>メソッドにより終了処理が行われ、その後、ガーベッジコレクションとファイナライズされます。
 * </ol>
 *
 * <p>このインターフェースではライフサイクルメソッドに加えて<code>getServletConfig</code>を提供しています。
 * これはサーブレットが起動時の情報を使用するために使うことができます。
 * また、<code>getServletInfo</code>で作者やバージョン、コピーライトなどのサーブレット自身についての基本的な情報を返すことが許されます。
 *
 * @author 	Various
 *
 * @see 	GenericServlet
 * @see 	javax.servlet.http.HttpServlet
 *
 */


public interface Servlet {

    /**
     * サーブレットがサービスに組み込まれるときにサーブレットコンテナにより呼び出されます。
     *
     * <p>サーブレットコンテナはサーブレットのインスタンスを生成した後に<code>init</code>メソッドを一回だけ呼び出します。
     * <code>init</code>メソッドはサーブレットがリクエストを受け取る前に正常に完了しなければいけません。
     *
     * <p>以下の場合にはサーブレットコンテナはサーブレットを実行状態にすることができません。
     * <ol>
     * <li><code>init</code>メソッドが<code>ServletException</code>を投げた
     * <li><code>init</code>メソッドがウェブサーバーで定義した時間内に処理を終了しない
     * </ol>
     *
     *
     * @param config			サーブレットの設定や初期化パラメーターが含まれる <code>ServletConfig</code> オブジェクト
     *
     * @exception ServletException 	サーブレットの通常の処理で例外が発生した
     *
     * @see 				UnavailableException
     * @see 				#getServletConfig
     *
     */

    public void init(ServletConfig config) throws ServletException;
    
    

    /**
     *
     * {@link ServletConfig} のオブジェクトを返します。これにはサーブレットを初期化するためのパラメーターが入っています。
     * この<code>ServletConfig</code> のオブジェクトは <code>init</code> に渡されたものが返されます。
     *
     * <p>このインターフェースの実装は <code>ServletConfig</code> のオブジェクトを格納しこのメソッドが返すようにします。 
     * このインターフェースを実装している {@link GenericServlet} クラスではすでにこれを行っています。
     *
     * @return		このサーブレットを初期化する <code>ServletConfig</code> のオブジェクト
     *
     * @see 		#init
     *
     */

    public ServletConfig getServletConfig();
    
    

    /**
     * サーブレットがリクエストに応答できるようにサーブレットコンテナによって呼び出されます。
     *
     * <p>このメソッドはサーブレットの<code>init()</code>メソッドが正常に完了した場合のみ呼び出されます。
     * 
     * <p> レスポンスのステータスコードはサーブレットがエラーをスローまたは送信するために常に設定するべきです。
     *
     * 
     * <p>サーブレットは通常、複数のリクエストを同時に処理できるようにサーブレットコンテナ内でマルチスレッドで度さします。
     * 開発者はファイルやネットワークコネクション、サーブレットのクラスやインスタンス変数などの共有リソースへのアクセスを同期させる必要があります。
     * マルチスレッドプログラミングに関する詳細な情報は 
     * <a href="http://java.sun.com/Series/Tutorial/java/threads/multithreaded.html">
     * the Java tutorial on multi-threaded programming</a>を参照してください。
     *
     * 訳注：原文はリンクが切れたままになっているので今のJavaのチュートリアルのページだと<a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/">Lesson: Concurrency</a>が良さそう。
     *
     * @param req 	クライアントのリクエストが含まれる<code>ServletRequest</code>のオブジェクト
     *
     * @param res 	サーブレットのレスポンスが含まれる<code>ServletResponse</code>のオブジェクト
     *
     * @exception ServletException 	サーブレットの通常の処理で例外が発生した 
     *
     * @exception IOException 		I/Oの例外が発生した
     *
     */

    public void service(ServletRequest req, ServletResponse res)
	throws ServletException, IOException;
	
	

    /**
     * サーブレットの情報を返します。作者やバージョン、コピーライトなどです。
     * 
     * <p>このメソッドが返す文字列はプレーンテキストにして、あらゆる種類のマークアップ(HTML、XMLその他)で装飾しないほうがよいです。
     *
     * @return 		サーブレットの情報が入った<code>String</code>
     *
     */

    public String getServletInfo();
    
    

    /**
     * サーブレットがサービスから取り除かれるときにサーブレットコンテナにより呼び出されます。
     * このメソッドはすべてのスレッドでサーブレットの<code>service</code>メソッドが終了したかタイムアウトした後に一度だけ呼び出されます。
     * サーブレットコンテナはこのメソッドを呼出した後に同じインスタンスで<code>service</code>メソッドを再度呼び出すことはありません。
     *
     * <p>このメソッドは保持されているすべてのリソース（メモリ、ファイルハンドル、スレッドなど）をクリーンアップする機会を与えます。
     * また、サーブレットのメモリ上の状態とあらゆる永続状態が同期されるように注意してください。
     *
     */

    public void destroy();
}
