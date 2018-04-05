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

package javax.servlet.http;

import java.util.Enumeration;

/**
 *
 * @author		Various
 *
 * @deprecated		セキュリティの理由によりJava(tm) Servlet API 2.1以降非推奨です。
 * 代替手段はありません。
 * のちのバージョンでこのAPIは削除されます。
 * @see			HttpSession
 * @see			HttpSessionBindingEvent
 * @see			HttpSessionBindingListener
 *
 */

@Deprecated
public interface HttpSessionContext {

    /**
     *
     * @deprecated 	セキュリティの理由によりJava(tm) Servlet API 2.1以降非推奨です。
     *          このメソッドはnullを返さなければいけません。また、のちのバージョンでこのAPIは削除されます。
     * 
     * @param sessionId 返してほしいセッションのID
     *
     * @return すべての場合にnull
     */
    @Deprecated
    public HttpSession getSession(String sessionId);
    
    
    
  
    /**
     *
     * @deprecated	セキュリティの理由によりJava(tm) Servlet API 2.1以降非推奨です。
     *          このメソッドは空の<code>Enumeration</code>を返さなければいけません。また、のちのバージョンでこのAPIは削除されます。
     *
     * @return null 
     *
     */
    @Deprecated
    public Enumeration<String> getIds();
}





