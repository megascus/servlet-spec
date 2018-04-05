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

import java.io.IOException;

/**
 * <p>フィルターはリソース(サーブレットもしくは静的コンテンツ)へのリクエストやレスポンス、もしくはその両方をフィルタリングするタスクを処理するためのオブジェクトです。</p>
 * 
 * <p>フィルターは<code>doFilter</code>メソッドによりフィルタリングを行います。
 * それぞれのフィルターはフィルタリングタスクのために必要なリソースをロードしたりするために、初期化パラメーターをもつ<code>FilterConfig</code>オブジェクトや使用できる<code>ServletContext</code>への参照を持ちます。
 *
 * <p>フィルターはWebアプリケーションのデプロイメントディスクリプタにより構成されます。
 *
 * <p>以下のような用途のために使用できます。
 * <ol>
 * <li>認証フィルター
 * <li>ロギングと監査フィルター
 * <li>イメージ変換フィルター
 * <li>データ圧縮フィルター
 * <li>暗号化フィルター
 * <li>トークン分割フィルター
 * <li>リソースへのアクセスイベントを引き起こすフィルター
 * <li>XSL/Tフィルター
 * <li>Mime-typeチェーンフィルター
 * </ol>
 *
 * @since Servlet 2.3
 */

public interface Filter {

    /** 
     * <p>フィルターがサービスに組み込まれるときにサーブレットコンテナにより呼び出されます。</p>
     *
     * <p>サーブレットコンテナはフィルターのインスタンスを生成した後にinitメソッドを一回だけ呼び出します。initメソッドはフィルタリング動作を行う前に正常に完了しなければいけません。</p>
     * 
     * <p>以下の場合にはサーブレットコンテナはフィルターを実行状態にすることができません。</p>
     * <ol>
     * <li>ServletExceptionが投げられた
     * <li>サーブレットコンテナで定義した時間内に処理を終了しない
     * </ol>
     * 
     * @implSpec
     * デフォルト実装では何も行いません。
     *
     * @param filterConfig フィルターの設定や初期化パラメーターが含まれる<code>FilterConfig</code>オブジェクト
     * @throws ServletException フィルターの通常の処理で例外が発生した
     */
    default public void init(FilterConfig filterConfig) throws ServletException {}
	
	
    /**
     * フィルターの<code>doFilter</code>メソッドはチェーンの最後にあるリソースへのクライアントからの要求により、リクエスト/レスポンスのペアがチェーンを通過するたびにコンテナによって呼び出されます。
     * このメソッドに渡された<code>FilterChain</code>は、フィルターがチェーンの次のエンティティへリクエストとレスポンスを渡せるようにします。
     *
     * <p>このメソッドの典型的な実装は以下のようなものになるでしょう。
     * <ol>
     * <li>リクエストを検査する。
     * <li>必要に応じて入力フィルタリングのためにコンテンツやヘッダーをフィルターするカスタム実装でリクエストをラップする。
     * <li>必要に応じて出力フィルタリングのためにコンテンツやヘッダーをフィルターするカスタム実装でレスポンスをラップする。
     * <li>FilterChainオブジェクトを利用(<code>chain.doFilter()</code>)して次のエンティティをチェーン実行する、しないの<strong>どちらかを選択する</strong>。
     * <li>次のフィルターチェーンのエンティティが実行された後に直接レスポンスのヘッダーに値をセットする。
     * </ol>
     *
     * @param request クライアントのリクエストが含まれる<code>ServletRequest</code>のオブジェクト
     * @param response フィルターのレスポンスが含まれる<code>ServletResponse</code>のオブジェクト
     * @param chain the 次のフィルターやリソースの実行に使用される<code>FilterChain</code>
     * @throws IOException 処理中にI/O関係のエラーが発生した
     * @throws ServletException フィルターの通常の処理で例外が発生した
     *
     * @see UnavailableException
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException;


    /**
     * <p>フィルターがサービスから取り除かれるときにサーブレットコンテナにより呼び出されます。</p>
     *
     * <p>このメソッドはすべてのスレッドでフィルターの<code>doFilter</code>メソッドが終了したかタイムアウトした後に一度だけ呼び出されます。
     * サーブレットコンテナはこのメソッドを呼出した後に同じインスタンスで<code>doFilter</code>メソッドを再度呼び出すことはありません。</p>
     *
     * <p>このメソッドは保持されているすべてのリソース（メモリ、ファイルハンドル、スレッドなど）をクリーンアップする機会を与えます。
     * また、フィルターのメモリ上の状態とあらゆる永続状態が同期されるように注意してください。</p>
     * 
     * @implSpec
     * デフォルト実装では何も行いません。
     */
    default public void destroy() {}
}
