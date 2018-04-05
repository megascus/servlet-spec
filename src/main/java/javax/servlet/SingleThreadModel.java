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
 * サーブレットが同時に一つのリクエストのみを扱うことを保証します。このインターフェースはメソッドを持ちません。
 *
 * <p>もしサーブレットがこのインターフェースを実装したのであれば、二つのスレッドが同時にこのサーブレットの<code>service</code>メソッドを実行しないことが <i>保証</i>されます。
 * サーブレットコンテナは一つのインスタンスのサーブレットを排他制御したり、サーブレットのインスタンスをプールし新しいリクエスト毎に空いているサーブレットを割り当てる事で保証します。
 *
 * <p>注意： SingleThreadModelはすべてのスレッドセーフの問題を解決しません。
 * 例えば、SingleThreadModelのサーブレットを使用したとしてもセッションの属性やstatic変数は複数のスレッドに乗った複数のリクエストから同時にアクセスすることができます。
 * 開発者はスレッドセーフの問題を解決するためにこのインターフェースを実装する代わりに、インスタンス変数の使用を避けたりリソースにアクセスするコードのブロックを同期させる等の手段を取ることをお勧めします。
 * このインターフェースはServlet API version 2.4で非推奨です。
 *
 *
 * @author	Various
 *
 * @deprecated	Java Servlet API 2.4以降での直接の代替手段はありません。
 */
@Deprecated
public interface SingleThreadModel {
}
