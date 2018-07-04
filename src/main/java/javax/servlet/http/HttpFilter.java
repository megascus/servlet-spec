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
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
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
 */

package javax.servlet.http;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * <p>Webサイトに適したHTTPフィルターを作成するためにサブクラスが作成される抽象クラスを提供します。
 * <code>HttpFilter</code>のサブクラスは{@link #doFilter(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)}メソッドをオーバーライドする必要があります。</p>
 * 
 * <p>フィルタは通常、サーバー上でマルチスレッドで実行されるため、フィルタは同時に行われるリクエストを処理し、共有リソースへのアクセスを同期するよう注意しなければなりません。
 * 
 * 共有リソースにはインスタンス変数、クラス変数などのインメモリデータや、ファイル、データベース接続、ネットワーク接続などの外部オブジェクトが含まれます。
 * Javaプログラムで複数のスレッドを処理する方法の詳細は、<a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/">Java Tutorial on Multithreaded Programming</a>を参照してください。
 *
 * @author  Various
 *
 * @since Servlet 4.0
 */

public abstract class HttpFilter extends GenericFilter
{
    
    /**
     * <p>このクラスはabstractクラスなので何もしません。</p>
     * 
     * <p>訳注：理由になってない・・・・・
     * 
     * @since 4.0
     */

    public HttpFilter() { }

    /**
     * <p>フィルターの<code>doFilter</code>メソッドはチェーンの最後にあるリソースへのクライアントからの要求により、リクエスト/レスポンスのペアがチェーンを通過するたびにコンテナによって呼び出されます。
     * このメソッドに渡されたFilterChainは、フィルターがチェーンの次のエンティティへリクエストとレスポンスを渡せるようにします。
     * このメソッドをオーバーライドする必要はありません。</p>
     * 
     * <p>デフォルトの実装では渡された{@code req}オブジェクトと{@code res}オブジェクトがそれぞれ{@link HttpServletRequest}と{@link HttpServletResponse}インスタンスであるかどうかを調べ、
     * そうでない場合は{@link ServletException}がスローされます。
     * それ以外の場合はprotected {@link #doFilter(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)}メソッドが呼び出されます。</p>
     *
     * @param req   クライアントのリクエストが含まれる{@link ServletRequest}のオブジェクト
     *
     * @param res  フィルターがクライアントに送るレスポンスが含まれる{@link ServletResponse}のオブジェクト
     * 
     * @param chain     次のフィルターもしくはリソースを実行するための <code>FilterChain</code>
     * 
     * @throws IOException   フィルターがリクエストを処理する中でI/Oエラーが発生した
     *
     * @throws ServletException  リクエストを処理できなかったか、いずれかのパラメータがそれぞれ{@link HttpServletRequest}もしくは{@link HttpServletResponse}のインスタンスでない場合
     *
     * @since Servlet 4.0
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (!(req instanceof HttpServletRequest &&
                res instanceof HttpServletResponse)) {
            throw new ServletException("non-HTTP request or response");
        }

        this.doFilter((HttpServletRequest)req, (HttpServletResponse)res, chain);
    }
    
    /**
     *
     * <p>フィルターの<code>doFilter</code>メソッドはチェーンの最後にあるリソースへのクライアントからの要求により、リクエスト/レスポンスのペアがチェーンを通過するたびにコンテナによって呼び出されます。
     * このメソッドに渡されたFilterChainは、フィルターがチェーンの次のエンティティへリクエストとレスポンスを渡せるようにします。</p>
     * 
     * <p>デフォルト実装では単純に{@link FilterChain#doFilter}が呼び出されます。</p>
     *
     * @param req   クライアントのリクエストが含まれる{@link HttpServletRequest}のオブジェクト
     *
     * @param res  フィルターがクライアントに送るレスポンスが含まれる{@link HttpServletResponse}のオブジェクト
     * 
     * @param chain     the <code>FilterChain</code> for invoking the next filter or the resource
     * 
     * @throws IOException   フィルターがリクエストを処理する中でI/Oエラーが発生した
     *
     * @throws ServletException  リクエストが処理できなかった場合
     *
     * @since Servlet 4.0
     */
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(req, res);
    }
    
}


