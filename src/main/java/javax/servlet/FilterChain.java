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
     * FilterChainはサーブレットコンテナによって開発者に提供されるオブジェクトで、フィルターされたリソース要求の呼び出しチェーンを表します。
     * フィルターはFilterChainを使用してチェーン内の次のフィルターを呼び出します。また、呼び出し元のフィルターがチェーンの最後のフィルターである場合はリソースを呼び出します。
     *
     * @see Filter
     * @since Servlet 2.3
     */

public interface FilterChain {

    /**
     * チェーンの次のフィルターが実行される契機となるか、呼び出し元のフィルターがチェーンの最後のフィルターである場合はリソースを呼び出す契機となります。
     * Causes the next filter in the chain to be invoked, or if the calling filter is the last filter
     * in the chain, causes the resource at the end of the chain to be invoked.
     *
     * @param request チェーンに沿って渡されるリクエスト。
     * @param response チェーンに沿って渡されるレスポンス。
     * @throws IOException 処理中にI/O関係のエラーが発生した
     * @throws ServletException FilterChainの通常の処理で例外が発生した
     */
    public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException;

}
